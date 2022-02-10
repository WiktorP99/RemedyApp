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

class CarScanActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var carChooseSpinner: Spinner
    lateinit var carModelSpinner: Spinner

    lateinit var textView: TextView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_scan)

        val backButton = findViewById<Button>(R.id.carScan_backButton)
        val nextButton = findViewById<Button>(R.id.carScan_nextButton)
        val text = findViewById<TextView>(R.id.car_scan_workInProgress_textView)
        text.setText("CAR SCAN, WORK IN PROGRESS").toString()
        val textViewValue = text.text
        val mp = MediaPlayer.create(this, R.raw.sample)
        val vib = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
        val intent = Intent(this, ArCoreActivity::class.java )

        carChooseSpinner = findViewById(R.id.carScan_carChoose_spinner)
        carModelSpinner = findViewById(R.id.carScan_modelChoose_spinner)
        textView = findViewById<TextView>(R.id.car_scan_workInProgress_textView)

        val carChooseAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.car_models_array,
            R.layout.spinner_text_view
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_text_view)
            carChooseSpinner.adapter = adapter
            carChooseSpinner.onItemSelectedListener = this
        }
        val modelChooseAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.car_models_array,
            R.layout.spinner_text_view
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_text_view)
            carModelSpinner.adapter = adapter
            carModelSpinner.onItemSelectedListener = this
        }

        backButton.setOnClickListener()
        {
            mp.start()
            vib.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            finish()
        }
        nextButton.setOnClickListener{
            mp.start()
            vib.vibrate(VibrationEffect.createOneShot(70, VibrationEffect.DEFAULT_AMPLITUDE))
            startActivity(intent)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val carChooseValue = carChooseSpinner.selectedItem.toString()
        val mp = MediaPlayer.create(this, R.raw.sample)

        when (carChooseValue) {
            "Ford" -> {
                val fordAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.ford_array,
                    R.layout.spinner_text_view
                ).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_text_view)
                    carModelSpinner.adapter = adapter
                    carModelSpinner.onItemSelectedListener = this
                }
            }
            "Audi" -> {
                val fordAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.audi_array,
                    R.layout.spinner_text_view
                ).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_text_view)
                    carModelSpinner.adapter = adapter
                    carModelSpinner.onItemSelectedListener = this
                }
            }
            "Nissan" -> {
                val fordAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.nisssan_array,
                    R.layout.spinner_text_view
                ).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_text_view)
                    carModelSpinner.adapter = adapter
                    carModelSpinner.onItemSelectedListener = this
                }

            }
            "Mazda" -> {
                val mazdaAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.mazda_array,
                    R.layout.spinner_text_view
                ).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_text_view)
                    carModelSpinner.adapter = adapter
                    carModelSpinner.onItemSelectedListener = this
                }
            }
            "Ferrari" -> {
                val ferrariAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.ferrari_array,
                    R.layout.spinner_text_view
                ).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_text_view)
                    carModelSpinner.adapter = adapter
                    carModelSpinner.onItemSelectedListener = this
                }
            }
            "Subaru" -> {
                val subaruAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.subaru_array,
                    R.layout.spinner_text_view
                ).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_text_view)
                    carModelSpinner.adapter = adapter
                    carModelSpinner.onItemSelectedListener = this
                }
            }
            "Opel" -> {
                val opelAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.opel_array,
                    R.layout.spinner_text_view
                ).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_text_view)
                    carModelSpinner.adapter = adapter
                    carModelSpinner.onItemSelectedListener = this
                }
            }
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}