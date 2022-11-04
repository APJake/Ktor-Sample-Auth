package com.apjake.utils.extensions

fun String?.parseStringArray(
    makeTrim: Boolean = true,
): List<String> {
    if (this.isNullOrEmpty()) return emptyList()
    val list = this.removeSurrounding("[", "]")
        .takeIf(String::isNotEmpty) // this handles the case of "[]"
        ?.split(",")
        ?: emptyList<String>()
    if (!makeTrim) return list
    return list.map { it.trim() }
}