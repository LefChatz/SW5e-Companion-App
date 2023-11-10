package com.example.swtrial2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
            R.id.buttonforce->{startActivity(Intent(this, ForcecastingActivity::class.java))}
            R.id.buttonclasses->{startActivity(Intent(this, ClassesActivity::class.java))}
            R.id.buttonspecies->{startActivity(Intent(this, SpeciesActivity::class.java))}
            R.id.buttonequipment->{startActivity(Intent(this, EquipmentActivity::class.java))}
        }
        finish()
    }

}