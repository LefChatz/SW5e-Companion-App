package com.example.swtrial2

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.swtrial2.classes.*
import com.example.swtrial2.databinding.ActivitySpeciesBinding
import com.example.swtrial2.species.*
import kotlin.properties.Delegates

class SpeciesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpeciesBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var infobutton: ImageButton
    private lateinit var scroll: ScrollView
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var temptxt: TextView
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private var mode by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpeciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflater=layoutInflater

        mode=0
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        scroll=binding.scrolly
        infobutton=findViewById(R.id.infobutton)
        infobutton.setOnClickListener {
            if(mode==0){
                scroll.removeAllViews()
                val tempview=inflater.inflate(R.layout.classes_info,scroll,false)
                ll=tempview.findViewById(R.id.classesinfoll)
                ll.removeAllViews()

                txt = TextView(this)
                txt.setTextColor(getColor(R.color.gold))
                txt.typeface=resources.getFont(R.font.starjedi)
                txt.textSize=20F
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
        val drawable= AppCompatResources.getDrawable(this,R.drawable.dots3gold)
        binding.toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }

    fun returntomain(view: View?) {
        if(mode==0){
            startActivity(Intent(this,SW5ECompanionApp::class.java))
            finish()
        }
        else{
            scroll.removeAllViews()
            scroll.addView(binding.contentcl)
            mode=0
        }
    }
    fun openspecies(view: View){
        when(view.id){
            R.id.bith->  {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","bith"))}
            R.id.bothan->   {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","bothan"))}
            R.id.cathar->   {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","cathar"))}
            R.id.cerean->    {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","cerean"))}
            R.id.chiss->   {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","chiss"))}
            R.id.devaronian->       {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","devaronian"))}
            R.id.droidclass1->  {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","droidclass1"))}
            R.id.droidclass2->    {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","droidclass2"))}
            R.id.droidclass3->      {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","droidclass3"))}
            R.id.droidclass4->   {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","droidclass4"))}
            R.id.droidclass5->    {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","droidclass5"))}
            R.id.duros->  {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","duros"))}
            R.id.ewok->   {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","ewok"))}
            R.id.gamorrean->  {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","gamorrean"))}
            R.id.gungan-> {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","gungan"))}
            R.id.human->  {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","human"))}
            R.id.ithorian->   {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","ithorian"))}
            R.id.jawa->   {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","jawa"))}
            R.id.kel_dor->    {startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","kel_dor"))}
            R.id.mon_calamari->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","mon_calamari"))}
            R.id.nautolan->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","nautolan"))}
            R.id.rodian->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","rodian"))}
            R.id.sith_pureblood->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","sith_pureblood"))}
            R.id.togruta->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","togruta"))}
            R.id.trandoshan->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","trandoshan"))}
            R.id.tusken->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","tusken"))}
            R.id.twilek->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","twilek"))}
            R.id.weequay->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","weequay"))}
            R.id.wookie->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","wookie"))}
            R.id.zabrak->{startActivity(Intent(this,SpeciesDetailsActivity::class.java).putExtra("Specie","zabrak"))}

            else->{
                Toast.makeText(this,"No such class",Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        returntomain(null)
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}