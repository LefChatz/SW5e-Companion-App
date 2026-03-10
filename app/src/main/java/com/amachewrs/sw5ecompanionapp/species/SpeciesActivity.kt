package com.amachewrs.sw5ecompanionapp.species

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesBinding
import com.amachewrs.sw5ecompanionapp.utility.Utilities.Companion.showSnackBar

class SpeciesActivity : AppCompatActivity() {

    private lateinit var binding: SpeciesBinding
    private lateinit var tempView: View
    private lateinit var temptxt: TextView
    private lateinit var txt: TextView
    private lateinit var infoLinearLayout: LinearLayout
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

    private fun handleInfoChange(){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        binding.scrolly.removeAllViews()
        if (!atInfo){
            if (!this::infoLinearLayout.isInitialized) generateInfo()
            binding.scrolly.addView(infoLinearLayout)
        }
        else binding.scrolly.addView(binding.contentcl)
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

    fun openSpecies(view: View){startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie",when(view.id){
        R.id.bith ->             "bith"
        R.id.bothan ->           "bothan"
        R.id.cathar ->           "cathar"
        R.id.cerean ->           "cerean"
        R.id.chiss ->            "chiss"
        R.id.devaronian ->       "devaronian"
        R.id.droidclass1 ->      "droid_class_1"
        R.id.droidclass2 ->      "droid_class_2"
        R.id.droidclass3 ->      "droid_class_3"
        R.id.droidclass4 ->      "droid_class_4"
        R.id.droidclass5 ->      "droid_class_5"
        R.id.duros ->            "duros"
        R.id.ewok ->             "ewok"
        R.id.gamorrean ->        "gamorrean"
        R.id.gungan ->           "gungan"
        R.id.human ->            "human"
        R.id.ithorian ->         "ithorian"
        R.id.jawa ->             "jawa"
        R.id.kel_dor ->          "kel_dor"
        R.id.mon_calamari ->     "mon_calamari"
        R.id.nautolan ->         "nautolan"
        R.id.rodian ->           "rodian"
        R.id.sith_pureblood ->   "sith_pureblood"
        R.id.togruta ->          "togruta"
        R.id.trandoshan ->       "trandoshan"
        R.id.tusken ->           "tusken"
        R.id.twilek ->           "twilek"
        R.id.weequay ->          "weequay"
        R.id.wookie ->           "wookie"
        R.id.zabrak ->           "zabrak"
        else->                  "error"
    }))}

    private fun returntomain() {
        if(!atInfo) finish()
        else handleInfoChange()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}