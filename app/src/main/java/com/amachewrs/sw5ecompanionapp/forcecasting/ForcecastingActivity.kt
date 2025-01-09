package com.amachewrs.sw5ecompanionapp.forcecasting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.SW5ECompanionApp
import com.amachewrs.sw5ecompanionapp.databinding.ForcecastingBinding
import java.util.LinkedList

class ForcecastingActivity : AppCompatActivity() {
    private lateinit var binding: ForcecastingBinding
    private lateinit var forcepoweradapter: ForcecastingAdapter
    private lateinit var lightforcepowers: List<Forcepower>
    private lateinit var darkforcepowers: List<Forcepower>
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

    private var atInfo = false
    private lateinit var starjedi: Typeface
    private lateinit var scrolly : ScrollView
    private lateinit var inflater: LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForcecastingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        inflater=layoutInflater
        starjedi = resources.getFont(R.font.starjedi)

        favSharedPreferences=getSharedPreferences("forcecasting", Context.MODE_PRIVATE)
        favForcepowerList.addAll(favSharedPreferences.getStringSet("favorite_force_powers", mutableSetOf())?.toList()!!)

        forcepowerList.addAll(getForcepowers())
        adapterForcepowerList.addAll(forcepowerList)
        forcepoweradapter = ForcecastingAdapter(this,adapterForcepowerList,favForcepowerList)
        binding.reclview.adapter = forcepoweradapter
        currentforcepowerlist.addAll(forcepowerList)
        lightforcepowers=forcepowerList.filter { it.side.contains("Light",true)}
        darkforcepowers=forcepowerList.filter { it.side.contains("Dark",true)}

        binding.BackButton.setOnClickListener{returntomain()}

        binding.searchview.setIconifiedByDefault(false)
        binding.searchview.queryHint="Search..."
        binding.searchview.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop("sharp")
                if(enttext.isNullOrBlank()){
                    forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{it !in eraselist}.toMutableList())
                    object : CountDownTimer(1000, 1001) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(trimEnteredText.isBlank()){binding.searchview.clearFocus()}
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
                returntotop("sharp")
                if(enttext.isNullOrBlank()){
                    forcepoweradapter.setForcepowerList(currentforcepowerlist.filter{it !in eraselist}.toMutableList())
                    object : CountDownTimer(1000, 999) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(trimEnteredText.isBlank()){binding.searchview.clearFocus()}
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


        binding.floatingActionButton.setOnClickListener{returntotop("smooth")}

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
                returntotop("sharp")}
            getText(R.string.sortABCup)->{
                currentforcepowerlist=forcepowerList.sortForcepowerByLevel().filter { it !in eraselist }.toMutableList()
                forcepoweradapter.setForcepowerList(adapterForcepowerList.sortForcepowerByName().sortForcepowerByLevel().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortLvldown)
                returntotop("sharp")}
            getText(R.string.sortLvldown)->{
                currentforcepowerlist=forcepowerList.sortForcepowerByLevelDescending().filter { it !in eraselist }.toMutableList()
                forcepoweradapter.setForcepowerList(adapterForcepowerList.sortForcepowerByLevelDescending().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortLvlup)
                returntotop("sharp")}
            getText(R.string.sortLvlup)->{
                currentforcepowerlist=forcepowerList.sortForcepowerByName().filter { it !in eraselist }.toMutableList()
                forcepoweradapter.setForcepowerList(adapterForcepowerList.sortForcepowerByName().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortABCdown)
                returntotop("sharp")}
            getText(R.string.casting_info)->handleInfoSwitch()
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
    private fun handleInfoSwitch(){
        if(!atInfo){
            binding.coord.removeView(binding.reclview)
            binding.searchview.visibility = View.GONE
            generateInfo()
        }
        else {
            binding.coord.removeView(scrolly)
            binding.coord.addView(binding.reclview)
            binding.searchview.visibility = View.VISIBLE
            returntotop("sharp")
        }
        atInfo=!atInfo
    }
    private fun generateInfo(){
        val infoHeap = LinkedList(resources.getTextArray(R.array.casting_info).toMutableSet())
        scrolly = inflater.inflate(R.layout.universal_scrollview_with_linearlayout,binding.coord,true).findViewById(R.id.scrolly)
        val ll = scrolly.findViewById<LinearLayout>(R.id.ll)
        val txt = inflater.inflate(R.layout.universal_textview_starjedi_gold,ll,false)
        var temptxt = txt.findViewById<TextView>(R.id.textview)
        temptxt.text = infoHeap.poll()
        temptxt.typeface = starjedi

        ll.addView(txt)
        for (i in 2..18){
            if (i !in setOf(5,6,7)) {
                val tempview = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)

                tempview.findViewById<TextView>(R.id.headertext).text=infoHeap.poll()
                temptxt = tempview.findViewById(R.id.contenttext)
                temptxt.text=infoHeap.poll()

                if (i in setOf(8,11,13,14,15)) temptxt.typeface = starjedi
                ll.addView(tempview)
            }
            else{
                val tempview = inflater.inflate(R.layout.universal_textview_nofont_gold,ll,false)

                temptxt = tempview.findViewById(R.id.textview)
                temptxt.text = infoHeap.poll()
                if(i != 5) temptxt.typeface = starjedi

                ll.addView(tempview)

                if(i == 6) ll.addView(generateTable(infoHeap,inflater.inflate(R.layout.two_column_table,ll,false).findViewById(R.id.table)))
            }
        }
    }
    private fun generateTable(infoHeap: LinkedList<CharSequence>,table: TableLayout) :TableLayout{
        table.findViewById<TextView>(R.id.title_1).text=infoHeap.poll()
        table.findViewById<TextView>(R.id.title_2).text=infoHeap.poll()
        for (i in 0..9){
            val extraRow = inflater.inflate(R.layout.two_column_table_extra_row_gold,table,false)
            extraRow.findViewById<TextView>(R.id.extra_row_1).text=i.toString()
            if (i==0) extraRow.findViewById<TextView>(R.id.extra_row_2).text="0"
            else {
                val temp = i + 1
                extraRow.findViewById<TextView>(R.id.extra_row_2).text = temp.toString()
            }
            if (i%2==1) extraRow.background=null
            table.addView(extraRow)
        }
        return table
    }
    private fun getForcepowers(): MutableList<Forcepower>{
        val getForcepowerList = mutableListOf<Forcepower>()
        val tempForcepowerList=resources.getTextArray(R.array.forcepowerlist)
        for(i in 8..tempForcepowerList.size step 9){
            getForcepowerList.add(Forcepower(tempForcepowerList[i-8].toString(),tempForcepowerList[i-7],tempForcepowerList[i-6].toString(),tempForcepowerList[i-5],tempForcepowerList[i-4].toString().toInt(),tempForcepowerList[i-3].toString().toBoolean(),tempForcepowerList[i-2].toString().toBoolean(),tempForcepowerList[i-1].toString().toBoolean(),tempForcepowerList[i]))
        }
        return getForcepowerList
    }
    private fun returntotop(mode: String){
        when(mode){
            "smooth"->binding.reclview.smoothScrollToPosition(0)
            "sharp"->binding.reclview.scrollToPosition(0)
        }

    }
    fun returntomain() {
        if (!atInfo) {
            startActivity(Intent(this, SW5ECompanionApp::class.java))
            with(favSharedPreferences.edit()){
                putStringSet("favorite_force_powers",favForcepowerList.toMutableSet())
                apply()
            }
            finish()
        }
        else handleInfoSwitch()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}