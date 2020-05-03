package com.example.project2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myButton.setOnClickListener {
            textView.text = "Hello Nastya!"
        }

    }


    var count: Int = 0

    fun newActivity(view: View){
        val newIntent = Intent(this, SecondActivity::class.java)

        newIntent.putExtra(SecondActivity.TOTAL_KEY, count)
        startActivity(newIntent)
    }

    fun toastMe(view: View){
        var toast = Toast.makeText(this, "Хватит тыкать!", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun countMe(view: View){
        count++
        textView2.text = count.toString()
    }

    fun randMe(view:View){
        count =  (1..12).shuffled().first().toInt()
        textView2.text = count.toString()
    }

    fun clearMe(view: View){
        //val countString = textView2.text.toString()
        //var count: Int = Integer.parseInt(countString)
        count = 0

        textView2.text = count.toString()
    }
}
