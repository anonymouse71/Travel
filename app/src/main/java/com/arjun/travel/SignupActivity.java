package com.arjun.travel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.arjun.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText username;
    private EditText password;
    private EditText cnfpassword;
    private EditText ph_no;
    private Button signin;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        ph_no = (EditText) findViewById(R.id.ph_no);
        cnfpassword = (EditText) findViewById(R.id.cnfpassword);

        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempuser = username.getText().toString().trim();
                String temppass = password.getText().toString().trim();
                String tempcnfpass = cnfpassword.getText().toString().trim();
                String tempph_no = ph_no.getText().toString().trim();
                if (temppass.equals(tempcnfpass)) {
                    RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
                    String url = "http://192.168.56.1:3000/users/";
                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put("username", tempuser.split("@")[0]);
                        jsonObject.put("password", temppass);
                        jsonObject.put("email_id", tempuser);
                        jsonObject.put("ph_no",tempph_no);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Thank You for registering.. Please Login", Toast.LENGTH_SHORT).show();
                            signin();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(objectRequest);
                }else{
                    Toast.makeText(getApplicationContext(),"Given passwords doesnot match", Toast.LENGTH_SHORT).show();
                }
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

    private void signin(){
        startActivity(new Intent(this,MainActivity.class));
    }
}
