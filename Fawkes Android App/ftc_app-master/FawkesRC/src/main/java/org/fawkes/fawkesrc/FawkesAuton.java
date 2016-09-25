package org.fawkes.fawkesrc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Environment;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.util.Range;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**Coded by:
 * <<<
 __      __                   _                             _
 \ \    / / ___     ___    __| |  __ __ __  ___      _ _   | |__    ___
 \ \/\/ / / _ \   / _ \  / _` |  \ V  V / / _ \    | '_|  | / /   (_-<
 \_/\_/  \___/   \___/  \__,_|   \_/\_/  \___/   _|_|_   |_\_\   /__/_
 _|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|
 "`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'

 * >>>
 *
 * For
 *
 * <<<
  ███████╗ █████╗ ██╗    ██╗██╗  ██╗███████╗███████╗
  ██╔════╝██╔══██╗██║    ██║██║ ██╔╝██╔════╝██╔════╝
  █████╗  ███████║██║ █╗ ██║█████╔╝ █████╗  ███████╗
  ██╔══╝  ██╔══██║██║███╗██║██╔═██╗ ██╔══╝  ╚════██║
  ██║     ██║  ██║╚███╔███╔╝██║  ██╗███████╗███████║
 ╚═╝     ╚═╝  ╚═╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚══════╝
 * >>>
 * Robotics #10919
 */
public class FawkesAuton extends LinearOpMode {

  DcMotor frontright, frontleft, backright, backleft;
  Servo climber;
  final static double WHEEL_RADIUS = 8;
  final static double GEAR_RATIO = 1.0/3.0;
  int curPix;
  ARGB color;
  static int left=50;
  static int right=50;
  public boolean rightSide=true, leftSide=false;
  File image;

  public static void classify(char c, char side){
    int col=0;
    switch(c){
      case 'r': col=1; break;
      case 'b': col=-1; break;
      case 'o': col=0; break;
      default: col=0; break;
    }
    if(side=='l') left+=col;
    else if(side=='r') right+=col;
    else Log.e("Image","Wrong arg to classify()");
  }

  public boolean processImage(File image){
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(),options);
    Log.e("Image","Converted into Bitmap");
    float height = bitmap.getHeight();
    float width = bitmap.getWidth();
    Log.e("Image", "Image is " + width + "x" + height);
    telemetry.addData("Imaging", "Image is " + width + "x" + height);

    //Now let's begin image processing

    curPix = bitmap.getPixel((int)(width/4),(int)(height/2));
    color = new ARGB(curPix);
    //gets us the argb
    Log.e("Image","Midleft pixel is "+color.toString());
    Log.e("Image","Midleft pixel color is "+color.getColor());
    classify(color.getColor(),'l');
    telemetry.addData("Imaging", "Pix is " + color.toString());
    telemetry.addData("Imaging","Pix is color "+color.getColor());
    curPix = bitmap.getPixel((int)(3*width/4),(int)(height/2));
    color.set(curPix);
    //gets us the argb
    Log.e("Image","Midright pixel is "+color.toString());
    Log.e("Image","Midright pixel color is "+color.getColor());
    classify(color.getColor(),'r');
    telemetry.addData("Imaging","Pix is "+color.toString());
    telemetry.addData("Imaging","Pix is color "+color.getColor());
    for(int x = 0; x<(width/2); x++)
      for (int y = 0; y < height; y++)
        classify(color.set(bitmap.getPixel(x, y)).getColor(), 'l');
    for(int x = (int)(width/2); x<width; x++)
      for(int y = 0; y<height; y++)
        classify(color.set(bitmap.getPixel(x,y)).getColor(),'r');
    Bitmap mutableBmp = bitmap.copy(bitmap.getConfig(),true);
    for(int x = 0; x<width; x++) {
      for (int y = 0; y < height; y++) {
        if (color.set(bitmap.getPixel(x, y)).getColor() == 'b')
          mutableBmp.setPixel(x, y, Color.BLUE);
        else if(color.set(bitmap.getPixel(x, y)).getColor() == 'r')
          mutableBmp.setPixel(x,y,Color.RED);
        else mutableBmp.setPixel(x,y,Color.BLACK);
      }
    }
    int minBlue = 0, maxBlue = 0, minRed = 0, maxRed = 0;
    int minBlueb = (int)(height/2), maxBlueb = (int)(height/2), minRedb = (int)(height/2), maxRedb = (int)(height/2);
    if(right<left){
      minBlue =(int) (3*width/4);
      maxBlue =(int) (3*width/4);
      minRed = (int) (width/4);
      maxRed = (int) (width/4);
    }
    if(left<right){
      minRed =(int) (3*width/4);
      maxRed =(int) (3*width/4);
      minBlue = (int) (width/4);
      maxBlue = (int) (width/4);
    }
    for(int x = 0; x<width; x++) {
      for(int y = 0; y< height; y++) {
        if(color.set(mutableBmp.getPixel(x,y)).getColor() == 'b'){
          if(x<minBlue){
            if(right<left && x>(int)(width/2))
              minBlue = x;
            else if(right>left) minBlue = x;
          }
          if(x>maxBlue){
            if(right>left && x<(int)(width/2))
              maxBlue = x;
            else if(right<left) maxBlue = x;
          }
          if(y<minBlueb) minBlueb = y;
          if(y>maxBlueb) maxBlueb = y;
        }
        else if(color.set(mutableBmp.getPixel(x,y)).getColor() == 'r'){
          if(x<minRed) minRed = x;
          if(x>maxRed) maxRed = x;
          if(y<minRedb) minRedb = y;
          if(y>maxRedb) maxRedb = y;
        }
      }
    }
    for(int y = minBlueb; y<=maxBlueb; y++) {
      mutableBmp.setPixel(minBlue, y, Color.WHITE);
      mutableBmp.setPixel(maxBlue, y, Color.WHITE);
    }
    for(int y = minRedb; y<=maxRedb; y++) {
      mutableBmp.setPixel(minRed, y, Color.WHITE);
      mutableBmp.setPixel(maxRed, y, Color.WHITE);
    }
    for(int x = minBlue; x<=maxBlue; x++) {
      mutableBmp.setPixel(x, minBlueb, Color.WHITE);
      mutableBmp.setPixel(x, maxBlueb, Color.WHITE);
    }
    for(int x = minRed; x<=maxRed; x++) {
      mutableBmp.setPixel(x, minRedb, Color.WHITE);
      mutableBmp.setPixel(x, maxRedb, Color.WHITE);
    }
    Log.e("Image","Blue is ("+minBlue+", "+minBlueb+") to ("+maxBlue+", "+maxBlueb+")");
    Log.e("Image","Red is ("+minRed+", "+minRedb+") to ("+maxRed+", "+maxRedb+")");

    //drew bounds

    try {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      mutableBmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
      File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
              + File.separator + "F_Auton_CV.jpg");
      Log.e("Image","Finished saving CV edit");
      f.createNewFile();
      FileOutputStream fo = new FileOutputStream(f);
      fo.write(bytes.toByteArray());
      fo.close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    Log.e("Image","Left is at gb "+left);
    Log.e("Image","Right is at gb "+right);
    telemetry.addData("Imaging","Left "+left+", Right "+right);
    if(right<left) return rightSide;
    else return leftSide;
  }

  public boolean processBlocks(File image){
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(),options);
    Log.e("Image","Converted into Bitmap");
    float height = bitmap.getHeight();
    float width = bitmap.getWidth();
    Log.e("Image", "Image is " + width + "x" + height);
    telemetry.addData("Imaging", "Image is " + width + "x" + height);
    Bitmap mutableBmp = bitmap.copy(bitmap.getConfig(),true);
    for(int x=0; x<width; x++){
      for(int y = 0; y<height; y++) {
        color.set(bitmap.getPixel(x,y));
        if(color.getColor()=='y') mutableBmp.setPixel(x,y,Color.YELLOW);
        else mutableBmp.setPixel(x,y,Color.BLACK);
      }
    }


    return true;
  }

  public static double encode(double dist) {
    return 1440 * (dist / (Math.PI * WHEEL_RADIUS * 2 * GEAR_RATIO));
  }

  public void pwr(double fr, double fl, double br, double bl) {
    fr = Range.clip(fr, -0.78, 0.78);
    fl = Range.clip(fl, -.78, 0.78);
    br = Range.clip(br, -.78, .78);
    bl = Range.clip(bl, -.78, .78);
    frontright.setPower(fr);
    frontleft.setPower(fl);
    backleft.setPower(bl);
    backright.setPower(br);
  }

  public void pwr(double l, double r) {
    pwr(r, l, r, l);
  }


  @Override
  public void runOpMode() throws InterruptedException {
    frontleft = hardwareMap.dcMotor.get("front_left");
    frontright = hardwareMap.dcMotor.get("front_right");
    backleft = hardwareMap.dcMotor.get("back_left");
    backright = hardwareMap.dcMotor.get("back_right");
    climber = hardwareMap.servo.get("climber");
    FawkesRCActivity main = ((FawkesRCActivity) hardwareMap.appContext);

    pwr(0.78, -.78);
    sleep(3500);
   // snap(main);
    //moves forwards, snaps picture
//    if(processImage(image)==rightSide){
  //    pwr(0.4,0.4);
    //  sleep(400);
      //
    //}
    climber.setPosition(1.0);
    pwr(0, 0);
    pwr(0.3, 0.3);
    sleep(600);
    pwr(.78, -.78);
    sleep(1250);
    pwr(0, 0);
    pwr(-.78, .78);
    sleep(1250);
    pwr(-.3, -.3);
    sleep(1400);
    pwr(.78, -.78);
    sleep(700);
    pwr(0, 0);
    climber.setPosition(0.7);
    sleep(1200);
    climber.setPosition(0);

    pwr(-.78,.78);
    sleep(700);
    pwr(0.3,0.3);
    sleep(1400);
    pwr(.78,-.78);
    sleep(1250);
    pwr(0,0);

    while(true)
      sleep(1000000);
  } // loop

}