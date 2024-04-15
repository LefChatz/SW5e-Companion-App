package com.example.sw5ecompanionapp.equipment
//
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.databinding.EquipmentAllListBinding
import com.example.sw5ecompanionapp.equipment.equipmentadapterstuff.EquipmentAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AllActivity : AppCompatActivity() {
    private lateinit var binding: EquipmentAllListBinding
    private lateinit var equipmentAdapter: EquipmentAdapter
    private lateinit var favSharedPreferences: SharedPreferences
    private lateinit var equipmentMenu:Menu

    private var bigEquipmentList = listOf<String>()
    private var equipmentList = listOf<String>()
    private var equipmentWeapons = listOf<String>()
    private var equipmentArmors = listOf<String>()
    private var equipmentAdvGear = listOf<String>()
    private var currentEquipmentList = listOf<String>()
    private var searchedList = listOf<String>()
    private val eraseList = mutableListOf<String>()
    private val faveEquipmentList = mutableListOf<String>()
    private val adapterEquipmentList = mutableListOf<String>()
    private val equipmentAttributeMap = mutableMapOf<String,String>()
    private val equipmentAttributedList= mutableMapOf<String,MutableList<String>>()
    private val filterMode = mutableListOf<String>()
    private val filterList = mutableListOf<String>()

    private var menuClose = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EquipmentAllListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("favequipmentlist", Context.MODE_PRIVATE)
        faveEquipmentList.addAll(favSharedPreferences.getStringSet("favequipmentlist", mutableSetOf())?.toList()!!)

        equipmentWeapons=resources.getStringArray(R.array.equipment_weapons).toList()
        equipmentArmors=resources.getStringArray(R.array.equipment_armors).toList()
        equipmentAdvGear=resources.getStringArray(R.array.equipment_advgear).toList()
        equipmentList=resources.getStringArray(R.array.equipmentlist).toList()
        bigEquipmentList=resources.getStringArray(R.array.bigequipmentnames).toList()

        equipmentList.filter {it!="empty"}.forEach{
            @SuppressLint("DiscouragedApi")
            equipmentAttributeMap[it]=resources.getStringArray(resources.getIdentifier(it,"array",packageName))[1]
        }
        binding.BackButton.setOnClickListener { returntomain() }

        currentEquipmentList= buildList { add("empty");addAll(equipmentList.sorted());add("empty") }
        adapterEquipmentList.addAll(currentEquipmentList)
        equipmentAdapter = EquipmentAdapter(this,adapterEquipmentList,bigEquipmentList,faveEquipmentList)
        binding.reclview.adapter = equipmentAdapter
        searchedList=currentEquipmentList

        binding.searchview.isIconifiedByDefault=false
        binding.searchview.queryHint="Search..."
        binding.searchview.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                if(enttext.isNullOrBlank()){
                    searchedList=currentEquipmentList.filter{it !in eraseList}
                }
                else {
                    if(equipmentList.none {it.replace("_"," ").contains(enttext.trim(),true)}){
                        searchedList= listOf("empty")
                        Toast.makeText(this@AllActivity,"No such Equipment found",Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        searchedList = currentEquipmentList.filter{ (it.replace("_"," ").contains(enttext.trim(),true) && it !in eraseList) || it=="empty"}
                    }
                }
                equipmentAdapter.setEquipmentList(searchedList)
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                if(enttext.isNullOrBlank()){
                    searchedList=currentEquipmentList.filter{it !in eraseList}
                }
                else {
                    if(equipmentList.none { it.replace("_"," ").contains(enttext.trim(),true) }){
                        searchedList= listOf("empty")
                        Toast.makeText(this@AllActivity,"No such Equipment found",Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        searchedList = currentEquipmentList.filter{ (it.replace("_"," ").contains(enttext.trim(),true) && it !in eraseList) || it=="empty"}
                    }
                }
                equipmentAdapter.setEquipmentList(searchedList)
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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        with(equipmentMenu.findItem(R.id.eqmenu_ok)){
            if(!isVisible)isVisible=true
        }
        if(!item.title.isNullOrBlank()){
            with(item.title){
                when{
                    this!! == getText(R.string.sortABCdown) ->{
                        currentEquipmentList=currentEquipmentList.reversed()
                        equipmentAdapter.setEquipmentList(currentEquipmentList.filter{it !in eraseList && it in searchedList})
                        item.title=getText(R.string.sortABCup)
                        returntotop("sharp")}
                    equals(getText(R.string.sortABCup))->{
                        currentEquipmentList=currentEquipmentList.reversed()
                        equipmentAdapter.setEquipmentList(currentEquipmentList.filter{it !in eraseList && it in searchedList})
                        item.title = getText(R.string.sortABCdown)
                        returntotop("sharp")}

                    contains("weapons",true) ->{
                        menuClose = true
                        if(!item.isChecked){
                            filterModeClear()
                            eraseList.addAll(equipmentList.filter { it !in equipmentWeapons })
                            equipmentMenu.setGroupVisible(R.id.eqmenu_weapons_group,true)
                            filterMode.add("weapons")
                        }
                        else{
                            equipmentMenu.setGroupVisible(R.id.eqmenu_weapons_group,false)
                            if(filterMode.contains("fav")){eraseList.removeAll(equipmentList.filter {it in faveEquipmentList})}
                            else{eraseList.removeAll(equipmentList)}
                            filterMode.remove("weapons")
                            with(equipmentMenu.findItem(R.id.eqmenu_martial)){
                                if (isChecked){
                                    isChecked = false
                                    filterList.remove("martial")
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_simple)){
                                if (isChecked){
                                    isChecked = false
                                    filterList.remove("simple")
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_melee)){
                                if (isChecked){
                                    isChecked = false
                                    filterList.remove("melee")
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_ranged)){
                                if (isChecked){
                                    isChecked = false
                                    filterList.remove("ranged")
                                }
                            }
                        }
                        equipmentAdapter.setEquipmentList(currentEquipmentList.filter { it !in eraseList && it in searchedList})
                        item.isChecked=!item.isChecked
                    }
                    contains("simple",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_martial)){
                                if (isChecked) {
                                    isChecked = false
                                    filterWeapons("show","martial")
                                }
                            }
                            filterWeapons("hide","simple")
                        }
                        else{
                            filterWeapons("show","simple")
                        }
                        item.isChecked=!item.isChecked

                    }
                    contains("martial",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_simple)){
                                if (isChecked) {
                                    isChecked = false
                                    filterWeapons("show","simple")
                                }
                            }
                            filterWeapons("hide","martial")
                        }
                        else{
                            filterWeapons("show","martial")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("melee",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_ranged)){
                                if (isChecked) {
                                    isChecked = false
                                    filterWeapons("show","ranged")
                                }
                            }
                            filterWeapons("hide","melee")
                        }
                        else{
                            filterWeapons("show","melee")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("ranged",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_melee)){
                                if (isChecked) {
                                    isChecked = false
                                    filterWeapons("show","melee")
                                }
                            }
                            filterWeapons("hide","ranged")
                        }
                        else{
                            filterWeapons("show","ranged")
                        }
                        item.isChecked=!item.isChecked
                    }

                    contains(getString(R.string.equipment_menu_armors_and_shields))->{
                        menuClose = true
                        if(!item.isChecked){
                            filterModeClear()
                            eraseList.addAll(equipmentList.filter { it !in equipmentArmors })
                            equipmentMenu.setGroupVisible(R.id.eqmenu_armors_group,true)
                            filterMode.add("armors")
                        }
                        else{
                            equipmentMenu.setGroupVisible(R.id.eqmenu_armors_group,false)
                            if(filterMode.contains("fav")){eraseList.removeAll(equipmentList.filter {it in faveEquipmentList})}
                            else{eraseList.removeAll(equipmentList)}
                            filterMode.remove("armors")
                            filterList.clear()
                            with(equipmentMenu.findItem(R.id.eqmenu_light)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_medium)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_heavy)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_armors)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_shields)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                        }
                        equipmentAdapter.setEquipmentList(currentEquipmentList.filter { it !in eraseList && it in searchedList})
                        item.isChecked=!item.isChecked
                    }
                    contains("light",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_medium)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmors("show","medium")
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_heavy)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmors("show","heavy")
                                }
                            }
                            filterArmors("hide","light")
                        }
                        else{
                            filterArmors("show","light")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("medium",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_light)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmors("show","light")
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_heavy)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmors("show","heavy")
                                }
                            }
                            filterArmors("hide","medium")
                        }
                        else{
                            filterArmors("show","medium")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("heavy",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_light)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmors("show","light")
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_medium)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmors("show","medium")
                                }
                            }
                            filterArmors("hide","heavy")
                        }
                        else{
                            filterArmors("show","heavy")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("armors",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_shields)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmors("show","shield")
                                }
                            }
                            filterArmors("hide","armor")
                        }
                        else{
                            filterArmors("show","armor")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("shields",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_armors)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmors("show","armor")
                                }
                            }
                            filterArmors("hide","shield")
                        }
                        else{
                            filterArmors("show","shield")
                        }
                        item.isChecked=!item.isChecked
                    }

                    contains("Adventuring Gear",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            filterModeClear()
                            eraseList.addAll(equipmentList.filter { it !in equipmentAdvGear })
                            equipmentMenu.setGroupVisible(R.id.eqmenu_advgear_group,true)
                            filterMode.add("advgear")
                        }
                        else{
                            equipmentMenu.setGroupVisible(R.id.eqmenu_advgear_group,false)
                            if(filterMode.contains("fav")){eraseList.removeAll(equipmentList.filter {it in faveEquipmentList})}
                            else{eraseList.removeAll(equipmentList)}
                            filterMode.remove("advgear")
                            filterList.clear()
                            with(equipmentMenu.findItem(R.id.eqmenu_ammunition)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_communications)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_data)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_life_support)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_medical_supplies)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_storage)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_utilities)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_accessories)){
                                if (isChecked){
                                    isChecked=false
                                }
                            }

                        }
                        equipmentAdapter.setEquipmentList(currentEquipmentList.filter { it !in eraseList && it in searchedList})
                        item.isChecked=!item.isChecked
                    }
                    contains("Ammunition",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            if (filterList.isNotEmpty())filterAdvGear("show",filterList.first());uncheckAdvGear()
                            filterAdvGear("hide","ammunition")
                        }
                        else{
                            filterAdvGear("show","ammunition")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("Communications",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            if (filterList.isNotEmpty())filterAdvGear("show",filterList.first());uncheckAdvGear()
                            filterAdvGear("hide","communications")
                        }
                        else{
                            filterAdvGear("show","communications")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("Data",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            if (filterList.isNotEmpty())filterAdvGear("show",filterList.first());uncheckAdvGear()
                            filterAdvGear("hide","data")
                        }
                        else{
                            filterAdvGear("show","data")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("Life",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            if (filterList.isNotEmpty())filterAdvGear("show",filterList.first());uncheckAdvGear()
                            filterAdvGear("hide","life_support")
                        }
                        else{
                            filterAdvGear("show","life_support")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("Medical",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            if (filterList.isNotEmpty())filterAdvGear("show",filterList.first());uncheckAdvGear()
                            filterAdvGear("hide","medical_supplies")
                        }
                        else{
                            filterAdvGear("show","medical_supplies")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("Storage",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            if (filterList.isNotEmpty())filterAdvGear("show",filterList.first());uncheckAdvGear()
                            filterAdvGear("hide","storage")
                        }
                        else{
                            filterAdvGear("show","storage")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("Utilities",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            if (filterList.isNotEmpty())filterAdvGear("show",filterList.first());uncheckAdvGear()
                            filterAdvGear("hide","utilities")
                        }
                        else{
                            filterAdvGear("show","utilities")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("Accessories",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            if (filterList.isNotEmpty())filterAdvGear("show",filterList.first());uncheckAdvGear()
                            filterAdvGear("hide","accessories")
                        }
                        else{
                            filterAdvGear("show","accessories")
                        }
                        item.isChecked=!item.isChecked
                    }

                    contains("Confirm Filters",true)->{
                        menuClose = false
                    }
                    contains("favorites",true)->{
                        menuClose = true
                        if (!item.isChecked){
                            eraseList.addAll(equipmentList.filter {it !in faveEquipmentList })
                            filterMode.add("fav")
                        }
                        else{
                            with(filterMode){
                                when{
                                    contains("weapons")->{
                                        eraseList.removeAll{it in equipmentWeapons}
                                        filterList.forEach{ fil ->
                                            val tempList = equipmentAttributedList.getOrDefault(fil,emptyList())
                                            eraseList.addAll(equipmentList.filter { it !in tempList })
                                        }
                                    }
                                    contains("armors")->{
                                        eraseList.removeAll{it in equipmentArmors}
                                        filterList.forEach{ fil ->
                                            val tempList = equipmentAttributedList.getOrDefault(fil,emptyList())
                                            eraseList.addAll(equipmentList.filter { it !in tempList })
                                        }
                                    }
                                    contains("advgear")->{
                                        eraseList.removeAll { it in equipmentAdvGear }
                                        val tempList = equipmentAttributedList.getOrDefault(filterList.first(),emptyList())
                                        eraseList.addAll(equipmentList.filter { it !in tempList })
                                    }
                                    else->eraseList.clear()
                                }
                                remove("fav")
                            }
                        }
                        equipmentAdapter.setEquipmentList(currentEquipmentList.filter{it !in eraseList && it in searchedList})
                        item.isChecked= !item.isChecked
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }   
    private fun filterWeapons(action: String, mode: String){
        if (equipmentAttributedList.getOrPut("simple"){ mutableListOf() }.isEmpty()){
            equipmentAttributedList.getOrPut("simple"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("simple",true)}.keys)
            equipmentAttributedList.getOrPut("martial"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("martial",true)}.keys)
            equipmentAttributedList.getOrPut("ranged"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("blaster",true)}.keys)
            equipmentAttributedList.getOrPut("melee"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("weapon",true)}.keys)
        }
        val tempList1 = equipmentAttributedList[mode]!!.toList()
        if(action=="hide"){
            eraseList.addAll(equipmentList.filter {it !in tempList1})
            filterList.add(mode)
        }
        else{
            filterList.remove(mode)
            eraseList.removeAll(equipmentWeapons)
            if (filterMode.contains("fav"))eraseList.addAll(equipmentList.filter { it !in faveEquipmentList })
            filterList.forEach { fil ->
                val tempList = equipmentAttributedList.getOrDefault(fil,emptyList())
                eraseList.addAll(equipmentList.filter { it !in tempList })
            }
        }
        equipmentAdapter.setEquipmentList(currentEquipmentList.filter { it !in eraseList && it in searchedList})
    }
    private fun filterArmors(action: String,mode: String){
        if (equipmentAttributedList.getOrPut("light"){ mutableListOf() }.isEmpty()){

            equipmentAttributedList.getOrPut("light"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("light",true)}.keys)
            equipmentAttributedList.getOrPut("medium"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("medium",true)}.keys)
            equipmentAttributedList.getOrPut("heavy"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("heavy",true)}.keys)
            equipmentAttributedList.getOrPut("armor"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("armor",true)}.keys)
            equipmentAttributedList.getOrPut("shield"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("shield",true)}.keys)
        }
        val tempList1 = equipmentAttributedList[mode]!!.toList()
        if (action == "hide"){
            eraseList.addAll(equipmentList.filter {it !in tempList1})
            filterList.add(mode)
        }
        else{
            filterList.remove(mode)
            eraseList.removeAll(equipmentArmors)
            if (filterMode.contains("fav"))eraseList.addAll(equipmentList.filter { it !in faveEquipmentList })
            filterList.forEach { fil ->
                val tempList = equipmentAttributedList.getOrDefault(fil,emptyList())
                eraseList.addAll(equipmentList.filter { it !in tempList })
            }
        }
        equipmentAdapter.setEquipmentList(currentEquipmentList.filter{it !in eraseList && it in searchedList})
    }
    private fun filterAdvGear(action: String,mode: String){
        if (equipmentAttributedList.getOrPut("ammunition"){ mutableListOf() }.isEmpty()){

            equipmentAttributedList.getOrPut("ammunition"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("Ammunition",true)}.keys)
            equipmentAttributedList.getOrPut("communications"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("Communications",true)}.keys)
            equipmentAttributedList.getOrPut("data"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("Data",true)}.keys)
            equipmentAttributedList.getOrPut("life_support"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("Life",true)}.keys)
            equipmentAttributedList.getOrPut("medical_supplies"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("Medical",true)}.keys)
            equipmentAttributedList.getOrPut("storage"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("Storage")}.keys)
            equipmentAttributedList.getOrPut("utilities"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("Utility",true)}.keys)
            equipmentAttributedList.getOrPut("accessories"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("accessory",true)}.keys)
        }
        val tempList1 = equipmentAttributedList[mode]!!.toList()
        if (action == "hide"){
            eraseList.addAll(equipmentList.filter {it !in tempList1})
            filterList.add(mode)
        }
        else{
            filterList.clear()
            eraseList.removeAll(equipmentAdvGear)
            if (filterMode.contains("fav"))eraseList.addAll(equipmentList.filter { it !in faveEquipmentList })
        }
        equipmentAdapter.setEquipmentList(currentEquipmentList.filter{it !in eraseList && it in searchedList})
    }

    private fun uncheckAdvGear(){
        with(equipmentMenu.findItem(R.id.eqmenu_ammunition)){
            if (isChecked){
                isChecked=false
            }
        }
        with(equipmentMenu.findItem(R.id.eqmenu_communications)){
            if (isChecked){
                isChecked=false
            }
        }
        with(equipmentMenu.findItem(R.id.eqmenu_data)){
            if (isChecked){
                isChecked=false
            }
        }
        with(equipmentMenu.findItem(R.id.eqmenu_life_support)){
            if (isChecked){
                isChecked=false
            }
        }
        with(equipmentMenu.findItem(R.id.eqmenu_medical_supplies)){
            if (isChecked){
                isChecked=false
            }
        }
        with(equipmentMenu.findItem(R.id.eqmenu_storage)){
            if (isChecked){
                isChecked=false
            }
        }
        with(equipmentMenu.findItem(R.id.eqmenu_utilities)){
            if (isChecked){
                isChecked=false
            }
        }
        with(equipmentMenu.findItem(R.id.eqmenu_accessories)){
            if (isChecked){
                isChecked=false
            }
        }
    }
    private fun filterModeClear(){
        if (filterMode.contains("armors")){
            equipmentMenu.findItem(R.id.eqmenu_armors_and_shields).isChecked=false
            equipmentMenu.setGroupVisible(R.id.eqmenu_armors_group,false)
            if(filterMode.contains("fav")){eraseList.removeAll(equipmentList.filter {it in faveEquipmentList})}
            else{eraseList.removeAll(equipmentList)}
            filterMode.remove("armors")
            filterList.clear()
            with(equipmentMenu.findItem(R.id.eqmenu_light)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_medium)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_heavy)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_armors)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_shields)){
                if (isChecked){
                    isChecked=false
                }
            }
        }
        if(filterMode.contains("weapons")){
            equipmentMenu.findItem(R.id.eqmenu_weapons).isChecked=false
            equipmentMenu.setGroupVisible(R.id.eqmenu_weapons_group,false)
            if(filterMode.contains("fav")){eraseList.removeAll(equipmentList.filter {it in faveEquipmentList})}
            else{eraseList.removeAll(equipmentList)}
            filterMode.remove("weapons")
            with(equipmentMenu.findItem(R.id.eqmenu_martial)){
                if (isChecked){
                    isChecked = false
                    filterList.remove("martial")
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_simple)){
                if (isChecked){
                    isChecked = false
                    filterList.remove("simple")
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_melee)){
                if (isChecked){
                    isChecked = false
                    filterList.remove("melee")
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_ranged)){
                if (isChecked){
                    isChecked = false
                    filterList.remove("ranged")
                }
            }
        }
        if (filterMode.contains("advgear")){
            equipmentMenu.findItem(R.id.eqmenu_advgear).isChecked=false
            equipmentMenu.setGroupVisible(R.id.eqmenu_advgear_group,false)
            if(filterMode.contains("fav")){eraseList.removeAll(equipmentList.filter {it in faveEquipmentList})}
            else{eraseList.removeAll(equipmentList)}
            filterMode.remove("advgear")
            filterList.clear()
            with(equipmentMenu.findItem(R.id.eqmenu_ammunition)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_communications)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_data)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_life_support)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_medical_supplies)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_storage)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_utilities)){
                if (isChecked){
                    isChecked=false
                }
            }
            with(equipmentMenu.findItem(R.id.eqmenu_accessories)){
                if (isChecked){
                    isChecked=false
                }
            }

        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuClose=false
        return super.onPrepareOptionsMenu(menu)
    }
    private fun returntotop(mode: String){
        when(mode){
            "smooth"->binding.reclview.smoothScrollToPosition(0)
            "sharp"->binding.reclview.scrollToPosition(0)
        }
    }
    private fun returntomain() {
        startActivity(Intent(this, EquipmentActivity::class.java))
        with(favSharedPreferences.edit()){
            putStringSet("favequipmentlist",faveEquipmentList.toMutableSet())
            apply()
        }
        finish()
    }
    override fun onPanelClosed(featureId: Int, menu: Menu) {
        if (menuClose) {
            openOptionsMenu()
        }
        else{
            equipmentMenu.findItem(R.id.eqmenu_ok).isVisible=false
        }
        super.onPanelClosed(featureId, menu)
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}