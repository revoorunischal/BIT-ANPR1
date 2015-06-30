package com.example.dellpc.material2;

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

/**
 * Created by Dell Pc on 2/11/2015.
 */
public class menu1login_fragment extends Fragment {
    View rootview;
    Button btnlogout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        rootview=inflater.inflate(R.layout.menu1login_layout,container,false);
        btnlogout = (Button) rootview.findViewById(R.id.Logout_but);
        btnlogout.setOnClickListener(new btnLogout());

        return rootview;
    }

    public class btnLogout implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedpreferences = getActivity().getSharedPreferences
                    (menu1_fragment.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            menu1_fragment f1 = new menu1_fragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.container, f1); // f1_container is your FrameLayout container
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();

        }
    }
public void exit(View view){}
}
