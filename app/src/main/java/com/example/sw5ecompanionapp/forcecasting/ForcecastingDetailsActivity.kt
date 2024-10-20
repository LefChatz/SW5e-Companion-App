package com.example.sw5ecompanionapp.forcecasting

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.databinding.ForcecastingForcepowerDetailsBinding

class ForcecastingDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ForcecastingForcepowerDetailsBinding
    private lateinit var forcepower: Forcepower
    private lateinit var txt: TextView
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forcepower = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra("Forcepower", Forcepower::class.java).toForcepower()
                    }
                    else {
                        @Suppress("DEPRECATION")
                        intent.getParcelableExtra<Forcepower>("Forcepower").toForcepower()
                    }
        binding= ForcecastingForcepowerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text=forcepower.printedname
        binding.title.alpha=0.0F

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //title
        binding.ForcepowerTitle.text=forcepower.printedname
        binding.ForcepowerText.text=forcepower.detailsText

        binding.scrolly.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY >= 120) {
                binding.title.animate()
                    .alpha(1F)
                    .duration=50
            }
            else binding.title.animate().alpha(0.0F)
        }


        //Background
        binding.coord.background=AppCompatResources.getDrawable(this@ForcecastingDetailsActivity,
            with(forcepower.side.toString()){
                when{
                    this.contains("Dark",true)->R.drawable.darkbg
                    this.contains("Light",true)->R.drawable.lightbg
                    this.contains("Universal",true)->R.drawable.neutralbg
                    else->R.drawable.error404
        }})

        //Special forcepower cases
        if (forcepower.forcepowername=="insanity"){
            layoutInflater.inflate(R.layout.forcecasting_instanity_hscroll,binding.ll)
            txt=layoutInflater.inflate(R.layout.universal_textview_nofont_gold,binding.ll).findViewById(R.id.textview)
            txt.text=resources.getText(R.string.insanity2)
        }
        if (forcepower.forcepowername=="mass_animation"){
            layoutInflater.inflate(R.layout.forcecasting_mass_animation_table,binding.ll)
            txt=layoutInflater.inflate(R.layout.universal_textview_nofont_gold,binding.ll).findViewById(R.id.textview)
            txt.text=resources.getText(R.string.mass_animation2)
        }

        binding.BackButton.setOnClickListener {returntomain()}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_forcecasting_details, menu)
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