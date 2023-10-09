package com.example.swtrial2.currentlyuseless;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class SpellXmlParser {

    public static ArrayList<Spell> parse(Activity activity, int xmlResourceId) throws XmlPullParserException, IOException{

        Resources res = activity.getResources();
        XmlResourceParser spelldataXmlParser = res.getXml(xmlResourceId);

        ArrayList<Spell> spelllist = new ArrayList<>();
        ArrayList<String> xmlTagStack = new ArrayList<>();
        Spell currentspell = null;
        ArrayList<String> spelldata = new ArrayList<>();
        spelldataXmlParser.next();
        int eventType= spelldataXmlParser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            //Start Tag
            if(eventType == XmlPullParser.START_TAG){
                String tagName = spelldataXmlParser.getName();
                xmlTagStack.add(tagName);
                if(tagName.equals("spell")){
                    currentspell=null;
                    spelldata.clear();
                }
            }
            //End Tag
            else if (eventType == XmlPullParser.END_TAG){
                String tagName = spelldataXmlParser.getName();
                xmlTagStack.remove(xmlTagStack.size()-1);
                if(tagName.equals("spell")){
                    currentspell = new Spell(spelldata.get(0),spelldata.get(1),spelldata.get(2),spelldata.get(3),Integer.parseInt(spelldata.get(4)),Boolean.parseBoolean(spelldata.get(5)),Boolean.parseBoolean(spelldata.get(6)));
                    spelllist.add(currentspell);
                }
            }
            //text
            else if (eventType == XmlPullParser.TEXT){
                String currentTag = xmlTagStack.get(xmlTagStack.size()-1);
                String text = spelldataXmlParser.getText();
                if(currentTag.equals("spellname")){
                    spelldata.set(0,text);
                }
                else if (currentTag.equals("castingtime")){
                    spelldata.set(1,text);
                }
                else if (currentTag.equals("side")){
                    spelldata.set(2,text);
                }
                else if (currentTag.equals("activityname")){
                    spelldata.set(3,text);
                }
                else if (currentTag.equals("level")){
                    spelldata.set(4,text);
                }
                else if (currentTag.equals("concentration")){
                    spelldata.set(5,text);
                }
                else if (currentTag.equals("prerequisite")){
                    spelldata.set(6,text);
                }
            }
            eventType= spelldataXmlParser.next();
        }

        return spelllist;
    }
}
