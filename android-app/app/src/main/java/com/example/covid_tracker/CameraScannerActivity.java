package com.example.covid_tracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CameraScannerActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;
    public RequestQueue queue;

    private static AlertDialog dialog;
    private static AlertDialog.Builder alertDialog;

    private static final int width = 1080;
    private static final int height = 1090;



    private static final int WAIT_TIME_FOR_PROCESSING = 3000; // 3 sek



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scanner);
        queue = Volley.newRequestQueue(this);


        surfaceView = findViewById(R.id.scannercammera);
        textView = findViewById(R.id.textviewtemp);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(width, height)
                .setAutoFocusEnabled(true)
                .build();



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
                    String stringQR = qrCode.valueAt(0).displayValue;
                    checkQrCode(stringQR);

                    textView.setText(stringQR);

                    //noinspection CatchMayIgnoreException
                    try {
                        Thread.sleep(WAIT_TIME_FOR_PROCESSING);
                        // Do some stuff
                    } catch (Exception e) {
                        e.getLocalizedMessage();
                    }

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


    private void checkQrCode(String stringQR) {
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "general/validate_passport.php",
                response -> {
                    System.out.println(response);

                    try {
                        JSONObject object = new JSONObject(response);
                        String firstname = Encryption.decryptData(object.getString("firstname"));
                        String lastname =  Encryption.decryptData(object.getString("surname"));
                        String fullName = firstname + " " + lastname;
                        String date_of_birth = object.getString("birthdate");

                        printValidation(true, fullName, date_of_birth);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }, error -> {
            printValidation(false, "", "");

            Toast.makeText(this, "error with sending qr code or receiving response", Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("qrcode", stringQR);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.User.username, WebRequest.User.password);
            }
        };
        queue.add(request);
    }

    private void printValidation(boolean responseMessage, String fullName, String date_of_birth) {
        alertDialog = new AlertDialog.Builder( CameraScannerActivity.this);

        View popupView = getLayoutInflater().inflate(R.layout.validationpopup, null);
        ImageView image = popupView.findViewById(R.id.confirmAppointment);
        TextView displayUsername = popupView.findViewById(R.id.displayusername);
        TextView displayDateBirth = popupView.findViewById(R.id.displaydateofbirth);

        if (responseMessage){
            image.setImageResource(R.drawable.greencheckmark);
            displayUsername.setText(fullName);
            displayDateBirth.setText(date_of_birth);
        }else{
            image.setImageResource(R.drawable.red_corss);
            displayUsername.setText(getString(R.string.passport_invalid));
        }

        alertDialog.setView(popupView);
        dialog = alertDialog.create();
        dialog.show();

        popupView.setOnClickListener(view -> dialog.dismiss());
    }
}