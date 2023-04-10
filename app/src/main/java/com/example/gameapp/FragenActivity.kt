package com.example.gameapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.SayBuilder
import java.util.*
import java.util.concurrent.TimeUnit


class FragenActivity : AppCompatActivity(), RobotLifecycleCallbacks {


    var timeLeftInMillis = 0L
    var countDownTimer: CountDownTimer? = null
    var timerIsRunning = false
    var remainingTimeInMillis = 180000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // fullscreen view
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide() // hides the action bar
        setContentView(R.layout.activity_fragen)
        QiSDK.register(this, this)
        stopOrResetTimer()
        Handler().postDelayed({
            startOrStopTimer()
        }, 9000)

    }

    override fun onRobotFocusGained(qiContext: QiContext?) {

        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)

        val say_1 = SayBuilder.with(qiContext)
            .withText("Ok, die Aufgabe zum Einstieg ist")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText("Öffnet bitte Eure Empathy Map in Miro und notiert die Namen " +
                    "der Gruppenmitglieder in der Ecke oben rechts. ")
            .build()
        val say_3 = SayBuilder.with(qiContext)
            .withText("Ihr habt dafür 3 Minuten. Zeit beginnt jetzt")
            .build()
        val say_4 = SayBuilder.with(qiContext)
            .withText("Prima, das hat schon mal gut funktioniert.")
            .build()

        say_1.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_2.async().run()
        TimeUnit.SECONDS.sleep(4L)
        say_3.async().run()
        TimeUnit.SECONDS.sleep(2L)
        mediaPlayerStart.start()
        TimeUnit.SECONDS.sleep(181L)
        mediaPlayerStop.start()
        TimeUnit.SECONDS.sleep(2L)
        say_4.async().run()
        TimeUnit.SECONDS.sleep(3L)
        val intent = Intent(this, FragenActivity1::class.java)
        startActivity(intent)

    }

    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")
    }


    private fun startOrStopTimer(){

        if (!timerIsRunning) {
            countDownTimer?.cancel()
            object : CountDownTimer(remainingTimeInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    remainingTimeInMillis = millisUntilFinished
                    timeLeftInMillis = millisUntilFinished
                    timerIsRunning = true
                    val countTime: TextView = findViewById(R.id.time)
                    countTime.text = remainingTimeInMillis.convertToTimeFormat()

                }

                override fun onFinish() {
                    timerIsRunning = false
                    remainingTimeInMillis = 0L
                    timeLeftInMillis = 0L
                    val countTime: TextView = findViewById(R.id.time)
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
        timeLeftInMillis = 180000
        remainingTimeInMillis = 180000 // time for a question
        val countTime:TextView = findViewById(R.id.time)
        countTime.text = remainingTimeInMillis.convertToTimeFormat()
    }

    fun Long.convertToTimeFormat(): String {
        val minutes = (this / 1000).toInt() / 60
        val seconds = (this / 1000).toInt() % 60
        return java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

}