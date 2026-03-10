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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.contains
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.ClassOperativeBinding
import com.amachewrs.sw5ecompanionapp.utility.Utilities.Companion.showSnackBar
import kotlin.math.absoluteValue

class OperativeActivity : AppCompatActivity() , GestureDetector.OnGestureListener{
    private lateinit var tempView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private lateinit var temptxt: TextView
    private lateinit var table: View
    private lateinit var hscroll: HorizontalScrollView
    private lateinit var binding: ClassOperativeBinding

    private lateinit var infoList: List<CharSequence>
    private lateinit var baseList: List<CharSequence>
    private lateinit var acquisitionsList: List<CharSequence>
    private lateinit var beguilerList: List<CharSequence>
    private lateinit var lethalityList: List<CharSequence>
    private lateinit var sharpshooterList: List<CharSequence>
    private val tabList = listOf("Info","Base","Table","Operative\nExploits","Acquisitions\nPractice","Beguiler\nPractice","Lethality\nPractice","Sharpshooter\nPractice")

    private lateinit var gestdect: GestureDetector
    private var rect = Rect()
    private val swipeThreshold = 100
    private var scrollMode=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ClassOperativeBinding.inflate(layoutInflater)
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

        infoList = resources.getTextArray(R.array.operativeInfo).toList()
        baseList = resources.getTextArray(R.array.operativeBase).toList()
        acquisitionsList = resources.getTextArray(R.array.acquisitions_practice).toList()
        beguilerList = resources.getTextArray(R.array.beguiler_practice).toList()
        lethalityList = resources.getTextArray(R.array.lethality_practice).toList()
        sharpshooterList = resources.getTextArray(R.array.sharpshooter_practice).toList()

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
        menuInflater.inflate(R.menu.menu_operative,menu)
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
                        ll.addView(tempView)
                    }
                }
            }
            "Table"->{
                table=inflater.inflate(R.layout.class_operative_table,ll,true)
                hscroll=table.findViewById(R.id.operativetablehscroll)
            }
            "Operative\nExploits"->{
                tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempView.findViewById(R.id.contenttext)
                tempView.findViewById<TextView>(R.id.headertext).text=getText(R.string.operative_exploitsHeader)
                temptxt.text=getText(R.string.operative_exploitstext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempView)
            }
            "Acquisitions\nPractice"->{
                for(i in acquisitionsList.indices step 2 ){
                    if(i==0){
                        txt.text=acquisitionsList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=acquisitionsList[i-1]
                        temptxt.text=acquisitionsList[i]
                        if(i==4){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                    }
                }

            }
            "Beguiler\nPractice"->{
                for(i in beguilerList.indices step 2 ){
                    if(i==0){
                        txt.text=beguilerList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=beguilerList[i-1]
                        temptxt.text=beguilerList[i]
                        if(i==2){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                    }
                }
            }
            "Lethality\nPractice"->{
                for(i in lethalityList.indices step 2 ){
                    if(i==0){
                        txt.text=lethalityList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=lethalityList[i-1]
                        temptxt.text=lethalityList[i]
                        if(i==4){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                    }
                }
            }
            "Sharpshooter\nPractice"->{
                for(i in sharpshooterList.indices step 2 ){
                    if(i==0){
                        txt.text=sharpshooterList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=sharpshooterList[i-1]
                        temptxt.text=sharpshooterList[i]
                        if(i==4){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                    }
                }

            }
            else->{showSnackBar("Error",binding.coord,this)}
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
    }
}