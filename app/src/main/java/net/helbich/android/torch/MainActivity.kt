package net.helbich.android.torch

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.ToggleButton


/**
 *
 * TODO graceful camera shutdown
 * TODO app icon
 * TODO ask for permissions if missing
 * TODO do not fail on missing permissions
 *
//Log.i("app", "Stopping application")
//torch.closeCamera()
 *
 *
 * @author Jan Helbich <janhelbich@gmail.com>
 */
class MainActivity : Activity() {

    private val torch = Torch()


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("torch", "Starting application")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hasCameraFlash = torch.isTorchAvailable(applicationContext)
        if (!hasCameraFlash) {
            Log.w("torch", "Camera flash not present!")
            Toast.makeText(applicationContext, R.string.torch_unavailable, Toast.LENGTH_LONG)
                    .show()
        }

        val torchControl: ToggleButton = findViewById(R.id.torchControl)
        torchControl.isEnabled = hasCameraFlash
        torchControl.setOnCheckedChangeListener { _, checked -> torch.toggleTorch(checked, applicationContext) }
    }

}
