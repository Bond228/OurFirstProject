package com.example.newproject

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.newproject.SecondActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent as Intent1


class MainActivity : AppCompatActivity() {


    companion object {
        private const val PERMISSION_CAMERA = 1
        private const val PERMISSION_GALLERY = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        camera_button.setOnClickListener {
            checkCamera()
        }


        gallery_button.setOnClickListener {
            checkGallery()
        }
    }



    private fun checkCamera (){
        val permissionStatus = checkSelfPermission(this, Manifest.permission.CAMERA)

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            newActivity(true)
        } else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)

    }

    private fun checkGallery(){
        val permissionStatus = checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            newActivity(false)

        } else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_GALLERY)
    }





    private fun newActivity(camera: Boolean){
        val newIntent = Intent1(this, SecondActivity::class.java)
        newIntent.putExtra(SecondActivity.TOTAL_KEY, camera)
        startActivity(newIntent)
    }




    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CAMERA -> {
                if( grantResults[0] == PackageManager.PERMISSION_GRANTED )
                    newActivity(true)
            }
            PERMISSION_GALLERY -> {
                if( grantResults[0] == PackageManager.PERMISSION_GRANTED )
                    newActivity(false)
            }
        }
    }
}
