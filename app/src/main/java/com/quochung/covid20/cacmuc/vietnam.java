package com.quochung.covid20.cacmuc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.quochung.covid20.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class vietnam extends Fragment {


    TextView tongsocasoluongvn,tongsochetsoluongvn,tongsohoiphucsoluongvn,socamoi;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_vietnam, container, false);
        khoitaoview();
        laydulieu();
        return view;

    }
    private void laydulieu() {

        RequestQueue queue1 = Volley.newRequestQueue(getActivity());

        String url="https://akashraj.tech/corona/api";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray total = jsonObject.getJSONArray("countries_stat");
                    for (int i =0; i < total.length(); i++) {
                        JSONObject world = (JSONObject) total.get(i);
                        if (world.getString("country_name").equals("Vietnam")) {
                            tongsocasoluongvn.setText(world.getString("cases"));
                            tongsochetsoluongvn.setText(world.getString("deaths"));
                            tongsohoiphucsoluongvn.setText(world.getString("total_recovered"));
                            socamoi.setText(world.getString("new_cases"));
                            break;
                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Display the first 500 characters of the response string.

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue1.add(stringRequest);

    }
    private void khoitaoview() {

        tongsocasoluongvn = view.findViewById(R.id.tongsocasoluongvn);
        tongsochetsoluongvn = view.findViewById(R.id.tongsochetsoluongvn);
        tongsohoiphucsoluongvn = view.findViewById(R.id.tongsohoiphucsoluongvn);
        socamoi = view.findViewById(R.id.socamoi);
    }
}