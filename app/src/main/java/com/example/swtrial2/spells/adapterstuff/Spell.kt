package com.example.swtrial2.spells.adapterstuff

data class Spell(
    var spellname: String="Empty_Name",
    var printedname: String="Default Name",
    var castingtime: String="",
    var side: String="",
    var level: Int=10,
    var concentration: Boolean = false,
    var prerequisite: Boolean = false){



    /*fun setName(tspellname: String){
        spellname = tspellname
    }
    fun setCastingTime(tcastingtime: String){
        castingtime=tcastingtime
    }
    fun setSide(tside: String){
        side=tside
    }
    fun setLeveL(tLevel: Int){
        level=tLevel
    }
    fun setConcentration(tConcentration: Boolean){
        concentration=tConcentration
    }
    fun setPrerequisite(tPrereq: Boolean){
        prerequisite=tPrereq
    }*/

}
fun MutableList<Spell>.sortSpellByLevel(): MutableList<Spell> {
    return this.sortedWith(compareBy { it.level }).toMutableList()
}
fun MutableList<Spell>.sortSpellByLevelDescending(): MutableList<Spell> {
    return this.sortedWith(compareByDescending { it.level }).toMutableList()
}
fun MutableList<Spell>.sortSpellByNameDescending(): MutableList<Spell>{
    return this.sortedByDescending { it.spellname }.toMutableList()
}
fun MutableList<Spell>.sortSpellByName(): MutableList<Spell>{
    return this.sortedBy{ it.spellname }.toMutableList()
}
fun MutableList<Spell>.indexOfSpellByName(name: String): Int {
    return this.indexOf(find{ it.spellname == name })
}
fun MutableList<Spell>.getSpellByName(name: String): Spell?{
    return find {it.spellname==name}
}
fun MutableList<Spell>.getSpellByNameOrDefault(name: String): Spell{
    return if(find {it.spellname==name}!=null){find{it.spellname==name}!!}else{Spell("Error Spell not found")}
}
fun MutableList<Spell>.getSpellByNameOrPut(name: String,newSpell: Spell): Spell{
    return if(find {it.spellname==name}!=null){find{it.spellname==name}!!}else{this.add(newSpell);Spell("Error Spell not found")}
}
fun MutableList<Spell>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.spellname)
    }
    return templist.toList()
}
@JvmName("ListSpellNameList")
fun List<Spell>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    forEach{
        templist.add(it.spellname)
    }
    return templist.toList()
}
@JvmName("MutableListSpellNameList")
fun MutableList<Spell>.getNameMutableList(): MutableList<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.spellname)
    }
    return templist
}