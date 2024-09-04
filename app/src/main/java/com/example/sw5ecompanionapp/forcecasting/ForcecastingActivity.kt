package com.example.sw5ecompanionapp.forcecasting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.SW5ECompanionApp
import com.example.sw5ecompanionapp.databinding.ForcecastingBinding

class ForcecastingActivity : AppCompatActivity() {
    private lateinit var binding: ForcecastingBinding
    private lateinit var reclview: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var darkforcepowers: List<Forcepower>

    private lateinit var forcepoweradapter: ForcecastingAdapter
    private lateinit var lightforcepowers: List<Forcepower>
    private var forcepowerList=mutableListOf<Forcepower>()
    private var adapterForcepowerList=mutableListOf<Forcepower>()
    private var currentforcepowerlist=mutableListOf<Forcepower>()
    private val eraselist: MutableList<Forcepower> = mutableListOf()

    private val favForcepowerList: MutableList<String> = mutableListOf()
    private lateinit var favSharedPreferences: SharedPreferences
    private var trimEnteredText=""
    private var darkChecked=true
    private var lightChecked=true
    private var favChecked=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForcecastingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("forcecasting", Context.MODE_PRIVATE)
        favForcepowerList.addAll(favSharedPreferences.getStringSet("favorite_force_powers", mutableSetOf())?.toList()!!)

        forcepowerList.addAll(getForcepowers())

        reclview = binding.reclview
        adapterForcepowerList.addAll(forcepowerList)
        forcepoweradapter = ForcecastingAdapter(this,adapterForcepowerList,favForcepowerList)
        reclview.adapter = forcepoweradapter
        currentforcepowerlist.addAll(forcepowerList)
        lightforcepowers=forcepowerList.filter { it.side.contains("Light",true)}
        darkforcepowers=forcepowerList.filter { it.side.contains("Dark",true)}

        binding.BackButton.setOnClickListener{returntomain()}

        searchView = binding.searchview
        searchView.setIconifiedByDefault(false)
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{it !in eraselist}.toMutableList())
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
                    trimEnteredText= enttext.trim().replace(" ","_")
                    if(forcepowerList.getNameList().none { it.contains(trimEnteredText,true)}){
                        forcepoweradapter.setForcepowerList(mutableListOf(Forcepower("NoSuchForcepower")))
                    }
                    else {
                        forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{(it.forcepowername.contains(trimEnteredText,true)) and (it !in eraselist)}.toMutableList())
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{it !in eraselist}.toMutableList())
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
                    trimEnteredText= enttext.trim().replace(" ","_")
                    if(forcepowerList.getNameList().none { it.contains(trimEnteredText,true)}){
                        forcepoweradapter.setForcepowerList(mutableListOf(Forcepower("NoSuchForcepower")))
                    }
                    else {
                        forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{(it.forcepowername.contains(trimEnteredText,true)) and (it !in eraselist)}.toMutableList())
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
        menuInflater.inflate(R.menu.menu_forcecasting,menu)
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.downarrowgold)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            getText(R.string.sortABCdown)->{
                currentforcepowerlist=forcepowerList.sortForcepowerByNameDescending().filter { it !in eraselist }.toMutableList()
                forcepoweradapter.setForcepowerList(adapterForcepowerList.sortForcepowerByNameDescending().filter { it !in eraselist }.toMutableList())
                item.title=getText(R.string.sortABCup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortABCup)->{
                currentforcepowerlist=forcepowerList.sortForcepowerByLevel().filter { it !in eraselist }.toMutableList()
                forcepoweradapter.setForcepowerList(adapterForcepowerList.sortForcepowerByName().sortForcepowerByLevel().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortLvldown)
                returntotop(reclview,"sharp")}
            getText(R.string.sortLvldown)->{
                currentforcepowerlist=forcepowerList.sortForcepowerByLevelDescending().filter { it !in eraselist }.toMutableList()
                forcepoweradapter.setForcepowerList(adapterForcepowerList.sortForcepowerByLevelDescending().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortLvlup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortLvlup)->{
                currentforcepowerlist=forcepowerList.sortForcepowerByName().filter { it !in eraselist }.toMutableList()
                forcepoweradapter.setForcepowerList(adapterForcepowerList.sortForcepowerByName().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortABCdown)
                returntotop(reclview,"sharp")}
            getText(R.string.Dark)->{
                if(item.isChecked){eraselist.addAll(darkforcepowers)}
                else{
                    if(favChecked){eraselist.removeAll{ (it in darkforcepowers) and (it.forcepowername in favForcepowerList)}}
                    else{eraselist.removeAll(darkforcepowers)}
                }
                forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{(it !in eraselist) and if(trimEnteredText.isNotBlank()){it.forcepowername.contains(trimEnteredText,true)}else{true}}.toMutableList())
                item.isChecked= !item.isChecked
                darkChecked= !darkChecked
            }
            getText(R.string.Light)->{
                if(item.isChecked){eraselist.addAll(lightforcepowers)}
                else{
                    if(favChecked){eraselist.removeAll{ (it in lightforcepowers) and (it.forcepowername in favForcepowerList)}}
                    else{eraselist.removeAll(lightforcepowers)}
                }
                forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{(it !in eraselist) and if(trimEnteredText.isNotBlank()){it.forcepowername.contains(trimEnteredText,true)}else{true}}.toMutableList())
                item.isChecked= !item.isChecked
                lightChecked= !lightChecked
            }
            getText(R.string.favorites_gold)->{
                if (item.isChecked){
                    eraselist.removeAll{(it.forcepowername !in favForcepowerList) and (if(!darkChecked) {!it.side.contains("Dark",true)}else{true})and(if(!lightChecked) { !it.side.contains("Light",true)}else{true})}
                }
                else{
                    eraselist.addAll(forcepowerList.filter {it.forcepowername !in favForcepowerList})
                }
                forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{(it !in eraselist) and if(trimEnteredText.isNotBlank()){it.forcepowername.contains(trimEnteredText,true)}else{true}}.toMutableList())
                item.isChecked= !item.isChecked
                favChecked= !favChecked
            }
        }
        return super.onOptionsItemSelected(item)
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
            putStringSet("favorite_force_powers",favForcepowerList.toMutableSet())
            apply()
        }
        finish()
    }
    private fun getForcepowers(): MutableList<Forcepower>{
        val getForcepowerList = mutableListOf<Forcepower>()
        val tempForcepowerList=resources.getTextArray(R.array.forcepowerlist)
        for(i in 8..tempForcepowerList.size step 9){
            getForcepowerList.add(Forcepower(tempForcepowerList[i-8].toString(),tempForcepowerList[i-7],tempForcepowerList[i-6].toString(),tempForcepowerList[i-5],tempForcepowerList[i-4].toString().toInt(),tempForcepowerList[i-3].toString().toBoolean(),tempForcepowerList[i-2].toString().toBoolean(),tempForcepowerList[i-1].toString().toBoolean(),tempForcepowerList[i]))
        }
        return getForcepowerList
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}