package com.example.project2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    fun clickMe(view: View){
        var toast = Toast.makeText(this, "Выйди от сюда, разбойник", Toast.LENGTH_SHORT)

        toast.show()
    }
}
