package com.amachewrs.sw5ecompanionapp.techcasting

import androidx.recyclerview.widget.DiffUtil

class TechpowerDiffUtilCallback(private val oldlist: MutableList<Techpower>, private val newlist: MutableList<Techpower>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlist.size
    }

    override fun getNewListSize(): Int {
        return newlist.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition].equalsByName(newlist[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

}