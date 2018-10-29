package com.project.pan.myproject.requestFramework;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.pan.myproject.R;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RequestActivity extends AppCompatActivity {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
    }

  /**************** okhttp start **************/
    public void getClick(View view){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postClick(View view){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON,"");
        Request request = new Request.Builder()
                .url("")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
           String result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

  /**************** okhttp end **************/


  /**************** volley start**************/
    public void volleyGetClick(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void volleyPostClick(){

    }
  /**************** volley end**************/

  /******************* retrofit start **********************/
  public void retrofitGetClick(){
      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl("https://api.github.com/")
              .build();

      GitHubService service = retrofit.create(GitHubService.class);

      Call<Object> repos = service.listRepos("octocat");
  }

  public void retrofitPostClick(){

  }
  /******************* retrofit end **********************/
}
