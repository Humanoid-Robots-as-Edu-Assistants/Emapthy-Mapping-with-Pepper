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
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.ListenBuilder
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.`object`.conversation.Listen
import com.aldebaran.qi.sdk.`object`.conversation.ListenResult
import com.aldebaran.qi.sdk.`object`.conversation.PhraseSet
import com.aldebaran.qi.sdk.util.PhraseSetUtil
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.scheduleAtFixedRate

class FragenActivity5 : AppCompatActivity(), RobotLifecycleCallbacks {

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
        setContentView(R.layout.activity_fragen5)

        QiSDK.register(this, this)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {

        val phraseSetYes: PhraseSet = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
            .withTexts("ja") // Add the phrases Pepper will listen to.
            .build() // Build the PhraseSet.

        val phraseSetNo: PhraseSet = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
            .withTexts("nein") // Add the phrases Pepper will listen to.
            .build() // Build the PhraseSet.

        val listen5: Listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
            .withPhraseSets(phraseSetYes, phraseSetNo) // Set the PhraseSets to listen to.
            .build() // Build the listen action.

        val btnskip: Button = findViewById(R.id.button_skip5) //skip questions
        val btnpause: Button = findViewById(R.id.button_pause5) //pause timer

        btnskip.setOnClickListener {
            val intent = Intent(this, FragenActivity6::class.java)
            startActivity(intent)
        }

        btnpause.setOnClickListener {
            isPaused = isPaused == false
        }

        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)
        val mediaPlayerWave = MediaPlayer.create(this, R.raw.introducing)

        val animation_1: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.nicereaction_a001).build()
        val animate_1: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_1)
            .build()
        animate_1.addOnStartedListener{
            mediaPlayerWave.start()
        }

        val say_1 = SayBuilder.with(qiContext)
            .withText("Oki doki. Könnt ihr euch eure Persona jetzt besser vorstellen?")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText(" Sehr gut.")
            .build()
        val say_2_1 = SayBuilder.with(qiContext)
            .withText("Was für eine deprimierende Existenz, weniger Vorstellungskraft als ein Roboter zu haben.")
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
            .withText("Ich kann Folien gut gestalten.")
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
        val say_10 = SayBuilder.with(qiContext)
            .withText("")
            .build()

        val first1: Future<Void> = say_1.async().run()
        val ballet: Future<Void> = first1.thenCompose {
            var listenResult: ListenResult = listen5.run()
            var understood = false
            while (!understood){
                val matchedPhraseSet: PhraseSet = listenResult.matchedPhraseSet
                if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetYes)){
                    say_2.async().run()
                    understood = true
                }
                else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetNo)){
                    say_2_1.async().run()
                    understood = true
                } else {
                    listenResult = listen5.run()
                }
            }
            TimeUnit.SECONDS.sleep(1L)
            say_3.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_4.async().run() }.thenCompose{
            TimeUnit.SECONDS.sleep(1L)
            say_5.async().run()}.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_6.async().run() }.thenCompose {
            say_7.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_8.async().run() }.thenCompose {
            say_9.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            mediaPlayerStart.start()
            timerIsRunning = true
            timer.scheduleAtFixedRate(500L, 1000L) {
                if (!isPaused) {
                    if (seconds > timeSpanInSec) {
                        isPaused = true
                        timer.cancel()
                        this@FragenActivity5.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time5)
                            countTime.text = "Finished"
                        })
                        isFinished = true
                        timerIsRunning = false
                    } else {
                        seconds += 1
                        val texti = seconds.convertToTimeFormat2()
                        this@FragenActivity5.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time5)
                            countTime.text = texti
                        })
                    }
                } else {
                    this@FragenActivity5.runOnUiThread(java.lang.Runnable {
                        val countTime: TextView = findViewById(R.id.time5)
                        countTime.text = "Paused"
                    })
                }
            }
            while (isFinished != true) {
                TimeUnit.SECONDS.sleep(1L)
            }
            mediaPlayerStop.start()
            say_10.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(3L)
            val intent = Intent(this, FragenActivity6::class.java)
            startActivity(intent)
            TimeUnit.SECONDS.sleep(2L)
            say_10.async().run()
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