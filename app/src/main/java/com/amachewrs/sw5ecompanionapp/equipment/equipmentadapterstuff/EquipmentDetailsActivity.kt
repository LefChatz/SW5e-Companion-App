package com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.scale
import androidx.core.view.updateLayoutParams
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.EquipmentDetailsBinding


class EquipmentDetailsActivity : AppCompatActivity() {
    private lateinit var binding: EquipmentDetailsBinding
    private lateinit var equipment: Equipment

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        equipment = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Equipment", Equipment::class.java).toEquipment()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Equipment>("Equipment").toEquipment()
        }

        binding= EquipmentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.BackButton.setOnClickListener { returntomain() }

        val starjedi: Typeface = Typeface.create(ResourcesCompat.getFont(this,R.font.starjedi),Typeface.NORMAL)

        binding.title.text=equipment.printedname

        binding.Attributes.text=equipment.attributes

        val temptxt = equipment.cost.toString()+" cr  "+equipment.weight+" lb"
        binding.CostWeight.text= temptxt

        binding.DamageAC.text=equipment.damage_ac

        binding.Properties.text=equipment.properties

        binding.Text.text=equipment.detailsText

        binding.Expansion.text=equipment.expansion

        val imidentif=resources.getIdentifier("equipment${equipment.equipmentname}","drawable",packageName)
        binding.Image
        if(imidentif!=0) {
            binding.Image.setImageResource(imidentif)
        }
        else{
            binding.Text.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToBottom = binding.Properties.id
                leftToLeft = binding.Properties.id
                topMargin = 10
            }
            binding.detailsConstl.removeView(binding.Image)
        }

        with(binding.Attributes.text){
            when{
                (contains("weapon",true) || contains("Blaster",true))&&(!contains("accessory"))->{
                    val weapproplist = resources.getTextArray(R.array.weapon_properties)
                    val weappropmap=mutableMapOf<String,CharSequence>()
                    for(i in weapproplist.indices step 2){
                        weappropmap[weapproplist[i].toString()]=weapproplist[i+1]
                    }
                    binding.Text.text= buildSpannedString{
                        append(equipment.detailsText)
                        appendLine(" ")
                        weappropmap.keys.forEach{
                            if(binding.Properties.text.contains(it,true)){
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
                (contains("shield",true)||contains("armor",true))&&(!contains("accessory"))->{
                    val armorproplist = resources.getTextArray(R.array.armor_properties)
                    val armorpropmap = mutableMapOf<String,CharSequence>()
                    for(i in armorproplist.indices step 2){
                        armorpropmap[armorproplist[i].toString()]=armorproplist[i+1]
                    }
                    binding.Text.text= buildSpannedString{
                        append(equipment.detailsText)
                        appendLine(" ")
                        armorpropmap.keys.forEach{
                            if(binding.Properties.text.contains(it,true)){
                                appendLine(" ")
                                val spanit= SpannableString(it)
                                spanit.setSpan(TypefaceSpan(starjedi),0,it.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                scale(1.1F){
                                    appendLine(spanit)
                                }
                                appendLine(armorpropmap[it])
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
        binding.toolbar.overflowIcon=AppCompatResources.getDrawable(this,R.drawable.dots3gold)
        return super.onCreateOptionsMenu(menu)
    }

    private fun returntomain() {
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}