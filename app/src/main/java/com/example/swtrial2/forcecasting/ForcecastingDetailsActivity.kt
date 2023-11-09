package com.example.swtrial2.forcecasting

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.swtrial2.R
import com.example.swtrial2.databinding.ActivityForcecastingdetailsBinding

class ForcecastingDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForcecastingdetailsBinding
    private lateinit var forcepower: Forcepower
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var backButton: AppCompatButton
    private lateinit var ll: LinearLayout
    private lateinit var txt: TextView
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forcepower = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Forcepower", Forcepower::class.java).toForcepower()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Forcepower>("Forcepower").toForcepower()
        }
        binding= ActivityForcecastingdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.force_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        ll=findViewById(R.id.ForcepowerDetailsll)

        //title and text assignment
        val spTitle:TextView=findViewById(R.id.ForcepowerDetailsTitle)
        spTitle.text=forcepower.printedname


        val spText:TextView=findViewById(R.id.ForcepowerDetailsText)
        spText.text=forcepower.detailsText

        //Background
        val spCoord:CoordinatorLayout=findViewById(R.id.ForcepowerDetailsCoord)
        with(forcepower.side.toString()){
            when{
                this.contains("Dark",true)->{spCoord.background=AppCompatResources.getDrawable(this@ForcecastingDetailsActivity,R.drawable.darkbg1)}
                this.contains("Light",true)->{spCoord.background=AppCompatResources.getDrawable(this@ForcecastingDetailsActivity,R.drawable.lightbg1)}
                this.contains("Universal",true)->{}
                else->{spCoord.background=AppCompatResources.getDrawable(this@ForcecastingDetailsActivity,R.drawable.error404)}
            }

        }
        if (forcepower.forcepowername=="insanity"){
            layoutInflater.inflate(R.layout.forcecasting_instanity_hscroll,ll)
            txt=layoutInflater.inflate(R.layout.universal_textview_nofont_gold,ll).findViewById(R.id.textview)
            txt.text=resources.getText(R.string.insanity2)
        }
        if (forcepower.forcepowername=="mass_animation"){
            layoutInflater.inflate(R.layout.forcecasting_mass_animation_table,ll)
            txt=layoutInflater.inflate(R.layout.universal_textview_nofont_gold,ll).findViewById(R.id.textview)
            txt.text=resources.getText(R.string.mass_animation2)
        }

        backButton=findViewById(R.id.forceBackButton)
        backButton.setOnClickListener {
            returntomain()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_forcecasting_details, menu)
        toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        return super.onCreateOptionsMenu(menu)
    }

    fun returntomain() {
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}