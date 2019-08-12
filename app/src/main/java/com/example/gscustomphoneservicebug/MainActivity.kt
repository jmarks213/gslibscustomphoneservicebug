package com.example.gscustomphoneservicebug

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val WRITE_EXTERNAL_PERMISSION_REQUEST = 78696
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionsRequest()


    }





    /**
     *  Permissions request related methods
     */
    private fun permissionsRequest() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                writePermissionsRequired()
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_PERMISSION_REQUEST)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            WRITE_EXTERNAL_PERMISSION_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                } else {
                    writePermissionsRequired()
                }
                return
            }
        }
    }
    private fun writePermissionsRequired() {
        Toast.makeText(this, "Write to external storage is required", Toast.LENGTH_LONG).show()
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_EXTERNAL_PERMISSION_REQUEST)
    }
}
