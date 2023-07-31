package com.example.empathymap

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.scheduleAtFixedRate

class FragenActivity2 : AppCompatActivity(), RobotLifecycleCallbacks {

    val timer1 = Timer()
    var seconds = 0
    var isPaused = true
    var timerIsRunning = false
    var timeSpanInSec = 180
    var isFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // fullscreen view
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide() // hides the action bar
        setContentView(R.layout.activity_fragen2)
        QiSDK.register(this, this)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {

        val btnskip: Button = findViewById(R.id.button_skip2) //skip questions
        val btnpause: Button = findViewById(R.id.button_pause2) //pause timer

        btnskip.setOnClickListener {
            val intent = Intent(this, FragenActivity3::class.java)
            startActivity(intent)
        }

        btnpause.setOnClickListener {
            isPaused = isPaused == false
        }

        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)
        val mediaPlayerString = MediaPlayer.create(this, R.raw.lil_strings)
        val mediaPlayerWave = MediaPlayer.create(this, R.raw.introducing)

        val animation_1: Animation =
            AnimationBuilder.with(qiContext) // Create the builder with the context.
                .withResources(R.raw.nicereaction_a001).build()
        val animate_1: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_1)
            .build()
        animate_1.addOnStartedListener {
            mediaPlayerWave.start()
        }
        val animation_4: Animation =
            AnimationBuilder.with(qiContext) // Create the builder with the context.
                .withResources(R.raw.affirmation_a001).build()
        val animate_4: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_4)
            .build()
        animate_4.addOnStartedListener {
            mediaPlayerString.start()
        }

        val say_1 = SayBuilder.with(qiContext)
            .withText("Ok, die zweite Frage ist.")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText("Wie fühle ich mich?")
            .build()
        val say_3 = SayBuilder.with(qiContext)
            .withText(
                "Wenn ihr jetzt die Frage mit dem Lernziel überzeugend präsentieren " +
                        "in Verbindung bringt, dann könnte die Antwort so lauten.")
            .build()
        val say_4 = SayBuilder.with(qiContext)
            .withText("Das Präsentieren stresst mich. Ich werde nervös, wenn ich vor großen Gruppen spreche.")
            .build()
        val say_7 = SayBuilder.with(qiContext)
            .withText(
                "Eure Aufgabe ist jetzt die Frage. Wie fühle ich mich. " +
                        "mit dem Lernziel in Verbindung zu bringen und eine Antwort in Miro zu notieren. Für diese Aufgabe habt ihr 3 Minuten Zeit."
            )
            .build()
        val say_8 = SayBuilder.with(qiContext)
            .withText("")
            .build()

        val first1: Future<Void> = say_1.async().run()
        val ballet: Future<Void> = first1.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_2.async().run( )}.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_3.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_4.async().run()}.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            animate_1.async().run()}.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_7.async().run()}.thenCompose {
            mediaPlayerStart.start()
            isPaused = false
            timerIsRunning = true
            timer1.scheduleAtFixedRate(500L, 1000L) {
                if (!isPaused) {
                    if (seconds > timeSpanInSec) {
                        isPaused = true
                        timer1.cancel()
                        this@FragenActivity2.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time2)
                            countTime.text = "Finished"
                        })
                        isFinished = true
                        timerIsRunning = false
                    } else {
                        seconds += 1
                        val texti = seconds.convertToTimeFormat2()
                        this@FragenActivity2.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time2)
                            countTime.text = texti
                        })
                    }
                } else {
                    this@FragenActivity2.runOnUiThread(java.lang.Runnable {
                        val countTime: TextView = findViewById(R.id.time2)
                        countTime.text = "Paused"
                    })
                }

            }
            while (isFinished != true) {
                TimeUnit.SECONDS.sleep(1L)
            }
            mediaPlayerStop.start()
            animate_4.async().run()}.thenCompose {
            val intent = Intent(this, FragenActivity3::class.java)
            startActivity(intent)
            TimeUnit.SECONDS.sleep(2L)
            say_8.async().run()}
    }



    override fun onRobotFocusLost() {
        timer1.cancel()
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

