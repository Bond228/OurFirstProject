package com.example.project2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.fragment_rotate.*


class RotateFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_rotate, container, false)


    }

    fun povorot(angle: Int){
        var selectedImage = SecondActivity().selectedImage

        val photo = RotateImage().rotate(selectedImage, angle)
        SecondActivity().selectedImage = photo
        imageView.setImageBitmap(photo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //onLeft.setOnClickListener() {
            //povorot(-90)
        //}

        onRight.setOnClickListener(){
            povorot(90)
        }


    }


}
