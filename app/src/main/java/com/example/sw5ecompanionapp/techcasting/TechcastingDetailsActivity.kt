package com.example.sw5ecompanionapp.techcasting

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.databinding.TechcastingTechpowerDetailsBinding

class TechcastingDetailsActivity : AppCompatActivity() {
    private lateinit var binding: TechcastingTechpowerDetailsBinding
    private lateinit var techpower: Techpower
    private lateinit var txt: TextView
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        techpower = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Techpower", Techpower::class.java).toTechpower()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Techpower>("Techpower").toTechpower()
        }
        binding= TechcastingTechpowerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text=techpower.printedname
        binding.title.alpha=0.0F

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //Title
        binding.TechpowerTitle.text=techpower.printedname

        //Level
        binding.TechpowerLevel.text= buildSpannedString {
            append(when(techpower.level){0->"At-will";1->"1st-level";2->"2nd-level";3->"3rd-level";else->"${techpower.level}th-level"})
            append(" tech power")
        }

        //Source
        binding.TechpowerSource.text=techpower.source

        //Details
        binding.TechpowerDetails.text= buildSpannedString {
            bold { append("Casting Time: ") };appendLine(techpower.castingtime)
            bold { append("Range: ") };appendLine(techpower.range)
            bold { append("Duration: ") };appendLine(techpower.duration)
            appendLine();append("   ");append(techpower.detailsText.trim())
        }

        //make Title appear on the top bar when the in details title gets scrolled past the top of the screen
        binding.scrolly.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY >= 120 && binding.title.alpha==0.0F) {
                binding.title.animate()
                    .alpha(1F)
                    .duration=50
            }
            else if(binding.title.alpha==1F && scrollY<120) binding.title.animate().alpha(0.0F)
        }


        //Background
        binding.coord.background=AppCompatResources.getDrawable(this@TechcastingDetailsActivity,R.drawable.neutralbg)

        //Special techpower cases
        if (techpower.techpowername=="spectrum_bolt"){
            layoutInflater.inflate(R.layout.techcasting_spectrum_bolt_table,binding.ll)
            txt=layoutInflater.inflate(R.layout.universal_textview_nofont_gold,binding.ll).findViewById(R.id.textview)
            txt.text=resources.getText(R.string.techcasting_spectrum_bolt_details_Text2)
        }
        if (techpower.techpowername=="construct_droid"){
            layoutInflater.inflate(R.layout.techcasting_construct_droid_table,binding.ll)
        }
        if (techpower.techpowername=="autonomous_servant"){
            layoutInflater.inflate(R.layout.techcasting_autonomous_servant_table,binding.ll)
            txt=layoutInflater.inflate(R.layout.universal_textview_nofont_gold,binding.ll).findViewById(R.id.textview)
            txt.text=resources.getText(R.string.techcasting_autonomous_servant_details_Text2)
        }

        binding.BackButton.setOnClickListener {returntomain()}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_techcasting_details, menu)
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        return super.onCreateOptionsMenu(menu)
    }

    private fun returntomain() {
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}