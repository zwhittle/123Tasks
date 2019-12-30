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

fun String.startsWithTilde() : Boolean = this.startsWith("~")


/**
 * ArrayList
 */

// Prints each item in the ArrayList on it's own line
fun ArrayList<String>.withNewLines(): String {
    val builder = StringBuilder()

    if (this.size > 0) {
        for (s in this) {
            builder.append(s).append("\n")
        }
        return builder.toString().trim()
    }

    return ""
}

// Prints each item in the ArrayList separated by commas
fun ArrayList<String>.withCommas(): String {
    val builder = StringBuilder()

    if (this.size > 0) {
        forEachIndexed { i, s ->
            builder.append(s.trim())

            if (i != this.lastIndex) {
                builder.append(", ")
            }
        }

        val output = builder.toString()

        return output
//        return output
    }

    return ""
}