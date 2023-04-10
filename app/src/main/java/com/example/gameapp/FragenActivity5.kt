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
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import java.util.*
import java.util.concurrent.TimeUnit

class FragenActivity5 : AppCompatActivity(), RobotLifecycleCallbacks {

    var examTime = 180000
    var timeLeftInMillis = 0L
    var countDownTimer: CountDownTimer? = null
    var timerIsRunning = false
    var remainingTimeInMillis = examTime.toLong()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_fragen5)

        QiSDK.register(this, this)
        stopOrResetTimer()
        Handler().postDelayed({
            startOrStopTimer()
        }, 27000)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)

        val animation_1: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.nicereaction_a001).build()
        val animate_1: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_1)
            .build()

        val say_1 = SayBuilder.with(qiContext)
            .withText("Oki doki. Könnt ihr euch eure Persona jetzt besser vorstellen?")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText(" Sehr gut.")
            .build()
        val say_3 = SayBuilder.with(qiContext)
            .withText("Dann geht es weiter mit der nächsten Frage.")
            .build()
        val say_4 = SayBuilder.with(qiContext)
            .withText("Was kann ich?")
            .build()
        val say_5 = SayBuilder.with(qiContext)
            .withText("Die Antwort könnte lauten.")
            .build()
        val say_6 = SayBuilder.with(qiContext)
            .withText("Ich kann Folien gut gestalten")
            .build()
        val say_7 = SayBuilder.with(qiContext)
            .withText("Ok, jetzt seid ihr dran.")
            .build()

        val say_8 = SayBuilder.with(qiContext)
            .withText("Was kann ich?")
            .build()
        val say_9 = SayBuilder.with(qiContext)
            .withText(" Für diese Aufgabe habt ihr 3 Minuten Zeit.")
            .build()

        say_1.async().run()
        TimeUnit.SECONDS.sleep(5L)
        say_2.async().run()
        TimeUnit.SECONDS.sleep(2L)
        animate_1.async().run()
        TimeUnit.SECONDS.sleep(6L)
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
        say_8.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_9.async().run()
        TimeUnit.SECONDS.sleep(2L)
        mediaPlayerStart.start()
        TimeUnit.SECONDS.sleep(181L)
        mediaPlayerStop.start()
        TimeUnit.SECONDS.sleep(2L)

        val intent = Intent(this, FragenActivity6::class.java)
        startActivity(intent)
    }

    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")
    }
    private fun startOrStopTimer(){

        val countTime: TextView = findViewById(R.id.time5)
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
        timeLeftInMillis = examTime.toLong() // time for a question
        remainingTimeInMillis = examTime.toLong() // time for a question
        val countTime: TextView = findViewById(R.id.time5)
        countTime.text = remainingTimeInMillis.convertToTimeFormat()
    }

    fun Long.convertToTimeFormat(): String {
        val minutes = (this / 1000).toInt() / 60
        val seconds = (this / 1000).toInt() % 60
        return java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}