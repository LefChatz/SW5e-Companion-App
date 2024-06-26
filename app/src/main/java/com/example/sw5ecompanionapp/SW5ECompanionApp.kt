package com.example.sw5ecompanionapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.sw5ecompanionapp.backgrounds.BackgroundsActivity
import com.example.sw5ecompanionapp.classes.ClassesActivity
import com.example.sw5ecompanionapp.databinding.ActivityHubBinding
import com.example.sw5ecompanionapp.equipment.EquipmentActivity
import com.example.sw5ecompanionapp.forcecasting.ForcecastingActivity
import com.example.sw5ecompanionapp.species.SpeciesActivity

class SW5ECompanionApp : AppCompatActivity() {

    private lateinit var binding: ActivityHubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHubBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun portal(view: View){
        when(view.id){
            binding.buttonclasses.id->      startActivity(Intent(this, ClassesActivity::class.java))
            binding.buttonbackgrounds.id->  startActivity(Intent(this, BackgroundsActivity::class.java))
            binding.buttonspecies.id->      startActivity(Intent(this, SpeciesActivity::class.java))
            binding.buttonforce.id->        startActivity(Intent(this, ForcecastingActivity::class.java))
            binding.buttonequipment.id->    startActivity(Intent(this, EquipmentActivity::class.java))
        }
        finish()
    }
}