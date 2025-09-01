package com.amachewrs.sw5ecompanionapp.species

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.SW5ECompanionApp
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesBinding
import com.amachewrs.sw5ecompanionapp.feats.Feat
import kotlin.properties.Delegates

class SpeciesActivity : AppCompatActivity() {

    private lateinit var binding: SpeciesBinding
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var temptxt: TextView
    private lateinit var txt: TextView
    private lateinit var speciesList: MutableList<Specie>
    private var mode by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SpeciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflater=layoutInflater

        mode=0
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.BackButton.setOnClickListener { returntomain() }

        binding.infobutton.setOnClickListener {if(mode==0)generateInfo()else Toast.makeText(this,"to return press back",Toast.LENGTH_SHORT).show()}
        onBackPressedDispatcher.addCallback(this,object:OnBackPressedCallback(true){override fun handleOnBackPressed(){returntomain()}})
        speciesList = getSpecies()
    }

    private fun generateInfo(){
        mode=1

        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        binding.ll.removeAllViews()

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
    }
    @SuppressLint("DiscouragedApi")
    private fun getSpecies(): MutableList<Specie>{
        val getSpecies = mutableListOf<Specie>()
        val tempSpecieList=resources.getTextArray(R.array.species_list)
        for(i in 6..tempSpecieList.size step 7){
            getSpecies.add(Specie(tempSpecieList[i-6].toString(),tempSpecieList[i-5],tempSpecieList[i-4].toString(),tempSpecieList[i-3],tempSpecieList[i-2],resources.getIdentifier(tempSpecieList[i-1].toString(),"drawable",packageName),resources.getIdentifier(tempSpecieList[i-1].toString()+"button","drawable",packageName),tempSpecieList[i].toString().toBoolean()))
        }
        return getSpecies
    }


    /* Menu Creation
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_species,menu)
        val drawable= AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        binding.toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }*/

    private fun returntomain() {
        if(mode==0) finish()
        else{
            binding.scrolly.scrollTo(0,0)
            binding.scrolly.fling(0)
            binding.ll.removeAllViews()
            mode=0
        }
    }
    fun openspecies(view: View){startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie",speciesList[1]))}
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}