package com.example.sw5ecompanionapp.feats

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sw5ecompanionapp.R


class FeatsAdapter(private val myContext: Context, private val dataset: MutableList<Feat>, private val favlist: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    val levels: List<Int> = listOf(0,1,2,3,4,5,6,7,8,9)
    class NoFeatHolder(view: View) : ViewHolder(view){
        private val noFeatText: TextView
        init {
            noFeatText= view.findViewById(R.id.feats_nosuchfeattext)
        }
    }
    class LeveledDividerHolder(view: View) : ViewHolder(view){
        val lvldividertextview: TextView
        init {
            lvldividertextview= view.findViewById(R.id.leveldividertextview)
        }
    }
    class FeatHolder(view: View) : ViewHolder(view){
        val featname: TextView
        val featdetails: TextView
        val sourcebook: TextView
        val constlout: ConstraintLayout
        val imbutton: ImageButton

        init {

            imbutton = view.findViewById(R.id.table_feat_fav)
            constlout = view.findViewById(R.id.table_feat_constlout)
            featname= view.findViewById(R.id.table_feat_name)
            sourcebook= view.findViewById(R.id.table_feat_casting_time)
            featdetails= view.findViewById(R.id.table_feat_details)
        }

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when(viewType){
            0->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.feats_button, viewGroup, false) ; FeatHolder(view) }
            1->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.feats_button_big, viewGroup, false) ; FeatHolder(view) }
            2->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.feats_nosuchfeat_button, viewGroup, false) ; NoFeatHolder(view) }
            else->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.universal_empty_button50sp, viewGroup, false) ; NoFeatHolder(view)
            }
        }



    }
    override fun getItemViewType(position: Int): Int {
         with(dataset[position]){
             return when{
                    this.isBig -> 1
                    this.featname=="NoSuchFeat" -> 2
                    this.featname=="Level"->3
                    else ->{0}
                }
            }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (viewHolder.itemViewType != 2) feat(viewHolder as FeatHolder,dataset[position])
    }
    fun setFeatList(updatedfeatlist: MutableList<Feat>){
        val diffResult = DiffUtil.calculateDiff(FeatDiffUtilCallback(dataset,updatedfeatlist))
        dataset.clear()
        dataset.addAll(updatedfeatlist)
        diffResult.dispatchUpdatesTo(this)

    }
    override fun getItemCount() = dataset.size
    private fun updatefav(feat: Feat, featbutton: View){
        if(feat.featname !in favlist){
            favlist.add(feat.featname)
            featbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            favlist.remove(feat.featname)
            featbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }
        with(myContext.getSharedPreferences("favlist",Context.MODE_PRIVATE).edit()){
            putStringSet("favlist",favlist.toMutableSet())
            apply()
        }
    }

    private fun feat(view: FeatHolder, feat: Feat){
        view.featname.text=feat.featname.replace("_"," ").replace(".","-").replace("..","'")

        val txt = "ASI: "+feat.asi + if (feat.prerequisite.isNotEmpty()) ", prereq:"+feat.prerequisite else ""
        view.featdetails.text = txt

        view.sourcebook.text = feat.source

        view.constlout.setOnClickListener{
            myContext.startActivity(Intent(myContext, FeatsDetailsActivity::class.java).putExtra("Feat",feat))
        }
        view.imbutton.setOnClickListener {
            updatefav(feat,view.imbutton)
        }
        if(feat.featname in favlist){
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

