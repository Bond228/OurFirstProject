package com.example.newproject

import android.graphics.Bitmap
import java.lang.Math.*

class RotateImage {
    fun rotate(image: Bitmap, angle: Int): Bitmap {
        val rad = angle * 3.14f / 180.toDouble()
        val cosf = cos(rad)
        val sinf = sqrt(1 - cosf * cosf)
        val imageWight = image.width
        val imageHeight = image.height
        val x1 = (-imageHeight * sinf).toInt()
        val y1 = (imageHeight * cosf).toInt()
        val x2 = (imageWight * cosf - imageHeight * sinf).toInt()
        val y2 = (imageHeight * cosf + imageWight * sinf).toInt()
        val x3 = (imageWight * cosf).toInt()
        val y3 = (imageWight * sinf).toInt()
        val minX =
            min(0, min(x1, min(x2, x3)))
        val minY =
            min(0, min(y1, min(y2, y3)))
        val maxX = max(x1, max(x2, x3))
        val maxY = max(y1, max(y2, y3))
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