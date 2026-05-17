package com.amachewrs.sw5ecompanionapp.backgrounds

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.BackgroundsBinding
import com.amachewrs.sw5ecompanionapp.utility.Utilities.Companion.showSnackBar
import java.util.LinkedList

class BackgroundsActivity : AppCompatActivity() {

    private lateinit var binding: BackgroundsBinding
    private lateinit var inflater: LayoutInflater
    private lateinit var tempView: View
    private lateinit var backgroundStringArray: Array<String>
    private lateinit var infoLinkedList: LinkedList<CharSequence>
    private val backgroundsList = mutableListOf<View>()
    private val infoList = mutableListOf<View>()
    private var infoGenerated = false
    private var atInfo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BackgroundsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        inflater=layoutInflater

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        backgroundStringArray = resources.getStringArray(R.array.backgrounds)

        infoLinkedList = LinkedList()

        generateBackgrounds()

        binding.BackButton.setOnClickListener { returntomain() }

        binding.infobutton.setOnClickListener { if (!atInfo) handleInfoSwitch() else showSnackBar("to return press back", binding.coord,this) }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    private fun handleInfoSwitch(){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        binding.ll.removeAllViews()
        if (!atInfo) {
            if (!infoGenerated) generateInfo() else infoList.forEach {binding.ll.addView(it)}
        }
        else backgroundsList.forEach { binding.ll.addView(it) }
        atInfo=!atInfo
    }

    private fun generateBackgrounds(){
        backgroundStringArray.forEach {
            val bt = inflater.inflate(R.layout.background_button,binding.ll,false)
            val info = it.split(" ")
            bt.findViewById<TextView>(R.id.background_name).text=info[0].replace("_"," ").replace(".","-")
            if(info[0]=="retired_adventurer") bt.findViewById<TextView>(R.id.background_name).text=getString(R.string.backgrounds_un_retired_adventurer)
            bt.findViewById<TextView>(R.id.background_source).text=info[1]
            bt.setOnClickListener{
                startActivity(Intent(this, BackgroundsDetailsActivity::class.java).putExtra("Background",info[0]))
            }
            backgroundsList.add(bt)
        }
        backgroundsList.forEach { binding.ll.addView(it) }
    }

    @SuppressLint("CutPasteId")
    private fun generateInfo(){
        resources.getTextArray(R.array.background_info).toCollection(infoLinkedList)

        tempView = inflater.inflate(R.layout.universal_textview_starjedi_gold,binding.ll,false)
        tempView.findViewById<TextView>(R.id.textview).text=infoLinkedList.poll()
        infoList.add(tempView)
        for (i in 2..13){
            if (i!=9 && i!=12){
                tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.ll,false)
                tempView.findViewById<TextView>(R.id.headertext).text=infoLinkedList.poll()
                tempView.findViewById<TextView>(R.id.contenttext).text=infoLinkedList.poll()
                if (i==8) tempView.findViewById<TextView>(R.id.contenttext).typeface=resources.getFont(R.font.starjedi)
                infoList.add(tempView)
                if (i==4) infoList.add(inflater.inflate(R.layout.backgrounds_info_table_1,binding.ll,false))
                if (i==6) {
                    repeat(2) {
                        tempView = inflater.inflate(
                            R.layout.single_column_d8_table,
                            binding.ll,
                            false
                        )
                        val table = tempView.findViewById<TableLayout>(R.id.table)
                        tempView.findViewById<TextView>(R.id.title_col_2).text =
                            infoLinkedList.poll()
                        table.findViewById<TextView>(R.id.title_d).text =
                            getString(R.string.d6)

                        table.removeViews(2, 8)

                        for (j in 1..3) {
                            val row = inflater.inflate(
                                R.layout.single_column_d8_table_extra_row_gold,
                                table,
                                false
                            )
                            if (j % 2 == 1) row.background = null
                            val text = "${(2 * j - 1)}-${(2 * j)}"
                            row.findViewById<TextView>(R.id.text_row_extra_1).text = text
                            row.findViewById<TextView>(R.id.text_row_extra_2).text =
                                infoLinkedList.poll()
                            table.addView(row)
                        }
                        infoList.add(tempView)
                    }
                    tempView = inflater.inflate(R.layout.universal_textview_starjedi_gold,binding.ll,false)
                    tempView.findViewById<TextView>(R.id.textview).text=infoLinkedList.poll()
                    infoList.add(tempView)
                }
                if (i==7) infoList.add(inflater.inflate(R.layout.backgrounds_info_table_2,binding.ll,false))
            }
            else{
                tempView = inflater.inflate(R.layout.universal_textview_starjedi_gold,binding.ll,false)
                tempView.findViewById<TextView>(R.id.textview).text = infoLinkedList.poll()
                infoList.add(tempView)
            }
        }
        infoList.forEach { binding.ll.addView(it) }
        infoGenerated = true
    }

    private fun returntomain() {
        if(!atInfo) finish()
        else handleInfoSwitch()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}