package com.amachewrs.sw5ecompanionapp.classes

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.contains
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.ClassScholarBinding
import kotlin.math.absoluteValue

class ScholarActivity : AppCompatActivity() , GestureDetector.OnGestureListener{
    private lateinit var tempView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private lateinit var temptxt: TextView
    private lateinit var table: View
    private lateinit var hscroll: HorizontalScrollView
    private lateinit var sideEffectsTable: View
    private lateinit var sideEffectsHscroll: HorizontalScrollView
    private lateinit var binding: ClassScholarBinding

    private lateinit var infoList: List<CharSequence>
    private lateinit var baseList: List<CharSequence>
    private lateinit var gamblerList: List<CharSequence>
    private lateinit var physicianList: List<CharSequence>
    private lateinit var politicianList: List<CharSequence>
    private lateinit var tacticianList: List<CharSequence>
    private lateinit var discoveriesList: List<CharSequence>
    private val tabList = listOf("Info","Base","Tables","Discoveries","Gambler Pursuit","Physician Pursuit","Politician Pursuit","Tactician Pursuit")

    private lateinit var tempParams: LinearLayout.LayoutParams
    private lateinit var gestdect: GestureDetector
    private var rect = Rect()
    private var rect2 = Rect()
    private val swipeThreshold = 100
    private var scrollMode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ClassScholarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.dummybutton.setOnClickListener { this.openOptionsMenu() }

        binding.BackButton.setOnClickListener { returntomain() }

        gestdect = GestureDetector(this,this)

        infoList = resources.getTextArray(R.array.scholarInfo).toList()
        baseList = resources.getTextArray(R.array.scholarBase).toList()
        gamblerList = resources.getTextArray(R.array.gambler_pursuit).toList()
        physicianList = resources.getTextArray(R.array.physician_pursuit).toList()
        politicianList = resources.getTextArray(R.array.politician_pursuit).toList()
        tacticianList = resources.getTextArray(R.array.tactician_pursuit).toList()
        discoveriesList = resources.getTextArray(R.array.discoveries).toList()

        ll = binding.ll
        inflater = layoutInflater

        txt = inflater.inflate(R.layout.universal_textview_starjedi_gold,ll,false).findViewById(R.id.textview)

        for(i in infoList.indices step 2 ){
            if(i==0){
                txt.text=infoList[i]
                ll.addView(txt)
            }
            else{
                tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempView.findViewById(R.id.contenttext)
                tempView.findViewById<TextView>(R.id.headertext).text=infoList[i-1]
                temptxt.text=infoList[i]
                if(i==6){
                    temptxt.typeface = resources.getFont(R.font.starjedi)
                }
                ll.addView(tempView)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scholar,menu)
        val drawable = AppCompatResources.getDrawable(this,R.drawable.downarrowgold)
        binding.toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.dummybutton.text=item.title
        generateView()
        return super.onOptionsItemSelected(item)
    }

    private fun generateView(){
        ll.removeAllViews()
        binding.scrolly.scrollTo(0,0)
        when(binding.dummybutton.text.toString()){
            "Info"->{
                for(i in infoList.indices step 2 ){
                    if(i==0){
                        txt.text=infoList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=infoList[i-1]
                        temptxt.text=infoList[i]
                        if(i==6){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                    }
                }
            }
            "Base"->{
                for(i in baseList.indices step 2 ){
                    if(i==0){
                        txt.text=baseList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=baseList[i-1]
                        temptxt.text=baseList[i]
                        if(i==2){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                    }
                }
            }
            "Tables"->{
                table=inflater.inflate(R.layout.class_scholar_table,ll,true)
                hscroll=table.findViewById(R.id.scholartablehscroll)
                sideEffectsTable=inflater.inflate(R.layout.class_scholar_physician_table,ll,true)
                sideEffectsHscroll=sideEffectsTable.findViewById(R.id.scholartablehscrollsidefs)
            }
            "Discoveries"->{
                for(i in discoveriesList.indices step 2){
                    tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                    temptxt=tempView.findViewById(R.id.contenttext)
                    tempView.findViewById<TextView>(R.id.headertext).text=discoveriesList[i]
                    temptxt.text=discoveriesList[i+1]
                    temptxt.typeface = resources.getFont(R.font.starjedi)
                    if(i!=0){
                        tempParams = tempView.layoutParams as LinearLayout.LayoutParams
                        tempParams.topMargin = 70
                        tempView.layoutParams = tempParams
                    }
                    ll.addView(tempView)
                }
            }
            "Gambler Pursuit"->{
                for(i in gamblerList.indices step 2 ){
                    if(i==0){
                        txt.text=gamblerList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=gamblerList[i-1]
                        temptxt.text=gamblerList[i]
                        ll.addView(tempView)
                        if(i==gamblerList.lastIndex){
                            tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                            temptxt=tempView.findViewById(R.id.contenttext)
                            tempView.findViewById<TextView>(R.id.headertext).text=discoveriesList[2]
                            temptxt.text=discoveriesList[3]
                            temptxt.typeface = resources.getFont(R.font.starjedi)

                            tempParams = tempView.layoutParams as LinearLayout.LayoutParams
                            tempParams.topMargin = 200
                            tempView.layoutParams = tempParams
                            ll.addView(tempView)
                        }

                    }
                }

            }
            "Physician Pursuit"->{
                for(i in physicianList.indices step 2 ){
                    if(i==0){
                        txt.text=physicianList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=physicianList[i-1]
                        temptxt.text=physicianList[i]
                        ll.addView(tempView)
                        if(i==physicianList.lastIndex){
                            tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                            temptxt=tempView.findViewById(R.id.contenttext)
                            tempView.findViewById<TextView>(R.id.headertext).text=discoveriesList[4]
                            temptxt.text=discoveriesList[5]
                            temptxt.typeface = resources.getFont(R.font.starjedi)

                            tempParams = tempView.layoutParams as LinearLayout.LayoutParams
                            tempParams.topMargin = 200
                            tempView.layoutParams = tempParams
                            ll.addView(tempView)
                            inflater.inflate(R.layout.class_scholar_physician_table,ll,true)
                        }
                    }
                }
            }
            "Politician Pursuit"->{
                for(i in politicianList.indices step 2 ){
                    if(i==0){
                        txt.text=politicianList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=politicianList[i-1]
                        temptxt.text=politicianList[i]
                        ll.addView(tempView)
                        if(i==politicianList.lastIndex){
                            tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                            temptxt=tempView.findViewById(R.id.contenttext)
                            tempView.findViewById<TextView>(R.id.headertext).text=discoveriesList[6]
                            temptxt.text=discoveriesList[7]
                            temptxt.typeface = resources.getFont(R.font.starjedi)

                            tempParams = tempView.layoutParams as LinearLayout.LayoutParams
                            tempParams.topMargin = 200
                            tempView.layoutParams = tempParams
                            ll.addView(tempView)
                        }
                    }
                }
            }
            "Tactician Pursuit"->{
                for(i in tacticianList.indices step 2 ){
                    if(i==0){
                        txt.text=tacticianList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=tacticianList[i-1]
                        temptxt.text=tacticianList[i]
                        ll.addView(tempView)
                        if(i==tacticianList.lastIndex){
                            tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                            temptxt=tempView.findViewById(R.id.contenttext)
                            tempView.findViewById<TextView>(R.id.headertext).text=discoveriesList[8]
                            temptxt.text=discoveriesList[9]
                            temptxt.typeface = resources.getFont(R.font.starjedi)

                            tempParams = tempView.layoutParams as LinearLayout.LayoutParams
                            tempParams.topMargin = 200
                            tempView.layoutParams = tempParams
                            ll.addView(tempView)
                        }

                    }
                }

            }
            else->{
                Toast.makeText(this,"Error",Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun swipeView(dir: String){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        ll.removeAllViews()
        when(dir){
            "RtoL"->{
                binding.dummybutton.text=tabList[tabList.indexOf(binding.dummybutton.text.toString())+1]
                generateView()
                if(binding.dummybutton.text==tabList.last()){
                    scrollMode=1
                }
                else{
                    if(scrollMode!=2){scrollMode=2}
                }
            }
            "LtoR"->{
                binding.dummybutton.text=tabList[tabList.indexOf(binding.dummybutton.text.toString())-1]
                generateView()
                if(binding.dummybutton.text==tabList.first()){
                    scrollMode=0
                }
                else{
                    if(scrollMode!=2){scrollMode=2}
                }
            }
        }

    }

    private fun returntomain(){
        finish()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        super.dispatchTouchEvent(ev)
        return gestdect.onTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestdect.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(p0: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent) {
        return
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
        return
    }

    override fun onFling(e0: MotionEvent?, e1: MotionEvent, vx: Float, vy: Float): Boolean {
        if (e0 != null) {
            val diffX = e1.x - e0.x
            if(diffX.absoluteValue>(e1.y-e0.y).absoluteValue) {
                if(this::hscroll.isInitialized){
                    hscroll.getGlobalVisibleRect(rect)
                    sideEffectsHscroll.getGlobalVisibleRect(rect2)
                    if((rect.contains(e0.x.toInt(),e0.y.toInt()) or rect2.contains(e0.x.toInt(),e0.y.toInt())) and ll.contains(hscroll)) return false
                }
                if (diffX.absoluteValue > swipeThreshold && vx.absoluteValue > swipeThreshold) {
                    //L to R
                    if (diffX>0 && scrollMode!=0) swipeView("LtoR")
                    //R to L
                    else if(diffX<0 && scrollMode!=1) swipeView("RtoL")

                    return true
                }
            }
        }
        return false
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}