package com.amachewrs.sw5ecompanionapp.spells.adapterstuff

import androidx.recyclerview.widget.DiffUtil
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.Specie

class SpeciesDiffUtilCallback(private val oldList: List<Specie>, private val newList: List<Specie>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]==newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

}