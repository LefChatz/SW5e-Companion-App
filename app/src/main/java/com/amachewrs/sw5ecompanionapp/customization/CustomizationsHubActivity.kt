package com.amachewrs.sw5ecompanionapp.customization

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.CustomizationsHubBinding
import java.util.LinkedList

class CustomizationsHubActivity : AppCompatActivity() {

    private lateinit var binding: CustomizationsHubBinding
    private lateinit var tempView: View
    private var mode=0
    private var customizations= mutableSetOf<CustomizationOption>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CustomizationsHubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val customizationsHeap = LinkedList<CharSequence>()
        resources.getStringArray(R.array.customization_options).toCollection(customizationsHeap)
        while (customizationsHeap.size>1){
            customizations.add(CustomizationOption(customizationsHeap.poll()!!.toString(),"","",customizationsHeap.poll()!!.toString().toBoolean(),""))
        }

        generateOptions()

        binding.BackButton.setOnClickListener { returntomain() }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    private fun generateOptions(){

        val starPaint = Paint()
        starPaint.textSize = 24F
        starPaint.typeface = resources.getFont(R.font.starjedi)
        starPaint.letterSpacing = 0.1F

        customizations.forEach { option ->
            val bt = layoutInflater.inflate(if (starPaint.measureText(option.name) > (windowManager.currentWindowMetrics.bounds.width() - 705))R.layout.customizations_button_big else R.layout.customizations_button,binding.ll,false)
            bt.findViewById<TextView>(R.id.customization_option).text=option.name.replace("_"," ")
            bt.setOnClickListener{ startActivity(Intent(this, CustomizationsActivity::class.java).putExtra("Customization Option",option.name)) }
            binding.ll.addView(bt)
        }
    }

    private fun returntomain() {
        finish()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}