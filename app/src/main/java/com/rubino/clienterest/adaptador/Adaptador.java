package com.rubino.clienterest.adaptador;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.rubino.clienterest.R;
import com.rubino.clienterest.pojo.Actividad;

import java.util.List;


/**
 * Created by marco on 22/11/2015.
 */
public class Adaptador extends ArrayAdapter {


    private Actividad c;
    private Context ctx;
    private int res;
    private LayoutInflater lInflator;
    private List<Actividad> valores;

    static class ViewHolder {
        private TextView txtDes;
        private TextView txtTipo;
        private TextView txtLugar;
        private TextView txtLugarf;
        private TextView txtFecha;
        private TextView txtFechaf;

    }

    public Adaptador(Context context, int resource, List<Actividad> objects) {
        super(context, resource, objects);
        this.ctx = context;//actividad
        this.res = resource;//layout del item
        this.valores = objects;//lista de valores
        this.lInflator = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1
        ViewHolder vh = new ViewHolder();
        Actividad valor = valores.get(position);
        if(convertView==null){
            convertView = lInflator.inflate(res, null);
            TextView txtDes = (TextView) convertView.findViewById(R.id.LblDescripcion);
            vh.txtDes = txtDes;
            TextView txtTipo = (TextView) convertView.findViewById(R.id.tvTipo);
            vh.txtTipo = txtTipo;
            TextView txtLugar = (TextView) convertView.findViewById(R.id.tvLugar);
            vh.txtLugar =txtLugar;
            TextView txtFecha = (TextView) convertView.findViewById(R.id.tvFecha);
            vh.txtFecha = txtFecha;
            TextView txtLugarf = (TextView) convertView.findViewById(R.id.tvlugarf);
            vh.txtLugarf =txtLugarf;
            TextView txtFechaf = (TextView) convertView.findViewById(R.id.tvfechaf);
            vh.txtFechaf = txtFechaf;


            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }


        vh.txtDes.setText(valores.get(position).getDescripcion());
        vh.txtFecha.setText(valores.get(position).getFechai());
        vh.txtLugar.setText(valores.get(position).getLugari());
        vh.txtTipo.setText(valores.get(position).getTipo());
        vh.txtFechaf.setText(valores.get(position).getFechaf());
        vh.txtLugarf.setText(valores.get(position).getLugarf());

        return convertView;
    }

}
