package com.amachewrs.sw5ecompanionapp.species
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Specie(
    var specieName: String="Empty_Name",
    var specieInfoType: String="normal",
    var infoText: CharSequence="Placeholder for the Specie's info",
    var traitsText: CharSequence="Placeholder for the Specie's traits",
    var image: Int=0,
    var isBig: Boolean = false): Parcelable

fun Specie?.toSpecie(): Specie {
    return this ?: Specie("Unknown Specie")
}

fun Specie.equalsByName(specie: Specie): Boolean{
    return this.specieName == specie.specieName
}
fun MutableList<Specie>.sortSpecieByNameDescending(): MutableList<Specie>{
    return this.sortedByDescending { it.specieName }.toMutableList()
}
fun MutableList<Specie>.sortSpecieByName(): MutableList<Specie>{
    return this.sortedBy{ it.specieName }.toMutableList()
}
fun MutableList<Specie>.indexOfSpecieByName(name: String): Int {
    return this.indexOf(find{ it.specieName == name })
}
fun MutableList<Specie>.getSpecieByName(name: String): Specie?{
    return find {it.specieName==name}
}
fun MutableList<Specie>.getSpecieByNameOrDefault(name: String): Specie {
    return if(find {it.specieName==name}!=null){find{it.specieName==name}!!}else{
        Specie("Error specie not found")
    }
}
fun MutableList<Specie>.getSpecieByNameOrPut(name: String, newSpecie: Specie): Specie {
    return if(find {it.specieName==name}!=null){find{it.specieName==name}!!}else{this.add(newSpecie);newSpecie}
}
@JvmName("MutableListSpecieNameList")
fun MutableList<Specie>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.specieName)
    }
    return templist.toList()
}
@JvmName("ListSpecieNameList")
fun List<Specie>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    forEach{
        templist.add(it.specieName)
    }
    return templist.toList()
}

fun MutableList<Specie>.getNameMutableList(): MutableList<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.specieName)
    }
    return templist
}
