//package com.example.entrega1;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//
//public class AdaptadorListView extends BaseAdapter {
//    private Context contexto;
//    private LayoutInflater inflater;
//    private String[] datos;
//    private int[] imagenes;
//    private double[] puntuaciones;
//    public AdaptadorListView(Context pcontext, String[] pdatos, int[] pimagenes, double[]ppuntuaciones)
//    {
//        contexto = pcontext;
//        datos = pdatos;
//        imagenes=pimagenes;
//        puntuaciones=ppuntuaciones;
//        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        return datos.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return datos[position];
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        view=inflater.inflate(R.layout.fila,null);
//        TextView nombre= (TextView) view.findViewById(R.id.etiqueta);
//        ImageView img=(ImageView) view.findViewById(R.id.imagen);
//        RatingBar barra= (RatingBar) view.findViewById(R.id.barra);
//        nombre.setText(datos[i]);
//        img.setImageResource(imagenes[i]);
//        barra.setRating((float)puntuaciones[i]);
//        return view;
//    }
//}
