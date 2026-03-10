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
import com.amachewrs.sw5ecompanionapp.databinding.ClassFighterBinding
import com.amachewrs.sw5ecompanionapp.utility.Utilities.Companion.showSnackBar
import kotlin.math.absoluteValue

class FighterActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private lateinit var tempbersk: View
    private lateinit var inflater: LayoutInflater
    private lateinit var ll: LinearLayoutCompat
    private lateinit var txt: TextView
    private lateinit var temptxt: TextView
    lateinit var table: View
    lateinit var hscroll: HorizontalScrollView
    private lateinit var binding: ClassFighterBinding

    private lateinit var infoList: List<CharSequence>
    private lateinit var baseList: List<CharSequence>
    private lateinit var assaultList: List<CharSequence>
    private lateinit var blademasterList: List<CharSequence>
    private lateinit var shieldList: List<CharSequence>
    private lateinit var tacticalList: List<CharSequence>
    private val tabList = listOf("Info","Base","Tables","Fighter\nStrategies","Assault\nSpecialist","Blademaster\nSpecialist","Shield\nSpecialist","Tactical\nSpecialist")

    private lateinit var gestdect: GestureDetector
    var rect = Rect()
    val swipeThreshold = 100
    var scrollMode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ClassFighterBinding.inflate(layoutInflater)
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

        infoList = resources.getTextArray(R.array.fighterInfo).toList()
        baseList = resources.getTextArray(R.array.fighterBase).toList()
        assaultList = resources.getTextArray(R.array.assault_specialist).toList()
        blademasterList = resources.getTextArray(R.array.blademaster_specialist).toList()
        shieldList = resources.getTextArray(R.array.shield_specialist).toList()
        tacticalList = resources.getTextArray(R.array.tactical_specialist).toList()

        ll = binding.ll

        inflater = layoutInflater

        txt = inflater.inflate(R.layout.universal_textview_starjedi_gold,ll,false).findViewById(R.id.textview)

        for(i in infoList.indices step 2 ){
            if(i==0){
                txt.text=infoList[i]
                ll.addView(txt)
            }
            else{
                tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=infoList[i-1]
                temptxt.text=infoList[i]
                if(i==6){
                    temptxt.typeface = resources.getFont(R.font.starjedi)
                }
                ll.addView(tempbersk)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fighter,menu)
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
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=infoList[i-1]
                        temptxt.text=infoList[i]
                        if(i==6){
                            temptxt.typeface = resources.getFont(R.font.starjedi)
                        }
                        ll.addView(tempbersk)
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
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=baseList[i-1]
                        temptxt.text=baseList[i]
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
            "Fighter\nStrategies"->{
                tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                temptxt=tempbersk.findViewById(R.id.contenttext)
                tempbersk.findViewById<TextView>(R.id.headertext).text=getText(R.string.fighter_strategiesHeader)
                temptxt.text=getText(R.string.fighter_strategiestext)
                temptxt.typeface = resources.getFont(R.font.starjedi)
                ll.addView(tempbersk)
            }

            "Assault\nSpecialist"->{
                for(i in assaultList.indices step 2 ){
                    if(i==0){
                        txt.text=assaultList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
                        temptxt=tempbersk.findViewById(R.id.contenttext)
                        tempbersk.findViewById<TextView>(R.id.headertext).text=assaultList[i-1]
                        temptxt.text=assaultList[i]
                        ll.addView(tempbersk)
                    }
                }

            }
            "Blademaster\nSpecialist"->{
                for(i in blademasterList.indices step 2 ){
                    if(i==0){
                        txt.text=blademasterList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
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
            "Shield\nSpecialist"->{
                for(i in shieldList.indices step 2 ){
                    if(i==0){
                        txt.text=shieldList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
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
            "Tactical\nSpecialist"->{
                for(i in tacticalList.indices step 2 ){
                    if(i==0){
                        txt.text=tacticalList[i]
                        ll.addView(txt)
                    }
                    else{
                        tempbersk = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
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