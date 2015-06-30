package com.example.dellpc.material2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Dell Pc on 2/11/2015.
 */
public class menu2_fragment extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String name = null;
    SharedPreferences sharedpreferences;
    Button btnTakePhoto;
    Text settext;
    private TextView status,role;
    Button btnchecking;
    View rootview;
    public  String username;
    private static final String TAG = "menu2_frag.java";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        rootview=inflater.inflate(R.layout.menu2_layout,container,false);
        sharedpreferences = getActivity().getSharedPreferences
                (menu1_fragment.MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(name)) {
            username=sharedpreferences.getString(name,"not found");
        }
        else
            username="not found";
        Log.e(TAG, "username found");
        role = (TextView)rootview.findViewById(R.id.textView74);
        status = (TextView)rootview.findViewById(R.id.textView64);

        btnTakePhoto = (Button) rootview.findViewById(R.id.button2);
        btnTakePhoto.setOnClickListener(new btnTakePhotoClicker());
        btnchecking= (Button) rootview.findViewById(R.id.chk_fin);
        btnchecking.setOnClickListener(new btnchk());
        return rootview;
    }



 public class btnTakePhotoClicker implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
           Intent intent= new Intent(getActivity(),CameraAct.class);
            startActivity(intent);
        }
        }

    public class btnchk implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            if(username.equals("not found"))
         {
          Toast.makeText(getActivity().getApplicationContext(), "not signed in ", Toast.LENGTH_SHORT).show();
         }
         else
          {
         new checking(getActivity().getBaseContext(),status,role).execute(username,username);
         }
       }
    }
 }