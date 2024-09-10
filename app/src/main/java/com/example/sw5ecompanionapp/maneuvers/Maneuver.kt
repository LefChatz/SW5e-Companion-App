package com.example.sw5ecompanionapp.maneuvers
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Maneuver(
    var maneuvername: String="Empty_Name",
    var type: String="Default Name",
    var prerequisite: String="",
    var source: String="SRC",
    var detailsText: CharSequence="Placeholder for the Maneuver's details text",
    var isBig: Boolean = false): Parcelable

fun Maneuver?.toManeuver(): Maneuver {
    return this ?: Maneuver("Unknown Maneuver")
}

fun Maneuver.equalsByName(maneuver: Maneuver): Boolean{
    return this.maneuvername == maneuver.maneuvername
}
fun MutableList<Maneuver>.sortManeuverByNameDescending(): MutableList<Maneuver>{
    return this.sortedByDescending { it.maneuvername }.toMutableList()
}
fun MutableList<Maneuver>.sortManeuverByName(): MutableList<Maneuver>{
    return this.sortedBy{ it.maneuvername }.toMutableList()
}
fun MutableList<Maneuver>.indexOfManeuverByName(name: String): Int {
    return this.indexOf(find{ it.maneuvername == name })
}
fun MutableList<Maneuver>.getManeuverByName(name: String): Maneuver?{
    return find {it.maneuvername==name}
}
fun MutableList<Maneuver>.getManeuverByNameOrDefault(name: String): Maneuver {
    return if(find {it.maneuvername==name}!=null){find{it.maneuvername==name}!!}else{
        Maneuver("Error maneuver not found")
    }
}
fun MutableList<Maneuver>.getManeuverByNameOrPut(name: String, newmaneuver: Maneuver): Maneuver {
    return if(find {it.maneuvername==name}!=null){find{it.maneuvername==name}!!}else{this.add(newmaneuver);newmaneuver}
}
@JvmName("MutableListmaneuverNameList")
fun MutableList<Maneuver>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.maneuvername)
    }
    return templist.toList()
}
@JvmName("ListmaneuverNameList")
fun List<Maneuver>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    forEach{
        templist.add(it.maneuvername)
    }
    return templist.toList()
}

fun MutableList<Maneuver>.getNameMutableList(): MutableList<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.maneuvername)
    }
    return templist
}
