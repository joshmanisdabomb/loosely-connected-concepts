package com.joshmanisdabomb.lcc.data.generators.commit

import net.minecraft.data.DataCache
import net.minecraft.data.DataProvider
import java.io.File
import java.nio.file.Path
import java.util.*

class CommitData(val source: Path, val to: Path, val excluder: (path: String) -> Boolean) : DataProvider {

    private val gen by lazy { source.toFile() }
    private val store by lazy { to.toFile() }

    override fun run(cache: DataCache) {
        cache.write()
        read("Commit new data to project at $to?", ::clean)
    }

    private fun read(prompt: String, decision: (choice: Char?) -> Boolean = { true }): Char? {
        val input = Scanner(System.`in`)
        var answer: Char?
        do {
            println(prompt)
            answer = input.next().trim().toLowerCase().firstOrNull()
        } while (decision(answer))
        return answer
    }

    private fun clean(answer: Char?) = when (answer) {
        'y', 'c' -> {
            if (gen.copyRecursively(store, true)) {
                println("Commit successful.")
                if (answer == 'c') {
                    val unknowns = store.walkTopDown().map { it.relativeTo(store).path }.minus(gen.walkTopDown().map { it.relativeTo(gen).path }).filter { excluder(it) }
                    unknowns.forEach { read("Delete $it?") { answer -> remove(store.resolve(it), answer) } }
                }
            } else {
                println("Commit failed. Generated assets can be found in $source")
            }
            false
        }
        'n' -> { println("Generated assets can be found in $source"); false }
        else -> true
    }

    private fun remove(file: File, answer: Char?) = when (answer) {
        'y' -> { println(if (file.delete()) "File deleted successfully." else "Failed to delete."); false }
        'n' -> false
        else -> true
    }

    companion object {
        fun defaultExcluder(path: String, vararg modids: String): Boolean {
            modids.forEach {
                if (path.startsWith("assets\\$it\\")) {
                    return !path.startsWith("assets\\$it\\textures") && !path.startsWith("assets\\$it\\particles") && !path.startsWith("assets\\$it\\sounds") && !path.startsWith("assets\\$it\\font") && !path.contains("\\template_") && !path.endsWith("\\sounds.json") && !path.endsWith("\\icon.png") && !path.endsWith("\\pack.png")
                }
                if (path.startsWith("data\\$it\\")) {
                    return !path.startsWith("data\\$it\\structures")
                }
            }
            return false
        }
    }

    override fun getName() = "Commit Prompt"

}