package com.joshmanisdabomb.lcc.abstracts.computing.storage

import com.joshmanisdabomb.lcc.component.ComputingStorageComponent
import com.joshmanisdabomb.lcc.extensions.onlyOrNull
import java.util.*

class StoragePathResolver(private var path: StoragePath) {

    private var using: UUID? = null
    private var cd: UUID? = null
    private var interest: List<StorageDivision.StorageDivisionType> = emptyList()

    fun alterPath(path: StoragePath): StoragePathResolver {
        this.path = path
        return this
    }

    fun interest(vararg type: StorageDivision.StorageDivisionType): StoragePathResolver {
        interest = type.toList()
        return this
    }

    fun withCurrentPartition(using: UUID?): StoragePathResolver {
        this.using = using
        return this
    }

    fun withCurrentDirectory(directory: UUID?): StoragePathResolver {
        cd = directory
        return this
    }

    fun single(disks: Iterable<StorageDisk>, storage: ComputingStorageComponent): StoragePathResult {
        val result = StoragePathResult(path)

        val disk = getDisk(disks)
        if (disk != null) {
            result._disk = disk
            result._partition = disk.partitions.onlyOrNull()
            result._folder = result._partition?.let { storage.getRootFolder(it.id ?: return@let null) }
            result._result = disk
        }
        val partition = getPartition(disks, disk)
        if (partition != null) {
            result._disk = partition.disk
            result._partition = partition
            result._folder = result._partition?.let { storage.getRootFolder(it.id ?: return@let null) }
            result._result = partition
        }
        val filepath = getFilepath(disks, storage)
        if (filepath != null) {
            result._partition = StorageDisk.findPartition(disks, filepath.partition)
            result._disk = result._partition?.disk
            when (filepath) {
                is StorageFolder -> {
                    result._folder = filepath
                    result._filepath = storage.getFolders(*filepath.path.toTypedArray())
                }
                is StorageFile -> {
                    result._file = filepath
                    result._folder = storage.getFolder(filepath.folder)
                    result._filepath = result._folder?.let { storage.getFolders(*it.path.toTypedArray()) }
                }
            }
            result._result = filepath
        }

        return result
    }

    private fun getDisk(disks: Iterable<StorageDisk>): StorageDisk? {
        val label = path.disk ?: path.result.takeIf { interest.contains(StorageDivision.StorageDivisionType.DISK) } ?: return null
        val available: List<StorageDisk>
        if (label.input.startsWith('#')) {
            val id = label.input.substring(1)
            if (id.isEmpty()) return null
            available = disks.filter { it.id?.toString()?.replace("-", "")?.startsWith(id) == true }
        } else {
            available = disks.filter { it.label == label.input }
        }
        if (available.count() > 1) throw TODO()
        if (available.count() == 1) return available.first()
        return null
    }

    private fun getPartition(disks: Iterable<StorageDisk>, under: StorageDisk? = null): StoragePartition? {
        val label = path.partition ?: path.result.takeIf { interest.contains(StorageDivision.StorageDivisionType.PARTITION) } ?: return null
        val disk = path.disk?.let { under ?: return null }
        val partitions = disks.filter { disk == null || it == disk }.flatMap { it.partitions }
        val available = if (label.input.startsWith('#')) {
            val id = label.input.substring(1)
            if (id.isEmpty()) return null
            partitions.filter { it.id?.toString()?.replace("-", "")?.startsWith(id) == true }
        } else {
            partitions.filter { it.label == label.input }
        }
        if (available.count() == 1) return available.first()
        return null
    }

    private fun getFilepath(disks: Iterable<StorageDisk>, storage: ComputingStorageComponent, underDisk: StorageDisk? = null, underPartition: StoragePartition? = null): StorageSoftDivision? {
        val filepath = path.filepath ?: listOf(path.result).takeIf { interest.contains(StorageDivision.StorageDivisionType.FOLDER) || interest.contains(StorageDivision.StorageDivisionType.FILE) } ?: return null
        var partition = path.partition?.let { underPartition ?: return null }
        if (partition == null) {
            val partitions = path.disk?.let { underDisk ?: return null }?.partitions
            partition = partitions?.onlyOrNull()
        }
        val final = partition ?: disks.flatMap { it.partitions }.firstOrNull { it.id == using } ?: return null
        val uuid = final.id ?: return null

        val start: StorageFolder
        if (path.absolute) {
            start = storage.getRootFolder(uuid) ?: return null
        } else {
            val cd = cd ?: return null
            start = storage.getFolder(cd) ?: return null
        }

        var folder = start
        for (step in filepath) {
            if (step.input == ".") continue
            if (step.input == "..") {
                val parent = folder.path.lastOrNull()
                if (parent != null) folder = storage.getFolder(parent) ?: return null
                continue
            }
            val files = storage.getFiles(*folder.files.toTypedArray())
            val file = files.firstOrNull { it.name == step.input }
            if (file != null) {
                return file
            }
            val folders = storage.getFolders(*folder.folders.toTypedArray())
            folder = folders.firstOrNull { it.name == step.input } ?: return null
        }
        return folder
    }

    fun getParentFolder(disks: Iterable<StorageDisk>, storage: ComputingStorageComponent): StoragePathResult {
        return StoragePathResolver(path.appendFilepath("..")).single(disks, storage)
    }

    class StoragePathResult internal constructor(val path: StoragePath) {
        val result get() = _result
        val division get() = result?.division
        val disk get() = _disk
        val partition get() = _partition
        val folder get() = _folder
        val file get() = _file
        val filepath get() = _filepath

        internal var _result: StorageDivision? = null
        internal var _disk: StorageDisk? = null
        internal var _partition: StoragePartition? = null
        internal var _folder: StorageFolder? = null
        internal var _file: StorageFile? = null
        internal var _filepath: List<StorageFolder>? = null
    }

}
