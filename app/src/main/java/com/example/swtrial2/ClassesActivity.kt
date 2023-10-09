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
import com.example.swtrial2.databinding.ActivityClassesBinding
import kotlin.properties.Delegates

class ClassesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassesBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var infobutton: ImageButton
    private lateinit var nscroll: NestedScrollView
    private lateinit var multiclassingList: Array<CharSequence>
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var temptxt: TextView
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private var mode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflater=layoutInflater

        mode=0
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)



        multiclassingList=resources.getTextArray(R.array.multiclassing)
        nscroll=findViewById(R.id.classesnscroll)
        infobutton=findViewById(R.id.infobutton)
        infobutton.setOnClickListener {
            if(mode==0){
                nscroll.removeAllViews()
                val tempview=inflater.inflate(R.layout.content_classes_info,nscroll,false)
                ll=tempview.findViewById(R.id.classesinfoll)
                for(i in multiclassingList.indices step 2 ){
                    txt = TextView(this)
                    txt.setTextColor(getColor(R.color.gold))
                    txt.typeface=resources.getFont(R.font.starjedi)
                    txt.textSize=20F

                    tempbersk = inflater.inflate(R.layout.class_textview,ll, false)
                    temptxt=tempbersk.findViewById(R.id.contenttext)
                    tempbersk.findViewById<TextView>(R.id.headertext).text=multiclassingList[i]
                    temptxt.text=multiclassingList[i+1]
                    if(i==(multiclassingList.lastIndex-1)){
                        temptxt.typeface = resources.getFont(R.font.starjedi)
                    }
                    ll.addView(tempbersk)
                    if(i==0){
                        inflater.inflate(R.layout.content_classes_multiclassing_prerequisites_table,ll,true)
                    }
                    if(i==8){
                        inflater.inflate(R.layout.content_classes_multiclassing_proficiencies_table,ll,true)
                        txt.text=getText(R.string.multiclassing_classfeatures)
                        ll.addView(txt)
                    }
                    if(i==(multiclassingList.lastIndex-1)){
                        inflater.inflate(R.layout.content_classes_multiclassing_maxpowerlevel_table,ll,true)
                        txt.text=getText(R.string.multiclassing_highlvlcasting)
                        ll.addView(txt)
                    }
                }
                nscroll.addView(ll)
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
            tempbersk=inflater.inflate(R.layout.content_classes,nscroll,false)
            nscroll.removeAllViews()
            nscroll.addView(tempbersk)
            mode=0
        }
    }
    fun openclass(view: View){
        when(view.id){
            R.id.buttonberserker->{startActivity(Intent(this,BerserkerActivity::class.java))}
            R.id.buttonconsular->{startActivity(Intent(this,ConsularActivity::class.java))}
            R.id.buttonengineer->{startActivity(Intent(this,EngineerActivity::class.java))}
            R.id.buttonfighter->{startActivity(Intent(this,FighterActivity::class.java))}
            R.id.buttonguardian->{startActivity(Intent(this,GuardianActivity::class.java))}
            R.id.buttonmonk->{startActivity(Intent(this,MonkActivity::class.java))}
            R.id.buttonoperative->{startActivity(Intent(this,OperativeActivity::class.java))}
            R.id.buttonscholar->{startActivity(Intent(this,ScholarActivity::class.java))}
            R.id.buttonscout->{startActivity(Intent(this,ScoutActivity::class.java))}
            R.id.buttonsentinel->{startActivity(Intent(this,SentinelActivity::class.java))}
            else->{
                Toast.makeText(this,"No such class",Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    @Deprecated("Deprecated in Java", ReplaceWith("returntomain(null)"))
    override fun onBackPressed(){
        returntomain(null)
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}