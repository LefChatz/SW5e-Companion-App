package com.amachewrs.sw5ecompanionapp.techcasting

import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.TechcastingBinding
import java.util.LinkedList

class TechcastingActivity : AppCompatActivity() {
    private lateinit var binding: TechcastingBinding
    private lateinit var techpoweradapter: TechcastingAdapter
    private var techpowerList=mutableListOf<Techpower>()
    private var adapterTechpowerList=mutableListOf<Techpower>()
    private var currenttechpowerlist=mutableListOf<Techpower>()
    private val eraselist: MutableList<Techpower> = mutableListOf()

    private val favTechpowerList: MutableList<String> = mutableListOf()
    private lateinit var favSharedPreferences: SharedPreferences
    private var searchedText=""
    private var favChecked=false

    private var atInfo = false
    private var infoGenerated = false
    private lateinit var techmenu: Menu
    private lateinit var starjedi: Typeface
    private lateinit var inflater: LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TechcastingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            binding.bottomNavigationView.updatePadding(0,0,0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom)
            WindowInsetsCompat.CONSUMED
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        inflater = layoutInflater
        starjedi = resources.getFont(R.font.starjedi)

        favSharedPreferences=getSharedPreferences("techcasting", MODE_PRIVATE)
        favTechpowerList.addAll(favSharedPreferences.getStringSet("favorite_tech_powers", mutableSetOf())?.toList()!!)

        techpowerList.addAll(getTechpowers())

        adapterTechpowerList.addAll(techpowerList.sortTechpowerByName())
        techpoweradapter = TechcastingAdapter(this,adapterTechpowerList,favTechpowerList)
        binding.reclview.adapter = techpoweradapter
        currenttechpowerlist.addAll(techpowerList.sortTechpowerByName())

        binding.BackButton.setOnClickListener{returntomain()}

        binding.searchview.setIconifiedByDefault(false)
        binding.searchview.queryHint="Search..."
        binding.searchview.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(enttext: String?): Boolean {
                returntotop("sharp")
                if(enttext.isNullOrBlank()){
                    techpoweradapter.setTechpowerList(currenttechpowerlist.filter{it !in eraselist}.toMutableList())
                    object : CountDownTimer(1000, 1001) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(searchedText.isBlank()){binding.searchview.clearFocus()}
                        }
                    }.start()
                    searchedText=""
                }
                else {
                    searchedText= enttext.trim().replace(" ","_")
                    if(techpowerList.getNameList().none { it.contains(searchedText,true)}){
                        techpoweradapter.setTechpowerList(mutableListOf(Techpower("NoSuchTechpower")))
                    }
                    else {
                        techpoweradapter.setTechpowerList(currenttechpowerlist.filter{(it.techpowername.contains(searchedText,true)) and (it !in eraselist)}.toMutableList())
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(enttext: String?): Boolean {
                returntotop("sharp")
                if(enttext.isNullOrBlank()){
                    techpoweradapter.setTechpowerList(currenttechpowerlist.filter{it !in eraselist}.toMutableList())
                    object : CountDownTimer(1000, 999) {
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            if(searchedText.isBlank()){binding.searchview.clearFocus()}
                        }
                    }.start()
                    searchedText=""
                }
                else {
                    searchedText= enttext.trim().replace(" ","_")
                    if(techpowerList.getNameList().none { it.contains(searchedText,true)}){
                        techpoweradapter.setTechpowerList(mutableListOf(Techpower("NoSuchTechpower")))
                    }
                    else {
                        techpoweradapter.setTechpowerList(currenttechpowerlist.filter{(it.techpowername.contains(searchedText,true)) and (it !in eraselist)}.toMutableList())
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
        menuInflater.inflate(R.menu.menu_techcasting,menu)
        if (menu != null){
            techmenu = menu
        }
        binding.toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.downarrowgold)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            getText(R.string.sortABCdown)->{
                currenttechpowerlist=techpowerList.sortTechpowerByNameDescending()
                techpoweradapter.setTechpowerList(adapterTechpowerList.sortTechpowerByNameDescending().filter { it !in eraselist }.toMutableList())
                item.title=getText(R.string.sortABCup)
                returntotop("sharp")}
            getText(R.string.sortABCup)->{
                currenttechpowerlist=techpowerList.sortTechpowerByLevel()
                techpoweradapter.setTechpowerList(adapterTechpowerList.sortTechpowerByName().sortTechpowerByLevel().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortLvldown)
                returntotop("sharp")}
            getText(R.string.sortLvldown)->{
                currenttechpowerlist=techpowerList.sortTechpowerByLevelDescending()
                techpoweradapter.setTechpowerList(adapterTechpowerList.sortTechpowerByLevelDescending().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortLvlup)
                returntotop("sharp")}
            getText(R.string.sortLvlup)->{
                currenttechpowerlist=techpowerList.sortTechpowerByName()
                techpoweradapter.setTechpowerList(adapterTechpowerList.sortTechpowerByName().filter { it !in eraselist }.toMutableList())
                item.title = getText(R.string.sortABCdown)
                returntotop("sharp")}
            getText(R.string.casting_info) ->handleInfoSwitch()
            getText(R.string.back_gold) ->handleInfoSwitch()
            getText(R.string.favorites_gold)->{
                if (item.isChecked) eraselist.removeAll{(it.techpowername !in favTechpowerList)}
                else eraselist.addAll(techpowerList.filter {it.techpowername !in favTechpowerList})

                techpoweradapter.setTechpowerList(currenttechpowerlist.filter{(it !in eraselist) and if(searchedText.isNotBlank()){it.techpowername.contains(searchedText,true)}else{true}}.toMutableList())
                item.isChecked= !item.isChecked
                favChecked= !favChecked
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun handleInfoSwitch(){
        if(!atInfo){
            binding.reclview.visibility = View.GONE
            binding.bottomNavigationView.visibility = View.GONE
            binding.floatingActionButton.visibility = View.GONE
            binding.scrolly.visibility = View.VISIBLE
            if (!infoGenerated) generateInfo()
            binding.scrolly.scrollTo(0,0)
            techmenu.forEach { if(it.order!=2)it.isVisible=false else it.title = resources.getText(R.string.back_gold) }
        }
        else {
            binding.scrolly.visibility = View.GONE
            binding.reclview.visibility = View.VISIBLE
            binding.bottomNavigationView.visibility = View.VISIBLE
            binding.floatingActionButton.visibility = View.VISIBLE
            techmenu.forEach { if(it.order!=2)it.isVisible=true else it.title = resources.getText(R.string.casting_info) }
            returntotop("sharp")
        }
        atInfo=!atInfo
    }
    private fun generateInfo(){
        val infoHeap = LinkedList(resources.getTextArray(R.array.casting_info).toMutableSet())

        val ll = binding.ll
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
        infoGenerated = true
    }
    private fun generateTable(infoHeap: LinkedList<CharSequence>, table: TableLayout) : TableLayout {
        table.findViewById<TextView>(R.id.title_1).text=infoHeap.poll()
        table.findViewById<TextView>(R.id.title_2).text=infoHeap.poll()
        for (i in 0..9){
            val extraRow = inflater.inflate(R.layout.two_column_table_extra_row_gold,table,false)
            extraRow.findViewById<TextView>(R.id.extra_row_1).text= "$i"
            if (i==0) extraRow.findViewById<TextView>(R.id.extra_row_2).text="0"
            else {
                val temp = i + 1
                extraRow.findViewById<TextView>(R.id.extra_row_2).text = "$temp"
            }
            if (i%2==1) extraRow.background=null
            table.addView(extraRow)
        }
        return table
    }

    private fun returntotop(mode: String){
        when(mode){
            "smooth"->binding.reclview.smoothScrollToPosition(0)
            "sharp"->binding.reclview.scrollToPosition(0)
        }
    }
    fun returntomain() {
        if (!atInfo) {
            favSharedPreferences.edit {
                putStringSet("favorite_tech_powers", favTechpowerList.toMutableSet())
            }
            finish()
        }
        else handleInfoSwitch()
    }
    private fun getTechpowers(): MutableList<Techpower>{
        val getTechpowerList = mutableListOf<Techpower>()
        val techpowerTextArray=resources.getTextArray(R.array.techpowerlist)

        val starPaint = Paint()
        starPaint.textSize = 24F
        starPaint.typeface = starjedi
        starPaint.letterSpacing = 0.1F

        for(i in 9..techpowerTextArray.size step 10){
            val isbig = starPaint.measureText(techpowerTextArray[i-9] as String?) > (windowManager.currentWindowMetrics.bounds.width() - 705)
            getTechpowerList.add(Techpower(techpowerTextArray[i-9].toString(),techpowerTextArray[i-8],techpowerTextArray[i-7].toString(),techpowerTextArray[i-6].toString().toInt(),techpowerTextArray[i-5].toString(),techpowerTextArray[i-4].toString(),techpowerTextArray[i-3].toString().toBoolean(),techpowerTextArray[i-2].toString(),isbig,techpowerTextArray[i]))

        }
        return getTechpowerList
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}