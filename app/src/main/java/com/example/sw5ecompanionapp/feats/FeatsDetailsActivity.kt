package com.example.sw5ecompanionapp.feats

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.databinding.FeatsDetailsBinding

class FeatsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: FeatsDetailsBinding
    private lateinit var feat: Feat
    private lateinit var txt: TextView
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Feat", Feat::class.java).toFeat()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Feat>("Feat").toFeat()
        }
        binding= FeatsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text=feat.featname.replace("_"," ").replace(".","-").replace("..","'")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //title
        binding.FeatText.text=feat.detailsText

        //Background
        binding.coord.background=AppCompatResources.getDrawable(this@FeatsDetailsActivity,R.drawable.neutralbg2)

        //Special feat cases

        binding.BackButton.setOnClickListener {returntomain()}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_feats_details, menu)
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