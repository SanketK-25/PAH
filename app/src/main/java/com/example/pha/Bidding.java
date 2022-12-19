package com.example.pha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Bidding extends AppCompatActivity {
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        while(true) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bidding);

            layout = findViewById(R.id.scrollLinerLayout);
            upload();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId()){
             case R.id.place_auction:
                 Intent i = new Intent(Bidding.this,PlaceAuction.class);
                 startActivity(i);
                 break;
             case R.id.winning:
                 Intent i1 = new Intent(Bidding.this,won_auction.class);
                 startActivity(i1);
                 break;
         }
        return super.onOptionsItemSelected(item);
    }

    public void creatCard(Bitmap imageMap, int a_id, int item_id){
        Button button = new Button(getApplicationContext());
        LinearLayout ll = new LinearLayout(getApplicationContext());
        CardView card = new CardView(getApplicationContext());
        card.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) card.getLayoutParams();
        cardViewMarginParams.setMargins(10, 10, 10, 10);
        card.requestLayout();

        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));


        ImageView imageView = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                500);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageBitmap(imageMap);



        button.setText("BID");
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Bidding.this,Bid_Placing.class);
                i.putExtra("a_id", ""+a_id);
                i.putExtra("item_id", ""+item_id);
                startActivity(i);
            }
        });


        if(imageView.getParent() != null) {
            ((ViewGroup)imageView.getParent()).removeView(imageView);
        }

        ll.addView(imageView);
        ll.addView(button);
        card.addView(ll);
        layout.addView(card);

    }

    private Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                encodeByte = Base64.getDecoder().decode(encodedString);
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void upload(){

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.USER_GETAUCTIONDATA,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {

                        try {

                            JSONArray jO = new JSONArray(response);
                            for(int i=0;i<jO.length();i++){
                                JSONObject object = jO.getJSONObject(i);

                                Bitmap bmap = StringToBitMap(object.getString("image_data"));
                                int a_id = Integer.parseInt(object.getString("a_id"));
                                int item_id = Integer.parseInt(object.getString("item_id"));
                                creatCard(bmap,a_id,item_id);
                            }



                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
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
//                String idata = imageToString(bmap);
                para.put("image_name", "username");
//                para.put("image", idata);

                return para;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}