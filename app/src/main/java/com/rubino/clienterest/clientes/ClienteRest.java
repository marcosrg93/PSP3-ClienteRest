package com.rubino.clienterest.clientes;


import android.os.AsyncTask;
import android.util.Log;

import com.rubino.clienterest.interfaces.Cliente;
import com.rubino.clienterest.pojo.Actividad;
import com.rubino.clienterest.pojo.Grupo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;

/**
 * Created by marco on 15/02/2016.
 */
public class ClienteRest {


    class Tarea extends AsyncTask<String, Long, String> {


        Tarea() {

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();
            Cliente api = retrofit.create(Cliente.class);
            Call<List<Actividad>> call = api.getActividades();

            call.enqueue(new Callback<List<Actividad>>() {
                @Override
                public void onResponse(Response<List<Actividad>>

                                               response, Retrofit retrofit) {
                    for (Actividad a : response.body()) {
                        Log.v("HOLA",a.toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.getLocalizedMessage();
                }
            });
            return "";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}
