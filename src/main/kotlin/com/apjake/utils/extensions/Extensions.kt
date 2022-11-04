package com.apjake.utils.extensions

inline fun <reified T : Enum<T>> enumContains(name: String, ignoreCase: Boolean = false): Boolean {
    return enumValues<T>().any { it.name.equals(name, ignoreCase)}
}

inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String, ignoreCase: Boolean = false): T? {
    return enumValues<T>().find { it.name.equals(name, ignoreCase) }
}