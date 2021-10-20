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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DigitalHealth extends Fragment {

    private ImageView qrView;
    private RecyclerView recyclerView;
    private View view;
    private List<FAQ_block> values;
    private RequestQueue queue;

    private static final int CAMERA_PERMISSION_CODE = 100;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_digital_health, container, false);

        qrView = view.findViewById(R.id.imageviewqr);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_userinfo);
        queue = Volley.newRequestQueue(getActivity());


        values = new ArrayList<>();

        InitData();

        //qrGenerator("https://youtu.be/iik25wqIuFo");

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

        FAQ_block_Adapter faq_block_adapter = new FAQ_block_Adapter(values);
        recyclerView.setAdapter(faq_block_adapter);
        recyclerView.setHasFixedSize(true);
    }


    public void InitData(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, WebRequest.urlbase + "user/get_passport.php", null,

 
                response -> {
                    try {
                        String date = response.getString("Date");
                        date = date.split(" ")[0];
                        values.add(new FAQ_block(getString(R.string.username), response.getString("firstname") +" "+ response.getString("surname")));
                        values.add(new FAQ_block(getString(R.string.birthdate), response.getString("birthdate")));
                        values.add(new FAQ_block(getString(R.string.manufacturer), response.getString("name")));
                        values.add(new FAQ_block(getString(R.string.time_for_second_dose), date));
                        qrGenerator(response.getString("qrcode"));            
                        setRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Log.i("gDFC", "response: " + response);


                }, error -> Log.i("gDFB", "No passport available"))
        {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.User.username, WebRequest.User.password);
            }
        };

        queue.add(request);
    }


}

