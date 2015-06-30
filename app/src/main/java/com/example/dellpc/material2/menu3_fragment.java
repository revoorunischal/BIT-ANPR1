package com.example.dellpc.material2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;

/**
 * Created by Dell Pc on 2/11/2015.
 */
public class menu3_fragment extends Fragment {
    Button btnTakePhoto, btnsubmission;
    ImageView imgTakenPhoto;
    public static final int CAM_REQUEST = 1313;
    public final int CAMERA_RESULT = 1;
    private final String Tag = getClass().getName();

    public boolean saveRegions = false;
    CascadeClassifier cas;

    private int mAbsolutePlateSize = 0;
    private float mRelativePlateSize = 0.1F;
    Rect[] tPlatesArray;
    Rect[] platesArray;
    private File mCascadeFile;
    private CascadeClassifier mJavaDetector;
    private static final String TAG = "CameraAct.java";
    String rText;
    Mat rbga;
    Mat gray;
    Bitmap textImg;
    Mat cmrFrame;
    Bitmap displayImg;
    Mat subPlate;
    Mat subPlateClone;
    TessBaseAPI tess;
    EditText textView;
    View rootview;
    String Path;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String name = null;
    SharedPreferences sharedpreferences;
    public String username;
    public String vehicle_no;

    private TextView status, role;
    Spinner dropdown;
    SharedPreferences sharedPreferences1;
    public static final String MyPREFERENCES1 = "MyPrefs";
    public static final String start = null;
    public static final String veno1 = null;
    int abcs=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu3_layout, container, false);
        btnTakePhoto = (Button) rootview.findViewById(R.id.button1);
        btnsubmission = (Button) rootview.findViewById(R.id.Submit1);
        textView = (EditText) rootview.findViewById(R.id.vehnum1);
        imgTakenPhoto = (ImageView) rootview.findViewById(R.id.imgpic1);
        dropdown = (Spinner) rootview.findViewById(R.id.spinner1);
        String[] items = new String[]{"1 Riding without helmet", "2 Signal Jump", "3 No parking", "4 triple riding", "5 footpath riding"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootview.getContext(), R.layout.support_simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        status = (TextView) rootview.findViewById(R.id.textView62);
        role = (TextView) rootview.findViewById(R.id.textView72);
        sharedpreferences = getActivity().getSharedPreferences
                (menu1_fragment.MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(name)) {
            username = sharedpreferences.getString(name, "not found");
        }
        else 
            username="not found";
        btnTakePhoto.setOnClickListener(new btnTakePhotoClicker());
        btnsubmission.setOnClickListener(new btnsubmit());
        return rootview;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG,"a");
        if (requestCode == 1) {
            Log.e(TAG,"b");

                Log.e(TAG,"c");

                sharedPreferences1 = getActivity().getSharedPreferences
                        (menu3_fragment.MyPREFERENCES1, Context.MODE_PRIVATE);
                if (sharedPreferences1.contains(veno1)) {
                    vehicle_no = sharedpreferences.getString(veno1, "not found");
                    Log.e(TAG,vehicle_no);
                    textView.setText(vehicle_no);
                }

            }
      }
    public void upd()
    {
        

        sharedPreferences1 = getActivity().getSharedPreferences
                (menu3_fragment.MyPREFERENCES1, Context.MODE_PRIVATE);
        if (sharedPreferences1.contains(veno1)) {
            vehicle_no = sharedpreferences.getString(veno1, "not found");
            Log.e(TAG,vehicle_no);
            textView.setText(vehicle_no);
        
    }
  }
    public class btnTakePhotoClicker implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent1= new Intent(getActivity(),CameraAct1.class);
            startActivityForResult(intent1,abcs);
            upd();
            }
    }

    public class btnsubmit implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            String typeof= String.valueOf(dropdown.getSelectedItem());
            String typeoff= String.valueOf(typeof.charAt(0));
            String num=textView.getText().toString();
            Toast.makeText(getActivity().getApplicationContext(),num,Toast.LENGTH_SHORT).show();

            if(username.equals("not found"))
            {
                Toast.makeText(getActivity().getApplicationContext(),"not signed in ",Toast.LENGTH_SHORT).show();

            }
            else
                new submission(getActivity().getBaseContext(),status,role).execute(username,num,typeoff);
        }
        }

}