package com.example.sw5ecompanionapp.customization

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import com.example.sw5ecompanionapp.databinding.CustomizationsDetailsBinding

class CustomizationsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val customOption = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Customization Option", CustomizationOption::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("Customization Option")!!
        }
        val inflater=layoutInflater

        val binding = CustomizationsDetailsBinding.inflate(inflater)
        setContentView(binding.root)

        binding.Title.text = customOption.name

        binding.SourceBook.text = customOption.source

        val txt = buildSpannedString {
            append(if (customOption.hasPreq()) "Prerequisite: "+ customOption.preq + "\n\n" else {""})
            append(customOption.text)
        }

        binding.CustomsText.text = txt

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