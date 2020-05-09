package com.example.project2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.R.drawable.header
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import kotlinx.android.synthetic.main.activity_second.*
import android.content.Intent as Intent1

class SecondActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_KEY = "total_key"
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_GALLERY = 0
    }

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val camera: Boolean = intent.getBooleanExtra(TOTAL_KEY, true)
        if (camera) dispatchTakePictureIntentCamera()
        else dispatchTakePictureIntentGalery()

        initFunc()

        restartButton.setOnClickListener {
            val newIntent = Intent1(this, MainActivity::class.java)
            startActivity(newIntent)
        }
    }





    private fun initFunc() {
        createHeader()
        createDrawer()
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(this)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Повернуть")
                    .withSelectable(false)
            ).build()
    }

    private fun createHeader() {
        mHeader = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(header)
            .addProfiles(
                ProfileDrawerItem().withName("The best")
            )
            .build()
    }





    private fun dispatchTakePictureIntentCamera() {
        val takePictureIntent = Intent1(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun dispatchTakePictureIntentGalery() {
        val intent = Intent1(Intent1.ACTION_PICK)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_GALLERY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent1?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                val extras = data?.extras
                val imageBitmap =
                    extras!!.get("data") as Bitmap?
                imageView.setImageBitmap(imageBitmap)
            }
            REQUEST_GALLERY ->{
                val imageUri: Uri = data?.data!!
                val imageStream = contentResolver.openInputStream(imageUri)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                imageView.setImageBitmap(selectedImage)
            }
        }

        val toast = Toast.makeText(this, "Свайпните вправо для того, чтобы открыть меню!", Toast.LENGTH_SHORT)
        toast.show()
        }
}


