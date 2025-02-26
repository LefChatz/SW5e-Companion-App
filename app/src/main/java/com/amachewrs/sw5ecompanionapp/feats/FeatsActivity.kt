package com.amachewrs.sw5ecompanionapp.feats

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.SW5ECompanionApp
import com.amachewrs.sw5ecompanionapp.databinding.FeatsBinding


class FeatsActivity : AppCompatActivity() {
    private lateinit var binding: FeatsBinding
    private lateinit var reclview: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    private lateinit var featadapter: FeatsAdapter
    private var featList=mutableListOf<Feat>()
    private var adapterFeatList=mutableListOf<Feat>()
    private var currentfeatlist=listOf<Feat>()

    private val favFeatList: MutableList<String> = mutableListOf()
    private lateinit var favSharedPreferences: SharedPreferences
    private var trimEnteredText=""
    private var filters= mutableSetOf<String>()
    private var filterASI= mutableSetOf<String>()
    private var filterPreq= mutableSetOf<String>()
    private var filterlvl = 20
    private var filterType= mutableSetOf<String>()
    private var filterSize= mutableSetOf<String>()
    private var filterForceLvl= 0
    private var filterTechLvl= 0
    private var filterForceItems = setOf<MenuItem>()
    private var filterTechItems = setOf<MenuItem>()
    private lateinit var featmenu: Menu
    private lateinit var infotext: TextView
    private var atInfo = false
    private var keepmenu = false
    private val preqCats = setOf(11,14,18,22,28,34)
    private var menuSnapshot: MutableMap<MenuItem,Boolean> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FeatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("feats", Context.MODE_PRIVATE)
        favFeatList.addAll(favSharedPreferences.getStringSet("favorite_feats", mutableSetOf())?.toList()!!)

        featList.addAll(getFeats())

        infotext=layoutInflater.inflate(R.layout.universal_textview_nofont_gold,binding.coord,false).findViewById(R.id.textview)
        infotext.text=getText(R.string.feats_info)

        reclview = binding.reclview
        adapterFeatList.addAll(featList.sortFeatByName())
        featadapter = FeatsAdapter(this,adapterFeatList,favFeatList)
        reclview.adapter = featadapter
        currentfeatlist = featList.sortFeatByName()

        binding.BackButton.setOnClickListener{returntomain()}

        searchView = binding.searchview
        searchView.setIconifiedByDefault(false)
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop("sharp")
                if(enttext.isNullOrBlank()){
                    trimEnteredText=""
                    featadapter.setFeatList(currentfeatlist.filter{filter(it)})
                    object : CountDownTimer(1000, 1001) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(trimEnteredText.isBlank()){searchView.clearFocus()}
                        }
                    }.start()
                }
                else {
                    trimEnteredText= enttext.trim().replace(" ","_").replace("'","..").replace("-",".")
                    if(featList.getNameList().none { it.contains(trimEnteredText,true)}){
                        featadapter.setFeatList(listOf(Feat("NoSuchFeat")))
                    }
                    else {
                        featadapter.setFeatList(currentfeatlist.filter { filter(it) })
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                returntotop("sharp")
                if(enttext.isNullOrBlank()){
                    trimEnteredText=""
                    featadapter.setFeatList(currentfeatlist.filter{filter(it)})
                    object : CountDownTimer(1000, 999) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(trimEnteredText.isBlank()){searchView.clearFocus()}
                        }
                    }.start()
                }
                else {
                    trimEnteredText= enttext.trim().replace(" ","_").replace("'","..").replace("-",".")
                    if(featList.getNameList().none { it.contains(trimEnteredText,true)}){
                        featadapter.setFeatList(listOf(Feat("NoSuchFeat")))
                    }
                    else {
                        featadapter.setFeatList(currentfeatlist.filter { filter(it) })
                    }
                }
                return false
            }
        })


        binding.floatingActionButton.setOnClickListener{returntotop("smooth")}

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_feats,menu)
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.downarrowgold)
        if (menu != null){
            featmenu = menu
        }
        filterForceItems= setOf(featmenu.findItem(R.id.feats_menu_preq_forcecasting_1),featmenu.findItem(R.id.feats_menu_preq_forcecasting_2),featmenu.findItem(R.id.feats_menu_preq_forcecasting_3),featmenu.findItem(R.id.feats_menu_preq_forcecasting_4),featmenu.findItem(R.id.feats_menu_preq_forcecasting_5))
        filterTechItems= setOf(featmenu.findItem(R.id.feats_menu_preq_techcasting_1),featmenu.findItem(R.id.feats_menu_preq_techcasting_2),featmenu.findItem(R.id.feats_menu_preq_techcasting_3),featmenu.findItem(R.id.feats_menu_preq_techcasting_4),featmenu.findItem(R.id.feats_menu_preq_techcasting_5))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        with(featmenu.findItem(R.id.eqmenu_ok)){
            if(!isVisible)isVisible=true
        }
        keepmenu=true
        when(item.title){
            getText(R.string.sortABCdown)->{
                keepmenu=false
                currentfeatlist=featList.sortFeatByNameDescending()
                item.title=getText(R.string.sortABCup)
                returntotop("sharp")}
            getText(R.string.sortABCup)->{
                keepmenu=false
                currentfeatlist=featList.sortFeatByName()
                item.title = getText(R.string.sortABCdown)
                returntotop("sharp")}
            getText(R.string.feats_info_label)->{
                handleInfoSwitch()
                keepmenu = false }
            getText(R.string.ability_score)->{
                if (!item.isChecked){
                    featmenu.setGroupVisible(R.id.feats_menu_asi_group,true)
                    filterASI.addAll(listOf("Str","Dex","Con","Int","Wis","Cha"))
                    filters.add("ASI")
                    featmenu.forEach { if (it.order==5) it.isChecked=true}
                }
                else {
                    filterASI.clear()
                    filters.remove("ASI")
                    featmenu.setGroupVisible(R.id.feats_menu_asi_group,false)
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_str)-> changeFilter("Str",item, filterASI)
            getText(R.string.feats_menu_dex)-> changeFilter("Dex",item, filterASI)
            getText(R.string.feats_menu_con)-> changeFilter("Con",item, filterASI)
            getText(R.string.feats_menu_int)-> changeFilter("Int",item, filterASI)
            getText(R.string.feats_menu_wis)-> changeFilter("Wis",item, filterASI)
            getText(R.string.feats_menu_cha)-> changeFilter("Cha",item, filterASI)
            getText(R.string.favorites_gold)-> changeFilter("Fav",item, filters)
            getText(R.string.feats_menu_preq)->{
                if (!item.isChecked){
                    preqCats.forEach{ featmenu[it].isVisible=true }
                    filterPreq.clear()
                    filters.add("preq")
                }
                else{
                    preqCats.forEach{with(featmenu[it]){
                            isVisible=false
                            isChecked=false
                    }}
                    featmenu.setGroupVisible(R.id.feats_menu_preq_level_group,false)
                    featmenu.setGroupVisible(R.id.feats_menu_preq_type_group,false)
                    featmenu.setGroupVisible(R.id.feats_menu_preq_size_group,false)
                    featmenu.setGroupVisible(R.id.feats_menu_preq_forcecasting_group,false)
                    featmenu.setGroupVisible(R.id.feats_menu_preq_techcasting_group,false)
                    filters.remove("preq")
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_level)->{
                if (!item.isChecked){

                    filterPreq.add("lvl")
                    filterlvl=20
                    featmenu.findItem(R.id.feats_menu_preq_level_1).isChecked=false
                    featmenu.findItem(R.id.feats_menu_preq_level_2).isChecked=false
                }
                else{
                    filterPreq.remove("lvl")
                }
                featmenu.setGroupVisible(R.id.feats_menu_preq_level_group,!item.isChecked)
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_level_1)->{
                if (!item.isChecked){
                    filterlvl=4
                    featmenu.findItem(R.id.feats_menu_preq_level_2).isChecked=false
                }
                else{
                    filterlvl=20
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_level_2)->{
                if (!item.isChecked){
                    filterlvl=12
                    featmenu.findItem(R.id.feats_menu_preq_level_1).isChecked=false
                }
                else{
                    filterlvl=20
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_type)->{
                if (!item.isChecked){
                    filterPreq.add("Type")
                    filterType.clear()
                    filterType.addAll(setOf("humanoid","beast","droid"))
                    featmenu.findItem(R.id.feats_menu_preq_type_1).isChecked=true
                    featmenu.findItem(R.id.feats_menu_preq_type_2).isChecked=true
                    featmenu.findItem(R.id.feats_menu_preq_type_3).isChecked=true
                }
                else{
                    filterPreq.remove("Type")
                }
                featmenu.setGroupVisible(R.id.feats_menu_preq_type_group,!item.isChecked)
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_type_1)-> changeFilter("humanoid",item,filterType)
            getText(R.string.feats_menu_preq_type_2)-> changeFilter("droid",item,filterType)
            getText(R.string.feats_menu_preq_type_3)-> changeFilter("beast",item,filterType)
            getText(R.string.feats_menu_preq_size)->{
                if (!item.isChecked){
                    filterPreq.add("Size")
                    filterSize.clear()
                    filterSize.addAll(setOf("tiny","small","medium"))
                    featmenu.findItem(R.id.feats_menu_preq_size_1).isChecked=true
                    featmenu.findItem(R.id.feats_menu_preq_size_2).isChecked=true
                    featmenu.findItem(R.id.feats_menu_preq_size_3).isChecked=true
                }
                else{
                    filterPreq.remove("Size")
                }
                featmenu.setGroupVisible(R.id.feats_menu_preq_size_group,!item.isChecked)
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_size_1)->changeFilter("tiny",item,filterSize)
            getText(R.string.feats_menu_preq_size_2)->changeFilter("small",item,filterSize)
            getText(R.string.feats_menu_preq_size_3)->changeFilter("medium",item,filterSize)
            getText(R.string.feats_menu_preq_forcecasting)->{
                if (!item.isChecked){
                    filterPreq.add("Force")
                    filterForceLvl=4
                    filterForceItems.forEach { it.isChecked = it.itemId == R.id.feats_menu_preq_forcecasting_5 }
                }
                else{
                    filterPreq.remove("Force")
                }
                featmenu.setGroupVisible(R.id.feats_menu_preq_forcecasting_group,!item.isChecked)
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_forcecasting_1)->{
                if (!item.isChecked){
                    filterForceLvl=-1
                    filterForceItems.forEach { if (it.itemId!=R.id.feats_menu_preq_forcecasting_1) it.isChecked=false }
                }
                else{
                    filterForceLvl=4
                    featmenu.findItem(R.id.feats_menu_preq_forcecasting_5).isChecked=true
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_forcecasting_2)->{
                if (!item.isChecked){
                    filterForceLvl=0
                    filterForceItems.forEach { if (it.itemId!=R.id.feats_menu_preq_forcecasting_2) it.isChecked=false }
                }
                else{
                    filterForceLvl=-1
                    featmenu.findItem(R.id.feats_menu_preq_forcecasting_1).isChecked=true
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_forcecasting_3)->{
                if (!item.isChecked){
                    filterForceLvl=2
                    filterForceItems.forEach { if (it.itemId!=R.id.feats_menu_preq_forcecasting_3) it.isChecked=false }
                }
                else{
                    filterForceLvl=-1
                    featmenu.findItem(R.id.feats_menu_preq_forcecasting_1).isChecked=true
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_forcecasting_4)->{
                if (!item.isChecked){
                    filterForceLvl=3
                    filterForceItems.forEach { if (it.itemId!=R.id.feats_menu_preq_forcecasting_4) it.isChecked=false }
                }
                else{
                    filterForceLvl=-1
                    featmenu.findItem(R.id.feats_menu_preq_forcecasting_1).isChecked=true
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_forcecasting_5)->{
                if (!item.isChecked){
                    filterForceLvl=4
                    filterForceItems.forEach { if (it.itemId!=R.id.feats_menu_preq_forcecasting_5) it.isChecked=false }
                }
                else{
                    filterForceLvl=-1
                    featmenu.findItem(R.id.feats_menu_preq_forcecasting_1).isChecked=true
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_techcasting)->{
                if (!item.isChecked){
                    filterPreq.add("Tech")
                    filterTechLvl=4
                    filterTechItems.forEach { it.isChecked = it.itemId == R.id.feats_menu_preq_techcasting_5 }
                }
                else{
                    filterPreq.remove("Tech")
                }
                featmenu.setGroupVisible(R.id.feats_menu_preq_techcasting_group,!item.isChecked)
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_techcasting_1)->{
                if (!item.isChecked){
                    filterTechLvl=-1
                    filterTechItems.forEach { if (it.itemId!=R.id.feats_menu_preq_techcasting_1) it.isChecked=false }
                }
                else{
                    filterTechLvl=4
                    featmenu.findItem(R.id.feats_menu_preq_techcasting_5).isChecked=true
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_techcasting_2)->{
                if (!item.isChecked){
                    filterTechLvl=0
                    filterTechItems.forEach { if (it.itemId!=R.id.feats_menu_preq_techcasting_2) it.isChecked=false }
                }
                else{
                    filterTechLvl=-1
                    featmenu.findItem(R.id.feats_menu_preq_techcasting_1).isChecked=true
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_techcasting_3)->{
                if (!item.isChecked){
                    filterTechLvl=2
                    filterTechItems.forEach { if (it.itemId!=R.id.feats_menu_preq_techcasting_3) it.isChecked=false }
                }
                else{
                    filterTechLvl=-1
                    featmenu.findItem(R.id.feats_menu_preq_techcasting_1).isChecked=true
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_techcasting_4)->{
                if (!item.isChecked){
                    filterTechLvl=3
                    filterTechItems.forEach { if (it.itemId!=R.id.feats_menu_preq_techcasting_4) it.isChecked=false }
                }
                else{
                    filterTechLvl=-1
                    featmenu.findItem(R.id.feats_menu_preq_techcasting_1).isChecked=true
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_techcasting_5)->{
                if (!item.isChecked){
                    filterTechLvl=4
                    filterTechItems.forEach { if (it.itemId!=R.id.feats_menu_preq_techcasting_5) it.isChecked=false }
                }
                else{
                    filterTechLvl=-1
                    featmenu.findItem(R.id.feats_menu_preq_techcasting_1).isChecked=true
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.equipment_menu_ok)->{
                keepmenu=false
                item.isVisible=false
            }
        }
        featadapter.setFeatList(currentfeatlist.filter {filter(it)})

        //keep menu from closing
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

    private fun handleInfoSwitch(){
        if(!atInfo){
            binding.coord.removeView(binding.reclview)
            binding.searchview.visibility = View.GONE
            binding.floatingActionButton.visibility = View.GONE
            binding.coord.addView(infotext)
            featmenu.forEach {
                menuSnapshot[it] = it.isVisible
                if(it.order!=2)it.isVisible=false
            }
        }
        else {
            binding.coord.removeView(infotext)
            binding.coord.addView(binding.reclview)
            binding.searchview.visibility = View.VISIBLE
            binding.floatingActionButton.visibility = View.VISIBLE
            featmenu.forEach { it.isVisible = menuSnapshot[it]!! }
            returntotop("sharp")
        }
        atInfo=!atInfo
    }
    private fun filter(feat: Feat): Boolean{
        if (!feat.featname.contains(trimEnteredText)) return false
        if (filters.contains("ASI")){
            if (filterASI.isNotEmpty()) {
                if (filterASI.none { feat.asi.contains(it) or feat.asi.contains("Any") }) return false
            }
            else{
                if (feat.asi != "-") return false
            }
        }
        if (filters.contains("preq")){
            if (filterPreq.contains("lvl")){
                if (feat.prerequisite.contains("level") && (filterlvl==4 || (feat.prerequisite.substringBefore("th level").last() == '2' && 12>=filterlvl))) return false
            }
            if(filterPreq.contains("Type")){
                if (feat.prerequisite.contains("Type") && filterType.none { feat.prerequisite.contains(it) }) return false
            }
            if(filterPreq.contains("Force")){
                if (filterForceLvl>-1){
                    if (!feat.prerequisite.contains("force")) return false
                    if (feat.prerequisite.substringAfter("casting lvl ","0").first().toString().toInt()>filterForceLvl) return false
                }
                else {
                    if (feat.prerequisite.contains("force")) return false
                }
            }
            if(filterPreq.contains("Tech")){
                if (filterTechLvl>-1){
                    if (!feat.prerequisite.contains("tech")) return false
                    if (feat.prerequisite.substringAfter("casting lvl ","0").first().toString().toInt()>filterTechLvl) return false
                }
                else {
                    if (feat.prerequisite.contains("tech")) return false
                }
            }
        }
        if (filters.contains("Fav") && feat.featname !in favFeatList) return false
        return true
    }
    private fun changeFilter(filter: String, item: MenuItem, filterList: MutableSet<String>){
        if (!item.isChecked) filterList.add(filter)
        else filterList.remove(filter)
        item.isChecked= !item.isChecked
    }
    private fun returntotop(mode: String){
        when(mode){
            "smooth"->binding.reclview.smoothScrollToPosition(0)
            "sharp"->binding.reclview.scrollToPosition(0)
        }

    }
    fun returntomain() {
        with(favSharedPreferences.edit()){
            putStringSet("favorite_feats",favFeatList.toMutableSet())
            apply()
        }
        finish()
    }
    private fun getFeats(): MutableList<Feat>{
        val getFeatList = mutableListOf<Feat>()
        val tempFeatList=resources.getTextArray(R.array.feats)
        for(i in 5..tempFeatList.size step 6){
            getFeatList.add(Feat(tempFeatList[i-5].toString(),tempFeatList[i-4].toString(),tempFeatList[i-3].toString(),tempFeatList[i-2].toString(),tempFeatList[i-1],tempFeatList[i].toString().toBoolean()))
        }
        return getFeatList
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}