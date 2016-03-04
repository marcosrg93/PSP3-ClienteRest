package com.rubino.clienterest.interfaces;

import com.rubino.clienterest.pojo.Actividad;
import com.rubino.clienterest.pojo.Grupo;
import com.rubino.clienterest.pojo.Profesor;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by marco on 15/02/2016.
 */
public interface Cliente {


    @GET("restful/api/grupo") Call<List<Grupo>> getGrupos();

    @GET("restful/api/actividad/marcos")
    Call<List<Actividad>> getActividades();

    @GET("restful/api/profesor")
    Call<List<Profesor>> getProfesores();

    @POST("restful/api/actividad")
    Call<Actividad> createAct(@Body Actividad actividad);

    @PUT("restful/api/actividad")
    Call<Actividad>updateActividad(@Body Actividad act);

    @DELETE("restful/api/actividad/{id}")
    Call<Actividad> deleteAct(@Path("id") String id);


}
