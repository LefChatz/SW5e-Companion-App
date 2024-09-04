package com.example.sw5ecompanionapp.techcasting

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
import com.example.sw5ecompanionapp.databinding.TechcastingBinding

class TechcastingActivity : AppCompatActivity() {
    private lateinit var binding: TechcastingBinding
    private lateinit var reclview: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var darktechpowers: List<Techpower>

    private lateinit var techpoweradapter: TechcastingAdapter
    private lateinit var lighttechpowers: List<Techpower>
    private var techpowerList=mutableListOf<Techpower>()
    private var adapterTechpowerList=mutableListOf<Techpower>()
    private var currenttechpowerlist=mutableListOf<Techpower>()
    private val eraselist: MutableList<Techpower> = mutableListOf()

    private val favTechpowerList: MutableList<String> = mutableListOf()
    private lateinit var favSharedPreferences: SharedPreferences
    private  var trimEnteredText=""
    private var darkChecked=true
    private var lightChecked=true
    private var favChecked=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TechcastingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("favList", Context.MODE_PRIVATE)
        favTechpowerList.addAll(favSharedPreferences.getStringSet("favList", mutableSetOf())?.toList()!!)

        techpowerList.addAll(getTechpowers())

        reclview = binding.reclview
        adapterTechpowerList.addAll(techpowerList.sortTechpowerByName())
        techpoweradapter = TechcastingAdapter(this,adapterTechpowerList,favTechpowerList)
        reclview.adapter = techpoweradapter
        currenttechpowerlist.addAll(techpowerList.sortTechpowerByName())
        lighttechpowers=techpowerList
        darktechpowers=techpowerList

        binding.BackButton.setOnClickListener{returntomain()}

        searchView = binding.searchview
        searchView.setIconifiedByDefault(false)
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    techpoweradapter.setTechpowerList(currenttechpowerlist.filter{it !in eraselist}.toMutableList())
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
                    if(techpowerList.getNameList().none { it.contains(trimEnteredText,true)}){
                        techpoweradapter.setTechpowerList(mutableListOf(Techpower("NoSuchTechpower")))
                    }
                    else {
                        techpoweradapter.setTechpowerList(currenttechpowerlist.filter{(it.techpowername.contains(trimEnteredText,true)) and (it !in eraselist)}.toMutableList())
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    techpoweradapter.setTechpowerList(currenttechpowerlist.filter{it !in eraselist}.toMutableList())
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
                    if(techpowerList.getNameList().none { it.contains(trimEnteredText,true)}){
                        techpoweradapter.setTechpowerList(mutableListOf(Techpower("NoSuchTechpower")))
                    }
                    else {
                        techpoweradapter.setTechpowerList(currenttechpowerlist.filter{(it.techpowername.contains(trimEnteredText,true)) and (it !in eraselist)}.toMutableList())
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
        menuInflater.inflate(R.menu.menu_techcasting,menu)
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.downarrowgold)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            getText(R.string.sortABCdown)->{
                currenttechpowerlist=techpowerList.sortTechpowerByNameDescending().filter { it !in eraselist }.toMutableList()
                techpoweradapter.setTechpowerList(adapterTechpowerList.sortTechpowerByNameDescending().filter { it !in eraselist }.toMutableList())
                item.title=getText(R.string.sortABCup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortABCup)->{
                currenttechpowerlist=techpowerList.sortTechpowerByLevel().filter { it !in eraselist }.toMutableList()
                techpoweradapter.setTechpowerList(adapterTechpowerList.sortTechpowerByName().sortTechpowerByLevel().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortLvldown)
                returntotop(reclview,"sharp")}
            getText(R.string.sortLvldown)->{
                currenttechpowerlist=techpowerList.sortTechpowerByLevelDescending().filter { it !in eraselist }.toMutableList()
                techpoweradapter.setTechpowerList(adapterTechpowerList.sortTechpowerByLevelDescending().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortLvlup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortLvlup)->{
                currenttechpowerlist=techpowerList.sortTechpowerByName().filter { it !in eraselist }.toMutableList()
                techpoweradapter.setTechpowerList(adapterTechpowerList.sortTechpowerByName().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortABCdown)
                returntotop(reclview,"sharp")}

            getText(R.string.favorites_gold)->{
                if (item.isChecked){
                    eraselist.removeAll{(it.techpowername !in favTechpowerList)}
                }
                else{
                    eraselist.addAll(techpowerList.filter {it.techpowername !in favTechpowerList})
                }
                techpoweradapter.setTechpowerList(currenttechpowerlist.filter{(it !in eraselist) and if(trimEnteredText.isNotBlank()){it.techpowername.contains(trimEnteredText,true)}else{true}}.toMutableList())
                item.isChecked= !item.isChecked
                favChecked= !favChecked
            }
            /*getText(R.string.Dark)->{
                if(item.isChecked){eraselist.addAll(darktechpowers)}
                else{
                    if(favChecked){eraselist.removeAll{ (it in darktechpowers) and (it.techpowername in favTechpowerList)}}
                    else{eraselist.removeAll(darktechpowers)}
                }
                techpoweradapter.setTechpowerList(currenttechpowerlist.filter{(it !in eraselist) and if(trimEnteredText.isNotBlank()){it.techpowername.contains(trimEnteredText,true)}else{true}}.toMutableList())
                item.isChecked= !item.isChecked
                darkChecked= !darkChecked
            }
            getText(R.string.Light)->{
                if(item.isChecked){eraselist.addAll(lighttechpowers)}
                else{
                    if(favChecked){eraselist.removeAll{ (it in lighttechpowers) and (it.techpowername in favTechpowerList)}}
                    else{eraselist.removeAll(lighttechpowers)}
                }
                techpoweradapter.setTechpowerList(currenttechpowerlist.filter{(it !in eraselist) and if(trimEnteredText.isNotBlank()){it.techpowername.contains(trimEnteredText,true)}else{true}}.toMutableList())
                item.isChecked= !item.isChecked
                lightChecked= !lightChecked
            }*/
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
            putStringSet("favList",favTechpowerList.toMutableSet())
            apply()
        }
        finish()
    }
    private fun getTechpowers(): MutableList<Techpower>{
        val getTechpowerList = mutableListOf<Techpower>()
        val tempTechpowerList=resources.getTextArray(R.array.techpowerlist)
        for(i in 9..tempTechpowerList.size step 10){
            getTechpowerList.add(Techpower(tempTechpowerList[i-9].toString(),tempTechpowerList[i-8],tempTechpowerList[i-7].toString(),tempTechpowerList[i-6].toString().toInt(),tempTechpowerList[i-5].toString(),tempTechpowerList[i-4].toString(),tempTechpowerList[i-3].toString().toBoolean(),tempTechpowerList[i-2].toString(),tempTechpowerList[i-1].toString().toBoolean(),tempTechpowerList[i]))
        }
        return getTechpowerList
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}