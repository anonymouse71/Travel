package com.arjun.travel;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arjun.network.VolleySingleton;
import com.arjun.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private NavBarAdapter adapter;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragrament_navigation_drawer);
        drawerFragment.setUp(R.id.fragrament_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        StrictMode.setThreadPolicy(policy);
        RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.urlCars, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    recyclerView = (RecyclerView) findViewById(R.id.drawerList);
                    adapter = new NavBarAdapter(getApplication(),getData(jsonArray));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);


    }

    public static List<NavBarData> getData(JSONArray array) throws JSONException, IOException {
        List<NavBarData> data = new ArrayList<>();
        int[] icon = {R.drawable.common_full_open_on_phone,R.drawable.common_full_open_on_phone,R.drawable.common_full_open_on_phone,
                R.drawable.common_full_open_on_phone};
        for(int i=0;i<array.length();i++){
            JSONObject obj = array.getJSONObject(i);
            NavBarData navBarData = new NavBarData();
            /*navBarData.setIconId(icon[i]);*/
            URL thumb_u = new URL("http://www.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808122.png");
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            /*navBarData.setIconUri("http://www.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808122.png");*/
            navBarData.setThumb_d(thumb_d);
            navBarData.setTitle(obj.getString("name"));
            navBarData.setIconId(obj.getString("_id"));
            navBarData.setDiscription("Seats : " + obj.getString("seats") +"\n" + obj.getString("location"));
            data.add(navBarData);
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}

