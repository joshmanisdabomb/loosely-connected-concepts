package com.joshmanisdabomb.lcc.abstracts.computing.storage

import com.joshmanisdabomb.lcc.component.ComputingStorageComponent
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

    fun get(disks: Iterable<StorageDisk>, storage: ComputingStorageComponent) = when (path.type) {
        StorageDivision.StorageDivisionType.DISK -> getDisk(disks)
        StorageDivision.StorageDivisionType.PARTITION -> getPartition(disks)
        else -> getFilepath(disks, storage)
    }

    fun getDisk(disks: Iterable<StorageDisk>): StorageDisk? {
        val label = path.disk ?: path.result.takeIf { interest.contains(StorageDivision.StorageDivisionType.DISK) } ?: return null
        val available: List<StorageDisk>
        if (label.startsWith('#')) {
            val id = label.substring(1)
            if (id.isEmpty()) return null
            available = disks.filter { it.id?.toString()?.replace("-", "")?.startsWith(id) == true }
        } else {
            available = disks.filter { it.label == label }
        }
        if (available.count() == 1) return available.first()
        return null
    }

    fun getPartition(disks: Iterable<StorageDisk>): StoragePartition? {
        val label = path.partition ?: path.result.takeIf { interest.contains(StorageDivision.StorageDivisionType.PARTITION) } ?: return null
        val disk = path.disk?.let { getDisk(disks) ?: return null }
        val partitions = disks.filter { disk == null || it == disk }.flatMap { it.partitions }
        val available: List<StoragePartition>
        if (label.startsWith('#')) {
            val id = label.substring(1)
            if (id.isEmpty()) return null
            available = partitions.filter { it.id?.toString()?.replace("-", "")?.startsWith(id) == true }
        } else {
            available = partitions.filter { it.label == label }
        }
        if (available.count() == 1) return available.first()
        return null
    }

    fun getFilepath(disks: Iterable<StorageDisk>, storage: ComputingStorageComponent): StorageDivision? {
        val filepath = path.filepath ?: listOf(path.result).takeIf { interest.contains(StorageDivision.StorageDivisionType.FOLDER) || interest.contains(StorageDivision.StorageDivisionType.FILE) } ?: return null
        var partition = path.partition?.let { getPartition(disks) ?: return null }
        if (partition == null) {
            val partitions = path.disk?.let { getDisk(disks) ?: return null }?.partitions
            if (partitions?.count() == 1) partition = partitions.first()
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
            if (step == ".") continue
            if (step == "..") {
                val parent = folder.path.lastOrNull()
                if (parent != null) folder = storage.getFolder(parent) ?: return null
                continue
            }
            val files = storage.getFiles(*folder.files.toTypedArray()).values
            val file = files.firstOrNull { it.name == step }
            if (file != null) {
                return file
            }
            val folders = storage.getFolders(*folder.folders.toTypedArray()).values
            folder = folders.firstOrNull { it.name == step } ?: return null
        }
        return folder
    }

    fun getFolder(disks: Iterable<StorageDisk>, storage: ComputingStorageComponent): StorageFolder? {
        return getFilepath(disks, storage) as? StorageFolder
    }

    fun getFile(disks: Iterable<StorageDisk>, storage: ComputingStorageComponent): StorageFile? {
        return getFilepath(disks, storage) as? StorageFile
    }

    fun getParentFolder(disks: Iterable<StorageDisk>, storage: ComputingStorageComponent): StorageFolder? {
        return StoragePathResolver(path.appendFilepath("..")).getFolder(disks, storage)
    }

}
