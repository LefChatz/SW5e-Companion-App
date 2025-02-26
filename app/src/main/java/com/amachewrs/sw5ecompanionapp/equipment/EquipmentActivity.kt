package com.amachewrs.sw5ecompanionapp.equipment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.SW5ECompanionApp
import com.amachewrs.sw5ecompanionapp.databinding.EquipmentsBinding


class EquipmentActivity : AppCompatActivity() {
    private lateinit var binding: EquipmentsBinding
    /*private var mode = 0*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = EquipmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.BackButton.setOnClickListener { returntomain() }

        /*binding.infobutton.setOnClickListener {
            if(mode==0){
                mode=1
            }
            else{
                Toast.makeText(this,"Already at Equipment info", Toast.LENGTH_SHORT)
                    .show()
            }
            return@setOnClickListener
        }*/
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }
    fun openequipment(view: View){
        when(view.id){
            R.id.info ->{startActivity(Intent(this, EquipmentInfo::class.java))}
            R.id.all ->{startActivity(Intent(this, AllActivity::class.java))}
            else->{
                Toast.makeText(this,"Error",Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    private fun returntomain() {
        finish()
    }
}