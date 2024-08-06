package com.example.sw5ecompanionapp.backgrounds

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.sw5ecompanionapp.R

private lateinit var detailsStringArray: Array<CharSequence>

fun generateAgent(ll: LinearLayoutCompat,resources: Resources,inflater: LayoutInflater) {
    var tempText: TextView
    var tempView: View

    detailsStringArray=resources.getTextArray(R.array.agent)

    tempText = inflater.inflate(R.layout.universal_textview_nofont_gold,ll,false).findViewById<TextView>(R.id.textview)
    tempText.text=detailsStringArray[0]
    ll.addView(tempText)

    tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
    tempView.findViewById<TextView>(R.id.headertext).text=detailsStringArray[1]
    tempView.findViewById<TextView>(R.id.contenttext).text=detailsStringArray[2]
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
    tempView.findViewById<TextView>(R.id.headertext).text=detailsStringArray[3]
    tempView.findViewById<TextView>(R.id.contenttext).text=detailsStringArray[4]
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_double_column_d8_table,ll,false)
    tempView.findViewById<TextView>(R.id.text_1_1).text=detailsStringArray[5]
    tempView.findViewById<TextView>(R.id.text_2_1).text=detailsStringArray[6]
    tempView.findViewById<TextView>(R.id.text_3_1).text=detailsStringArray[7]
    tempView.findViewById<TextView>(R.id.text_4_1).text=detailsStringArray[8]
    tempView.findViewById<TextView>(R.id.text_1_2).text=detailsStringArray[9]
    tempView.findViewById<TextView>(R.id.text_2_2).text=detailsStringArray[10]
    tempView.findViewById<TextView>(R.id.text_3_2).text=detailsStringArray[11]
    tempView.findViewById<TextView>(R.id.text_4_2).text=detailsStringArray[12]
    ll.addView(tempView)

    tempText = inflater.inflate(R.layout.universal_textview_starjedi_gold,ll,false).findViewById<TextView>(R.id.textview)
    tempText.text=detailsStringArray[13]
    ll.addView(tempText)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table,ll,false)
    tempView.findViewById<TextView>(R.id.title_col_2).text="Personality Trait"
    tempView.findViewById<TextView>(R.id.text_row_1).text=detailsStringArray[14]
    tempView.findViewById<TextView>(R.id.text_row_2).text=detailsStringArray[15]
    tempView.findViewById<TextView>(R.id.text_row_3).text=detailsStringArray[16]
    tempView.findViewById<TextView>(R.id.text_row_4).text=detailsStringArray[17]
    tempView.findViewById<TextView>(R.id.text_row_5).text=detailsStringArray[18]
    tempView.findViewById<TextView>(R.id.text_row_6).text=detailsStringArray[19]
    tempView.findViewById<TextView>(R.id.text_row_7).text=detailsStringArray[20]
    tempView.findViewById<TextView>(R.id.text_row_8).text=detailsStringArray[21]
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table,ll,false)
    tempView.findViewById<TextView>(R.id.title_col_2).text="Ideal"
    tempView.findViewById<TextView>(R.id.text_row_1).text=detailsStringArray[22]
    tempView.findViewById<TextView>(R.id.text_row_2).text=detailsStringArray[23]
    tempView.findViewById<TextView>(R.id.text_row_3).text=detailsStringArray[24]
    tempView.findViewById<TextView>(R.id.text_row_4).text=detailsStringArray[25]
    tempView.findViewById<TextView>(R.id.text_row_5).text=detailsStringArray[26]
    tempView.findViewById<TextView>(R.id.text_row_6).text=detailsStringArray[27]
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8,2)
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table,ll,false)
    tempView.findViewById<TextView>(R.id.title_col_2).text="Bond"
    tempView.findViewById<TextView>(R.id.text_row_1).text=detailsStringArray[28]
    tempView.findViewById<TextView>(R.id.text_row_2).text=detailsStringArray[29]
    tempView.findViewById<TextView>(R.id.text_row_3).text=detailsStringArray[20]
    tempView.findViewById<TextView>(R.id.text_row_4).text=detailsStringArray[31]
    tempView.findViewById<TextView>(R.id.text_row_5).text=detailsStringArray[32]
    tempView.findViewById<TextView>(R.id.text_row_6).text=detailsStringArray[33]
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8,2)
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table,ll,false)
    tempView.findViewById<TextView>(R.id.title_col_2).text="Flaw"
    tempView.findViewById<TextView>(R.id.text_row_1).text=detailsStringArray[34]
    tempView.findViewById<TextView>(R.id.text_row_2).text=detailsStringArray[35]
    tempView.findViewById<TextView>(R.id.text_row_3).text=detailsStringArray[36]
    tempView.findViewById<TextView>(R.id.text_row_4).text=detailsStringArray[37]
    tempView.findViewById<TextView>(R.id.text_row_5).text=detailsStringArray[38]
    tempView.findViewById<TextView>(R.id.text_row_6).text=detailsStringArray[39]
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8,2)
    ll.addView(tempView)
}
fun generateBountyHunter(ll: LinearLayoutCompat,resources: Resources,inflater: LayoutInflater) {
    var tempText: TextView
    var tempView: View

    detailsStringArray=resources.getTextArray(R.array.bounty_hunter)

    tempText = inflater.inflate(R.layout.universal_textview_nofont_gold,ll,false).findViewById<TextView>(R.id.textview)
    tempText.text=detailsStringArray[0]
    ll.addView(tempText)

    tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
    tempView.findViewById<TextView>(R.id.headertext).text=detailsStringArray[1]
    tempView.findViewById<TextView>(R.id.contenttext).text=detailsStringArray[2]
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.universal_title_goldbar_text_textview,ll,false)
    tempView.findViewById<TextView>(R.id.headertext).text=detailsStringArray[3]
    tempView.findViewById<TextView>(R.id.contenttext).text=detailsStringArray[4]
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_double_column_d8_table,ll,false)
    tempView.findViewById<TextView>(R.id.text_1_1).text=detailsStringArray[5]
    tempView.findViewById<TextView>(R.id.text_2_1).text=detailsStringArray[6]
    tempView.findViewById<TextView>(R.id.text_3_1).text=detailsStringArray[7]
    tempView.findViewById<TextView>(R.id.text_4_1).text=detailsStringArray[8]
    tempView.findViewById<TextView>(R.id.text_1_2).text=detailsStringArray[9]
    tempView.findViewById<TextView>(R.id.text_2_2).text=detailsStringArray[10]
    tempView.findViewById<TextView>(R.id.text_3_2).text=detailsStringArray[11]
    tempView.findViewById<TextView>(R.id.text_4_2).text=detailsStringArray[12]
    ll.addView(tempView)

    tempText = inflater.inflate(R.layout.universal_textview_starjedi_gold,ll,false).findViewById<TextView>(R.id.textview)
    tempText.text=detailsStringArray[13]
    ll.addView(tempText)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table,ll,false)
    tempView.findViewById<TextView>(R.id.title_col_2).text="Personality Trait"
    tempView.findViewById<TextView>(R.id.text_row_1).text=detailsStringArray[14]
    tempView.findViewById<TextView>(R.id.text_row_2).text=detailsStringArray[15]
    tempView.findViewById<TextView>(R.id.text_row_3).text=detailsStringArray[16]
    tempView.findViewById<TextView>(R.id.text_row_4).text=detailsStringArray[17]
    tempView.findViewById<TextView>(R.id.text_row_5).text=detailsStringArray[18]
    tempView.findViewById<TextView>(R.id.text_row_6).text=detailsStringArray[19]
    tempView.findViewById<TextView>(R.id.text_row_7).text=detailsStringArray[20]
    tempView.findViewById<TextView>(R.id.text_row_8).text=detailsStringArray[21]
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table,ll,false)
    tempView.findViewById<TextView>(R.id.title_col_2).text="Ideal"
    tempView.findViewById<TextView>(R.id.text_row_1).text=detailsStringArray[22]
    tempView.findViewById<TextView>(R.id.text_row_2).text=detailsStringArray[23]
    tempView.findViewById<TextView>(R.id.text_row_3).text=detailsStringArray[24]
    tempView.findViewById<TextView>(R.id.text_row_4).text=detailsStringArray[25]
    tempView.findViewById<TextView>(R.id.text_row_5).text=detailsStringArray[26]
    tempView.findViewById<TextView>(R.id.text_row_6).text=detailsStringArray[27]
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8,2)
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table,ll,false)
    tempView.findViewById<TextView>(R.id.title_col_2).text="Bond"
    tempView.findViewById<TextView>(R.id.text_row_1).text=detailsStringArray[28]
    tempView.findViewById<TextView>(R.id.text_row_2).text=detailsStringArray[29]
    tempView.findViewById<TextView>(R.id.text_row_3).text=detailsStringArray[20]
    tempView.findViewById<TextView>(R.id.text_row_4).text=detailsStringArray[31]
    tempView.findViewById<TextView>(R.id.text_row_5).text=detailsStringArray[32]
    tempView.findViewById<TextView>(R.id.text_row_6).text=detailsStringArray[33]
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8,2)
    ll.addView(tempView)

    tempView = inflater.inflate(R.layout.backgrounds_single_column_d8_table,ll,false)
    tempView.findViewById<TextView>(R.id.title_col_2).text="Flaw"
    tempView.findViewById<TextView>(R.id.text_row_1).text=detailsStringArray[34]
    tempView.findViewById<TextView>(R.id.text_row_2).text=detailsStringArray[35]
    tempView.findViewById<TextView>(R.id.text_row_3).text=detailsStringArray[36]
    tempView.findViewById<TextView>(R.id.text_row_4).text=detailsStringArray[37]
    tempView.findViewById<TextView>(R.id.text_row_5).text=detailsStringArray[38]
    tempView.findViewById<TextView>(R.id.text_row_6).text=detailsStringArray[39]
    tempView.findViewById<TableLayout>(R.id.table).removeViews(8,2)
    ll.addView(tempView)
}