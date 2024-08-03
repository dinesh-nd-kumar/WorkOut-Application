package com.dineshdk.workoutapplication

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
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

    private var exerciseList : ArrayList<Exercise>? = null
    private var currentExercisePosition = -1


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

        exerciseList = Constants.defaultExerciseList()
        setUpRestView()

    }
    private fun setUpRestView(){

        binding.flRestView.visibility = VISIBLE
        binding.tvTitle.visibility = VISIBLE
        binding.tvExerciseName.visibility = INVISIBLE
        binding.flExerciseView.visibility = INVISIBLE
        binding.ivImage.visibility = INVISIBLE

        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setUpExView(){

        binding.flRestView.visibility = INVISIBLE
        binding.tvTitle.visibility = INVISIBLE
        binding.tvExerciseName.visibility = VISIBLE
        binding.flExerciseView.visibility = VISIBLE
        binding.ivImage.visibility = VISIBLE

        if (exTimer != null){
            exTimer?.cancel()
            exProgress = 0
        }
        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
        setExerciseProgressBar()
    }

    private fun setRestProgressBar(){
        binding.progressbar.progress = restProgress

        restTimer = object : CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding.progressbar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()

            }

            override fun onFinish() {
                currentExercisePosition++
                setUpExView()

            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding.progressbarEx.progress = exProgress

        exTimer = object : CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {
                exProgress++
                binding.progressbarEx.progress = 30 - exProgress
                binding.tvTimerEX.text = (30 - exProgress).toString()

            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList!!.size -1){
                    setUpRestView()
                }else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congrats! you complete exercise",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }
}