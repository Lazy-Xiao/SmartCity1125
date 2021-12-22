package com.example.smartcity1125;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartcity1125.bean.loginbean;
import com.example.smartcity1125.databinding.ActivityLoginBinding;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText editText = findViewById(R.id.logined1);
        EditText editText2 = findViewById(R.id.logined2);
        findViewById(R.id.loginbt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\r\n\"username\":\"" + editText.getText() + "\",\r\n\"password\":\"" + editText2.getText() + "\"\r\n}");

                Request request = new Request.Builder()
                        .url("http://124.93.196.45:10091/prod-api/api/login")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                App.client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        loginbean loginbean = new Gson().fromJson(response.body().string(), com.example.smartcity1125.bean.loginbean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (loginbean.getCode() == 200) {
                                    App.token = loginbean.getToken();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else if (loginbean.getCode()==500){
                                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

    }
}