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
import com.amachewrs.sw5ecompanionapp.databinding.ClassBerserkerBinding
import kotlin.math.absoluteValue

class BerserkerActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private lateinit var tempView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private lateinit var temptxt: TextView
    private lateinit var table: View
    private lateinit var hscroll: HorizontalScrollView
    private lateinit var binding: ClassBerserkerBinding

    private lateinit var infoList: List<CharSequence>
    private lateinit var baseList: List<CharSequence>
    private lateinit var ballisticList: List<CharSequence>
    private lateinit var cycloneList: List<CharSequence>
    private lateinit var juggernautList: List<CharSequence>
    private lateinit var marauderList: List<CharSequence>
    private val tabList = listOf("Info","Base","Tables","Instincts","Ballistic Approach","Cyclone Approach","Juggernaut Approach","Marauder Approach")

    private lateinit var gestdect: GestureDetector
    private var rect = Rect()
    private val swipeThreshold = 100
    private var scrollMode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ClassBerserkerBinding.inflate(layoutInflater)
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

        infoList = resources.getTextArray(R.array.berserkerInfo).toList()
        baseList = resources.getTextArray(R.array.berserkerBase).toList()
        ballisticList = resources.getTextArray(R.array.ballistic_approach).toList()
        cycloneList = resources.getTextArray(R.array.cyclone_approach).toList()
        juggernautList = resources.getTextArray(R.array.juggernaut_approach).toList()
        marauderList = resources.getTextArray(R.array.marauder_approach).toList()

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
        menuInflater.inflate(R.menu.menu_berserk,menu)
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
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        ll.removeAllViews()
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
                        ll.addView(tempView)
                    }
                }
            }
            "Tables"->{
                table = inflater.inflate(R.layout.class_berserker_table,ll,true)
                hscroll=table.findViewById(R.id.berserkertablehscroll)
                inflater.inflate(R.layout.class_berserker_marauder_table,ll,true)
            }
            "Instincts"->{
                tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempView.findViewById(R.id.contenttext)
                tempView.findViewById<TextView>(R.id.headertext).text=getText(R.string.berserker_instinctsHeader)
                temptxt.text=getText(R.string.berserker_instinctstext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempView)
            }
            "Ballistic Approach"->{
                for(i in ballisticList.indices step 2 ){
                    if(i==0){
                        txt.text=ballisticList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=ballisticList[i-1]
                        temptxt.text=ballisticList[i]
                        ll.addView(tempView)
                    }
                }

            }
            "Cyclone Approach"->{
                for(i in cycloneList.indices step 2 ){
                    if(i==0){
                        txt.text=cycloneList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=cycloneList[i-1]
                        temptxt.text=cycloneList[i]
                        ll.addView(tempView)
                    }
                }
            }
            "Juggernaut Approach"->{
                for(i in juggernautList.indices step 2 ){
                    if(i==0){
                        txt.text=juggernautList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=juggernautList[i-1]
                        temptxt.text=juggernautList[i]
                        ll.addView(tempView)
                    }
                }
            }
            "Marauder Approach"->{
                for(i in marauderList.indices step 2 ){
                    if(i==0){
                        txt.text=marauderList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=marauderList[i-1]
                        temptxt.text=marauderList[i]
                        if(i==2){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                        if(i==juggernautList.lastIndex){
                            inflater.inflate(R.layout.class_berserker_marauder_table,ll,true)
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
                    if(rect.contains(e0.x.toInt(),e0.y.toInt()) and ll.contains(hscroll)) return false
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
    }}