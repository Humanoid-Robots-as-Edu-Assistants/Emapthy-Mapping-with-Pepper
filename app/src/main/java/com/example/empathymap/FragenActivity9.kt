package com.example.empathymap

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.SayBuilder
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.scheduleAtFixedRate

class FragenActivity9 : AppCompatActivity(), RobotLifecycleCallbacks {

    val timer = Timer()
    var seconds = 0
    var isPaused = false
    var timerIsRunning = false
    var timeSpanInSec = 180
    var isFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_fragen9)
        QiSDK.register(this, this)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {

        val btnskip: Button = findViewById(R.id.button_skip9) //skip questions
        val btnpause: Button = findViewById(R.id.button_pause9) //pause timer

        btnskip.setOnClickListener {
            val intent = Intent(this, FragenActivity10::class.java)
            startActivity(intent)
        }

        btnpause.setOnClickListener {
            isPaused = isPaused == false
        }

        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)

        val say_1 = SayBuilder.with(qiContext)
            .withText("Die vorletzte Frage lautet.")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText("Was motiviert mich beim Lernen?")
            .build()
        val say_3 = SayBuilder.with(qiContext)
            .withText("Die Antwort könnte lauten.")
            .build()
        val say_4 = SayBuilder.with(qiContext)
            .withText("Mich motiviert, wenn ich sofort Feedback bekomme.")
            .build()
        val say_5 = SayBuilder.with(qiContext)
            .withText("Ok, jetzt seid ihr dran.")
            .build()
        val say_6 = SayBuilder.with(qiContext)
            .withText("Was motiviert mich?")
            .build()
        val say_7 = SayBuilder.with(qiContext)
            .withText("Für diese Aufgabe habt ihr 3 Minuten Zeit.")
            .build()
        val say_8 = SayBuilder.with(qiContext)
            .withText("")
            .build()

        val first1: Future<Void> = say_1.async().run()
        val ballet: Future<Void> = first1.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_2.async().run()}.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_3.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_4.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_5.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_6.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_7.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            mediaPlayerStart.start()
            timerIsRunning = true
            timer.scheduleAtFixedRate(500L, 1000L) {
                if (!isPaused) {
                    if (seconds > timeSpanInSec) {
                        isPaused = true
                        timer.cancel()
                        this@FragenActivity9.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time9)
                            countTime.text = "Finished"
                        })
                        isFinished = true
                        timerIsRunning = false
                    } else {
                        seconds += 1
                        val texti = seconds.convertToTimeFormat2()
                        this@FragenActivity9.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time9)
                            countTime.text = texti
                        })
                    }
                } else {
                    this@FragenActivity9.runOnUiThread(java.lang.Runnable {
                        val countTime: TextView = findViewById(R.id.time9)
                        countTime.text = "Paused"
                    })
                }
            }
            while (isFinished != true) {
                TimeUnit.SECONDS.sleep(1L)
            }
            mediaPlayerStop.start()
            say_8.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(3L)
            val intent = Intent(this, FragenActivity10::class.java)
            startActivity(intent)
            say_8.async().run()
        }

    }

    override fun onRobotFocusLost() {
        timer.cancel()
    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")
    }
    fun Int.convertToTimeFormat2(): String {
        val minutes = this .toInt() / 60
        val seconds = this .toInt() % 60
        return java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}