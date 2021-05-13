package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class phto extends AppCompatActivity implements View.OnClickListener{

    SurfaceView surfaceview;
    SurfaceHolder surfaceHolder;

    File fileshowname;

    CameraDevice cameradevice;
    String cameraId = "0";
    Size previewSize;
    CaptureRequest.Builder captureRequestBuilder;
    CameraCaptureSession cameraSession;

    ImageReader imgReader;

    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    private int rotion;

    private AutoFitTextureView autoFitTextureView;

    private int screen_oritation;

    public int ch_f = -1;

    private FloatingActionButton fbtn,button;

    //Zooming
    protected float fingerSpacing = 0;
    protected float zoomLevel = 10f;
    protected float maximumZoomLevel;
    protected Rect zoom;

    OrientationEventListener mScreenOrientationEventListener;

    private int[] frame_order = {R.drawable.src_01,R.drawable.asset1,R.drawable.asset2};
    private int[] time_series = {0,0,0};
    private int time_order=0;

    private TextView zoom_num;
    private ImageButton nxtbtn;
    private Bitmap nor_01,nor_02,lie_02_90,lie_02_270,lie_01_90,lie_01_270;
    private Bitmap picuse_01,picuse_02,picuse_lie_01,picuse_lie_02;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phto);

        ArrayList<String> permissions = new ArrayList<>();
        if (this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissions.size() > 0) requestPermissions(permissions.toArray(new String[0]), 99);
        zoom_num = findViewById(R.id.zoom_number);

        surfaceview = findViewById(R.id.surfaceview);
        surfaceview.setZOrderOnTop(true);//处于顶层
        surfaceview.getHolder().setFormat(PixelFormat.TRANSPARENT);//设置surface为透明
        surfaceHolder = surfaceview.getHolder();

        autoFitTextureView = findViewById(R.id.autoFitTextureView);
        autoFitTextureView.setSurfaceTextureListener(surfaceTextureListener);

        FloatingActionButton btn = findViewById(R.id.main_pic);
        btn.setOnClickListener(this);

        fbtn = findViewById(R.id.fbtn);
        fbtn.setOnClickListener(this);

        button = findViewById(R.id.opbtn);
        button.setOnClickListener(this);

        nxtbtn = findViewById(R.id.imageButton19);
        nxtbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ph_share();
            }
        });
    }
    public void ph_share(){
        Intent intent = new Intent(this, photo_share.class);
        startActivity(intent);
    };

    AutoFitTextureView.SurfaceTextureListener surfaceTextureListener = new AutoFitTextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera(width,height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            //configureTransform(width,height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openCamera(int width,int height) {

        mScreenOrientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                screen_oritation = orientation;
                zoom_num.setRotation(360-getJudge_dim());
                fbtn.setRotation(360-getJudge_dim());
                button.setRotation(360-getJudge_dim());

                time_series[time_order] = getJudge_dim();
                time_order++;
                if(time_order==3)
                    time_order = 0;
                if(andgle_compare()!=true && ch_f !=-1){
                    new Processing().execute();
                    updatePreview();
                }

            }
        };

        mScreenOrientationEventListener.enable();
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if(cameraId == ""){
                cameraId = cameraManager.getCameraIdList()[0];
            }

            CameraCharacteristics camchr = cameraManager.getCameraCharacteristics(cameraId);
            Size[] jpegsize;

            jpegsize = camchr.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
            Size largest = Collections.max(Arrays.asList(jpegsize), new CompareSizeByArea());

            int picwidth = 640;
            int picheight = 480;

            if(largest != null ){

                if(largest.getWidth()>=1920)
                    picwidth = 1920;
                if(largest.getHeight()>=1080)
                    picheight = 1080;

                picwidth = largest.getWidth();
                picheight = largest.getHeight();
                Log.d("TAG","largest:" + picwidth + picheight);

            }

            imgReader = ImageReader.newInstance(picwidth,picheight,ImageFormat.JPEG,1);
            imgReader.setOnImageAvailableListener(imageReaderOnImageAvailable,mBackgroundHandler);

            CameraCharacteristics cc = cameraManager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            Point displaySize = new Point();
            getWindowManager().getDefaultDisplay().getSize(displaySize);
            int maxPreviewWidth = displaySize.y;
            int maxPreviewHeight = displaySize.x;

            if (maxPreviewWidth > 1920) {
                maxPreviewWidth = 1920;
            }

            if (maxPreviewHeight > 1080) {
                maxPreviewHeight = 1080;
            }

            //previewSize = getOptimalSize(map.getOutputSizes(SurfaceTexture.class),width,height);
            previewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),height,width,maxPreviewWidth,maxPreviewHeight,Collections.max(
                    Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                    new CompareSizeByArea()));

            Log.d("TAG","preview size" + previewSize.getWidth() + " " + previewSize.getHeight() + " "  + displaySize.x  +" " + displaySize.y);

            autoFitTextureView.setAspectRatio(previewSize.getHeight(), previewSize.getWidth());
            //configureTransform(width,height);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            cameraManager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            cameradevice = cameraDevice;
            startCameraPre();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameradevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            cameradevice.close();
            cameradevice = null;
        }
    };

    //google original
    private static Size chooseOptimalSize(Size[] choices, int textureViewWidth,
                                          int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {

        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<>();
        // Collect the supported resolutions that are smaller than the preview Surface
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();

        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
                    option.getHeight() == option.getWidth() * h / w) {
                if (option.getWidth() >= textureViewWidth &&
                        option.getHeight() >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }

        // Pick the smallest of those big enough. If there is no one big enough, pick the
        // largest of those not big enough.
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizeByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new CompareSizeByArea());
        } else {
            //Log.e(ACTIVITY_TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    public static class CompareSizeByArea implements Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }

    private void startCameraPre() {

        SurfaceTexture texture = autoFitTextureView.getSurfaceTexture();
        if(cameraId.equals("1")){
            texture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
        }
        else {
            texture.setDefaultBufferSize(previewSize.getHeight(), previewSize.getWidth());
        }
        Surface surface = new Surface(texture);

        try {
            new Processing().execute();
            captureRequestBuilder = cameradevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            //Zoom
            if (zoom != null) {
                captureRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, zoom);
            }
            captureRequestBuilder.addTarget(surface);

            cameradevice.createCaptureSession(Arrays.asList(surface,imgReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if(cameradevice == null){
                        return;
                    }
                    cameraSession = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                }
            },mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void updatePreview() {
        if(cameradevice == null){
            return;
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraSession.setRepeatingRequest(captureRequestBuilder.build(),null,mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //textureview.setSurfaceTextureListener(surfaceTextureListener);
        startBackgroundThread();
        if (autoFitTextureView.isAvailable()) {
            openCamera(autoFitTextureView.getWidth(),autoFitTextureView.getHeight());
        } else {
            autoFitTextureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }


    @Override
    protected void onPause() {
        closecamera();
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_pic:
                takepic();
                break;
            case R.id.fbtn:
                //findViewById(R.id.horizontalScrollView).setVisibility(View.VISIBLE);
                //showLayoutDialog();
                ch_f++;
                if(ch_f == 2)
                    ch_f = -1;
                fbtn.setImageResource(frame_order[ch_f+1]);
                //startCameraPre();
                new Processing().execute();
                updatePreview();
                break;
            case R.id.opbtn:
                switchcamera();
                break;
        }

    }

    private void switchcamera() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String id;
        try {
            id = cameraManager.getCameraIdList()[0];
            if(cameraId != id){
                id = cameraId;
            }
            if (id.equals("0")) {   // If currently on FRONT camera (0 = CameraCharacteristics.LENS_FACING_FRONT)
                id = "1";           // switch to BACK camera (1 = CameraCharacteristics.LENS_FACING_BACK)
            } else {  // If currently on BACK camera
                id = "0"; // switch to front camera
            }
            cameraId = id;
            closecamera();
            reopenCamera();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    protected void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void closecamera() {
        if (null != cameraSession) {
            cameraSession.close();
            cameraSession = null;
        }
        if (null != cameradevice) {
            cameradevice.close();
            cameradevice = null;
        }
        if (null != imgReader) {
            imgReader.close();
            imgReader = null;
        }
    }

    private void reopenCamera() {
        openCamera(autoFitTextureView.getWidth(),autoFitTextureView.getHeight());
    }

    ImageReader.OnImageAvailableListener imageReaderOnImageAvailable = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String date = sDateFormat.format(new java.util.Date());
            String filename = date + "pic.jpg";
            //final File file = new File(filedir,filename);
            File filedir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),"Frame");
            if(!filedir.exists()){
                filedir.mkdir();
            }
            final File file = new File(filedir,filename);
            fileshowname = file;
            //獲取內部儲存狀態jpg
            String state = Environment.getExternalStorageState();
            //如果狀態不是mounted，無法讀寫
            if (!state.equals(Environment.MEDIA_MOUNTED)) {
                //Log.d(ACTIVITY_TAG, "SAVE_ERROR!");
                Toast.makeText(phto.this,"ExternalStorage is not ready!!",Toast.LENGTH_SHORT);
                return;
            }
            mBackgroundHandler.post(new ImageSaver(reader.acquireNextImage(), file));

        }
    };

    private void takepic() {

        if(cameradevice == null ){
            Toast.makeText(this,"camera錯誤",Toast.LENGTH_LONG).show();
            return;
        }
        CameraManager cammgr = (CameraManager)getSystemService(CAMERA_SERVICE);
        try {
            CameraCharacteristics camchr = cammgr.getCameraCharacteristics(cameradevice.getId());

            if(camchr != null){
                int mSensorOrientation = camchr.get(CameraCharacteristics.SENSOR_ORIENTATION);
                Log.d("TAG","mSensorOrientation" + mSensorOrientation);

                int roff = getJudge_dim();

                if(cameraId.equals("0")) {
                    rotion = (roff + mSensorOrientation) % 360;
                    Log.d("TAG","ZERO");
                }
                else
                    rotion = (360 - roff + mSensorOrientation) % 360;

                String s = String.valueOf(rotion);
                Log.d("TAG","PIC_MUST_ROTAE:------->" + s);

                final CaptureRequest.Builder capturebuilder = cameradevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                capturebuilder.addTarget(imgReader.getSurface());
                //capturebuilder.set(CaptureRequest.CONTROL_MODE,CameraMetadata.CONTROL_MODE_AUTO);
                capturebuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                capturebuilder.set(CaptureRequest.JPEG_ORIENTATION,rotion);
                //Zoom
                if (zoom != null) {
                    capturebuilder.set(CaptureRequest.SCALER_CROP_REGION, zoom);
                }

                final CameraCaptureSession.CaptureCallback camCaptureCallback = new CameraCaptureSession.CaptureCallback(){

                    @Override
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                        super.onCaptureCompleted(session, request, result);
                        //Toast.makeText(MainActivity.this,"拍照完成\n影像檔:"+ fileshowname,Toast.LENGTH_SHORT).show();
                        startCameraPre();
                    }

                    @Override
                    public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult result){
                    }
                };
                cameraSession.stopRepeating();
                cameraSession.capture(capturebuilder.build(),camCaptureCallback,null);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private int getJudge_dim(){
        int roft = screen_oritation/45;
        if(roft == 0 || roft == 7 || roft == 8) {
            roft = 0;
        }
        else if(roft == 1 || roft == 2) {
            roft = 90;
        }
        else if(roft == 3 || roft == 4) {
            roft = 180;
        }
        else {
            roft = 270;
        }

        return roft;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        try {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
            maximumZoomLevel = cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)*10;
            Rect rect = cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            if (rect == null)
                return false;
            float currentFingerSpacing;

            if (event.getPointerCount() == 2) { //Multi touch.
                currentFingerSpacing = getFingerSpacing(event);
                float delta = 1f; //Control this value to control the zooming sensibility
                if (fingerSpacing != 0) {
                    if (currentFingerSpacing > fingerSpacing) { //Don't over zoom-in(放大)
                        /*if ((maximumZoomLevel - zoomLevel) <= delta) {
                            delta = maximumZoomLevel - zoomLevel;
                        }*/
                        zoomLevel = zoomLevel + delta;
                        if(zoomLevel >= maximumZoomLevel){
                            zoomLevel = maximumZoomLevel;
                        }
                    } else if (currentFingerSpacing < fingerSpacing){ //Don't over zoom-out(縮小)
                        /*if ((zoomLevel - delta) < 1f) {
                            delta = zoomLevel - 1f;
                        }*/
                        zoomLevel = zoomLevel - delta;
                        if(zoomLevel <= 10f){
                            zoomLevel = 10f;
                        }
                    }
                    zoom_num.setText(String.format("%.1f",zoomLevel/10f));
                    float ratio = (float) 10 / zoomLevel; //This ratio is the ratio of cropped Rect to Camera's original(Maximum) Rect
                    //croppedWidth and croppedHeight are the pixels cropped away, not pixels after cropped
                    int croppedWidth = rect.width() - Math.round((float)rect.width() * ratio);
                    int croppedHeight = rect.height() - Math.round((float)rect.height() * ratio);
                    //Finally, zoom represents the zoomed visible area
                    zoom = new Rect(croppedWidth/2, croppedHeight/2,
                            rect.width() - croppedWidth/2, rect.height() - croppedHeight/2);
                    captureRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, zoom);
                }
                fingerSpacing = currentFingerSpacing;
            } else { //Single touch point, needs to return true in order to detect one more touch point
                return true;
            }
            cameraSession.setRepeatingRequest(captureRequestBuilder.build(), null, null);

            return true;
        } catch (final Exception e) {
            //Error handling up to you
            return true;
        }
    }

    private float getFingerSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    //保存图片
    public void scanFile(File file){
        String mimeType = getMimeType(file);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            String fileName = file.getName();
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME,fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if(uri == null){
                Toast.makeText(phto.this,"圖片保存失敗",Toast.LENGTH_SHORT).show();
                Log.d("tag","pic save fail!");
                return;
            }
            try {
                OutputStream out = contentResolver.openOutputStream(uri);
                FileInputStream fis = new FileInputStream(file);
                FileUtils.copy(fis,out);
                fis.close();
                out.close();
                Toast.makeText(phto.this,"圖片保存成功\n" + file.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                Log.d("tag","pic save success!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //first try
            /*MediaScannerConnection.scanFile(MainActivity.this,
                    new String[]{file.getAbsolutePath()},
                    null,null);*/

            //second try
            /*Uri uri = Uri.fromFile(file);
            MainActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));*/

            //third try
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(uri);
            sendBroadcast(intent);

            Toast.makeText(phto.this,"圖片保存到成功\n"/* + file.getAbsolutePath()*/ ,Toast.LENGTH_SHORT).show();
            Log.d("tag","pic save finally!!!");
        }
    }


    public static String getMimeType(File file){
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(file.getName());
        return type;
    }

    private boolean andgle_compare(){
        if(time_series[1] - time_series[0] ==time_series[2] - time_series[1])
            return true;
        else
            return false;
    }

    private class ImageSaver implements Runnable {

        /**
         * The JPEG image
         */
        private final Image mImage;
        /**
         * The file we save the image into.
         */
        private final File mFile;

        private Bitmap final_bitmap,frame;

        ImageSaver(Image image, File file) {
            mImage = image;
            mFile = file;
        }

        @Override
        public void run() {
            ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            Bitmap rawbitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length).copy(Bitmap.Config.ARGB_8888,true);

            Matrix m = new Matrix();
            m.setRotate(rotion);
            rawbitmap = Bitmap.createBitmap(rawbitmap, 0, 0, rawbitmap.getWidth(), rawbitmap.getHeight(), m, true);

            if ("1".equals(cameraId)){
                Matrix matrix = new Matrix();
                matrix.setScale(-1.0f, 1.0f); // 镜像水平翻转
                rawbitmap = Bitmap.createBitmap(rawbitmap, 0, 0, rawbitmap.getWidth(), rawbitmap.getHeight(), matrix, true);
            }

            final_bitmap = rawbitmap;

            if(ch_f!=-1) {
                if (ch_f == 0) {
                    if (getJudge_dim() == 90 || getJudge_dim() == 270) {
                        //frame = BitmapFactory.decodeResource(getResources(), R.drawable.lie_01);
                        if(picuse_lie_01!=null)
                            frame = picuse_lie_01;
                        else{
                            frame = inSampleSize(R.drawable.lie_01, final_bitmap.getWidth(), final_bitmap.getHeight());
                            picuse_lie_01 = frame;
                        }
                    } else {
                        //frame = BitmapFactory.decodeResource(getResources(), R.drawable.frame01);
                        if(picuse_01!=null)
                            frame = picuse_01;
                        else{
                            frame = inSampleSize(R.drawable.frame01, final_bitmap.getWidth(), final_bitmap.getHeight());
                            picuse_01 = frame;
                        }
                    }
                } else {
                    if (getJudge_dim() == 90 || getJudge_dim() == 270) {
                        //frame = BitmapFactory.decodeResource(getResources(), R.drawable.lie_02);
                        if(picuse_lie_02!=null)
                            frame = picuse_lie_02;
                        else{
                            frame = inSampleSize(R.drawable.lie_02, final_bitmap.getWidth(), final_bitmap.getHeight());
                            picuse_lie_02 = frame;
                        }
                    } else {
                        //frame = BitmapFactory.decodeResource(getResources(), R.drawable.frame02);
                        if(picuse_02!=null)
                            frame = picuse_02;
                        else{
                            frame = inSampleSize(R.drawable.frame02, final_bitmap.getWidth(), final_bitmap.getHeight());
                            picuse_02 = frame;
                        }
                    }
                }

                Log.d("tag","final_bitmap"+final_bitmap.getWidth()+ " " +final_bitmap.getHeight());
                Log.d("tag","frame_attributes"+frame.getWidth()+" "+frame.getHeight());

                Bitmap drawBitmap = Bitmap.createBitmap(rawbitmap, 0, 0, rawbitmap.getWidth(), rawbitmap.getHeight());
                Canvas cv = new Canvas(drawBitmap);
                Paint pt = new Paint();
                cv.drawBitmap(drawBitmap, 0, 0, pt);
                //pt.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.LIGHTEN));
                //對邊框進行縮放
                int w = frame.getWidth();
                int h = frame.getHeight();
                //縮放比 如果圖片尺寸超過邊框尺寸 會自動匹配
                float scaleX = drawBitmap.getWidth() * 1F / w;
                float scaleY = drawBitmap.getHeight() * 1F / h;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleX, scaleY);   //縮放圖片
                Bitmap copyBitmap = Bitmap.createBitmap(frame, 0, 0, w, h, matrix, true);
                cv.drawBitmap(copyBitmap, 0, 0, pt);
                final_bitmap = drawBitmap;
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(final_bitmap.getByteCount());
            final_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            FileOutputStream output = null;
            try {
                output = new FileOutputStream(mFile);
                output.write(outputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mImage.close();
                if (null != output) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                scanFile(mFile);
            }
        }

    }


    private class Processing extends AsyncTask<String,Integer,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {

            Canvas canvas;
            canvas =  surfaceHolder.lockCanvas();
            if(canvas!=null)
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); //清楚掉上一次的画框。

            Log.d("tag","memory out_1");

            if(ch_f != -1) {
                Bitmap bmp;
                if(ch_f == 0) {
                    Log.d("tag","memory out_2");
                    if(getJudge_dim()==90 || getJudge_dim()==270) {
                        if(getJudge_dim() == 90) {
                            //bmp = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.lie01_270);
                            //bmp = inSampleSize(R.drawable.lie01_270,previewSize.getHeight(),previewSize.getWidth());
                            if(lie_01_270 != null)
                                bmp = lie_01_270;
                            else {
                                bmp = inSampleSize(R.drawable.lie01_270, previewSize.getHeight(), previewSize.getWidth());
                                lie_01_270 = bmp;
                            }
                            //bmp = lie_01_90;
                        }
                        else {
                            //bmp = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.lie01_90);
                            if(lie_01_90!=null)
                                bmp = lie_01_90;
                            else {
                                bmp = inSampleSize(R.drawable.lie01_90, previewSize.getHeight(), previewSize.getWidth());
                                lie_01_90 = bmp;
                            }
                            //bmp = lie_01_270;
                        }
                    }
                    else {
                        //bmp = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.frame01);
                        if(nor_01 != null)
                            bmp = nor_01;
                        else {
                            bmp = inSampleSize(R.drawable.frame01, previewSize.getHeight(), previewSize.getWidth());
                            nor_01 = bmp;
                        }
                    }
                    Log.d("tag","memory out_3");
                }
                else {
                    if(getJudge_dim()==90||getJudge_dim()==270) {
                        if(getJudge_dim() == 90) {
                            //bmp = inSampleSize(R.drawable.lie02_270,previewSize.getHeight(),previewSize.getWidth());
                            if (lie_02_270 != null)
                                bmp = lie_02_270;
                            else {
                                bmp = inSampleSize(R.drawable.lie02_270, previewSize.getHeight(), previewSize.getWidth());
                                lie_02_270 = bmp;
                            }
                        }
                        else {
                            //bmp = inSampleSize(R.drawable.lie02_90, previewSize.getHeight(), previewSize.getWidth());
                            if(lie_02_90!=null)
                                bmp = lie_02_90;
                            else{
                                bmp = inSampleSize(R.drawable.lie02_90, previewSize.getHeight(), previewSize.getWidth());
                                lie_02_90 = bmp;
                            }
                        }
                    }
                    else {
                        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.frame02);
                        if(nor_02!=null)
                            bmp = nor_02;
                        else{
                            bmp = inSampleSize(R.drawable.frame02, previewSize.getHeight(), previewSize.getWidth());
                            nor_02 = bmp;
                        }

                    }
                }
                int width = autoFitTextureView.getWidth();
                int height = autoFitTextureView.getHeight();

                int w = bmp.getWidth();
                int h = bmp.getHeight();
                //縮放比 如果圖片尺寸超過邊框尺寸 會自動匹配
                float scaleX = width * 1F / w;
                float scaleY = height * 1F / h;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleX, scaleY);   //縮放圖片
                Bitmap copyBitmap = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);
                canvas.drawBitmap(copyBitmap, 0, 0, new Paint());

                Log.d("tag","memory out_4");
            }
            surfaceHolder.unlockCanvasAndPost(canvas);

            return null;
        }

    }



    public Bitmap inSampleSize(int drawable,int reqWidth,int reqHeight){

        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        //BitmapFactory.decodeByteArray(data, 0, data.length, options);
        BitmapFactory.decodeResource(getResources(),drawable,options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        //return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return BitmapFactory.decodeResource(getResources(),drawable,options);

    }

    public static int calculateInSampleSize(BitmapFactory.Options options,

                                            int reqWidth, int reqHeight) {

        final int pictureheight = options.outHeight;

        final int picturewidth = options.outWidth;

        int targetheight = pictureheight;

        int targetwidth = picturewidth;

        int inSampleSize = 1;

        if (targetheight > reqHeight || targetwidth > reqWidth) {

            while (targetheight >= reqHeight

                    && targetwidth >= reqWidth) {

                inSampleSize += 1;

                targetheight = pictureheight / inSampleSize;

                targetwidth = picturewidth / inSampleSize;

            }
        }
        return inSampleSize;
    }
}