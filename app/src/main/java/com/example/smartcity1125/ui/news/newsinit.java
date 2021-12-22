package com.example.smartcity1125.ui.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartcity1125.MainActivity;
import com.example.smartcity1125.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newsinit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newsinit extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public newsinit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment newsinit.
     */
    // TODO: Rename and change types and number of parameters
    public static newsinit newInstance(String param1, String param2) {
        newsinit fragment = new newsinit();
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

        View view= inflater.inflate(R.layout.fragment_newsinit, container, false);
//        Toast.makeText(getContext(),MainActivity.x, Toast.LENGTH_SHORT).show();
        TextView tv = view.findViewById(R.id.newsinittv1);
        TextView tv2 = view.findViewById(R.id.newsinittv2);
        ImageView imageView = view.findViewById(R.id.newsinitim);
        Glide.with(imageView).load("http://124.93.196.45:10091"+(String) MainActivity.x.get("image")).into(imageView);
        tv.setText((String) MainActivity.x.get("title"));
        tv2.setText((String) MainActivity.x.get("content"));
        return view;
    }
}