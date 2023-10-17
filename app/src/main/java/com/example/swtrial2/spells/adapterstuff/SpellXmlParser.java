package com.example.swtrial2.spells.adapterstuff;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SpellXmlParser {

    public ArrayList<Spell> parse(Activity activity, int xmlResourceId) throws XmlPullParserException, IOException{

        Resources res = activity.getResources();
        XmlResourceParser spelldataXmlParser = res.getXml(xmlResourceId);

        ArrayList<Spell> spelllist = new ArrayList<>();
        ArrayList<String> xmlTagStack = new ArrayList<>();
        Spell currentspell;
        ArrayList<String> spelldata = new ArrayList<>(Arrays.asList("1","2","3","4","5","6","7","8","9","10"));
        spelldataXmlParser.next();
        int eventType= spelldataXmlParser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            //Start Tag
            if(eventType == XmlPullParser.START_TAG){
                String tagName = spelldataXmlParser.getName();
                xmlTagStack.add(tagName);
                /*if(tagName.contains("spell")){
                    spelldata.forEach(it-> spelldata.set(spelldata.indexOf(it),"mple"));
                }*/
            }
            //End Tag
            else if (eventType == XmlPullParser.END_TAG){
                String tagName = spelldataXmlParser.getName();
                xmlTagStack.remove(xmlTagStack.size()-1);
                if(tagName.equals("spell")){
                    currentspell = new Spell(spelldata.get(0),spelldata.get(1),spelldata.get(2),spelldata.get(3),Integer.parseInt(spelldata.get(4)),Boolean.parseBoolean(spelldata.get(5)),Boolean.parseBoolean(spelldata.get(6)),Boolean.parseBoolean(spelldata.get(7)),spelldata.get(8),spelldata.get(9));
                    spelllist.add(currentspell);
                }
            }
            //text
            else if (eventType == XmlPullParser.TEXT){
                String currentTag = xmlTagStack.get(xmlTagStack.size()-1);
                String text = spelldataXmlParser.getText();
                switch (currentTag) {
                    case "name":
                        spelldata.set(0, text);
                        break;
                    case "printname":
                        spelldata.set(1, text);
                        break;
                    case "castingTime":
                        spelldata.set(2, text);
                        break;
                    case "side":
                        spelldata.set(3, text);
                        break;
                    case "level":
                        spelldata.set(4, text);
                        break;
                    case "concentration":
                        spelldata.set(5, text);
                        break;
                    case "prerequisite":
                        spelldata.set(6, text);
                        break;
                    case "detailsText":
                        spelldata.set(8, text);
                        break;
                    case "isBig":
                        spelldata.set(7, text);
                        break;
                    case "levelInFull":
                        spelldata.set(9, text);
                        break;

                }
            }
            eventType= spelldataXmlParser.next();
        }

        return spelllist;
    }
}
