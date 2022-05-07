package com.example.remedy

import android.app.VoiceInteractor
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.KeyCharacterMap
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.remedy.CoolingFluidActivity
import com.example.remedy.R
import com.google.ar.core.*
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.ux.ArFragment
import java.Helpers.CameraPermissionHelper
import java.io.IOException
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.Pose
import com.google.ar.core.TrackingState
import TmpClass




class CoolingFluidScanning : AppCompatActivity() {
    private var mSession: Session? = null
    private var mUserRequestedInstall = true
    private var arFragment: TmpClass? = null
    private var textView: TextView? = null

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



/*        private fun onUpdate(frameTime: FrameTime) {
            val frame = arFragment?.arSceneView?.arFrame
            val images = frame!!.getUpdatedTrackables(
                AugmentedImage::class.java
            )
            for (image in images) {
                if (image.trackingMethod == AugmentedImage.TrackingMethod.FULL_TRACKING) {
                    if (image.name == "cooling_fluid.jpg") {
                        textView?.text  = "IMAGE IS VISIBLE"
                    }
                }
            }
        }*/

    private fun onUpdate() {
        val frame: Frame? = arFragment?.getArSceneView()?.getArFrame()

        val camera = frame?.camera

        if (camera != null) {
            if (camera.trackingState === TrackingState.TRACKING) {
                val CameraPose = camera.displayOrientedPose
            }
        }
    }

    fun loadDB(session: Session?, config: Config) {
        val dbStream =
            this.assets.open("imgs.imgdb")
        try {
            val aid = AugmentedImageDatabase.deserialize(session, dbStream)
            config.augmentedImageDatabase = aid
        } catch (e: IOException) {
            e.printStackTrace()
        }
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
                        val config = Config(mSession)

                        val dbStream =
                            this.assets.open("imgs.imgdb")
                        try {
                            val aid = AugmentedImageDatabase.deserialize(mSession, dbStream)
                            config.augmentedImageDatabase = aid
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        arFragment = supportFragmentManager
                            .findFragmentById(R.id.arFragment) as TmpClass?
                        textView = findViewById(R.id.txtView)
                        arFragment!!.arSceneView.scene.addOnUpdateListener { frameTime: FrameTime ->
                            arFragment!!.onUpdate(frameTime)
                            onUpdate(frameTime)
                        /*onUpdate(
                                frameTime
                            )*/
                        }
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

        //val backButton = findViewById<Button>(R.id.cooling_fluid_scanning_backButton)
        val startCameraARButton = findViewById<Button>(R.id.check_for_ar_core)

/*        backButton.setOnClickListener{
            intentMaker(backButton, CoolingFluidActivity::class.java)
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