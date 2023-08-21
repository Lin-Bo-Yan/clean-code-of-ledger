package com.example.cleancodeofledger.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ThumbnailUtils {

    // Bitmap 轉 Base64
    public static String getBase64FromBitmap(Bitmap bitmap) {
        String base64 = "";
        ByteArrayOutputStream baos = null;
        try{
            if(bitmap != null){
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                baos.flush();
                baos.close();
                byte[] byteArray = baos.toByteArray();
                base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(baos != null){
                    baos.flush();
                    baos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return base64;
    }

    // 檔案路徑 轉 Base64
    public static String getBase64FromPath(String path) {
        String base64 = "";
        try {
            File file = new File(path);
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    //可用
    public static String resizeAndConvertToBase64(String imagePath, int thumbnailSize) {
        // 加載原始圖像
        Bitmap originalBitmap = BitmapFactory.decodeFile(imagePath);
        // 計算縮放比例
        float scaleFactor = calculateScaleFactor(originalBitmap.getWidth(), originalBitmap.getHeight(), thumbnailSize);
        // 計算縮放後的尺寸
        int targetWidth = Math.round(originalBitmap.getWidth() * scaleFactor);
        int targetHeight = Math.round(originalBitmap.getHeight() * scaleFactor);
        // 縮放圖像為縮略圖
        Bitmap thumbnailBitmap = Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, false);
        // 釋放原始圖像資源
        originalBitmap.recycle();
        // 將縮略圖轉換為Base64字符串
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumbnailBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    // 計算縮放比例
    private static float calculateScaleFactor(int originalWidth, int originalHeight, int targetSize) {
        float scale;
        if (originalWidth > originalHeight) {
            scale = (float) targetSize / originalWidth;
        } else {
            scale = (float) targetSize / originalHeight;
        }
        return scale;
    }
}
