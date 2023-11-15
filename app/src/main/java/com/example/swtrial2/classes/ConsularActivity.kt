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
import android.widget.HorizontalScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.swtrial2.R
import com.example.swtrial2.databinding.ClassConsularBinding
import kotlin.math.absoluteValue

class ConsularActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private lateinit var temptxt: TextView
    private lateinit var hscroll: HorizontalScrollView
    private lateinit var binding: ClassConsularBinding

    private lateinit var infolist: List<CharSequence>
    private lateinit var baselist: List<CharSequence>
    private lateinit var balanceList: List<CharSequence>
    private lateinit var lightningList: List<CharSequence>
    private lateinit var sageList: List<CharSequence>
    private lateinit var suggestionList: List<CharSequence>
    private val tablist = listOf("Info","Base","Table","Force\nEmpowered\nCastings","Way of Balance","Way of Lightning","Way of the Sage","Way of Suggestion")

    private lateinit var gestdect: GestureDetector
    private lateinit var table: View
    private var rect = Rect()
    private val swipethreshold = 100
    private var scrollmode=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ClassConsularBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.dummybutton.setOnClickListener { this.openOptionsMenu() }

        binding.BackButton.setOnClickListener { returntomain() }

        gestdect = GestureDetector(this,this)

        infolist = resources.getTextArray(R.array.consularInfo).toList()
        baselist = resources.getTextArray(R.array.consularBase).toList()
        balanceList = resources.getTextArray(R.array.way_of_balance).toList()
        lightningList = resources.getTextArray(R.array.way_of_lightning).toList()
        sageList = resources.getTextArray(R.array.way_of_the_sage).toList()
        suggestionList = resources.getTextArray(R.array.way_of_suggestion).toList()

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
        menuInflater.inflate(R.menu.menu_consular,menu)
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
                        if((i==2) or (i==10)){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
                    }
                }
            }
            "Table"->{
                table =inflater.inflate(R.layout.class_consular_table,ll,true)
                hscroll=table.findViewById(R.id.consulartablehscroll)
            }
            "Force\nEmpowered\nCastings"->{
                tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.consular_force_empowered_castingsHeader)
                temptxt.text=getText(R.string.consular_force_empowered_castingstext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempbersk)
            }
            "Way of Balance"->{
                for(i in balanceList.indices step 2 ){
                    if(i==0){
                        txt.text=balanceList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=balanceList[i-1]
                        temptxt.text=balanceList[i]
                        ll.addView(tempbersk)
                    }
                }
            }
            "Way of Lightning"->{
                for(i in lightningList.indices step 2 ){
                    if(i==0){
                        txt.text=lightningList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=lightningList[i-1]
                        temptxt.text=lightningList[i]
                        ll.addView(tempbersk)
                    }
                }
            }
            "Way of the Sage"->{
                for(i in sageList.indices step 2 ){
                    if(i==0){
                        txt.text=sageList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=sageList[i-1]
                        temptxt.text=sageList[i]
                        ll.addView(tempbersk)
                    }
                }
            }
            "Way of Suggestion"->{
                for(i in suggestionList.indices step 2 ){
                    if(i==0){
                        txt.text=suggestionList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=suggestionList[i-1]
                        temptxt.text=suggestionList[i]
                        if(i==10){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
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

    override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
        return
    }

    override fun onFling(e0: MotionEvent, e1: MotionEvent, vx: Float, vy: Float): Boolean {
        val diffX = e1.x - e0.x
        return if(diffX.absoluteValue>(e1.y-e0.y).absoluteValue) {
            if(binding.dummybutton.text.toString()!="Table"){
                 if (diffX.absoluteValue > swipethreshold && vx.absoluteValue > swipethreshold) {
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
                     false
                }
                else{
                     if (diffX.absoluteValue > swipethreshold && vx.absoluteValue > swipethreshold) {
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