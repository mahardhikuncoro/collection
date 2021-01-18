package base.widget;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.collection.mobile.application.BuildConfig;
import id.collection.mobile.application.R;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

public class BaseCamera extends AppCompatActivity {

    protected static final int REQUEST_IMAGE_CAPTURE = 100;
    protected static final int REQUEST_IMAGE_RECAPTURE = 101;
    protected static final int SELECT_PICTURE = 102;
    protected static final int RESELECT_PICTURE = 103;
    protected boolean fromCamera;
    protected int photoId;
    protected int width;
    protected Uri output;
    protected String category;
    protected String photoPath;
    protected String photoPath2;
    protected String photoPathGallery;

    public void intentChooser(final Integer photoId, final Context contextWrapper, final PackageManager packageManager,
                              final Activity activity, final String category) {
        final String[] items = new String[]{"Dari Kamera", "Dari Galeri"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(contextWrapper, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(contextWrapper);
        builder.setTitle("Pilih Gambar");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    if (photoId == null) {
                        takePicture(packageManager, activity, category);
                    } else {
                        recapturePhoto(photoId, packageManager, activity);
                    }
                }
                if (item == 1) {
                    if (photoId == null) {
                        openGallery(null, activity);
                    } else {
                        openGallery(photoId, activity);
                    }
                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void takePictureMod(final PackageManager packageManager, final Activity activity, String category){
        fromCamera = true;

        this.category = category;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (category.equals("PROFILE")) {
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    photoPath = photoFile.getAbsolutePath();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (photoFile != null) {
                    output = Uri.fromFile(photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }else if (category.equals("KTP")){
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                File photoFile2 = null;
                try {
                    photoFile2 = createImageFile();
                    photoPath2 = photoFile2.getAbsolutePath();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (photoFile2 != null) {
                    output = Uri.fromFile(photoFile2);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile2));
                    activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }

        }
    }
    public void takePicture(final PackageManager packageManager, final Activity activity, String category){
        fromCamera = true;

        this.category = category;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                photoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {

                output =  Uri.fromFile(photoFile);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(this,
                                    getString(R.string.packageProvider),photoFile));
                }else {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                }

                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    public void recapturePhoto(Integer photoId, final PackageManager packageManager, final Activity activity) {
        fromCamera = true;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                photoPath = photoFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(this,
                                    getString(R.string.packageProvider),photoFile));
                }else {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                }
                if (photoId != null) {
                    this.photoId = photoId;
                    activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_RECAPTURE);
                }
            }
        }
    }
    public void openGallery(Integer photoId, final Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        if (photoId == null){
            activity.startActivityForResult(Intent.createChooser(intent, "Pilih Aksi"), SELECT_PICTURE);
        } else {
            this.photoId = photoId;
            activity.startActivityForResult(Intent.createChooser(intent, "Pilih Aksi"), RESELECT_PICTURE);
        }
    }

    public void getFromGallery(Intent data){
        Uri uri = data.getData();
        photoPathGallery = getRealPathFromURI(this, uri);
        try {
            File photoFile = createImageFile();
            photoPath = photoFile.getAbsolutePath();
            copyFile(photoPathGallery,photoFile);
            checkResolution(photoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkResolution(final String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
            final int height = options.outHeight;
            final int width = options.outWidth;

            if ((height*width) >= 1228800) {
                resizePhoto(path);
            }
//            new LocationActivity(this, new LocationActivity.LocationCallback() {
//                @Override
//                public void getPosition(Double lat, Double longi) {
//                    saveImage(path, height, width, lat, longi);
//                }
//            });
            bitmap.recycle();
            System.gc();
        }
    }




    protected void saveImage(String path, int height, int width, Double lat, Double lng){

    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (category == "PROFILE") {
                Intent intent = new Intent(this, CameraLocationActivity.class);
                intent.putExtra(CameraActivity.PHOTO_PATH, photoPath);
                intent.putExtra(CameraActivity.CATEGORY, category);
                startActivity(intent);
            } else if (category == "KTP"){
                Intent intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.PHOTO_PATH, photoPath);
                intent.putExtra(CameraActivity.CATEGORY, category);
                startActivity(intent);
            }
        } else if (requestCode == REQUEST_IMAGE_RECAPTURE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, CameraActivity.class);
            intent.putExtra(CameraActivity.PHOTO_PATH, photoPath);
            intent.putExtra(CameraActivity.PHOTO_ID, photoId);
            startActivity(intent);
        } else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            photoPathGallery = getRealPathFromURI(this, uri);
            try {
                File photoFile = createImageFile();
                photoPath = photoFile.getAbsolutePath();
                copyFile(photoPathGallery,photoFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, CameraActivity.class);
            intent.putExtra(CameraActivity.PHOTO_PATH, photoPath);
            intent.putExtra(CameraActivity.CATEGORY, this.category);
            startActivity(intent);
        } else if (requestCode == RESELECT_PICTURE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            photoPathGallery = getRealPathFromURI(this, uri);
            try {
                File photoFile = createImageFile();
                photoPath = photoFile.getAbsolutePath();
                copyFile(photoPathGallery,photoFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, CameraActivity.class);
            intent.putExtra(CameraActivity.PHOTO_PATH, photoPath);
            intent.putExtra(CameraActivity.PHOTO_ID, photoId);
            startActivity(intent);
        }
    }*/


    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromPath(String path, int sampleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = sampleSize;

        options.inJustDecodeBounds = false;
        Bitmap  bitmap = BitmapFactory.decodeFile(path, options);
        try{
            bitmap  = BaseCamera.modifyOrientation(bitmap,path);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".JPEG";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/LOSSAPP/"+ BuildConfig.FLAVOR);
        if (!storageDir.exists())
            storageDir.mkdirs();

        return new File(storageDir, imageFileName);
        /*return File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpeg",         *//* suffix *//*
                storageDir      *//* directory *//*
        );*/
    }

    public static void resizePhoto(String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 4;

            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
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

    public static Bitmap createGrayscale(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmOut);
        ColorMatrix ma = new ColorMatrix();
        ma.setSaturation(0);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(ma));
        canvas.drawBitmap(src, 0, 0, paint);
        return bmOut;
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

    public static void createImageTarget(Bitmap bitmap, String filename) {
        try {
            File storageDir = new File(Environment.getExternalStorageDirectory()+"/LOSSAPP/"+ BuildConfig.FLAVOR);
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
    public static void createImageTargetNoMedia(Bitmap bitmap, String filename) {
        try {
            File storageDir = new File(Environment.getExternalStorageDirectory()+"/LOSSAPP/"+ BuildConfig.FLAVOR);
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



    public static String getDefaultImagePath(String filename) {
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/SMMF/"+ BuildConfig.FLAVOR);
        if (!storageDir.exists())
            storageDir.mkdirs();
        File file = new File(storageDir, filename);

        return file.getAbsolutePath();
    }
    public static String getDefaultImagePathNoMedia(String filename) {
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/SMMF/"+ BuildConfig.FLAVOR+"/.nomedia");
        if (!storageDir.exists())
            storageDir.mkdirs();
        File file = new File(storageDir, filename);

        return file.getAbsolutePath();
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

    public static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/ Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/ Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static void sizeDirNoMedia(){
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/SMMF/"+ BuildConfig.FLAVOR+"/.nomedia");
    }
    public static void sizeDir(){
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/SMMF/"+ BuildConfig.FLAVOR);
    }


    public boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public boolean hasCameraFront(){
        int numCameras= Camera.getNumberOfCameras();
        for(int i=0;i<numCameras;i++){
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if(CAMERA_FACING_FRONT == info.facing){
                return true;
            }
        }
        return false;
    }


    public static Bitmap bitmapResizer(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }

    public static void setScaleImage(String path) {

        File imgFile = new File(path);
        if (imgFile.exists()) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 4;
            Bitmap photoBm = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);

            int bmOriginalWidth = photoBm.getWidth();
            int bmOriginalHeight = photoBm.getHeight();
            double originalWidthToHeightRatio = 1.0 * bmOriginalWidth / bmOriginalHeight;
            double originalHeightToWidthRatio = 1.0 * bmOriginalHeight / bmOriginalWidth;

            int maxHeight = 1440;
            int maxWidth = 2560;



            photoBm = getScaledBitmap(photoBm, bmOriginalWidth, bmOriginalHeight,
                    originalWidthToHeightRatio, originalHeightToWidthRatio,
                    maxHeight, maxWidth);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            photoBm.compress(Bitmap.CompressFormat.JPEG,100, bos);
            byte[] bitmapData = bos.toByteArray();

            try {
                FileOutputStream fos = new FileOutputStream(imgFile);
                fos.write(bitmapData);

                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            photoBm.recycle();
            System.gc();

        }
    }
    private static Bitmap getScaledBitmap(Bitmap bm, int bmOriginalWidth, int bmOriginalHeight, double originalWidthToHeightRatio, double originalHeightToWidthRatio, int maxHeight, int maxWidth) {
        if(bmOriginalWidth > maxWidth || bmOriginalHeight > maxHeight) {

            if(bmOriginalWidth > bmOriginalHeight) {
                bm = scaleDeminsFromWidth(bm, maxWidth, bmOriginalHeight, originalHeightToWidthRatio);
            } else if (bmOriginalHeight > bmOriginalWidth){
                bm = scaleDeminsFromHeight(bm, maxHeight, bmOriginalHeight, originalWidthToHeightRatio);
            }

        }
        return bm;
    }
    private static Bitmap scaleDeminsFromHeight(Bitmap bm, int maxHeight, int bmOriginalHeight, double originalWidthToHeightRatio) {
        int newHeight = (int) Math.max(maxHeight, bmOriginalHeight * .55);
        int newWidth = (int) (newHeight * originalWidthToHeightRatio);
        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
        return bm;
    }

    private static Bitmap scaleDeminsFromWidth(Bitmap bm, int maxWidth, int bmOriginalWidth, double originalHeightToWidthRatio) {
        //scale the width
        int newWidth = (int) Math.max(maxWidth, bmOriginalWidth * .75);
        int newHeight = (int) (newWidth * originalHeightToWidthRatio);
        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
        return bm;
    }

    protected static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws IOException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth
                , height_tmp = o.outHeight;
        int scale = 1;

        while(true) {
            if(width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap img = BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
        img = rotateImageIfRequired(img, uri);
        return img ;
    }

    public static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    public static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(img, selectedImage);
        return img;
    }

    protected static int calculateInSampleSize(BitmapFactory.Options options,
                                               int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            final float totalPixels = width * height;
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    public static String encodeToBase64(String path) {
        File imageFile = new File(path);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bm = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP);
    }

    public static String simpleBase64Fix(String path)
    {
        File imageFile = new File(path);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        String base64 = "";
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if(imageFile.length()/1024  > 180){
                bitmap = scaleDown(bitmap,
                        800, false);
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
            }else {
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
            }
            byte[] imageBytes = baos.toByteArray();
            base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }




    public static String simpleBase64(String path,Context context)
    {

        File file = new File(path);
        Uri mImageUri = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mImageUri = FileProvider.getUriForFile(context,
                    context.getString(R.string.packageProvider),file);
        }else {
            mImageUri = Uri.fromFile(file);
        }
        context.getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = context.getContentResolver();
        Bitmap bitmap;
        String base64 = "";
        try
        {
            bitmap = MediaStore.Images.Media.getBitmap(cr, mImageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if(file.length()/1024  > 195){
                bitmap = scaleDown(rotateImageIfRequired(bitmap, context.getApplicationContext(), mImageUri),
                        800, false);
                bitmap.compress(Bitmap.CompressFormat.JPEG,90,baos);
            }else {
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
            }
            byte[] imageBytes = baos.toByteArray();
            base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 12;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap rotateImageIfRequired(Bitmap img, Context context, Uri selectedImage) throws IOException {
        if (selectedImage.getScheme().equals("content")) {
            String[] projection = { MediaStore.Images.ImageColumns.ORIENTATION };
            Cursor c = context.getContentResolver().query(selectedImage, projection, null, null, null);
            if (c.moveToFirst()) {
                final int rotation = c.getInt(0);
                c.close();
                return rotateImage(img, rotation);
            }
            return img;
        } else {
            ExifInterface ei = new ExifInterface(selectedImage.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 270);
                default:
                    return img;
            }
        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());

        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static  Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }



}
