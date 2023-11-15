package com.example.swtrial2.classes

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
import com.example.swtrial2.databinding.ClassesBinding
import com.example.swtrial2.databinding.ClassesInfoBinding

class ClassesActivity : AppCompatActivity() {

    private lateinit var binding: ClassesBinding
    private lateinit var infobinding: ClassesInfoBinding
    private lateinit var scroll: ScrollView
    private lateinit var multiclassingList: Array<CharSequence>
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var temptxt: TextView
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private var mode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ClassesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflater=layoutInflater


        mode=0
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        multiclassingList=resources.getTextArray(R.array.multiclassing)
        scroll=binding.classesscroll
        binding.BackButton.setOnClickListener { returntomain() }

        infobinding= ClassesInfoBinding.inflate(layoutInflater,scroll,false)
        binding.infobutton.setOnClickListener {
            if(mode==0){
                scroll.removeAllViews()
                scroll.fling(0)
                scroll.scrollTo(0,0)
                ll=infobinding.classesinfoll
                for(i in multiclassingList.indices step 2 ){
                    txt = TextView(this)
                    txt.setTextAppearance(R.style.GoldTextStarjedi)

                    tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll, false)
                    temptxt=tempbersk.findViewById(R.id.contenttext)
                    tempbersk.findViewById<TextView>(R.id.headertext).text=multiclassingList[i]
                    temptxt.text=multiclassingList[i+1]
                    if(i==(multiclassingList.lastIndex-1)){
                        temptxt.typeface = resources.getFont(R.font.starjedi)
                    }
                    ll.addView(tempbersk)
                    if(i==0){
                        inflater.inflate(R.layout.classes_multiclassing_prerequisites_table,ll,true)
                    }
                    if(i==8){
                        inflater.inflate(R.layout.classes_multiclassing_proficiencies_table,ll,true)
                        txt.text=getText(R.string.multiclassing_classfeatures)
                        ll.addView(txt)
                    }
                    if(i==(multiclassingList.lastIndex-1)){
                        inflater.inflate(R.layout.classes_multiclassing_maxpowerlevel_table,ll,true)
                        txt.text=getText(R.string.multiclassing_highlvlcasting)
                        ll.addView(txt)
                    }
                }
                scroll.addView(ll)
                mode=1
            }
            else{
                Toast.makeText(this,"Already at Class info",Toast.LENGTH_SHORT)
                    .show()
            }
            return@setOnClickListener
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_classes,menu)
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
            scroll.removeAllViews()
            scroll.addView(binding.contentclassescl)
            mode=0
        }
    }
    fun openclass(view: View){
        when(view.id){
            R.id.buttonberserker ->{startActivity(Intent(this,BerserkerActivity::class.java))}
            R.id.buttonconsular ->{startActivity(Intent(this,ConsularActivity::class.java))}
            R.id.buttonengineer ->{startActivity(Intent(this,EngineerActivity::class.java))}
            R.id.buttonfighter ->{startActivity(Intent(this,FighterActivity::class.java))}
            R.id.buttonguardian ->{startActivity(Intent(this,GuardianActivity::class.java))}
            R.id.buttonmonk ->{startActivity(Intent(this,MonkActivity::class.java))}
            R.id.buttonoperative ->{startActivity(Intent(this,OperativeActivity::class.java))}
            R.id.buttonscholar ->{startActivity(Intent(this,ScholarActivity::class.java))}
            R.id.buttonscout ->{startActivity(Intent(this,ScoutActivity::class.java))}
            R.id.buttonsentinel ->{startActivity(Intent(this,SentinelActivity::class.java))}
            else->{
                Toast.makeText(this,"No such class",Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    @Deprecated("Deprecated in Java", ReplaceWith("returntomain(null)"))
    override fun onBackPressed(){
        returntomain()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}