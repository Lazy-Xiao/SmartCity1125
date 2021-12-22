package com.example.smartcity1125.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.smartcity1125.App;
import com.example.smartcity1125.MainActivity;
import com.example.smartcity1125.R;
import com.example.smartcity1125.bean.severbean;
import com.example.smartcity1125.databinding.FragmentDashboardBinding;
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

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    List<Map<String, Object>> sim = new ArrayList<Map<String, Object>>();

    SimpleAdapter simpleAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        extracted("车主服务");
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extracted("车主服务");
            }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extracted("生活服务");
            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                extracted("便民服务");
            }
        });
        return root;
    }

    private void extracted(String s) {
        sim.clear();
        Request request = new Request.Builder()
                .url("http://124.93.196.45:10091/prod-api/api/service/list?serviceType="+s)
                .method("GET", null)
                .build();
        App.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                severbean severbean = new Gson().fromJson(response.body().string(), com.example.smartcity1125.bean.severbean.class);
                for (int i = 0; i < severbean.getTotal(); i++) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("image", severbean.getRows().get(i).getImgUrl());
                    map.put("name", severbean.getRows().get(i).getServiceName());
                    sim.add(map);
                }
                simpleAdapter = new SimpleAdapter(getActivity(), sim, R.layout.serveritem, new String[]{"image", "name"}, new int[]{R.id.svittv, R.id.svitim});
                simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                        if (view instanceof ImageView) {
                            ImageView imageView= (ImageView) view;
                            Glide.with(imageView).load("http://124.93.196.45:10091"+data).into(imageView);
                            return true;
                        }
                        return false;
                    }
                });

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.severlist.setAdapter(simpleAdapter);
                        binding.severlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (((HashMap) parent.getItemAtPosition(position)).get("name").equals("智慧巴士")) {
                                    Navigation.findNavController(view).navigate(R.id.action_navigation_dashboard_to_severinitFragment);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}