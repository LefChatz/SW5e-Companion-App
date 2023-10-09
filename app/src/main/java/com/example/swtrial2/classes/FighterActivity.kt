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

class FighterActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
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
    private lateinit var assaultList: List<CharSequence>
    private lateinit var blademasterList: List<CharSequence>
    private lateinit var shieldList: List<CharSequence>
    private lateinit var tacticalList: List<CharSequence>
    private lateinit var gestdect: GestureDetector
    private val tablist = listOf("Info","Base","Tables","Fighter Strategies","Assault Specialist","Blademaster Specialist","Shield Specialist","Tactical Specialist")
    lateinit var hscroll: HorizontalScrollView
    lateinit var table: View
    var rect = Rect()
    val swipethreshold = 100
    var scrollmode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fighter)

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        dummybutton = findViewById(R.id.dummybutton)
        dummybutton.setOnClickListener {
            this.openOptionsMenu()
        }

        gestdect = GestureDetector(this,this)

        infolist = resources.getTextArray(R.array.fighterInfo).toList()
        baselist = resources.getTextArray(R.array.fighterBase).toList()
        assaultList = resources.getTextArray(R.array.assault_specialist).toList()
        blademasterList = resources.getTextArray(R.array.blademaster_specialist).toList()
        shieldList = resources.getTextArray(R.array.shield_specialist).toList()
        tacticalList = resources.getTextArray(R.array.tactical_specialist).toList()

        ll = findViewById(R.id.fighterll)
        scrolly= findViewById(R.id.fighterscollview)
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
        menuInflater.inflate(R.menu.menu_fighter,menu)
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
                        if(i==8){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }
            }
            "Tables"->{
                table=inflater.inflate(R.layout.class_fighter_table,ll,true)
                hscroll=table.findViewById(R.id.fightertablehscroll)
                inflater.inflate(R.layout.class_fighter_shield_specialist_table,ll,true)
                inflater.inflate(R.layout.class_fighter_tactical_specialist_table,ll,true)
            }
            "Fighter Strategies"->{
                tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.fighter_strategiesHeader)
                temptxt.text=getText(R.string.fighter_strategiestext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempbersk)
            }

            "Assault Specialist"->{
                for(i in assaultList.indices step 2 ){
                    if(i==0){
                        txt.text=assaultList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=assaultList[i-1]
                        temptxt.text=assaultList[i]
                        ll.addView(tempbersk)
                    }
                }

            }
            "Blademaster Specialist"->{
                for(i in blademasterList.indices step 2 ){
                    if(i==0){
                        txt.text=blademasterList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=blademasterList[i-1]
                        temptxt.text=blademasterList[i]
                        if(i==4){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }
            }
            "Shield Specialist"->{
                for(i in shieldList.indices step 2 ){
                    if(i==0){
                        txt.text=shieldList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=shieldList[i-1]
                        temptxt.text=shieldList[i]
                        if(i==2){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                        if(i==shieldList.lastIndex){
                            inflater.inflate(R.layout.class_fighter_shield_specialist_table,ll,true)
                        }
                    }
                }
            }
            "Tactical Specialist"->{
                for(i in tacticalList.indices step 2 ){
                    if(i==0){
                        txt.text=tacticalList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.class_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=tacticalList[i-1]
                        temptxt.text=tacticalList[i]
                        if(i==4){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                        if(i==tacticalList.lastIndex){
                            inflater.inflate(R.layout.class_fighter_tactical_specialist_table,ll,true)
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
            if(dummybutton.text!="Tables"){
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