package com.amachewrs.sw5ecompanionapp.customization

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.databinding.CustomizationsDetailsBinding

class CustomizationsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: CustomizationsDetailsBinding
    private lateinit var customOption: CustomizationOption

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CustomizationsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        customOption = getOption()

        binding.Title.text = customOption.name

        binding.SourceBook.text = customOption.source

        binding.CustomsText.text = buildSpannedString {
            append(if (customOption.hasPreq()) "Prerequisite: "+ customOption.preq + "\n\n" else {""})
            append(customOption.text)
        }

        binding.BackButton.setOnClickListener { returntomain() }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })


    }
    @Suppress("DEPRECATION")
    private fun getOption(): CustomizationOption{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getParcelableExtra("Customization Option", CustomizationOption::class.java)!!
        else intent.getParcelableExtra("Customization Option")!!
    }

    private fun returntomain() {
        finish()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}