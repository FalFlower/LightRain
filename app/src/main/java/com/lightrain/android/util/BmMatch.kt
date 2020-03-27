package com.lightrain.android.util

class BmMatch(pattern: String) {
    private val patternBytes = pattern.toByteArray()
    private val patternSize = patternBytes.size
    private val badSkip = MutableList((Byte.MAX_VALUE - Byte.MIN_VALUE + 1), { pattern.toByteArray().size })
    private val goodSkip = MutableList(pattern.toByteArray().size, { patternSize })

    init {
        // 构建坏字符跳转记录表
        var pLen = patternSize
        for (b in patternBytes) {
            badSkip[b - Byte.MIN_VALUE] = --pLen
        }

        // 构建好后缀跳转记录表
        val suff = MutableList(patternSize, { patternSize })
        for (i in (patternSize - 2 downTo 0)) {
            var q = i
            while (q >= 0 && patternBytes[q] == patternBytes[patternSize - 1 - i + q]) {
                --q
            }
            suff[i] = i - q
        }
        for (i in (patternSize - 1 downTo 0)) {
            if (suff[i] == i + 1) {
                for (j in (0 until patternSize - 1 - i)) {
                    if (goodSkip[j] == patternSize) {
                        goodSkip[j] = patternSize - 1 - i
                    }
                }
            }
        }
        for (i in (0 until patternSize - 1)) {
            goodSkip[patternSize - 1 - suff[i]] = patternSize - 1 - i
        }
    }

    fun match(txt: String): List<Int> {
        val txtBytes = txt.toByteArray()
        val txtBytesSize = txtBytes.size
        var txtIndex = 0
        val res = mutableListOf<Int>()
        while (txtIndex <= txtBytesSize - patternSize) {
            var ptnIndex = patternSize - 1
            while (ptnIndex >= 0 && patternBytes[ptnIndex] == txtBytes[ptnIndex + txtIndex]) {
                --ptnIndex
            }
            if (ptnIndex < 0) {
                res.add(txtIndex)
                txtIndex += goodSkip[0]
            } else {
                txtIndex += maxOf(goodSkip[ptnIndex], badSkip[txtBytes[ptnIndex + txtIndex] - Byte.MIN_VALUE] - patternSize + 1 + ptnIndex)
            }
        }
        return res
    }
}