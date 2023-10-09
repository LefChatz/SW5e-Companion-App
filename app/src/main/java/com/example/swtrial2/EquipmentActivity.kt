package com.example.swtrial2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swtrial2.classes.*
import com.example.swtrial2.databinding.ActivityEquipmentBinding
import com.example.swtrial2.equipment.AdvGearActivity
import com.example.swtrial2.equipment.AllActivity
import com.example.swtrial2.equipment.ArmourActivity
import com.example.swtrial2.equipment.WeaponsActivity


class EquipmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEquipmentBinding
    private lateinit var infobutton: ImageButton
    var mode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEquipmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        infobutton=findViewById(R.id.infobutton)
        infobutton.setOnClickListener {
            if(mode==0){
                mode=1
            }
            else{
                Toast.makeText(this,"Already at Class info", Toast.LENGTH_SHORT)
                    .show()
            }
            return@setOnClickListener
        }
    }
    fun openequipment(view: View){
        when(view.id){
            R.id.weapons->{startActivity(Intent(this, WeaponsActivity::class.java))}
            R.id.armourandshields->{startActivity(Intent(this, ArmourActivity::class.java))}
            R.id.advgear->{startActivity(Intent(this, AdvGearActivity::class.java))}
            R.id.all->{startActivity(Intent(this, AllActivity::class.java))}
            else->{
                Toast.makeText(this,"Error",Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    fun returntomain(view: View?) {
        if(mode==0){
            startActivity(Intent(this,SW5ECompanionApp::class.java))
            finish()
        }
        else{
            mode=0
        }
    }
    @Deprecated("Deprecated in Java", ReplaceWith("returntomain(null)"))
    override fun onBackPressed(){
        returntomain(null)
    }
}