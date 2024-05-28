package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.stopwatch.databinding.ActivityMainBinding
import kotlin.concurrent.timer

lateinit var text : TextView
lateinit var bar : LinearLayout
lateinit var play : ImageButton
lateinit var stop : ImageButton
lateinit var reset : ImageButton
lateinit  var binding : ActivityMainBinding

class MainActivity : AppCompatActivity() {
        var isRunning = false
        var timerMilliseconds = 0L
        val handler = Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        override fun run() {
            timerMilliseconds += 10
            val totalSeconds = timerMilliseconds / 1000
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            val milliseconds = (timerMilliseconds % 1000) / 10

            val time = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
            binding.text.text = time

            handler.postDelayed(this, 10)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        text = findViewById(R.id.text)
        stop = findViewById(R.id.stop)
        reset = findViewById(R.id.reset)
        bar = findViewById(R.id.bar)
        play = findViewById(R.id.play)

        binding.play.setOnClickListener {
            startTimer()
        }
        binding.stop.setOnClickListener {
            stopTimer()
        }
        binding.reset.setOnClickListener {
            resetTimer()
        }
    }

    fun startTimer(){
        if(!isRunning){
            handler.postDelayed(runnable, 1000)
            isRunning = true

            binding.stop.isVisible = true
            binding.play.isVisible = false
            binding.reset.isVisible = true

            val barParams = binding.bar.layoutParams as ViewGroup.MarginLayoutParams
            barParams.width = 700 // Set the desired width in pixels
            barParams.marginStart = 170
            binding.bar.layoutParams = barParams


        }
    }

    fun stopTimer(){
        if(isRunning){
            handler.removeCallbacks(runnable)
            isRunning = false
            binding.stop.isVisible = false
            binding.play.isVisible = true
            binding.reset.isVisible = true
        }
    }

    fun resetTimer(){
        stopTimer()
        timerMilliseconds = 0L
        binding.text.text = "00:00:00"
        binding.stop.isVisible = true
        binding.play.isVisible = true
        binding.reset.isVisible = false
    }
}