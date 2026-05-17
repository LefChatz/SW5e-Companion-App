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
import com.amachewrs.sw5ecompanionapp.spells.adapterstuff.EquipmentDiffUtilCallback

class EquipmentAdapter(private val myContext: Context, dataset: MutableList<Equipment>, private val favEquipmentList: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    private val currentList = dataset.toMutableList()

    class EmptyEquipmentHolder(view: View) : ViewHolder(view)

    class NoEquipmentHolder(view: View) : ViewHolder(view)

    class EquipmentHolder(view: View) : ViewHolder(view){
        val equipmentText: TextView = view.findViewById(R.id.table_equipmenttext)
        val details: TextView = view.findViewById(R.id.table_equipmenttext2)
        val reLayout: RelativeLayout = view.findViewById(R.id.relayout)
        val favButton: ImageButton = view.findViewById(R.id.equipment_fav)

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
            currentList[position].equipmentName=="noEquipment"->2
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
    fun setEquipmentList(updatedEquipmentList: List<Equipment>){
        val diffResult = DiffUtil.calculateDiff(EquipmentDiffUtilCallback(currentList,updatedEquipmentList))
        currentList.clear()
        currentList.addAll(updatedEquipmentList)
        diffResult.dispatchUpdatesTo(this)

    }

    override fun getItemCount() = currentList.size

    private fun updateFav(name: String, equipmentButton: View){
        if(name !in favEquipmentList){
            favEquipmentList.add(name)
            equipmentButton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            favEquipmentList.remove(name)
            equipmentButton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }
    }

    private fun setEquipment(view: EquipmentHolder, equipment: Equipment){
        view.equipmentText.text = equipment.printedName
        view.equipmentText.typeface = myContext.resources.getFont(R.font.starjedi)
        view.details.text = equipment.attributes
        view.reLayout.setOnClickListener{
            myContext.startActivity(Intent(myContext, EquipmentDetailsActivity::class.java).putExtra("Equipment",equipment))
        }
        view.favButton.setOnClickListener {
            updateFav(equipment.equipmentName,view.favButton)
        }
        if(equipment.equipmentName in favEquipmentList){
            view.favButton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegoldtrue)
        }
        else{
            view.favButton.foreground=AppCompatResources.getDrawable(myContext,R.drawable.favouritegold)
        }

    }
}