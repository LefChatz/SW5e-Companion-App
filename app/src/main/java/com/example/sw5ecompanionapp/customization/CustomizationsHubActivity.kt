package com.example.sw5ecompanionapp.customization

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.SW5ECompanionApp
import com.example.sw5ecompanionapp.databinding.CustomizationsBinding
import com.example.sw5ecompanionapp.databinding.CustomizationsHubBinding
import java.util.LinkedList

class CustomizationsHubActivity : AppCompatActivity() {

    private lateinit var binding: CustomizationsHubBinding
    private lateinit var inflater: LayoutInflater
    private lateinit var tempView: View
    private var mode=0
    private lateinit var customizations: Array<String>
    private lateinit var customizationsInfo: LinkedList<CharSequence>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CustomizationsHubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflater=layoutInflater

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        customizations = resources.getStringArray(R.array.customization_options)

        customizationsInfo = LinkedList()

        generateOptions()

        binding.BackButton.setOnClickListener { returntomain() }

        binding.infobutton.setOnClickListener { generateCustomizationsInfo() }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    private fun generateOptions(){
        customizations.forEach { option ->
            val bt = inflater.inflate(R.layout.customizations_button,binding.ll,false)
            bt.findViewById<TextView>(R.id.customization_option).text=option.replace("_"," ")
            bt.setOnClickListener{ startActivity(Intent(this, CustomizationsActivity::class.java).putExtra("Customization Option",option)) }
            binding.ll.addView(bt)
        }
    }

    @SuppressLint("CutPasteId")
    private fun generateCustomizationsInfo(){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        /*if(mode==0){
            binding.ll.removeAllViews()
            mode=1
        }
        else{
            Toast.makeText(this,"Already at Species info",Toast.LENGTH_SHORT)
                .show()
        }*/
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
            startActivity(Intent(this, SW5ECompanionApp::class.java))
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