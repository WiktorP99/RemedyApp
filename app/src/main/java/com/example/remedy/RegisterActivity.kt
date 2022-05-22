package com.example.remedy

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi

class RegisterActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val backButton = findViewById<Button>(R.id.arcore_backButton)
        val text = findViewById<TextView>(R.id.arcore_workInProgress_textView)
        val mp = MediaPlayer.create(this, R.raw.sample)
        text.setText("REGISTER, WORK IN PROGRESS").toString()
        val textViewValue = text.text
        val vib = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
        backButton.setOnClickListener()
        {
            vib.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            mp.start()
            finish()
        }

    }
}