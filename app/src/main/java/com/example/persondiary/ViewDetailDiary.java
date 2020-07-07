package com.example.persondiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ViewDetailDiary extends AppCompatActivity {
    TextView txtDateTime;
    EditText edtTitle, edtContent;
    Button btnEdit, btnDelete;
    Diary diary;
    User user;
    String userId;
    String dateTime, title, content;
    String diaryId;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_diary);
        txtDateTime = findViewById(R.id.txtDateTime);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        requestQueue = Volley.newRequestQueue(this);

        diary = (Diary) getIntent().getSerializableExtra("DIARY");
        diaryId =String.valueOf(diary.getDiaryId());
        user = (User) getIntent().getSerializableExtra("USER");
        txtDateTime.setText(diary.getDateTime());
        edtTitle.setText(diary.getTitle()) ;
        edtContent.setText(diary.getContent());
        userId =  String.valueOf(user.getUserId());
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTime = txtDateTime.getText().toString();
                title = edtTitle.getText().toString();
                content= edtContent.getText().toString();
                putRequest();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(ViewDetailDiary.this);
                alertDialogBuilder.setTitle("Delete")
                        .setMessage("Do you want to delete this diary?")
                        .setPositiveButton("No", null)
                        .setNegativeButton("Yes", null)
                        .show();
                alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("USER", user);
                        startActivity(intent);
                    }
                });
                alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ViewDetailDiary.this, "Deleting your diary", Toast.LENGTH_LONG).show();
                        deleteRequest();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("USER", user);
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
    public void putRequest(){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(ViewDetailDiary.this);
            String url="https://persondiaryapi20200625032017.azurewebsites.net/api/Diaries/"+diaryId;
            final JSONObject jsonBody  = new JSONObject();
            jsonBody.put("title",title);
            jsonBody.put("content",content);
            jsonBody.put("status","enable");
            jsonBody.put("dateTime",dateTime);
            jsonBody.put("description","description");
            jsonBody.put("userId",userId);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(ViewDetailDiary.this, "Edit Diary Successful!", Toast.LENGTH_LONG).show();
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ViewDetailDiary.this, "Error to edit diary!", Toast.LENGTH_LONG).show();
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
    public void deleteRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(ViewDetailDiary.this);
        String url="https://persondiaryapi20200625032017.azurewebsites.net/api/Diaries/"+diaryId;
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ViewDetailDiary.this, "Deleted diary!", Toast.LENGTH_LONG).show();
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewDetailDiary.this, "Error to delete diary!", Toast.LENGTH_LONG).show();
                Log.i("VOLLEY", error.toString());
            }
        });
        requestQueue.add(deleteRequest);

    }
}