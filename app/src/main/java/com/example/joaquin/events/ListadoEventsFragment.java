package com.example.joaquin.events;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by Joaquin on 07/02/2017.
 */

public class ListadoEventsFragment extends ListFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ListView listView;
    private String idAcon;
    private ArrayList<EventoItem> items;
    private EventoAdapter evtAdapter;
    private OnFragmentInteractionListener mListener;
    public ListadoEventsFragment(){

    }
    public static  ListadoEventsFragment newInstance(String param1, String param2, String idAcon){
        ListadoEventsFragment fragment = new ListadoEventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,param1);
        args.putString(ARG_PARAM2,param2);

        fragment.setArguments(args);
        return fragment;


    }
    @Override
    public  void onStart(){
        super.onStart();
        if(getFragmentManager().findFragmentById(R.id.fragmentMostrarEvento)!=null){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_listado_eventos , container , false);
        listView = (ListView) rootView.findViewById(android.R.id.list);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //se usan difetentes listas de item dependiendo de la version
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB? android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;


        SharedPreferences prefs = getActivity().getSharedPreferences("Ajustes", Context.MODE_PRIVATE);

        idAcon = prefs.getString("id", "Error con Shared");
        BBDDSQLiteHelper usdbh = new BBDDSQLiteHelper(getActivity(), Environment.getExternalStorageDirectory()+"/Events.db",null,1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        String[] argsId =new String[]{idAcon};
        Cursor cursor = db.rawQuery("SELECT id, nombre FROM evento WHERE id_acontecimiento=? ", argsId);

        //miramos si existe algo
        if(cursor.moveToFirst()){
            items = new ArrayList<EventoItem>();
            //recorremos y obtenemos los datos a traves del cursor
            do{

                String id = cursor.getString(cursor.getColumnIndex("id"));
                String nombreAcon = cursor.getString(cursor.getColumnIndex("nombre"));
                items.add(new EventoItem(id,nombreAcon));


            }while (cursor.moveToNext());
        }
        evtAdapter = new EventoAdapter(getActivity(), layout, items);
        listView.setAdapter(evtAdapter);

    }

    @Override
    public void onListItemClick(ListView list, View view, int position, long id){
        if(mListener != null){
            mListener.onFragmentInteraction(position,items.get(position).getId());

        }
        getListView().setItemChecked(position, true);


    }
    @Override
    public void onAttach (Context context){
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }else{

            throw  new RuntimeException(context.toString() + "must implement OnFragmentIteractionListener");

        }
    }
    @Override
    public void onDetach(){
        super.onDetach();
        mListener=null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position,String id);
    }




}
