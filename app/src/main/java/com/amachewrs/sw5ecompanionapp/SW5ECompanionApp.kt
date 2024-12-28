package com.amachewrs.sw5ecompanionapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
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

class SW5ECompanionApp : AppCompatActivity() {

    private lateinit var binding: ActivityHubBinding
    private var leave=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this,object: OnBackPressedCallback(true){override fun handleOnBackPressed(){backpressed()}})
    }
    fun portal(view: View){
        when(view.id){
            binding.buttonclasses.id->      startActivity(Intent(this, ClassesActivity::class.java))
            binding.buttonbackgrounds.id->  startActivity(Intent(this, BackgroundsActivity::class.java))
            binding.buttonspecies.id->      startActivity(Intent(this, SpeciesActivity::class.java))
            binding.buttonforce.id->        startActivity(Intent(this, ForcecastingActivity::class.java))
            binding.buttonequipment.id->    startActivity(Intent(this, EquipmentActivity::class.java))
            binding.buttontech.id->         startActivity(Intent(this, TechcastingActivity::class.java))
            binding.buttonfeats.id->        startActivity(Intent(this, FeatsActivity::class.java))
            binding.buttonmaneuvers.id->    startActivity(Intent(this, ManeuversActivity::class.java))
            binding.buttoncustoms.id->      startActivity(Intent(this, CustomizationsHubActivity::class.java))
        }
        finish()
    }
    private fun backpressed(){
        if (!leave) {
            Toast.makeText(this,"press back again to exit the app",Toast.LENGTH_SHORT).show()
            leave=true
            Handler(Looper.getMainLooper()).postDelayed({leave=false},3000)
        }
        else finish()
    }
}