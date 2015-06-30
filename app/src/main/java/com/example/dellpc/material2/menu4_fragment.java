package com.example.dellpc.material2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Dell Pc on 2/11/2015.
 */
public class menu4_fragment extends Fragment {
    View rootview;
    Button btnregister;
    Spinner dropdown;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String name = null;
    SharedPreferences sharedpreferences;
   public  String username;
    private TextView status,role;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        rootview=inflater.inflate(R.layout.menu4_layout,container,false);      
         dropdown = (Spinner)rootview.findViewById(R.id.spinner2);
        String[] items = new String[]{"Jaynagar", "Utthralli", "Kengeri","JP nagar",
                "Chamrajpet","Rajajinagar","Basveshwaranagar","Srinagar","Banshakari"
                ,"Mg Road","Kormangla","Btm Layout"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootview.getContext(),R.layout.support_simple_spinner_dropdown_item,items);
        dropdown.setAdapter(adapter);
        status = (TextView)rootview.findViewById(R.id.textView61);
        role = (TextView)rootview.findViewById(R.id.textView71);
        sharedpreferences = getActivity().getSharedPreferences
                (menu1_fragment.MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(name)) {
           username=sharedpreferences.getString(name,"not found");
        }
        else
        username="not found";
        btnregister = (Button) rootview.findViewById(R.id.REG_BUT);
        btnregister.setOnClickListener(new btnregis());

        return rootview;
    }

    public class btnregis implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
          String loca= String.valueOf(dropdown.getSelectedItem());
           
            if(username.equals("not found"))
        {
            Toast.makeText(getActivity().getApplicationContext(),"not signed in ",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {

            new registration(getActivity().getBaseContext(), status, role).execute(username,loca);

        }
        }
    }
}
