package com.dineshdk.workoutapplication

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dineshdk.workoutapplication.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exTimer: CountDownTimer? = null
    private var exProgress = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        if (supportActionBar!= null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        setUpRestView()

    }
    private fun setUpRestView(){
        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setUpExView(){

        binding.flProgressbar.visibility = View.INVISIBLE
        binding.tvTitle.text = "Exercise Name"
        binding.flExerciseView.visibility = View.VISIBLE

        if (exTimer != null){
            exTimer?.cancel()
            exProgress = 0
        }
        setExerciseProgressBar()
    }

    private fun setRestProgressBar(){
        binding.progressbar.progress = restProgress

        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding.progressbar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()

            }

            override fun onFinish() {
                setUpExView()

            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding.progressbarEx.progress = exProgress

        exTimer = object : CountDownTimer(30000,1000){
            override fun onTick(p0: Long) {
                exProgress++
                binding.progressbarEx.progress = 30 - exProgress
                binding.tvTimerEX.text = (30 - exProgress).toString()

            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "go to rest", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}