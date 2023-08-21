package com.example.cleancodeofledger.tools;

import android.content.Context;

import java.io.File;

public class FileUtils {
    public static String getApplicationFolder(Context context, String subFolder) {
        File file = new File(context.getExternalFilesDir("").getParentFile(), subFolder);
        if (file != null) {
            if (!file.exists())
                file.mkdirs();
            return file.getAbsolutePath();
        }
        return context.getExternalCacheDir().getAbsolutePath();
    }
}
