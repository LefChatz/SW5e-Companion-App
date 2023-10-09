package com.example.swtrial2

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.NestedScrollView
import com.example.swtrial2.classes.*
import com.example.swtrial2.databinding.ActivitySpeciesBinding
import com.example.swtrial2.species.*
import kotlin.properties.Delegates

class SpeciesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpeciesBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var infobutton: ImageButton
    private lateinit var nscroll: NestedScrollView
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
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        nscroll=findViewById(R.id.speciesnscroll)
        infobutton=findViewById(R.id.infobutton)
        infobutton.setOnClickListener {
            if(mode==0){
                nscroll.removeAllViews()
                val tempview=inflater.inflate(R.layout.content_classes_info,nscroll,false)
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

                nscroll.addView(tempview)

                mode=1

            }
            else{
                Toast.makeText(this,"Already at Class info",Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_species,menu)
        val drawable= AppCompatResources.getDrawable(this,R.drawable.dots3gold)
        toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }

    fun returntomain(view: View?) {
        if(mode==0){
            startActivity(Intent(this,SW5ECompanionApp::class.java))
            finish()
        }
        else{
            tempbersk=inflater.inflate(R.layout.content_species,nscroll,false)
            nscroll.removeAllViews()
            nscroll.addView(tempbersk)
            mode=0
        }
    }
    fun openspecies(view: View){
        when(view.id){
            R.id.buttonbith->  {startActivity(Intent(this,BithActivity::class.java))}
            R.id.buttonbothan->   {startActivity(Intent(this, BothanActivity::class.java))}
            R.id.buttoncathar->   {startActivity(Intent(this, CatharActivity::class.java))}
            R.id.buttoncerean->    {startActivity(Intent(this, CereanActivity::class.java))}
            R.id.buttonchiss->   {startActivity(Intent(this, ChissActivity::class.java))}
            R.id.buttondevaronian->       {startActivity(Intent(this,DevaronianActivity::class.java))}
            R.id.buttondroidclass1->  {startActivity(Intent(this,DroidClass1Activity::class.java))}
            R.id.buttondroidclass2->    {startActivity(Intent(this,DroidClass2Activity::class.java))}
            R.id.buttondroidclass3->      {startActivity(Intent(this,DroidClass3Activity::class.java))}
            R.id.buttondroidclass4->   {startActivity(Intent(this,DroidClass4Activity::class.java))}
            R.id.buttondroidclass5->    {startActivity(Intent(this,DroidClass5Activity::class.java))}
            R.id.buttonduros->  {startActivity(Intent(this, DurosActivity::class.java))}
            R.id.buttonewok->   {startActivity(Intent(this, EwokActivity::class.java))}
            R.id.buttongamorrean->  {startActivity(Intent(this, GamorreanActivity::class.java))}
            R.id.buttongungan-> {startActivity(Intent(this, GunganActivity::class.java))}
            R.id.buttonhuman->  {startActivity(Intent(this, HumanActivity::class.java))}
            R.id.buttonithorian->   {startActivity(Intent(this, IthorianActivity::class.java))}
            R.id.buttonjawa->   {startActivity(Intent(this, JawaActivity::class.java))}
            R.id.buttonkel_dor->    {startActivity(Intent(this, KelDorActivity::class.java))}
            R.id.buttonmon_calamari->{startActivity(Intent(this, MonCalamariActivity::class.java))}
            R.id.buttonnautolan->{startActivity(Intent(this, NautolanActivity::class.java))}
            R.id.buttonrodian->{startActivity(Intent(this, RodianActivity::class.java))}
            R.id.buttonsith_pureblood->{startActivity(Intent(this, SithPurebloodActivity::class.java))}
            R.id.buttontogruta->{startActivity(Intent(this, TogrutaActivity::class.java))}
            R.id.buttontrandoshan->{startActivity(Intent(this, TrandoshanActivity::class.java))}
            R.id.buttontusken->{startActivity(Intent(this, TuskenActivity::class.java))}
            R.id.buttontwilek->{startActivity(Intent(this, TwilekActivity::class.java))}
            R.id.buttonweequay->{startActivity(Intent(this, WeequayActivity::class.java))}
            R.id.buttonwookie->{startActivity(Intent(this, WookieActivity::class.java))}
            R.id.buttonzabrak->{startActivity(Intent(this, ZabrakActivity::class.java))}

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