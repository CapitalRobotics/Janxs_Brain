package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.other;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.InputStream;

public class FileUtils {

    public static String readAssetFile(Context context, String fileName) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}