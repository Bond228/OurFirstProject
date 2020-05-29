package com.example.newproject

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_KEY = "total_key"
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_GALLERY = 0
    }

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    lateinit var selectedImage: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val camera: Boolean = intent.getBooleanExtra(TOTAL_KEY, true)
        if (camera) dispatchTakePictureIntentCamera()
        else dispatchTakePictureIntentGallery()

        initFunc()

    }

    private fun newActivity(){
        val newIntent = Intent(this, MainActivity::class.java)
        startActivity(newIntent)
    }

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
                            //val transaction = supportFragmentManager.beginTransaction()
                            //transaction.replace(R.id.fragment_for_rotate, RotateFragment())
                            //transaction.commit()
                            //val photo = RotateImage().rotate(selectedImage, 90)
                            //selectedImage = photo
                            //imageView.setImageBitmap(photo)
                        }
                        7 -> newActivity()
                       // 8 -> galleryAddPic()
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





















    private fun dispatchTakePictureIntentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
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
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    selectedImage = data!!.extras!!.get("data") as Bitmap
                    imageView.setImageBitmap(selectedImage)
                }
            }
            REQUEST_GALLERY -> {
                val imageUri = data?.data!!
                val imageStream = contentResolver.openInputStream(imageUri)
                selectedImage = BitmapFactory.decodeStream(imageStream)
                imageView.setImageBitmap(selectedImage)
            }
        }
    }








}



