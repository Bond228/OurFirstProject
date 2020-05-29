package com.example.newproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mikepenz.iconics.Iconics.applicationContext
import kotlinx.android.synthetic.main.fragment_rotate.*

class RotateFragment : Fragment() {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //var selectImage = SecondActivity().selectedImage
        right_button.setOnClickListener{
           // imageView.setImageBitmap(SecondActivity().rotate(selectImage,90))
            Toast.makeText(applicationContext,"Ещё не работает..(", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rotate, container, false)
    }


}
