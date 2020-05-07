package com.example.project2

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.graphics.createBitmap
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

import java.io.File

class SecondActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_KEY = "total_key"
        val REQUEST_IMAGE_CAPTURE = 1
        val REQUEST_GALERY = 0
    }

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var camera: Boolean = intent.getBooleanExtra(TOTAL_KEY, true)
        if (camera) dispatchTakePictureIntentCamera()
        else dispatchTakePictureIntentGalery()

        initFunc()

    }

    private fun initFunc() {
        createHeader()
        createDrawer()
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(this)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Создать группу")
                    .withSelectable(false)
            ).build()

    }

    private fun createHeader() {
        mHeader = AccountHeaderBuilder()
            .withActivity(this)
            .addProfiles(
                ProfileDrawerItem().withName("Yura Pterov")
                    .withEmail("+7911111111")
            ).build()
    }







    fun rotateImage(view: View){
        imageView2.animate().rotation(90F)
    }

    private fun dispatchTakePictureIntentCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun dispatchTakePictureIntentGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_GALERY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data?.extras
            val imageBitmap =
                extras!!.get("data") as Bitmap?
            imageView2.setImageBitmap(imageBitmap)
        }

        if(requestCode == REQUEST_GALERY && data!=null){
            var imageUri: Uri = data.getData()!!
            var imageStream = getContentResolver().openInputStream(imageUri)
            var selectedImage = BitmapFactory.decodeStream(imageStream)
            imageView2.setImageBitmap(selectedImage)
        }

    }
}

