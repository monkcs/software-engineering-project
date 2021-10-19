package com.example.covid_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.VibrationAttributes;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Objects;

public class CameraScannerActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scanner);

        surfaceView = (SurfaceView) findViewById(R.id.scannercammera);
        textView = (TextView) findViewById(R.id.textviewtemp);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).setAutoFocusEnabled(true).build();


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission")
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                //ask for permission to use the camera takes care in DigitalHealth
                try {
                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            //get the data
            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCode = detections.getDetectedItems();

                if (qrCode.size() != 0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibration = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibration.vibrate(1000);
                            textView.setText(qrCode.valueAt(0).displayValue);
                        }
                    });
                }
             }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}