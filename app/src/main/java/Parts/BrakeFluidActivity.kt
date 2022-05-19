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

public class BrakeFluidActivity : AppCompatActivity() {

    fun intentMaker(button: Button, classs: Class<*>?){
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
        setContentView(R.layout.activity_brake_fluid)

        val backButton = findViewById<Button>(R.id.brake_fluid_backButton)

        backButton.setOnClickListener{
            finish()
        }
    }
}