@file:Suppress("unused")

package com.amachewrs.sw5ecompanionapp.species.speciesadapter
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.LinkedList

@Parcelize

data class Specie(
    var name: String="Empty_Name",
    var printName: CharSequence="Empty Name",
    var expansion: String="PHB",
    var specieInfoType: Int=0,
    var infoTextHeap: LinkedList<String> = LinkedList(mutableListOf("Placeholder for the Specie's info")),
    var traitsText: CharSequence="Placeholder for the Specie's traits",
    var imageID: String="error404",
    var buttonImageID: String="error404"): Parcelable

fun Specie.isEmpty(): Boolean{
    return name=="Empty_name"
}
@SuppressLint("DiscouragedApi")
fun Specie.getImageID(resources: Resources, packageName: String): Int{
    return resources.getIdentifier(this.imageID,"drawable",packageName)
}
@SuppressLint("DiscouragedApi")
fun Specie.getButtonImageID(resources: Resources, packageName: String): Int{
    return resources.getIdentifier(this.buttonImageID,"drawable",packageName)
}
fun Specie?.ensureNotEmpty(): Specie {
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
    val tempList = mutableListOf<String>()
    for(i in this){
        tempList.add(i.name)
    }
    return tempList.toList()
}
@JvmName("ListSpecieNameList")
fun List<Specie>.getNameList(): List<String>{
    val tempList = mutableListOf<String>()
    forEach{
        tempList.add(it.name)
    }
    return tempList.toList()
}
fun MutableList<Specie>.getNameMutableList(): MutableList<String>{
    val tempList = mutableListOf<String>()
    for(i in this){
        tempList.add(i.name)
    }
    return tempList
}