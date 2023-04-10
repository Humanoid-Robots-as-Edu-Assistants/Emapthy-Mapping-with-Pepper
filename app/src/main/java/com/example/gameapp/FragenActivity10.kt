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

class FragenActivity10 : AppCompatActivity(), RobotLifecycleCallbacks {
    var examTime = 300000
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
        setContentView(R.layout.activity_fragen10)
        QiSDK.register(this, this)
        stopOrResetTimer()
        Handler().postDelayed({
            startOrStopTimer()
        }, 15000)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)

        val animation_1: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.hello_a001).build() // Set the animation resource.
        val animation_2: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.salute_right_b001).build()

        val animate_1: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_1)
            .build()
        val animate_2: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_2)
            .build()

        val say_1 = SayBuilder.with(qiContext)
            .withText("Ok. wir sind jetzt bei der letzten Frage. Die letzte Frage ist.")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText("Welche Lernerfahrung wünsche ich mir?")
            .build()
        val say_3 = SayBuilder.with(qiContext)
            .withText("Zum Beispiel. Ich wünsche mir ein interaktives Lernangebot mit einem Experten. " +
                    "mit dem ich das Präsentieren praktisch üben kann und personalisiertes Feedback bekomme.")
            .build()
        val say_4 = SayBuilder.with(qiContext)
            .withText("Ok, jetzt seid ihr dran.")
            .build()
        val say_5 = SayBuilder.with(qiContext)
            .withText("Welche Lernerfahrung wünsche ich mir?")
            .build()
        val say_6 = SayBuilder.with(qiContext)
            .withText(" Für diese Aufgabe habt ihr mehr Zeit. fünf Minuten. Jetzt solltet " +
                    "ihr brainstormen und Eure Ideen in Miro notieren.")
            .build()
        val say_7 = SayBuilder.with(qiContext)
            .withText("Ok. Ihr habt alle Fragen beantwortet und eure Antworten in Miro notiert.")
            .build()
        val say_8 = SayBuilder.with(qiContext)
            .withText("Seid Ihr mit dem Ergebnis zufrieden?")
            .build()
        val say_9 = SayBuilder.with(qiContext)
            .withText("Ihr könnt Eure Empathy Map noch später  ergänzen und von Zeit zu Zeit aktualisieren.")
            .build()
        val say_10 = SayBuilder.with(qiContext)
            .withText("Und denkt daran. Eine Empathiekarte ist nur ein Startpunkt für das Learning Experience Design.")
            .build()
        val say_11 = SayBuilder.with(qiContext)
            .withText("Weitere Schritte sind notwendig. um eine gute Lernerfahrung zu gestalten. ")
            .build()
        val say_12 = SayBuilder.with(qiContext)
            .withText("Tschüss und bis zum nächsten Mal!")
            .build()


        say_1.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_2.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_3.async().run()
        TimeUnit.SECONDS.sleep(4L)
        say_4.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_5.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_6.async().run()
        TimeUnit.SECONDS.sleep(3L)
        mediaPlayerStart.start()
        TimeUnit.SECONDS.sleep(181L)
        mediaPlayerStop.start()
        TimeUnit.SECONDS.sleep(2L)
        say_7.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_8.async().run()
        TimeUnit.SECONDS.sleep(4L)
        animate_2.async().run()
        TimeUnit.SECONDS.sleep(5L)
        say_9.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_10.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_11.async().run()
        TimeUnit.SECONDS.sleep(2L)
        say_12.async().run()
        TimeUnit.SECONDS.sleep(2L)
        animate_1.async().run()
        TimeUnit.SECONDS.sleep(10L)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")
    }
    private fun startOrStopTimer(){

        val countTime: TextView = findViewById(R.id.time10)
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
        val countTime: TextView = findViewById(R.id.time10)
        countTime.text = remainingTimeInMillis.convertToTimeFormat()
    }

    fun Long.convertToTimeFormat(): String {
        val minutes = (this / 1000).toInt() / 60
        val seconds = (this / 1000).toInt() % 60
        return java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}