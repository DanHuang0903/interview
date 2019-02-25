package com.example.interview;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Callable<ArrayList<Movie>> {

    private ArrayList<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    private final OkHttpClient client = new OkHttpClient();
    private List<String> albumList = new ArrayList<String>();
    private List<String> nameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    /*
        HttpUtil.sendRequestWithOkHttp("https://rss.itunes.apple.com/api/v1/us/apple-music/coming-soon/all/10/explicit.json", new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e){

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                parseJsonWithJsonObject(response);

            }

        });
*/
        try {
            movieList = call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public ArrayList<Movie> call() throws Exception {
        Request request = new Request.Builder()
                .url("https://rss.itunes.apple.com/api/v1/us/apple-music/coming-soon/all/10/explicit.json")//请求的url
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                movieList = parseJsonWithJsonObject(response);
            }
        });
        System.out.println(movieList.size());
        return movieList;

    }


    private ArrayList<Movie> parseJsonWithJsonObject(Response response) throws IOException{
        String responseData = response.body().string();
      //  System.out.println(response.code());
        // System.out.println(responseData);



        try {
            JSONObject jsonObjectMain = new JSONObject(responseData);
            JSONObject jsonObjectSub = jsonObjectMain.getJSONObject("feed");
            JSONArray json = jsonObjectSub.getJSONArray("results");
            for (int i = 0; i < json.length(); i++) {
                JSONObject jsonArray = json.getJSONObject(i);
                String album = jsonArray.getString("name");
                String name = jsonArray.getString("artistName");

               // albumList.add(album);
               // nameList.add(name);
             //   System.out.println(movie.getTitle());
                Movie movie = new Movie(album, name);
                movieList.add(movie);

            }
          //  mAdapter.notifyDataSetChanged();

        }catch (JSONException e){
            e.printStackTrace();
        }

        return movieList;

    }



}
