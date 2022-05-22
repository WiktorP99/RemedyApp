package com.example.remedy
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import detection.CameraActivity

class CoolingFluidScanning : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_cooling_fluid_scanning)

        val intent = Intent(this, CameraActivity::class.java);
        startActivity(intent);

    }
}