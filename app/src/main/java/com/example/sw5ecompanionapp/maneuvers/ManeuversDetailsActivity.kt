package com.example.sw5ecompanionapp.maneuvers

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.databinding.ManeuversDetailsBinding

class ManeuversDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ManeuversDetailsBinding
    private lateinit var maneuver: Maneuver
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        maneuver = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Maneuver", Maneuver::class.java).toManeuver()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Maneuver>("Maneuver").toManeuver()
        }
        binding= ManeuversDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Title.text=maneuver.maneuvername

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //SRC
        binding.SourceBook.text=maneuver.source
        //title
        val temptext = "Type: " + maneuver.type +if (maneuver.prerequisite.isNotEmpty()){ "\n\n" + "prerequisite: " + maneuver.prerequisite } else {""} + "\n\n" + maneuver.detailsText
        binding.ManeuverText.text=temptext

        //Background
        binding.coord.background=AppCompatResources.getDrawable(this@ManeuversDetailsActivity,R.drawable.neutralbg2)

        //Special maneuver cases

        binding.BackButton.setOnClickListener {returntomain()}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_maneuvers_details, menu)
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