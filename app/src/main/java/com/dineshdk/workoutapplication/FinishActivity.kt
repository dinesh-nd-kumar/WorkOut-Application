package com.dineshdk.workoutapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dineshdk.workoutapplication.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.finishButton.setOnClickListener {
            finish()
        }


    }
}