package com.amachewrs.sw5ecompanionapp.species

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
import com.amachewrs.sw5ecompanionapp.widget.ClassButton


class SpeciesAdapter(private val myContext: Context, private val dataset: MutableList<List<Specie>>, private val favlist: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    val levels: List<Int> = listOf(0,1,2,3,4,5,6,7,8,9)
    class NoSpecieHolder(view: View) : ViewHolder(view)

    class SpecieHolder(view: View) : ViewHolder(view){
        val button1: ClassButton
        val button2: ClassButton

        init {
            button1 = view.findViewById(R.id.species_button1)
            button2 = view.findViewById(R.id.species_button2)
        }

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when(viewType){
            0->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.species_row, viewGroup, false) ; SpecieHolder(view) }
            1->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.species_row_big, viewGroup, false) ; SpecieHolder(view) }
            else->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.universal_empty_button50sp, viewGroup, false) ; NoSpecieHolder(view)
            }
        }



    }
    override fun getItemViewType(position: Int): Int {
         with(dataset[position][0]){
             return when{
                    this.isBig -> 1
                    else ->{if (dataset[position][1].isBig) 1 else 0}
                }
            }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (viewHolder.itemViewType != 2) specie(viewHolder as SpecieHolder,dataset[position][0],dataset[position][1])
    }
    fun setSpecieList(updatedspecielist: List<List<Specie>>){
        val diffResult = DiffUtil.calculateDiff(SpecieDiffUtilCallback(dataset,updatedspecielist))
        dataset.clear()
        dataset.addAll(updatedspecielist)
        diffResult.dispatchUpdatesTo(this)

    }
    override fun getItemCount() = dataset.size
    private fun updatefav(specie: Specie, speciebutton: View){
        if(specie.name !in favlist){
            favlist.add(specie.name)
            speciebutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            favlist.remove(specie.name)
            speciebutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }
        with(myContext.getSharedPreferences("favlist",Context.MODE_PRIVATE).edit()){
            putStringSet("favlist",favlist.toMutableSet())
            apply()
        }
    }

    private fun specie(view: SpecieHolder, specie1: Specie, specie2: Specie){
        view.button1.setText(specie1.printname)
        view.button1.setBackgroundImage(AppCompatResources.getDrawable(myContext,specie1.buttonimageID))
        view.button1.setOnClickListener{ myContext.startActivity(Intent(myContext, SpeciesDetailsActivity::class.java).putExtra("Specie",specie1)) }

        view.button2.setText(specie2.printname)
        view.button2.setBackgroundImage(AppCompatResources.getDrawable(myContext,specie2.buttonimageID))
        view.button2.setOnClickListener{ myContext.startActivity(Intent(myContext, SpeciesDetailsActivity::class.java).putExtra("Specie",specie2)) }
    }
}

