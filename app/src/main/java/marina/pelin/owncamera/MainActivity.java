package marina.pelin.owncamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class MainActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;
    //new
   // Preview preview;
  //  String mCurrentPhotoPath = "/storage/sdcard0/Pictures/JPEG_20150720_212749_-232171051.jpg";
    //end new

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        //open the camera
        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);

        //new

        //end new


    }


    Camera.PictureCallback mPictureCallBack = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File picture_file = getOutputMediaFile();
            if (picture_file == null) {
                return;
            } else {
                try {
                    FileOutputStream fos = new FileOutputStream(picture_file);
                    fos.write(data);
                    fos.close();

                    camera.stopPreview();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
    };

    private File getOutputMediaFile() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        } else {
            File folder_gui = new File(Environment.getExternalStorageDirectory() + File.separator + "OwnCameraGallery");

            if (!folder_gui.exists()) {
                folder_gui.mkdirs();
            }
//u need to get current timestamp , then save the image with this timestamp. so in this way, everytime, the picture name will be different so the pictures will be saved differently.

            //new

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            File outputFile = new File(folder_gui, timestamp + ".jpg");
//new new new


//end new new new
            return outputFile;

            //end new
            /*
           //old version
            File outputFile = new File(folder_gui, "temp.jpg");
            return outputFile;

            //end old

             */
        }
    }

    //new new
  /*  public void onAutoFocus(boolean focussed,Camera camera)
    {
        if(focussed)
            Log.d(TAG, "Sharp");
        else
            Log.d(TAG, "Not Sharp");
    }
*/
    //end new new
    public void captureImage(View v) {
        //old if(camera!=null) {
        //                camera.takePicture(null, null, mPictureCallBack);
        //            }
        //new new new new


        if (camera != null) {

            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                    camera.takePicture(null, null, mPictureCallBack);

                }

            });
/*
        else
        {
        camera.takePicture(null,null,mPictureCallBack);
        }
*/
        }
    }

//new new
public  Bitmap RotateBitmap(Bitmap source, float angle)
{
    Matrix matrix = new Matrix();
    matrix.postRotate(angle);
    return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
}
 /*   private void saveFullImage() {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

      try {



        mOutputFileUri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", getOutputMediaFile());
    }
      catch (Exception e)
    {

    }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
  //  startActivityForResult(intent, TAKE_PICTURE);
        camera.autoFocus(new Camera.AutoFocusCallback() {
                             @Override
                             public void onAutoFocus(boolean success, Camera camera) {
                                 camera.takePicture(null, null,null, mPictureCallBack);
                             }

}
*/
/*    private void saveFullImage() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {



            mOutputFileUri = FileProvider.getUriForFile(FullscreenActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
        }
        catch (Exception e)
        {

        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
        startActivityForResult(intent, TAKE_PICTURE);


    }
*/
}
//end new new
/*public void takePhoto(File photoFile, String workerName, int width, int height, int quality)
{
    if (getAutoFocusStatus()){ camera.autoFocus(new Camera.AutoFocusCallback()
    {
        @Override public void onAutoFocus(boolean success, Camera camera)
        {
            camera.takePicture(shutterCallback, rawCallback, jpegCallback);
        }
    });
    }else{
        camera.takePicture(shutterCallback, rawCallback, jpegCallback); }
}
 */   //end new new
//new

    //The reason the image did not show is when you create the file with getExternalFilesDir(), it stores the image in private directory,
// the system's default media scanner can't scan it and has no access to the directory private to our app.
// You need enable the system scanner to scan the image, to achieve that you need to add image into media provider database by simply using this method
    /*
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

     */
/*
private void addPicToGallery() {

    File f = new File(mCurrentPhotoPath);


    Uri contentUri = Uri.fromFile(f);

    Intent mediaScanIntent = new    Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    mediaScanIntent.setData(contentUri);
    sendBroadcast(mediaScanIntent);

}
    private void setFullImageFromFilePath() {

        Log.i(MYTAG, "Entered setFullImageFromFilePath method");

        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        Log.i(MYTAG,"var targetW in setFullImageFromFilePath is:"  +targetW);
        Log.i(MYTAG,"the targetH in setFullImageFromFilePath is:" + targetH);


        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        Log.i(MYTAG, "the mCurrentPhotoPath in setFullImageFromFilePath is:" + mCurrentPhotoPath);


        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW= bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        //Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        //Decode the image file into a Bitmap sized to fill the view
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap) ;

    }
//end new

 */
