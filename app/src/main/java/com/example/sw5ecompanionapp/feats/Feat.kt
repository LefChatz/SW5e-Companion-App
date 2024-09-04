package com.example.sw5ecompanionapp.feats
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Feat(
    var featname: String="Empty_Name",
    var asi: String="Default Name",
    var prerequisite: String="",
    var source: String="SRC",
    var detailsText: CharSequence="Placeholder for the Feat's details text",
    var isBig: Boolean = false): Parcelable

fun Feat?.toFeat(): Feat {
    return this ?: Feat("Unknown Feat")
}

fun Feat.equalsByName(feat: Feat): Boolean{
    return this.featname == feat.featname
}
fun MutableList<Feat>.sortFeatByNameDescending(): MutableList<Feat>{
    return this.sortedByDescending { it.featname }.toMutableList()
}
fun MutableList<Feat>.sortFeatByName(): MutableList<Feat>{
    return this.sortedBy{ it.featname }.toMutableList()
}
fun MutableList<Feat>.indexOfFeatByName(name: String): Int {
    return this.indexOf(find{ it.featname == name })
}
fun MutableList<Feat>.getFeatByName(name: String): Feat?{
    return find {it.featname==name}
}
fun MutableList<Feat>.getFeatByNameOrDefault(name: String): Feat {
    return if(find {it.featname==name}!=null){find{it.featname==name}!!}else{
        Feat("Error feat not found")
    }
}
fun MutableList<Feat>.getFeatByNameOrPut(name: String, newfeat: Feat): Feat {
    return if(find {it.featname==name}!=null){find{it.featname==name}!!}else{this.add(newfeat);newfeat}
}
@JvmName("MutableListfeatNameList")
fun MutableList<Feat>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.featname)
    }
    return templist.toList()
}
@JvmName("ListfeatNameList")
fun List<Feat>.getNameList(): List<String>{
    val templist = mutableListOf<String>()
    forEach{
        templist.add(it.featname)
    }
    return templist.toList()
}

fun MutableList<Feat>.getNameMutableList(): MutableList<String>{
    val templist = mutableListOf<String>()
    for(i in this){
        templist.add(i.featname)
    }
    return templist
}
