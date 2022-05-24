package com.example.remedy

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import detection.DetectorActivity

public class BrakeFluidActivity : AppCompatActivity() {

    fun intentMaker(button: Button){
        val vib = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
        if (Build.VERSION.SDK_INT >= 26) {
            vib.vibrate(VibrationEffect.createOneShot(70, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vib.vibrate(200)
        }
        val mp = MediaPlayer.create(this, R.raw.sample)
        mp.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooling_fluid)

        val backButton = findViewById<Button>(R.id.windshield_washer_fluid_backButton)
        val carScanButton = findViewById<Button>(R.id.windshield_washer_fluid_carscan)

        backButton.setOnClickListener{
            intentMaker(backButton)
            finish()
        }
        carScanButton.setOnClickListener{
            intentMaker(carScanButton)
            val intent = Intent(this, DetectorActivity::class.java)
            startActivity(intent);
        }
    }
}