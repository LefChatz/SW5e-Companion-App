package com.example.sw5ecompanionapp.techcasting
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Techpower(
    var techpowername: String="Empty_Name",
    var printedname: CharSequence="Default Name",
    var castingtime: String="",
    var level: Int=10,
    var range: String="0 feet",
    var duration: String="up to 0 minutes",
    var concentration: Boolean = false,
    var source: String="FU",
    var isBig: Boolean = false,
    var detailsText: CharSequence="Placeholder for the Tech power's details text"): Parcelable

fun Techpower?.toTechpower(): Techpower {
    return this ?: Techpower("Unknown Tech Power")
}

fun Techpower.equalsByName(techpower: Techpower): Boolean{
    return this.techpowername == techpower.techpowername
}
fun Techpower.equalsStrict(techpower: Techpower): Boolean{
    return (this.techpowername == techpower.techpowername && this.printedname==techpower.printedname && this.castingtime==techpower.castingtime && this.level==techpower.level && this.concentration==techpower.concentration)
}
fun MutableList<Techpower>.sortTechpowerByLevel(): MutableList<Techpower> {
    return this.sortedWith(compareBy { it.level }).toMutableList()
}
fun MutableList<Techpower>.sortTechpowerByLevelDescending(): MutableList<Techpower> {
    return this.sortedWith(compareByDescending { it.level }).toMutableList()
}
fun MutableList<Techpower>.sortTechpowerByNameDescending(): MutableList<Techpower>{
    return this.sortedByDescending { it.techpowername }.toMutableList()
}
fun MutableList<Techpower>.sortTechpowerByName(): MutableList<Techpower>{
    return this.sortedBy{ it.techpowername }.toMutableList()
}
fun MutableList<Techpower>.indexOfTechpowerByName(name: String): Int {
    return this.indexOf(find{ it.techpowername == name })
}
fun MutableList<Techpower>.getTechpowerByName(name: String): Techpower?{
    return find {it.techpowername==name}
}
fun MutableList<Techpower>.getTechpowerByNameOrDefault(name: String): Techpower {
    return if(find {it.techpowername==name}!=null){find{it.techpowername==name}!!}else{
        Techpower("Error techpower not found")
    }
}
fun MutableList<Techpower>.getTechpowerByNameOrPut(name: String, newtechpower: Techpower): Techpower {
    return if(find {it.techpowername==name}!=null){find{it.techpowername==name}!!}else{this.add(newtechpower);newtechpower}
}
@JvmName("MutableListtechpowerNameList")
fun MutableList<Techpower>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.techpowername)
    }
    return templist.toList()
}
@JvmName("ListtechpowerNameList")
fun List<Techpower>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    forEach{
        templist.add(it.techpowername)
    }
    return templist.toList()
}

fun MutableList<Techpower>.getNameMutableList(): MutableList<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.techpowername)
    }
    return templist
}
