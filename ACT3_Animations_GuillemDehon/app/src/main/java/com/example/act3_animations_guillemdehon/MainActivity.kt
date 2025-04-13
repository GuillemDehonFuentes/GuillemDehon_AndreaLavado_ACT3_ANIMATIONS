package com.example.act3_animations_guillemdehon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.Intent
import android.widget.ImageView
import android.widget.Button
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer

class MainActivity : AppCompatActivity() {

    private lateinit var characterImage: ImageView
    private lateinit var idleAnimation: AnimationDrawable
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterImage = findViewById(R.id.character)
        startIdleAnimation()

        // Iniciar música de fondo
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        findViewById<Button>(R.id.btnAttackA).setOnClickListener {
            playAttackAnimation(R.drawable.attack_a_animation)
            playSFX(R.raw.attack_sound)
        }

        findViewById<Button>(R.id.btnAttackB).setOnClickListener {
            playAttackAnimation(R.drawable.attack_b_animation)
            playSFX(R.raw.attack_sound)
        }

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    private fun startIdleAnimation() {
        characterImage.setBackgroundResource(R.drawable.idle_animation)
        idleAnimation = characterImage.background as AnimationDrawable
        idleAnimation.start()
    }

    private fun playAttackAnimation(animationRes: Int) {
        if (::idleAnimation.isInitialized) {
            idleAnimation.stop()
        }

        characterImage.setBackgroundResource(animationRes)
        val attackAnimation = characterImage.background as AnimationDrawable
        attackAnimation.start()

        val attackDuration = attackAnimation.numberOfFrames * 100
        Handler(Looper.getMainLooper()).postDelayed({
            attackAnimation.stop()
            startIdleAnimation()
        }, attackDuration.toLong())
    }

    private fun playSFX(soundResId: Int) {
        val sfx = MediaPlayer.create(this, soundResId)
        sfx.setOnCompletionListener {
            it.release()
        }
        sfx.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}