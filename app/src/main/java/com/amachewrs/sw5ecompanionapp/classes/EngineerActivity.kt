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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.ClassEngineerBinding
import kotlin.math.absoluteValue

class EngineerActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private lateinit var temptxt: TextView
    private lateinit var table: View
    private lateinit var hscroll: HorizontalScrollView
    private lateinit var unsTable: View
    private lateinit var hscrollunstable: HorizontalScrollView
    private lateinit var binding: ClassEngineerBinding

    private lateinit var infolist: List<CharSequence>
    private lateinit var baselist: List<CharSequence>
    private lateinit var armormechList: List<CharSequence>
    private lateinit var armstechList: List<CharSequence>
    private lateinit var gadgeteerList: List<CharSequence>
    private lateinit var unstableList: List<CharSequence>
    private val tablist = listOf("Info","Base","Tables","Armormech Modifications","Armstech Modifications","Gadgeteer Modifications","Armormech Engineering","Armstech Engineering","Gadgeteer Engineering","Unstable Engineering")

    private lateinit var gestdect: GestureDetector
    private var rect = Rect()
    private var rect2 = Rect()
    private val swipethreshold = 100
    private var scrollmode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ClassEngineerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.dummybutton.setOnClickListener {
            this.openOptionsMenu()
        }

        binding.BackButton.setOnClickListener {
            returntomain()
        }

        gestdect = GestureDetector(this,this)

        infolist = resources.getTextArray(R.array.engineerInfo).toList()
        baselist = resources.getTextArray(R.array.engineerBase).toList()
        armormechList = resources.getTextArray(R.array.armormech_engineering).toList()
        armstechList = resources.getTextArray(R.array.armstech_engineering).toList()
        gadgeteerList = resources.getTextArray(R.array.gadgeteer_engineering).toList()
        unstableList = resources.getTextArray(R.array.unstable_engineering).toList()

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
                tempbersk.findViewById<TextView>(R.id.headertext).text=infolist[i-1]
                temptxt=tempbersk.findViewById(R.id.contenttext)
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
        menuInflater.inflate(R.menu.menu_engineer,menu)
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
        when(binding.dummybutton.text.toString()){
            "Info"->{
                for(i in infolist.indices step 2 ){
                    if(i==0){
                        txt.text=infolist[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=infolist[i-1]
                        temptxt=tempbersk.findViewById(R.id.contenttext)
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
                        if(i==2){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }
            }
            "Tables"->{
                table=inflater.inflate(R.layout.class_engineer_table,ll,true)
                hscroll=table.findViewById(R.id.engineertablehscroll)
                unsTable=inflater.inflate(R.layout.class_engineer_unstable_engineering_table,ll,true)
                hscrollunstable=unsTable.findViewById(R.id.class_engineer_unstable_table)
            }
            "Armormech Modifications"->{
                tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.armormech_modificationsHeader)
                temptxt.text=getText(R.string.armormech_modificationstext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempbersk)
            }
            "Armstech Modifications"->{
                tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.armstech_modificationsHeader)
                temptxt.text=getText(R.string.armstech_modificationstext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempbersk)
            }
            "Gadgeteer Modifications"->{
                tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.gadgeteer_modificationsHeader)
                temptxt.text=getText(R.string.gadgeteer_modificationstext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempbersk)
            }
            "Armormech Engineering"->{
                for(i in armormechList.indices step 2 ){
                    if(i==0){
                        txt.text=armormechList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=armormechList[i-1]
                        temptxt.text=armormechList[i]
                        ll.addView(tempbersk)
                    }
                }

            }
            "Armstech Engineering"->{
                for(i in armstechList.indices step 2 ){
                    if(i==0){
                        txt.text=armstechList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=armstechList[i-1]
                        temptxt.text=armstechList[i]
                        ll.addView(tempbersk)
                    }
                }

            }
            "Gadgeteer Engineering"->{
                for(i in gadgeteerList.indices step 2 ){
                    if(i==0){
                        txt.text=gadgeteerList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=gadgeteerList[i-1]
                        temptxt.text=gadgeteerList[i]
                        if(i==6){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }

            }
            "Unstable Engineering"->{
                for(i in unstableList.indices step 2 ){
                    if(i==0){
                        txt.text=unstableList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=unstableList[i-1]
                        temptxt.text=unstableList[i]
                        ll.addView(tempbersk)
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
                    hscrollunstable.getGlobalVisibleRect(rect2)
                    if(rect.contains(e0.x.toInt(),e0.y.toInt()) or rect2.contains(e0.x.toInt(),e0.y.toInt())){
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
    }
}