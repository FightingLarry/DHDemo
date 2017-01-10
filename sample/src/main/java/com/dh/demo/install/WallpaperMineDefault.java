package com.dh.demo.install;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WallpaperMineDefault {

    static String deepFile(Context context, String path) {
        try {
            String fileList[] = context.getAssets().list(path);
            if (fileList.length > 0) {// 如果是目录
                for (String fileName : fileList) {
                    String from = path + File.separator + fileName;
                    String to = getInitHistoryDir(context);
                    File file = getFilePath(fileName, context);
                    if (!file.exists()) {
                        copyFile(context, from, to, fileName);
                    }
                    return file.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    private static void copyFile(Context context, String from, String to, String name) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = context.getAssets().open(from);
            fos = new FileOutputStream(new File(to, name));
            byte[] buffer = new byte[1024];
            int count = 0;
            while (true) {
                count++;
                int len = is.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static String getInitHistoryDir(Context context) {
        File file = context.getExternalFilesDir("apk");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath().toString();
    }

    private static File getFilePath(String name, Context context) {
        return new File(getInitHistoryDir(context) + File.separator + name);
    }
}
