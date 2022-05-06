package com.example.remedy

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.KeyCharacterMap
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.remedy.CoolingFluidActivity
import com.example.remedy.R
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import java.Helpers.CameraPermissionHelper

class CoolingFluidScanning : AppCompatActivity()
{
    private var mSession: Session? = null
    private var mUserRequestedInstall = true


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

    fun isARCoreSupportedAndUpToDate(): Boolean {
        return when (ArCoreApk.getInstance().checkAvailability(this)) {
            ArCoreApk.Availability.SUPPORTED_INSTALLED -> true
            ArCoreApk.Availability.SUPPORTED_APK_TOO_OLD, ArCoreApk.Availability.SUPPORTED_NOT_INSTALLED -> {
                try {
                    // Request ARCore installation or update if needed.
                    when (ArCoreApk.getInstance().requestInstall(this, true)) {
                        ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                            Log.i(TAG, "ARCore installation requested.")
                            false
                        }
                        ArCoreApk.InstallStatus.INSTALLED -> true
                    }
                } catch (e: KeyCharacterMap.UnavailableException) {
                    Log.e(TAG, "ARCore not installed", e)
                    false
                }
            }
            else -> {true}
        }
    }

    fun maybeEnableArButton() {

    }


    override fun onResume() {
        super.onResume()

        // ARCore requires camera permission to operate.
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            CameraPermissionHelper.requestCameraPermission(this)
            return
        }

        try {
            if (mSession == null) {
                when (ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)) {
                    ArCoreApk.InstallStatus.INSTALLED -> {
                        Toast.makeText(this, "INSTALLED ", Toast.LENGTH_LONG)
                            .show()
                        // Success: Safe to create the AR session.
                        mSession = Session(this.applicationContext)
                    }
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        Toast.makeText(this, "UNINSTALLED ", Toast.LENGTH_LONG)
                            .show()
                        mUserRequestedInstall = false
                        return
                    }
                }
            }
        } catch (e: UnavailableUserDeclinedInstallationException) {
            Toast.makeText(this, "TODO: handle exception " + e, Toast.LENGTH_LONG)
                .show()
            return
        } catch (e: ExceptionInInitializerError) {
            return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            Toast.makeText(this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
                .show()
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings(this)
            }
            finish()
        }
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooling_fluid_scanning)

        val backButton = findViewById<Button>(R.id.cooling_fluid_scanning_backButton)
        val startCameraARButton = findViewById<Button>(R.id.check_for_ar_core)

        startCameraARButton.isVisible
        backButton.setOnClickListener{
            intentMaker(backButton, CoolingFluidActivity::class.java)
        }

/*        val imageDatabase = this.assets.open("cooling_fluid.jpg").use {
            //AugmentedImageDatabase.deserialize(session, it)
        }*/

        val availability = ArCoreApk.getInstance().checkAvailability(this)

        if (availability.isTransient) {
            // Continue to query availability at 5Hz while compatibility is checked in the background.
            Handler().postDelayed({
                maybeEnableArButton()
            }, 200)
        }
        if (availability.isSupported) {
            startCameraARButton.visibility = View.VISIBLE
            startCameraARButton.isEnabled = true
        } else { // The device is unsupported or unknown.
            startCameraARButton.visibility = View.INVISIBLE
            startCameraARButton.isEnabled = false
        }

    }
}