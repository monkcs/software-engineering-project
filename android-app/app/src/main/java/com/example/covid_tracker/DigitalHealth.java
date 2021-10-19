package com.example.covid_tracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DigitalHealth extends Fragment {

    private ImageView qrView;
    private RecyclerView recyclerView;
    private View view;
    private List<FAQ_block> list;
    private RequestQueue queue;

    private static final int CAMERA_PERMISSION_CODE = 100;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_digital_health, container, false);

        qrView = view.findViewById(R.id.imageviewqr);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_userinfo);
        queue = Volley.newRequestQueue(getActivity());


        list = new ArrayList<>();
        setRecyclerView();
        initUserInfo();

        qrGenerator("https://youtu.be/iik25wqIuFo");

        Button toocamera = view.findViewById(R.id.btn_camera);
        toocamera.setOnClickListener(view -> {

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

            }else{
                startActivity(new Intent(getActivity(), CameraScannerActivity.class));
            }

        });

        return view;
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

    private void setRecyclerView() {

        FAQ_block_Adapter faq_block_adapter = new FAQ_block_Adapter(list);
        recyclerView.setAdapter(faq_block_adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initUserInfo() {

        list.add(new FAQ_block(getString(R.string.Username)         , "Lukas Axelborn",""));
        list.add(new FAQ_block(getString(R.string.Dateofbirth), "2000 03 21"  ,""));
        list.add(new FAQ_block(getString(R.string.Manufacturer) , "sputnik"     ,""));
        list.add(new FAQ_block(getString(R.string.Dosetwodate), " I sommaras" ,""));

    }

    public void TOBEDOOOO(){
        StringRequest request = new StringRequest(Request.Method.GET, WebRequest.urlbase + "user/appointment/ .php",
                response -> {

                    Log.i("gDFA", "response: " + response);



                }, error -> Log.i("gDFA", "Något blev fel"))
        {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.User.username, WebRequest.User.password);
            }
        };

        queue.add(request);
    }


}

