package com.example.sw5ecompanionapp.customization

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.databinding.CustomizationsBinding
import java.util.LinkedList

class CustomizationsActivity : AppCompatActivity() {

    private lateinit var binding: CustomizationsBinding
    private lateinit var inflater: LayoutInflater
    private lateinit var tempView: View
    private var mode=0
    private var customizationOptions= mutableSetOf<CustomizationOption>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val customOption = intent.getStringExtra("Customization Option").toString()

        binding = CustomizationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflater=layoutInflater

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        @SuppressLint("DiscouragedApi")
        val identifier = resources.getIdentifier(customOption,"array",packageName)

        if (identifier==0) {
            val tempText = inflater.inflate(R.layout.universal_textview_nofont_gold,binding.ll,false).findViewById<TextView>(R.id.textview)
            tempText.text = resources.getString(R.string.error_please_report_this)
            binding.ll.addView(tempText)
        }
        else{
            val detailsHeap = LinkedList<CharSequence>()
            resources.getTextArray(identifier).toCollection(detailsHeap)
            while (detailsHeap.size>4){
                customizationOptions.add(CustomizationOption(detailsHeap.poll()!!.toString(),detailsHeap.poll()!!.toString(),detailsHeap.poll()!!.toString(),detailsHeap.poll()!!.toString().toBoolean(),detailsHeap.poll()!!))
            }
        }

        generateOptions()

        binding.BackButton.setOnClickListener { returntomain() }

        binding.infobutton.setOnClickListener { if (mode == 0) generateCustomizationsInfo(customOption) else Toast.makeText(this,"to return press back",Toast.LENGTH_SHORT).show() }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    private fun generateOptions(){
        customizationOptions.forEach { option ->
            val bt = inflater.inflate(if (option.isBig)R.layout.customizations_button_big else R.layout.customizations_button,binding.ll,false)
            val txt = bt.findViewById<TextView>(R.id.customization_option)
            txt.text=option.name

            if(option.hasPreq()) {
                bt.findViewById<TextView>(R.id.customization_option_preq).text = option.preq
                (txt.layoutParams as ConstraintLayout.LayoutParams).bottomToBottom = -1
            }

            bt.findViewById<TextView>(R.id.customizations_button_sourcebook).text = option.source
            bt.setOnClickListener{ startActivity(Intent(this, CustomizationsDetailsActivity::class.java).putExtra("Customization Option",option)) }
            binding.ll.addView(bt)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun generateCustomizationsInfo(customOption: String){
        when(customOption){
            "fighting_styles"-> generateInfoViews(LinkedList(resources.getTextArray(R.array.fighting_styles_info).toMutableSet()))
            "fighting_masteries"-> generateInfoViews(LinkedList(resources.getTextArray(R.array.fighting_masteries_info).toMutableSet()))
            "lightsaber_forms"-> generateInfoViews(LinkedList(resources.getTextArray(R.array.lightsaber_forms_info).toMutableSet()))
            else->{
                Toast.makeText(this,"Could not find Info for this part of the app.",Toast.LENGTH_SHORT)
                    .show()
                Toast.makeText(this,"send suggestions at sw5ecompanionapp@gmail.com",Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun generateInfoViews(infoHeap: LinkedList<CharSequence>){
        mode=1
        binding.ll.removeAllViews()
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        val txt = inflater.inflate(R.layout.universal_textview_starjedi_gold,binding.ll,false)
        txt.findViewById<TextView>(R.id.textview).text = infoHeap.poll()
        binding.ll.addView(txt)
        val diesize= infoHeap.poll()!!.toString().toInt()
        val title=infoHeap.poll()
        tempView = inflater.inflate(R.layout.two_column_d_table,binding.ll,false)
        val table = tempView.findViewById<TableLayout>(R.id.table)
        val dDiesize="d$diesize"
        tempView.findViewById<TextView>(R.id.dieSize_1).text=dDiesize
        tempView.findViewById<TextView>(R.id.dieSize_2).text=dDiesize
        tempView.findViewById<TextView>(R.id.title_1).text=title
        tempView.findViewById<TextView>(R.id.title_2).text=title
        for (i in 1..diesize/2){
            if (infoHeap.size<2) break
            val extraRow = inflater.inflate(R.layout.two_column_d_table_extra_row_gold,table,false)
            extraRow.findViewById<TextView>(R.id.extra_row_dieNumber_1).text="$i"
            extraRow.findViewById<TextView>(R.id.extra_row_value_1).text=infoHeap.poll()
            val col2dienum=i+(diesize/2)
            extraRow.findViewById<TextView>(R.id.extra_row_dieNumber_2).text="$col2dienum"
            extraRow.findViewById<TextView>(R.id.extra_row_value_2).text=infoHeap.poll()
            if (i%2==1) extraRow.background=null
            table.addView(extraRow)
        }
        if (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).width<resources.displayMetrics.widthPixels) (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).layoutParams as LinearLayout.LayoutParams).gravity=1
        binding.ll.addView(tempView)
    }
    //Menu creation: Currently unnecessary
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_species,menu)
        val drawable= AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        binding.toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }*/

    private fun returntomain() {
        if(mode==0){
            finish()
        }
        else{
            binding.scrolly.scrollTo(0,0)
            binding.scrolly.fling(0)
            binding.ll.removeAllViews()

            generateOptions()

            mode=0
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}