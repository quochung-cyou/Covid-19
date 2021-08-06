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

import org.json.JSONException;
import org.json.JSONObject;

public class trangchu extends Fragment {


    TextView tongsocasoluong,tongsochetsoluong,tongsohoiphucsoluong,activecase,nghiemtrong;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_trangchu, container, false);
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
                JSONObject world = jsonObject.getJSONObject("world_total");
                tongsocasoluong.setText(world.getString("total_cases"));
                tongsochetsoluong.setText(world.getString("total_deaths"));
                tongsohoiphucsoluong.setText(world.getString("total_recovered"));
                activecase.setText(world.getString("active_cases"));
                nghiemtrong.setText(world.getString("serious_critical"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Display the first 500 characters of the response string.

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            tongsocasoluong.setText("Lỗi mạng");
            tongsochetsoluong.setText("Lỗi mạng");
            tongsohoiphucsoluong.setText("Lỗi mạng");
        }
    });

    queue1.add(stringRequest);

    }
    private void khoitaoview() {

        tongsocasoluong = view.findViewById(R.id.tongsocasoluong);
        tongsochetsoluong = view.findViewById(R.id.tongsochetsoluong);
        tongsohoiphucsoluong = view.findViewById(R.id.tongsohoiphucsoluong);
        activecase = view.findViewById(R.id.activecase);
        nghiemtrong = view.findViewById(R.id.nghiemtrong);
    }
}