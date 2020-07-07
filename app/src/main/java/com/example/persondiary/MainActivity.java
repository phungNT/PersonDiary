package com.example.persondiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DiaryAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private DiaryAdapter diaryAdapter;
    private ArrayList<Diary> diaries;
    private RequestQueue requestQueue;
    FloatingActionButton btnCreate;
    User user;
    String userId;
    String diaryID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCreate = findViewById(R.id.btnCreate);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diaries = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        user = (User) getIntent().getSerializableExtra("USER");
        userId =  String.valueOf(user.getUserId());
        parseJson();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateDiary.class).putExtra("USER", user);
                startActivity(intent);
            }
        });


    }
    private void parseJson(){
        String url="https://persondiaryapi20200625032017.azurewebsites.net/api/Users/"+userId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            JSONArray jsonArray = response.getJSONArray("diarys");
                                            for (int i=0; i<jsonArray.length(); i++) {
                                                JSONObject diary = jsonArray.getJSONObject(i);
                                                String dateTime = diary.getString("dateTime").toString();
                                                String title = diary.getString("title");
                                                 int diaryId = diary.getInt("diaryId");
                                                 String content =  diary.getString("content");
                                                 String status =  diary.getString("status");
                                                 String description =  diary.getString("description");
                                                  int userId = diary.getInt("userId");
                                                diaries.add(new Diary(diaryId, title,content, status, dateTime, description,userId));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        diaryAdapter = new DiaryAdapter(MainActivity.this, diaries);
                                        recyclerView.setAdapter(diaryAdapter);
                                        diaryAdapter.setOnItemClickListener(MainActivity.this);
                                    }
                                    } ,new Response.ErrorListener(){
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                        });
        requestQueue.add(request);
        };

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ViewDetailDiary.class);
        Diary clickItem = diaries.get(position);
        intent.putExtra("USER", user);
        intent.putExtra("DIARY", clickItem);
        startActivity(intent);
    }
}