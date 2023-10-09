package com.example.swtrial2.equipment.equipmentadapterstuff

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.scale
import androidx.core.view.updateLayoutParams
import com.example.swtrial2.R

class EquipmentDetailsActivity : AppCompatActivity() {
    companion object {
        const val Equipment_Name = "Equipment_Name"
    }

    lateinit var name:String
    lateinit var details:Array<CharSequence>
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name=intent.getStringExtra(Equipment_Name).toString()
        setContentView(R.layout.activity_equipment_details)

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val starjedi = Typeface.create(ResourcesCompat.getFont(this,R.font.starjedi),Typeface.NORMAL)

        details=resources.getTextArray(resources.getIdentifier(name,"array",packageName))

        findViewById<TextView>(R.id.EquipmentDetailsTitle).text=details[0]

        val eqattrs = findViewById<TextView>(R.id.EquipmentDetailsAttributes)
        eqattrs.text=details[1]

        findViewById<TextView>(R.id.EquipmentDetailsCostWeight).text=details[2]

        findViewById<TextView>(R.id.EquipmentDetailsDamageAC).text=details[3]

        val eqprops = findViewById<TextView>(R.id.EquipmentDetailsProperties)
        eqprops.text=details[4]

        val eqtext = findViewById<TextView>(R.id.EquipmentDetailsText)
        eqtext.text=details[5]

        findViewById<TextView>(R.id.EquipmentDetailsExpansion).text=details[6]

        val imidentif=resources.getIdentifier("equipment$name","drawable",packageName)
        val imview = findViewById<ImageView>(R.id.EquipmentDetailsImage)
        if(imidentif!=0) {
            imview.setImageResource(imidentif)
        }
        else{
            eqtext.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToBottom = eqprops.id
                leftToLeft = eqprops.id
                topMargin = 10
            }
            with(imview.parent as ViewGroup){
                removeView(imview)
            }


        }

        with(eqattrs.text){
            when{
                (contains("weapon",true) || contains("Blaster",true))&&(!contains("accessory"))->{
                    val weapproplist = resources.getTextArray(R.array.weapon_properties)
                    val weappropmap=mutableMapOf<String,CharSequence>()
                    for(i in weapproplist.indices step 2){
                        weappropmap[weapproplist[i].toString()]=weapproplist[i+1]
                    }
                    eqtext.text= buildSpannedString{
                        append(details[5])
                        appendLine(" ")
                        weappropmap.keys.forEach{
                            if(eqprops.text.contains(it,true)){
                                appendLine(" ")
                                val spanit= SpannableString(it)
                                spanit.setSpan(TypefaceSpan(starjedi),0,it.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                scale(1.1F){
                                    appendLine(spanit)
                                }
                                appendLine(weappropmap[it])
                            }
                        }
                    }
                }
                (contains("shield",true)||contains("armour",true))&&(!contains("accessory"))->{
                    val armourproplist = resources.getTextArray(R.array.armour_properties)
                    val armourpropmap = mutableMapOf<String,CharSequence>()
                    for(i in armourproplist.indices step 2){
                        armourpropmap[armourproplist[i].toString()]=armourproplist[i+1]
                    }
                    eqtext.text= buildSpannedString{
                        append(details[5])
                        appendLine(" ")
                        armourpropmap.keys.forEach{
                            if(eqprops.text.contains(it,true)){
                                appendLine(" ")
                                val spanit= SpannableString(it)
                                spanit.setSpan(TypefaceSpan(starjedi),0,it.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                scale(1.1F){
                                    appendLine(spanit)
                                }
                                appendLine(armourpropmap[it])
                            }
                        }
                    }
                }
                else->{}
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_equipment_details,menu)
        toolbar.overflowIcon=AppCompatResources.getDrawable(this,R.drawable.dots3gold)
        return super.onCreateOptionsMenu(menu)
    }

    fun returntomain(view: View) {
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}