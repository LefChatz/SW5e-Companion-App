package com.example.swtrial2.classes

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.widget.*
import android.widget.LinearLayout.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.swtrial2.R
import kotlin.math.absoluteValue

class MonkActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
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
    private lateinit var crimsonList: List<CharSequence>
    private lateinit var echaniList: List<CharSequence>
    private lateinit var matukaiList: List<CharSequence>
    private lateinit var nightsisterList: List<CharSequence>
    private lateinit var gestdect: GestureDetector
    private val tablist = listOf("Info","Base","Table","Monastic Vows","Crimson Order","Echani Order","Matukai Order","Nightsister Order")
    private lateinit var hscroll: HorizontalScrollView
    lateinit var table: View
    private var rect = Rect()
    val swipethreshold = 100
    private var scrollmode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monk)

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        dummybutton = findViewById(R.id.dummybutton)
        dummybutton.setOnClickListener {
            this.openOptionsMenu()
        }

        gestdect = GestureDetector(this,this)

        infolist = resources.getTextArray(R.array.monkInfo).toList()
        baselist = resources.getTextArray(R.array.monkBase).toList()
        crimsonList = resources.getTextArray(R.array.crimson_order).toList()
        echaniList = resources.getTextArray(R.array.echani_order).toList()
        matukaiList = resources.getTextArray(R.array.matukai_order).toList()
        nightsisterList = resources.getTextArray(R.array.nightsister_order).toList()

        ll = findViewById(R.id.monkll)
        scrolly= findViewById(R.id.monkscollview)
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
        menuInflater.inflate(R.menu.menu_monk,menu)
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
                        if(i==2){
                            val vartxt = TextView(this)
                            val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
                            layoutParams.marginStart=5
                            vartxt.layoutParams=layoutParams
                            vartxt.background=AppCompatResources.getDrawable(this,R.drawable.goldbox)
                            vartxt.text=getText(R.string.variant_monk)
                            vartxt.setTextColor(getColor(R.color.gold))
                            vartxt.textSize=18F
                            ll.addView(vartxt)
                        }
                    }
                }
            }
            "Table"->{
                table=inflater.inflate(R.layout.class_monk_table,ll,true)
                hscroll=table.findViewById(R.id.monktablehscroll)
            }
            "Monastic Vows"->{
                tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.monastic_vowsHeader)
                temptxt.text=getText(R.string.monastic_vowstext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempbersk)
            }
            "Crimson Order"->{
                for(i in crimsonList.indices step 2 ){
                    if(i==0){
                        txt.text=crimsonList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=crimsonList[i-1]
                        temptxt.text=crimsonList[i]
                        ll.addView(tempbersk)
                    }
                }
            }
            "Echani Order"->{
                for(i in echaniList.indices step 2 ){
                    if(i==0){
                        txt.text=echaniList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=echaniList[i-1]
                        temptxt.text=echaniList[i]
                        if((i==2)or(i==4)){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }
            }
            "Matukai Order"->{
                for(i in matukaiList.indices step 2 ){
                    if(i==0){
                        txt.text=matukaiList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=matukaiList[i-1]
                        temptxt.text=matukaiList[i]
                        ll.addView(tempbersk)
                    }
                }
            }
            "Nightsister Order"->{
                for(i in nightsisterList.indices step 2 ){
                    if(i==0){
                        txt.text=nightsisterList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=nightsisterList[i-1]
                        temptxt.text=nightsisterList[i]
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