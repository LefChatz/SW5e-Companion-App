package com.example.swtrial2.spells.adapterstuff

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.swtrial2.R


class SpellAdapter(private val mycontext: Context,private val dataset: MutableList<Spell>,private val favlist: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    val levels: List<Int> = listOf(0,1,2,3,4,5,6,7,8,9)
    class EmptySpellHolder(view: View) : ViewHolder(view){
        private val emptyrelout: RelativeLayout
        init{
            emptyrelout= view.findViewById(R.id.emptyrelout)
        }
    }
    class LeveledDividerHolder(view: View) : ViewHolder(view){
        val lvldividertextview: TextView
        init {
            lvldividertextview= view.findViewById(R.id.leveldividertextview)
        }
    }
    class SpellHolder(view: View) : ViewHolder(view){
        val spellname: TextView
        val spelldetails: TextView
        val castingtime: TextView
        val constlout: ConstraintLayout
        val imbutton: ImageButton

        init {

            imbutton = view.findViewById(R.id.table_spell_fav)
            constlout = view.findViewById(R.id.table_spell_constlout)
            spellname= view.findViewById(R.id.table_spell_name)
            castingtime= view.findViewById(R.id.table_spell_casting_time)
            spelldetails= view.findViewById(R.id.table_spell_details)
        }

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when(viewType){
            0->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.spell_button, viewGroup, false) ; SpellHolder(view) }
            1->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.spell_button_big, viewGroup, false) ; SpellHolder(view) }
            2->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.empty_button50sp, viewGroup, false) ; EmptySpellHolder(view) }
            3->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.spell_leveldivider, viewGroup, false) ; LeveledDividerHolder(view)
            }
            else->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.empty_button50sp, viewGroup, false) ; EmptySpellHolder(view)
            }
        }



    }
    override fun getItemViewType(position: Int): Int {
         with(dataset[position]){
             return when{
                    this.isBig -> 1
                    this.spellname=="Empty_Name" -> 2
                    this.spellname=="Level"->3
                    else ->{0}
                }
            }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when(viewHolder.itemViewType){
            2->{}
            3->{setlevel(viewHolder as LeveledDividerHolder,dataset[position].level)}
            else->{spell(viewHolder as SpellHolder,dataset[position])}
        }


    }
    fun setSpellList(updatedspelllist: MutableList<Spell>){
        val diffResult = DiffUtil.calculateDiff(SpellDiffUtilCallback(dataset,updatedspelllist))
        dataset.clear()
        dataset.addAll(updatedspelllist)
        diffResult.dispatchUpdatesTo(this)

    }
    override fun getItemCount() = dataset.size
    private fun updatefav(spell: Spell, spellbutton: View){
        if(spell.spellname !in favlist){
            favlist.add(spell.spellname)
            spellbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegoldtrue)
        }
        else{
            favlist.remove(spell.spellname)
            spellbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegold)
        }
        with(mycontext.getSharedPreferences("favlist",Context.MODE_PRIVATE).edit()){
            putStringSet("favlist",favlist.toMutableSet())
            apply()
        }
    }

    @SuppressLint("DiscouragedApi")
    fun spell(view: SpellHolder, spell: Spell){
        view.spellname.text=spell.printedname
        view.spelldetails.text= buildSpannedString{
            append(when(spell.level){0->"At-will";1->"1st-level";2->"2nd-level";3->"3rd-level";else->"${spell.level}th-level"})
            append(" ");append(spell.side)}
        view.castingtime.text= spell.castingtime
        view.constlout.setOnClickListener{
            mycontext.startActivity(Intent(mycontext, SpellDetailsActivity::class.java).putExtra("Spell",spell))
        }
        view.imbutton.setOnClickListener {
            updatefav(spell,view.imbutton)
        }
        if(spell.spellname in favlist){
            view.imbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegoldtrue)
        }
        else{
            view.imbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegold)
        }
    }
    private fun setlevel(view: LeveledDividerHolder, lvl: Int){
        when(lvl){
            0->{view.lvldividertextview.text=mycontext.getText(R.string.at_will)}
            1->{view.lvldividertextview.text=mycontext.getText(R.string.first_level)}
            2->{view.lvldividertextview.text=mycontext.getText(R.string.second_level)}
            3->{view.lvldividertextview.text=mycontext.getText(R.string.third_level)}
            4->{view.lvldividertextview.text=mycontext.getText(R.string.fourth_level)}
            5->{view.lvldividertextview.text=mycontext.getText(R.string.fifth_level)}
            6->{view.lvldividertextview.text=mycontext.getText(R.string.sixth_level)}
            7->{view.lvldividertextview.text=mycontext.getText(R.string.seventh_level)}
            8->{view.lvldividertextview.text=mycontext.getText(R.string.eighth_level)}
            9->{view.lvldividertextview.text=mycontext.getText(R.string.nineth_level)}
            else->{

            }
        }
    }
}

