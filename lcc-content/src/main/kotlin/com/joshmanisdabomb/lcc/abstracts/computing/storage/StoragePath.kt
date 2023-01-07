package com.joshmanisdabomb.lcc.abstracts.computing.storage

data class StoragePath(val input: String) {

    val parts get() = _parts.toList()
    private val _parts = mutableListOf<StoragePathPart>()

    val disk get() = _disk
    private var _disk: StoragePathPart? = null
    val partition get() = _partition
    private var _partition: StoragePathPart? = null
    val filepath get() = _filepath?.toList()
    private var _filepath: MutableList<StoragePathPart>? = null
    val result: StoragePathPart
    val type: StorageDivision.StorageDivisionType?

    val absolute get() = _absolute
    private var _absolute = true

    init {
        var left = input
        var pos = 0
        var unknown: StoragePathPart? = null
        while (left.isNotEmpty()) {
            var mode = 0
            if (left.startsWith("::")) {
                left = left.substring(2)
                _parts.add(StoragePathPart("::", "::", pos, token = true, type = StorageDivision.StorageDivisionType.DISK))
                pos += 2
                mode = 1
            } else if (left.startsWith(':')) {
                left = left.substring(1)
                _parts.add(StoragePathPart(":", ":", pos, token = true, type = StorageDivision.StorageDivisionType.PARTITION))
                pos += 1
                mode = 2
            } else if (left.startsWith('/')) {
                left = left.substring(1)
                _parts.add(StoragePathPart("/", "/", pos, token = true, type = StorageDivision.StorageDivisionType.FOLDER))
                pos += 1
                mode = 3
            }

            val (token, index) = parseLabel(left)
            val length = index.coerceAtLeast(1)
            val raw = left.substring(0, index)
            when (mode) {
                1 -> {
                    if (token.isNotEmpty()) {
                        val part = StoragePathPart(token, raw, pos, token = false, type = StorageDivision.StorageDivisionType.DISK)
                        _disk = part
                        _parts.add(part)
                    }
                }
                2 -> {
                    if (unknown != null) {
                        val part = unknown.copy(type = StorageDivision.StorageDivisionType.DISK)
                        _disk = part
                        _parts.add(part)
                        unknown = null
                    }
                    if (token.isNotEmpty()) {
                        val part = StoragePathPart(token, raw, pos, token = false, type = StorageDivision.StorageDivisionType.PARTITION)
                        _partition = part
                        _parts.add(part)
                    }
                }
                3 -> {
                    val filepath = _filepath ?: mutableListOf()
                    if (unknown != null) {
                        val part = unknown.copy(type = StorageDivision.StorageDivisionType.FOLDER)
                        filepath.add(part)
                        _parts.add(part)
                        unknown = null
                        _absolute = false
                    }
                    if (token.isNotEmpty()) {
                        val part = StoragePathPart(token, raw, pos, token = false, type = StorageDivision.StorageDivisionType.FILE)
                        if (filepath.any { it.input != ".." } && token == "..") {
                            filepath.removeAt(filepath.lastIndex)
                        } else if (filepath.isEmpty() || token != ".") {
                            filepath.add(part)
                        }
                        _parts.add(part)
                    }
                    _filepath = filepath
                }
                else -> {
                    unknown = StoragePathPart(token, raw, pos, token = false, type = null)
                }
            }
            if (left.isEmpty()) break
            left = left.substring(length)
            pos += length
        }

        if (unknown != null) {
            if (unknown.input == ".." || unknown.input == ".") {
                unknown = unknown.copy(type = StorageDivision.StorageDivisionType.FOLDER)
                type = StorageDivision.StorageDivisionType.FOLDER
            } else {
                type = null
            }
            result = unknown
            _parts.add(unknown)
            _absolute = false
        } else if (_filepath != null) {
            result = _filepath?.lastOrNull() ?: StoragePathPart(".", ".", 0, token = false, type = StorageDivision.StorageDivisionType.FILE)
            type = StorageDivision.StorageDivisionType.FILE
        } else if (_partition != null) {
            result = _partition!!
            type = StorageDivision.StorageDivisionType.PARTITION
        } else if (_disk != null) {
            result = _disk!!
            type = StorageDivision.StorageDivisionType.DISK
        } else {
            result = StoragePathPart(input, input, 0, token = false, type = null)
            type = null
            _absolute = false
        }
        _parts.sortBy(StoragePathPart::start)
    }

    fun appendFilepath(vararg filepath: String) = StoragePath(input + filepath.joinToString("/", prefix = "/"))

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

    data class StoragePathPart internal constructor(val input: String, val raw: String, val start: Int, val token: Boolean = false, val type: StorageDivision.StorageDivisionType? = null) {}

}