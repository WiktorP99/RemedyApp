package com.example.remedy

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_car_scan.*

class CarScanActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var textView: TextView

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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_scan)

        val brakeFluidButton = findViewById<Button>(R.id.Brake_fluid_btn)
        val oilLevelButton = findViewById<Button>(R.id.Oil_level_btn)
        val windshieldWasherFluidButton = findViewById<Button>(R.id.windshield_washer_fluid_btn)
        val oilRefillButton = findViewById<Button>(R.id.Oil_refill_btn)
        val coolingFluidButton = findViewById<Button>(R.id.Cooling_fluid_btn)
        val backButton = findViewById<Button>(R.id.carScan_backButton)

        brakeFluidButton.setOnClickListener{
            intentMaker(brakeFluidButton, BrakeFluidActivity::class.java)
        }

        oilLevelButton.setOnClickListener{
            intentMaker(oilLevelButton, OilLevelActivity::class.java)
        }

        windshieldWasherFluidButton.setOnClickListener{
            intentMaker(windshieldWasherFluidButton, WindshieldWasherFluidActivity::class.java)
        }

        oilRefillButton.setOnClickListener{
            intentMaker(oilRefillButton, OilRefillActivity::class.java)
        }
        coolingFluidButton.setOnClickListener{
            intentMaker(coolingFluidButton, CoolingFluidActivity::class.java)
        }
         backButton.setOnClickListener{
             intentMaker(backButton, MainActivity::class.java)
         }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


        }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}