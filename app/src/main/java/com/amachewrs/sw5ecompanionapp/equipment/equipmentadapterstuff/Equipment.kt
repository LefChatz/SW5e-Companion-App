package com.amachewrs.sw5ecompanionapp.equipment.equipmentadapterstuff
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Equipment(
    var equipmentname: String="Empty_Name",
    var printedname: CharSequence="Default Name",
    var attributes: String="",
    var cost: Int=0,
    var weight: Double=0.0,
    var damage_ac: String = "",
    var properties: String = "",
    var detailsText: CharSequence="Placeholder for the Equipment's details text",
    var expansion: String= "PHB"): Parcelable

fun Equipment?.toEquipment(): Equipment {
    return this ?: Equipment("Unknown Force Power")
}
fun Equipment.isEmpty(): Boolean{
    return this.equipmentname=="Empty_Name"
}
fun Equipment.equalsByName(equipment: Equipment): Boolean{
    return this.equipmentname == equipment.equipmentname
}
fun MutableList<Equipment>.sortEquipmentByNameDescending(): MutableList<Equipment>{
    return this.sortedByDescending { it.equipmentname }.toMutableList()
}
fun MutableList<Equipment>.sortEquipmentByName(): MutableList<Equipment>{
    return this.sortedBy{ it.equipmentname }.toMutableList()
}
fun MutableList<Equipment>.indexOfEquipmentByName(name: String): Int {
    return this.indexOf(find{ it.equipmentname == name })
}
fun MutableList<Equipment>.getEquipmentByName(name: String): Equipment?{
    return find {it.equipmentname==name}
}
fun MutableList<Equipment>.getEquipmentByNameOrDefault(name: String): Equipment {
    return if(find {it.equipmentname==name}!=null){find{it.equipmentname==name}!!}else{
        Equipment("Error equipment not found")
    }
}
fun MutableList<Equipment>.getEquipmentByNameOrPut(name: String, newequipment: Equipment): Equipment {
    return if(find {it.equipmentname==name}!=null){find{it.equipmentname==name}!!}else{this.add(newequipment);newequipment}
}
@JvmName("MutableListequipmentNameList")
fun MutableList<Equipment>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.equipmentname)
    }
    return templist.toList()
}
@JvmName("ListequipmentNameList")
fun List<Equipment>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    forEach{
        templist.add(it.equipmentname)
    }
    return templist.toList()
}

fun MutableList<Equipment>.getNameMutableList(): MutableList<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.equipmentname)
    }
    return templist
}
