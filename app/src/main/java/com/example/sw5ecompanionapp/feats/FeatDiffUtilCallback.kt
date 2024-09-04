package com.example.sw5ecompanionapp.feats

import androidx.recyclerview.widget.DiffUtil

class FeatDiffUtilCallback(private val oldlist: MutableList<Feat>, private val newlist: MutableList<Feat>): DiffUtil.Callback() {
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