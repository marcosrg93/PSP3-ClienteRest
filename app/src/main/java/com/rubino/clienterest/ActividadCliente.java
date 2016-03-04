package com.rubino.clienterest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.rubino.clienterest.adaptador.Adaptador;
import com.rubino.clienterest.interfaces.Cliente;
import com.rubino.clienterest.pojo.Actividad;
import java.util.ArrayList;
import java.util.List;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ActividadCliente extends AppCompatActivity {

    private FloatingActionButton fab;
    private ArrayList<Actividad> datos;
    private  ListView lv;
    private Adaptador ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        loadActividades();
        init();


    }


    public void init() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add(view);
            }
        });



        lv = (ListView)findViewById(R.id.lv);

        ad = new Adaptador(this, R.layout.item, datos);

        lv.setAdapter(ad);
        registerForContextMenu(lv);

       /* lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    delete(view, position);
                return false;
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                edit(v, (Actividad) lv.getItemAtPosition(position));
            }
        });*/


    }


    //Creamos el menu contextual que nos dará las opciones de editar y borrar de cada Contacto
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);

    }
    //Damos funcionalidad al los elementos del menu contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long id = item.getItemId();
        AdapterView.AdapterContextMenuInfo vistaInfo =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int posicion = vistaInfo.position;

        if(id==R.id.mn_editar){
             edit(getCurrentFocus(),(Actividad) lv.getItemAtPosition(posicion));
            return true;
        } else if(id==R.id.mn_borrar){
            delete(posicion);

            return true;
        }
        return super.onContextItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
        ad.notifyDataSetChanged();
        lv.setAdapter(ad);
        Log.v("ONRESUME", "ENTRO");
    }

    public void add(View v) {
        Intent i = new Intent(this, Add.class);
        startActivity(i);
    }


    public void edit(View v, Actividad actividad) {
        Intent i = new Intent(this, Add.class);
        Bundle b = new Bundle();
        Log.v("ID", actividad.getId() + "");
        b.putBoolean("edit", true);
        b.putParcelable("actividad", actividad);
        i.putExtras(b);
        startActivity(i);
    }




    public void loadActividades() {
        datos = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();
        Cliente api = retrofit.create(Cliente.class);
        Call<List<Actividad>> call = api.getActividades();

        call.enqueue(new Callback<List<Actividad>>() {
            @Override
            public void onResponse(Response<List<Actividad>>

                                           response, Retrofit retrofit) {
                for (Actividad a : response.body()) {
                    Log.v("ACTIV", a.toString());
                    datos.add(a);
                }

                ad.notifyDataSetChanged();
                /*adaptador.notifyDataSetChanged();
                recView.setAdapter(adaptador);*/

            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }



    public void delete( final int pos) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("BORRAR");

        alertDialogBuilder
                .setMessage("¿Quiere eliminar?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteActividad((Actividad)lv.getItemAtPosition(pos));
                        ad.notifyDataSetChanged();
                        lv.setAdapter(ad);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void deleteActividad(Actividad id) {
        Log.v("DELETE ID", id.getId()+"");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();
        Cliente api = retrofit.create(Cliente.class);
        Call<Actividad> call = api.deleteAct(id.getId()+"");

        call.enqueue(new Callback<Actividad>() {
            @Override
            public void onResponse(Response<Actividad> response, Retrofit retrofit) {
                Log.v("DELETE", response.message() + "");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.v("DELETE NO", t + "");
            }
        });
    }


    public void tostada(String texto) {
        Context context = getApplicationContext();
        CharSequence text = texto;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
