package base.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraHelper {

    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".JPEG";
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/LOS/IMAGE");
        if (!storageDir.exists())
            storageDir.mkdirs();

        return new File(storageDir, imageFileName);
        /*return File.createTempFile(
                imageFileName,  *//* prefix *//*
                "",             *//* suffix *//*
                storageDir      *//* directory *//*
        );*/
    }


    public static File createImageFileFromServer(String name) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = name;
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/BARAMA/TSC");
        if (!storageDir.exists())
            storageDir.mkdirs();

        return new File(storageDir, imageFileName);
        /*return File.createTempFile(
                imageFileName,  *//* prefix *//*
                "",             *//* suffix *//*
                storageDir      *//* directory *//*
        );*/
    }

    public static void resizePhoto(String path) {
        File imgFile = new File(path);
        if(imgFile.exists()){
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 4;

            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            byte[] bitmapData = bos.toByteArray();

            try {
                FileOutputStream fos = new FileOutputStream(imgFile);
                fos.write(bitmapData);

                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            bitmap.recycle();
            System.gc();

        }
    }

    public static void deleteFile(String path){
        File file = new File(path);
        if(file.exists())
        {
            file.delete();
        }
    }

    public static String getFileInfo(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        return options.outHeight + " " + options.outWidth + " ";
    }

    public static String getFilename(String path) {
        int index = 0;

        for (int i = path.length() - 1; i >= 0; i--) {
            if (path.charAt(i) == '/') {
                index = i;
                break;
            }
        }

        return path.substring(index + 1);
    }

    public static void copyFile(String sourceFilePath, File destFile) throws IOException {
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            return;
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public static Bitmap decodeSampledBitmapFromPath(String path, int sampleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = sampleSize;

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static String getDefaultImagePath(String filename) {
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/MufInS/MSVY");
        if (!storageDir.exists())
            storageDir.mkdirs();
        File file = new File(storageDir, filename);

        return file.getAbsolutePath();
    }

    public static void createImageTarget(Bitmap bitmap, String filename) {
        try {
            File storageDir = new File(Environment.getExternalStorageDirectory()+"/MufInS/MSVY");
            if (!storageDir.exists())
                storageDir.mkdirs();
            File file = new File(storageDir, filename);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
