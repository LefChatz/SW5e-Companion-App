package com.example.sw5ecompanionapp.techcasting

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sw5ecompanionapp.R


class TechcastingAdapter(private val myContext: Context, private val dataset: MutableList<Techpower>, private val favlist: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    val levels: List<Int> = listOf(0,1,2,3,4,5,6,7,8,9)
    class NoTechpowerHolder(view: View) : ViewHolder(view){
        private val noTechpowerText: TextView
        init {
            noTechpowerText= view.findViewById(R.id.techcasting_nosuchtechpowertext)
        }
        init{
        }
    }
    class LeveledDividerHolder(view: View) : ViewHolder(view){
        val lvldividertextview: TextView
        init {
            lvldividertextview= view.findViewById(R.id.leveldividertextview)
        }
    }
    class TechpowerHolder(view: View) : ViewHolder(view){
        val techpowername: TextView
        val techpowerdetails: TextView
        val castingtime: TextView
        val constlout: ConstraintLayout
        val imbutton: ImageButton

        init {

            imbutton = view.findViewById(R.id.table_techpower_fav)
            constlout = view.findViewById(R.id.table_techpower_constlout)
            techpowername= view.findViewById(R.id.table_techpower_name)
            castingtime= view.findViewById(R.id.table_techpower_casting_time)
            techpowerdetails= view.findViewById(R.id.table_techpower_details)
        }

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when(viewType){
            0->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.techcasting_button, viewGroup, false) ; TechpowerHolder(view) }
            1->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.techcasting_button_big, viewGroup, false) ; TechpowerHolder(view) }
            2->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.techcasting_nosuchtechpower_button, viewGroup, false) ; NoTechpowerHolder(view) }
            3->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.techcasting_leveldivider, viewGroup, false) ; LeveledDividerHolder(view)
            }
            else->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.universal_empty_button50sp, viewGroup, false) ; NoTechpowerHolder(view)
            }
        }



    }

    override fun getItemViewType(position: Int): Int {
         with(dataset[position]){
             return when{
                    this.isBig -> 1
                    this.techpowername=="NoSuchTechpower" -> 2
                    this.techpowername=="Level"->3
                    else ->{0}
                }
            }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when(viewHolder.itemViewType){
            2->{}
            3->{setlevel(viewHolder as LeveledDividerHolder,dataset[position].level)}
            else->{techpower(viewHolder as TechpowerHolder,dataset[position])}
        }


    }
    fun setTechpowerList(updatedtechpowerlist: MutableList<Techpower>){
        val diffResult = DiffUtil.calculateDiff(TechpowerDiffUtilCallback(dataset,updatedtechpowerlist))
        dataset.clear()
        dataset.addAll(updatedtechpowerlist)
        diffResult.dispatchUpdatesTo(this)

    }
    override fun getItemCount() = dataset.size
    private fun updatefav(techpower: Techpower, techpowerbutton: View){
        if(techpower.techpowername !in favlist){
            favlist.add(techpower.techpowername)
            techpowerbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            favlist.remove(techpower.techpowername)
            techpowerbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }
        with(myContext.getSharedPreferences("favlist",Context.MODE_PRIVATE).edit()){
            putStringSet("favlist",favlist.toMutableSet())
            apply()
        }
    }

    private fun techpower(view: TechpowerHolder, techpower: Techpower){
        view.techpowername.text=techpower.printedname
        if (techpower.techpowername=="superior_translation_program") view.techpowername.textSize=22F
        else view.techpowername.textSize=24F
        view.techpowerdetails.text= buildSpannedString{
            append(when(techpower.level){0->"At-will";1->"1st-level";2->"2nd-level";3->"3rd-level";else->"${techpower.level}th-level"})
        }
        val temptext = techpower.castingtime + if (techpower.concentration) " C" else ""
        view.castingtime.text= temptext
        view.constlout.setOnClickListener{
            myContext.startActivity(Intent(myContext, TechcastingDetailsActivity::class.java).putExtra("Techpower",techpower))
        }
        view.imbutton.setOnClickListener {
            updatefav(techpower,view.imbutton)
        }
        if(techpower.techpowername in favlist){
            view.imbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            view.imbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }

    }
    private fun setlevel(view: LeveledDividerHolder, lvl: Int){
        when(lvl){
            0->{view.lvldividertextview.text=myContext.getText(R.string.at_will)}
            1->{view.lvldividertextview.text=myContext.getText(R.string.first_level)}
            2->{view.lvldividertextview.text=myContext.getText(R.string.second_level)}
            3->{view.lvldividertextview.text=myContext.getText(R.string.third_level)}
            4->{view.lvldividertextview.text=myContext.getText(R.string.fourth_level)}
            5->{view.lvldividertextview.text=myContext.getText(R.string.fifth_level)}
            6->{view.lvldividertextview.text=myContext.getText(R.string.sixth_level)}
            7->{view.lvldividertextview.text=myContext.getText(R.string.seventh_level)}
            8->{view.lvldividertextview.text=myContext.getText(R.string.eighth_level)}
            9->{view.lvldividertextview.text=myContext.getText(R.string.nineth_level)}
            else->{

            }
        }
    }
}

