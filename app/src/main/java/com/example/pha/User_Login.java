package com.example.pha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_Login extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsername, editTextPassword;
    TextView textViewRegister;
    Button buttonSignUn, buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        editTextUsername = findViewById(R.id.inputUserName);
        editTextPassword = findViewById(R.id.inputPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        textViewRegister = findViewById(R.id.creatAccount);

        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
    }

    private void login(){
        final String username = editTextUsername.getText().toString().trim();
        final String userpass = editTextPassword.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.USER_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jO = new JSONObject(response);
                            boolean error = Boolean.parseBoolean(jO.getString("error"));

                            if(!error){
                                Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_SHORT).show();
                                Constants.U_ID = jO.getString("u_id");
                                Intent i = new Intent(User_Login.this,Bidding.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(getApplicationContext(), jO.getString("msg"),Toast.LENGTH_SHORT).show();
                            }




                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> para = new HashMap<>();
                para.put("username", username);
                para.put("userpass", userpass);

                return para;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void toRegisterIntent() {
        Intent intent = new Intent(this, User_Registration.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View view) {
        if(view == buttonLogin){
            login();
        }
        else if(view == textViewRegister){
            toRegisterIntent();
        }
    }


}