package com.example.dellpc.material2;

/**
 * Created by Dell Pc on 5/5/2015.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class checking extends AsyncTask<String,Void,String>
{
    private static final String TAG = "checking.java";
        private TextView statusField,roleField;
        private Context context;
        //private int byGetOrPost = 0;
        //flag 0 means get and 1 means post.(By default it is get.)
        public checking(Context context, TextView statusField,
                        TextView roleField) {
            this.context = context;
            this.statusField = statusField;
            this.roleField = roleField;
          //  byGetOrPost = flag;
        }

        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String... arg0) {
                try{

                    String username = arg0[1];
                    String link="http://apnr8.blg.lt/checking.php";
                    String data  = URLEncoder.encode("uname", "UTF-8")
                            + "=" + URLEncoder.encode(username, "UTF-8");
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter
                            (conn.getOutputStream());
                    wr.write( data );
                    wr.flush();
                    Log.e(TAG, "ovnv,dv");
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }

                    return sb.toString();
                    
                }catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }
        //}
        @Override
        protected void onPostExecute(String result){
            if(result.charAt(12)=='1') {
                this.statusField.setText("found");
                String result1=result.substring(26,55);
                result1+="\n";
                result1+=result.substring(56,70);
                result1+="\n";
                result1+=result.substring(71,88);
                result1+="\n";
                this.roleField.setText(result1);

            }
            else {

                this.statusField.setText("not found");
                this.roleField.setText("not found");

            }
            
            
        }
    }
