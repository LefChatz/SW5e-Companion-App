package com.example.sw5ecompanionapp.backgrounds

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.sw5ecompanionapp.R
import java.util.LinkedList


fun generateDetails(background: String, ll: LinearLayoutCompat, resources: Resources, inflater: LayoutInflater, packageName: String){
    var tempText: TextView
    var tempView: View

    @SuppressLint("DiscouragedApi")
    val identifier = resources.getIdentifier(background,"array",packageName)

    if (identifier==0) {
        tempText = inflater.inflate(R.layout.universal_textview_nofont_gold,ll,false).findViewById(R.id.textview)
        tempText.text = resources.getString(R.string.error_please_report_this_to_todo)
        ll.addView(tempText)
        return
    }

    val detailsHeap = LinkedList<CharSequence>()
    resources.getTextArray(identifier).toCollection(detailsHeap)

    val type = detailsHeap.poll()?.toString()?.toInt()

    ll.findViewById<TextView>(R.id.SourceBook).text = detailsHeap.poll()
    if (type==0) {
        tempText = inflater.inflate(R.layout.universal_textview_nofont_gold,ll,false).findViewById(R.id.textview)
        tempText.text = detailsHeap.poll()
        ll.addView(tempText)
    }
    else {
        tempText = inflater.inflate(R.layout.universal_textview_starjedi_gold, ll, false).findViewById(R.id.textview)
        tempText.text = detailsHeap.poll()
        ll.addView(tempText)

        tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table, ll, false)
        tempView.findViewById<TextView>(R.id.title_col_2).text = detailsHeap.poll()
        tempView.findViewById<TextView>(R.id.text_row_1).text = detailsHeap.poll()
        tempView.findViewById<TextView>(R.id.text_row_2).text = detailsHeap.poll()
        tempView.findViewById<TextView>(R.id.text_row_3).text = detailsHeap.poll()
        tempView.findViewById<TextView>(R.id.text_row_4).text = detailsHeap.poll()
        tempView.findViewById<TextView>(R.id.text_row_5).text = detailsHeap.poll()
        tempView.findViewById<TextView>(R.id.text_row_6).text = detailsHeap.poll()
        if (type!=3) {
            tempView.findViewById<TextView>(R.id.text_row_7).text = detailsHeap.poll()
            tempView.findViewById<TextView>(R.id.text_row_8).text = detailsHeap.poll()
        }
        else {
            tempView.findViewById<TextView>(R.id.title_d).text = "d6"
            tempView.findViewById<TableLayout>(R.id.table).removeViews(8, 2)
        }
        if(type==2){
            tempView.findViewById<TextView>(R.id.title_d).text = "d10"
            val table = tempView.findViewById<TableLayout>(R.id.table)
            val extraRows = inflater.inflate(R.layout.backgrounds_single_column_d8_table_2_extra_rows, table, true)
            extraRows.findViewById<TextView>(R.id.text_row_9).text = detailsHeap.poll()
            extraRows.findViewById<TextView>(R.id.text_row_10).text = detailsHeap.poll()
        }
        else if(type==4){
            tempView.findViewById<TextView>(R.id.title_d).text = "d16"
            val table = tempView.findViewById<TableLayout>(R.id.table)
            var extraRows = inflater.inflate(R.layout.backgrounds_single_column_d8_table_2_extra_rows, table, true)
            extraRows.findViewById<TextView>(R.id.text_row_9).text = detailsHeap.poll()
            extraRows.findViewById<TextView>(R.id.text_row_10).text = detailsHeap.poll()
            extraRows = inflater.inflate(R.layout.backgrounds_single_column_d8_table_2_extra_rows, table, true)
            extraRows.findViewById<TextView>(R.id.text_row_9).text = detailsHeap.poll()
            extraRows.findViewById<TextView>(R.id.text_row_10).text = detailsHeap.poll()
            extraRows = inflater.inflate(R.layout.backgrounds_single_column_d8_table_2_extra_rows, table, true)
            extraRows.findViewById<TextView>(R.id.text_row_9).text = detailsHeap.poll()
            extraRows.findViewById<TextView>(R.id.text_row_10).text = detailsHeap.poll()
            extraRows = inflater.inflate(R.layout.backgrounds_single_column_d8_table_2_extra_rows, table, true)
            extraRows.findViewById<TextView>(R.id.text_row_9).text = detailsHeap.poll()
            extraRows.findViewById<TextView>(R.id.text_row_10).text = detailsHeap.poll()
        }
        if (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).width<resources.displayMetrics.widthPixels) (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).layoutParams as LinearLayoutCompat.LayoutParams).gravity=1
        ll.addView(tempView)
    }

    tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview, ll, false)
    tempView.findViewById<TextView>(R.id.headertext).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.contenttext).text = detailsHeap.poll()
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview, ll, false)
    tempView.findViewById<TextView>(R.id.headertext).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.contenttext).text = detailsHeap.poll()
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_double_column_d8_table, ll, false)
    tempView.findViewById<TextView>(R.id.text_1_1).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_2_1).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_3_1).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_4_1).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_1_2).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_2_2).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_3_2).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_4_2).text = detailsHeap.poll()
    if (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).width<resources.displayMetrics.widthPixels) (tempView.findViewById<HorizontalScrollView>(R.id.hscroll).layoutParams as LinearLayoutCompat.LayoutParams).gravity=1
    ll.addView(tempView)

    tempText = inflater.inflate(R.layout.universal_textview_starjedi_gold, ll, false).findViewById(R.id.textview)
    tempText.text = detailsHeap.poll()
    ll.addView(tempText)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table, ll, false)
    tempView.findViewById<TextView>(R.id.title_col_2).text = resources.getString(R.string.personality_trait)
    tempView.findViewById<TextView>(R.id.text_row_1).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_2).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_3).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_4).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_5).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_6).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_7).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_8).text = detailsHeap.poll()
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table, ll, false)
    tempView.findViewById<TextView>(R.id.title_col_2).text = resources.getString(R.string.ideal)
    tempView.findViewById<TextView>(R.id.text_row_1).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_2).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_3).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_4).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_5).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_6).text = detailsHeap.poll()
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8, 2)
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table, ll, false)
    tempView.findViewById<TextView>(R.id.title_col_2).text = resources.getString(R.string.bond)
    tempView.findViewById<TextView>(R.id.text_row_1).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_2).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_3).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_4).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_5).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_6).text = detailsHeap.poll()
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8, 2)
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table, ll, false)
    tempView.findViewById<TextView>(R.id.title_col_2).text = resources.getString(R.string.flaw)
    tempView.findViewById<TextView>(R.id.text_row_1).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_2).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_3).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_4).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_5).text = detailsHeap.poll()
    tempView.findViewById<TextView>(R.id.text_row_6).text = detailsHeap.poll()
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8, 2)
    ll.addView(tempView)
}