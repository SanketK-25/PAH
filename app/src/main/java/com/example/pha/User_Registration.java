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

public class User_Registration extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsername, editTextPassword, editTextFullname, editTextContact, editTextAddress;
    Button buttonSignup;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        editTextUsername = findViewById(R.id.inputUserName);
        editTextPassword = findViewById(R.id.inputPassword);
        editTextFullname = findViewById(R.id.inputName);
        editTextContact = findViewById(R.id.inputNumber);
        editTextAddress = findViewById(R.id.inputEmail);

        buttonSignup = findViewById(R.id.creatAccount);

        login = findViewById(R.id.textViewLogin);

        buttonSignup.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignup){
            register();
        }
        if(view == login){
            Intent intent = new Intent(this, User_Login.class);
            startActivity(intent);
            finish();
        }
    }

    private void register() {
        final String username = editTextUsername.getText().toString().trim();
        final String userpass = editTextPassword.getText().toString().trim();
        final String fullname = editTextFullname.getText().toString().trim();
        final String contact = editTextContact.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.USER_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "error",Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jO = new JSONObject(response);
                            boolean error = Boolean.parseBoolean(jO.getString("error"));

                            if(!error){
                                Constants.U_ID = jO.getString("u_id");
                                Toast.makeText(getApplicationContext(), "Registered Successfully",Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(User_Registration.this,Bidding.class);
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
                para.put("name", fullname);
                para.put("contact", contact);
                para.put("address", address);

                return para;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}