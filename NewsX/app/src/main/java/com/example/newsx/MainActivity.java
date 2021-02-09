package com.example.newsx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsItemClicked {
    NewsListAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<News> newsList = fetchData();
        madapter = new NewsListAdapter(this,newsList);
        recyclerView.setAdapter(madapter);
    }

    public ArrayList<News> fetchData(){
        final ArrayList<News> newsArrayList = new ArrayList<>();
         //ArrayList<News> newsArrayList ;
        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=2a9b65c6b1034049a8768b2b60ab35bf";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray newsJsonArray = null;
                        try {
                            newsJsonArray = response.getJSONArray("articles");
                            Log.e("arin","newsJsonArrayParHu");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("arin","newsJsonArrayNiChala");
                        }

                        //newsArrayList = new ArrayList<>();

                        for(int i = 0; i<newsJsonArray.length(); i++){
                            JSONObject newsJsonObject = null;
                            try {
                                newsJsonObject = newsJsonArray.getJSONObject(i);
                                Log.e("arin","newsJsonObjectParHu");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("arin","newsJsonObjectNiChala");
                            }
                            News news = null;
                            try {
                                news = new News(newsJsonObject.getString("title"),
                                                         newsJsonObject.getString("author"),
                                                         newsJsonObject.getString("url"),
                                                         newsJsonObject.getString("urlToImage"));
                                Log.e("arin","newsParHu");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("arin","newsNiBani");
                            }

                            newsArrayList.add(news);
                            Log.e("arin","newsLeLi");
                        }
                        madapter.updateNewsItems(newsArrayList);
                        Log.e("arin","newsUpdateKarli");
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                }
        });
        MyRequest.getInstance(this).addToRequestQueue(jsonObjectRequest);
        Log.e("arin","newsDalDi");


        return newsArrayList;
    }

    @Override
    public void onItemClicked(News item) {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}