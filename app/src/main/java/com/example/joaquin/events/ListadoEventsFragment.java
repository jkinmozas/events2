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
    public ListadoEventsFragment{

    }
    public static  ListadoEventsFragment newInstance(String param1, String param2, String idAcon){

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position,String id);
    }



}
