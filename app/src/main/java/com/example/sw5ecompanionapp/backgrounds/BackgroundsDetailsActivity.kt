package com.example.sw5ecompanionapp.backgrounds

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.sw5ecompanionapp.databinding.BackgroundsDetailsBinding

class BackgroundsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: BackgroundsDetailsBinding
    private lateinit var inflater: LayoutInflater
    private lateinit var background: String
    private lateinit var detailsStringArray: Array<CharSequence>
    private lateinit var tempText: TextView
    private lateinit var tempView: View
    private lateinit var tempstring: String
    private var backgroundidentif = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        background = intent.getStringExtra("Background").toString()
        inflater=layoutInflater

        binding = BackgroundsDetailsBinding.inflate(inflater)
        setContentView(binding.root)

        binding.Title.text=background.replace("_"," ")

        when(background){
            "agent"->           generateAgent(binding.ll,resources,inflater)
            "bounty_hunter"->   generateBountyHunter(binding.ll,resources,inflater)
            "criminal"->        generateCriminal(binding.ll,resources,inflater)
            "entertainer"->     generateBountyHunter(binding.ll,resources,inflater)
            "force_adept"->     generateBountyHunter(binding.ll,resources,inflater)
            "gambler"->         generateBountyHunter(binding.ll,resources,inflater)
            "investigator"->    generateBountyHunter(binding.ll,resources,inflater)
            "jedi"->            generateBountyHunter(binding.ll,resources,inflater)
            "mandalorian"->     generateBountyHunter(binding.ll,resources,inflater)
            "mercenary"->       generateBountyHunter(binding.ll,resources,inflater)
            "noble"->           generateBountyHunter(binding.ll,resources,inflater)
            "nomad"->           generateBountyHunter(binding.ll,resources,inflater)
            "outlaw"->          generateBountyHunter(binding.ll,resources,inflater)
            "pirate"->          generateBountyHunter(binding.ll,resources,inflater)
            "scientist"->       generateBountyHunter(binding.ll,resources,inflater)
            "scoundrel"->       generateBountyHunter(binding.ll,resources,inflater)
            "sith"->            generateBountyHunter(binding.ll,resources,inflater)
            "smuggler"->        generateBountyHunter(binding.ll,resources,inflater)
            "soldier"->         generateBountyHunter(binding.ll,resources,inflater)
            "spacer"->          generateBountyHunter(binding.ll,resources,inflater)
            "error"->           generateBountyHunter(binding.ll,resources,inflater)
        }

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