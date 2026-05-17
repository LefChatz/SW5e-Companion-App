package com.amachewrs.sw5ecompanionapp.species

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.edit
import androidx.core.content.res.getIntOrThrow
import androidx.core.content.res.getStringOrThrow
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.core.view.isEmpty
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesBinding
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.Specie
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.SpeciesAdapter
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.getNameList
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.sortSpecieByName
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.sortSpecieByNameDescending
import java.util.LinkedList
import kotlin.math.max

class SpeciesActivity : AppCompatActivity() {

    private lateinit var binding: SpeciesBinding
    private lateinit var speciesAdapter: SpeciesAdapter
    private lateinit var speciesPrefs: SharedPreferences
    private lateinit var speciesMenu:Menu

    private var speciesList = mutableListOf<Specie>()
    private val favSpecieList = mutableListOf<String>()
    private lateinit var tempView: View
    private lateinit var tempTxt: TextView
    private lateinit var txt: TextView

    private var favChecked = false
    private var searchedText=""
    private var atInfo = false
    private var ecChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SpeciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            binding.bottomNavigationView.updatePadding(0,0,0,
                max(windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom,windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom)
            )
            WindowInsetsCompat.CONSUMED }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        speciesPrefs = getSharedPreferences("species", MODE_PRIVATE)
        favSpecieList.addAll(speciesPrefs.getStringSet("favSpecieList", mutableSetOf())!!.toMutableList())
        
        speciesList = getSpecies()
        speciesAdapter = SpeciesAdapter(this,speciesList, favSpecieList)
        binding.reclview.adapter=speciesAdapter
        
        binding.BackButton.setOnClickListener { returnToMain() }
        
        binding.floatingActionButton.setOnClickListener { returnToTop("smooth") }
        
        onBackPressedDispatcher.addCallback(this,object:OnBackPressedCallback(true){override fun handleOnBackPressed(){returnToMain()}})

        binding.searchview.setIconifiedByDefault(false)
        binding.searchview.queryHint="Search..."
        binding.searchview.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returnToTop("sharp")
                if(enttext.isNullOrBlank()){
                    object : CountDownTimer(1000, 1001) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(searchedText.isBlank()){binding.searchview.clearFocus()}
                        }
                    }.start()
                    searchedText=""
                    updateAdapterList()
                }
                else {
                    searchedText= enttext.trim().replace(" ","_")
                    if(speciesList.getNameList().none { it.contains(searchedText,true)}){
                        speciesAdapter.setSpeciesList(mutableListOf(Specie("noSpecie")))
                    }
                    else {
                        updateAdapterList()
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                returnToTop("sharp")
                if(enttext.isNullOrBlank()){
                    object : CountDownTimer(1000, 1001) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(searchedText.isBlank()){binding.searchview.clearFocus()}
                        }
                    }.start()
                    searchedText=""
                    updateAdapterList()
                }
                else {
                    searchedText= enttext.trim().replace(" ","_")
                    if(speciesList.getNameList().none { it.contains(searchedText,true)}){
                        speciesAdapter.setSpeciesList(mutableListOf(Specie("noSpecie")))
                    }
                    else {
                        updateAdapterList()
                    }
                }
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_species,menu)
        if (menu != null) {
            speciesMenu = menu
        }
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.downarrowgold)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            getText(R.string.sortABCdown)->{
                speciesList=speciesList.sortSpecieByNameDescending()
                item.title=getText(R.string.sortABCup)
                returnToTop("sharp")}
            getText(R.string.sortABCup)->{
                speciesList=speciesList.sortSpecieByName()
                item.title = getText(R.string.sortABCdown)
                returnToTop("sharp")}
            getText(R.string.species_info)->handleInfoSwitch()
            getText(R.string.ec)-> {
                ecChecked = !ecChecked
                item.isChecked = !item.isChecked
            }
            getText(R.string.back_gold) ->handleInfoSwitch()
            getText(R.string.favorites_gold)->{
                favChecked= !favChecked
                item.isChecked= !item.isChecked
            }
        }
        updateAdapterList()
        return super.onOptionsItemSelected(item)
    }

    private fun handleInfoSwitch(){
        if(!atInfo){
            binding.reclview.visibility = View.GONE
            binding.bottomNavigationView.visibility = View.GONE
            binding.floatingActionButton.visibility = View.GONE
            binding.scrolly.visibility = View.VISIBLE
            speciesMenu.forEach { if(it.order!=2)it.isVisible=false else it.title = resources.getText(R.string.back_gold) }
            binding.scrolly.scrollTo(0,0)
            if (binding.ll.isEmpty()) generateInfo()
        }
        else {
            binding.scrolly.visibility = View.GONE
            binding.reclview.visibility = View.VISIBLE
            binding.bottomNavigationView.visibility = View.VISIBLE
            binding.floatingActionButton.visibility = View.VISIBLE
            speciesMenu.forEach { if(it.order!=2)it.isVisible=true else it.title = resources.getText(R.string.species_info) }
        }
        returnToTop("sharp")
        atInfo=!atInfo
    }

    private fun generateInfo(){

        txt = layoutInflater.inflate(R.layout.universal_textview_starjedi_gold,binding.scrolly,false).findViewById(R.id.textview)
        txt.text=getText(R.string.species_info1)
        binding.ll.addView(txt)

        tempView=layoutInflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.scrolly,false)
        tempView.findViewById<TextView>(R.id.headertext).text=getText(R.string.species_info2Header)
        tempView.findViewById<TextView>(R.id.contenttext).text=getText(R.string.species_info2Text)
        binding.ll.addView(tempView)

        tempView=layoutInflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.scrolly,false)
        tempView.findViewById<TextView>(R.id.headertext).text=getText(R.string.species_info3Header)
        tempTxt=tempView.findViewById(R.id.contenttext)
        tempTxt.text=getText(R.string.species_info3Text)
        tempTxt.typeface=resources.getFont(R.font.starjedi)
        binding.ll.addView(tempView)
    }

    private fun updateAdapterList() {
        speciesAdapter.setSpeciesList(speciesList.filter { filterSpecies(it) })
    }

    private fun filterSpecies(specie: Specie): Boolean{
        if (favChecked && specie.name !in favSpecieList) return false
        if (searchedText.isNotEmpty() && !specie.name.contains(searchedText,false)) return false
        if (ecChecked && specie.expansion == "EC") return false

        return true
    }

    private fun returnToTop(mode: String){
        when(mode){
            "smooth"->binding.reclview.smoothScrollToPosition(0)
            "sharp"->binding.reclview.scrollToPosition(0)
        }
    }

    private fun returnToMain(){
        if(!atInfo) {
            speciesPrefs.edit { putStringSet("favSpecieList", favSpecieList.toSet()) }
            finish()
        }
        else handleInfoSwitch()
    }

    private fun getSpecies(): MutableList<Specie>{
        val getSpeciesList = mutableListOf<Specie>()
        val speciesArray = resources.obtainTypedArray(R.array.species_list)
        for (i in 7..speciesArray.length() step 8){
            getSpeciesList.add(
                Specie(
                    speciesArray.getStringOrThrow(i-7),
                    speciesArray.getText(i - 6),
                    speciesArray.getStringOrThrow(i - 5),
                    speciesArray.getIntOrThrow(i - 4),
                    LinkedList(speciesArray.getText(i-3).split("|")),
                    speciesArray.getText(i - 2),
                    speciesArray.getStringOrThrow(i-1),
                    speciesArray.getStringOrThrow(i)
                )
            )
        }
        speciesArray.recycle()
        return getSpeciesList
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}