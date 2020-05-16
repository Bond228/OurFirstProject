package com.example.project2;

import android.graphics.Bitmap;

import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

public class RotateImage {

    public Bitmap rotate(Bitmap image, int angle){
        double rad = (angle*3.14f)/180;
        double cosf = cos(rad);
        double sinf = sqrt(1 - cosf*cosf);

        int imageWight = image.getWidth();
        int imageHeight = image.getHeight();

        int x1 = (int)(-imageHeight*sinf);
        int y1 = (int)(imageHeight*cosf);
        int x2 = (int)(imageWight*cosf - imageHeight*sinf);
        int y2 = (int)(imageHeight*cosf + imageWight*sinf);
        int x3 = (int)(imageWight*cosf);
        int y3 = (int)(imageWight*sinf);

        int minX = min(0,min(x1,(min(x2,x3))));
        int minY = min(0,min(y1,(min(y2,y3))));
        int maxX = max(x1,max(x2,x3));
        int maxY = max(y1,max(y2,y3));

        int Wight = maxX - minX;
        int Height = maxY - minY;

        Bitmap newBitmap = Bitmap.createBitmap(Wight,Height, image.getConfig());

        for (int y = 0; y < Height; y++){
            for (int x = 0; x < Wight; x++){
                int sourceX = (int)((x + minX)*cosf + (y + minY)*sinf);
                int sourceY = (int)((y + minY)*cosf - (x + minX)*sinf);

                if (sourceX >= 0 && sourceX < imageWight && sourceY >=0 && sourceY < imageHeight)
                    newBitmap.setPixel(x,y,image.getPixel(sourceX,sourceY));


                else
                    newBitmap.setPixel(x,y, 0xffffff);
            }
        }
        return newBitmap;


    }

}

