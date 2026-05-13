package com.amachewrs.sw5ecompanionapp.species

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesBinding
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.Specie
import com.amachewrs.sw5ecompanionapp.utility.Utilities.Companion.showSnackBar
import java.util.LinkedList

class SpeciesActivity : AppCompatActivity() {

    private lateinit var binding: SpeciesBinding
    private lateinit var tempView: View
    private lateinit var temptxt: TextView
    private lateinit var txt: TextView
    private lateinit var infoLinearLayout: LinearLayout
    private lateinit var speciesMenu: Menu
    private var atInfo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SpeciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.BackButton.setOnClickListener { returntomain() }

        binding.infobutton.setOnClickListener {if(!atInfo) handleInfoChange() else showSnackBar("to return press back",binding.coord,this)}
        onBackPressedDispatcher.addCallback(this,object:OnBackPressedCallback(true){override fun handleOnBackPressed(){returntomain()}})
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_forcecasting,menu)
        speciesMenu != menu
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.downarrowgold)
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleInfoChange(){
        if(!atInfo){
            binding.reclview.visibility = View.GONE
            binding.bottomNavigationView.visibility = View.GONE
            binding.floatingActionButton.visibility = View.GONE
            binding.scrolly.visibility = View.VISIBLE
            speciesMenu.forEach { if(it.order!=2)it.isVisible=false else it.title = resources.getText(R.string.back_gold) }
            binding.scrolly.scrollTo(0,0)
            if (!this::infoLinearLayout.isInitialized) generateInfo()
        }
        else {
            binding.scrolly.visibility = View.GONE
            binding.reclview.visibility = View.VISIBLE
            binding.bottomNavigationView.visibility = View.VISIBLE
            binding.floatingActionButton.visibility = View.VISIBLE
            speciesMenu.forEach { if(it.order!=2)it.isVisible=true else it.title = resources.getText(R.string.casting_info) }
        }
        returntotop("sharp")
        atInfo=!atInfo
    }

    private fun generateInfo(){
        infoLinearLayout = LinearLayout(this)
        infoLinearLayout.orientation = LinearLayout.VERTICAL

        txt = layoutInflater.inflate(R.layout.universal_textview_starjedi_gold,binding.scrolly,false).findViewById(R.id.textview)
        txt.text=getText(R.string.species_info1)
        infoLinearLayout.addView(txt)

        tempView=layoutInflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.scrolly,false)
        tempView.findViewById<TextView>(R.id.headertext).text=getText(R.string.species_info2Header)
        tempView.findViewById<TextView>(R.id.contenttext).text=getText(R.string.species_info2Text)
        infoLinearLayout.addView(tempView)

        tempView=layoutInflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.scrolly,false)
        tempView.findViewById<TextView>(R.id.headertext).text=getText(R.string.species_info3Header)
        temptxt=tempView.findViewById(R.id.contenttext)
        temptxt.text=getText(R.string.species_info3Text)
        temptxt.typeface=resources.getFont(R.font.starjedi)
        infoLinearLayout.addView(tempView)
    }

    private fun returntotop(mode: String){
        when(mode){
            "smooth"->binding.reclview.smoothScrollToPosition(0)
            "sharp"->binding.reclview.scrollToPosition(0)
        }
    }

    private fun returntomain() {
        if(!atInfo) finish()
        else handleInfoChange()
    }

    private fun getSpecies(): MutableList<Specie>{
        val getSpeciesList = mutableListOf<Specie>()
        val speciesTextArray = resources.getTextArray(R.array.species_list)
        for (i in 6..speciesTextArray.size step 7){
            getSpeciesList.add(
                Specie(
                    speciesTextArray[i - 6].toString(),
                    speciesTextArray[i - 5],
                    speciesTextArray[i - 4].toString().toInt(),
                    LinkedList(speciesTextArray[i - 3].split("|")),
                    speciesTextArray[i - 2],
                    speciesTextArray[i - 1].toString().toInt(),
                    speciesTextArray[i].toString().toInt()
                )
            )
        }
        return getSpeciesList
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}