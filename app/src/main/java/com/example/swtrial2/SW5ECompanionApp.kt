package com.example.swtrial2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.swtrial2.backgrounds.BackgroundsActivity
import com.example.swtrial2.classes.ClassesActivity
import com.example.swtrial2.databinding.ActivityHubBinding
import com.example.swtrial2.equipment.EquipmentActivity
import com.example.swtrial2.forcecasting.ForcecastingActivity
import com.example.swtrial2.species.SpeciesActivity

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