package com.example.project2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_KEY = "total_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        showRandom()
    }

    fun clickMe(view: View){
        var toast = Toast.makeText(this, "Выйди от сюда, разбойник", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun showRandom(){
        val count = intent.getIntExtra(TOTAL_KEY, 0)
        var randomInt = (0..count).shuffled().first().toInt()

        randomTextView.text = Integer.toString(randomInt)
        infoTextView.text = getString(R.string.interes_text, count)




    }
}
