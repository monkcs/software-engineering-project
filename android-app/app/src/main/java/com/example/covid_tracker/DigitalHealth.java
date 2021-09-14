package com.example.covid_tracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class DigitalHealth extends AppCompatActivity implements View.OnClickListener {

    private ImageView qrView;
    private Button goback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_health);

        qrView = findViewById(R.id.imageviewqr);

        goback = (Button) findViewById(R.id.goback);
        goback.setOnClickListener(this);


        qrGenerator("LUKAS IS BEST!");

    }

    public void goBack(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }



    private void qrGenerator(String code){

        MultiFormatWriter writer = new MultiFormatWriter();

        try{

            BitMatrix matrix = writer.encode(code, BarcodeFormat.QR_CODE, 350, 350);

            BarcodeEncoder encoder = new BarcodeEncoder();

            Bitmap bitmap = encoder.createBitmap(matrix);

            qrView.setImageBitmap(bitmap);


        }catch (WriterException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.goback:
                System.out.println("button has been pressed");
                goBack();
                break;

        }
    }
}