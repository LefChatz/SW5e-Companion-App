package com.amachewrs.sw5ecompanionapp.spells.adapterstuff

import androidx.recyclerview.widget.DiffUtil
import com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff.Equipment

class EquipmentDiffUtilCallback(private val oldlist: List<Equipment>, private val newlist: List<Equipment>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlist.size
    }

    override fun getNewListSize(): Int {
        return newlist.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition]==newlist[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

}