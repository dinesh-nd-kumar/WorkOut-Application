package com.dineshdk.workoutapplication

import android.content.ContentResolver
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dineshdk.workoutapplication.databinding.ActivityExerciseBinding
import java.lang.Exception
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExerciseBinding

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exTimer: CountDownTimer? = null
    private var exProgress = 0

    private var exerciseList : ArrayList<Exercise>? = null
    private var currentExercisePosition = -1

    private var mTextToSpeech:TextToSpeech? = null

    private var player : MediaPlayer? = null

    private var statusAdapter :ExerciseStatusAdapter? = null


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

        try {
//            val soundUri = Uri.parse("android.resource://"+packageName+"/"+ R.raw.press_start)
            val soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName+ "/" + R.raw.press_start)
            player = MediaPlayer.create(this,soundUri)
            player?.isLooping = false

        }catch (e :  Exception){
            e.printStackTrace()
        }

        exerciseList = Constants.defaultExerciseList()

        mTextToSpeech = TextToSpeech(this,this)
        setUpRestView()
        setUpStatusView()

    }

    private fun setUpStatusView(){
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(
            this,LinearLayoutManager.HORIZONTAL,false)
        statusAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding.rvExerciseStatus.adapter = statusAdapter



    }
    private fun setUpRestView(){

        player?.start()

        binding.flRestView.visibility = VISIBLE
        binding.tvTitle.visibility = VISIBLE
        binding.tvExerciseName.visibility = INVISIBLE
        binding.flExerciseView.visibility = INVISIBLE
        binding.ivImage.visibility = INVISIBLE
        binding.tvUpcomingLable.visibility = VISIBLE
        binding.tvUpcomingName.visibility = VISIBLE

        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding.tvUpcomingName.text = exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()
    }

    private fun setUpExView(){

        binding.flRestView.visibility = INVISIBLE
        binding.tvTitle.visibility = INVISIBLE
        binding.tvExerciseName.visibility = VISIBLE
        binding.flExerciseView.visibility = VISIBLE
        binding.ivImage.visibility = VISIBLE
        binding.tvUpcomingLable.visibility = INVISIBLE
        binding.tvUpcomingName.visibility = INVISIBLE

        if (exTimer != null){
            exTimer?.cancel()
            exProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

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
                exerciseList!![currentExercisePosition].setIsSelected(true)
                statusAdapter!!.notifyDataSetChanged()
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

                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                statusAdapter!!.notifyDataSetChanged()

                if(currentExercisePosition < exerciseList!!.size -1){
                    setUpRestView()
                }else {
                    /*Toast.makeText(
                        this@ExerciseActivity,
                        "Congrats! you complete exercise",
                        Toast.LENGTH_SHORT
                    ).show()*/
                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }
    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS){
            val result = mTextToSpeech!!.setLanguage(Locale.US)
            if ( result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("tts", "onInit: the language specified not supported", )
            }
        } else{
            Log.e("tts", "onInit: initialisation failed", )
        }

    }

    private fun speakOut(text: String): Unit {
        mTextToSpeech!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")

    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (exTimer != null) {
            exTimer?.cancel()
            exProgress = 0
        }
        if (mTextToSpeech != null) {
            mTextToSpeech!!.stop()
            mTextToSpeech!!.shutdown()
        }
        if (player != null){
            player!!.stop()
        }


    }

}