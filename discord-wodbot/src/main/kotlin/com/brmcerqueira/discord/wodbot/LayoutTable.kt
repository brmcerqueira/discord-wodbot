package com.brmcerqueira.discord.wodbot

class LayoutTable(private val margin: Int) {
    private val rows = mutableListOf<List<Any?>>()

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


    fun row(vararg values: Any?) {
        rows.add(values.toList())
    }

    fun print(stringBuffer: StringBuffer) {
        val map = HashMap<Int, Int>()

        rows.forEach { row ->
            row.forEachIndexed { index, value ->
                val length = map.getOrPut(index) {
                    value?.toString()?.length ?: 0
                }

                if (value != null && length < value.toString().length) {
                    map[index] = value.toString().length
                }
            }
        }

        rows.forEach { row ->
            var isOpen = false
            stringBuffer.append("> ")
            row.forEachIndexed { index, value ->
                val size = map[index]!!
                val text = center(value?.toString() ?: "".padEnd(size), size + margin)

                when(value) {
                    is LayoutTableEscape -> {
                        if (isOpen) {
                            stringBuffer.append("`")
                            isOpen = false
                        }
                        stringBuffer.append(text)
                    }
                    else -> {
                        if (!isOpen) {
                            stringBuffer.append("`")
                            isOpen = true
                        }

                        stringBuffer.append(text)
                    }
                }
            }

            if (isOpen) {
                stringBuffer.appendln("`")
            }
            else {
                stringBuffer.appendln()
            }
        }
    }
}

