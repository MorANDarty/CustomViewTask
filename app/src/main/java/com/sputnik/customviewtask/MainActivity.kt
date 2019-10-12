package com.sputnik.customviewtask

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_ok.setOnClickListener {
            if (et_1.text.isNotEmpty() && et_2.text.isNotEmpty()
                && et_3.text.isNotEmpty() && et_4.text.isNotEmpty()
            ){
                custom_view.setValues(listOf(
                    et_1.text.toString().toFloat(),
                    et_2.text.toString().toFloat(),
                    et_3.text.toString().toFloat(),
                    et_4.text.toString().toFloat()
                    ))
                custom_view.visibility = View.VISIBLE
            }
        }
    }
}
