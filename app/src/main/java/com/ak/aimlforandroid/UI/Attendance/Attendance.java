package com.ak.aimlforandroid.UI.Attendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import com.ak.aimlforandroid.UI.AddFaceData.AddFaceData;
import com.ak.aimlforandroid.Untils.Constants;
import com.ak.aimlforandroid.Untils.FaceData;
import com.ak.aimlforandroid.Untils.FaceUntils;
import com.ak.aimlforandroid.Untils.Untils;
import com.ak.aimlforandroid.databinding.ActivityAttendanceBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.mlkit.vision.face.Face;

import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Attendance extends AppCompatActivity {

    private final String PATH = "mobile_face_net.tflite";
    private ActivityAttendanceBinding binding;
    private Bitmap imageRedult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bt.setOnClickListener(v->{
            startActivityIfNeeded(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        imageRedult = (Bitmap) extras.get("data");
        //binding.img.setImageBitmap(imageRedult);

    }
}