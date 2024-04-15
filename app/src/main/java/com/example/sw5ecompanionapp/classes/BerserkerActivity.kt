package com.example.sw5ecompanionapp.classes

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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.sw5ecompanionapp.R
import com.example.sw5ecompanionapp.databinding.ClassBerserkerBinding
import kotlin.math.absoluteValue

class BerserkerActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private lateinit var temptxt: TextView
    private lateinit var table: View
    private lateinit var hscroll: HorizontalScrollView
    private lateinit var binding: ClassBerserkerBinding

    private lateinit var infolist: List<CharSequence>
    private lateinit var baselist: List<CharSequence>
    private lateinit var ballisticList: List<CharSequence>
    private lateinit var cycloneList: List<CharSequence>
    private lateinit var juggernautList: List<CharSequence>
    private lateinit var marauderList: List<CharSequence>
    private val tablist = listOf("Info","Base","Tables","Instincts","Ballistic Approach","Cyclone Approach","Juggernaut Approach","Marauder Approach")

    private lateinit var gestdect: GestureDetector
    private var rect = Rect()
    private val swipethreshold = 100
    private var scrollmode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ClassBerserkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.dummybutton.setOnClickListener { this.openOptionsMenu() }

        binding.BackButton.setOnClickListener { returntomain() }

        gestdect = GestureDetector(this,this)

        infolist = resources.getTextArray(R.array.berserkerInfo).toList()
        baselist = resources.getTextArray(R.array.berserkerBase).toList()
        ballisticList = resources.getTextArray(R.array.ballistic_approach).toList()
        cycloneList = resources.getTextArray(R.array.cyclone_approach).toList()
        juggernautList = resources.getTextArray(R.array.juggernaut_approach).toList()
        marauderList = resources.getTextArray(R.array.marauder_approach).toList()

        ll = binding.ll
        inflater = layoutInflater

        txt = inflater.inflate(R.layout.universal_textview_starjedi_gold,ll,false).findViewById(R.id.textview)

        for(i in infolist.indices step 2 ){
            if(i==0){
                txt.text=infolist[i]
                ll.addView(txt)
            }
            else{
                tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=infolist[i-1]
                temptxt.text=infolist[i]
                if(i==6){
                    temptxt.typeface = resources.getFont(R.font.starjedi)
                }
                ll.addView(tempbersk)
            }
        }
    }
    private fun returntomain(){
        finish()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_berserk,menu)
        val drawable = AppCompatResources.getDrawable(this,R.drawable.downarrowgold)
        binding.toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.dummybutton.text=item.title
        changeclassview()
        return super.onOptionsItemSelected(item)
    }

    private fun changeclassview(){
        ll.removeAllViews()
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        when(binding.dummybutton.text.toString()){
            "Info"->{
                for(i in infolist.indices step 2 ){
                    if(i==0){
                        txt.text=infolist[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=infolist[i-1]
                        temptxt.text=infolist[i]
                        if(i==6){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }
            }
            "Base"->{
                for(i in baselist.indices step 2 ){
                    if(i==0){
                        txt.text=baselist[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=baselist[i-1]
                        temptxt.text=baselist[i]
                        ll.addView(tempbersk)
                    }
                }
            }
            "Tables"->{
                table = inflater.inflate(R.layout.class_berserker_table,ll,true)
                hscroll=table.findViewById(R.id.berserkertablehscroll)
                inflater.inflate(R.layout.class_berserker_marauder_table,ll,true)
            }
            "Instincts"->{
                tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.berserker_instinctsHeader)
                temptxt.text=getText(R.string.berserker_instinctstext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempbersk)
            }
            "Ballistic Approach"->{
                for(i in ballisticList.indices step 2 ){
                    if(i==0){
                        txt.text=ballisticList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=ballisticList[i-1]
                        temptxt.text=ballisticList[i]
                        ll.addView(tempbersk)
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
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=cycloneList[i-1]
                        temptxt.text=cycloneList[i]
                        ll.addView(tempbersk)
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
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=juggernautList[i-1]
                        temptxt.text=juggernautList[i]
                        ll.addView(tempbersk)
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
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=marauderList[i-1]
                        temptxt.text=marauderList[i]
                        if(i==2){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
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
    private fun changeview(dir: String){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        ll.removeAllViews()
        when(dir){
            "RtoL"->{
                binding.dummybutton.text=tablist[tablist.indexOf(binding.dummybutton.text.toString())+1]
                changeclassview()
                if(binding.dummybutton.text==tablist.last()){
                    scrollmode=1
                }
                else{
                    if(scrollmode!=2){scrollmode=2}
                }
            }
            "LtoR"->{
                binding.dummybutton.text=tablist[tablist.indexOf(binding.dummybutton.text.toString())-1]
                changeclassview()
                if(binding.dummybutton.text==tablist.first()){
                    scrollmode=0
                }
                else{
                    if(scrollmode!=2){scrollmode=2}
                }
            }
        }

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
                if(binding.dummybutton.text.toString()!="Tables"){
                    return if (diffX.absoluteValue > swipethreshold && vx.absoluteValue > swipethreshold) {
                        //L to R
                        if (diffX > 0 && scrollmode!=0) {
                            changeview("LtoR")
                        }
                        //R to L
                        else if(diffX<0 && scrollmode!=1){
                            changeview("RtoL")
                        }
                        true
                    } else{
                        false
                    }
                }
                else{
                    hscroll.getGlobalVisibleRect(rect)
                    if(rect.contains(e0.x.toInt(),e0.y.toInt())){
                        return false
                    }
                    else{
                        return if (diffX.absoluteValue > swipethreshold && vx.absoluteValue > swipethreshold) {
                            //L to R
                            if (diffX > 0 && scrollmode!=0) {
                                changeview("LtoR")
                            }
                            //R to L
                            else if(diffX<0 && scrollmode!=1){
                                changeview("RtoL")
                            }
                            true
                        } else{
                            false
                        }
                    }
                }
            }
            else{
                return false
            }
        }
        else return false
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }}