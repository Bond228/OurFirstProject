package com.example.project2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent as Intent1


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun newActivityCamera(view: View) {
        val newIntent = Intent1(this, SecondActivity::class.java).also {
            it.putExtra(SecondActivity.TOTAL_KEY, true)
            startActivity(it)
        }
    }

    fun newActivityGalery(view: View) {
        val newIntent = Intent1(this, SecondActivity::class.java).also {
            it.putExtra(SecondActivity.TOTAL_KEY, false)
            startActivity(it)
        }
    }


















   // fun toastMe(view: View){
     //   var toast = Toast.makeText(this, "Хватит тыкать!", Toast.LENGTH_SHORT)
       // toast.show()
    //}


    //fun clearMe(view: View){
    //count = 0
    //textView2.text = count.toString()
    //}
}
