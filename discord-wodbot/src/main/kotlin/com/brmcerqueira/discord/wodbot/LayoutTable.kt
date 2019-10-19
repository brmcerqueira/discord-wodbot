package com.brmcerqueira.discord.wodbot

class LayoutTable(private val margin: Int) {
    private val rows = mutableListOf<List<String>>()

    private fun center(value: String, size: Int): String {
        val pad = ' '
        if (size <= value.length)
            return value

        val stringBuffer = StringBuffer(size);
        for (i in 0 until (size - value.length) / 2) {
            stringBuffer.append(pad)
        }
        stringBuffer.append(value);
        while (stringBuffer.length < size) {
            stringBuffer.append(pad);
        }
        return stringBuffer.toString();
    }


    fun row(vararg values: String) {
        rows.add(values.toList())
    }

    fun print(stringBuffer: StringBuffer) {
        val map = HashMap<Int, Int>()

        rows.forEach { row ->
            row.forEachIndexed { index, value ->
                val length = map.getOrPut(index) {
                    value.length
                }

                if (length < value.length) {
                    map[index] = value.length
                }
            }
        }

        rows.forEach { row ->
            row.forEachIndexed { index, value ->
                stringBuffer.append(center(value, map[index]!!  + margin))
            }
            stringBuffer.appendln()
        }
    }
}