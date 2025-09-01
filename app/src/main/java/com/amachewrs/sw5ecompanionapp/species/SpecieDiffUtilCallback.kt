package com.amachewrs.sw5ecompanionapp.species

import androidx.recyclerview.widget.DiffUtil

class SpecieDiffUtilCallback(private val oldlist: MutableList<List<Specie>>, private val newlist: List<List<Specie>>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlist.size
    }

    override fun getNewListSize(): Int {
        return newlist.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition][0].equalsByName(newlist[newItemPosition][0])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

}