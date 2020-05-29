package com.example.newproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.fragment_rotate.*

class RotateFragment : Fragment() {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectImage = SecondActivity().selectedImage
        right_button.setOnClickListener{
            imageView.setImageBitmap(RotateImage().rotate(selectImage,90))
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rotate, container, false)
    }


}
