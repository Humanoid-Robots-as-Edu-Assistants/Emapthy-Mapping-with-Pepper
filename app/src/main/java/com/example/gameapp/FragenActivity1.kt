package com.example.gameapp

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
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.SayBuilder
import java.util.*
import java.util.concurrent.TimeUnit


class FragenActivity1 : AppCompatActivity(), RobotLifecycleCallbacks {

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
        setContentView(R.layout.activity_fragen1)
        QiSDK.register(this, this)
        stopOrResetTimer()
        Handler().postDelayed({
            startOrStopTimer()
        }, 16000)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {

        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)

        val say_1 = SayBuilder.with(qiContext)
            .withText("Ok, jetzt geht es weiter mit mit der ersten Frage zum Empathy Mapping.")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText("Setzt Euch in Eure Persona hinein. Die erste Frage ist: ")
            .build()
        val say_3 = SayBuilder.with(qiContext)
            .withText("Wer bin ich und was möchte ich lernen?")
            .build()
        val say_4 = SayBuilder.with(qiContext)
            .withText("Notiert Eure kurze und sachliche Antwort auf diese Frage in Miro. ")
            .build()
        val say_5 = SayBuilder.with(qiContext)
            .withText("Hier ein Beispiel: Ich bin Annika Stolz, 35 Jahre alt, " +
                    "Account Manager bei Cisco und möchte überzeugend präsentieren lernen. ")
            .build()
        val say_6 = SayBuilder.with(qiContext)
            .withText("Das  überzeugend präsentieren ist hier das Lernziel. Wer bin ich und was möchte ich lernen. " +
                    "Für diese Aufgabe habt ihr 3 Minuten Zeit. Zeit beginnt jetzt.")
            .build()
        val say_7 = SayBuilder.with(qiContext)
            .withText("Ok, erste Frage geschafft. Ab jetzt gehen wir die restlichen Fragen in der Reihenfolge des Uhrzeigersinns durch." +
                    "Ab jetzt ist es wichtig, dass ihr jede Frage mit dem Lernziel in Verbindung bringt.")
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
        TimeUnit.SECONDS.sleep(4L)
        say_6.async().run()
        TimeUnit.SECONDS.sleep(6L)
        mediaPlayerStart.start()
        TimeUnit.SECONDS.sleep(181L)
        mediaPlayerStop.start()
        TimeUnit.SECONDS.sleep(2L)
        say_7.async().run()
        TimeUnit.SECONDS.sleep(5L)
        val intent = Intent(this, FragenActivity2::class.java)
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
                    val countTime: TextView = findViewById(R.id.time1)
                    countTime.text = remainingTimeInMillis.convertToTimeFormat()

                }

                override fun onFinish() {
                    timerIsRunning = false
                    remainingTimeInMillis = 0L
                    timeLeftInMillis = 0L
                    val countTime: TextView = findViewById(R.id.time1)
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
        remainingTimeInMillis = 180000
        val countTime:TextView = findViewById(R.id.time1)
        countTime.text = remainingTimeInMillis.convertToTimeFormat()
    }

    fun Long.convertToTimeFormat(): String {
        val minutes = (this / 1000).toInt() / 60
        val seconds = (this / 1000).toInt() % 60
        return java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}