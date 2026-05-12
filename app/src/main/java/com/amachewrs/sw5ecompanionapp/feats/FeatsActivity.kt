package com.amachewrs.sw5ecompanionapp.feats

import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.FeatsBinding
import kotlin.math.max


class FeatsActivity : AppCompatActivity() {
    private lateinit var binding: FeatsBinding
    private lateinit var reclview: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    private lateinit var featadapter: FeatsAdapter
    private var featList=mutableListOf<Feat>()
    private var adapterFeatList=mutableListOf<Feat>()
    private var currentFeatList=listOf<Feat>()

    private val favFeatList: MutableList<String> = mutableListOf()
    private lateinit var favSharedPreferences: SharedPreferences
    private var trimEnteredText=""
    private var filters= mutableSetOf<String>()
    private var filterASI= mutableSetOf<String>()
    private var filterPrereq= mutableSetOf<String>()
    private var filterLevel = 20
    private var filterType= mutableSetOf<String>()
    private var filterSize= mutableSetOf<String>()
    private var filterForceLvl= 0
    private var filterTechLvl= 0
    private var filterForceItems = setOf<MenuItem>()
    private var filterTechItems = setOf<MenuItem>()
    private lateinit var featmenu: Menu
    private lateinit var infotext: TextView
    private var atInfo = false
    private var keepMenu = false
    private val prereqCats = setOf(11,14,18,22,28)
    private var menuSnapshot: MutableMap<MenuItem,Boolean> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FeatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            binding.bottomNavigationView.updatePadding(0,0,0,
                max(windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom,windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom)
            )
            WindowInsetsCompat.CONSUMED
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("feats", MODE_PRIVATE)
        favFeatList.addAll(favSharedPreferences.getStringSet("favorite_feats", mutableSetOf())?.toList()!!)

        featList.addAll(getFeats())

        infotext=layoutInflater.inflate(R.layout.universal_textview_nofont_gold,binding.coord,false).findViewById(R.id.textview)
        infotext.text=getText(R.string.feats_info)
        infotext.visibility = View.GONE
        infotext.updatePadding(0,10)
        binding.coord.addView(infotext)

        reclview = binding.reclview
        adapterFeatList.addAll(featList.sortFeatByName())
        featadapter = FeatsAdapter(this,adapterFeatList,favFeatList)
        reclview.adapter = featadapter
        currentFeatList = featList.sortFeatByName()

        binding.BackButton.setOnClickListener{returntomain()}

        searchView = binding.searchview
        searchView.setIconifiedByDefault(false)
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop("sharp")
                if(enttext.isNullOrBlank()){
                    trimEnteredText=""
                    featadapter.setFeatList(currentFeatList.filter{filter(it)})
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
                        featadapter.setFeatList(currentFeatList.filter { filter(it) })
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                returntotop("sharp")
                if(enttext.isNullOrBlank()){
                    trimEnteredText=""
                    featadapter.setFeatList(currentFeatList.filter{filter(it)})
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
                        featadapter.setFeatList(currentFeatList.filter { filter(it) })
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

        keepMenu=true
        when(item.title){
            getText(R.string.sortABCdown)->{
                keepMenu=false
                currentFeatList=featList.sortFeatByNameDescending()
                item.title=getText(R.string.sortABCup)
                returntotop("sharp")}
            getText(R.string.sortABCup)->{
                keepMenu=false
                currentFeatList=featList.sortFeatByName()
                item.title = getText(R.string.sortABCdown)
                returntotop("sharp")}
            getText(R.string.feats_info_label)->{
                handleInfoSwitch()
                keepMenu = false }
            getText(R.string.back_gold)->{
                handleInfoSwitch()
                keepMenu = false }
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
                    prereqCats.forEach{ featmenu[it].isVisible=true }
                    filterPrereq.clear()
                    filters.add("preq")
                }
                else{
                    prereqCats.forEach{with(featmenu[it]){
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

                    filterPrereq.add("lvl")
                    filterLevel=20
                    featmenu.findItem(R.id.feats_menu_preq_level_1).isChecked=false
                    featmenu.findItem(R.id.feats_menu_preq_level_2).isChecked=false
                }
                else{
                    filterPrereq.remove("lvl")
                }
                featmenu.setGroupVisible(R.id.feats_menu_preq_level_group,!item.isChecked)
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_level_1)->{
                if (!item.isChecked){
                    filterLevel=4
                    featmenu.findItem(R.id.feats_menu_preq_level_2).isChecked=false
                }
                else{
                    filterLevel=20
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_level_2)->{
                if (!item.isChecked){
                    filterLevel=12
                    featmenu.findItem(R.id.feats_menu_preq_level_1).isChecked=false
                }
                else{
                    filterLevel=20
                }
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_type)->{
                if (!item.isChecked){
                    filterPrereq.add("Type")
                    filterType.clear()
                    filterType.addAll(setOf("humanoid","beast","droid"))
                    featmenu.findItem(R.id.feats_menu_preq_type_1).isChecked=true
                    featmenu.findItem(R.id.feats_menu_preq_type_2).isChecked=true
                    featmenu.findItem(R.id.feats_menu_preq_type_3).isChecked=true
                }
                else{
                    filterPrereq.remove("Type")
                }
                featmenu.setGroupVisible(R.id.feats_menu_preq_type_group,!item.isChecked)
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_type_1)-> changeFilter("humanoid",item,filterType)
            getText(R.string.feats_menu_preq_type_2)-> changeFilter("droid",item,filterType)
            getText(R.string.feats_menu_preq_type_3)-> changeFilter("beast",item,filterType)
            getText(R.string.feats_menu_preq_size)->{
                if (!item.isChecked){
                    filterPrereq.add("Size")
                    filterSize.clear()
                    filterSize.addAll(setOf("tiny","small","medium"))
                    featmenu.findItem(R.id.feats_menu_preq_size_1).isChecked=true
                    featmenu.findItem(R.id.feats_menu_preq_size_2).isChecked=true
                    featmenu.findItem(R.id.feats_menu_preq_size_3).isChecked=true
                }
                else{
                    filterPrereq.remove("Size")
                }
                featmenu.setGroupVisible(R.id.feats_menu_preq_size_group,!item.isChecked)
                item.isChecked = !item.isChecked
            }
            getText(R.string.feats_menu_preq_size_1)->changeFilter("tiny",item,filterSize)
            getText(R.string.feats_menu_preq_size_2)->changeFilter("small",item,filterSize)
            getText(R.string.feats_menu_preq_size_3)->changeFilter("medium",item,filterSize)
            getText(R.string.feats_menu_preq_forcecasting)->{
                if (!item.isChecked){
                    filterPrereq.add("Force")
                    filterForceLvl=4
                    filterForceItems.forEach { it.isChecked = it.itemId == R.id.feats_menu_preq_forcecasting_5 }
                }
                else{
                    filterPrereq.remove("Force")
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
                    filterPrereq.add("Tech")
                    filterTechLvl=4
                    filterTechItems.forEach { it.isChecked = it.itemId == R.id.feats_menu_preq_techcasting_5 }
                }
                else{
                    filterPrereq.remove("Tech")
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
                keepMenu=false
                item.isVisible=false
            }
        }
        featadapter.setFeatList(currentFeatList.filter {filter(it)})

        //keep menu from closing
        if (keepMenu){
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
            with(featmenu.findItem(R.id.eqmenu_ok)){
                if(!isVisible)isVisible=true
            }
        }
        return false
    }

    private fun handleInfoSwitch(){
        if(!atInfo){
            binding.reclview.visibility = View.GONE
            binding.bottomNavigationView.visibility = View.GONE
            binding.floatingActionButton.visibility = View.GONE
            infotext.visibility = View.VISIBLE
            featmenu.forEach {
                menuSnapshot[it] = it.isVisible
                if(it.order!=2)it.isVisible=false else it.title = resources.getText(R.string.back_gold)
            }
        }
        else {
            binding.reclview.visibility = View.VISIBLE
            binding.bottomNavigationView.visibility = View.VISIBLE
            binding.floatingActionButton.visibility = View.VISIBLE
            infotext.visibility = View.GONE
            featmenu.forEach { if(it.order!=2) it.isVisible= menuSnapshot[it]!! else it.title = resources.getText(R.string.feats_info_label)}
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
            if (filterPrereq.contains("lvl")){
                if (feat.prerequisite.contains("level") && (filterLevel==4 || (feat.prerequisite.substringBefore("th level").last() == '2' && 12>=filterLevel))) return false
            }
            if(filterPrereq.contains("Type")){
                if (feat.prerequisite.contains("Type") && filterType.none { feat.prerequisite.contains(it) }) return false
            }
            if(filterPrereq.contains("Force")){
                if (filterForceLvl>-1){
                    if (!feat.prerequisite.contains("force")) return false
                    if (feat.prerequisite.substringAfter("casting lvl ","0").first().toString().toInt()>filterForceLvl) return false
                }
                else {
                    if (feat.prerequisite.contains("force")) return false
                }
            }
            if(filterPrereq.contains("Tech")){
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
        if (!atInfo){
            favSharedPreferences.edit {
                putStringSet("favorite_feats", favFeatList.toMutableSet())
            }
            finish()
        }
        else handleInfoSwitch()
    }
    private fun getFeats(): MutableList<Feat>{
        val getFeatList = mutableListOf<Feat>()
        val featTextArray=resources.getTextArray(R.array.feats)

        val starPaint = Paint()
        starPaint.textSize = 24F
        starPaint.typeface = resources.getFont(R.font.starjedi)
        starPaint.letterSpacing = 0.1F

        for(i in 5..featTextArray.size step 6){
            val isBig = resources.displayMetrics.run{starPaint.measureText(featTextArray[i-5] as String?) > (widthPixels/density- 35)}
            getFeatList.add(Feat(featTextArray[i-5].toString(),featTextArray[i-4].toString(),featTextArray[i-3].toString(),featTextArray[i-2].toString(),featTextArray[i-1],isBig))
        }
        return getFeatList
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}