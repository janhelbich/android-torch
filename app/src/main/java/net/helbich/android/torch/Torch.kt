package net.helbich.android.torch

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera

/**
 * @author Jan Helbich <janhelbich@gmail.com>
 */
class Torch {

    private var camera: Camera? = null
    private var cameraOpened = false
    private var previewStarted = false

    fun isTorchAvailable(ctx: Context): Boolean {
        return ctx.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    fun toggleTorch(torchOn: Boolean, ctx: Context) {
        if (torchOn) torchOn() else torchOff()
    }

    fun closeCamera() {
        if (cameraOpened) {
            camera?.stopPreview()
            camera?.release()
            cameraOpened = false
            previewStarted
        }
    }

    private fun torchOff() {
        val cam = getOpenCamera()
        setPreviewStarted(cam)
        setCamParams(cam, Camera.Parameters.FLASH_MODE_OFF)
    }

    private fun setCamParams(cam: Camera, flashMode: String) {
        val params = cam.parameters
        params.flashMode = flashMode
        cam.parameters = params
    }

    private fun torchOn() {
        val cam = getOpenCamera()
        setPreviewStarted(cam)
        setCamParams(cam, Camera.Parameters.FLASH_MODE_TORCH)
    }

    private fun setPreviewStarted(cam: Camera) {
        if (!previewStarted) {
            setCamParams(cam, Camera.Parameters.FLASH_MODE_OFF)
            cam.startPreview()
            cam.setAutoFocusMoveCallback { _, _ -> previewStarted = true }
        }
    }

    private fun getOpenCamera(): Camera {
        if (cameraOpened) {
            return camera!!
        } else {
            camera = Camera.open()
            cameraOpened = true
            return camera!!
        }
    }
}