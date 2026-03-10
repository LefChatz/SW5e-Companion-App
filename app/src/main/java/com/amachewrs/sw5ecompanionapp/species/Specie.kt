package com.amachewrs.sw5ecompanionapp.species
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Specie(
    var name: String="Empty_Name",
    var printName: CharSequence="Empty Name",
    var specieInfoType: String="normal",
    var infoText: CharSequence="Placeholder for the Specie's info",
    var traitsText: CharSequence="Placeholder for the Specie's traits",
    var imageID: Int=0,
    var buttonImageID: Int=0,
    var isBig: Boolean = false): Parcelable

@SuppressLint("DiscouragedApi")
fun Specie.getLayoutID(resources: Resources, packageName: String): Int{
    return resources.getIdentifier(this.name,"layout",packageName)
}
fun Specie?.toSpecie(): Specie {
    return this ?: Specie("Unknown Specie")
}

fun Specie.equalsByName(specie: Specie): Boolean{
    return this.name == specie.name
}
fun MutableList<Specie>.sortSpecieByNameDescending(): MutableList<Specie>{
    return this.sortedByDescending { it.name }.toMutableList()
}
fun MutableList<Specie>.sortSpecieByName(): MutableList<Specie>{
    return this.sortedBy{ it.name }.toMutableList()
}
fun MutableList<Specie>.indexOfSpecieByName(name: String): Int {
    return this.indexOf(find{ it.name == name })
}
fun MutableList<Specie>.getSpecieByName(name: String): Specie?{
    return find {it.name==name}
}
fun MutableList<Specie>.getSpecieByNameOrDefault(name: String): Specie {
    return if(find {it.name==name}!=null){find{it.name==name}!!}else{
        Specie("Error specie not found")
    }
}
fun MutableList<Specie>.getSpecieByNameOrPut(name: String, newSpecie: Specie): Specie {
    return if(find {it.name==name}!=null){find{it.name==name}!!}else{this.add(newSpecie);newSpecie}
}
@JvmName("MutableListSpecieNameList")
fun MutableList<Specie>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.name)
    }
    return templist.toList()
}
@JvmName("ListSpecieNameList")
fun List<Specie>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    forEach{
        templist.add(it.name)
    }
    return templist.toList()
}

fun MutableList<Specie>.getNameMutableList(): MutableList<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.name)
    }
    return templist
}
