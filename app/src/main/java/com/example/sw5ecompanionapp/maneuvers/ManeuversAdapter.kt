package com.example.sw5ecompanionapp.maneuvers

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


class ManeuversAdapter(private val myContext: Context, private val dataset: MutableList<Maneuver>, private val favlist: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    val levels: List<Int> = listOf(0,1,2,3,4,5,6,7,8,9)
    class NoManeuverHolder(view: View) : ViewHolder(view){
        private val noManeuverText: TextView
        init {
            noManeuverText= view.findViewById(R.id.maneuvers_nosuchmaneuvertext)
        }
    }

    class ManeuverHolder(view: View) : ViewHolder(view){
        val maneuvername: TextView
        val maneuverdetails: TextView
        val sourcebook: TextView
        val constlout: ConstraintLayout
        val imbutton: ImageButton

        init {

            imbutton = view.findViewById(R.id.table_maneuver_fav)
            constlout = view.findViewById(R.id.table_maneuver_constlout)
            maneuvername= view.findViewById(R.id.table_maneuver_name)
            sourcebook= view.findViewById(R.id.maneuvers_button_sourcebook)
            maneuverdetails= view.findViewById(R.id.table_maneuver_details)
        }

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when(viewType){
            0->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.maneuvers_button, viewGroup, false) ; ManeuverHolder(view) }
            1->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.maneuvers_button_big, viewGroup, false) ; ManeuverHolder(view) }
            2->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.maneuvers_nosuchmaneuver_button, viewGroup, false) ; NoManeuverHolder(view) }
            else->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.universal_empty_button50sp, viewGroup, false) ; NoManeuverHolder(view)
            }
        }



    }
    override fun getItemViewType(position: Int): Int {
         with(dataset[position]){
             return when{
                    this.isBig -> 1
                    this.maneuvername=="NoSuchManeuver" -> 2
                    else ->{0}
                }
            }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (viewHolder.itemViewType != 2) maneuver(viewHolder as ManeuverHolder,dataset[position])
    }
    fun setManeuverList(updatedmaneuverlist: List<Maneuver>){
        val diffResult = DiffUtil.calculateDiff(ManeuverDiffUtilCallback(dataset,updatedmaneuverlist))
        dataset.clear()
        dataset.addAll(updatedmaneuverlist)
        diffResult.dispatchUpdatesTo(this)

    }
    override fun getItemCount() = dataset.size
    private fun updatefav(maneuver: Maneuver, maneuverbutton: View){
        if(maneuver.maneuvername !in favlist){
            favlist.add(maneuver.maneuvername)
            maneuverbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            favlist.remove(maneuver.maneuvername)
            maneuverbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }
        with(myContext.getSharedPreferences("favlist",Context.MODE_PRIVATE).edit()){
            putStringSet("favlist",favlist.toMutableSet())
            apply()
        }
    }

    private fun maneuver(view: ManeuverHolder, maneuver: Maneuver){
        view.maneuvername.text=maneuver.maneuvername

        val txt = "Type: " + maneuver.type.replace(" or ","/") + if (maneuver.prerequisite.isNotEmpty()) " | pre: "+maneuver.prerequisite.replace(" maneuver","") else ""
        view.maneuverdetails.text = txt

        view.sourcebook.text = maneuver.source

        view.constlout.setOnClickListener{
            myContext.startActivity(Intent(myContext, ManeuversDetailsActivity::class.java).putExtra("Maneuver",maneuver))
        }
        view.imbutton.setOnClickListener {
            updatefav(maneuver,view.imbutton)
        }
        if(maneuver.maneuvername in favlist){
            view.imbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            view.imbutton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }

    }
}

