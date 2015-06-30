package com.example.dellpc.material2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Dell Pc on 2/11/2015.
 */

public class menu1_fragment extends Fragment 
{
    private EditText username, password;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String name = null;
    public static final String pass = null;
    SharedPreferences sharedpreferences;

    private static final String TAG_SUCCESS = "success";
    private ProgressDialog pDialog;
    Button btnlogin;
    View rootview;
    private static final String TAG = "menu1_frag";
    private TextView status,role;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu1_layout, container, false);
        username = (EditText) rootview.findViewById(R.id.username);
        password = (EditText) rootview.findViewById(R.id.Password);
        btnlogin = (Button) rootview.findViewById(R.id.LOG_BUT);
        btnlogin.setOnClickListener(new btnLogin());
        status = (TextView)rootview.findViewById(R.id.textView6);
        role = (TextView)rootview.findViewById(R.id.textView7);
        return rootview;
    }

    @Override
    public void onResume() {
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(name)) {
            if (sharedpreferences.contains(pass)) {
                menu1login_fragment f1 = new menu1login_fragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, f1); // f1_container is your FrameLayout container
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        }

        super.onResume();
    }

    public class btnLogin implements Button.OnClickListener {
        @Override
                public void onClick(View v) {
            String g1="Login Successful";
            String g3="Not login";
          
            String u = username.getText().toString();
            String p = password.getText().toString();

            new signin_activity(getActivity().getBaseContext(),status,role).execute(u,p);
            String g2;
            
            g2 = status.getText().toString();
            if(g2.equals("Login Successful"))
            Toast.makeText(getActivity().getBaseContext(),g2,Toast.LENGTH_SHORT).show();
            if(g2.equals(g1))
            {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(name, u);
                editor.putString(pass, p);
                editor.commit();
                menu1login_fragment f1 = new menu1login_fragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, f1); // f1_container is your FrameLayout container
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }
}