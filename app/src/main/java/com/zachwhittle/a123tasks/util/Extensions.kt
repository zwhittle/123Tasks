package com.zachwhittle.a123tasks.util

import java.lang.StringBuilder

fun Char.isVowel() : Boolean = this.toLowerCase() == 'a' ||
        this.toLowerCase() == 'e' ||
        this.toLowerCase() == 'i' ||
        this.toLowerCase() == 'o' ||
        this.toLowerCase() == 'u'

fun String.startsWithAt() : Boolean = this.startsWith("@")

fun String.startsWithHash() : Boolean = this.startsWith("#")

fun String.startsWithBang() : Boolean = this.startsWith("!")

fun ArrayList<String>.onNewLines() : String {
    val builder = StringBuilder()

    for (s in this) {
        builder.append(s).append("\n")
    }

    return builder.toString().trim()
}