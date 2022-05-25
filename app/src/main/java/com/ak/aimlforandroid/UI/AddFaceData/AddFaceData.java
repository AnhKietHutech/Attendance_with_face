package com.ak.aimlforandroid.UI.AddFaceData;

import static com.ak.aimlforandroid.Untils.Untils.getCropBitmapByCPU;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.ak.aimlforandroid.Untils.Constants;
import com.ak.aimlforandroid.Untils.FaceModel;
import com.ak.aimlforandroid.Untils.FaceUntils;
import com.ak.aimlforandroid.Untils.Untils;
import com.ak.aimlforandroid.databinding.ActivityAddFaceDataBinding;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.List;

public class AddFaceData extends AppCompatActivity {

    private ActivityAddFaceDataBinding binding;
    private FaceUntils faceUntils;
    private Bitmap imageRedult;
    private final String PATH = "mobile_face_net.tflite";
    private MappedByteBuffer file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faceUntils = new FaceUntils(new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build());

        binding = ActivityAddFaceDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            file = Untils.loadModelFile(AddFaceData.this,PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        binding.button.setOnClickListener(v->{
            startActivityIfNeeded(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),1);
        });

        binding.add.setOnClickListener(v -> {
            if (imageRedult!=null){
                faceUntils.FaceDetector(imageRedult, new FaceUntils.FaceDetectorEvent() {
                    @Override
                    public void OnSuccess(List<Face> faces) {
                        //Bitmap b = new FaceModel(AddFaceData.this).cropRectFromBitmap(imageRedult,faces.get(0).getBoundingBox(),false);
                        Bitmap bitmap = Bitmap.createBitmap(imageRedult);
                        FaceModel faceNetModel = new FaceModel(AddFaceData.this);
                        float[] em = faceNetModel.getFaceEmbedding(imageRedult,faces.get(0).getBoundingBox(),false);
                        binding.faceView.setImageBitmap(faceNetModel.cropRectFromBitmap(bitmap,faces.get(0).getBoundingBox(),false));
                        Constants.USER_DB.child(Constants.AUTH.getCurrentUser().getUid()).child("face_data").setValue(Untils.toArray(em));

                    }

                    @Override
                    public void OnFailure(Exception e) {

                    }

                    @Override
                    public void onComplete(Task<List<Face>> task) {

                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        imageRedult = (Bitmap) extras.get("data");
        binding.image.setImageBitmap(imageRedult);

    }
}