package com.example.sw5ecompanionapp.feats

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.SW5ECompanionApp
import com.example.sw5ecompanionapp.databinding.FeatsBinding


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
    private var favChecked=false
    private var asichecked = false
    private var filterASI= mutableSetOf<String>()
    private lateinit var featmenu: Menu
    private var keepmenu = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FeatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("feats", Context.MODE_PRIVATE)
        favFeatList.addAll(favSharedPreferences.getStringSet("favorite_feats", mutableSetOf())?.toList()!!)

        featList.addAll(getFeats())

        reclview = binding.reclview
        adapterFeatList.addAll(featList)
        featadapter = FeatsAdapter(this,adapterFeatList,favFeatList)
        reclview.adapter = featadapter
        currentfeatlist = featList

        binding.BackButton.setOnClickListener{returntomain()}

        searchView = binding.searchview
        searchView.setIconifiedByDefault(false)
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    featadapter.setFeatList(currentfeatlist.filter{filter(it)})
                    object : CountDownTimer(1000, 1001) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(trimEnteredText.isBlank()){searchView.clearFocus()}
                        }
                    }.start()
                    trimEnteredText=""
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
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    featadapter.setFeatList(currentfeatlist.filter{filter(it)})
                    object : CountDownTimer(1000, 999) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(trimEnteredText.isBlank()){searchView.clearFocus()}
                        }
                    }.start()
                    trimEnteredText=""
                }
                else {
                    trimEnteredText= enttext.trim().replace(" ","_").replace("'","..").replace("-",".")
                    if(featList.getNameList().none { it.contains(trimEnteredText,true)}){
                        /*searchedlist = listOf(Feat("NoSuchFeat"))*/
                        featadapter.setFeatList(listOf(Feat("NoSuchFeat")))
                    }
                    else {
                        /*searchedlist = currentfeatlist.filter{(it.featname.contains(trimEnteredText,true)) and (it !in eraselist)}*/
                        featadapter.setFeatList(currentfeatlist.filter { filter(it) })
                    }
                }
                return false
            }
        })


        binding.floatingActionButton.setOnClickListener{returntotop(reclview,"smooth")}

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
                returntotop(reclview,"sharp")}
            getText(R.string.sortABCup)->{
                keepmenu=false
                currentfeatlist=featList.sortFeatByName()
                item.title = getText(R.string.sortABCdown)
                returntotop(reclview,"sharp")}
            getText(R.string.feats_menu_ability_score)->{
                if (!item.isChecked){
                    featmenu.setGroupVisible(R.id.featsmenu_asi_group,true)
                    filterASI.addAll(listOf("Str","Dex","Con","Int","Wis","Cha"))
                    featmenu.forEach { if (it.order==3) it.isChecked=true}
                }
                else {
                    filterASI.clear()
                    featmenu.setGroupVisible(R.id.featsmenu_asi_group,false)
                }
                asichecked = !asichecked
                item.isChecked= !item.isChecked
            }
            getText(R.string.feats_menu_str)->{
                if (!item.isChecked) filterASI.add("Str")
                else filterASI.remove("Str")
                item.isChecked= !item.isChecked
            }
            getText(R.string.feats_menu_dex)->{
                if (!item.isChecked) filterASI.add("Dex")
                else filterASI.remove("Dex")
                item.isChecked= !item.isChecked
            }
            getText(R.string.feats_menu_con)->{
                if (!item.isChecked) filterASI.add("Con")
                else filterASI.remove("Con")
                item.isChecked= !item.isChecked
            }
            getText(R.string.feats_menu_int)->{
                if (!item.isChecked) filterASI.add("Int")
                else filterASI.remove("Int")
                item.isChecked= !item.isChecked
            }
            getText(R.string.feats_menu_wis)->{
                if (!item.isChecked) filterASI.add("Wis")
                else filterASI.remove("Wis")
                item.isChecked= !item.isChecked
            }
            getText(R.string.feats_menu_cha)->{
                if (!item.isChecked) filterASI.add("Cha")
                else filterASI.remove("Cha")
                item.isChecked= !item.isChecked
            }
            getText(R.string.favorites_gold)->{
                favChecked= !favChecked
                item.isChecked= !item.isChecked
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


    private fun filter(feat: Feat): Boolean{
        if (!feat.featname.contains(trimEnteredText)) return false
        if (asichecked){
            if (filterASI.isNotEmpty()) {
                if (filterASI.none { feat.asi.contains(it) or feat.asi.contains("Any") }) return false
            }
            else{
                if (feat.asi != "-") return false
            }
        }
        if (favChecked && feat.featname !in favFeatList) return false
        return true
    }
    private fun returntotop(view: RecyclerView,mode: String){
        when(mode){
            "smooth"->view.smoothScrollToPosition(0)
            "sharp"->view.scrollToPosition(0)
        }

    }
    fun returntomain() {
        startActivity(Intent(this, SW5ECompanionApp::class.java))
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
    /*override fun onPanelClosed(featureId: Int, menu: Menu) {
        if (keepmenu) {
            openOptionsMenu()
        }
        else{
            featmenu.findItem(R.id.eqmenu_ok).isVisible=false
        }
        super.onPanelClosed(featureId, menu)
    }*/
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}