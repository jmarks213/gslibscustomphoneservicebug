package com.example.gscustomphoneservicebug

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gs.account.api.BaseAccountApi
import com.gs.common.client.ApiClient
import com.gs.phone.api.audio.AudioRouteApi
import com.gs.phone.api.call.BaseCallApi
import com.gs.phone.api.line.BaseLineApi
import com.gs.phone.context.PhoneContext
import com.gs.phone.entity.BaseLine
import com.gs.phone.listener.ICallStatusListener
import com.gs.sms.api.SmsManagerApi

class MainActivity : AppCompatActivity() {

    companion object {
        private const val WAKE_LOCK_PERMISSION_REQUEST = 7868
    }


    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionsRequest()

        val toggleButton: ToggleButton = findViewById(R.id.toggle_btn)
        toggleButton.setOnCheckedChangeListener{button, checked ->

            if (this.mediaPlayer == null) {
                this.mediaPlayer = MediaPlayer.create(this, R.raw.mp_grail)
            }

            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer!!.stop()
                mediaPlayer!!.release()
                mediaPlayer = null
                return@setOnCheckedChangeListener
            }

            mediaPlayer?.isLooping = true
            mediaPlayer?.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            mediaPlayer?.start()
        }
    }


    /**
     *  Permissions request related methods
     */
    private fun permissionsRequest() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WAKE_LOCK)) {
                wakeLockPermissionsRequired()
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WAKE_LOCK),
                    WAKE_LOCK_PERMISSION_REQUEST)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            WAKE_LOCK_PERMISSION_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                } else {
                    wakeLockPermissionsRequired()
                }
                return
            }
        }
    }


    private fun wakeLockPermissionsRequired() {
        Toast.makeText(this, "Wake lock is required", Toast.LENGTH_LONG).show()
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WAKE_LOCK),
            WAKE_LOCK_PERMISSION_REQUEST)
    }


    private fun log(msg: String) {
        Log.v("MainActivity", msg)
        FileUtils.writeToExternalNotificationsLog(this, msg)
    }
}
