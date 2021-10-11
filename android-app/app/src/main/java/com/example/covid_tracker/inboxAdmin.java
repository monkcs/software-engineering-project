package com.example.covid_tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class inboxAdmin extends Fragment {

    private View view;
    private RequestQueue queue;

    private RecyclerView rV;
    private Button boka;
    List<Admin_block> list;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inboxadmin, container, false);

        queue = Volley.newRequestQueue(getActivity());


        System.out.println("insidde box");

        rV = (RecyclerView) view.findViewById(R.id.recyclerView_inboxAdmin);



        getPendingBookings();
        setRecyclerView();


        return view;
    }
    private void setRecyclerView() {

        Admin_block_Adapter Admin_block_adapter = new Admin_block_Adapter(list);
        rV.setAdapter(Admin_block_adapter);
        rV.setHasFixedSize(true);


    }

    private void getPendingBookings() {

        list = new ArrayList<>();

            //Här hämtar du hela listan med pending bookings


            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/pending/",null,
                    response -> {

                        for (int i =0; i < response.length(); i++) {

                            try {

                                System.out.println("response namn: " + response.getJSONObject(i).getString("firstname"));

                                list.add(new Admin_block(response.getJSONObject(i).getString("firstname"),"123","123","123", response.getJSONObject(i).getInt("account")) );
                                System.out.println("Try fungerade");

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Catch fungerade");

                            }

                        }
                        setRecyclerView();

                    }, error -> {

                    System.out.println("Error, den når inte fram");

            }
            ) {

                @Override
                public Map<String, String> getHeaders() {
                    return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
                }
            };

            queue.add(request);
        }
}
