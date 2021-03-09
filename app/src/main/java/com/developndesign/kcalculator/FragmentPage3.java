package com.developndesign.kcalculator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developndesign.firebaseautomlvisionedge.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentPage3 extends androidx.fragment.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_page_3, container, false);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        ArrayList<Object> arrayList = new ArrayList<>();
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();

//        databaseReference = database.getReference("ShowKcal");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //파이어베이스 데이터베이스 받기
//                arrayList.clear();//배열리스트 초기화
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    ShowKcal showKcal = snapshot.getValue(ShowKcal.class);
//                    arrayList.add(showKcal);
//                }
//                adapter.notifyDataSetChanged();

        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
