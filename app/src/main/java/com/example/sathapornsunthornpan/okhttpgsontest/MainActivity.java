package com.example.sathapornsunthornpan.okhttpgsontest;

import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private TextView textViewShow;
    private HttpUrl url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewShow = (TextView) findViewById(R.id.txtShow);


        // url
        url = new HttpUrl.Builder()
                .scheme("http")
                .host("192.168.1.104").port(80)
                .addPathSegments("android/basic-crud/getAllEmp.php")
                .build();


        callSyncGet();

//        textViewShow.setText(""+url);

    }



    // method Synchronous Get
    private void callSyncGet() {

        // Create Instance from Anonymous Class
        new AsyncTask<Void, Void, Message>() {

            @Override
            protected Message doInBackground(Void... voids) {
                OkHttpClient okHttpClient = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(url).build();

                Message message = new Message();

                try {
                    Response response = okHttpClient.newCall(request).execute();




                    if (response.isSuccessful()) {
                        message.what = 1;
                        message.obj = response;
                    } else {
                        message.what = 0;
                        message.obj = "Not Success\ncode : " + response.code();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = 0;
                    message.obj = "Error\n" + e.getMessage();
                }

                return message;
            }


            // post
            @Override
            protected void onPostExecute(Message message) {
                super.onPostExecute(message);

                Log.d("OkHttp1234: ", message.what + " " + message.obj);

                Toast.makeText(getApplicationContext(), message.obj.toString(), Toast.LENGTH_LONG).show();

                switch (message.what) {
                    case 0:
//                        dataBody = (String) message.obj;
                        break;
                    case 1:
//                        getResponseData((Response) message.obj);
                        break;
                }

//                showView();

                message.recycle();
            }

        }.execute(); // end new AsyncTask

    }
}
