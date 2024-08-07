package com.example.sw5ecompanionapp.backgrounds

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.sw5ecompanionapp.databinding.BackgroundsDetailsBinding

class BackgroundsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: BackgroundsDetailsBinding
    private lateinit var inflater: LayoutInflater
    private lateinit var background: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        background = intent.getStringExtra("Background").toString()
        inflater=layoutInflater

        binding = BackgroundsDetailsBinding.inflate(inflater)
        setContentView(binding.root)

        binding.Title.text=background.replace("_"," ")

        generateDetails(background,binding.ll,resources,inflater,packageName)

        binding.BackButton.setOnClickListener { returntomain() }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })

    }

    private fun returntomain() {
        finish()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}