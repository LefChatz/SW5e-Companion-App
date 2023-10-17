package com.example.swtrial2.spells.adapterstuff

import androidx.recyclerview.widget.DiffUtil

class SpellDiffUtilCallback(private val oldlist: MutableList<Spell>,private val newlist: MutableList<Spell>): DiffUtil.Callback() {
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