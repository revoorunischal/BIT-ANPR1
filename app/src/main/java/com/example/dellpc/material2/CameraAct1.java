package com.example.dellpc.material2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class CameraAct1 extends Activity implements CvCameraViewListener2
{
    EditText textView;

    String Path;
  Scalar black = new Scalar(0.0D);
  Bitmap[] bmArr;
  CascadeClassifier cas;
  Mat cmrFrame;
  Bitmap displayImg;
  Mat gray;
  ImageView iv;
  private int mAbsolutePlateSize = 0;
  private float mRelativePlateSize = 0.1F;
    private File mCascadeFile;
    private CascadeClassifier mJavaDetector;
    private static final String TAG = "CameraAct.java";
    Rect[] platesArray;
    String rText;
    Mat rbga;
    Mat subPlate;
    Mat subPlateClone;
    Rect[] tPlatesArray;
     TessBaseAPI tess;
    Bitmap textImg;
    JavaCameraView v;
    public static final String MyPREFERENCES = "MyPrefs";
    public  String username;
    SharedPreferences sharedpreferences;
    SharedPreferences sharedpreferences1;
    public static final String name = null;
    public static final String start = null;
    public static final String veno1 = null;
    int abcs=1;
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.e(TAG, "OpenCloadedsuccessfully");
                   try {
                        // load cascade file from application resources
                        InputStream is = getResources().openRawResource(R.raw.cascade);

                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir, "cascade.xml");

                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                       Path=(getDir("cascade", 0).getAbsolutePath() + "/");
                        is.close();
                        os.close();
                        CascadeClassifier cas1;
                        mJavaDetector = new CascadeClassifier(
                                mCascadeFile.getAbsolutePath());
                         cas= mJavaDetector;
                        if (mJavaDetector.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier1");
                            mJavaDetector = null;
                        } else
                            Log.e(TAG, "Loaded cascade classifier from "
                                    + mCascadeFile.getAbsolutePath());
                                           } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }
                }

                v.enableView();
                break;

               
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    private void createFileStructure() {
        File storageDir = new File ("/mnt/sdcard/" + "BTP");
        File tessData = new File ("/mnt/sdcard/" + "BTP/tessdata");
        storageDir.mkdirs();
        tessData.mkdirs();
    }

    public void createTrainedData() {
        Context a = this;
        AssetManager assets = a.getAssets();
        try {
            FileOutputStream out2 = new FileOutputStream("/mnt/sdcard/" + "BTP/tessdata/eng.traineddata");
            InputStream in2 = assets.open("eng.traineddata");
            byte[] buffer2 = new byte[1024];
            int len2;
            while ((len2 = in2.read(buffer2)) != -1) {
                out2.write(buffer2, 0, len2);
            }
        } catch (Exception e) {
            Log.e("Failed","Full Failure");
        }
    }

      public void readtext(int a){
      
            Log.e(TAG,"processStarted");
           rText = "";
           //isRunning = true;
            ArrayList localArrayList1 = new ArrayList();
                    subPlate =cmrFrame.submat(tPlatesArray[a]);
                    double d = subPlate.height() * subPlate.width();
                    displayImg = Bitmap.createBitmap(subPlate.width(), subPlate.height(), Bitmap.Config.ARGB_8888);
                    Imgproc.blur(subPlate, subPlateClone, new Size(4.0D, 4.0D));
                    Imgproc.adaptiveThreshold(subPlateClone,subPlateClone, 255.0D, 1, 1, 91, -5.0D);
                    Imgproc.morphologyEx(subPlateClone,subPlateClone, 2, Imgproc.getStructuringElement(2, new Size(4.0D, 4.0D)));
                    Imgproc.findContours(subPlateClone, localArrayList1, new Mat(), 0, 2);
                    Log.e(TAG,"processStarted"+localArrayList1.size());

          ArrayList localArrayList2 = new ArrayList();
                    int k;
                    k = localArrayList1.size();
                    int m=0;
                    if(k>0) {
                        for (int j = 0; j < k; j++) {
                            Rect localRect = Imgproc.boundingRect((MatOfPoint) localArrayList1.get(j));
                            if ((localRect.area() / d >= 0.02D) && (localRect.area() / d <= 0.06D) && (localRect.width /
                                    subPlateClone.width() < 0.5D) && (localRect.tl().x / subPlateClone.width() > 0.05D)) {
                                localArrayList2.add(localRect);
                            }
                        }
                        int p;
                        p = localArrayList2.size();
                        if (p > 0) {
                            Collections.sort(localArrayList2, new MyCompare());
                            Log.e(TAG, "process Started detect" + p);


                            for (int l = 0; l < p; l++) {
                                new Mat();
                                int i1 = (int) ((Rect) localArrayList2.get(l)).tl().y;
                                int i2 = (int) ((Rect) localArrayList2.get(l)).br().y;
                                int i3 = (int) ((Rect) localArrayList2.get(l)).tl().x;
                                int i4 = (int) ((Rect) localArrayList2.get(l)).br().x;
                                if (((Rect) localArrayList2.get(l)).tl().y - 5.0D > 0.0D) {
                                    i1 = (int) (((Rect) localArrayList2.get(m)).tl().y - 5.0D);
                                }
                                if (5.0D + ((Rect) localArrayList2.get(l)).br().y < ((Rect) localArrayList2.get(l)).height) {
                                    i2 = (int) (5.0D + ((Rect) localArrayList2.get(l)).br().y);
                                }
                                if (((Rect) localArrayList2.get(l)).tl().x - 5.0D > 0.0D) {
                                    i3 = (int) (((Rect) localArrayList2.get(l)).tl().x - 5.0D);
                                }
                                if (5.0D + ((Rect) localArrayList2.get(l)).br().x < ((Rect) localArrayList2.get(l)).width) {
                                    i4 = (int) (5.0D + ((Rect) localArrayList2.get(l)).br().x);
                                }
                                if (i1 > 0 && i2 > 0 && i3 > 0 && i4 > 0) 
                                {
                                    Mat localMat = subPlate.submat(i1, i2, i3, i4).clone();
                                    if((i4-i3)>0 && i2-i1>0)
                                    {
                                        textImg = Bitmap.createBitmap(i4 - i3, i2 - i1, Bitmap.Config.ARGB_8888);
                                    
                                    Utils.matToBitmap(localMat, textImg);
                                    iv.setImageBitmap(textImg);
                                    if (textImg != null) {

                                        tess.setImage(textImg);
                                        String str = tess.getUTF8Text();

                                        if (str != "") {
                                            rText += str;
                                        }
                                    }

                                  if (rText.length() == 10) {
                                      sharedpreferences1 = this.getSharedPreferences
                                              (menu3_fragment.MyPREFERENCES1, Context.MODE_PRIVATE);
                                          String abda = check(rText);
                                      if(!abda.contains("?"))
                                      {
                                          if(Character.isLetter(abda.charAt(0)) && Character.isLetter(abda.charAt(1)) && Character.isDigit(abda.charAt(2)) && Character.isDigit(abda.charAt(3)) && Character.isLetter(abda.charAt(4)) && Character.isLetter(abda.charAt(5)))
                                              if (Character.isDigit(abda.charAt(6)) &&Character.isDigit(abda.charAt(7)) &&Character.isDigit(abda.charAt(8)) &&Character.isDigit(abda.charAt(9)) ) 
                                              {

                                                  Toast.makeText(CameraAct1.this, abda, Toast.LENGTH_LONG).show();
                                                  Log.e(TAG, "1");
                                                  EditText editText = (EditText) findViewById(R.id.vehnum1);
                                                  SharedPreferences.Editor editor = sharedpreferences1.edit();
                                                  editor.putString(veno1, abda);
                                                  editor.apply();
                                                  finish();
                                              }
                                      }
                                  }
                              }
                            }
                        } 
                        }
                        else 
                        {
                            Toast.makeText(CameraAct1.this, rText, 0).show();
                        }
                    }
            } 
        
String check(String abd) {
    String new1 = null;
    if (abd.charAt(0) != 'K' || abd.charAt(1) != 'A') 
        new1 = "KA";
     else
        new1 = "KA";
    if (abd.charAt(2) == 'D')
        new1 += '0';
    else if(Character.isDigit(abd.charAt(2)))
        new1 += abd.charAt(2);
    else
      new1+='?';
    if (abd.charAt(3) == '6')
        new1 += '4';
    
    else
        new1 += abd.charAt(3);
    if (Character.isLetter(abd.charAt(4)) && Character.isLetter(abd.charAt(5))) 
    {
        
        if (abd.charAt(4)=='E' &&abd.charAt(5) == 'N' )
            new1 += abd.substring(4,6);
        else if(abd.charAt(4)=='B')
        {
            new1+='E';
            new1 += abd.charAt(5);
        }
        else if(abd.charAt(4)=='E')
        {
            new1 += abd.charAt(4);
            new1+='N';
        }
           else
            new1 += abd.substring(4,6);

    } 
    else
        new1 += "EN";
    if (Character.isDigit(abd.charAt(6)) && Character.isDigit(abd.charAt(7)) && Character.isDigit(abd.charAt(8)) && Character.isDigit(abd.charAt(9))) 
        new1 += abd.substring(6, 10);
     else {
        if (Character.isLetter(abd.charAt(6))) 
        {
            if (abd.charAt(6) == 'B' || abd.charAt(6) == 'D')
                new1 += '8';
            else
                new1 += abd.charAt(6);
        } 
        else
            new1 += abd.charAt(6);

        if (Character.isLetter(abd.charAt(7))) {
            if (abd.charAt(7) == 'S')
                new1 += '6';
            else
                new1 += abd.charAt(7);
        } else if( abd.charAt(7)=='8')
            new1+='6';
           else new1 += abd.charAt(7);
        if (Character.isLetter(abd.charAt(8))) {
            if (abd.charAt(8) == 'S')
                new1 += '5';
            else
                new1 += abd.charAt(8);
        }
        else if( abd.charAt(8)=='6')
            new1+='5';
        else
            new1 += abd.charAt(8);
        if (Character.isLetter(abd.charAt(9))) {
            if (abd.charAt(9) == 'B' || abd.charAt(9) == 'D' || abd.charAt(9)=='S')
                new1 += '8';
            else
                new1 += abd.charAt(9);
        } else
            new1 += abd.charAt(9);
    }
    return new1;
}
  public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame paramCvCameraViewFrame) {
      rbga = paramCvCameraViewFrame.rgba();
    gray = paramCvCameraViewFrame.gray();

     CameraAct1.this.runOnUiThread(new Runnable()
      {
          public void run() {
              if (mAbsolutePlateSize == 0) {
                  int j = gray.rows();
                  if (Math.round(j * mRelativePlateSize) > 0) {
                      mAbsolutePlateSize = Math.round(j * mRelativePlateSize);
                  }
              }
              MatOfRect localMatOfRect = new MatOfRect();
              if (cas != null) {

                  cas.detectMultiScale(gray, localMatOfRect, 1.1D, 2, 2, new Size(mAbsolutePlateSize, mAbsolutePlateSize), new Size());
              }
              platesArray = localMatOfRect.toArray();
              if (platesArray.length > 0) {
                  for (int i = 0; i < platesArray.length; i++) {
                      cmrFrame = gray.clone();
                      tPlatesArray = platesArray.clone();
                       readtext(i);


                      Core.rectangle(rbga, platesArray[i].tl(), platesArray[i].br(), new Scalar(0.0D, 255.0D, 0.0D, 255.0D), 3);
                  }
              }
          }  
     });
        return paramCvCameraViewFrame.rgba();
  }
  
  public void onCameraViewStarted(int paramInt1, int paramInt2)
  {
      Log.e(TAG,"camerastarted");
    rbga = new Mat();
    gray = new Mat();
    subPlate = new Mat();
    subPlateClone = new Mat();
    cmrFrame = new Mat();
      tess= new TessBaseAPI();

      String path = "/mnt/sdcard/BTP/";

      tess.setDebug(true);
      tess.init(path,"eng");

  }

    @Override
    public void onCameraViewStopped() {
        Log.e(TAG,"stopped");
        super.onDestroy();
        if (v != null)
            v.disableView();

    }


    protected void onCreate(Bundle paramBundle)
  {
      Log.e(TAG,"start");
    super.onCreate(paramBundle);
    setContentView(R.layout.main1);
    iv = ((ImageView)findViewById(R.id.iv));
    v=((JavaCameraView)findViewById(R.id.jvcmr));
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      createFileStructure();
      createTrainedData();
      Log.e(TAG, "Creating and setting view");
      v.setVisibility(View.VISIBLE);
    v.setCvCameraViewListener(this);

  }
  
  protected void onPause()
  {
      Log.e(TAG,"paused");
    super.onPause();
    if (this.v != null) {
      this.v.disableView();
    }
  }
    @Override
    public void onResume()
    {
        Log.e(TAG,"resumed");
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_6, this,mLoaderCallback);
    }

}

