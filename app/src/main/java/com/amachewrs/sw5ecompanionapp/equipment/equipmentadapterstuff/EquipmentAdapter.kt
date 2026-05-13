package com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.spells.adapterstuff.SpeciesDiffUtilCallback
import androidx.core.content.edit

class EquipmentAdapter(private val mycontext: Context, private val dataset: MutableList<Equipment> ,private val favequipmentlist: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    private val currentList = dataset.toMutableList()

    class EmptyEquipmentHolder(view: View) : ViewHolder(view)

    class NoEquipmentHolder(view: View) : ViewHolder(view)

    class EquipmentHolder(view: View) : ViewHolder(view){
        val equipmenttext: TextView = view.findViewById(R.id.table_equipmenttext)
        val details: TextView = view.findViewById(R.id.table_equipmenttext2)
        val relout: RelativeLayout = view.findViewById(R.id.relayout)
        val imbutton: ImageButton = view.findViewById(R.id.equipment_fav)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when(viewType){
            0->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.equipment_button, viewGroup, false) ; EquipmentHolder(view) }
            2->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.equipment_no_button, viewGroup, false) ; NoEquipmentHolder(view) }
            else->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.universal_empty_button50sp, viewGroup, false) ; EmptyEquipmentHolder(view) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when{
            currentList[position].equipmentname=="noEquipment"->2
            currentList[position].isEmpty()->1
            else ->0
        }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (viewHolder.itemViewType){
            1->{}
            2->{}
            else->{setEquipment(viewHolder as EquipmentHolder,currentList[position])}
        }
    }
    fun setEquipmentList(updatedequipmentlist: List<Equipment>){
        val diffResult = DiffUtil.calculateDiff(SpeciesDiffUtilCallback(currentList,updatedequipmentlist))
        currentList.clear()
        currentList.addAll(updatedequipmentlist)
        diffResult.dispatchUpdatesTo(this)

    }

    override fun getItemCount() = currentList.size

    private fun updatefav(name: String, equipmentbutton: View){
        if(name !in favequipmentlist){
            favequipmentlist.add(name)
            equipmentbutton.foreground=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegoldtrue)
        }
        else{
            favequipmentlist.remove(name)
            equipmentbutton.foreground=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegold)
        }
        mycontext.getSharedPreferences("favequipmentlist", Context.MODE_PRIVATE).edit {
            putStringSet("favequipmentlist", favequipmentlist.toMutableSet())
        }
    }

    private fun setEquipment(view: EquipmentHolder, equipment: Equipment){
        view.equipmenttext.text = equipment.printedname
        view.equipmenttext.typeface = mycontext.resources.getFont(R.font.starjedi)
        view.details.text = equipment.attributes
        view.relout.setOnClickListener{
            mycontext.startActivity(Intent(mycontext, EquipmentDetailsActivity::class.java).putExtra("Equipment",equipment))
        }
        view.imbutton.setOnClickListener {
            updatefav(equipment.equipmentname,view.imbutton)
        }
        if(equipment.equipmentname in favequipmentlist){
            view.imbutton.foreground=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegoldtrue)
        }
        else{
            view.imbutton.foreground=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegold)
        }

    }
}