package com.example.sw5ecompanionapp.maneuvers

import androidx.recyclerview.widget.DiffUtil

class ManeuverDiffUtilCallback(private val oldlist: MutableList<Maneuver>, private val newlist: List<Maneuver>): DiffUtil.Callback() {
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