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

class FragenActivity10 : AppCompatActivity(), RobotLifecycleCallbacks {

    val timer = Timer()
    var seconds = 0
    var isPaused = false
    var timerIsRunning = false
    var timeSpanInSec = 300
    var isFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_fragen10)
        QiSDK.register(this, this)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {

        val phraseSetYes: PhraseSet = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
            .withTexts("ja") // Add the phrases Pepper will listen to.
            .build() // Build the PhraseSet.

        val phraseSetNo: PhraseSet = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
            .withTexts("nein") // Add the phrases Pepper will listen to.
            .build() // Build the PhraseSet.

        val listen10: Listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
            .withPhraseSets(phraseSetYes, phraseSetNo) // Set the PhraseSets to listen to.
            .build() // Build the listen action.

        val btnskip: Button = findViewById(R.id.button_skip10) //skip questions
        val btnpause: Button = findViewById(R.id.button_pause10) //pause timer

        btnskip.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnpause.setOnClickListener {
            isPaused = isPaused == false
        }

        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)
        val mediaPlayerWave = MediaPlayer.create(this, R.raw.introducing)
        val mediaPlayerNod = MediaPlayer.create(this, R.raw.little_ring_for_nod)
        val mediaPlayerGruss = MediaPlayer.create(this, R.raw.gruss)

        val animation_1: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.hello_a001).build() // Set the animation resource.
        val animation_2: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.salute_right_b001).build()
        val animation_3: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.affirmation_a001).build()

        val animate_1: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_1)
            .build()
        animate_1.addOnStartedListener{
            mediaPlayerWave.start()
        }
        val animate_2: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_2)
            .build()
        animate_2.addOnStartedListener{
            mediaPlayerNod.start()
        }
        val animate_3: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_2)
            .build()
        animate_3.addOnStartedListener {
            mediaPlayerGruss.start()
        }

        val say_1 = SayBuilder.with(qiContext)
            .withText("Wir sind jetzt bei der letzten Frage. Die letzte Frage ist.")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText("Welche Lernerfahrung wünsche ich mir?")
            .build()
        val say_3 = SayBuilder.with(qiContext)
            .withText("Das kann zum Beispiel sin. Ich wünsche mir ein interaktives Lernangebot mit einem Experten. " +
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
                    "ihr brainstormen und eure Ideen in Miro notieren.")
            .build()
        val say_7 = SayBuilder.with(qiContext)
            .withText("Super! Ihr habt jetzt alle Fragen beantwortet und eure Antworten in Miro notiert.")
            .build()
        val say_8 = SayBuilder.with(qiContext)
            .withText("Seid Ihr mit dem Ergebnis zufrieden?")
            .build()
        val say_9 = SayBuilder.with(qiContext)
            .withText("Ihr könnt Eure Empathy Map noch später ergänzen und von Zeit zu Zeit aktualisieren.")
            .build()
        val say_9_1 = SayBuilder.with(qiContext)
            .withText("Das bin ich auch! Ich bin sehr stolz auf euch.")
            .build()
        val say_9_2 = SayBuilder.with(qiContext)
            .withText("Das müsst ihr jetzt auch noch nicht.")
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
        val say_13 = SayBuilder.with(qiContext)
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
            mediaPlayerStart.start()
            timerIsRunning = true
            timer.scheduleAtFixedRate(500L, 1000L) {
                if (!isPaused) {
                    if (seconds > timeSpanInSec) {
                        isPaused = true
                        timer.cancel()
                        this@FragenActivity10.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time10)
                            countTime.text = "Finished"
                        })
                        isFinished = true
                        timerIsRunning = false
                    } else {
                        seconds += 1
                        val texti = seconds.convertToTimeFormat2()
                        this@FragenActivity10.runOnUiThread(java.lang.Runnable {
                            val countTime: TextView = findViewById(R.id.time10)
                            countTime.text = texti
                        })
                    }
                } else {
                    this@FragenActivity10.runOnUiThread(java.lang.Runnable {
                        val countTime: TextView = findViewById(R.id.time10)
                        countTime.text = "Paused"
                    })
                }
            }
            while (isFinished != true) {
                TimeUnit.SECONDS.sleep(1L)
            }
            mediaPlayerStop.start()
            TimeUnit.SECONDS.sleep(2L)
            say_13.async().run()}.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_7.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_8.async().run()
            animate_3.async().run()}.thenCompose {

            var listenResult: ListenResult = listen10.run()
            var understood = false
            while (!understood){
                val matchedPhraseSet: PhraseSet = listenResult.matchedPhraseSet
                if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetYes)){
                    say_9_1.async().run()
                    understood = true
                }
                else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetNo)){
                    say_9_2.async().run()
                    understood = true
                } else {
                    listenResult = listen10.run()
                }
            }
            TimeUnit.SECONDS.sleep(1L)
            say_9.async().run()
            TimeUnit.SECONDS.sleep(1L)
            say_10.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_11.async().run() }.thenCompose {
            TimeUnit.SECONDS.sleep(1L)
            say_12.async().run()
            animate_1.async().run()}.thenCompose {
            TimeUnit.SECONDS.sleep(3L)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            say_13.async().run() }

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