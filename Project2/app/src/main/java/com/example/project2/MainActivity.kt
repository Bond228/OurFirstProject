package com.example.project2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent as Intent1


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraButton.setOnClickListener{
            newActivity(true)
        }

        galleryButton.setOnClickListener{
            newActivity(false)
        }
    }

    private fun newActivity(camera: Boolean){
        val newIntent = Intent1(this, SecondActivity::class.java)

        if (camera) newIntent.putExtra(SecondActivity.TOTAL_KEY, true)
        else newIntent.putExtra(SecondActivity.TOTAL_KEY, false)

        startActivity(newIntent)
    }
}








/*fun newActivityCamera(view: View) {
       val newIntent = Intent1(this, SecondActivity::class.java)
           newIntent.putExtra(SecondActivity.TOTAL_KEY, true)
           startActivity(newIntent)
       }


   fun newActivityGallery(view: View) {
       val newIntent = Intent1(this, SecondActivity::class.java)
           newIntent.putExtra(SecondActivity.TOTAL_KEY, false)
           startActivity(newIntent)
       }
   }*/


// fun toastMe(view: View){
//   var toast = Toast.makeText(this, "Хватит тыкать!", Toast.LENGTH_SHORT)
// toast.show()
//}