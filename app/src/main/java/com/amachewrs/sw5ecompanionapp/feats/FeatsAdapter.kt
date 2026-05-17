package com.amachewrs.sw5ecompanionapp.feats

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
import com.amachewrs.sw5ecompanionapp.R


class FeatsAdapter(private val myContext: Context, private val dataset: MutableList<Feat>, private val favFeatList: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    val levels: List<Int> = listOf(0,1,2,3,4,5,6,7,8,9)

    class NoFeatHolder(view: View) : ViewHolder(view)

    class FeatHolder(view: View) : ViewHolder(view){
        val featName: TextView = view.findViewById(R.id.table_feat_fav)
        val featDetails: TextView = view.findViewById(R.id.table_feat_constlout)
        val sourcebook: TextView = view.findViewById(R.id.table_feat_name)
        val conLayout: ConstraintLayout = view.findViewById(R.id.feats_button_sourcebook)
        val favButton: ImageButton = view.findViewById(R.id.table_feat_details)
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
                    else ->{0}
                }
            }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (viewHolder.itemViewType != 2) feat(viewHolder as FeatHolder,dataset[position])
    }
    fun setFeatList(updatedFeatList: List<Feat>){
        val diffResult = DiffUtil.calculateDiff(FeatDiffUtilCallback(dataset,updatedFeatList))
        dataset.clear()
        dataset.addAll(updatedFeatList)
        diffResult.dispatchUpdatesTo(this)

    }
    override fun getItemCount() = dataset.size
    private fun updateFav(feat: Feat, featButton: View){
        if(feat.featname !in favFeatList){
            favFeatList.add(feat.featname)
            featButton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            favFeatList.remove(feat.featname)
            featButton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }
    }

    private fun feat(view: FeatHolder, feat: Feat){
        view.featName.text=feat.featname.replace("_"," ").replace("..","'").replace(".","-")

        val txt = "ASI: " + feat.asi.replace(" or ","/") + if (feat.prerequisite.isNotEmpty()) " | pre:"+feat.prerequisite.replace("th level"," lvl").replace(" or ","/") else ""
        view.featDetails.text = txt

        view.sourcebook.text = feat.source

        view.conLayout.setOnClickListener{
            myContext.startActivity(Intent(myContext, FeatsDetailsActivity::class.java).putExtra("Feat",feat))
        }
        view.favButton.setOnClickListener {
            updateFav(feat,view.favButton)
        }
        if(feat.featname in favFeatList){
            view.favButton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            view.favButton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }

    }
}

