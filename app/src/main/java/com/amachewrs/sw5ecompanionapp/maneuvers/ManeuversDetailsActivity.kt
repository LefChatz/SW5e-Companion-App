package com.amachewrs.sw5ecompanionapp.maneuvers

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.ManeuversDetailsBinding

class ManeuversDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ManeuversDetailsBinding
    private lateinit var maneuver: Maneuver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ManeuversDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        maneuver = getManeuver()

        binding.Title.text=maneuver.maneuvername

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //SRC
        binding.SourceBook.text=maneuver.source
        //title
        val tempText = "Type: " + maneuver.type +if (maneuver.prerequisite.isNotEmpty()){ "\n\n" + "prerequisite: " + maneuver.prerequisite } else {""} + "\n\n" + maneuver.detailsText
        binding.ManeuverText.text=tempText

        //Background
        binding.coord.background=AppCompatResources.getDrawable(this@ManeuversDetailsActivity,R.drawable.neutralbg)

        //no special maneuver cases

        binding.BackButton.setOnClickListener {returntomain()}
    }
    @Suppress("DEPRECATION")
    private fun getManeuver(): Maneuver{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getParcelableExtra("Maneuver", Maneuver::class.java).toManeuver()
        else intent.getParcelableExtra<Maneuver>("Maneuver").toManeuver()
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_maneuvers_details, menu)
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        return super.onCreateOptionsMenu(menu)
    }*/

    private fun returntomain() {
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}