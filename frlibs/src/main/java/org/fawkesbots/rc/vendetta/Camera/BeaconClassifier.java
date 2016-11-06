package org.fawkesbots.rc.vendetta.Camera;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import org.fawkesbots.rc.vendetta.ARGB;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Priansh on 11/2/16.
 */
public class BeaconClassifier {

    final static int WIDTH = 0,HEIGHT=1, LEFT=0, RIGHT=1;

    //processBitmap returns an integer array containing the left and right colors
    public int[] processBitmap(Bitmap bmp){
        int[] colors = {0,0};
        int guess = 0;
        int pix;
        int[] dimensions = {bmp.getHeight(),bmp.getWidth()};

        //Start by classifying middle pixels
        pix = bmp.getPixel(dimensions[WIDTH]/4,dimensions[HEIGHT]/2); //middle left pixel
        colors[LEFT] = colors[LEFT]+ARGB.classify((new ARGB(pix)).getColor());
        pix = bmp.getPixel(dimensions[WIDTH]*3/4,dimensions[HEIGHT]/2); //middle right pixel
        colors[RIGHT] = colors[RIGHT]+ARGB.classify((new ARGB(pix)).getColor());

        ARGB pixel = new ARGB(0);

/*
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
                else if(color.set(bitmap.getPixel(x,y)).getColor() == 'y')
                    mutableBmp.setPixel(x,y,Color.YELLOW);
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
        else return leftSide;*/
        return new int[2];
    }


}
