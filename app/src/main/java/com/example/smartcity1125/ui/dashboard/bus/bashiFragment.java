package com.example.smartcity1125.ui.dashboard.bus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.smartcity1125.App;
import com.example.smartcity1125.R;
import com.example.smartcity1125.bean.busbean;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bashiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bashiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public bashiFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static bashiFragment newInstance(String param1, String param2) {
        bashiFragment fragment = new bashiFragment();
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
        View view=inflater.inflate(R.layout.fragment_severinit, container, false);
        List<Map<String, Object>> buslist = new ArrayList<Map<String, Object>>();
        Request request = new Request.Builder()
                .url("http://124.93.196.45:10091/prod-api/api/bus/line/list")
                .method("GET", null)
                .build();

        App.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                busbean busbean=new Gson().fromJson(response.body().string(), com.example.smartcity1125.bean.busbean.class);
                for (int i = 0; i < busbean.getTotal(); i++) {
                    Map<String,Object> map=new HashMap<>();
                    map.put("start",busbean.getRows().get(i).getFirst());
                    map.put("end",busbean.getRows().get(i).getEnd());
                    map.put("money","票价："+busbean.getRows().get(i).getPrice());
                    map.put("km","里程："+busbean.getRows().get(i).getMileage());
                    map.put("title",busbean.getRows().get(i).getName());
                    map.put("startTime","出发时间："+busbean.getRows().get(i).getStartTime());
                    map.put("endTime","到达时间："+busbean.getRows().get(i).getEndTime());
                    buslist.add(map);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),buslist,R.layout.busitem,
                                new String[]{"start","end","money","km","title","startTime","endTime"}
                                ,new int[]{R.id.busstart,R.id.busend,R.id.busmoney,R.id.buskm,R.id.busitem_name,
                                R.id.busstartTime,R.id.busendTime}){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view1=super.getView(position, convertView, parent);
                                view1.findViewById(R.id.busitem_name).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Navigation.findNavController(v).navigate(R.id.action_severinitFragment_to_businitFragment);
                                    }
                                });
                                return view1;
                            }
                        };
                      ListView listView= view.findViewById(R.id.buslist);
                      listView.setAdapter(simpleAdapter);
                    }
                });
            }
        });
        return view;
    }
}