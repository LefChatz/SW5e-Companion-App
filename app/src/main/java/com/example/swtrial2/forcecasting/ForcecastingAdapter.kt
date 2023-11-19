package com.example.swtrial2.forcecasting

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
import com.example.swtrial2.R


class ForcecastingAdapter(private val myContext: Context, private val dataset: MutableList<Forcepower>, private val favlist: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    val levels: List<Int> = listOf(0,1,2,3,4,5,6,7,8,9)
    class NoForcepowerHolder(view: View) : ViewHolder(view){
        private val noForcepowerText: TextView
        init {
            noForcepowerText= view.findViewById(R.id.forcecasting_nosuchforcepowertext)
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
    class ForcepowerHolder(view: View) : ViewHolder(view){
        val forcepowername: TextView
        val forcepowerdetails: TextView
        val castingtime: TextView
        val constlout: ConstraintLayout
        val imbutton: ImageButton

        init {

            imbutton = view.findViewById(R.id.table_forcepower_fav)
            constlout = view.findViewById(R.id.table_forcepower_constlout)
            forcepowername= view.findViewById(R.id.table_forcepower_name)
            castingtime= view.findViewById(R.id.table_forcepower_casting_time)
            forcepowerdetails= view.findViewById(R.id.table_forcepower_details)
        }

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when(viewType){
            0->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.forcecasting_button, viewGroup, false) ; ForcepowerHolder(view) }
            1->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.forcecasting_button_big, viewGroup, false) ; ForcepowerHolder(view) }
            2->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.forcecasting_nosuchforcepower_button, viewGroup, false) ; NoForcepowerHolder(view) }
            3->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.forcecasting_leveldivider, viewGroup, false) ; LeveledDividerHolder(view)
            }
            else->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.universal_empty_button50sp, viewGroup, false) ; NoForcepowerHolder(view)
            }
        }



    }
    override fun getItemViewType(position: Int): Int {
         with(dataset[position]){
             return when{
                    this.isBig -> 1
                    this.forcepowername=="NoSuchForcepower" -> 2
                    this.forcepowername=="Level"->3
                    else ->{0}
                }
            }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when(viewHolder.itemViewType){
            2->{}
            3->{setlevel(viewHolder as LeveledDividerHolder,dataset[position].level)}
            else->{forcepower(viewHolder as ForcepowerHolder,dataset[position])}
        }


    }
    fun setForcepowerList(updatedforcepowerlist: MutableList<Forcepower>){
        val diffResult = DiffUtil.calculateDiff(ForcepowerDiffUtilCallback(dataset,updatedforcepowerlist))
        dataset.clear()
        dataset.addAll(updatedforcepowerlist)
        diffResult.dispatchUpdatesTo(this)

    }
    override fun getItemCount() = dataset.size
    private fun updatefav(forcepower: Forcepower, forcepowerbutton: View){
        if(forcepower.forcepowername !in favlist){
            favlist.add(forcepower.forcepowername)
            forcepowerbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            favlist.remove(forcepower.forcepowername)
            forcepowerbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }
        with(myContext.getSharedPreferences("favlist",Context.MODE_PRIVATE).edit()){
            putStringSet("favlist",favlist.toMutableSet())
            apply()
        }
    }

    private fun forcepower(view: ForcepowerHolder, forcepower: Forcepower){
        view.forcepowername.text=forcepower.printedname
        view.forcepowerdetails.text= buildSpannedString{
            append(when(forcepower.level){0->"At-will";1->"1st-level";2->"2nd-level";3->"3rd-level";else->"${forcepower.level}th-level"})
            append(" ");append(forcepower.side)}
        view.castingtime.text= forcepower.castingtime
        view.constlout.setOnClickListener{
            myContext.startActivity(Intent(myContext, ForcecastingDetailsActivity::class.java).putExtra("Forcepower",forcepower))
        }
        view.imbutton.setOnClickListener {
            updatefav(forcepower,view.imbutton)
        }
        if(forcepower.forcepowername in favlist){
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

