package com.example.newproject

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rotate.*

class RotateActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_KEY = "total_key"

    }

    //private var selectedImage: Bitmap = SecondActivity().selectedImage
    private lateinit var photo: Bitmap
    var angle: Int = 0
    lateinit var selectedImage: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate)



        val imageUri = Uri.parse(intent.getStringExtra("total_key"))
        val imageStream = contentResolver.openInputStream(imageUri)
        selectedImage = BitmapFactory.decodeStream(imageStream)
        imageView1.setImageBitmap(selectedImage)



        angle_view.text = angle.toString()

        right_button.setOnClickListener {
            photo = rotate(selectedImage, -90)
            selectedImage = photo
            imageView1.setImageBitmap(photo)

        }

        left_button.setOnClickListener {
            photo = rotate(selectedImage, -90)
            selectedImage = photo
            imageView1.setImageBitmap(photo)

        }

        any_angle_button.setOnClickListener{
            photo = rotate(selectedImage, angle)
            selectedImage = photo
            imageView1.setImageBitmap(photo)

        }

        save_image_button.setOnClickListener{
            val newIntent = Intent(this, SecondActivity::class.java)
            newIntent.putExtra(SecondActivity.WHAT, 1)
            newIntent.putExtra(SecondActivity.BitmapImage,selectedImage)
            startActivity(newIntent)


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


        old_photo_button.setOnClickListener{
            val newIntent = Intent(this, SecondActivity::class.java)
            newIntent.putExtra(SecondActivity.TOTAL_KEY, imageUri.toString())
            startActivity(newIntent)
        }
    }



    private fun rotate(image: Bitmap, angle: Int): Bitmap {
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
