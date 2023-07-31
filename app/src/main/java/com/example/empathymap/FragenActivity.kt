package com.example.empathymap

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class FragenActivity : AppCompatActivity(), RobotLifecycleCallbacks {

    val timer = Timer()
    var seconds = 0
    var isPaused = false
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
        setContentView(R.layout.activity_fragen)
        QiSDK.register(this, this)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {

        //val btnskip: Button = findViewById(R.id.button_skip) //skip questions
        //val btnpause: Button = findViewById(R.id.button_pause) //pause timer

        //btnskip.setOnClickListener {
          //  val intent = Intent(this, FragenActivity1::class.java)
            //startActivity(intent)
       // }

        //btnpause.setOnClickListener {
          //  isPaused = isPaused == false
        //}

        //val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        //val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)

        //val say_1 = SayBuilder.with(qiContext)
          //  .withText("Ok, die Aufgabe zum Einstieg ist")
            //.build()
        //val say_2 = SayBuilder.with(qiContext)
          //  .withText(
            //    "Öffnet bitte Eure Empathy Map in Miro und notiert die Namen " +
              //          "der Gruppenmitglieder in der Ecke oben rechts. "
           // )
            //.build()
        //val say_3 = SayBuilder.with(qiContext)
        //  .withText("Ihr habt dafür 3 Minuten. Zeit beginnt jetzt")
        //  .build()
        //val say_4 = SayBuilder.with(qiContext)
        //    .withText("Prima, das hat schon mal gut funktioniert.")
        //  .build()
        //val say_5 = SayBuilder.with(qiContext)
        //  .withText("")
        //  .build()

        //val first1: Future<Void> = say_1.async().run()
        //val ballet: Future<Void> = first1.thenCompose {
        //    TimeUnit.SECONDS.sleep(1L)
        //  say_2.async().run()
        //}.thenCompose {
        //    TimeUnit.SECONDS.sleep(2L)
        //    say_3.async().run()
        //}.thenCompose {
        //    TimeUnit.SECONDS.sleep(1L)
        //    mediaPlayerStart.start()
        //    timerIsRunning = true
        //    timer.scheduleAtFixedRate(500L, 1000L) {
        //        if (!isPaused) {
        //            if (seconds > timeSpanInSec) {
        //                isPaused = true
        //                timer.cancel()
        //                this@FragenActivity.runOnUiThread(java.lang.Runnable {
        //                  val countTime: TextView = findViewById(R.id.time)
        //                  countTime.text = "Finished"
        //              })
        //              isFinished = true
        //              timerIsRunning = false
        //          } else {
        //              seconds += 1
        //              val texti = seconds.convertToTimeFormat2()
        //                this@FragenActivity.runOnUiThread(java.lang.Runnable {
        //              val countTime: TextView = findViewById(R.id.time)
        //              countTime.text = texti
        //              })
        //          }
        //      } else {
        //          this@FragenActivity.runOnUiThread(java.lang.Runnable {
        //              val countTime: TextView = findViewById(R.id.time)
        //              countTime.text = "Paused"
        //          })
        //      }
        //  }
        //  while (isFinished != true) {
        //      TimeUnit.SECONDS.sleep(1L)
        //  }
        //  mediaPlayerStop.start()
        //  say_5.async().run()
        //  TimeUnit.SECONDS.sleep(2L)
        //  say_4.async().run()
        //}.thenCompose {
        //    TimeUnit.SECONDS.sleep(3L)
        //val intent = Intent(this, FragenActivity1::class.java)
        //  startActivity(intent)
        //  say_5.async().run()
        //}

    }


    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
        //timer.cancel()

    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")

    }

    //fun Int.convertToTimeFormat2(): String {
       // val minutes = this .toInt() / 60
        //val seconds = this .toInt() % 60
        //return java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    //}

}






