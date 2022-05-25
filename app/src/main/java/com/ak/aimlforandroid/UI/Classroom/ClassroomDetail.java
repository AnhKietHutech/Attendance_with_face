package com.ak.aimlforandroid.UI.Classroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ak.aimlforandroid.R;
import com.ak.aimlforandroid.UI.AddClass.AddClass;
import com.ak.aimlforandroid.UI.Classroom.CreateAttendanceRoom.CreateAttendance;
import com.ak.aimlforandroid.UI.Classroom.ui.home.HomeFragment;
import com.ak.aimlforandroid.UI.JoinClassroom.JoinClassroom;
import com.ak.aimlforandroid.UI.Models.Classroom;
import com.ak.aimlforandroid.Untils.Constants;
import com.ak.aimlforandroid.databinding.ActivityClassroomDetailBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class ClassroomDetail extends AppCompatActivity {

    private ActivityClassroomDetailBinding binding;
    private BottomSheetDialog bottomSheet;
    private String classID;
    private Classroom classroom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classID = getIntent().getStringExtra("ClassID");
        binding = ActivityClassroomDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = new Bundle();
        bundle.putString("ClassID",classID);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_classroom_detail);
        navController.navigate(R.id.navigation_home,bundle);
        NavigationUI.setupWithNavController(binding.navView, navController);


        unitUI();
        cickEvent();

    }

    private void unitUI() {
        bottomSheet = new BottomSheetDialog(this);


    }

    private void cickEvent() {
        binding.addBT.setOnClickListener(v -> {
            showBottomSheet();
        });
    }

    private void showBottomSheet(){
        bottomSheet.setContentView(R.layout.classroom_bottom_sheet);
        bottomSheet.show();
        //
        LinearLayout attendance = bottomSheet.findViewById(R.id.attendance);
        LinearLayout addPost = bottomSheet.findViewById(R.id.add_post);
        //
//        if (user.getLoaiTaiKhoan().equals("Student")){
//            addClass.setVisibility(View.GONE);
//        }
        //click event
        attendance.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateAttendance.class);
            intent.putExtra("ClassID",classID);
            startActivity(intent);
            bottomSheet.hide();
        });



        addPost.setOnClickListener(v->{
            startActivity(new Intent(this, JoinClassroom.class));
            bottomSheet.hide();
        });
    }

}