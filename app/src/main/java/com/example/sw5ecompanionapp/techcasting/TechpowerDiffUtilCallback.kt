package com.example.sw5ecompanionapp.forcecasting

import androidx.recyclerview.widget.DiffUtil

class ForcepowerDiffUtilCallback(private val oldlist: MutableList<Forcepower>, private val newlist: MutableList<Forcepower>): DiffUtil.Callback() {
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