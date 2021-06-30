package com.example.remedy

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class AppInfoActivity : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener {
    lateinit var spinner: Spinner
    lateinit var textView: TextView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)

        val button = findViewById<Button>(R.id.appInfo_backButton)
        val mp = MediaPlayer.create(this, R.raw.sample)
        val languages = resources.getStringArray(R.array.infos_array)
        val vib = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)

        spinner = findViewById(R.id.appinfo_DropDownMenu)
        textView = findViewById<TextView>(R.id.appinfo_workInProgress_textView)
        textView.movementMethod = ScrollingMovementMethod()

       val adapter =  ArrayAdapter.createFromResource(
            this,
            R.array.infos_array,
            R.layout.spinner_text_view

        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_text_view)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = this


        }
        button.setOnClickListener()
        {
            vib.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            mp.start()
            finish()
        }
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val value = spinner.selectedItem.toString()
        when(value){
            "Informacje ogólne" -> textView.setText(getString(R.string.general_info))
            "Grupa odbiorcza" -> textView.setText(getString(R.string.receiving_group))
            "Rozwój aplikacji" -> textView.setText(getString(R.string.app_development))
            "Autor" -> textView.setText(getString(R.string.author))

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val text: String = parent?.getItemAtPosition(position).toString()


    }
}