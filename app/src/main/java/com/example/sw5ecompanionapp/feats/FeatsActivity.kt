package com.example.sw5ecompanionapp.feats

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
import com.example.sw5ecompanionapp.databinding.FeatsBinding

class FeatsActivity : AppCompatActivity() {
    private lateinit var binding: FeatsBinding
    private lateinit var reclview: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    private lateinit var featadapter: FeatsAdapter
    private var featList=mutableListOf<Feat>()
    private var adapterFeatList=mutableListOf<Feat>()
    private var currentfeatlist=mutableListOf<Feat>()
    private val eraselist: MutableList<Feat> = mutableListOf()

    private val favFeatList: MutableList<String> = mutableListOf()
    private lateinit var favSharedPreferences: SharedPreferences
    private var trimEnteredText=""
    private var favChecked=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FeatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("feats", Context.MODE_PRIVATE)
        favFeatList.addAll(favSharedPreferences.getStringSet("favorite_force_powers", mutableSetOf())?.toList()!!)

        featList.addAll(getFeats())

        reclview = binding.reclview
        adapterFeatList.addAll(featList)
        featadapter = FeatsAdapter(this,adapterFeatList,favFeatList)
        reclview.adapter = featadapter
        currentfeatlist.addAll(featList)

        binding.BackButton.setOnClickListener{returntomain()}

        searchView = binding.searchview
        searchView.setIconifiedByDefault(false)
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    featadapter.setFeatList(currentfeatlist.filter{it !in eraselist}.toMutableList())
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
                    trimEnteredText= enttext.trim().replace(" ","_").replace("-",".").replace("'","..")
                    if(featList.getNameList().none { it.contains(trimEnteredText,true)}){
                        featadapter.setFeatList(mutableListOf(Feat("NoSuchFeat")))
                    }
                    else {
                        featadapter.setFeatList(currentfeatlist.filter{(it.featname.contains(trimEnteredText,true)) and (it !in eraselist)}.toMutableList())
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    featadapter.setFeatList(currentfeatlist.filter{it !in eraselist}.toMutableList())
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
                    trimEnteredText= enttext.trim().replace(" ","_").replace("-",".").replace("'","..")
                    if(featList.getNameList().none { it.contains(trimEnteredText,true)}){
                        featadapter.setFeatList(mutableListOf(Feat("NoSuchFeat")))
                    }
                    else {
                        featadapter.setFeatList(currentfeatlist.filter{(it.featname.contains(trimEnteredText,true)) and (it !in eraselist)}.toMutableList())
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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            getText(R.string.sortABCdown)->{
                currentfeatlist=featList.sortFeatByNameDescending().filter { it !in eraselist }.toMutableList()
                featadapter.setFeatList(adapterFeatList.sortFeatByNameDescending().filter { it !in eraselist }.toMutableList())
                item.title=getText(R.string.sortABCup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortABCup)->{
                currentfeatlist=featList.sortFeatByName().filter { it !in eraselist }.toMutableList()
                featadapter.setFeatList(adapterFeatList.sortFeatByName().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortABCdown)
                returntotop(reclview,"sharp")}
            getText(R.string.favorites_gold)->{
                if (item.isChecked){
                    eraselist.removeAll{(it.featname !in favFeatList)}
                }
                else{
                    eraselist.addAll(featList.filter {it.featname !in favFeatList})
                }
                featadapter.setFeatList(currentfeatlist.filter{(it !in eraselist) and if(trimEnteredText.isNotBlank()){it.featname.contains(trimEnteredText,true)}else{true}}.toMutableList())
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
            putStringSet("favorite_force_powers",favFeatList.toMutableSet())
            apply()
        }
        finish()
    }
    private fun getFeats(): MutableList<Feat>{
        val getFeatList = mutableListOf<Feat>()
        val tempFeatList=resources.getTextArray(R.array.feats)
        for(i in 4..tempFeatList.size step 5){
            getFeatList.add(Feat(tempFeatList[i-4].toString(),tempFeatList[i-3].toString(),tempFeatList[i-2].toString(),tempFeatList[i-1].toString(),tempFeatList[i]))
        }
        return getFeatList
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}