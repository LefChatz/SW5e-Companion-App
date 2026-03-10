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
import com.amachewrs.sw5ecompanionapp.databinding.ClassGuardianBinding
import com.amachewrs.sw5ecompanionapp.utility.Utilities.Companion.showSnackBar
import kotlin.math.absoluteValue

class GuardianActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private lateinit var tempView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private lateinit var temptxt: TextView
    private lateinit var table: View
    private lateinit var hscroll: HorizontalScrollView
    private lateinit var binding: ClassGuardianBinding

    private lateinit var infoList: List<CharSequence>
    private lateinit var baseList: List<CharSequence>
    private lateinit var makashiList: List<CharSequence>
    private lateinit var nimanList: List<CharSequence>
    private lateinit var shiendjemList: List<CharSequence>
    private lateinit var soresuList: List<CharSequence>
    private val tabList = listOf("Info","Base","Table","Guardian\nAuras","Makashi\nForm","Niman\nForm","Shien/Djem So\nForm","Soresu\nForm")

    private lateinit var gestdect: GestureDetector
    private var rect = Rect()
    private val swipeThreshold = 100
    private var scrollMode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ClassGuardianBinding.inflate(layoutInflater)
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

        infoList = resources.getTextArray(R.array.guardianInfo).toList()
        baseList = resources.getTextArray(R.array.guardianBase).toList()
        makashiList = resources.getTextArray(R.array.makashi_form).toList()
        nimanList = resources.getTextArray(R.array.niman_form).toList()
        shiendjemList = resources.getTextArray(R.array.shien_djem_so_form).toList()
        soresuList = resources.getTextArray(R.array.soresu_form).toList()
        
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
        menuInflater.inflate(R.menu.menu_guardian,menu)
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
                        if((i==2)or(i==4)){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                    }
                }
            }
            "Table"->{
                table=inflater.inflate(R.layout.class_guardian_table,ll,true)
                hscroll=table.findViewById(R.id.guardiantablehscroll)
            }
            "Guardian\nAuras"->{
                tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                val temptxt=tempView.findViewById<TextView>(R.id.contenttext)
                tempView.findViewById<TextView>(R.id.headertext).text=getText(R.string.guardian_aurasHeader)
                temptxt.text=getText(R.string.guardian_aurastext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempView)
            }
            "Makashi\nForm"->{
                for(i in makashiList.indices step 2 ){
                    if(i==0){
                        txt.text=makashiList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=makashiList[i-1]
                        temptxt.text=makashiList[i]
                        if(i==6){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                    }
                }

            }
            "Niman\nForm"->{
                for(i in nimanList.indices step 2 ){
                    if(i==0){
                        txt.text=nimanList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=nimanList[i-1]
                        temptxt.text=nimanList[i]
                        if(i==6){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                    }
                }
            }
            "Shien/Djem So\nForm"->{
                for(i in shiendjemList.indices step 2 ){
                    if(i==0){
                        txt.text=shiendjemList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=shiendjemList[i-1]
                        temptxt.text=shiendjemList[i]
                        if((i==8)or(i==10)or(i==12)){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempView)
                    }
                }
            }
            "Soresu\nForm"->{
                for(i in soresuList.indices step 2 ){
                    if(i==0){
                        txt.text=soresuList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempView.findViewById(R.id.contenttext)
                        tempView.findViewById<TextView>(R.id.headertext).text=soresuList[i-1]
                        temptxt.text=soresuList[i]
                        if(i==8){
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