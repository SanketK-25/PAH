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

public class Bid_Placing extends AppCompatActivity implements View.OnClickListener
{
    TextView textViewPaintingname, textViewArtistname, textViewDescription;
    EditText editTextBidprice;
    Button buttonSubmit;
    String a_id,item_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_placing);

        textViewPaintingname = findViewById(R.id.paintingname);
        textViewArtistname = findViewById(R.id.artistname);
        textViewDescription = findViewById(R.id.description);

        editTextBidprice = findViewById(R.id.price);

        buttonSubmit = findViewById(R.id.submit);
        buttonSubmit.setOnClickListener(this);
//


        Intent i = getIntent();
        a_id = i.getStringExtra("a_id");
        item_id = i.getStringExtra("item_id");
//        Toast.makeText(getApplicationContext(), item_id,Toast.LENGTH_SHORT).show();
        setData();
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSubmit){
            place();
        }
    }

    private void place(){
        String bid_price = editTextBidprice.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.USER_BID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jO = new JSONObject(response);
                            boolean error = Boolean.parseBoolean(jO.getString("error"));

                            if(!error){
                                Toast.makeText(getApplicationContext(), "Bid Placed!!",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Bid_Placing.this,Bidding.class);
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
                para.put("u_id", Constants.U_ID );
                para.put("a_id", a_id);
                para.put("bid_price", bid_price);

                return para;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setData(){
        String bid_price = editTextBidprice.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.USER_GETIMAGEDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jO = new JSONObject(response);
                            boolean error = Boolean.parseBoolean(jO.getString("error"));

                            if(!error){
                                textViewPaintingname.setText(jO.getString("painting_name"));
                                textViewArtistname.setText(jO.getString("artist"));
                                textViewDescription.setText(jO.getString("painting_description"));

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
                para.put("item_id", item_id );


                return para;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}