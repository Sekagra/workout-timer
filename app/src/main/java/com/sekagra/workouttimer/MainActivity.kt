package com.sekagra.workouttimer

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private var button: MaterialButton? = null
    private var workBlock: LinearLayout? = null
    private var pauseBlock: LinearLayout? = null
    private var run: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.button = findViewById(R.id.btnStartStop)
        this.workBlock = findViewById(R.id.workBlock)
        this.pauseBlock = findViewById(R.id.pauseBlock)
    }

    fun onStartStopButtonClick(view: View) {
        this.run = !this.run

        if (this.run)
        {
            this.button?.text = "■"
            this.workBlock?.visibility = View.INVISIBLE
            this.pauseBlock?.visibility = View.INVISIBLE
        }
        else
        {
            this.button?.text = "▶"
            this.workBlock?.visibility = View.VISIBLE
            this.pauseBlock?.visibility = View.VISIBLE
        }
    }
}