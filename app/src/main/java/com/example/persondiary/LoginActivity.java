package com.example.persondiary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtUsername;
    EditText edtPassword;
    User user;
    Connection con;
    String usernameDb, pw, dbName, ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        ip="databaseserver19.database.windows.net:1433";
        dbName="PersonDiary";
        usernameDb = "phungnguyen";
        pw = "LoginMyDB19";


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        });
    }

    public  class CheckLogin extends AsyncTask<String, String, String> {
        boolean loginSuccess=false;
        String tmp ="";
        @Override
        protected void onPostExecute(String s) {
            if(loginSuccess){
                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // get object User => Truyền Object qua Intent

                intent.putExtra("USER", user);

                startActivity(intent);

            }
        }

        @Override



        protected String doInBackground(String... strings) {
            String username =edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            if(username.trim().equals("") || password.trim().equals("")){
                tmp="Please enter usernanme or password";
            }
            else{
                try{
                    con = connectionClass(usernameDb, pw,dbName,ip);
                    if(con==null){
                        tmp = "Connection Db Fail!";
                    }
                    else{
                        String sql="SELECT * FROM Users WHERE userName = '" + username +"' AND password='"+password+"'";
                        Statement statement = con.createStatement();
                        ResultSet rs  =statement.executeQuery(sql);

                        if(rs.next()){
                            tmp="Login Successfull";
                            loginSuccess=true;
                            int userId = rs.getInt("userId");
                            String name= rs.getString("userName").toString();
                            String pw =rs.getString("password").toString();
                            String gender =rs.getString("gender").toString();
                             user = new User(userId,name,pw,gender);
                            con.close();
                            return tmp;
                        }
                        tmp="Invalid username or password";
                        loginSuccess = false;
                        
                    }
                }
                catch (Exception e){
                    loginSuccess=false;
                    tmp = e.getMessage();
                }
            }
            return tmp;




        }

    }
        @SuppressLint("NewApi")
        public Connection connectionClass(String user, String password, String db, String server){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection connection = null;
            String connectionUrl = null;
            try{
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                connectionUrl = "jdbc:jtds:sqlserver://" + server+";databaseName=" + db + ";user="+user+";password="+password+";Encrypt=True;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
//                String User="phungnguyen";
//                String pw="LoginMyDB19";
//                connectionUrl = "jdbc:sqlserver://"+server+";databaseName="+db;
                connection = DriverManager.getConnection(connectionUrl, user, password);
            }
            catch (SQLException e){
                Log.e(" error sql : ",e.getMessage());
            }
            catch (ClassNotFoundException e){
                Log.e("Class Not Found Ex: ",e.getMessage());
            }
            catch (Exception e){
                Log.e("Exception" ,e.getMessage());
            }
            return connection;
        }
}
