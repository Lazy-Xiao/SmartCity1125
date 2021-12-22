package com.example.smartcity1125;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;
import com.example.smartcity1125.bean.xinxibean;
import com.example.smartcity1125.bean.ztbean;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.smartcity1125.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    List<Map<String, Object>> sim2 = new ArrayList<Map<String, Object>>();
    public static MutableLiveData<List<Map<String, Object>>> ss = new MutableLiveData<>();
    public static MutableLiveData<xinxibean>  xinxi=new MutableLiveData<>();
    public static HashMap x= new HashMap<>();
    public static HashMap severx= new HashMap<>();
    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Request request = new Request.Builder()
                .url("http://124.93.196.45:10091/prod-api/press/press/list")
                .method("GET", null)
                .build();
        App.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ztbean ztbean = new Gson().fromJson(response.body().string(), com.example.smartcity1125.bean.ztbean.class);

                for (int i = 0; i < 4; i++) {
                    Map<String, Object> map = new HashMap();
                    map.put("image", ztbean.getRows().get(i).getCover());
                    map.put("content", "    " + Html.fromHtml(ztbean.getRows().get(i).getContent()));
                    map.put("title", ztbean.getRows().get(i).getTitle());

                    sim2.add(map);
                }
                ss.postValue(sim2);
            }
        });


         request = new Request.Builder()
                .url("http://124.93.196.45:10091/prod-api/api/common/user/getInfo")
                .method("GET", null)
                .addHeader("Authorization", App.token)
                .build();
         App.client.newCall(request).enqueue(new Callback() {
             @Override
             public void onFailure(@NonNull Call call, @NonNull IOException e) {
             }

             @Override
             public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                 xinxi.postValue(new Gson().fromJson(response.body().string(), xinxibean.class));
             }
         });



        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
         appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.nav_news, R.id.nav_me)
                .build();
         navController = Navigation.findNavController(MainActivity.this, R.id.navmain);
        NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}