package com.example.newproject

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.SeekBar
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
import kotlinx.android.synthetic.main.fragment_rotate.*

class SecondActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_KEY = "total_key"
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_GALLERY = 0
    }

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    lateinit var selectedImage: Bitmap
    var angle: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val camera: Boolean = intent.getBooleanExtra(TOTAL_KEY, true)
        if (camera) dispatchTakePictureIntentCamera()
        else dispatchTakePictureIntentGallery()

        initFunc()
        angle_view.text = angle.toString()

        right_button.setOnClickListener() {
            val photo = rotate(selectedImage, 90)
            selectedImage = photo
            imageView.setImageBitmap(photo)
        }

        left_button.setOnClickListener() {
            val photo = rotate(selectedImage, -90)
            selectedImage = photo
            imageView.setImageBitmap(photo)
        }

        any_angle_button.setOnClickListener(){
            val photo = rotate(selectedImage, angle)
            selectedImage = photo
            imageView.setImageBitmap(photo)
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                angle_view.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                angle = seekBar?.progress!!
            }
        })
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
                            //transaction.replace(R.id.fragment_for_rotate, StartFragment())
                            //transaction.commit()
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









    fun rotate(image: Bitmap, angle: Int): Bitmap {
        val rad = angle * 3.14f / 180.toDouble()
        val cosf = Math.cos(rad)
        val sinf = Math.sqrt(1 - cosf * cosf)
        val imageWight = image.width
        val imageHeight = image.height
        val x1 = (-imageHeight * sinf).toInt()
        val y1 = (imageHeight * cosf).toInt()
        val x2 = (imageWight * cosf - imageHeight * sinf).toInt()
        val y2 = (imageHeight * cosf + imageWight * sinf).toInt()
        val x3 = (imageWight * cosf).toInt()
        val y3 = (imageWight * sinf).toInt()
        val minX =
            Math.min(0, Math.min(x1, Math.min(x2, x3)))
        val minY =
            Math.min(0, Math.min(y1, Math.min(y2, y3)))
        val maxX = Math.max(x1, Math.max(x2, x3))
        val maxY = Math.max(y1, Math.max(y2, y3))
        val Wight = maxX - minX
        val Height = maxY - minY
        val newBitmap: Bitmap =
            Bitmap.createBitmap(Wight, Height, image.config)
        for (y in 0 until Height) {
            for (x in 0 until Wight) {
                val sourceX = ((x + minX) * cosf + (y + minY) * sinf).toInt()
                val sourceY = ((y + minY) * cosf - (x + minX) * sinf).toInt()
                if (sourceX >= 0 && sourceX < imageWight && sourceY >= 0 && sourceY < imageHeight) newBitmap.setPixel(
                    x,
                    y,
                    image.getPixel(sourceX, sourceY)
                ) else newBitmap.setPixel(x, y, 0xffffff)
            }
        }
        return newBitmap
    }




}





