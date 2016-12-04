package org.fawkesbots.rc.vendetta.Camera;

import android.graphics.*;
import android.util.*;
import java.io.*;

/**
 * Created by Priansh on 11/2/16.
 */
public class ImageUtil {

    public static Bitmap bmpFromImage(File image) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(),options);
        return bitmap;
    }


}
