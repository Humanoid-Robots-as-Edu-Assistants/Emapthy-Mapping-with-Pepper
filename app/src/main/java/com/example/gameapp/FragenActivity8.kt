package com.example.gameapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.SayBuilder
import java.util.*
import java.util.concurrent.TimeUnit

class FragenActivity8 : AppCompatActivity(), RobotLifecycleCallbacks {
    var timeLeftInMillis = 0L
    var countDownTimer: CountDownTimer? = null
    var timerIsRunning = false
    var remainingTimeInMillis = 180000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_fragen8)
        QiSDK.register(this, this)
        stopOrResetTimer()
        Handler().postDelayed({
            startOrStopTimer()
        }, 14000)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)

        val say_1 = SayBuilder.with(qiContext)
            .withText("Die nächste Frage lautet.")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText("Was demotiviert mich beim Lernen?")
            .build()
        val say_3 = SayBuilder.with(qiContext)
            .withText("Die Antwort könnte lauten.")
            .build()
        val say_4 = SayBuilder.with(qiContext)
            .withText("Ich bin demotiviert, wenn ich viele lange Texte lesen muss. ")
            .build()
        val say_5 = SayBuilder.with(qiContext)
            .withText("Ok, jetzt seid ihr dran")
            .build()
        val say_6 = SayBuilder.with(qiContext)
            .withText("Was demotiviert mich?")
            .build()
        val say_7 = SayBuilder.with(qiContext)
            .withText("Für diese Aufgabe habt ihr 3 Minuten Zeit.")
            .build()

        say_1.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_2.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_3.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_4.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_5.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_6.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_7.async().run()
        TimeUnit.SECONDS.sleep(2L)
        mediaPlayerStart.start()
        TimeUnit.SECONDS.sleep(181L)
        mediaPlayerStop.start()
        TimeUnit.SECONDS.sleep(2L)

        val intent = Intent(this, FragenActivity9::class.java)
        startActivity(intent)
    }

    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")
    }
    private fun startOrStopTimer(){

        val countTime: TextView = findViewById(R.id.time8)
        if (!timerIsRunning) {
            countDownTimer?.cancel()
            object : CountDownTimer(remainingTimeInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    remainingTimeInMillis = millisUntilFinished
                    timeLeftInMillis = millisUntilFinished
                    timerIsRunning = true
                    countTime.text = remainingTimeInMillis.convertToTimeFormat()

                }

                override fun onFinish() {
                    timerIsRunning = false
                    remainingTimeInMillis = 0L
                    timeLeftInMillis = 0L
                    countTime.text = "Finished"

                }
            }.start()
        }else {
            countDownTimer?.cancel()
            timerIsRunning = false
        }
    }

    private fun stopOrResetTimer() {
        countDownTimer?.cancel()
        timerIsRunning = false
        timeLeftInMillis = 180000 // time for a question
        remainingTimeInMillis = 180000 // time for a question
        val countTime: TextView = findViewById(R.id.time8)
        countTime.text = remainingTimeInMillis.convertToTimeFormat()
    }

    fun Long.convertToTimeFormat(): String {
        val minutes = (this / 1000).toInt() / 60
        val seconds = (this / 1000).toInt() % 60
        return java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}