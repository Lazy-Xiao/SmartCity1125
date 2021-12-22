package com.example.smartcity1125.ui.me;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartcity1125.App;
import com.example.smartcity1125.MainActivity;
import com.example.smartcity1125.R;
import com.example.smartcity1125.bean.xinxibean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link xinxiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class xinxiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public xinxiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment xinxiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static xinxiFragment newInstance(String param1, String param2) {
        xinxiFragment fragment = new xinxiFragment();
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
     View view=   inflater.inflate(R.layout.fragment_xinxi, container, false);
        ImageView imageView=view.findViewById(R.id.xinxied1);
        EditText editText1=view.findViewById(R.id.xinxied2);
        EditText editText2=view.findViewById(R.id.xinxied3);
        EditText editText3=view.findViewById(R.id.xinxied4);
        EditText editText4=view.findViewById(R.id.xinxied5);

        editText1.setText(MainActivity.xinxi.getValue().getUser().getNickName());
        if (MainActivity.xinxi.getValue().getUser().getSex() .equals("0")) {
            editText2.setText("男");
        }else {
            editText2.setText("女");
        }
        editText3.setText(String.valueOf(MainActivity.xinxi.getValue().getUser().getIdCard()));
        editText4.setText(String.valueOf(MainActivity.xinxi.getValue().getUser().getPhonenumber()));

        view.findViewById(R.id.xinxibt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.xinxi.postValue(new xinxibean(new xinxibean.UserBean(editText1.getText().toString(),editText4.getText().toString(),editText2.getText().toString(),"",editText3.getText().toString())));
            }
        });
        if (MainActivity.xinxi.getValue().getUser().getAvatar().equals("")){
            Glide.with(imageView).load(R.mipmap.touxiang).into(imageView);
        }else{
            Glide.with(imageView).load(MainActivity.xinxi.getValue().getUser().getAvatar()).into(imageView);
        }

        return view;
    }
}