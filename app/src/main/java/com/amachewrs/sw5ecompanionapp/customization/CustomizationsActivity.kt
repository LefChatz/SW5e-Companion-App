package com.amachewrs.sw5ecompanionapp.customization

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.CustomizationsBinding
import com.google.android.material.snackbar.Snackbar
import java.util.LinkedList

class CustomizationsActivity : AppCompatActivity() {

    private lateinit var binding: CustomizationsBinding
    private lateinit var inflater: LayoutInflater
    private lateinit var tempView: View
    private val optionSet= mutableSetOf<View>()
    private val infoSet= mutableSetOf<View>()
    private var infoGenerated= false
    private var atInfo= false
    private var customizationOptions= mutableSetOf<CustomizationOption>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val customOption = intent.getStringExtra("Customization Option").toString()

        binding = CustomizationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.setTheme(R.style.Base_ThemeOverlay_AppCompat_Dark_NoActionBar)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED
        }

        inflater=layoutInflater

        binding.title.text=customOption.replace("_"," ")
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        @SuppressLint("DiscouragedApi")
        val identifier = resources.getIdentifier(customOption,"array",packageName)

        if (identifier==0) {
            val tempText = inflater.inflate(R.layout.universal_textview_nofont_gold,binding.ll,false).findViewById<TextView>(R.id.textview)
            tempText.text = resources.getString(R.string.error_please_report_this)
            binding.ll.addView(tempText)
        }
        else{
            val detailsHeap = LinkedList<CharSequence>()
            resources.getTextArray(identifier).toCollection(detailsHeap)
            while (detailsHeap.size>4){
                customizationOptions.add(CustomizationOption(detailsHeap.poll()!!.toString(),detailsHeap.poll()!!.toString(),detailsHeap.poll()!!.toString(),detailsHeap.poll()!!.toString().toBoolean(),detailsHeap.poll()!!))
            }
        }

        generateOptions()

        binding.BackButton.setOnClickListener { returntomain() }

        binding.infobutton.setOnClickListener { checkInfoExists(customOption) }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    private fun generateOptions(){

        val starPaint = Paint()
        starPaint.textSize = 24F
        starPaint.typeface = resources.getFont(R.font.starjedi)
        starPaint.letterSpacing = 0.1F

        customizationOptions.forEach { option ->

            val bt = inflater.inflate(if (starPaint.measureText(option.name) > (windowManager.currentWindowMetrics.bounds.width() - 705)) R.layout.customizations_button_big else R.layout.customizations_button,binding.ll,false)
            val txt = bt.findViewById<TextView>(R.id.customization_option)
            txt.text=option.name

            if(option.hasPreq()) {
                bt.findViewById<TextView>(R.id.customization_option_preq).text = option.preq
                (txt.layoutParams as ConstraintLayout.LayoutParams).bottomToBottom = -1
            }

            bt.findViewById<TextView>(R.id.customizations_button_sourcebook).text = option.source
            bt.setOnClickListener{ startActivity(Intent(this, CustomizationsDetailsActivity::class.java).putExtra("Customization Option",option)) }
            optionSet.add(bt)
            binding.ll.addView(bt)
        }
    }

    private fun checkInfoExists(customOption: String){
        when(customOption){
            "fighting_styles"-> switchToInfo(LinkedList(resources.getTextArray(R.array.fighting_styles_info).toMutableSet()))
            "fighting_masteries"-> switchToInfo(LinkedList(resources.getTextArray(R.array.fighting_masteries_info).toMutableSet()))
            "lightsaber_forms"-> switchToInfo(LinkedList(resources.getTextArray(R.array.lightsaber_forms_info).toMutableSet()))
            else -> showSnackBar("Could not find Info for this part of the app\nsend suggestions at sw5ecompanionapp@gmail.com")
        }
    }

    private fun switchToInfo(infoHeap: LinkedList<CharSequence>){
        if (!atInfo) {
            optionSet.forEach{it.visibility= View.GONE }
            if (!infoGenerated) generateInfo(infoHeap)
            else infoSet.forEach { it.visibility= View.VISIBLE }
            atInfo=true
        }
        else showSnackBar("to return press back")
    }

    private fun generateInfo(infoHeap: LinkedList<CharSequence>){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        val txt = inflater.inflate(R.layout.universal_textview_starjedi_gold,binding.ll,false)
        txt.findViewById<TextView>(R.id.textview).text = infoHeap.poll()
        binding.ll.addView(txt)
        infoSet.add(txt)
        val diesize= infoHeap.poll()!!.toString().toInt()
        val title=infoHeap.poll()
        tempView = inflater.inflate(R.layout.two_column_d_table,binding.ll,false)
        val table = tempView.findViewById<TableLayout>(R.id.table)
        val dDiesize="d$diesize"
        tempView.findViewById<TextView>(R.id.dieSize_1).text=dDiesize
        tempView.findViewById<TextView>(R.id.dieSize_2).text=dDiesize
        tempView.findViewById<TextView>(R.id.title_1).text=title
        tempView.findViewById<TextView>(R.id.title_2).text=title
        for (i in 1..diesize/2){
            if (infoHeap.size<2) break
            val extraRow = inflater.inflate(R.layout.two_column_d_table_extra_row_gold,table,false)
            extraRow.findViewById<TextView>(R.id.extra_row_dieNumber_1).text="$i"
            extraRow.findViewById<TextView>(R.id.extra_row_value_1).text=infoHeap.poll()
            val col2dienum=i+(diesize/2)
            extraRow.findViewById<TextView>(R.id.extra_row_dieNumber_2).text="$col2dienum"
            extraRow.findViewById<TextView>(R.id.extra_row_value_2).text=infoHeap.poll()
            if (i%2==1) extraRow.background=null
            table.addView(extraRow)
        }
        if (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).width<resources.displayMetrics.widthPixels) (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).layoutParams as LinearLayout.LayoutParams).gravity=1
        binding.ll.addView(tempView)
        infoSet.add(tempView)
    }

    //Menu creation: Currently unnecessary
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_species,menu)
        val drawable= AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        binding.toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }*/

    private fun showSnackBar(text: String){
        val snackBar = Snackbar.make(binding.coord,text, Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.gold))

        snackBar.view.background = AppCompatResources.getDrawable(this,R.drawable.snackbar_background)

        val params = snackBar.view.layoutParams as (CoordinatorLayout.LayoutParams)
        params.width= CoordinatorLayout.LayoutParams.WRAP_CONTENT
        params.setMargins(60,0,70,60)
        params.gravity = Gravity.BOTTOM or Gravity.CENTER

        snackBar.view.layoutParams = params

        snackBar.show()
        //UsE sNaCkBaR iNsTeAd
    }
    private fun returntomain() {
        if(!atInfo) finish()
        else{
            binding.scrolly.scrollTo(0,0)
            binding.scrolly.fling(0)
            optionSet.forEach { it.visibility= View.VISIBLE }
            infoSet.forEach { it.visibility= View.GONE }
            atInfo=false
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}