package com.amachewrs.sw5ecompanionapp.forcecasting
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forcepower(
    var forcepowername: String="Empty_Name",
    var printedname: CharSequence="Default Name",
    var castingtime: String="",
    var side: CharSequence="",
    var level: Int=10,
    var concentration: Boolean = false,
    var prerequisite: Boolean = false,
    var isBig: Boolean = false,
    var detailsText: CharSequence="Placeholder for the Force power's details text"): Parcelable

fun Forcepower?.toForcepower(): Forcepower {
    return this ?: Forcepower("Unknown Force Power")
}

fun Forcepower.equalsByName(forcepower: Forcepower): Boolean{
    return this.forcepowername == forcepower.forcepowername
}
fun Forcepower.equalsStrict(forcepower: Forcepower): Boolean{
    return (this.forcepowername == forcepower.forcepowername && this.printedname==forcepower.printedname && this.castingtime==forcepower.castingtime && this.side==forcepower.side && this.level==forcepower.level && this.concentration==forcepower.concentration && this.prerequisite==forcepower.prerequisite)
}
fun MutableList<Forcepower>.sortForcepowerByLevel(): MutableList<Forcepower> {
    return this.sortedWith(compareBy { it.level }).toMutableList()
}
fun MutableList<Forcepower>.sortForcepowerByLevelDescending(): MutableList<Forcepower> {
    return this.sortedWith(compareByDescending { it.level }).toMutableList()
}
fun MutableList<Forcepower>.sortForcepowerByNameDescending(): MutableList<Forcepower>{
    return this.sortedByDescending { it.forcepowername }.toMutableList()
}
fun MutableList<Forcepower>.sortForcepowerByName(): MutableList<Forcepower>{
    return this.sortedBy{ it.forcepowername }.toMutableList()
}
fun MutableList<Forcepower>.indexOfForcepowerByName(name: String): Int {
    return this.indexOf(find{ it.forcepowername == name })
}
fun MutableList<Forcepower>.getForcepowerByName(name: String): Forcepower?{
    return find {it.forcepowername==name}
}
fun MutableList<Forcepower>.getForcepowerByNameOrDefault(name: String): Forcepower {
    return if(find {it.forcepowername==name}!=null){find{it.forcepowername==name}!!}else{
        Forcepower("Error forcepower not found")
    }
}
fun MutableList<Forcepower>.getForcepowerByNameOrPut(name: String, newforcepower: Forcepower): Forcepower {
    return if(find {it.forcepowername==name}!=null){find{it.forcepowername==name}!!}else{this.add(newforcepower);newforcepower}
}
@JvmName("MutableListforcepowerNameList")
fun MutableList<Forcepower>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.forcepowername)
    }
    return templist.toList()
}
@JvmName("ListforcepowerNameList")
fun List<Forcepower>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    forEach{
        templist.add(it.forcepowername)
    }
    return templist.toList()
}

fun MutableList<Forcepower>.getNameMutableList(): MutableList<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.forcepowername)
    }
    return templist
}
