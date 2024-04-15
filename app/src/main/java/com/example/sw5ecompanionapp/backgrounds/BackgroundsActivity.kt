package com.example.sw5ecompanionapp.backgrounds

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.SW5ECompanionApp
import com.example.sw5ecompanionapp.databinding.BackgroundsBinding
import kotlin.properties.Delegates

class BackgroundsActivity : AppCompatActivity() {

    private lateinit var binding: BackgroundsBinding
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var temptxt: TextView
    private lateinit var txt: TextView
    private var mode by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BackgroundsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflater=layoutInflater

        mode=0
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.BackButton.setOnClickListener { returntomain() }

        binding.infobutton.setOnClickListener {
            /*binding.scrolly.fling(0)
            binding.scrolly.scrollTo(0,0)
            binding.ll.removeAllViews()
            if(mode==0){
                txt = inflater.inflate(R.layout.universal_textview_starjedi_gold,binding.ll,false).findViewById(R.id.textview)
                txt.text=getText(R.string.species_info1)
                binding.ll.addView(txt)

                tempbersk=inflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.ll,false)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.species_info2Header)
                tempbersk.findViewById<TextView>(R.id.contenttext).text=getText(R.string.species_info2Text)
                binding.ll.addView(tempbersk)

                tempbersk=inflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.ll,false)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.species_info3Header)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                temptxt.text=getText(R.string.species_info3Text)
                temptxt.typeface=resources.getFont(R.font.starjedi)
                binding.ll.addView(tempbersk)

                mode=1


            }
            else{
                Toast.makeText(this,"Already at Species info",Toast.LENGTH_SHORT)
                    .show()
            }*/
        }
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_species,menu)
        val drawable= AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        binding.toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }

    private fun returntomain() {
        if(mode==0){
            startActivity(Intent(this, SW5ECompanionApp::class.java))
            finish()
        }
        else{
            binding.scrolly.fling(0)
            binding.scrolly.scrollTo(0,0)
            binding.scrolly.removeAllViews()
            binding.scrolly.addView(binding.contentcl)
            mode=0
        }
    }
    fun openspecies(view: View){startActivity(Intent(this, BackgroundsDetailsActivity::class.java).putExtra("Background",when(view.id){
        binding.agent.id->       "agent"
        binding.bountyHunter.id->"bounty_hunter"
        binding.criminal.id ->   "criminal"
        binding.entertainer.id ->"entertainer"
        binding.forceAdept.id -> "force_adept"
        binding.gambler.id ->    "gambler"
        binding.investigator.id->"investigator"
        binding.jedi.id ->       "jedi"
        binding.mandalorian.id ->"mandalorian"
        binding.mercenary.id ->  "mercenary"
        binding.noble.id ->      "noble"
        binding.nomad.id ->      "nomad"
        binding.outlaw.id ->     "outlaw"
        binding.pirate.id ->     "pirate"
        binding.scientist.id ->  "scientist"
        binding.scoundrel.id ->  "scoundrel"
        binding.sith.id ->       "sith"
        binding.smuggler.id ->   "smuggler"
        binding.soldier.id ->    "soldier"
        binding.spacer.id ->     "spacer"
        else->                   "error"
    }))}
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}