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

class CoolingFluidActivity : AppCompatActivity() {

    private fun intentMaker(button: Button, classs: Class<*>?){
        val intent = Intent(this, classs )
        val vib = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
        if (Build.VERSION.SDK_INT >= 26) {
            vib.vibrate(VibrationEffect.createOneShot(70, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vib.vibrate(200)
        }
        val mp = MediaPlayer.create(this, R.raw.sample)
        mp.start()
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooling_fluid)

        val backButton = findViewById<Button>(R.id.cooling_fluid_backButton)
        val carScanButton = findViewById<Button>(R.id.cooling_fluid_carscan)

        backButton.setOnClickListener{
            intentMaker(backButton, CarScanActivity::class.java)
        }
        carScanButton.setOnClickListener{
            intentMaker(carScanButton, CoolingFluidScanning::class.java)
        }
    }
}