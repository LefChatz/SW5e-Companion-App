package com.amachewrs.sw5ecompanionapp.species.speciesadapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.species.SpeciesDetailsActivity
import com.amachewrs.sw5ecompanionapp.spells.adapterstuff.SpeciesDiffUtilCallback

class SpeciesAdapter(private val myContext: Context, dataset: MutableList<Specie>, private val favSpecieList: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    private val currentList = dataset.toMutableList()

    class EmptySpecieHolder(view: View) : ViewHolder(view)

    class NoSpecieHolder(view: View) : ViewHolder(view)

    class SpecieHolder(view: View) : ViewHolder(view){
        val buttonName: TextView = view.findViewById(R.id.table_specietext)
        val buttonExpansion: TextView = view.findViewById(R.id.table_specieExpansion)
        val reLayout: RelativeLayout = view.findViewById(R.id.relayout)
        val favButton: ImageButton = view.findViewById(R.id.specie_fav)
        val buttonImage: ImageView = view.findViewById(R.id.table_specieimage)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            0->{SpecieHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.specie_button, viewGroup, false))}
            1->{NoSpecieHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.specie_no_button, viewGroup, false))}
            else->{EmptySpecieHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.universal_empty_button50sp, viewGroup, false)) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when{
            currentList[position].name=="noSpecie"->1
            else ->0
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (viewHolder.itemViewType == 0) setSpecie(viewHolder as SpecieHolder,currentList[position])
    }

    fun setSpeciesList(updatedSpecieList: List<Specie>){
        val diffResult = DiffUtil.calculateDiff(SpeciesDiffUtilCallback(currentList,updatedSpecieList))
        currentList.clear()
        currentList.addAll(updatedSpecieList)
        diffResult.dispatchUpdatesTo(this)

    }

    override fun getItemCount() = currentList.size

    private fun updateFav(name: String, specieButton: View){
        if(name !in favSpecieList){
            favSpecieList.add(name)
            specieButton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            favSpecieList.remove(name)
            specieButton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }
    }

    private fun setSpecie(view: SpecieHolder, specie: Specie){
        view.buttonImage.setImageResource(specie.getButtonImageID(myContext.resources,myContext.packageName))
        view.buttonName.text = specie.printName
        view.buttonExpansion.text = specie.expansion
        view.reLayout.setOnClickListener{
            myContext.startActivity(Intent(myContext, SpeciesDetailsActivity::class.java).putExtra("Specie",specie))
        }
        view.favButton.setOnClickListener {
            updateFav(specie.name,view.favButton)
        }
        view.favButton.foreground=AppCompatResources.getDrawable(myContext,if (specie.name in favSpecieList)R.drawable.favouritegoldtrue else R.drawable.favouritegold)
    }
}