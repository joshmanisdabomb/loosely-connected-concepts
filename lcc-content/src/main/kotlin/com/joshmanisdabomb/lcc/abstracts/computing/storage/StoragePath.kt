package com.joshmanisdabomb.lcc.abstracts.computing.storage

data class StoragePath(val input: String) {

    val disk get() = _disk
    private var _disk: String? = null
    val partition get() = _partition
    private var _partition: String? = null
    val filepath get() = _filepath
    private var _filepath: MutableList<String>? = null
    val result: String
    val type: StorageDivision.StorageDivisionType?

    val absolute get() = _absolute
    private var _absolute = true

    init {
        var left = input
        var unknown: String? = null
        while (left.isNotEmpty()) {
            var mode = 0
            if (left.startsWith("::")) {
                left = left.substring(2)
                mode = 1
            } else if (left.startsWith(':')) {
                left = left.substring(1)
                mode = 2
            } else if (left.startsWith('/')) {
                left = left.substring(1)
                mode = 3
            }
            val (token, index) = parseLabel(left)
            when (mode) {
                1 -> _disk = token
                2 -> {
                    if (unknown != null) {
                        _disk = unknown
                        unknown = null
                    }
                    _partition = token
                }
                3 -> {
                    _filepath = _filepath ?: mutableListOf()
                    if (unknown != null) {
                        _filepath?.add(unknown)
                        unknown = null
                        _absolute = false
                    }
                    _filepath?.add(token)
                }
                else -> unknown = token
            }
            if (left.isNotEmpty()) {
                left = left.substring(index.coerceAtLeast(1))
            }
        }

        if (unknown != null) {
            result = unknown
            type = null
        } else if (_filepath != null) {
            result = _filepath?.lastOrNull() ?: "."
            type = StorageDivision.StorageDivisionType.FILE
        } else if (_partition != null) {
            result = _partition!!
            type = StorageDivision.StorageDivisionType.PARTITION
        } else if (_disk != null) {
            result = _disk!!
            type = StorageDivision.StorageDivisionType.DISK
        } else {
            result = input
            type = null
        }
    }

    private fun parseLabel(str: String): Pair<String, Int> {
        val label = StringBuilder()
        var quote: Char? = null
        for ((k, v) in str.withIndex()) {
            if (v == '"' || v == '\'') {
                if (quote == null) {
                    quote = v
                    continue
                }
                else if (quote == v) {
                    quote = null
                    continue
                }
            } else if (quote == null && (v == ':' || v == '/')) {
                return label.toString() to k
            }
            label.append(v)
        }
        return label.toString() to str.length
    }

}