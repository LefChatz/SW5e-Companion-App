package com.example.sw5ecompanionapp.maneuvers

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
import com.example.sw5ecompanionapp.databinding.ManeuversBinding


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
    private var favChecked=false
    private var typechecked = false
    private var filterType= mutableSetOf<String>()
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
                    maneuveradapter.setManeuverList(currentmaneuverlist.filter{filter(it)})
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
                    maneuveradapter.setManeuverList(currentmaneuverlist.filter{filter(it)})
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
            getText(R.string.maneuvers_menu_ability_score)->{
                if (!item.isChecked){
                    maneuvermenu.setGroupVisible(R.id.maneuversmenu_asi_group,true)
                    filterType.addAll(listOf("Str","Dex","Con","Int","Wis","Cha"))
                    maneuvermenu.forEach { if (it.order==3) it.isChecked=true}
                }
                else {
                    filterType.clear()
                    maneuvermenu.setGroupVisible(R.id.maneuversmenu_asi_group,false)
                }
                typechecked = !typechecked
                item.isChecked= !item.isChecked
            }
            getText(R.string.maneuvers_menu_str)->{
                if (!item.isChecked) filterType.add("Str")
                else filterType.remove("Str")
                item.isChecked= !item.isChecked
            }
            getText(R.string.maneuvers_menu_dex)->{
                if (!item.isChecked) filterType.add("Dex")
                else filterType.remove("Dex")
                item.isChecked= !item.isChecked
            }
            getText(R.string.maneuvers_menu_con)->{
                if (!item.isChecked) filterType.add("Con")
                else filterType.remove("Con")
                item.isChecked= !item.isChecked
            }
            getText(R.string.maneuvers_menu_int)->{
                if (!item.isChecked) filterType.add("Int")
                else filterType.remove("Int")
                item.isChecked= !item.isChecked
            }
            getText(R.string.maneuvers_menu_wis)->{
                if (!item.isChecked) filterType.add("Wis")
                else filterType.remove("Wis")
                item.isChecked= !item.isChecked
            }
            getText(R.string.maneuvers_menu_cha)->{
                if (!item.isChecked) filterType.add("Cha")
                else filterType.remove("Cha")
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
        if (!maneuver.maneuvername.contains(trimEnteredText)) return false
        if (typechecked){
            if (filterType.isNotEmpty()) {
                if (filterType.none { maneuver.type.contains(it) or maneuver.type.contains("Any") }) return false
            }
            else{
                if (maneuver.type != "-") return false
            }
        }
        if (favChecked && maneuver.maneuvername !in favManeuverList) return false
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