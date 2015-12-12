package com.arjun.travel;

import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arjun.network.VolleySingleton;
import com.arjun.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CarsActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView image;
    private TextView txtCar;
    private TextView txtSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id").trim();
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragrament_navigation_drawer);
        drawerFragment.setUp(R.id.fragrament_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        image = (ImageView) findViewById(R.id.car_pic);
        txtCar = (TextView) findViewById(R.id.txtcar);
        txtSeats = (TextView) findViewById(R.id.txtseats);
        RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.urlCars + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    URL thumb_u = null;
                    try {
                        thumb_u = new URL("http://www.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808122.png");
                        Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
                        image.setImageDrawable(thumb_d);
                        txtCar.setText(jsonObject.getString("name"));
                        txtSeats.setText("Seats : "+jsonObject.getString("seats"));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
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
