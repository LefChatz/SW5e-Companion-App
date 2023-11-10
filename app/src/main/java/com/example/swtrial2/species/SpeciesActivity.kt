package com.example.swtrial2.species

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.swtrial2.R
import com.example.swtrial2.SW5ECompanionApp
import com.example.swtrial2.classes.*
import com.example.swtrial2.databinding.SpeciesBinding
import com.example.swtrial2.species.*
import kotlin.properties.Delegates

class SpeciesActivity : AppCompatActivity() {

    private lateinit var binding: SpeciesBinding
    private lateinit var scroll: ScrollView
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var temptxt: TextView
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private var mode by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SpeciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflater=layoutInflater

        mode=0
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        scroll=binding.scrolly
        binding.infobutton.setOnClickListener {
            if(mode==0){
                scroll.removeAllViews()
                val tempview=inflater.inflate(R.layout.classes_info,scroll,false)
                ll=tempview.findViewById(R.id.classesinfoll)
                ll.removeAllViews()

                txt = TextView(this)
                txt.setTextAppearance(R.style.GoldTextStarjedi)
                txt.text=getText(R.string.species_info1)
                ll.addView(txt)
                tempbersk=inflater.inflate(R.layout.class_textview,ll,false)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.species_info2Header)
                tempbersk.findViewById<TextView>(R.id.contenttext).text=getText(R.string.species_info2Text)
                ll.addView(tempbersk)
                tempbersk=inflater.inflate(R.layout.class_textview,ll,false)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.species_info3Header)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                temptxt.text=getText(R.string.species_info3Text)
                temptxt.typeface=resources.getFont(R.font.starjedi)
                ll.addView(tempbersk)

                scroll.addView(tempview)

                mode=1
            }
            else{
                Toast.makeText(this,"Already at Species info",Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_species,menu)
        val drawable= AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        binding.toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }

    fun returntomain(view: View?) {
        if(mode==0){
            startActivity(Intent(this, SW5ECompanionApp::class.java))
            finish()
        }
        else{
            scroll.removeAllViews()
            scroll.addView(binding.contentcl)
            mode=0
        }
    }
    fun openspecies(view: View){startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie",when(view.id){
        R.id.bith ->             "bith"
        R.id.bothan ->           "bothan"
        R.id.cathar ->           "cathar"
        R.id.cerean ->           "cerean"
        R.id.chiss ->            "chiss"
        R.id.devaronian ->       "devaronian"
        R.id.droidclass1 ->      "droid_class1"
        R.id.droidclass2 ->      "droid_class2"
        R.id.droidclass3 ->      "droid_class3"
        R.id.droidclass4 ->      "droid_class4"
        R.id.droidclass5 ->      "droid_class5"
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
    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        returntomain(null)
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}