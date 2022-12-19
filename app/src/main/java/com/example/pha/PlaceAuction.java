package com.example.pha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PlaceAuction extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    EditText editTextDuration, editTextBaseprice, editTextPaintingname, editTextArtistname, editTextDescription;
    Button buttonSubmit;
    Bitmap bmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_auction);

        iv = findViewById(R.id.selected_image);
        editTextDuration = findViewById(R.id.duration);
        editTextArtistname = findViewById(R.id.artistname);
        editTextPaintingname = findViewById(R.id.paintingName);
        editTextBaseprice = findViewById(R.id.baseprice);
        editTextDescription = findViewById(R.id.description);

        buttonSubmit = findViewById(R.id.submit);

        buttonSubmit.setOnClickListener(this);
        ;
        iv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == iv) {
            Toast.makeText(getApplicationContext(), "ky", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(PlaceAuction.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);
        }
        if(view == buttonSubmit){
            upload();
            Intent intent = new Intent(this, Bidding.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 999) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "Select Image"), 999);

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 999 && resultCode == RESULT_OK && data != null) {
            Uri filepath = data.getData();
            try {
                InputStream in = getContentResolver().openInputStream(filepath);
                bmap = BitmapFactory.decodeStream(in);
                iv.setImageBitmap(bmap);
                iv.setBackground(null);
            } catch (Exception e) {

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void upload() {

        String artistName = editTextArtistname.getText().toString().trim();
        String paintingName = editTextPaintingname.getText().toString().trim();
        String duration = editTextDuration.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String basePrice = editTextBaseprice.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.USER_UPLOAD,
                new Response.Listener<String>() {
                    @Override


                    public void onResponse(String response) {


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> para = new HashMap<>();
                String idata = imageToString(bmap);
                para.put("painting_name", paintingName );
                para.put("artist_name", artistName );
                para.put("duration", duration );
                para.put("description", description );
                para.put("base_price", basePrice );
                para.put("u_id", Constants.U_ID );
                para.put("image", idata);

                return para;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private String imageToString(Bitmap bm) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, os);
        byte[] imageByte = os.toByteArray();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(imageByte);
        }
        return null;
    }
}