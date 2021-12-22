package com.example.smartcity1125.ui.me;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartcity1125.App;
import com.example.smartcity1125.MainActivity;
import com.example.smartcity1125.R;
import com.example.smartcity1125.bean.xinxibean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link meFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class meFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public meFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static meFragment newInstance(String param1, String param2) {
        meFragment fragment = new meFragment();
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
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ImageView imageView=view.findViewById(R.id.meimage);
        TextView textView=view.findViewById(R.id.username);
        MainActivity.xinxi.observe(getViewLifecycleOwner(), new Observer<xinxibean>() {
            @Override
            public void onChanged(xinxibean xinxibean) {
                textView.setText(xinxibean.getUser().getNickName());
                if (xinxibean.getUser().getAvatar().equals("")){
                    Glide.with(imageView).load(R.mipmap.touxiang).into(imageView);
                }else{
                    Glide.with(imageView).load(xinxibean.getUser().getAvatar()).into(imageView);
                }
            }
        });



        view.findViewById(R.id.mebt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_me_to_xinxiFragment);
            }
        });
        view.findViewById(R.id.mebt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.findViewById(R.id.mebt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.findViewById(R.id.mebt4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_me_to_yijianFragment);

            }
        });

        return view;
    }
}