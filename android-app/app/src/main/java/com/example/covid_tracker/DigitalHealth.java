package com.example.covid_tracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
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
            Intent intent = new Intent(getActivity(), StatisticsVacc.class);
            startActivity(intent);
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
                        values.add(new FAQ_block(getString(R.string.Username), response.getString("firstname") +" "+ response.getString("surname")));
                        values.add(new FAQ_block(getString(R.string.Dateofbirth), response.getString("birthdate")));
                        values.add(new FAQ_block(getString(R.string.Manufacturer), response.getString("name")));
                        values.add(new FAQ_block(getString(R.string.Dosetwodate), response.getString("Date")));
                        qrGenerator(response.getString("qrcode"));
                        Log.i("gDFC", "QR: " + response.getString("qrcode"));
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