package com.dineshsawant.datamig.database

import java.time.LocalDate
import java.util.*

class PartitionKey(
    val column: Column,
    val min: Any,
    val max: Any
) {

    val lastValue: Any = min

    fun nextRange(fetchSize: Int, last: Any?): Pair<Any, Any> {
        var lastValueToBeUsed = last?: lastValue

        var nextValue: Any
        return when (lastValueToBeUsed) {
            is Long -> {
                nextValue = lastValueToBeUsed + fetchSize
                Pair(lastValueToBeUsed, nextValue)
            }
            is Int  -> {
                nextValue = lastValueToBeUsed + fetchSize
                Pair(lastValueToBeUsed, nextValue)
            }
            is LocalDate -> {
                nextValue = lastValueToBeUsed.plusDays(fetchSize.toLong())
                Pair(lastValueToBeUsed, nextValue)
            }
            else -> throw IllegalArgumentException("Unable to create range for $lastValueToBeUsed")
        }
    }

}