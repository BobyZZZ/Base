package com.ist.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFragment extends Fragment {

    private String name;

    public String getName() {
        return name;
    }

    public static MyFragment newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        MyFragment myFragment = new MyFragment();
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name", "name为空");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = content.findViewById(R.id.textView);
        textView.setText(name);
        return content;
    }
}
