import com.example.remedy.CoolingFluidScanning
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

class TmpClass : ArFragment() {


    override fun getSessionConfiguration(session: Session): Config {
        val config = Config(session)
        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        val tmp = activity as CoolingFluidScanning?
        tmp!!.loadDB(session, config)
        arSceneView.setupSession(session)
        return config
    }
}