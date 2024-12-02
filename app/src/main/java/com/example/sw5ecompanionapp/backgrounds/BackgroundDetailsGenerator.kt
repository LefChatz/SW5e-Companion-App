package com.example.sw5ecompanionapp.backgrounds

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.sw5ecompanionapp.R
import java.util.LinkedList


fun generateDetails(ll: LinearLayoutCompat, resources: Resources, inflater: LayoutInflater, detailsHeap: LinkedList<CharSequence>){
    var tempText: TextView
    var tempView: View

    val type = detailsHeap.poll()?.toString()?.toInt()

    if (type==0) {
        tempText = inflater.inflate(R.layout.universal_textview_nofont_gold,ll,false).findViewById(R.id.textview)
        tempText.text = detailsHeap.poll()
        ll.addView(tempText)
    }
    else {
        tempText = inflater.inflate(R.layout.universal_textview_starjedi_gold, ll, false)
            .findViewById(R.id.textview)
        tempText.text = detailsHeap.poll()
        ll.addView(tempText)

        if (type==1 || type==3) {
            tempView = inflater.inflate(R.layout.single_column_d8_table, ll, false)
            val table = tempView.findViewById<TableLayout>(R.id.table)
            val lines = detailsHeap.poll()?.toString()!!.toInt()
            tempView.findViewById<TextView>(R.id.title_col_2).text = detailsHeap.poll()

            val tempStr = "d$lines"
            table.findViewById<TextView>(R.id.title_d).text=tempStr

            table.removeViews(2, 8)

            for (i in 1..lines) {
                val row = inflater.inflate(R.layout.single_column_d8_table_extra_row_gold, table, false)
                if (i % 2 == 1) row.background = null
                row.findViewById<TextView>(R.id.text_row_extra_1).text = i.toString()
                row.findViewById<TextView>(R.id.text_row_extra_2).text = detailsHeap.poll()
                table.addView(row)
            }

            if (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).width<resources.displayMetrics.widthPixels) (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).layoutParams as LinearLayoutCompat.LayoutParams).gravity=1
            ll.addView(tempView)
        }
    }

    tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview, ll, false)
    tempView.findViewById<TextView>(R.id.headertext).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.contenttext).text = detailsHeap.poll()
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview, ll, false)
    tempView.findViewById<TextView>(R.id.headertext).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.contenttext).text = detailsHeap.poll()
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.two_column_d8_table, ll, false)

    // Background Feat
    var tableTextArray= arrayOf(tempView.findViewById<TextView>(R.id.text_1_1),tempView.findViewById(R.id.text_2_1),tempView.findViewById(R.id.text_3_1),tempView.findViewById(R.id.text_4_1),tempView.findViewById(R.id.text_1_2),tempView.findViewById(R.id.text_2_2),tempView.findViewById(R.id.text_3_2),tempView.findViewById(R.id.text_4_2))
    tableTextArray.forEach {it.text=detailsHeap.poll()}

    if (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).width<resources.displayMetrics.widthPixels) (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).layoutParams as LinearLayoutCompat.LayoutParams).gravity=1
    ll.addView(tempView)

    tempText = inflater.inflate(R.layout.universal_textview_starjedi_gold, ll, false).findViewById(R.id.textview)
    tempText.text = detailsHeap.poll()
    ll.addView(tempText)

    // Personality traits
    tempView = inflater.inflate(R.layout.single_column_d8_table, ll, false)
    tempView.findViewById<TextView>(R.id.title_col_2).text = resources.getString(R.string.personality_trait)
    tableTextArray = arrayOf(tempView.findViewById(R.id.text_row_1),tempView.findViewById(R.id.text_row_2),tempView.findViewById(R.id.text_row_3),tempView.findViewById(R.id.text_row_4),tempView.findViewById(R.id.text_row_5),tempView.findViewById(R.id.text_row_6))
    if (type!=3){
        tableTextArray+=(arrayOf(tempView.findViewById(R.id.text_row_7),tempView.findViewById(R.id.text_row_8)))
    }
    else{
        tempView.findViewById<TableLayout>(R.id.table).removeViews(8, 2)
    }
    tableTextArray.forEach { it.text=detailsHeap.poll() }
    ll.addView(tempView)


    tempView = inflater.inflate(R.layout.single_column_d8_table, ll, false)
    tempView.findViewById<TextView>(R.id.title_col_2).text = resources.getString(R.string.ideal)
    tableTextArray = arrayOf(tempView.findViewById(R.id.text_row_1),tempView.findViewById(R.id.text_row_2),tempView.findViewById(R.id.text_row_3),tempView.findViewById(R.id.text_row_4),tempView.findViewById(R.id.text_row_5),tempView.findViewById(R.id.text_row_6))
    tableTextArray.forEach { it.text=detailsHeap.poll() }
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8, 2)
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.single_column_d8_table, ll, false)
    tempView.findViewById<TextView>(R.id.title_col_2).text = resources.getString(R.string.bond)
    tableTextArray = arrayOf(tempView.findViewById(R.id.text_row_1),tempView.findViewById(R.id.text_row_2),tempView.findViewById(R.id.text_row_3),tempView.findViewById(R.id.text_row_4),tempView.findViewById(R.id.text_row_5),tempView.findViewById(R.id.text_row_6))
    tableTextArray.forEach { it.text=detailsHeap.poll() }
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8, 2)
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.single_column_d8_table, ll, false)
    tempView.findViewById<TextView>(R.id.title_col_2).text = resources.getString(R.string.flaw)
    tableTextArray = arrayOf(tempView.findViewById(R.id.text_row_1),tempView.findViewById(R.id.text_row_2),tempView.findViewById(R.id.text_row_3),tempView.findViewById(R.id.text_row_4),tempView.findViewById(R.id.text_row_5),tempView.findViewById(R.id.text_row_6))
    tableTextArray.forEach { it.text=detailsHeap.poll() }
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8, 2)
    ll.addView(tempView)
}