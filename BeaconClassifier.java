package org.fawkesbots.rc.vendetta.Camera;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
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
    public static boolean debugImage(Bitmap bmp, int[] dimensions, ARGB pixel) {
        Bitmap mutableBmp = bmp.copy(bmp.getConfig(),true);
        for(int x = 0; x<dimensions[WIDTH]; x++)
            for (int y = 0; y < dimensions[HEIGHT]; y++)
                mutableBmp.setPixel(x,y, ARGB.convertToColor(pixel.set(bmp.getPixel(x,y)).getColor()));
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
        return true;
    }

    //NEW CODE
    public static int[][] blurBitmap (Bitmap bmp){
        Log.e("Camera","Bitmap blurring started");
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[][] pic = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pic[row][col] = bmp.getPixel(row,col);
            }
        }
        int[][] blurry = new int[width][height];
        for (int row = 1; row < height-1; row++) {
            for (int col = 1; col < width - 1; col++) {
               // int topLeftPixel = pic[row - 1][col];
                //int topMiddlePixel = pic[row - 1][col];
                //int topRightPixel = pic[row - 1][col + 1];
                //int leftPixel = pic[row][col - 1];
                //int rightPixel = pic[row][col + 1];
                //int bottomLeftPixel = pic[row + 1][col - 1];
                //int bottomMiddlePixel = pic[row + 1][col];
                //int bottomRightPixel = pic[row + 1][col + 1];
                int mainPixel = (pic[row - 1][col] + pic[row - 1][col] + pic[row - 1][col + 1] + pic[row][col - 1] + pic[row][col + 1] + pic[row + 1][col - 1] + pic[row + 1][col] + pic[row + 1][col + 1])/ 8;
                blurry[row][col] = mainPixel;
            }
        }
        //you need to average the pixel values of nearby pixels so they're the same and blurred
        //edges are not blurred
        return blurry;
    }

    //MODIFIED BITMAP PROCESSING
    public static int[] processBitmap(int[][] blurry){
        Log.e("Camera","Bitmap processing started");
        int[] colors = {0,0};
        int guess = 0;
        int pix;
        int[] dimensions = {blurry.length,blurry[0].length};
        ARGB pixel = new ARGB(0);
        //we now traverse the left and right sides of the image independently
        for(int y = 0; (y<(dimensions[HEIGHT])-10)&&(y< blurry.length); y=y+5) {
            for (int x = 0; x < (dimensions[WIDTH]/2); x=x+5)
                colors[0] = colors[0] + blurry[x][y];
            for (int x = (dimensions[WIDTH] / 2); x < dimensions[WIDTH]; x=x+5)
                colors[1] = colors[1] + blurry[x][y];
        }
        return colors;
    }



    //BLUE IS NEGATIVE
    /*public static int[] processBitmap(Bitmap bmp){
        Log.e("Camera","Bitmap processing started");
        int[] colors = {0,0};
        int guess = 0;
        int pix;
        int[] dimensions = {bmp.getHeight(),bmp.getWidth()};
        ARGB pixel = new ARGB(0);
        //we now traverse the left and right sides of the image independently
        for(int y = 0; (y<(dimensions[HEIGHT])-10)&&(y<bmp.getHeight()); y=y+5) {
            for (int x = 0; x < (dimensions[WIDTH]/2); x=x+5)
                colors[0] = colors[0] + ARGB.classify(pixel.set(bmp.getPixel(x, y)).getColor());
            for (int x = (dimensions[WIDTH] / 2); x < dimensions[WIDTH]; x=x+5)
                colors[1] = colors[1] + ARGB.classify(pixel.set(bmp.getPixel(x, y)).getColor());
        }
        return colors;
    }*/
}
