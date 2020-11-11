package com.sekagra.workouttimer

import android.graphics.ColorFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.text.format.DateUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var button: MaterialButton
    private lateinit var workBlock: LinearLayout
    private lateinit var pauseBlock: LinearLayout
    private lateinit var timerProgressBar: ProgressBar
    private lateinit var workTimer: CountDownTimer
    private lateinit var pauseTimer: CountDownTimer

    private lateinit var lblTimerProgress: TextView

    private lateinit var txtWorkMinutes: EditText
    private lateinit var txtWorkSeconds: EditText
    private lateinit var txtPauseMinutes: EditText
    private lateinit var txtPauseSeconds: EditText

    private lateinit var mediaPlayer: MediaPlayer

    private var run: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.button = findViewById(R.id.btnStartStop)
        this.workBlock = findViewById(R.id.workBlock)
        this.pauseBlock = findViewById(R.id.pauseBlock)
        this.timerProgressBar = findViewById(R.id.timerProgressBar)
        this.lblTimerProgress = findViewById(R.id.lblTimerProgress)
        this.timerProgressBar.visibility = View.GONE
        this.lblTimerProgress.visibility = View.GONE

        this.txtWorkMinutes = findViewById(R.id.txtWorkMinutes)
        this.txtWorkSeconds = findViewById(R.id.txtWorkSeconds)
        this.txtPauseMinutes = findViewById(R.id.txtPauseMinutes)
        this.txtPauseSeconds = findViewById(R.id.txtPauseSeconds)

        this.mediaPlayer = MediaPlayer.create(this, R.raw.beep)
    }

    fun onStartStopButtonClick(view: View) {
        this.run = !this.run

        if (this.run)
        {
            var workTime = getWorkTime()
            var pauseTime = getPauseTime()
            if (workTime > 1000 && pauseTime > 1000)
            {
                runTimer(workTime, pauseTime)
                viewRunningTimer()
            }
            else
            {
                Toast.makeText(applicationContext, "Invalid times", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            stopTimer()
            viewStoppedTimer()
        }
    }

    private fun viewRunningTimer() {
        this.button?.text = "■"
        this.workBlock?.visibility = View.GONE
        this.pauseBlock?.visibility = View.GONE
        this.timerProgressBar?.visibility = View.VISIBLE
        this.lblTimerProgress?.visibility = View.VISIBLE
    }

    private fun viewStoppedTimer() {
        this.button?.text = "▶"
        this.workBlock?.visibility = View.VISIBLE
        this.pauseBlock?.visibility = View.VISIBLE
        this.timerProgressBar?.visibility = View.GONE
        this.lblTimerProgress?.visibility = View.GONE
    }

    private fun getWorkTime(): Int {
        return this.txtWorkMinutes.text.toString().toInt() * 60 * 1000 + this.txtWorkSeconds.text.toString().toInt() * 1000
    }

    private fun getPauseTime(): Int {
        return this.txtPauseMinutes.text.toString().toInt() * 60 * 1000 + this.txtPauseSeconds.text.toString().toInt() * 1000
    }

    private fun runTimer(millisWork: Int, millisPause: Int) {
        this.workTimer = object : CountDownTimer(millisWork.toLong(), 100) {
            override fun onFinish() {
                pauseTimer.cancel()
                pauseTimer.start()
                mediaPlayer.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                timerProgressBar.progress = 10000 - (10000 * (millisUntilFinished / millisWork.toDouble())).toInt()
                lblTimerProgress.text = DateUtils.formatElapsedTime((millisUntilFinished.toDouble() / 1000).toLong())
            }
        }

        this.pauseTimer = object : CountDownTimer(millisPause.toLong(), 100) {
            override fun onFinish() {
                workTimer.cancel()
                workTimer.start()
                mediaPlayer.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                timerProgressBar.progress = 10000 - (10000 * (millisUntilFinished / millisPause.toDouble())).toInt()
                lblTimerProgress.text = DateUtils.formatElapsedTime((millisUntilFinished.toDouble() / 1000).toLong())
            }
        }

        this.workTimer.start()
    }

    private fun stopTimer() {
        if (this.workTimer != null)
            this.workTimer.cancel()
        if (this.pauseTimer != null)
            this.pauseTimer.cancel()
    }
}