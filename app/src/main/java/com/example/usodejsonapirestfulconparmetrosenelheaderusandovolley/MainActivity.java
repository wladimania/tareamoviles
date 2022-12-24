package com.example.usodejsonapirestfulconparmetrosenelheaderusandovolley;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.usodejsonapirestfulconparmetrosenelheaderusandovolley.adaptadores.AdaptadorCliente;
import com.example.usodejsonapirestfulconparmetrosenelheaderusandovolley.modelos.ModeloCliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView lstClientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstClientes = (ListView) findViewById(R.id.lstUser);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/clientes/search";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject JSONlista =  new JSONObject(response);
                            JSONArray JSONlistaUsuarios= JSONlista.getJSONArray("clientes");
                            ArrayList<ModeloCliente> lstProductoss = ModeloCliente.JsonObjectsBuild(JSONlistaUsuarios);
                            AdaptadorCliente adapatorUsuario = new AdaptadorCliente( getApplicationContext(), lstProductoss);
                            lstClientes.setAdapter(adapatorUsuario);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),  error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZHVzciI6OSwiZW1haWwiOiJjemFtYnJhbm9AdXRlcS5lZHUuZWMiLCJpYXQiOjE2NzE0OTYzOTEsImV4cCI6MTY3MTg1NjM5MX0.hZooTHB43sV3tdjL2unBAk7xIdClGRg3ltWUFTEfzyc");
                return headerMap;
            }
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("fuente", "1");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}