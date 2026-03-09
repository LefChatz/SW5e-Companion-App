package com.amachewrs.sw5ecompanionapp.classes

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.ClassesBinding
import com.amachewrs.sw5ecompanionapp.databinding.ClassesInfoBinding
import com.amachewrs.sw5ecompanionapp.utility.Utilities.Companion.showSnackBar

class ClassesActivity : AppCompatActivity() {

    private lateinit var binding: ClassesBinding
    private lateinit var infobinding: ClassesInfoBinding
    private lateinit var multiclassingList: Array<CharSequence>
    private lateinit var tempView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var temptxt: TextView
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private var atInfo = false
    private var infoGenerated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ClassesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        inflater=layoutInflater

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        multiclassingList=resources.getTextArray(R.array.multiclassing)

        binding.BackButton.setOnClickListener {returntomain()}

        binding.infobutton.setOnClickListener {if(!atInfo) handleInfoSwitch() else showSnackBar("to return press back",binding.coord,this)}

        infobinding= ClassesInfoBinding.inflate(layoutInflater,binding.scrolly,false)
        onBackPressedDispatcher.addCallback(this,object:OnBackPressedCallback(true){override fun handleOnBackPressed(){returntomain()}})
    }

    private fun handleInfoSwitch(){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        binding.scrolly.removeAllViews()
        if (!atInfo) {
            if (!infoGenerated) generateInfo() else binding.scrolly.addView(ll)
        }
        else binding.scrolly.addView(binding.contentcl)
        atInfo = !atInfo
    }

    private fun generateInfo(){
        ll=infobinding.classesinfoll
        for(i in multiclassingList.indices step 2 ){
            txt = TextView(this)
            txt.setTextAppearance(R.style.GoldTextStarjedi)

            tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll, false)
            temptxt=tempView.findViewById(R.id.contenttext)
            tempView.findViewById<TextView>(R.id.headertext).text=multiclassingList[i]
            temptxt.text=multiclassingList[i+1]
            if(i==(multiclassingList.lastIndex-1)){
                temptxt.typeface = resources.getFont(R.font.starjedi)
            }
            ll.addView(tempView)
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
        binding.scrolly.addView(ll)
        infoGenerated = true
    }

    private fun returntomain() {
        if(!atInfo) finish()
        else handleInfoSwitch()
    }
    fun openClass(view: View){
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
            else-> {showSnackBar("Error, no such class",binding.coord,this)}
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}