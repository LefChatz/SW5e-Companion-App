package com.example.sw5ecompanionapp.customization

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.databinding.CustomizationsBinding
import java.util.LinkedList
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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

        binding.infobutton.setOnClickListener { if (mode == 0) generateCustomizationsInfo(customOption) }

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
        val identifier = resources.getIdentifier("customization_$customOption"+"_info","string",packageName)
        if (identifier!=0){
            mode=1
            Toast.makeText(this,getText(identifier),Toast.LENGTH_LONG)
                .show()
            Executors.newSingleThreadScheduledExecutor().schedule({
                mode=0
            }, 5, TimeUnit.SECONDS)
        }
        else {
            Toast.makeText(this,"Error, Could not find Info for this part of the app. \n Please report this to #TODO",Toast.LENGTH_LONG)
                .show()
        }
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