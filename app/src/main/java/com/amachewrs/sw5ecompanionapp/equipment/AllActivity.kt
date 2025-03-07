
package com.amachewrs.sw5ecompanionapp.equipment
//
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.EquipmentAllListBinding
import com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff.Equipment
import com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff.EquipmentAdapter
import com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff.getNameList
import com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff.sortEquipmentByName
import com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff.sortEquipmentByNameDescending
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AllActivity : AppCompatActivity() {
    private lateinit var binding: EquipmentAllListBinding
    private lateinit var equipmentAdapter: EquipmentAdapter
    private lateinit var equipmentPrefs: SharedPreferences
    private lateinit var equipmentMenu:Menu

    private var equipmentList = mutableListOf<Equipment>()
    private val favouriteEquipmentList = mutableListOf<String>()
    private val eqprefs = "EquipmentPrefs"
    private val eqfavlistkey = "FavouriteEquipmentList"

    private val category1Filters = listOf("Simple","Martial","Light","Medium","Heavy")
    private val cat1="category 1"
    private val category2Filters = listOf("Vibroweapon","Lightweapon","Blaster","Armor","Shield")
    private val cat2="category 2"

    private val filters = mapOf<String,MutableList<String>>(Pair(cat1, mutableListOf()),Pair(cat2, mutableListOf()))

    private lateinit var weaponFilterMenuItems: List<MenuItem>
    private lateinit var armorFilterMenuItems: List<MenuItem>
    private lateinit var advGearFilterMenuItems: List<MenuItem>

    private var searchedText=""
    private var keepmenu = false
    private var favchecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EquipmentAllListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        equipmentPrefs=getSharedPreferences(eqprefs, Context.MODE_PRIVATE)
        favouriteEquipmentList.addAll(equipmentPrefs.getStringSet(eqfavlistkey, mutableSetOf())!!.toMutableList())

        equipmentList=getEquipmentList().sortEquipmentByName()

        binding.BackButton.setOnClickListener { returntomain() }

        equipmentAdapter = EquipmentAdapter(this,equipmentList,favouriteEquipmentList)
        binding.reclview.adapter = equipmentAdapter

        binding.searchview.isIconifiedByDefault=false
        binding.searchview.queryHint="Search..."
        binding.searchview.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop("sharp")
                if(enttext.isNullOrBlank()){
                    object : CountDownTimer(1000, 1001) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(searchedText.isBlank()){binding.searchview.clearFocus()}
                        }
                    }.start()
                    searchedText=""
                    updateAdapterList()
                }
                else {
                    searchedText= enttext.trim().replace(" ","_")
                    if(equipmentList.getNameList().none { it.contains(searchedText,true)}){
                        equipmentAdapter.setEquipmentList(mutableListOf(Equipment("NoSuchEquipment","NoSuchEquipment")))
                    }
                    else {
                        updateAdapterList()
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                returntotop("sharp")
                if(enttext.isNullOrBlank()){
                    object : CountDownTimer(1000, 1001) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(searchedText.isBlank()){binding.searchview.clearFocus()}
                        }
                    }.start()
                    searchedText=""
                    updateAdapterList()
                }
                else {
                    searchedText= enttext.trim().replace(" ","_")
                    if(equipmentList.getNameList().none { it.contains(searchedText,true)}){
                        equipmentAdapter.setEquipmentList(mutableListOf(Equipment("NoSuchEquipment")))
                    }
                    else {
                        updateAdapterList()
                    }
                }
                return false
            }
        })
        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener{
            returntotop("smooth")
        }
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_equipment,menu)
        if (menu != null) {
            equipmentMenu = menu
        }
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this,R.drawable.downarrowgold)

        weaponFilterMenuItems = listOf(equipmentMenu.findItem(R.id.eqmenu_martial),equipmentMenu.findItem(R.id.eqmenu_simple),equipmentMenu.findItem(R.id.eqmenu_vibroweapons),equipmentMenu.findItem(R.id.eqmenu_lightweapons),equipmentMenu.findItem(R.id.eqmenu_blasters))
        weaponFilterMenuItems.forEach {addFilter(it.title.toString().trim())}

        armorFilterMenuItems = listOf(equipmentMenu.findItem(R.id.eqmenu_light),equipmentMenu.findItem(R.id.eqmenu_medium),equipmentMenu.findItem(R.id.eqmenu_heavy),equipmentMenu.findItem(R.id.eqmenu_armors),equipmentMenu.findItem(R.id.eqmenu_shields))
        armorFilterMenuItems.forEach {addFilter(it.title.toString().trim())}

        advGearFilterMenuItems = listOf(equipmentMenu.findItem(R.id.eqmenu_ammunition),equipmentMenu.findItem(R.id.eqmenu_communications),equipmentMenu.findItem(R.id.eqmenu_data),equipmentMenu.findItem(R.id.eqmenu_life_support),equipmentMenu.findItem(R.id.eqmenu_medical_supplies),equipmentMenu.findItem(R.id.eqmenu_storage),equipmentMenu.findItem(R.id.eqmenu_utilities),equipmentMenu.findItem(R.id.eqmenu_accessories),equipmentMenu.findItem(R.id.eqmenu_tools))
        advGearFilterMenuItems.forEach {addFilter(it.title.toString().trim())}
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        with(equipmentMenu.findItem(R.id.eqmenu_ok)){
            if(!isVisible)isVisible=true
        }
        if(!item.title.isNullOrBlank()){
            keepmenu = true
            with(item.title){ when{
                    this!! == getText(R.string.sortABCdown) ->{
                        keepmenu = false
                        equipmentList=equipmentList.sortEquipmentByNameDescending()
                        item.title=getText(R.string.sortABCup)
                        returntotop("sharp")}
                    equals(getText(R.string.sortABCup))->{
                        keepmenu = false
                        equipmentList=equipmentList.sortEquipmentByName()
                        item.title = getText(R.string.sortABCdown)
                        returntotop("sharp")}
                    contains("weapons",true) ->{
                        if(!item.isChecked){
                            equipmentMenu.setGroupVisible(R.id.eqmenu_weapons_group,true)
                            weaponFilterMenuItems.forEach {addFilter(it.title.toString().trim());it.isChecked=true}
                        }
                        else{
                            equipmentMenu.setGroupVisible(R.id.eqmenu_weapons_group,false)
                            weaponFilterMenuItems.forEach {removeFilter(it.title.toString().trim());it.isChecked=false}
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains(getString(R.string.equipment_menu_armors_and_shields))->{
                        if(!item.isChecked){
                            equipmentMenu.setGroupVisible(R.id.eqmenu_armors_group,true)
                            armorFilterMenuItems.forEach {addFilter(it.title.toString().trim());it.isChecked=true}
                        }
                        else{
                            equipmentMenu.setGroupVisible(R.id.eqmenu_armors_group,false)
                            armorFilterMenuItems.forEach {removeFilter(it.title.toString().trim());it.isChecked=false}
                        }
                        
                        item.isChecked=!item.isChecked
                    }

                    contains("Adventuring Gear",true)->{
                        if(!item.isChecked){
                            equipmentMenu.setGroupVisible(R.id.eqmenu_advgear_group,true)
                            advGearFilterMenuItems.forEach {addFilter(it.title.toString().trim());it.isChecked=true}

                        }
                        else{
                            equipmentMenu.setGroupVisible(R.id.eqmenu_advgear_group,false)
                            advGearFilterMenuItems.forEach {removeFilter(it.title.toString().trim());it.isChecked=false}
                        }
                        
                        item.isChecked=!item.isChecked
                    }

                    contains("Confirm Filters",true)-> {
                        keepmenu = false
                        item.isVisible =false
                    }

                    contains("favorites",true)->{
                        favchecked = !item.isChecked
                        item.isChecked= !item.isChecked
                    }
                    else->{
                        updateSubcategoryFilter(item)
                        checkEmptyCategory()
                    }
                }
            }
            updateAdapterList()
        }
        if (keepmenu){
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
            item.actionView = View(this)
            item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                    return false
                }

                override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                    return false
                }
            })
        }
        return false
    }
    private fun updateAdapterList(){
        equipmentAdapter.setEquipmentList(equipmentList.filter{filterEquipment(it)})
    }
    private fun updateSubcategoryFilter(item: MenuItem){
        if(!item.isChecked){
            addFilter(item.title.toString().trim())
        }
        else{
            removeFilter(item.title.toString().trim())
        }
        item.isChecked=!item.isChecked
    }
    private fun filterEquipment(equipment: Equipment): Boolean{
        if (favchecked && equipment.equipmentname !in favouriteEquipmentList) return false
        if (searchedText.isNotEmpty() && !equipment.equipmentname.contains(searchedText,false)) return false

        if (filters.all { list -> list.value.any {equipment.attributes.contains(it,true)}}) return true

        return false
    }

    private fun getEquipmentList(): MutableList<Equipment>{
        val tempEquipmentList = mutableListOf<Equipment>()
        val equipmentsTextArray = resources.getTextArray(R.array.newequipmentslist)
        for (i in 8..equipmentsTextArray.size step 9){
            tempEquipmentList.add(Equipment(equipmentsTextArray[i-8].toString(),equipmentsTextArray[i-7],equipmentsTextArray[i-6].toString(),equipmentsTextArray[i-5].toString().toInt(),equipmentsTextArray[i-4].toString().toDouble(),equipmentsTextArray[i-3].toString(),equipmentsTextArray[i-2].toString(),equipmentsTextArray[i-1],equipmentsTextArray[i].toString()))
        }
        return tempEquipmentList
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        keepmenu=false
        return super.onPrepareOptionsMenu(menu)
    }
    private fun addFilter(filter: String){
        if (filter in category1Filters) {filters[cat1]!!.add(filter);return}
        if (filter in category2Filters) {filters[cat2]!!.add(filter);return}
        filters[cat1]!!.add(filter)
        filters[cat2]!!.add(filter)
    }
    private fun removeFilter(filter: String){
        if (filter in category1Filters) {filters[cat1]!!.remove(filter);return}
        if (filter in category2Filters) {filters[cat2]!!.remove(filter);return}
        filters[cat1]!!.remove(filter)
        filters[cat2]!!.remove(filter)
    }
    private fun checkEmptyCategory(){
        if (weaponFilterMenuItems.none { it.isChecked }){equipmentMenu.setGroupVisible(R.id.eqmenu_weapons_group,false);equipmentMenu.findItem(R.id.eqmenu_weapons).isChecked=false}
        if (armorFilterMenuItems.none { it.isChecked }){equipmentMenu.setGroupVisible(R.id.eqmenu_armors_group,false);equipmentMenu.findItem(R.id.eqmenu_armors_and_shields).isChecked=false}
        if (advGearFilterMenuItems.none { it.isChecked }){equipmentMenu.setGroupVisible(R.id.eqmenu_advgear_group,false);equipmentMenu.findItem(R.id.eqmenu_advgear).isChecked=false}
    }
    private fun returntotop(mode: String){
        when(mode){
            "smooth"->binding.reclview.smoothScrollToPosition(0)
            "sharp"->binding.reclview.scrollToPosition(0)
        }
    }
    private fun returntomain() {
        with(equipmentPrefs.edit()){
            putStringSet(eqfavlistkey,favouriteEquipmentList.toSet())
            apply()
        }
        finish()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}