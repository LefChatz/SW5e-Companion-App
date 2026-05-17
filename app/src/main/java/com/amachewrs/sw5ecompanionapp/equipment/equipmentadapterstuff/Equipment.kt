@file:Suppress("unused")

package com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Equipment(
    var equipmentName: String="Empty_Name",
    var printedName: CharSequence="Default Name",
    var attributes: String="",
    var cost: Int=0,
    var weight: Double=0.0,
    var damageAc: String = "",
    var properties: String = "",
    var detailsText: CharSequence="Placeholder for the Equipment's details text",
    var expansion: String= "PHB"): Parcelable

fun Equipment?.toEquipment(): Equipment {
    return this ?: Equipment("Unknown Equipment")
}
fun Equipment.isEmpty(): Boolean{
    return this.equipmentName=="Empty_Name"
}
fun Equipment.equalsByName(equipment: Equipment): Boolean{
    return this.equipmentName == equipment.equipmentName
}
fun MutableList<Equipment>.sortEquipmentByNameDescending(): MutableList<Equipment>{
    return this.sortedByDescending { it.equipmentName }.toMutableList()
}
fun MutableList<Equipment>.sortEquipmentByName(): MutableList<Equipment>{
    return this.sortedBy{ it.equipmentName }.toMutableList()
}
fun MutableList<Equipment>.indexOfEquipmentByName(name: String): Int {
    return this.indexOf(find{ it.equipmentName == name })
}
fun MutableList<Equipment>.getEquipmentByName(name: String): Equipment?{
    return find {it.equipmentName==name}
}
fun MutableList<Equipment>.getEquipmentByNameOrDefault(name: String): Equipment {
    return if(find {it.equipmentName==name}!=null){find{it.equipmentName==name}!!}else{
        Equipment("Error equipment not found")
    }
}
fun MutableList<Equipment>.getEquipmentByNameOrPut(name: String, newEquipment: Equipment): Equipment {
    return if(find {it.equipmentName==name}!=null){find{it.equipmentName==name}!!}else{this.add(newEquipment);newEquipment}
}
@JvmName("MutableListEquipmentNameList")
fun MutableList<Equipment>.getNameList(): List<String>{
    val tempList = mutableListOf<String>()
    for(i in this){
        tempList.add(i.equipmentName)
    }
    return tempList.toList()
}
@JvmName("ListEquipmentNameList")
fun List<Equipment>.getNameList(): List<String>{
    val tempList = mutableListOf<String>()
    forEach{
        tempList.add(it.equipmentName)
    }
    return tempList.toList()
}

fun MutableList<Equipment>.getNameMutableList(): MutableList<String>{
    val tempList = mutableListOf<String>()
    for(i in this){
        tempList.add(i.equipmentName)
    }
    return tempList
}
