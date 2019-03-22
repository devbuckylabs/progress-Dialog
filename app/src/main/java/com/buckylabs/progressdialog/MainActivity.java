package com.buckylabs.progressdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button;
    ProgressDialog progressDialog;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);


        final String fruits[]={"Apple","Orange","Mango","Banana","Tomato"};

        list.add("Apple");
        list.add("Orange");
        list.add("Mango");
        list.add("Banana");
        list.add("Tomato");
        list.add("Grape");
        list.add("Tamarind");
        list.add("Beer");
        list.add("Curd");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressDialog.show();
                backThread bt = new backThread(MainActivity.this, progressDialog);
                bt.execute(fruits);


            }
        });

    }

    class backThread extends AsyncTask<String, Integer, String> {

        Context context;
        ProgressDialog progressDialog;


        public backThread(Context context, ProgressDialog progressDialog) {
            this.context = context;
            this.progressDialog = progressDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Downloading");
            progressDialog.setMessage("please wait");
             progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();

        }


        @Override
        protected String doInBackground(String... strings) {

            progressDialog.setMax(strings.length);

            for (int i = 0; i < strings.length; i++) {
                try {

                    progressDialog.setMessage(strings[i]);
                  // progressDialog.setMax(100);
                    Thread.sleep(700);
                    publishProgress(i);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }




            }


            return "Backkup";
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressDialog.incrementProgressBy(values[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("POST", s);
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }


}

