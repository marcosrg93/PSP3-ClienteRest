package com.rubino.clienterest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.rubino.clienterest.interfaces.Cliente;
import com.rubino.clienterest.pojo.Actividad;
import com.rubino.clienterest.pojo.Profesor;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Add extends AppCompatActivity{

    private ArrayList<Profesor> datos;
    private TextView textView4;
    private Spinner prof;
    private TextView textView8;
    private EditText horaIn;
    private TextView textView10;
    private EditText horaFin;
    private TextView textView11;
    private EditText lugarI;
    private TextView textView12;
    private EditText lugarF;
    private TextView textView13;
    private EditText fecha;
    private TextView textView14;
    private TextView textView15;
    private EditText desc;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private RadioGroup radioGroup;
    private ScrollView scrollView;
    private Button button;
    private Button button2;
    private LinearLayout linearLayout;
    private Long profesorId;
    private ArrayAdapter spinner_adapter;
    private String item;
    private  boolean edit;
    private  Actividad activ;
    private long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        this.linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        this.button2 = (Button) findViewById(R.id.button2);
        this.button = (Button) findViewById(R.id.button);
        this.scrollView = (ScrollView) findViewById(R.id.scrollView);
        this.radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        this.radioButton2 = (RadioButton) findViewById(R.id.rbExtraescolar);
        this.radioButton = (RadioButton) findViewById(R.id.rbComplementaria);
        this.desc = (EditText) findViewById(R.id.desc);
        this.textView15 = (TextView) findViewById(R.id.textView15);
        this.textView14 = (TextView) findViewById(R.id.textView14);
        this.fecha = (EditText) findViewById(R.id.fecha);
        this.textView13 = (TextView) findViewById(R.id.textView13);
        this.lugarF = (EditText) findViewById(R.id.lugarF);
        this.textView12 = (TextView) findViewById(R.id.textView12);
        this.lugarI = (EditText) findViewById(R.id.lugarI);
        this.textView11 = (TextView) findViewById(R.id.textView11);
        this.horaFin = (EditText) findViewById(R.id.horaFin);
        this.textView10 = (TextView) findViewById(R.id.textView10);
        this.horaIn = (EditText) findViewById(R.id.horaIn);
        this.textView8 = (TextView) findViewById(R.id.textView8);
        this.prof = (Spinner) findViewById(R.id.prof);
        this.textView4 = (TextView) findViewById(R.id.textView4);


        Bundle b = getIntent().getExtras();
        if (b != null) {
            edit = b.getBoolean("edit");

            Log.v("BOOL", edit + "");

            if (b.getParcelable("actividad") != null) {
                activ = b.getParcelable("actividad");
                Log.v("Actividad", activ.toString());
                if (activ != null) {
                    id = activ.getId();
                    desc.setText(activ.getDescripcion().toString());

                    fecha.setText(activ.getFechai().toString().substring(0, 11));

                    lugarI.setText(activ.getLugari().toString());
                    lugarF.setText(activ.getLugarf().toString());

                    horaIn.setText(activ.getFechai().toString().substring(11, 19));
                    horaFin.setText(activ.getFechaf().toString().substring(11,19));

                    if (activ.getTipo().contains("Complementaria")) {
                        radioButton.setChecked(true);
                    } else if (activ.getTipo().contains("extraescolar")) {
                        radioButton2.setChecked(true);
                    }
                }
            }
        }

        loadProfesores();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit != true){
                    add(v);
                    finish();
                }else {
                    edit(v);
                    finish();
                }


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //Creamos el adaptador
        spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, datos);
        //Añadimos el layout para el menú y se lo damos al spinner
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        prof.setAdapter(spinner_adapter);
        prof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });








    }


    public void abrir(View view) {
        Intent i = new Intent(this, ActividadCliente.class);
        startActivity(i);
    }




    public void add(View view) {
        Actividad nueva = new Actividad();
        nueva.setDescripcion(desc.getText().toString());
        nueva.setFechai(fecha.getText().toString() + " " + horaIn.getText().toString());
        nueva.setFechaf(fecha.getText().toString() + " " + horaFin.getText().toString());
        nueva.setLugari(lugarI.getText().toString());
        nueva.setLugarf(lugarF.getText().toString());

        nueva.setIdprofesor(1);
        nueva.setAlumno("Marcos");
        if (radioButton.isChecked()) {
            nueva.setTipo("Complementaria");
        } else if (radioButton2.isChecked()) {
            nueva.setTipo("extraescolar");
        }
        Log.v("NUEVA ACTIVIDAD", nueva.toString());
        addActividad(nueva);
    }

    public void addActividad(Actividad actividad) {
        Log.v("ADD RETRO", actividad.toString());
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();

        Cliente api = retrofit.create(Cliente.class);
        Call<Actividad> call = api.createAct(actividad);
        call.enqueue(new Callback<Actividad>() {
            @Override
            public void onResponse(Response<Actividad> response, Retrofit retrofit) {
                Log.v("URL RETRO", response + "");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.v("URL NO RETRO", t + "");
            }
        });


    }

    public void edit(View view) {
        Actividad editada = new Actividad();
        editada.setId(id);
        editada.setDescripcion(desc.getText().toString());
        editada.setFechai(fecha.getText().toString() + " " + horaIn.getText().toString());
        editada.setFechaf(fecha.getText().toString() + " " + horaFin.getText().toString());
        editada.setLugari(lugarI.getText().toString());
        editada.setLugarf(lugarF.getText().toString());

        editada.setIdprofesor(1);
        editada.setAlumno("Marcos");
        if (radioButton.isChecked()) {
            editada.setTipo("Complementaria");
        } else if (radioButton2.isChecked()) {
            editada.setTipo("extraescolar");
        }
        Log.v("Edit ACTIVIDAD", editada.toString());
        editActividad(editada);
    }

    public void editActividad(Actividad actividad) {
        Log.v("EDIT RETRO", actividad.toString());
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();

        Cliente api = retrofit.create(Cliente.class);
        Call<Actividad> call = api.updateActividad(actividad);
        call.enqueue(new Callback<Actividad>() {
            @Override
            public void onResponse(Response<Actividad> response, Retrofit retrofit) {
                Log.v("URL EDIT RETRO", response + "");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.v("URL EDIT NO RETRO", t + "");
            }
        });


    }

    public void loadProfesores() {
        datos = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();
        Cliente api = retrofit.create(Cliente.class);
        Call<List<Profesor>> call = api.getProfesores();

        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Response<List<Profesor>>

                                           response, Retrofit retrofit) {
                for (Profesor a : response.body()) {
                    Log.v("PROF", a.toString());
                    datos.add(a);

                }

                /*ArrayAdapter<String> adaptador =
                        new ArrayAdapter<Profesor>(this,
                                android.R.layout.simple_spinner_item, datos);
                prof.setAdapter(adaptador);*/


            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }


}
