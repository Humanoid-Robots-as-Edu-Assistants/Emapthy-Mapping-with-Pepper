package com.example.gameapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), RobotLifecycleCallbacks{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // fullscreen view
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide() // hides the action bar
        setContentView(R.layout.activity_main)
        QiSDK.register(this, this)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)

        // animation for greetings
        val animation_1: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.hello_a001).build() // Set the animation resource.
        val animation_2: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.hello_a005).build()
        val animation_3: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.left_hand_high_b001).build()

        // Build the action.
        val animate_1: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_1)
            .build()
        val animate_2: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_2)
            .build()
        val animate_3: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_3)
            .build()

        // speech for the start button
        val bt1: Button = findViewById(R.id.button_start)
        val bt2: Button = findViewById(R.id.button_start2)

        val say_1 = SayBuilder.with(qiContext)
            .withText("Guten Morgen und herzlich willkommen zum Workshop Empathy Mapping")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText("Schön, dass Ihr alle heute hier seid.")
            .build()
        val say_3 = SayBuilder.with(qiContext)
            .withText("Ich heiße Pepper und moderiere heute den Workshop")
            .build()
        val say_4 = SayBuilder.with(qiContext)
            .withText("Wir haben ein spannendes Programm vor uns und ich bin sicher, dass wir gemeinsam viel Spaß haben werden")
            .build()
        val say_5 = SayBuilder.with(qiContext)
            .withText("Lasst uns also direkt beginnen!")
            .build()
        val say_6 = SayBuilder.with(qiContext)
            .withText("Das Ziel von unserem Workshop heute ist, dass ihr in kleinen Gruppen – eine Empathy Map in Miro entwickelt")
            .build()
        val say_7 = SayBuilder.with(qiContext)
            .withText("Eure Aufgabe heute ist es, die zukünftigen Nutzerinnen besser zu verstehen. Ihr stellt Euch dabei eine Persona vor, die Euren zukünftigen Nutzerinnen repräsentiert.")
            .build()
        val say_8 = SayBuilder.with(qiContext)
            .withText("Ich wisst, was eine Persona ist, oder?")
            .build()
        val say_9 = SayBuilder.with(qiContext)
            .withText("Super!")
            .build()
        val say_10 = SayBuilder.with(qiContext)
            .withText("Dann lasst uns mit Empathy Mapping beginnen. Ich werde Euch gleich mit Fragen und Zeitvorgaben leiten.")
            .build()
        val say_11 = SayBuilder.with(qiContext)
            .withText("Ihr könnt euch komplett auf den Inhalt konzentrieren. Ich übernehme die Zeitmessung")
            .build()
        val say_12 = SayBuilder.with(qiContext)
            .withText("Ihr hört die Glocke")
            .build()
        val say_13 = SayBuilder.with(qiContext)
            .withText("wenn ein Schritt beginnt, und den Gong ")
            .build()
        val say_14 = SayBuilder.with(qiContext)
            .withText("wenn ein Schritt und Ende ist. Alles klar?")
            .build()
        val say_15 = SayBuilder.with(qiContext)
            .withText("Schön!")
            .build()
        bt2.setOnClickListener {
            val intent = Intent(this, FragenActivity10::class.java)
            startActivity(intent)
        }
        bt1.setOnClickListener {
            // onclick robot speech
            say_1.async().run()
            // animation starts with speech
            animate_1.async().run()
            TimeUnit.SECONDS.sleep(9L)
            say_2.async().run()
            TimeUnit.SECONDS.sleep(2L)
            animate_2.async().run()
            TimeUnit.SECONDS.sleep(2L)
            say_3.async().run()
            TimeUnit.SECONDS.sleep(4L)
            say_4.async().run()
            TimeUnit.SECONDS.sleep(6L)
            say_5.async().run()
            TimeUnit.SECONDS.sleep(2L)
            say_6.async().run()
            TimeUnit.SECONDS.sleep(7L)
            say_7.async().run()
            TimeUnit.SECONDS.sleep(8L)
            say_8.async().run()
            TimeUnit.SECONDS.sleep(4L)
            animate_3.async().run()
            TimeUnit.SECONDS.sleep(4L)
            say_9.async().run()
            TimeUnit.SECONDS.sleep(2L)
            say_10.async().run()
            TimeUnit.SECONDS.sleep(6L)
            say_11.async().run()
            TimeUnit.SECONDS.sleep(6L)
            say_12.async().run()
            TimeUnit.SECONDS.sleep(2L)
            mediaPlayerStart.start()
            TimeUnit.SECONDS.sleep(2L)
            say_13.async().run()
            TimeUnit.SECONDS.sleep(2L)
            mediaPlayerStop.start()
            TimeUnit.SECONDS.sleep(2L)
            say_14.async().run()
            TimeUnit.SECONDS.sleep(2L)
            say_15.async().run()
            // waiting for the finishing of speech
            Handler().postDelayed({
                // switching to the MainActivity2
                val intent = Intent(this, FragenActivity::class.java)
                startActivity(intent)
            }, 1000)
        }

    }

    override fun onRobotFocusLost() {

    }

    override fun onRobotFocusRefused(reason: String?) {

    }
}