package com.example.swtrial2.currentlyunused

data class Spell(
    var spellname: String="Empty",
    var castingtime: String="",
    var side: String="",
    var activityname: String="",
    var level: Int=0,
    var concentration: Boolean = false,
    var prerequisite: Boolean = false){

    fun ArrangeByLevelDown(spelllist: List<Spell>): List<Spell> {
        return spelllist.sortedWith(compareBy { it.level })
    }
    fun ArrangeByLevelUp(spelllist: List<Spell>): List<Spell> {
        return spelllist.sortedWith(compareByDescending { it.level })
    }
    fun ArrangeByABCUp(spelllist: List<Spell>): List<Spell>{
        return spelllist.sortedByDescending { it.spellname }
    }
    fun SetName(tspellname: String){
        spellname = tspellname
    }
    fun SetCastingTime(tcastingtime: String){
        castingtime=tcastingtime
    }

}