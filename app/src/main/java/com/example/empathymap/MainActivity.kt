package com.example.empathymap

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.GoToBuilder
import com.aldebaran.qi.sdk.builder.ListenBuilder
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.builder.TransformBuilder
import com.aldebaran.qi.sdk.`object`.actuation.Actuation
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.`object`.actuation.Frame
import com.aldebaran.qi.sdk.`object`.actuation.FreeFrame
import com.aldebaran.qi.sdk.`object`.actuation.GoTo
import com.aldebaran.qi.sdk.`object`.actuation.Mapping
import com.aldebaran.qi.sdk.`object`.conversation.Listen
import com.aldebaran.qi.sdk.`object`.conversation.ListenResult
import com.aldebaran.qi.sdk.`object`.conversation.PhraseSet
import com.aldebaran.qi.sdk.`object`.geometry.Transform
import com.aldebaran.qi.sdk.util.PhraseSetUtil
import java.util.*
import java.util.concurrent.TimeUnit

// variable for the text-to-speech
lateinit var talk: TextToSpeech

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
        val phraseSetYes: PhraseSet = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
            .withTexts("ja") // Add the phrases Pepper will listen to.
            .build() // Build the PhraseSet.

        val phraseSetNo: PhraseSet = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
            .withTexts("nein") // Add the phrases Pepper will listen to.
            .build() // Build the PhraseSet.

        val listen: Listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
            .withPhraseSets(phraseSetYes, phraseSetNo) // Set the PhraseSets to listen to.
            .build() // Build the listen action

        val mediaPlayerStart = MediaPlayer.create(this, R.raw.start)
        val mediaPlayerStop = MediaPlayer.create(this, R.raw.stop)
        val mediaPlayerGruss = MediaPlayer.create(this, R.raw.gruss)
        val mediaPlayerNod = MediaPlayer.create(this, R.raw.little_ring_for_nod)
        val mediaPlayerString = MediaPlayer.create(this, R.raw.lil_strings)
        val mediaPlayerGo = MediaPlayer.create(this, R.raw.beginn)

        // animation for greetings
        val animation_1: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.hello_a001).build() // Set the animation resource.
        val animation_2: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.hello_a005).build()
        val animation_3: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.left_hand_high_b001).build()
        val animation_4: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.affirmation_a001).build()
        val animation_5: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.both_hands_high_b001).build()

        // Build the action.
        val animate_1: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_1)
            .build()
        val animate_2: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_2)
            .build()
        animate_2.addOnStartedListener{
            mediaPlayerNod.start()
        }

        val animate_3: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_3)
            .build()
        animate_3.addOnStartedListener {
            mediaPlayerGruss.start()
        }

        val animate_4: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_4)
            .build()
        animate_4.addOnStartedListener {
            mediaPlayerString.start()
        }

        val animate_5: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation_5)
            .build()
        animate_5.addOnStartedListener{
            mediaPlayerGo.start()
        }


        val say_1 = SayBuilder.with(qiContext)
            .withText("Guten Morgen und herzlich Willkommen zum Workshop Empathy Mapping.")
            .build()
        val say_2 = SayBuilder.with(qiContext)
            .withText("Schön, dass Ihr alle hier seid.")
            .build()
        val say_3 = SayBuilder.with(qiContext)
            .withText("Ich heiße Pepper und moderiere den Workshop.")
            .build()
        val say_4 = SayBuilder.with(qiContext)
            .withText("Wir haben ein spannendes Programm vor uns und ich bin sicher, dass " +
                    "wir gemeinsam viel Spaß haben werden.")
            .build()
        val say_5 = SayBuilder.with(qiContext)
            .withText("Lasst uns also direkt beginnen.")
            .build()
        val say_6 = SayBuilder.with(qiContext)
            .withText("Das Ziel von unserem Workshop ist, dass ihr" +
                    "in kleinen Gruppen eine Empathy Map in Miro entwickelt.")
            .build()
        val say_7 = SayBuilder.with(qiContext)
            .withText("Eure Aufgabe heute ist es, die zukünftigen Nutzer innen besser zu verstehen. " +
                    "Ihr stellt Euch dabei eine Persona vor, die eure zukünftigen Nutzer innen repräsentiert.")
            .build()
        val say_8 = SayBuilder.with(qiContext)
            .withText("Ihr wisst, was eine Persona ist, oder?")
            .build()
        val say_9 = SayBuilder.with(qiContext)
            .withText("Super! ")
            .build()
        val say_10 = SayBuilder.with(qiContext)
            .withText("Dann lasst uns mit dem Empathy Mapping beginnen.")
            .build()
        val say_11 = SayBuilder.with(qiContext)
            .withText("Ich werde Euch mit Fragen und Zeitvorgaben leiten.")
            .build()
        val say_12 = SayBuilder.with(qiContext)
            .withText("Ihr könnt euch komplett auf den Inhalt konzentrieren.")
            .build()
        val say_13 = SayBuilder.with(qiContext)
            .withText("Ich übernehme die Zeitmessung.")
            .build()
        val say_14 = SayBuilder.with(qiContext)
            .withText("Ihr hört die Glocke")
            .build()
        val say_15 = SayBuilder.with(qiContext)
            .withText("wenn ein Schritt beginnt, und den Gong.")
            .build()

        val say_16 = SayBuilder.with(qiContext)
            .withText(" wenn ein Schritt zuende ist.")
            .build()
        val say_17 = SayBuilder.with(qiContext)
            .withText("Alles klar?")
            .build()
        val say_18 = SayBuilder.with(qiContext)
            .withText(" Schön! ")
            .build()
        val say_18_1 = SayBuilder.with(qiContext)
            .withText("Ist in Ordnung. Dann lernt ihr es einfach nach und nach.")
            .build()
        val say_9_1 = SayBuilder.with(qiContext)
            .withText("Wenn das stimmt könnt ihr ja gleich gehen!")
            .build()

        val btn1:Button = findViewById(R.id.button_start)
        val btn2:Button = findViewById(R.id.button_start2)

        btn2.setOnClickListener {
            val intent = Intent(this, FragenActivity1::class.java)
            startActivity(intent)
        }


        btn1.setOnClickListener {
            val first1: Future<Void> = animate_1.async().run()
            say_1.async().run()
            val ballet: Future<Void>  = first1.thenCompose {
                say_2.async().run()
                animate_3.async().run() }.thenCompose {
                say_3.async().run()
                animate_2.async().run()}.thenCompose {
                say_4.async().run() }.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                say_5.async().run() }.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                say_6.async().run() }.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                say_7.async().run() }.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                say_8.async().run() }.thenCompose {
                TimeUnit.SECONDS.sleep(1L)

                var listenResult: ListenResult = listen.run()
                var understood = false
                while (!understood){
                    val matchedPhraseSet: PhraseSet = listenResult.matchedPhraseSet
                    if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetYes)){
                        say_9.async().run()
                        understood = true
                    }
                    else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetNo)){
                        say_9_1.async().run()
                        understood = true
                    } else {
                        listenResult = listen.run()
                    }
                }

                animate_3.async().run()}.thenCompose {
                say_10.async().run() }.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                say_11.async().run() }.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                say_12.async().run() }.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                say_13.async().run() }.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                say_14.async().run() }.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                mediaPlayerStart.start()
                say_15.async().run()}.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                mediaPlayerStop.start()
                say_16.async().run()}.thenCompose {
                TimeUnit.SECONDS.sleep(1L)
                say_17.async().run() }.thenCompose {
                var listenResult: ListenResult = listen.run()
                var understood = false
                while (!understood){
                    val matchedPhraseSet: PhraseSet = listenResult.matchedPhraseSet
                    if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetYes)){
                        say_18.async().run()
                        understood = true
                    }
                    else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetNo)){
                        say_18_1.async().run()
                        understood = true
                    } else {
                        listenResult = listen.run()
                    }
                }
                TimeUnit.SECONDS.sleep(3L)
                animate_5.async().run()}.thenCompose {
                val intent = Intent(this, FragenActivity1::class.java)
                startActivity(intent)
                animate_4.async().run()

            }


        }

    }



    override fun onRobotFocusLost() {

    }

    override fun onRobotFocusRefused(reason: String?) {

    }


}