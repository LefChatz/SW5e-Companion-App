package com.example.swtrial2

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
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.swtrial2.databinding.ActivityForcecastingBinding
import com.example.swtrial2.forcecasting.ForcecastingAdapter
import com.example.swtrial2.forcecasting.Forcepower
import com.example.swtrial2.forcecasting.getNameList
import com.example.swtrial2.forcecasting.sortForcepowerByLevel
import com.example.swtrial2.forcecasting.sortForcepowerByLevelDescending
import com.example.swtrial2.forcecasting.sortForcepowerByName
import com.example.swtrial2.forcecasting.sortForcepowerByNameDescending
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ForceCastingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForcecastingBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var reclview: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var darkforcepowers: List<Forcepower>
    private lateinit var backButton: AppCompatButton

    private lateinit var forcepoweradapter: ForcecastingAdapter
    private lateinit var lightforcepowers: List<Forcepower>
    private var forcepowerList=mutableListOf<Forcepower>()
    private var adapterForcepowerList=mutableListOf<Forcepower>()
    private var currentforcepowerlist=mutableListOf<Forcepower>()
    private val eraselist: MutableList<Forcepower> = mutableListOf()

    private val favForcepowerList: MutableList<String> = mutableListOf()
    private lateinit var favSharedPreferences: SharedPreferences
    private  var trimEnteredText=""
    private var darkChecked=true
    private var lightChecked=true
    private var favChecked=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForcecastingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar=findViewById(R.id.force_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("favList", Context.MODE_PRIVATE)
        favForcepowerList.addAll(favSharedPreferences.getStringSet("favList", mutableSetOf())?.toList()!!)

        forcepowerList.addAll(getForcepowers())

        reclview = findViewById(R.id.reclview)
        adapterForcepowerList.addAll(forcepowerList)
        forcepoweradapter = ForcecastingAdapter(this,adapterForcepowerList,favForcepowerList)
        reclview.adapter = forcepoweradapter
        currentforcepowerlist.addAll(forcepowerList)
        lightforcepowers=forcepowerList.filter { it.side.contains("Light",true)}
        darkforcepowers=forcepowerList.filter { it.side.contains("Dark",true)}

        backButton=findViewById(R.id.forceBackButton)
        backButton.setOnClickListener{returntomain()}

        searchView = findViewById(R.id.searchview)
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

        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener{
            returntotop(reclview,"smooth")
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_forcecasting,menu)
        toolbar.overflowIcon = AppCompatResources.getDrawable(this,R.drawable.downarrowgold)
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
                    if(favChecked){eraselist.removeAll(forcepowerList.filter { (it in darkforcepowers) and (it.forcepowername in favForcepowerList) and if(trimEnteredText.isNotBlank()){it.forcepowername.contains(trimEnteredText)}else{true}})}
                    else{if(trimEnteredText.isNotBlank()){forcepowerList.filter { (it in darkforcepowers) and it.forcepowername.contains(trimEnteredText)}}else{eraselist.removeAll(darkforcepowers)}}
                }
                forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{it !in eraselist}.toMutableList())
                item.isChecked= !item.isChecked
                darkChecked= !darkChecked
            }
            getText(R.string.Light)->{
                if(item.isChecked){eraselist.addAll(lightforcepowers)}
                else{
                    if(favChecked){eraselist.removeAll(forcepowerList.filter { (it in lightforcepowers) and (it.forcepowername in favForcepowerList) and if(trimEnteredText.isNotBlank()){it.forcepowername.contains(trimEnteredText)}else{true}})}
                    else{if(trimEnteredText.isNotBlank()){forcepowerList.filter { (it in lightforcepowers) and it.forcepowername.contains(trimEnteredText)}}else{eraselist.removeAll(lightforcepowers)}}
                }
                forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{it !in eraselist}.toMutableList())
                item.isChecked= !item.isChecked
                lightChecked= !lightChecked
            }
            getText(R.string.favorites_forcepowers)->{
                if (item.isChecked){
                    eraselist.removeAll(forcepowerList.filter {(it.forcepowername !in favForcepowerList)})
                    if(!darkChecked){eraselist.addAll(darkforcepowers)}
                    if(!lightChecked){eraselist.addAll(lightforcepowers)}
                }
                else{
                    eraselist.addAll(forcepowerList.filter {it.forcepowername !in favForcepowerList})
                }
                forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{it !in eraselist}.toMutableList())
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
        startActivity(Intent(this,SW5ECompanionApp::class.java))
        with(favSharedPreferences.edit()){
            putStringSet("favList",favForcepowerList.toMutableSet())
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