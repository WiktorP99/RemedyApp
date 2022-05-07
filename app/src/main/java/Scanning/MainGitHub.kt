package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.rendering.ExternalTexture
import android.media.MediaPlayer
import com.google.ar.sceneform.rendering.ModelRenderable
import android.os.Bundle
import com.google.ar.sceneform.Scene.OnUpdateListener
import com.google.ar.sceneform.FrameTime
import com.google.ar.core.AugmentedImage
import com.google.ar.core.TrackingState
import android.graphics.SurfaceTexture.OnFrameAvailableListener
import android.graphics.SurfaceTexture
import android.net.Uri
import com.google.ar.core.Anchor
import com.google.ar.core.Frame
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color

class MainActivity : AppCompatActivity() {
    private var texture: ExternalTexture? = null
    private var mediaPlayer: MediaPlayer? = null
    private var arFragment: CustomArFragment? = null
    private var scene: Scene? = null
    private var renderable: ModelRenderable? = null
    private var isImageDetected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        texture = ExternalTexture()
        mediaPlayer = MediaPlayer.create(this, R.raw.video)
        mediaPlayer!!.setSurface(texture!!.surface)
        mediaPlayer!!.isLooping = true
        ModelRenderable
            .builder()
            .setSource(this, Uri.parse("video_screen.sfb"))
            .build()
            .thenAccept { modelRenderable: ModelRenderable ->
                modelRenderable.material.setExternalTexture(
                    "videoTexture",
                    texture
                )
                modelRenderable.material.setFloat4(
                    "keyColor",
                    Color(0.01843f, 1f, 0.098f)
                )
                renderable = modelRenderable
            }
        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as CustomArFragment?
        scene = arFragment.getArSceneView().getScene()
        scene!!.addOnUpdateListener { frameTime: FrameTime -> onUpdate(frameTime) }
    }

    private fun onUpdate(frameTime: FrameTime) {
        if (isImageDetected) return
        val frame: Frame = arFragment.getArSceneView().getArFrame()
        val augmentedImages = frame.getUpdatedTrackables(
            AugmentedImage::class.java
        )
        for (image in augmentedImages) {
            if (image.trackingState == TrackingState.TRACKING) {
                if (image.name == "image") {
                    isImageDetected = true
                    playVideo(
                        image.createAnchor(image.centerPose), image.extentX,
                        image.extentZ
                    )
                    break
                }
            }
        }
    }

    private fun playVideo(anchor: Anchor, extentX: Float, extentZ: Float) {
        mediaPlayer!!.start()
        val anchorNode = AnchorNode(anchor)
        texture!!.surfaceTexture.setOnFrameAvailableListener { surfaceTexture: SurfaceTexture? ->
            anchorNode.renderable = renderable
            texture!!.surfaceTexture.setOnFrameAvailableListener(null)
        }
        anchorNode.worldScale = Vector3(extentX, 1f, extentZ)
        scene!!.addChild(anchorNode)
    }
}