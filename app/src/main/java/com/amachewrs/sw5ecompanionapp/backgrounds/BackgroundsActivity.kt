package com.amachewrs.sw5ecompanionapp.backgrounds

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.SW5ECompanionApp
import com.amachewrs.sw5ecompanionapp.databinding.BackgroundsBinding
import java.util.LinkedList
import kotlin.properties.Delegates

class BackgroundsActivity : AppCompatActivity() {

    private lateinit var binding: BackgroundsBinding
    private lateinit var inflater: LayoutInflater
    private lateinit var tempView: View
    private var mode by Delegates.notNull<Int>()
    private lateinit var backgrounds: Array<String>
    private lateinit var backgroundInfo: LinkedList<CharSequence>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BackgroundsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflater=layoutInflater

        mode=0
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        backgrounds = resources.getStringArray(R.array.backgrounds)

        backgroundInfo = LinkedList()

        generateBackgrounds()

        binding.BackButton.setOnClickListener { returntomain() }

        binding.infobutton.setOnClickListener { generateBackgroundInfo() }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    private fun generateBackgrounds(){
        backgrounds.forEach {
            val bt = inflater.inflate(R.layout.background_button,binding.ll,false)
            val info = it.split(" ")
            bt.findViewById<TextView>(R.id.background_name).text=info[0].replace("_"," ").replace(".","-")
            if(info[0]=="retired_adventurer") bt.findViewById<TextView>(R.id.background_name).text=getString(R.string.backgrounds_un_retired_adventurer)
            bt.findViewById<TextView>(R.id.background_source).text=info[1]
            bt.setOnClickListener{
                startActivity(Intent(this, BackgroundsDetailsActivity::class.java).putExtra("Background",info[0]))
            }
            binding.ll.addView(bt)
        }
    }

    @SuppressLint("CutPasteId")
    private fun generateBackgroundInfo(){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        if(mode==0){
            binding.ll.removeAllViews()
            resources.getTextArray(R.array.background_info).toCollection(backgroundInfo)

            tempView = inflater.inflate(R.layout.universal_textview_starjedi_gold,binding.ll,false)
            tempView.findViewById<TextView>(R.id.textview).text=backgroundInfo.poll()
            binding.ll.addView(tempView)
            for (i in 2..13){
                if (i!=9 && i!=12){
                    tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.ll,false)
                    tempView.findViewById<TextView>(R.id.headertext).text=backgroundInfo.poll()
                    tempView.findViewById<TextView>(R.id.contenttext).text=backgroundInfo.poll()
                    if (i==8) tempView.findViewById<TextView>(R.id.contenttext).typeface=resources.getFont(R.font.starjedi)
                    binding.ll.addView(tempView)
                    if (i==4) inflater.inflate(R.layout.backgrounds_info_table_1,binding.ll,true)
                    if (i==6) {
                        repeat(2) {
                            tempView = inflater.inflate(
                                R.layout.single_column_d8_table,
                                binding.ll,
                                false
                            )
                            val table = tempView.findViewById<TableLayout>(R.id.table)
                            tempView.findViewById<TextView>(R.id.title_col_2).text =
                                backgroundInfo.poll()
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
                                    backgroundInfo.poll()
                                table.addView(row)
                            }
                            binding.ll.addView(tempView)
                        }
                        tempView = inflater.inflate(R.layout.universal_textview_starjedi_gold,binding.ll,false)
                        tempView.findViewById<TextView>(R.id.textview).text=backgroundInfo.poll()
                        binding.ll.addView(tempView)
                    }
                    if (i==7) inflater.inflate(R.layout.backgrounds_info_table_2,binding.ll,true)
                }
                else{
                    tempView = inflater.inflate(R.layout.universal_textview_starjedi_gold,binding.ll,false)
                    tempView.findViewById<TextView>(R.id.textview).text = backgroundInfo.poll()
                    binding.ll.addView(tempView)
                }
            }
            mode=1
        }
        else{
            Toast.makeText(this,"Already at Species info",Toast.LENGTH_SHORT)
                .show()
        }
    }

    //Menu creation: Currently unnecessary

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_species,menu)
        val drawable= AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        binding.toolbar.overflowIcon=drawable
        return super.onCreateOptionsMenu(menu)
    }*/

    private fun returntomain() {
        if(mode==0) finish()
        else{
            binding.scrolly.scrollTo(0,0)
            binding.scrolly.fling(0)
            binding.ll.removeAllViews()

            generateBackgrounds()

            mode=0
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}