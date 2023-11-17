package com.example.swtrial2.equipment

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.swtrial2.R
import com.example.swtrial2.databinding.EquipmentInfoAdventuringGearBinding
import com.example.swtrial2.databinding.EquipmentInfoArmorsAndShieldsBinding
import com.example.swtrial2.databinding.EquipmentInfoBinding
import com.example.swtrial2.databinding.EquipmentInfoExpensesBinding
import com.example.swtrial2.databinding.EquipmentInfoTablesBinding
import com.example.swtrial2.databinding.EquipmentInfoToolsBinding
import com.example.swtrial2.databinding.EquipmentInfoWealthBinding
import com.example.swtrial2.databinding.EquipmentInfoWeaponsBinding
import kotlin.math.absoluteValue

class EquipmentInfo : AppCompatActivity() , GestureDetector.OnGestureListener {
    private lateinit var binding: EquipmentInfoBinding
    private lateinit var bindingWealth: EquipmentInfoWealthBinding
    private lateinit var bindingArmorsAndShields: EquipmentInfoArmorsAndShieldsBinding
    private lateinit var bindingWeapons: EquipmentInfoWeaponsBinding
    private lateinit var bindingAdventuringGear: EquipmentInfoAdventuringGearBinding
    private lateinit var bindingTools: EquipmentInfoToolsBinding
    private lateinit var bindingExpenses: EquipmentInfoExpensesBinding
    private lateinit var bindingTables: EquipmentInfoTablesBinding
    private lateinit var advgearmenu: PopupMenu

    private val tabList = listOf("Wealth","Armors & Shields","Weapons","Adventuring Gear","Tools","Expenses","Tables")

    private lateinit var gestdect: GestureDetector
    private var rect = Rect()
    private val swipethreshold = 100
    private var scrollmode=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= EquipmentInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingWealth = EquipmentInfoWealthBinding.inflate(layoutInflater,binding.scrolly,false)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        gestdect= GestureDetector(this,this)

        binding.dummybutton.setOnClickListener { this.openOptionsMenu() }

        binding.BackButton.setOnClickListener { returntomain()
        }

        bindingWealth.scrolly.removeAllViews()

        advgearmenu=PopupMenu(this,binding.fab,3)
        menuInflater.inflate(R.menu.menu_equipment_info_advgear_scrollpoints,advgearmenu.menu)
        binding.fab.setOnClickListener {advgearmenu.show()}
        advgearmenu.setOnMenuItemClickListener {menuItem->
            binding.scrolly.smoothScrollTo(0,when(menuItem.title.toString().trim()){
                "Equipment Packs"-> 300
                "Ammunition"->      4600
                "Communications"->  9600
                "Data"->            13850
                "Explosives"->      18300
                "Life Support"->    28050
                "Medical Supplies"->30300
                "Storage"->         35640
                "Utilities"->       38320
                "Accessories"->     47750
                else->              0
            })
            false
        }

        binding.scrolly.addView(bindingWealth.llWealth)


        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })



    }
    private fun returntomain(){
        finish()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_equipment_info,menu)
        val drawable = AppCompatResources.getDrawable(this,R.drawable.downarrowgold)
        binding.toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.dummybutton.text=item.title.toString()
        changeview()
        return super.onOptionsItemSelected(item)
    }
    private fun changeview() {
        binding.fab.visibility= View.GONE
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        binding.scrolly.removeAllViews()
        when(binding.dummybutton.text.toString()){
            tabList[0]->{ binding.scrolly.addView(bindingWealth.llWealth);scrollmode = 0 }
            tabList[1]->{
                if (!this::bindingArmorsAndShields.isInitialized) bindingArmorsAndShields = EquipmentInfoArmorsAndShieldsBinding.inflate(layoutInflater,binding.scrolly,false);bindingArmorsAndShields.scrolly.removeAllViews()
                binding.scrolly.addView(bindingArmorsAndShields.llArmorsAndShields);scrollmode = 2}
            tabList[2]->{
                if (!this::bindingWeapons.isInitialized) {bindingWeapons = EquipmentInfoWeaponsBinding.inflate(layoutInflater, binding.scrolly, false);bindingWeapons.scrolly.removeAllViews()}
                binding.scrolly.addView(bindingWeapons.llWeapons);scrollmode = 2}
            tabList[3]->{
                if (!this::bindingAdventuringGear.isInitialized) {bindingAdventuringGear = EquipmentInfoAdventuringGearBinding.inflate(layoutInflater, binding.scrolly, false);bindingAdventuringGear.scrolly.removeAllViews()}
                binding.scrolly.addView(bindingAdventuringGear.llAdventuringGear);binding.fab.visibility= View.VISIBLE;scrollmode = 2}
            tabList[4]->{
                if (!this::bindingTools.isInitialized) bindingTools = EquipmentInfoToolsBinding.inflate(layoutInflater,binding.scrolly,false);bindingTools.scrolly.removeAllViews()
                binding.scrolly.addView(bindingTools.llTools);scrollmode = 2}
            tabList[5]->{
                if (!this::bindingExpenses.isInitialized) bindingExpenses = EquipmentInfoExpensesBinding.inflate(layoutInflater,binding.scrolly,false);bindingExpenses.scrolly.removeAllViews()
                binding.scrolly.addView(bindingExpenses.llExpenses);scrollmode = 2}
            tabList[6]->{
                if (!this::bindingTables.isInitialized) bindingTables = EquipmentInfoTablesBinding.inflate(layoutInflater,binding.scrolly,false);bindingTables.scrolly.removeAllViews()
                binding.scrolly.addView(bindingTables.llTables);scrollmode = 1}

            else->{binding.scrolly.addView(bindingWealth.llWealth);scrollmode = 0;Toast.makeText(this,"error unknown Tab",Toast.LENGTH_LONG).show()}
        }
    }
    private fun swipeview(dir: String) {
        when(dir){
            "LtoR"->{
                if (scrollmode==0){
                    binding.dummybutton.text=tabList.last()
                    changeview()
                }
                else binding.dummybutton.text=tabList[tabList.indexOf(binding.dummybutton.text.toString())-1];changeview()
            }
            "RtoL"->{
                if (scrollmode==1){
                    binding.dummybutton.text=tabList.first()
                    changeview()
                }
                else binding.dummybutton.text=tabList[tabList.indexOf(binding.dummybutton.text.toString())+1];changeview()
            }
        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        super.dispatchTouchEvent(ev)
        return gestdect.onTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestdect.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(p0: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent) {
        return
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
        return
    }

    override fun onFling(e0: MotionEvent?, e1: MotionEvent, vx: Float, vy: Float): Boolean {
        if (e0 != null) {
            val diffX = e1.x - e0.x
            if(diffX.absoluteValue>(e1.y-e0.y).absoluteValue) {
                if(binding.dummybutton.text.toString()!="Tables"){
                    return if (diffX.absoluteValue > swipethreshold && vx.absoluteValue > swipethreshold) {
                        //L to R
                        if (diffX > 0) {
                            swipeview("LtoR")
                        }
                        //R to L
                        else if(diffX<0){
                            swipeview("RtoL")
                        }
                        true
                    } else{
                        false
                    }
                }
                else{
                    bindingTables.hscrollArmors.getGlobalVisibleRect(rect)
                    if(rect.contains(e0.x.toInt(),e0.y.toInt())){
                        return false
                    }
                    else{
                        return if (diffX.absoluteValue > swipethreshold && vx.absoluteValue > swipethreshold) {
                            //L to R
                            if (diffX > 0) {
                                swipeview("LtoR")
                            }
                            //R to L
                            else if(diffX<0){
                                swipeview("RtoL")
                            }
                            true
                        } else{
                            false
                        }
                    }
                }
            }
            else{
                return false
            }
        }
        else return false
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}


