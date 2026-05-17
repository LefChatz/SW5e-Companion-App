package com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.buildSpannedString
import androidx.core.text.scale
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.EquipmentDetailsBinding


class EquipmentDetailsActivity : AppCompatActivity() {
    private lateinit var binding: EquipmentDetailsBinding
    private lateinit var equipment: Equipment

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= EquipmentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        equipment = getEquipment()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.BackButton.setOnClickListener { returntomain() }

        binding.title.text=equipment.printedName

        binding.Attributes.text=equipment.attributes

        val temptxt = equipment.cost.toString()+" cr  "+equipment.weight+" lb"
        binding.CostWeight.text= temptxt

        binding.DamageAC.text=equipment.damageAc

        binding.Properties.text=equipment.properties

        binding.Text.text=equipment.detailsText

        binding.Expansion.text=equipment.expansion

        val imidentif=resources.getIdentifier("equipment${equipment.equipmentName}","drawable",packageName)

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

        makeAttributesText()
    }

    @Suppress("DEPRECATION")
    private fun getEquipment(): Equipment{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getParcelableExtra("Equipment", Equipment::class.java).toEquipment()
        else intent.getParcelableExtra<Equipment>("Equipment").toEquipment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_equipment_details,menu)
        binding.toolbar.overflowIcon=AppCompatResources.getDrawable(this,R.drawable.dots3gold)
        return super.onCreateOptionsMenu(menu)
    }

    private fun makeAttributesText(){

        val starJedi = resources.getFont(R.font.starjedi)

        with(binding.Attributes.text){
            when{
                (contains("weapon",true) || contains("Blaster",true))&&(!contains("accessory"))->{
                    val weaponPropList = resources.getTextArray(R.array.weapon_properties)
                    val weaponPropMap = mutableMapOf<String,CharSequence>()
                    for(i in weaponPropList.indices step 2){
                        weaponPropMap[weaponPropList[i].toString()]=weaponPropList[i+1]
                    }
                    binding.Text.text= buildSpannedString{
                        append(equipment.detailsText)
                        appendLine(" ")
                        weaponPropMap.keys.forEach{
                            if(binding.Properties.text.contains(it,true)){
                                appendLine(" ")
                                val spanit= SpannableString(it)
                                spanit.setSpan(TypefaceSpan(starJedi),0,it.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                scale(1.1F){
                                    appendLine(spanit)
                                }
                                appendLine(weaponPropMap[it])
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
                                spanit.setSpan(TypefaceSpan(starJedi),0,it.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
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

    private fun returntomain() {
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}