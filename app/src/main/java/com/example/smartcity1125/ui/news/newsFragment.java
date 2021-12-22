package com.example.smartcity1125.ui.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;
import com.example.smartcity1125.App;
import com.example.smartcity1125.MainActivity;
import com.example.smartcity1125.R;
import com.example.smartcity1125.bean.severbean;
import com.example.smartcity1125.bean.ztbean;
import com.example.smartcity1125.ui.home.HomeFragment;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MutableLiveData<List<Map<String, Object>>>  newslist = new MutableLiveData<>();

    public newsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment news.
     */
    // TODO: Rename and change types and number of parameters
    public static newsFragment newInstance(String param1, String param2) {
        newsFragment fragment = new newsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        ListView listView = view.findViewById(R.id.newslist);
        extracted(listView, "9");
//9 17 19 20 21 22
        view.findViewById(R.id.newsfl1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                extracted(listView, "9");
            }
        });
        view.findViewById(R.id.newsfl2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                extracted(listView, "17");
            }
        });
        view.findViewById(R.id.newsfl3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extracted(listView, "19");
            }
        });
        view.findViewById(R.id.newsfl4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extracted(listView, "20");
            }
        });
        view.findViewById(R.id.newsfl5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extracted(listView, "21");
            }
        });
        view.findViewById(R.id.newsfl6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extracted(listView, "22");
            }
        });

        newslist.observe(getViewLifecycleOwner(), new Observer<List<Map<String, Object>>>() {
            @Override
            public void onChanged(List<Map<String, Object>> maps) {
                SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), newslist.getValue(), R.layout.newsitem, new String[]{"image", "content", "title"}, new int[]{R.id.newsim, R.id.newsittv1, R.id.newsittv2});
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
                listView.setAdapter(simpleAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MainActivity.x = (HashMap) parent.getItemAtPosition(position);
                        //
                        if (getParentFragment() instanceof HomeFragment){
                            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_newsinit);
                        }else {
                            Navigation.findNavController(view).navigate(R.id.action_nav_news_to_newsinit);
                        }
                    }
                });
            }
        });
        return view;
    }

    private void extracted(ListView listView, String s) {

        Request request = new Request.Builder()
                .url("http://124.93.196.45:10091/prod-api/press/press/list?type=" + s)
                .method("GET", null)
                .build();
        App.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ztbean ztbean = new Gson().fromJson(response.body().string(), com.example.smartcity1125.bean.ztbean.class);

                List<Map<String, Object>> ad=new ArrayList<>();
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < ztbean.getTotal(); i++) {
                            Map<String, Object> map = new HashMap();
                            map.put("image", ztbean.getRows().get(i).getCover());
                            map.put("content", "    " + Html.fromHtml(ztbean.getRows().get(i).getContent()));
                            map.put("title", ztbean.getRows().get(i).getTitle());
                            ad.add(map);
                            newslist.setValue(ad);
                        }
                    }
                });

            }
        });
    }
}