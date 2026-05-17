package com.amachewrs.sw5ecompanionapp.equipment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.EquipmentsBinding
import com.amachewrs.sw5ecompanionapp.utility.Utilities.Companion.showSnackBar


class EquipmentActivity : AppCompatActivity() {
    private lateinit var binding: EquipmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = EquipmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.BackButton.setOnClickListener { returntomain() }

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
            else->showSnackBar("Error",binding.coord,this)
        }
    }
    private fun returntomain() {
        finish()
    }
}