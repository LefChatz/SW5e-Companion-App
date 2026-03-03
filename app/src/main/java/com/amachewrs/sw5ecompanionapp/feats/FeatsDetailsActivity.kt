package com.amachewrs.sw5ecompanionapp.feats

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.FeatsDetailsBinding

class FeatsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: FeatsDetailsBinding
    private lateinit var feat: Feat
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= FeatsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        feat = getFeat()

        binding.Title.text=feat.featname.replace("_"," ").replace("..","'").replace(".","-")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //SRC
        binding.SourceBook.text=feat.source

        //title
        val temptext = "ASI: " + feat.asi +if (feat.prerequisite.isNotEmpty()){ "\n\n" + "prerequisite: " + feat.prerequisite } else {""} + "\n\n" + feat.detailsText
        binding.FeatText.text=temptext

        //Background
        binding.coord.background=AppCompatResources.getDrawable(this@FeatsDetailsActivity,R.drawable.neutralbg)

        //no special feat cases

        binding.BackButton.setOnClickListener {returntomain()}
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_feats_details, menu)
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        return super.onCreateOptionsMenu(menu)
    }*/

    @Suppress("DEPRECATION")
    private fun getFeat(): Feat{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getParcelableExtra("Feat", Feat::class.java).toFeat()
        else intent.getParcelableExtra<Feat>("Feat").toFeat()
    }

    private fun returntomain() {
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}