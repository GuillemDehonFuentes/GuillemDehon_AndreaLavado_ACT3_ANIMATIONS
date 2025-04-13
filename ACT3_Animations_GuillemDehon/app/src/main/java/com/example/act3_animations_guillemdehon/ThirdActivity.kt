package com.example.act3_animations_guillemdehon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.media.MediaPlayer
class ThirdActivity : AppCompatActivity() {
    private var isLeft = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val btnMove = findViewById<Button>(R.id.btnMove)
        btnMove.setOnClickListener {
            playSFX(R.raw.button_click)

            val params = it.layoutParams as RelativeLayout.LayoutParams
            if (isLeft) {
                params.removeRule(RelativeLayout.ALIGN_PARENT_START)
                params.addRule(RelativeLayout.ALIGN_PARENT_END)
            } else {
                params.removeRule(RelativeLayout.ALIGN_PARENT_END)
                params.addRule(RelativeLayout.ALIGN_PARENT_START)
            }
            it.layoutParams = params
            isLeft = !isLeft

            it.animate()
                .setDuration(300)
                .rotationBy(360f)
                .start()
        }
    }

    private fun playSFX(soundResId: Int) {
        val sfx = MediaPlayer.create(this, soundResId)
        sfx.setOnCompletionListener {
            it.release()
        }
        sfx.start()
    }
}