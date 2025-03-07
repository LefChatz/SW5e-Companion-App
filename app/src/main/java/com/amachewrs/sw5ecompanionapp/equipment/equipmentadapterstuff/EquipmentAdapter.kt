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
class EquipmentAdapter(private val mycontext: Context, private val dataset: MutableList<Equipment> ,private val favequipmentlist: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    private val currentList = dataset.toMutableList()

    class EmptyEquipmentHolder(view: View) : ViewHolder(view){
        private val emptyrelout: RelativeLayout
        init{
            emptyrelout= view.findViewById(R.id.emptyrelout)
        }
    }
    class EquipmentHolder(view: View) : ViewHolder(view){
        val equipmenttext: TextView
        val details: TextView
        val relout: RelativeLayout
        val imbutton: ImageButton

        init {

            imbutton = view.findViewById(R.id.equipment_fav)
            relout = view.findViewById(R.id.relayout)
            equipmenttext= view.findViewById(R.id.table_equipmenttext)
            details= view.findViewById(R.id.table_equipmenttext2)
        }

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when(viewType){
            0->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.equipment_button, viewGroup, false) ; EquipmentHolder(view) }
            else->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.universal_empty_button50sp, viewGroup, false) ; EmptyEquipmentHolder(view) }
        }



    }

    override fun getItemViewType(position: Int): Int {
        return when{
            currentList[position].isEmpty()->{1}
            else ->{0}
        }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (viewHolder.itemViewType!=1) setEquipment(viewHolder as EquipmentHolder,currentList[position])
    }
    fun setEquipmentList(updatedequipmentlist: List<Equipment>){
        val diffResult = DiffUtil.calculateDiff(EquipmentDiffUtilCallback(currentList,updatedequipmentlist))
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
        with(mycontext.getSharedPreferences("favequipmentlist",Context.MODE_PRIVATE).edit()){
            putStringSet("favequipmentlist",favequipmentlist.toMutableSet())
            apply()
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