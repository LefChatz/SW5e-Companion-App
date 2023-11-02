package com.example.swtrial2.classes

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.swtrial2.R
import kotlin.math.absoluteValue

class ScholarActivity : AppCompatActivity() , GestureDetector.OnGestureListener{
    private lateinit var dummybutton: Button
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private lateinit var backButton: AppCompatButton
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var scrolly: ScrollView
    private lateinit var temptxt: TextView
    private lateinit var infolist: List<CharSequence>
    private lateinit var baselist: List<CharSequence>
    private lateinit var gamblerList: List<CharSequence>
    private lateinit var physicianList: List<CharSequence>
    private lateinit var politicianList: List<CharSequence>
    private lateinit var tacticianList: List<CharSequence>
    private lateinit var discoveriesList: List<CharSequence>
    private lateinit var classparams: LinearLayout.LayoutParams
    private lateinit var gestdect: GestureDetector
    private val tablist = listOf("Info","Base","Tables","Discoveries","Gambler Pursuit","Physician Pursuit","Politician Pursuit","Tactician Pursuit")
    private lateinit var hscroll: HorizontalScrollView
    private lateinit var table: View
    private lateinit var hscrollsideefs: HorizontalScrollView
    private lateinit var tablesidefs: View
    private var rect = Rect()
    private var rect2 = Rect()
    val swipethreshold = 100
    private var scrollmode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scholar)

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        dummybutton = findViewById(R.id.dummybutton)
        dummybutton.setOnClickListener {
            this.openOptionsMenu()
        }

        backButton=findViewById(R.id.BackButton)
        backButton.setOnClickListener {
            returntomain()
        }

        gestdect = GestureDetector(this,this)

        infolist = resources.getTextArray(R.array.scholarInfo).toList()
        baselist = resources.getTextArray(R.array.scholarBase).toList()
        gamblerList = resources.getTextArray(R.array.gambler_pursuit).toList()
        physicianList = resources.getTextArray(R.array.physician_pursuit).toList()
        politicianList = resources.getTextArray(R.array.politician_pursuit).toList()
        tacticianList = resources.getTextArray(R.array.tactician_pursuit).toList()
        discoveriesList = resources.getTextArray(R.array.discoveries).toList()

        ll = findViewById(R.id.scholarll)
        scrolly= findViewById(R.id.scholarscollview)
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
    private fun returntomain(){
        finish()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scholar,menu)
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
                        if(i==2){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }
            }
            "Tables"->{
                table=inflater.inflate(R.layout.class_scholar_table,ll,true)
                hscroll=table.findViewById(R.id.scholartablehscroll)
                tablesidefs=inflater.inflate(R.layout.class_scholar_physician_table,ll,true)
                hscrollsideefs=tablesidefs.findViewById(R.id.scholartablehscrollsidefs)
            }
            "Discoveries"->{
                for(i in discoveriesList.indices step 2){
                    tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                    temptxt=tempbersk.findViewById(R.id.contenttext)
                    tempbersk.findViewById<TextView>(R.id.headertext).text=discoveriesList[i]
                    temptxt.text=discoveriesList[i+1]
                    temptxt.typeface = resources.getFont(R.font.starjedi)
                    if(i!=0){
                        classparams = tempbersk.layoutParams as LinearLayout.LayoutParams
                        classparams.topMargin=70
                        tempbersk.layoutParams=classparams
                    }
                    ll.addView(tempbersk)
                }
            }
            "Gambler Pursuit"->{
                for(i in gamblerList.indices step 2 ){
                    if(i==0){
                        txt.text=gamblerList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=gamblerList[i-1]
                        temptxt.text=gamblerList[i]
                        ll.addView(tempbersk)
                        if(i==gamblerList.lastIndex){
                            tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                            temptxt=tempbersk.findViewById(R.id.contenttext)
                            tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.gambler_discoveriesHeader)
                            temptxt.text=getText(R.string.gambler_discoveriesText)
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                            classparams = tempbersk.layoutParams as LinearLayout.LayoutParams
                            classparams.topMargin=200
                            tempbersk.layoutParams=classparams
                            ll.addView(tempbersk)
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
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=physicianList[i-1]
                        temptxt.text=physicianList[i]
                        ll.addView(tempbersk)
                        if(i==physicianList.lastIndex){
                            tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                            temptxt=tempbersk.findViewById(R.id.contenttext)
                            tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.physician_discoveriesHeader)
                            temptxt.text=getText(R.string.physician_discoveriesText)
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                            classparams = tempbersk.layoutParams as LinearLayout.LayoutParams
                            classparams.topMargin=200
                            tempbersk.layoutParams=classparams
                            ll.addView(tempbersk)
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
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=politicianList[i-1]
                        temptxt.text=politicianList[i]
                        ll.addView(tempbersk)
                        if(i==politicianList.lastIndex){
                            tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                            temptxt=tempbersk.findViewById(R.id.contenttext)
                            tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.politician_discoveriesHeader)
                            temptxt.text=getText(R.string.politician_discoveriesText)
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                            classparams = tempbersk.layoutParams as LinearLayout.LayoutParams
                            classparams.topMargin=200
                            tempbersk.layoutParams=classparams
                            ll.addView(tempbersk)
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
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=tacticianList[i-1]
                        temptxt.text=tacticianList[i]
                        ll.addView(tempbersk)
                        if(i==tacticianList.lastIndex){
                            tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                            temptxt=tempbersk.findViewById(R.id.contenttext)
                            tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.tactician_discoveriesHeader)
                            temptxt.text=getText(R.string.tactician_discoveriesText)
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                            classparams = tempbersk.layoutParams as LinearLayout.LayoutParams
                            classparams.topMargin=200
                            tempbersk.layoutParams=classparams
                            ll.addView(tempbersk)
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
            if(dummybutton.text.toString()!="Tables"){
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
                hscrollsideefs.getGlobalVisibleRect(rect2)
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
    override fun onConfigurationChanged(newConfig: Configuration) {
        /*val windowinsetcontroller = WindowCompat.getInsetsController(window, window.decorView)
        windowinsetcontroller.hide(WindowInsetsCompat.Type.systemBars())*/
        super.onConfigurationChanged(newConfig)
    }
}