package com.example.newproject

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import com.example.newproject.SecondActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import android.content.Intent as Intent1


class MainActivity : AppCompatActivity() {


    companion object {
        private const val PERMISSION_CAMERA = 1
        private const val PERMISSION_GALLERY = 0
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_GALLERY_IMAGE = 0
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


    private fun checkCamera() {
        val permissionStatus = checkSelfPermission(this, Manifest.permission.CAMERA)

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntentCamera()
        } else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA
            )
    }


    private fun checkGallery() {
        val permissionStatus = checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntentGallery()

        } else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_GALLERY
            )
    }


    private fun dispatchTakePictureIntentCamera() {
        val takePictureIntent = Intent1(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg")
        val uri = FileProvider.getUriForFile(
            this,
            this.applicationContext.packageName + ".provider",
            file
        )
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun dispatchTakePictureIntentGallery() {
        val intent = android.content.Intent(android.content.Intent.ACTION_PICK)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, SecondActivity.REQUEST_GALLERY_IMAGE)
        }
    }


    private fun newActivity(camera: Uri) {
        val newIntent = Intent1(this, SecondActivity::class.java)
        newIntent.putExtra(SecondActivity.TOTAL_KEY, camera.toString())
        startActivity(newIntent)
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: android.content.Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    //File object of camera image
                    val file = File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg")
                    //Uri of camera image
                    val photoUri = FileProvider.getUriForFile(
                        this,
                        this.applicationContext.packageName + ".provider",
                        file
                    )
                    if (photoUri != null) {
                        try {
                            newActivity(photoUri)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            REQUEST_GALLERY_IMAGE -> {
                val imageUri = data?.data!!
                newActivity(imageUri)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    dispatchTakePictureIntentCamera()
            }
            PERMISSION_GALLERY -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    dispatchTakePictureIntentGallery()
            }
        }
    }


}
