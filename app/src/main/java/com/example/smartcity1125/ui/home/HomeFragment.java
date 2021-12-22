package com.example.smartcity1125.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.smartcity1125.App;
import com.example.smartcity1125.MainActivity;
import com.example.smartcity1125.R;
import com.example.smartcity1125.bean.bannerbean;
import com.example.smartcity1125.bean.severbean;
import com.example.smartcity1125.bean.ztbean;
import com.example.smartcity1125.databinding.FragmentHomeBinding;
import com.example.smartcity1125.ui.news.newsFragment;
import com.google.gson.Gson;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    List<Map<String, Object>> sim = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> sim2 = new ArrayList<Map<String, Object>>();

    SimpleAdapter simpleAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root= binding.getRoot();


        Request request = new Request.Builder()
                .url("http://124.93.196.45:10091/prod-api/api/rotation/list?pageNum=1&pageSize=8&type=2")
                .method("GET", null)
                .build();

        App.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                bannerbean bannerbean = new Gson().fromJson(response.body().string(), com.example.smartcity1125.bean.bannerbean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.mainbanner.setAdapter(new BannerImageAdapter<bannerbean.RowsBean>(bannerbean.getRows()) {
                            @Override
                            public void onBindView(BannerImageHolder bannerImageHolder, com.example.smartcity1125.bean.bannerbean.RowsBean rowsBean, int i, int i1) {
                                Glide.with(bannerImageHolder.imageView).load("http://124.93.196.45:10091" + rowsBean.getAdvImg()).into(bannerImageHolder.imageView);
                            }
                        }).setIndicator(new CircleIndicator(getActivity()));
                    }
                });
            }
        });
        mainserver();

        MainActivity.ss.observe(getViewLifecycleOwner(), new Observer<List<Map<String, Object>>>() {
            @Override
            public void onChanged(List<Map<String, Object>> maps) {
                simpleAdapter = new SimpleAdapter(getContext(), maps, R.layout.ztitem,
                        new String[]{"image", "content"}, new int[]{R.id.ztittv, R.id.ztitim});


                simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                        if (view instanceof ImageView) {
                            Glide.with(view).load("http://124.93.196.45:10091" + data).into((ImageView) view);
                            return true;
                        }
                        return false;
                    }
                });
                binding.ztnews.setAdapter(simpleAdapter);
            }
        });



        binding.ztnews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.x = (HashMap) parent.getItemAtPosition(position);
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_newsinit);
            }
        });
        return binding.getRoot();
    }

    private void mainserver() {
        Request request;
        request = new Request.Builder()
                .url("http://124.93.196.45:10091/prod-api/api/service/list")
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

                /*simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                        if (view instanceof ImageView) {
                            ImageView imageView = (ImageView) view;
                            Glide.with(imageView).load("http://124.93.196.45:10091" + data).into(imageView);
                            return true;
                        }
                        return false;
                    }
                });*/
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.mainsever.setAdapter(new SimpleAdapter(getActivity(), sim, R.layout.serveritem, new String[]{"image", "name"}, new int[]{R.id.svittv, R.id.svitim})
                        {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view= super.getView(position, convertView, parent);
                                ImageView imageView =  view.findViewById(R.id.svittv);
                                Glide.with(imageView).load("http://124.93.196.45:10091" + sim.get(position).get("image")).into(imageView);
                                return view;
                            }
                        });
                        binding.mainsever.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (((HashMap) parent.getItemAtPosition(position)).get("name").equals("智慧巴士")) {
                                    Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_severinitFragment);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().
                beginTransaction().
                add(R.id.mainnewsfl,new newsFragment()).
                commitAllowingStateLoss();
    }
}