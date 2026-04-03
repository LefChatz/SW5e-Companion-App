package com.amachewrs.sw5ecompanionapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.backgrounds.BackgroundsActivity
import com.amachewrs.sw5ecompanionapp.classes.ClassesActivity
import com.amachewrs.sw5ecompanionapp.customization.CustomizationsHubActivity
import com.amachewrs.sw5ecompanionapp.databinding.ActivityHubBinding
import com.amachewrs.sw5ecompanionapp.equipment.EquipmentActivity
import com.amachewrs.sw5ecompanionapp.feats.FeatsActivity
import com.amachewrs.sw5ecompanionapp.forcecasting.ForcecastingActivity
import com.amachewrs.sw5ecompanionapp.maneuvers.ManeuversActivity
import com.amachewrs.sw5ecompanionapp.species.SpeciesActivity
import com.amachewrs.sw5ecompanionapp.techcasting.TechcastingActivity
import com.amachewrs.sw5ecompanionapp.utility.Utilities.Companion.showSnackBar

class SW5ECompanionApp : AppCompatActivity() {

    private lateinit var binding: ActivityHubBinding
    private lateinit var aboutView: TextView
    private lateinit var aboutItem: MenuItem
    private lateinit var settingsMenu: PopupMenu
    private var leave=false
    private var atAbout= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHubBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED
        }

        this.setTheme(R.style.Base_ThemeOverlay_AppCompat_Dark_NoActionBar)
        setContentView(binding.root)

        settingsMenu = PopupMenu(this,binding.menubutton)
        settingsMenu.inflate(R.menu.menu_hub_attempt)
        settingsMenu.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.about -> {aboutItem=item;handleAboutSwitch()}
                R.id.report_errors -> startActivity(Intent(Intent.ACTION_SENDTO).setData(resources.getString(R.string.error_open_email).toUri()))
            }
            true
        }

        binding.menubutton.setOnClickListener {
            settingsMenu.show()
        }

        aboutView = layoutInflater.inflate(R.layout.universal_textview_nofont_gold,binding.scrolly,false).findViewById(R.id.textview)
        aboutView.text = resources.getText(R.string.about_text)
        aboutView.updatePadding(0,0,0,50)

        onBackPressedDispatcher.addCallback(this,object: OnBackPressedCallback(true){override fun handleOnBackPressed(){backPressed()}})
    }
    fun portal(view: View){
        startActivity(Intent(this, when(view.id){
            binding.buttonclasses.id->       ClassesActivity::class.java
            binding.buttonbackgrounds.id->   BackgroundsActivity::class.java
            binding.buttonspecies.id->       SpeciesActivity::class.java
            binding.buttonforce.id->         ForcecastingActivity::class.java
            binding.buttonequipment.id->     EquipmentActivity::class.java
            binding.buttontech.id->          TechcastingActivity::class.java
            binding.buttonfeats.id->         FeatsActivity::class.java
            binding.buttonmaneuvers.id->     ManeuversActivity::class.java
            binding.buttoncustoms.id->       CustomizationsHubActivity::class.java
            else ->                          ClassesActivity::class.java
        }))
    }

    private fun handleAboutSwitch(){
        if (!atAbout){
            binding.scrolly.removeView(binding.constl)
            binding.scrolly.addView(aboutView)
            aboutItem.title = resources.getText(R.string.back_gold)
        }
        else{
            binding.scrolly.removeView(aboutView)
            binding.scrolly.addView(binding.constl)
            aboutItem.title = resources.getText(R.string.about)
        }
        atAbout=!atAbout
    }
    private fun backPressed(){
        if (!leave) {
            if (atAbout) handleAboutSwitch()
            else{
                showSnackBar("press back again to exit the app",binding.coord,this)
                leave=true
                Handler(Looper.getMainLooper()).postDelayed({leave=false},3000)
            }
        }
        else finish()
    }
}