package com.example.persondiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.sourceforge.jtds.jdbc.DateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CreateDiary extends AppCompatActivity {
    TextView txtDateTime;
    EditText edtTitle;
    EditText edtContent;
    Button btnCancel, btnSave;
    User user;
    String userId;
    String dateTime, title, content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);
        txtDateTime = findViewById(R.id.txtDateTime);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        //System.out.println(dtf.format(now));
        txtDateTime.setText(dtf.format(now));

        user = (User) getIntent().getSerializableExtra("USER");
        userId =  String.valueOf(user.getUserId());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTitle.getText().toString().trim().equals("")){
                    Toast.makeText(CreateDiary.this, "Enter Your Title", Toast.LENGTH_LONG).show();
                    return;
                }
                if(edtContent.getText().toString().trim().equals("")){
                    Toast.makeText(CreateDiary.this, "Content can not empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                 dateTime = txtDateTime.getText().toString();
                 title = edtTitle.getText().toString();
                 content=edtContent.getText().toString();
                postRequest();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);

            }
        });
    }
    public void postRequest(){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(CreateDiary.this);
            String url="https://persondiaryapi20200625032017.azurewebsites.net/api/Diaries";
            JSONObject jsonBody  = new JSONObject();
            jsonBody.put("title",title);
            jsonBody.put("content",content);
            jsonBody.put("status","enable");
            jsonBody.put("dateTime",dateTime);
            jsonBody.put("description","description");
            jsonBody.put("userId",userId);
            final String requestBody = jsonBody.toString();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(CreateDiary.this, "Added Diary!", Toast.LENGTH_LONG).show();
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CreateDiary.this, "Error to add diary!", Toast.LENGTH_LONG).show();
                    Log.i("VOLLEY", error.toString());
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);
        }
        catch (JSONException e){
            e.printStackTrace();
        }


    }
}