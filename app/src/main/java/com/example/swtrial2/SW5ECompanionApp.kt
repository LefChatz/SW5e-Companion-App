package com.example.swtrial2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.swtrial2.databinding.ActivityHubAttemptBinding

class SW5ECompanionApp : AppCompatActivity() {

    private lateinit var binding: ActivityHubAttemptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHubAttemptBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun portal(view: View){
        when(view.id){
            R.id.buttonforce->{startActivity(Intent(this, ForceCastingActivity::class.java))}
            R.id.buttonclasses->{startActivity(Intent(this, ClassesActivity::class.java))}
            R.id.buttonspecies->{startActivity(Intent(this, SpeciesActivity::class.java))}
            R.id.buttonequipment->{startActivity(Intent(this, EquipmentActivity::class.java))}
        }
        finish()
    }

}