import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.example.remedy.R
import com.google.ar.sceneform.Scene.OnUpdateListener
import com.google.ar.sceneform.FrameTime
import com.google.ar.core.AugmentedImage
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import java.io.IOException

class TmpClassMain : AppCompatActivity() {
    private var arFragment: TmpClass? = null
    private var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arFragment = supportFragmentManager
            .findFragmentById(R.id.arFragment) as TmpClass?
        textView = findViewById(R.id.txtView)
        arFragment!!.arSceneView.scene.addOnUpdateListener { frameTime: FrameTime ->
            onUpdate(
                frameTime
            )
        }
    }

    private fun onUpdate(frameTime: FrameTime) {
        val frame = arFragment!!.arSceneView.arFrame
        val images = frame!!.getUpdatedTrackables(
            AugmentedImage::class.java
        )
        for (image in images) {
            if (image.trackingMethod == AugmentedImage.TrackingMethod.FULL_TRACKING) {
                if (image.name == "cooling_fluid.jpg") {
                    textView!!.text = "IMAGE IS VISIBLE"
                }
            }
        }
    }

    fun loadDB(session: Session?, config: Config) {
        val dbStream =
            this.assets.open("IMGDB.imgdb")
        try {
            val aid = AugmentedImageDatabase.deserialize(session, dbStream)
            config.augmentedImageDatabase = aid
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}