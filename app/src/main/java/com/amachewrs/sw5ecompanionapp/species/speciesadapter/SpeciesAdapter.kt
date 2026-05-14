package com.amachewrs.sw5ecompanionapp.species.speciesadapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.edit
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.species.SpeciesDetailsActivity
import com.amachewrs.sw5ecompanionapp.spells.adapterstuff.SpeciesDiffUtilCallback

class SpeciesAdapter(private val mycontext: Context, private val dataset: MutableList<Specie>, private val favspecielist: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    private val currentList = dataset.toMutableList()

    class EmptySpecieHolder(view: View) : ViewHolder(view)

    class NoSpecieHolder(view: View) : ViewHolder(view)

    class SpecieHolder(view: View) : ViewHolder(view){
        val buttonName: TextView = view.findViewById(R.id.table_specietext)
        val buttonExpansion: TextView = view.findViewById(R.id.table_specieExpansion)
        val relout: RelativeLayout = view.findViewById(R.id.relayout)
        val imbutton: ImageButton = view.findViewById(R.id.specie_fav)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when(viewType){
            0->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.specie_button, viewGroup, false) ; SpecieHolder(view) }
            2->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.specie_no_button, viewGroup, false) ; NoSpecieHolder(view) }
            else->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.universal_empty_button50sp, viewGroup, false) ; EmptySpecieHolder(view) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when{
            currentList[position].name=="noSpecie"->2
            else ->0
        }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (viewHolder.itemViewType){
            1->{}
            2->{}
            else->{setSpecie(viewHolder as SpecieHolder,currentList[position])}
        }
    }
    fun setSpeciesList(updatedSpecieList: List<Specie>){
        val diffResult = DiffUtil.calculateDiff(SpeciesDiffUtilCallback(currentList,updatedSpecieList))
        currentList.clear()
        currentList.addAll(updatedSpecieList)
        diffResult.dispatchUpdatesTo(this)

    }

    override fun getItemCount() = currentList.size

    private fun updateFav(name: String, specieButton: View){
        if(name !in favspecielist){
            favspecielist.add(name)
            specieButton.foreground=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegoldtrue)
        }
        else{
            favspecielist.remove(name)
            specieButton.foreground=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegold)
        }
        mycontext.getSharedPreferences("favspecieslist", Context.MODE_PRIVATE).edit {
            putStringSet("favspecieslist", favspecielist.toMutableSet())
        }
    }

    private fun setSpecie(view: SpecieHolder, specie: Specie){
        view.buttonName.text = specie.printName
        view.buttonExpansion.text = specie.expansion
        view.relout.setOnClickListener{
            mycontext.startActivity(Intent(mycontext, SpeciesDetailsActivity::class.java).putExtra("Specie",specie))
        }
        view.imbutton.setOnClickListener {
            updateFav(specie.name,view.imbutton)
        }
        view.imbutton.foreground=AppCompatResources.getDrawable(mycontext,if (specie.name in favspecielist)R.drawable.favouritegoldtrue else R.drawable.favouritegold)
    }
}