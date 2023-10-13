package com.example.swtrial2.equipment
//
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.swtrial2.EquipmentActivity
import com.example.swtrial2.R
import com.example.swtrial2.databinding.ActivityEquipmentListBinding
import com.example.swtrial2.equipment.equipmentadapterstuff.EquipmentAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AllActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEquipmentListBinding
    private lateinit var reclview: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var searchView: SearchView
    private lateinit var equipmentAdapter: EquipmentAdapter
    private lateinit var favSharedPreferences: SharedPreferences
    private lateinit var equipmentMenu:Menu
    private var menuClose = false
    private var bigEquipmentList = listOf<String>()
    private var equipmentList = listOf<String>()
    private var equipmentWeapons = listOf<String>()
    private var equipmentArmours = listOf<String>()
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar=findViewById(R.id.force_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("favequipmentlist", Context.MODE_PRIVATE)

        equipmentWeapons=resources.getStringArray(R.array.equipment_weapons).toList()
        equipmentArmours=resources.getStringArray(R.array.equipment_armours).toList()
        equipmentAdvGear=resources.getStringArray(R.array.equipment_advgear).toList()
        equipmentList=resources.getStringArray(R.array.equipmentlist).toList()
        bigEquipmentList=resources.getStringArray(R.array.bigequipmentnames).toList()
        faveEquipmentList.addAll(favSharedPreferences.getStringSet("favequipmentlist", mutableSetOf())?.toList()!!)

        equipmentList.filter {it!="empty"}.forEach{
            equipmentAttributeMap[it]=resources.getStringArray(resources.getIdentifier(it,"array",packageName))[1]
        }

        reclview= findViewById(R.id.reclview)

        currentEquipmentList= buildList { add("empty");addAll(equipmentList.sorted());add("empty") }
        adapterEquipmentList.addAll(currentEquipmentList)
        equipmentAdapter = EquipmentAdapter(this,adapterEquipmentList,bigEquipmentList,faveEquipmentList)
        reclview.adapter = equipmentAdapter
        searchedList=currentEquipmentList

        searchView = findViewById(R.id.searchview)
        searchView.isIconifiedByDefault=false
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
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
            returntotop(reclview,"smooth")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_equipment,menu)
        if (menu != null) {
            equipmentMenu = menu
        }
        toolbar.overflowIcon = AppCompatResources.getDrawable(this,R.drawable.downarrowgold)
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
                        returntotop(reclview,"sharp")}
                    equals(getText(R.string.sortABCup))->{
                        currentEquipmentList=currentEquipmentList.reversed()
                        equipmentAdapter.setEquipmentList(currentEquipmentList.filter{it !in eraseList && it in searchedList})
                        item.title = getText(R.string.sortABCdown)
                        returntotop(reclview,"sharp")}

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

                    contains(getString(R.string.equipment_menu_armours_and_shields))->{
                        menuClose = true
                        if(!item.isChecked){
                            filterModeClear()
                            eraseList.addAll(equipmentList.filter { it !in equipmentArmours })
                            equipmentMenu.setGroupVisible(R.id.eqmenu_armours_group,true)
                            filterMode.add("armours")
                        }
                        else{
                            equipmentMenu.setGroupVisible(R.id.eqmenu_armours_group,false)
                            if(filterMode.contains("fav")){eraseList.removeAll(equipmentList.filter {it in faveEquipmentList})}
                            else{eraseList.removeAll(equipmentList)}
                            filterMode.remove("armours")
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
                            with(equipmentMenu.findItem(R.id.eqmenu_armours)){
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
                                    filterArmours("show","medium")
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_heavy)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmours("show","heavy")
                                }
                            }
                            filterArmours("hide","light")
                        }
                        else{
                            filterArmours("show","light")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("medium",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_light)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmours("show","light")
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_heavy)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmours("show","heavy")
                                }
                            }
                            filterArmours("hide","medium")
                        }
                        else{
                            filterArmours("show","medium")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("heavy",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_light)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmours("show","light")
                                }
                            }
                            with(equipmentMenu.findItem(R.id.eqmenu_medium)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmours("show","medium")
                                }
                            }
                            filterArmours("hide","heavy")
                        }
                        else{
                            filterArmours("show","heavy")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("armours",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_shields)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmours("show","shield")
                                }
                            }
                            filterArmours("hide","armour")
                        }
                        else{
                            filterArmours("show","armour")
                        }
                        item.isChecked=!item.isChecked
                    }
                    contains("shields",true)->{
                        menuClose = true
                        if(!item.isChecked){
                            with(equipmentMenu.findItem(R.id.eqmenu_armours)){
                                if (isChecked) {
                                    isChecked = false
                                    filterArmours("show","armour")
                                }
                            }
                            filterArmours("hide","shield")
                        }
                        else{
                            filterArmours("show","shield")
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
                                    contains("armours")->{
                                        eraseList.removeAll{it in equipmentArmours}
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
            /*Was beefy , if new code is slow will put back
            with(filterList){
                if (!filterMode.contains("fav")){
                    eraseList.removeAll(
                        when{
                            contains(difFilter1)->{
                                val tempList2 = equipmentAttributedList.getOrDefault(difFilter1, emptyList())
                                equipmentList.filter {it !in tempList1 && it in tempList2}
                            }
                            contains(difFilter2)->{
                                val templist2 = equipmentAttributedList.getOrDefault(difFilter2, emptyList())
                                equipmentList.filter {it !in tempList1 && it in templist2}
                            }
                            else->{
                                equipmentList.filter {it !in tempList1 && it in equipmentWeapons}
                            }
                        }
                    )
                }
                else{
                    eraseList.removeAll(
                        when{
                            contains(difFilter1)->{
                                val tempList2 = equipmentAttributedList.getOrDefault(difFilter1, emptyList())
                                equipmentList.filter {it !in tempList1 && it in tempList2 && it in faveEquipmentList}
                            }
                            contains(difFilter2)->{
                                val templist2 = equipmentAttributedList.getOrDefault(difFilter2, emptyList())
                                equipmentList.filter {it !in tempList1 && it in templist2 && it in faveEquipmentList}
                            }
                            else->{
                                equipmentList.filter {it !in tempList1 && it in faveEquipmentList && it in equipmentWeapons}
                            }
                        }
                    )
                }
                remove(mode)
            }*/
        }
        equipmentAdapter.setEquipmentList(currentEquipmentList.filter { it !in eraseList && it in searchedList})
    }
    private fun filterArmours(action: String,mode: String){
        if (equipmentAttributedList.getOrPut("light"){ mutableListOf() }.isEmpty()){

            equipmentAttributedList.getOrPut("light"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("light",true)}.keys)
            equipmentAttributedList.getOrPut("medium"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("medium",true)}.keys)
            equipmentAttributedList.getOrPut("heavy"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("heavy",true)}.keys)
            equipmentAttributedList.getOrPut("armour"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("armour",true)}.keys)
            equipmentAttributedList.getOrPut("shield"){ mutableListOf() }.addAll(equipmentAttributeMap.filterValues {it.contains("shield",true)}.keys)
        }
        val tempList1 = equipmentAttributedList[mode]!!.toList()
        if (action == "hide"){
            eraseList.addAll(equipmentList.filter {it !in tempList1})
            filterList.add(mode)
        }
        else{
            filterList.remove(mode)
            eraseList.removeAll(equipmentArmours)
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
        if (filterMode.contains("armours")){
            equipmentMenu.findItem(R.id.eqmenu_armours_and_shields).isChecked=false
            equipmentMenu.setGroupVisible(R.id.eqmenu_armours_group,false)
            if(filterMode.contains("fav")){eraseList.removeAll(equipmentList.filter {it in faveEquipmentList})}
            else{eraseList.removeAll(equipmentList)}
            filterMode.remove("armours")
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
            with(equipmentMenu.findItem(R.id.eqmenu_armours)){
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
    private fun returntotop(view: RecyclerView,mode: String){
        when(mode){
            "smooth"->view.smoothScrollToPosition(0)
            "sharp"->view.scrollToPosition(0)
        }
    }
    fun returntomain(view: View?) {
        with(favSharedPreferences.edit()){
            putStringSet("favequipmentlist",faveEquipmentList.toMutableSet())
            apply()
        }
        startActivity(Intent(this, EquipmentActivity::class.java))
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
    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        returntomain(null)
        return super.getOnBackInvokedDispatcher()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}