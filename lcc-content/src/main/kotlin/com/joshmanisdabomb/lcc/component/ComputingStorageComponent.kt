package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskFile
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskFolder
import com.joshmanisdabomb.lcc.extensions.getCompoundObject
import com.joshmanisdabomb.lcc.extensions.modifyCompound
import com.joshmanisdabomb.lcc.extensions.putCompoundObject
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.world.WorldProperties
import java.util.*

class ComputingStorageComponent(private val properties: WorldProperties) : ComponentV3 {

    private val stackDictionary = mutableMapOf<ItemStack, Long>()
    private val stackDictionaryFlip = mutableMapOf<Long, ItemStack>()
    private var stackDictionaryLast = 0L

    private val roots = mutableMapOf<UUID, UUID>()
    private val folders = mutableMapOf<UUID, DiskFolder>()
    private val files = mutableMapOf<UUID, DiskFile>()

    fun getDictionaryStackId(stack: ItemStack): Long {
        val entry = stack.copy().apply { count = 1 }
        if (!stackDictionary.containsKey(entry)) {
            stackDictionary[stack] = ++stackDictionaryLast
            stackDictionaryFlip[stackDictionaryLast] = stack
        }
        return stackDictionary.get(entry) ?: error("Failed to determine dictionary ID for stack $stack.")
    }

    fun getDictionaryStack(id: Long): ItemStack? {
        return stackDictionaryFlip[id]
    }

    override fun readFromNbt(tag: NbtCompound) {
        roots.clear()
        tag.getCompound("PartitionRoots").apply {
            keys.forEach {
                val uuid = UUID.fromString(it)
                val folderId = getUuid(it)
                roots[uuid] = folderId
            }
        }
        folders.clear()
        tag.getCompound("Folders").apply {
            keys.forEach {
                val uuid = UUID.fromString(it)
                val folder = getCompoundObject(it, ::DiskFolder)
                folders[uuid] = folder
            }
        }
        files.clear()
        tag.getCompound("Files").apply {
            keys.forEach {
                val uuid = UUID.fromString(it)
                val file = getCompoundObject(it, ::DiskFile)
                files[uuid] = file
            }
        }
        stackDictionary.clear()
        stackDictionaryFlip.clear()
        stackDictionaryLast = 0L
        tag.getCompound("ItemDictionary").apply {
            keys.forEach {
                val id = it.toLong(36)
                val stack = ItemStack.fromNbt(getCompound(it).also { it.putByte("Count", 1) })
                stackDictionary[stack] = id
                stackDictionaryFlip[id] = stack
                if (id > stackDictionaryLast) stackDictionaryLast = id
            }
        }
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.modifyCompound("ItemDictionary", NbtCompound()) {
            stackDictionary.forEach { (k, v) -> put(v.toString(36), k.writeNbt(NbtCompound()).also { it.remove("Count") }) }
        }
        tag.modifyCompound("PartitionRoots", NbtCompound()) {
            roots.forEach { (k, v) -> putUuid(k.toString(), v) }
        }
        tag.modifyCompound("Folders", NbtCompound()) {
            folders.forEach { (k, v) -> putCompoundObject(k.toString(), v) {
                val nbt = NbtCompound()
                it.writeNbt(nbt)
                nbt
            } }
        }
        tag.modifyCompound("Files", NbtCompound()) {
            files.forEach { (k, v) -> putCompoundObject(k.toString(), v) {
                val nbt = NbtCompound()
                it.writeNbt(nbt)
                nbt
            } }
        }
    }

    fun getFolder(folder: UUID) = folders[folder]

    fun getFolders(vararg folders: UUID) = folders.mapNotNull { it to (this.folders[it] ?: return@mapNotNull null) }.toMap()

    fun getFile(file: UUID) = files[file]

    fun getFiles(vararg files: UUID) = files.mapNotNull { it to (this.files[it] ?: return@mapNotNull null) }.toMap()

    fun saveFile(file: DiskFile, folder: DiskFolder) {
        file.folder = folder.id
        folder.files += file.id
        files[file.id] = file
    }

    fun saveFolder(folder: DiskFolder, parent: DiskFolder) {
        folder.path = parent.path + parent.id
        parent.folders += folder.id
        folders[folder.id] = folder
    }

    fun getRootFolder(partition: UUID): DiskFolder? {
        val uuid = roots[partition] ?: return null
        return getFolder(uuid)
    }

    fun addRootFolder(partition: UUID): DiskFolder {
        val folder = DiskFolder(UUID.randomUUID(), emptyList(), "root")
        folders[folder.id] = folder
        roots[partition] = folder.id
        return folder
    }

    fun unlinkRootFolder(partition: UUID) {
        roots.remove(partition)
    }

}