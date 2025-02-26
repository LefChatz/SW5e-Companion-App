package com.amachewrs.sw5ecompanionapp.maneuvers

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
import androidx.recyclerview.widget.RecyclerView
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.SW5ECompanionApp
import com.amachewrs.sw5ecompanionapp.databinding.ManeuversBinding


class ManeuversActivity : AppCompatActivity() {
    private lateinit var binding: ManeuversBinding
    private lateinit var reclview: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    private lateinit var maneuveradapter: ManeuversAdapter
    private var maneuverList=mutableListOf<Maneuver>()
    private var adapterManeuverList=mutableListOf<Maneuver>()
    private var currentmaneuverlist=listOf<Maneuver>()

    private val favManeuverList: MutableList<String> = mutableListOf()
    private lateinit var favSharedPreferences: SharedPreferences
    private var trimEnteredText=""
    private var filters = mutableSetOf("Force","Tech")
    private var filterType= mutableSetOf<String>()
    private var filterTypeItems = setOf<MenuItem>()
    private lateinit var maneuvermenu: Menu
    private var keepmenu = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ManeuversBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("maneuvers", Context.MODE_PRIVATE)
        favManeuverList.addAll(favSharedPreferences.getStringSet("favorite_maneuvers", mutableSetOf())?.toList()!!)

        maneuverList.addAll(getManeuvers())

        reclview = binding.reclview
        adapterManeuverList.addAll(maneuverList)
        maneuveradapter = ManeuversAdapter(this,adapterManeuverList,favManeuverList)
        reclview.adapter = maneuveradapter
        currentmaneuverlist = maneuverList

        binding.BackButton.setOnClickListener{returntomain()}

        searchView = binding.searchview
        searchView.setIconifiedByDefault(false)
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    trimEnteredText=""
                    maneuveradapter.setManeuverList(currentmaneuverlist.filter{filter(it)})
                    object : CountDownTimer(1000, 1001) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(trimEnteredText.isBlank()){searchView.clearFocus()}
                        }
                    }.start()
                }
                else {
                    trimEnteredText= enttext.trim()
                    if(maneuverList.getNameList().none { it.contains(trimEnteredText,true)}){
                        maneuveradapter.setManeuverList(listOf(Maneuver("NoSuchManeuver")))
                    }
                    else {
                        maneuveradapter.setManeuverList(currentmaneuverlist.filter { filter(it) })
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    trimEnteredText=""
                    maneuveradapter.setManeuverList(currentmaneuverlist.filter{filter(it)})
                    object : CountDownTimer(1000, 999) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(trimEnteredText.isBlank()){searchView.clearFocus()}
                        }
                    }.start()
                }
                else {
                    trimEnteredText= enttext.trim()
                    if(maneuverList.getNameList().none { it.contains(trimEnteredText,true)}){
                        maneuveradapter.setManeuverList(listOf(Maneuver("NoSuchManeuver")))
                    }
                    else {
                        maneuveradapter.setManeuverList(currentmaneuverlist.filter { filter(it) })
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
        menuInflater.inflate(R.menu.menu_maneuvers,menu)
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.downarrowgold)
        if (menu != null){
            maneuvermenu = menu
        }
        filterTypeItems = setOf(maneuvermenu.findItem(R.id.menu_maneuvers_type_general),maneuvermenu.findItem(R.id.menu_maneuvers_type_mental),maneuvermenu.findItem(R.id.menu_maneuvers_type_physical))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        with(maneuvermenu.findItem(R.id.eqmenu_ok)){
            if(!isVisible)isVisible=true
        }
        keepmenu=true
        when(item.title){
            getText(R.string.sortABCdown)->{
                keepmenu=false
                currentmaneuverlist=maneuverList.sortManeuverByNameDescending()
                item.title=getText(R.string.sortABCup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortABCup)->{
                keepmenu=false
                currentmaneuverlist=maneuverList.sortManeuverByName()
                item.title = getText(R.string.sortABCdown)
                returntotop(reclview,"sharp")}
            getText(R.string.maneuvers_menu_type)->{
                if (!item.isChecked){
                    filterType.clear()
                    filterType.addAll(listOf("General","Mental","Physical"))
                    filterTypeItems.forEach {it.isChecked=true}
                }
                maneuvermenu.setGroupVisible(R.id.maneuvers_menu_type_group,!item.isChecked)
                changeFilter("Type",item,filters)
            }
            getText(R.string.maneuvers_menu_general)-> changeFilter("General",item, filterType)
            getText(R.string.maneuvers_menu_mental)-> changeFilter("Mental",item, filterType)
            getText(R.string.maneuvers_menu_physical)-> changeFilter("Physical",item, filterType)
            getText(R.string.maneuvers_menu_forcecasting)->changeFilter("Force",item,filters)
            getText(R.string.maneuvers_menu_techcasting)->changeFilter("Tech",item,filters)
            getText(R.string.favorites_gold)-> changeFilter("Fav",item, filters)
            getText(R.string.equipment_menu_ok)->{
                keepmenu=false
                item.isVisible=false
            }
        }
        maneuveradapter.setManeuverList(currentmaneuverlist.filter {filter(it)})

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


    private fun filter(maneuver: Maneuver): Boolean{
        if (!maneuver.maneuvername.contains(trimEnteredText,true)) return false
        if (filters.contains("Type")){
            if (filterType.none {maneuver.type.contains(it)}) return false
        }
        if (filters.contains("Fav") && maneuver.maneuvername !in favManeuverList) return false
        if (!filters.contains("Force") && maneuver.prerequisite.contains("force")) return false
        if (!filters.contains("Tech") && maneuver.prerequisite.contains("tech")) return false
        return true
    }

    private fun changeFilter(filter: String, item: MenuItem, filterList: MutableSet<String>){
        if (!item.isChecked) filterList.add(filter)
        else filterList.remove(filter)
        item.isChecked= !item.isChecked
    }

    private fun returntotop(view: RecyclerView,mode: String){
        when(mode){
            "smooth"->view.smoothScrollToPosition(0)
            "sharp"->view.scrollToPosition(0)
        }

    }
    fun returntomain() {
        with(favSharedPreferences.edit()){
            putStringSet("favorite_maneuvers",favManeuverList.toMutableSet())
            apply()
        }
        finish()
    }
    private fun getManeuvers(): MutableList<Maneuver>{
        val getManeuverList = mutableListOf<Maneuver>()
        val tempManeuverList=resources.getTextArray(R.array.maneuvers)
        for(i in 5..tempManeuverList.size step 6){
            getManeuverList.add(Maneuver(tempManeuverList[i-5].toString(),tempManeuverList[i-4].toString(),tempManeuverList[i-3].toString(),tempManeuverList[i-2].toString(),tempManeuverList[i-1],tempManeuverList[i].toString().toBoolean()))
        }
        return getManeuverList
    }
    /*override fun onPanelClosed(maneuverureId: Int, menu: Menu) {
        if (keepmenu) {
            openOptionsMenu()
        }
        else{
            maneuvermenu.findItem(R.id.eqmenu_ok).isVisible=false
        }
        super.onPanelClosed(maneuverureId, menu)
    }*/
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}