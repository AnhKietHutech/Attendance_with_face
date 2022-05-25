package com.ak.aimlforandroid.UI.Classroom.ui.home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ak.aimlforandroid.UI.Models.AttendanceRoom;
import com.ak.aimlforandroid.Untils.Constants;
import com.ak.aimlforandroid.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String classID;
    private DatabaseReference data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);


        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onResume() {
        classID = getArguments().getString("ClassID");
        Log.e("classID",""+(classID!=null));
        if (classID!=null){
            data = Constants.CLASSROOM_DB.child(classID).child(Constants.ATTEN_ROOM);
            data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    AttendanceRoom attendanceRoom = snapshot.getValue(AttendanceRoom.class);
                    if (binding != null)
                        if (attendanceRoom != null) {
                            binding.cardV.setVisibility(View.VISIBLE);
                            binding.className.setText(attendanceRoom.getLop());
                            binding.time.setText(attendanceRoom.getTime());
                        } else
                            binding.cardV.setVisibility(View.GONE);
                    else data.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}