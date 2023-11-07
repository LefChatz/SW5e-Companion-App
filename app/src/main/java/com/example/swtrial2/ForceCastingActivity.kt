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
import com.example.swtrial2.spells.adapterstuff.Spell
import com.example.swtrial2.spells.adapterstuff.SpellAdapter
import com.example.swtrial2.spells.adapterstuff.getNameList
import com.example.swtrial2.spells.adapterstuff.sortSpellByLevel
import com.example.swtrial2.spells.adapterstuff.sortSpellByLevelDescending
import com.example.swtrial2.spells.adapterstuff.sortSpellByName
import com.example.swtrial2.spells.adapterstuff.sortSpellByNameDescending
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ForceCastingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForcecastingBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var reclview: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var darkspells: List<Spell>
    private lateinit var backButton: AppCompatButton

    private lateinit var spelladapter: SpellAdapter
    private lateinit var lightspells: List<Spell>
    private var spellList=mutableListOf<Spell>()
    private var adapterSpellList=mutableListOf<Spell>()
    private var currentspelllist=mutableListOf<Spell>()
    private val eraselist: MutableList<Spell> = mutableListOf()

    private val favSpellList: MutableList<String> = mutableListOf()
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
        favSpellList.addAll(favSharedPreferences.getStringSet("favList", mutableSetOf())?.toList()!!)

        spellList.addAll(getSpells())

        reclview = findViewById(R.id.reclview)
        adapterSpellList.addAll(spellList)
        spelladapter = SpellAdapter(this,adapterSpellList,favSpellList)
        reclview.adapter = spelladapter
        currentspelllist.addAll(spellList)
        lightspells=spellList.filter { it.side.contains("Light",true)}
        darkspells=spellList.filter { it.side.contains("Dark",true)}

        backButton=findViewById(R.id.forceBackButton)
        backButton.setOnClickListener{returntomain()}

        searchView = findViewById(R.id.searchview)
        searchView.setIconifiedByDefault(false)
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    spelladapter.setSpellList(currentspelllist.filter{it !in eraselist}.toMutableList())
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
                    if(spellList.getNameList().none { it.contains(trimEnteredText,true)}){
                        spelladapter.setSpellList(mutableListOf(Spell("NoSuchSpell")))
                    }
                    else {
                        spelladapter.setSpellList(currentspelllist.filter{(it.spellname.contains(trimEnteredText,true)) and (it !in eraselist)}.toMutableList())
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                returntotop(reclview,"sharp")
                if(enttext.isNullOrBlank()){
                    spelladapter.setSpellList(currentspelllist.filter{it !in eraselist}.toMutableList())
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
                    if(spellList.getNameList().none { it.contains(trimEnteredText,true)}){
                        spelladapter.setSpellList(mutableListOf(Spell("NoSuchSpell")))
                    }
                    else {
                        spelladapter.setSpellList(currentspelllist.filter{(it.spellname.contains(trimEnteredText,true)) and (it !in eraselist)}.toMutableList())
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
                currentspelllist=spellList.sortSpellByNameDescending().filter { it !in eraselist }.toMutableList()
                spelladapter.setSpellList(adapterSpellList.sortSpellByNameDescending().filter { it !in eraselist }.toMutableList())
                item.title=getText(R.string.sortABCup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortABCup)->{
                currentspelllist=spellList.sortSpellByLevel().filter { it !in eraselist }.toMutableList()
                spelladapter.setSpellList(adapterSpellList.sortSpellByLevel().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortLvldown)
                returntotop(reclview,"sharp")}
            getText(R.string.sortLvldown)->{
                currentspelllist=spellList.sortSpellByLevelDescending().filter { it !in eraselist }.toMutableList()
                spelladapter.setSpellList(adapterSpellList.sortSpellByLevelDescending().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortLvlup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortLvlup)->{
                currentspelllist=spellList.sortSpellByName().filter { it !in eraselist }.toMutableList()
                spelladapter.setSpellList(adapterSpellList.sortSpellByName().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortABCdown)
                returntotop(reclview,"sharp")}
            getText(R.string.Dark)->{
                if(item.isChecked){eraselist.addAll(darkspells)}
                else{
                    if(favChecked){eraselist.removeAll(spellList.filter { (it in darkspells) and (it.spellname in favSpellList) and if(trimEnteredText.isNotBlank()){it.spellname.contains(trimEnteredText)}else{true}})}
                    else{if(trimEnteredText.isNotBlank()){spellList.filter { (it in darkspells) and it.spellname.contains(trimEnteredText)}}else{eraselist.removeAll(darkspells)}}
                }
                spelladapter.setSpellList(currentspelllist.filter{it !in eraselist}.toMutableList())
                item.isChecked= !item.isChecked
                darkChecked= !darkChecked
            }
            getText(R.string.Light)->{
                if(item.isChecked){eraselist.addAll(lightspells)}
                else{
                    if(favChecked){eraselist.removeAll(spellList.filter { (it in lightspells) and (it.spellname in favSpellList) and if(trimEnteredText.isNotBlank()){it.spellname.contains(trimEnteredText)}else{true}})}
                    else{if(trimEnteredText.isNotBlank()){spellList.filter { (it in lightspells) and it.spellname.contains(trimEnteredText)}}else{eraselist.removeAll(lightspells)}}
                }
                spelladapter.setSpellList(currentspelllist.filter{it !in eraselist}.toMutableList())
                item.isChecked= !item.isChecked
                lightChecked= !lightChecked
            }
            getText(R.string.favorites_forcepowers)->{
                if (item.isChecked){
                    eraselist.removeAll(spellList.filter {(it.spellname !in favSpellList)})
                    if(!darkChecked){eraselist.addAll(darkspells)}
                    if(!lightChecked){eraselist.addAll(lightspells)}
                }
                else{
                    eraselist.addAll(spellList.filter {it.spellname !in favSpellList})
                }
                spelladapter.setSpellList(currentspelllist.filter{it !in eraselist}.toMutableList())
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
            putStringSet("favList",favSpellList.toMutableSet())
            apply()
        }
        finish()
    }
    private fun getSpells(): MutableList<Spell>{
        val getSpellList = mutableListOf<Spell>()
        val tempSpellList=resources.getTextArray(R.array.spelllist)
        for(i in 8..tempSpellList.size step 9){
            getSpellList.add(Spell(tempSpellList[i-8].toString(),tempSpellList[i-7],tempSpellList[i-6].toString(),tempSpellList[i-5],tempSpellList[i-4].toString().toInt(),tempSpellList[i-3].toString().toBoolean(),tempSpellList[i-2].toString().toBoolean(),tempSpellList[i-1].toString().toBoolean(),tempSpellList[i]))
        }
        return getSpellList
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}