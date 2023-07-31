package com.example.empathymap

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.GoToBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.builder.TransformBuilder
import com.aldebaran.qi.sdk.`object`.actuation.Actuation
import com.aldebaran.qi.sdk.`object`.actuation.Frame
import com.aldebaran.qi.sdk.`object`.actuation.FreeFrame
import com.aldebaran.qi.sdk.`object`.actuation.GoTo
import com.aldebaran.qi.sdk.`object`.actuation.Mapping
import com.aldebaran.qi.sdk.`object`.actuation.PathPlanningPolicy
import com.aldebaran.qi.sdk.`object`.geometry.Transform
import com.aldebaran.qi.sdk.`object`.touch.Touch
import com.aldebaran.qi.sdk.`object`.touch.TouchSensor
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.scheduleAtFixedRate



class FragenActivity1 : AppCompatActivity(), RobotLifecycleCallbacks {

    val timer = Timer()
    val timerx = Timer()
    var seconds = 0
    var isPaused = false
    var timerIsRunning = false
    var timeSpanInSec = 180
    var isFinished = false
    private var qiContext: QiContext? = null
    private var actuation: Actuation? = null
    private var mapping: Mapping? = null
    var timerxIsRunning = false
    var beginn = true


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
        Handler().postDelayed({
        }, 37000)

    }

    override fun onRobotFocusGained(qiContext: QiContext?) {

        this.qiContext = qiContext;
        actuation = qiContext?.actuation
        mapping = qiContext?.mapping

        this@FragenActivity1.runOnUiThread(java.lang.Runnable {
            val titel: TextView = findViewById(R.id.fragen)
            titel.text = "Aufgabe zum Einstieg"
        })
        this@FragenActivity1.runOnUiThread(java.lang.Runnable {
            val aufgabe: TextView = findViewById(R.id.textView2)
            aufgabe.text = "Öffnet bitte Eure Empathy Map in Miro und notiert die Namen " +
                    "der Gruppenmitglieder in der Ecke oben rechts. "
        })
        this@FragenActivity1.runOnUiThread(java.lang.Runnable {
            val seitenzahl: TextView = findViewById(R.id.textView4)
            seitenzahl.text = " "
        })

        val btnskip: Button = findViewById(R.id.button_skip1) //skip questions
        val btnpause: Button = findViewById(R.id.button_pause1) //pause timer

        btnskip.setOnClickListener {
            if (beginn == true){
                beginn = false
                isFinished = true
            }else {
                val intent = Intent(this, FragenActivity2::class.java)
                startActivity(intent)
            }
        }

        btnpause.setOnClickListener {
            isPaused = isPaused == false
        }

        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)
        val mediaPlayerString = MediaPlayer.create(this, R.raw.lil_strings)

        val animation_4: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.affirmation_a001).build()
        val animate_4: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_4)
            .build()
        animate_4.addOnStartedListener {
            mediaPlayerString.start()
        }

        val say_1 = SayBuilder.with(qiContext)
            .withText("Ok, jetzt geht es weiter mit der ersten Frage zum Empathy Mapping.")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText("Versetzt euch in eure Persona hinein. Die erste Frage ist: ")
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
            .withText("Ok, erste Frage geschafft. Ab jetzt gehen wir die restlichen Fragen im Uhrzeigersinn durch." +
                    "Ab jetzt ist es wichtig, dass ihr jede Frage mit dem Lernziel in Verbindung bringt.")
            .build()
        val say_01 = SayBuilder.with(qiContext)
            .withText("Ok, die Aufgabe zum Einstieg ist")
            .build()
        val say_02 = SayBuilder.with(qiContext)
            .withText("Öffnet bitte Eure Empathy Map in Miro und notiert die Namen " +
                    "der Gruppenmitglieder in der Ecke oben rechts. ")
            .build()
        val say_03 = SayBuilder.with(qiContext)
            .withText("Ihr habt dafür 3 Minuten. Zeit beginnt jetzt")
            .build()
        val say_04 = SayBuilder.with(qiContext)
            .withText("Prima, das hat schon mal gut funktioniert.")
            .build()
        val say_05 = SayBuilder.with(qiContext)
            .withText("")
            .build()



        val first1: Future<Void> = say_01.async().run()
        val ballet: Future<Void>  = first1.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_02.async().run()
        }.thenCompose {
            TimeUnit.SECONDS.sleep(2L)
            say_03.async().run()
        }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            mediaPlayerStart.start()
            timerxIsRunning = true
            timerx.scheduleAtFixedRate(500L, 1000L) {
                if (!isPaused) {
                    if (seconds > timeSpanInSec) {
                        isPaused = true
                        timerx.cancel()
                        this@FragenActivity1.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time1)
                            countTime.text = "Finished"
                        })
                        isFinished = true
                        timerxIsRunning = false
                    } else {
                        seconds += 1
                        val texti = seconds.convertToTimeFormat2()
                        this@FragenActivity1.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time1)
                            countTime.text = texti
                        })
                    }
                } else {
                    this@FragenActivity1.runOnUiThread(java.lang.Runnable {
                        val countTime: TextView = findViewById(R.id.time1)
                        countTime.text = "Paused"
                    })
                }
            }
            while (isFinished != true && beginn == true) {
                TimeUnit.SECONDS.sleep(1L)
            }
            mediaPlayerStop.start()
            timerx.cancel()
            say_04.async().run()
        }.thenCompose {
            TimeUnit.SECONDS.sleep(2L)
            say_05.async().run()
            TimeUnit.SECONDS.sleep(2L)
            isPaused = false
            beginn = false
            timeSpanInSec = 180
            isFinished = false
            seconds = 0
            this@FragenActivity1.runOnUiThread(java.lang.Runnable {
                val titel: TextView = findViewById(R.id.fragen)
                titel.text = "F1: Wer bin ich?"
            })
            this@FragenActivity1.runOnUiThread(java.lang.Runnable {
                val aufgabe: TextView = findViewById(R.id.textView2)
                aufgabe.text = "Hier ein Beispiel: Ich bin Annika Stolz, 35 Jahre alt, Account Manager bei Cisco und möchte überzeugend präsentieren lernen"
            })
            this@FragenActivity1.runOnUiThread(java.lang.Runnable {
                val seitenzahl: TextView = findViewById(R.id.textView4)
                seitenzahl.text = "1/10"
            })
            TimeUnit.SECONDS.sleep(1L)
            say_1.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_2.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_3.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_4.async().run()  }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_5.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_6.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            mediaPlayerStart.start()
            animate_4.async().run()}.thenCompose {
            timerIsRunning = true
            isPaused = false
            isFinished = false
            seconds = 0
            //saveLocation("erste")
            timer.scheduleAtFixedRate(500L, 1000L) {
                if (!isPaused) {
                    if (seconds > timeSpanInSec) {
                        isPaused = true
                        timer.cancel()
                        this@FragenActivity1.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time1)
                            countTime.text = "Finished"
                        })
                        isFinished = true
                        timerIsRunning = false
                    } else {
                        seconds += 1
                        val texti = seconds.convertToTimeFormat2()
                        this@FragenActivity1.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time1)
                            countTime.text = texti
                        })
                    }
                } else {
                    this@FragenActivity1.runOnUiThread(java.lang.Runnable {
                        val countTime: TextView = findViewById(R.id.time1)
                        countTime.text = "Paused"
                    })
                }
            }
            while (isFinished != true) {
                TimeUnit.SECONDS.sleep(1L)
            }
            mediaPlayerStop.start()
            animate_4.async().run()}.thenCompose {
            say_7.async().run() }.thenCompose {
            val intent = Intent(this, FragenActivity2::class.java)
            startActivity(intent)
            TimeUnit.SECONDS.sleep(2L)
            say_7.async().run()  }.thenCompose {
            animate_4.async().run()}
            timer.cancel()
            //headTouchSensor?.removeAllOnStateChangedListeners()

    }

    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
        qiContext = null
        timer.cancel()
        timerx.cancel()
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
