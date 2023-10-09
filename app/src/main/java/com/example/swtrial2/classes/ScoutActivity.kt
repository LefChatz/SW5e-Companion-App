package com.example.swtrial2.classes

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.swtrial2.R
import kotlin.math.absoluteValue

class ScoutActivity : AppCompatActivity() , GestureDetector.OnGestureListener{
    private lateinit var dummybutton: Button
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var scrolly: ScrollView
    private lateinit var temptxt: TextView
    private lateinit var infolist: List<CharSequence>
    private lateinit var baselist: List<CharSequence>
    private lateinit var bulwarkList: List<CharSequence>
    private lateinit var hunterList: List<CharSequence>
    private lateinit var slayerList: List<CharSequence>
    private lateinit var stalkerList: List<CharSequence>
    private lateinit var gestdect: GestureDetector
    private val tablist = listOf("Info","Base","Table","Scout Routines","Bulwark Technique","Hunter Technique","Slayer Technique","Stalker Technique")
    private lateinit var hscroll: HorizontalScrollView
    lateinit var table: View
    private var rect = Rect()
    val swipethreshold = 100
    private var scrollmode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scout)

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        dummybutton = findViewById(R.id.dummybutton)
        dummybutton.setOnClickListener {
            this.openOptionsMenu()
        }

        gestdect = GestureDetector(this,this)

        infolist = resources.getTextArray(R.array.scoutInfo).toList()
        baselist = resources.getTextArray(R.array.scoutBase).toList()
        bulwarkList = resources.getTextArray(R.array.bulwark_technique).toList()
        hunterList = resources.getTextArray(R.array.hunter_technique).toList()
        slayerList = resources.getTextArray(R.array.slayer_technique).toList()
        stalkerList = resources.getTextArray(R.array.stalker_technique).toList()

        ll = findViewById(R.id.scoutll)
        scrolly= findViewById(R.id.scoutscollview)
        inflater = layoutInflater

        txt = TextView(this)
        txt.setTextColor(getColor(R.color.gold))
        txt.typeface=resources.getFont(R.font.starjedi)
        txt.textSize=20F

        for(i in infolist.indices step 2 ){
            if(i==0){
                txt.text=infolist[i]
                ll.addView(txt)
            }
            else{
                tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
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
    fun returntomain(view: View?){
        finish()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scout,menu)
        val drawable = AppCompatResources.getDrawable(this,R.drawable.downarrowgold)
        toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        dummybutton.text=item.title
        changeclassview()
        return super.onOptionsItemSelected(item)
    }

    private fun changeclassview(){
        ll.removeAllViews()
        scrolly.scrollTo(0,0)
        when(dummybutton.text.toString()){
            "Info"->{
                for(i in infolist.indices step 2 ){
                    if(i==0){
                        txt.text=infolist[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
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
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=baselist[i-1]
                        temptxt.text=baselist[i]
                        if(i==6){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }
            }
            "Table"->{
                table=inflater.inflate(R.layout.class_scout_table,ll,true)
                hscroll=table.findViewById(R.id.scouttablehscroll)
            }
            "Scout Routines"->{
                tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.scout_routinesHeader)
                temptxt.text=getText(R.string.scout_routinestext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempbersk)
            }
            "Bulwark Technique"->{
                for(i in bulwarkList.indices step 2 ){
                    if(i==0){
                        txt.text=bulwarkList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=bulwarkList[i-1]
                        temptxt.text=bulwarkList[i]
                        if(i==8){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }

            }
            "Hunter Technique"->{
                for(i in hunterList.indices step 2 ){
                    if(i==0){
                        txt.text=hunterList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=hunterList[i-1]
                        temptxt.text=hunterList[i]
                        if((i==2)or(i==6)or(i==8)or(i==10)){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }
            }
            "Slayer Technique"->{
                for(i in slayerList.indices step 2 ){
                    if(i==0){
                        txt.text=slayerList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=slayerList[i-1]
                        temptxt.text=slayerList[i]
                        ll.addView(tempbersk)
                    }
                }
            }
            "Stalker Technique"->{
                for(i in stalkerList.indices step 2 ){
                    if(i==0){
                        txt.text=stalkerList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=stalkerList[i-1]
                        temptxt.text=stalkerList[i]
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
        scrolly.scrollTo(0,0)
        scrolly.fling(0)
        ll.removeAllViews()
        when(dir){
            "RtoL"->{
                dummybutton.text=tablist[tablist.indexOf(dummybutton.text.toString())+1]
                changeclassview()
                if(dummybutton.text==tablist.last()){
                    scrollmode=1
                }
                else{
                    if(scrollmode!=2){scrollmode=2}
                }
            }
            "LtoR"->{
                dummybutton.text=tablist[tablist.indexOf(dummybutton.text.toString())-1]
                changeclassview()
                if(dummybutton.text==tablist.first()){
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

    override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
        return
    }

    override fun onFling(e0: MotionEvent, e1: MotionEvent, vx: Float, vy: Float): Boolean {
        val diffX = e1.x - e0.x
        if(diffX.absoluteValue>(e1.y-e0.y).absoluteValue) {
            if(dummybutton.text!="Table"){
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
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}