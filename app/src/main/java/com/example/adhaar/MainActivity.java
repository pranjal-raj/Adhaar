package com.example.adhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView result;
    EditText editText;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.result);
        Button b = findViewById(R.id.button);
        EditText an = findViewById(R.id.an);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        b.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String aadhaar = an.getText().toString();
                tv.setText("");
                an.setText("");

                JSONObject jb = new JSONObject();
                try {
                    jb.put("aadhaar", aadhaar);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://api.apyhub.com/validate/aadhaar", jb, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response==null)
                        {
                            Toast.makeText(MainActivity.this, "Error3", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            tv.setText(response.getString("data"));
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error1 : "+ error, Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        // below line we are creating a map for
                        // storing our values in key and value pair.
                        Map<String, String> params = new HashMap<String, String>();

                        // on below line we are passing our key
                        // and value pair to our parameters.
                        params.put("Content-type","application/json");
                        params.put("apy-token", "APY0kSVxDHNY1k1AveWKIlPVxAQPIZh93t24I8X8p9Y32Tyo76sCsnTCsFbyroRlyB0tfEz");
                        params.put("aadhaar", "461023573455");


                        // at last we are
                        // returning our params.
                        return params;
                    }

                    @Override
                    public byte[] getBody() {
                        return super.getBody();

                    }
                };
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}