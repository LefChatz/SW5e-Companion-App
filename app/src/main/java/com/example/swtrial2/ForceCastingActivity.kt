package com.example.swtrial2
//
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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.swtrial2.databinding.ActivityForcecastingBinding
import com.example.swtrial2.spells.adapterstuff.SpellAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ForceCastingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForcecastingBinding
    private lateinit var reclview: RecyclerView
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var searchView: SearchView
    private lateinit var spelladapter: SpellAdapter
    private lateinit var adapterspelllist: MutableList<String>
    private lateinit var bigspellnames: List<String>
    lateinit var levels: List<String>
    lateinit var spelllist: List<String>
    private lateinit var lvledspelllist: List<String>
    private lateinit var lvledspelllistrev: List<String>
    private lateinit var darkspells: List<String>
    private lateinit var lightspells: List<String>
    lateinit var currentspelllist: List<String>
    lateinit var templist: List<String>
    val eraselist: MutableList<String> = mutableListOf()
    private val favspelllist: MutableList<String> = mutableListOf()
    private lateinit var favSharedPreferences: SharedPreferences
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

        bigspellnames=resources.getStringArray(R.array.bigspellnames).toList()
        levels=resources.getStringArray(R.array.levels).toList()
        spelllist=resources.getStringArray(R.array.spelllist).toList()
        lvledspelllist=resources.getStringArray(R.array.leveledspelllist).toList()
        lvledspelllistrev=resources.getStringArray(R.array.leveledspelllistrev).toList()
        darkspells=resources.getStringArray(R.array.darkspells).toList()
        lightspells=resources.getStringArray(R.array.lightspells).toList()
        favspelllist.addAll(favSharedPreferences.getStringSet("favlist", mutableSetOf())?.toList()!!)

        reclview= findViewById(R.id.reclview)
        adapterspelllist= mutableListOf()
        adapterspelllist.addAll(spelllist)
        spelladapter = SpellAdapter(this,adapterspelllist,bigspellnames,favspelllist)
        reclview.adapter = spelladapter
        currentspelllist=spelllist

        searchView = findViewById(R.id.searchview)
        searchView.isIconifiedByDefault=false
        searchView.queryHint="Search..."
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                if(enttext.isNullOrBlank()){
                    spelladapter.setSpellList(currentspelllist.filter{it !in eraselist})
                }
                else {
                    if(spelllist.none { it.contains(enttext.trim()) }){
                        spelladapter.setSpellList(listOf())
                        Toast.makeText(this@ForceCastingActivity,"No such Spell found",Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        templist = currentspelllist.filter{(it.contains(enttext.trim()) or (it in levels)) and (it !in eraselist)}
                        spelladapter.setSpellList(templist.filter {((it in levels) and (templist[templist.indexOf(it)+1] !in levels)) or (it == "empty") or (it !in levels)})

                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                if(enttext.isNullOrBlank()){
                    spelladapter.setSpellList(currentspelllist.filter{it !in eraselist})
                }
                else {
                    if(spelllist.none { it.contains(enttext.trim()) }){
                        spelladapter.setSpellList(listOf())
                        Toast.makeText(this@ForceCastingActivity,"No such Spell found",Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        templist = currentspelllist.filter{(it.contains(enttext.trim()) or (it in levels)) and (it !in eraselist)}
                        spelladapter.setSpellList(templist.filter {((it in levels) and (templist[templist.indexOf(it)+1] !in levels)) or (it == "empty") or (it !in levels)})

                    }
                }
                return false
            }
        })
        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener{
            returntotop(reclview,"smooth")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_forcecasting,menu)
        toolbar.overflowIcon = AppCompatResources.getDrawable(this,R.drawable.downarrowgold)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            getText(R.string.sortABCdown)->{
                spelladapter.setSpellList(spelllist.reversed().filter{it !in eraselist})
                currentspelllist=spelllist.reversed()
                item.title=getText(R.string.sortABCup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortABCup)->{
                templist=lvledspelllist.filter{it !in eraselist}
                spelladapter.setSpellList(templist.filter{((it in levels) and (templist[templist.indexOf(it)+1] !in levels)) or (it == "empty") or (it !in levels)})
                currentspelllist=lvledspelllist
                item.title = getText(R.string.sortLvldown)
                returntotop(reclview,"sharp")}
            getText(R.string.sortLvldown)->{
                templist=lvledspelllistrev.filter{it !in eraselist}
                spelladapter.setSpellList(templist.filter{((it in levels) and (templist[templist.indexOf(it)+1] !in levels)) or (it == "empty") or (it !in levels)})
                currentspelllist=lvledspelllistrev
                item.title = getText(R.string.sortLvlup)
                returntotop(reclview,"sharp")}
            getText(R.string.sortLvlup)->{
                spelladapter.setSpellList(spelllist.filter{it !in eraselist})
                currentspelllist=spelllist
                item.title = getText(R.string.sortABCdown)
                returntotop(reclview,"sharp")}
            getText(R.string.Dark)->{
                if(item.isChecked){eraselist.addAll(darkspells)}
                else{
                    if(favchecked){eraselist.removeAll(darkspells.filter {(it in favspelllist)})}
                    else{eraselist.removeAll(darkspells)}
                }
                templist = currentspelllist.filter{it !in eraselist}
                spelladapter.setSpellList(templist.filter {((it in levels) and (templist[templist.indexOf(it)+1] !in levels)) or (it == "empty") or (it !in levels)})
                item.isChecked= !item.isChecked
                darkchecked= !darkchecked
            }
            getText(R.string.Light)->{
                if(item.isChecked){eraselist.addAll(lightspells)}
                else{
                    if(favchecked){eraselist.removeAll(lightspells.filter {(it in favspelllist)})}
                    else{eraselist.removeAll(lightspells)}
                }
                templist = currentspelllist.filter{it !in eraselist}
                spelladapter.setSpellList(templist.filter {((it in levels) and (templist[templist.indexOf(it)+1] !in levels)) or (it == "empty") or (it !in levels)})
                item.isChecked= !item.isChecked
                lightchecked= !lightchecked
            }
            getText(R.string.favorites_forcepowers)->{
                if (item.isChecked){
                    eraselist.removeAll(spelllist.filter {(it !in favspelllist)})
                    if(!darkchecked){eraselist.addAll(darkspells)}
                    if(!lightchecked){eraselist.addAll(lightspells)}
                }
                else{
                    eraselist.addAll(spelllist.filter {(it !in favspelllist)&&(it !in levels)})
                }
                templist=currentspelllist.filter{it !in eraselist}
                spelladapter.setSpellList(templist.filter{((it in levels) and (templist[templist.indexOf(it)+1] !in levels)) or (it == "empty") or (it !in levels)})
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
    fun returntomain(view: View?) {
        startActivity(Intent(this,SW5ECompanionApp::class.java))
        with(favSharedPreferences.edit()){
            putStringSet("favlist",favspelllist.toMutableSet())
            apply()
        }
        finish()
    }
    /*fun addfavouritespell(spellbutton: View){
        if(spellbutton.contentDescription.toString() !in favspelllist){
            favspelllist.add(spellbutton.contentDescription.toString())
            spellbutton.background=AppCompatResources.getDrawable(this,R.drawable.favouritegoldtrue)
        }
        else{
            favspelllist.remove(spellbutton.contentDescription.toString())
            spellbutton.background=AppCompatResources.getDrawable(this,R.drawable.favouritegold)
        }

    }*/
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        returntomain(null)
        super.onBackPressed()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}