package com.example.act3_animations_guillemdehon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.Intent
import android.widget.ImageView
import android.widget.Button
import android.graphics.drawable.AnimationDrawable

class MainActivity : AppCompatActivity() {

    private lateinit var characterImage: ImageView
    private lateinit var idleAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuración animación idle
        characterImage = findViewById(R.id.character)
        startIdleAnimation()

        // Botones de ataque
        findViewById<Button>(R.id.btnAttackA).setOnClickListener {
            playAttackAnimation(R.drawable.attack_a_animation)
        }

        findViewById<Button>(R.id.btnAttackB).setOnClickListener {
            playAttackAnimation(R.drawable.attack_b_animation)
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
        // Detener animación actual
        if (::idleAnimation.isInitialized) {
            idleAnimation.stop()
        }

        // Configurar y comenzar animación de ataque
        characterImage.setBackgroundResource(animationRes)
        val attackAnimation = characterImage.background as AnimationDrawable
        attackAnimation.start()

        // Calcular duración total del ataque
        val attackDuration = attackAnimation.numberOfFrames * 100 // 100ms por frame

        // Restaurar idle después del ataque
        Handler(Looper.getMainLooper()).postDelayed({
            attackAnimation.stop()
            startIdleAnimation() // Reiniciar animación idle correctamente
        }, attackDuration.toLong())
    }
}
