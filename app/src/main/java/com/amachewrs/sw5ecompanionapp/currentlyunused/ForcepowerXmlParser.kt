package com.amachewrs.sw5ecompanionapp.currentlyunused

import android.app.Activity
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.text.buildSpannedString
import com.amachewrs.sw5ecompanionapp.forcecasting.Forcepower
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.Arrays

class ForcepowerXmlParser {
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(activity: Activity, xmlResourceId: Int): ArrayList<Forcepower> {
        val res = activity.resources
        val spelldataXmlParser = res.getXml(xmlResourceId)
        val spelllist = ArrayList<Forcepower>()
        val xmlTagStack = ArrayList<String>()
        var currentspell: Forcepower
        val spelldata = ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
        spelldataXmlParser.next()
        var eventType = spelldataXmlParser.eventType
        val gold=Color.parseColor("#D4af37")
        while (eventType != XmlPullParser.END_DOCUMENT) {
            //Start Tag
            if (eventType == XmlPullParser.START_TAG) {
                val tagName = spelldataXmlParser.name
                xmlTagStack.add(tagName)
            } else if (eventType == XmlPullParser.END_TAG) {
                val tagName = spelldataXmlParser.name
                xmlTagStack.removeAt(xmlTagStack.size - 1)
                if (tagName == "spell") {
                    currentspell = Forcepower(spelldata[0],spelldata[1],spelldata[2],spelldata[3],spelldata[4].toInt(),spelldata[5].toBoolean(),spelldata[6].toBoolean(),spelldata[7].toBoolean(),spelldata[8])
                    val editedspell = currentspell
                    with(currentspell.side){
                        when{
                            this.contains("Dark")->{
                                editedspell.printedname = buildSpannedString {val spanit=SpannableString(currentspell.printedname);spanit.setSpan(ForegroundColorSpan(Color.RED),0,currentspell.printedname.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);append(spanit)}
                                editedspell.side= buildSpannedString {val spanit=SpannableString(currentspell.side);spanit.setSpan(ForegroundColorSpan(Color.RED),0,4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);append(spanit)}
                            }
                            this.contains("Light")->{
                                editedspell.printedname= buildSpannedString {val spanit=SpannableString(currentspell.printedname);spanit.setSpan(ForegroundColorSpan(Color.CYAN),0,currentspell.printedname.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);append(spanit)}
                                editedspell.side= buildSpannedString {val spanit=SpannableString(currentspell.side);spanit.setSpan(ForegroundColorSpan(Color.CYAN),0,5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);append(spanit)}
                            }
                            this.contains("Universal")->{
                                editedspell.printedname= buildSpannedString {val spanit=SpannableString(currentspell.printedname);spanit.setSpan(ForegroundColorSpan(gold),0,currentspell.printedname.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);append(spanit)}
                                editedspell.side= buildSpannedString {val spanit=SpannableString(currentspell.side);spanit.setSpan(ForegroundColorSpan(gold),0,this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);append(spanit)}
                            }
                            else->{}
                        }
                    }

                    spelllist.add(editedspell)
                }
            } else if (eventType == XmlPullParser.TEXT) {
                val currentTag = xmlTagStack[xmlTagStack.size - 1]
                val text = spelldataXmlParser.text
                when (currentTag) {
                    "name" -> spelldata[0] = text
                    "printname" -> spelldata[1] = text
                    "castingtime" -> spelldata[2] = text
                    "side" -> spelldata[3] = text
                    "level" -> spelldata[4] = text
                    "concentration" -> spelldata[5] = text
                    "prerequisite" -> spelldata[6] = text
                    "isBig" -> spelldata[7] = text
                    "levelInFull" -> spelldata[8] = text
                    "detailsText" -> spelldata[9] = text
                }
            }
            eventType = spelldataXmlParser.next()
        }
        return spelllist
    }
}