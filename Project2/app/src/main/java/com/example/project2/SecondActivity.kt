package com.example.project2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent.ACTION_GET_CONTENT
import android.app.ProgressDialog.show
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.core.graphics.createBitmap
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import com.example.project2.SecondActivity.Companion.REQUEST_GALLERY
import com.example.project2.SecondActivity.Companion.REQUEST_IMAGE_CAPTURE
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.fragment_rotate.*
import java.util.Collections.rotate

//@Suppress("DEPRECATION")
@Suppress("DEPRECATION")
class SecondActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_KEY = "total_key"
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_GALLERY = 0
    }

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private lateinit var imageUri: Uri
    private lateinit var currentPhotoPath: String
    lateinit var selectedImage: Bitmap
    private var angle: Int = -90

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_second)

            val camera: Boolean = intent.getBooleanExtra(TOTAL_KEY, true)
            if (camera) dispatchTakePictureIntentCamera()
            else dispatchTakePictureIntentGallery()

            initFunc()

            //onLeft.setOnClickListener(){ angle = -90 }
    }




//По мелочи
    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)// Надо тут разобраться
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }

    private fun newActivity(){
        val newIntent = Intent(this, MainActivity::class.java)
        startActivity(newIntent)
    }




//Боковое меню
    private fun initFunc() {
        createHeader()
        createDrawer()
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(this)
            .withAccountHeader(mHeader)
            .withSelectedItem(-1)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(1)
                    .withIconTintingEnabled(true)
                    .withName("Повернуть")
                    .withSelectable(false)
                    .withIcon(R.drawable.ar),
                PrimaryDrawerItem().withIdentifier(2)
                    .withIconTintingEnabled(true)
                    .withName("Масштабировать")
                    .withSelectable(false)
                    .withIcon(R.drawable.arrow),
                PrimaryDrawerItem().withIdentifier(3)
                    .withIconTintingEnabled(true)
                    .withName("Цветокоррекция")
                    .withSelectable(false)
                    .withIcon(R.drawable.palette),
                PrimaryDrawerItem().withIdentifier(4)
                    .withIconTintingEnabled(true)
                    .withName("Ретуширование")
                    .withSelectable(false)
                    .withIcon(R.drawable.magic),
                PrimaryDrawerItem().withIdentifier(5)
                    .withIconTintingEnabled(true)
                    .withName("Маскирование")
                    .withSelectable(false)
                    .withIcon(R.drawable.paint),
                DividerDrawerItem(),
                PrimaryDrawerItem().withIdentifier(7)
                    .withIconTintingEnabled(true)
                    .withName("Новое фото")
                    .withSelectable(false)
                    .withIcon(R.drawable.hangehoto),
                PrimaryDrawerItem().withIdentifier(8)
                    .withIconTintingEnabled(true)
                    .withName("Сохранить фото")
                    .withSelectable(false)
                    .withIcon(R.drawable.file)
            )
            .withOnDrawerItemClickListener(object :Drawer.OnDrawerItemClickListener{
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when(position){
                        1 -> {
                            //val photo = RotateImage().rotate(selectedImage, 90)
                            //selectedImage = photo
                            //imageView.setImageBitmap(photo)
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_rotate,RotateFragment()).commit()
                            onRight.setOnClickListener() {
                                RotateFragment().povorot(90)
                            }

                        }
                        7 -> newActivity()
                        8 -> galleryAddPic()
                        else -> Toast.makeText(applicationContext,"Ещё не работает..(", Toast.LENGTH_SHORT).show()
                    }
                    return false
                }
            }).build()
    }

    private fun createHeader() {
        mHeader = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.new_bg)
            .build()
    }





//Загрузка фото
@SuppressLint("SimpleDateFormat", "NewApi")
@Throws(IOException::class)

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmSS").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntentCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }



    private fun dispatchTakePictureIntentGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_GALLERY)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data?.extras
            val imageBitmap =
                extras!!.get("data") as Bitmap?
            imageView.setImageBitmap(imageBitmap)
        }

        if (requestCode == REQUEST_GALLERY && data != null) {
            val imageUri: Uri = data.data!!
            val imageStream = contentResolver.openInputStream(imageUri)
            selectedImage = BitmapFactory.decodeStream(imageStream)
            imageView.setImageBitmap(selectedImage)
        }
        Toast.makeText(this, "Свайпните вправо для того, чтобы открыть меню!", Toast.LENGTH_SHORT).show()
    }




}


