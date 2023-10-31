package com.example.swtrial2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
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
    private lateinit var spelladapter: SpellAdapter
    private lateinit var reclview: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var levels: List<String>
    private lateinit var darkspells: List<Spell>
    private lateinit var lightspells: List<Spell>
    private lateinit var currentspelllist: List<Spell>
    private lateinit var spellList:MutableList<Spell>
    private var templist=mutableListOf<Spell>()
    private var adapterspelllist=mutableListOf<Spell>()
    private val eraselist: MutableList<Spell> = mutableListOf()
    private val favspelllist: MutableList<String> = mutableListOf()
    private lateinit var favSharedPreferences: SharedPreferences
    private  var trimEntext=""
    private var darkchecked=true
    private var lightchecked=true
    private var favchecked=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForcecastingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar=findViewById(R.id.force_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        favSharedPreferences=getSharedPreferences("favlist", Context.MODE_PRIVATE)
        levels= listOf("0","1","2","3","4","5","6","7","8","9","empty")
        favspelllist.addAll(favSharedPreferences.getStringSet("favlist", mutableSetOf())?.toList()!!)
        spellList=getSpells()


        reclview = findViewById(R.id.reclview)
        adapterspelllist= mutableListOf()
        adapterspelllist.addAll(spellList)
        spelladapter = SpellAdapter(this,adapterspelllist,favspelllist)
        reclview.adapter = spelladapter
        currentspelllist = spellList
        lightspells=spellList.filter { it.side.contains("Light",true)}
        darkspells=spellList.filter { it.side.contains("Dark",true)}


        searchView = findViewById(R.id.searchview)
        searchView.isIconifiedByDefault=false
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                if(enttext.isNullOrBlank()){
                    spelladapter.setSpellList(currentspelllist.filter{it !in eraselist}.toMutableList())
                }
                else {
                    trimEntext= enttext.trim().replace(" ","_")
                    if(spellList.getNameList().none { it.contains(trimEntext,true) }){
                        spelladapter.setSpellList(mutableListOf())
                        Toast.makeText(this@ForceCastingActivity,"No such Spell found",Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        spelladapter.setSpellList(currentspelllist.filter{(it.spellname.contains(trimEntext,true)) and (it !in eraselist)}.toMutableList())

                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                if(enttext.isNullOrBlank()){
                    spelladapter.setSpellList(currentspelllist.filter{it !in eraselist}.toMutableList())
                }
                else {
                    trimEntext= enttext.trim().replace(" ","_")
                    if(spellList.getNameList().none { it.contains(trimEntext,true) }){
                        spelladapter.setSpellList(mutableListOf())
                        Toast.makeText(this@ForceCastingActivity,"No such Spell found",Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        spelladapter.setSpellList(currentspelllist.filter{(it.spellname.contains(trimEntext,true)) and (it !in eraselist)}.toMutableList())

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
                returntomain(null)
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
                spelladapter.setSpellList(spellList.sortSpellByNameDescending().filter { it !in eraselist }.toMutableList())
                currentspelllist=templist
                item.title=getText(R.string.sortABCup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortABCup)->{
                spelladapter.setSpellList(spellList.sortSpellByLevel().filter { it !in eraselist }.toMutableList())
                currentspelllist=templist
                item.title = getText(R.string.sortLvldown)
                returntotop(reclview,"sharp")}
            getText(R.string.sortLvldown)->{
                spelladapter.setSpellList(spellList.sortSpellByLevelDescending().filter { it !in eraselist }.toMutableList())
                currentspelllist=templist
                item.title = getText(R.string.sortLvlup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortLvlup)->{
                spelladapter.setSpellList(spellList.sortSpellByName().filter { it !in eraselist }.toMutableList())
                currentspelllist=templist
                item.title = getText(R.string.sortABCdown)
                returntotop(reclview,"sharp")}
            getText(R.string.Dark)->{
                if(item.isChecked){eraselist.addAll(darkspells)}
                else{
                    if(favchecked){eraselist.removeAll(spellList.filter { (it in darkspells) and (it.spellname in favspelllist) and if(trimEntext.isNotBlank()){it.spellname.contains(trimEntext)}else{true}})}
                    else{if(trimEntext.isNotBlank()){spellList.filter { (it in darkspells) and it.spellname.contains(trimEntext)}}else{eraselist.removeAll(darkspells)}}
                }
                spelladapter.setSpellList(currentspelllist.filter{it !in eraselist}.toMutableList())
                item.isChecked= !item.isChecked
                darkchecked= !darkchecked
            }
            getText(R.string.Light)->{
                if(item.isChecked){eraselist.addAll(lightspells)}
                else{
                    if(favchecked){eraselist.removeAll(spellList.filter { (it in lightspells) and (it.spellname in favspelllist) and if(trimEntext.isNotBlank()){it.spellname.contains(trimEntext)}else{true}})}
                    else{if(trimEntext.isNotBlank()){spellList.filter { (it in lightspells) and it.spellname.contains(trimEntext)}}else{eraselist.removeAll(lightspells)}}
                }
                spelladapter.setSpellList(currentspelllist.filter{it !in eraselist}.toMutableList())
                item.isChecked= !item.isChecked
                lightchecked= !lightchecked
            }
            getText(R.string.favorites_forcepowers)->{
                if (item.isChecked){
                    eraselist.removeAll(spellList.filter {(it.spellname !in favspelllist)})
                    if(!darkchecked){eraselist.addAll(darkspells)}
                    if(!lightchecked){eraselist.addAll(lightspells)}
                }
                else{
                    eraselist.addAll(spellList.filter {it.spellname !in favspelllist})
                }
                spelladapter.setSpellList(currentspelllist.filter{it !in eraselist}.toMutableList())
                item.isChecked= !item.isChecked
                favchecked= !favchecked
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
    private fun returntomain(view: View?) {
        startActivity(Intent(this,SW5ECompanionApp::class.java))
        with(favSharedPreferences.edit()){
            putStringSet("favlist",favspelllist.toMutableSet())
            apply()
        }
        finish()
    }
    private fun getSpells(): MutableList<Spell>{
        val getSpellList = mutableListOf<Spell>()
        val tempSpellList=resources.getTextArray(R.array.testspelllist)
        for(i in 8..tempSpellList.size step 9){
            getSpellList.add(Spell(tempSpellList[i-8].toString(),tempSpellList[i-7],tempSpellList[i-6].toString(),tempSpellList[i-5],tempSpellList[i-4].toString().toInt(),tempSpellList[i-3].toString().toBoolean(),tempSpellList[i-2].toString().toBoolean(),tempSpellList[i-1].toString().toBoolean(),tempSpellList[i]))
        }
        return getSpellList
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}