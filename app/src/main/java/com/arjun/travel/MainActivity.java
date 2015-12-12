package com.arjun.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private EditText username;
    private EditText password;
    private Button signin;
    private Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        username.setText("arjunn@mindcraft.in");
        password.setText("password");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
                final String tempuser = username.getText().toString().trim();
                final String temppass = password.getText().toString().trim();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.urlUser + tempuser, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (tempuser.equalsIgnoreCase(jsonObject.getString("email_id")) && temppass.equalsIgnoreCase(jsonObject.getString("password"))) {
                                success();
                            } else {
                                Toast.makeText(getApplication(), "Invalid Login", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplication(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
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

    private void success(){
        startActivity(new Intent(this,HomeActivity.class));
    }
    private void signup(){
        startActivity(new Intent(this,SignupActivity.class));
    }

}