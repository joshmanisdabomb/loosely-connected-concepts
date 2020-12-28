package com.joshmanisdabomb.lcc.data

import com.google.common.base.Stopwatch
import com.google.gson.GsonBuilder
import net.minecraft.data.DataGenerator
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

object LCCData {

    internal val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

    fun generate(client: Boolean = true, server: Boolean = true, path: String = "..\\src\\generated\\resources"): Long {
        val stopwatch = Stopwatch.createStarted()
        val output = Paths.get(path)
        val dg = DataGenerator(output, emptyList())

        if (client) {
            dg.install(LangData(dg))
        }

        try {
            output.toFile().deleteRecursively()
            dg.run()
        } catch (e: IOException) {
            e.printStackTrace()
            return -1
        }
        return stopwatch.elapsed(TimeUnit.MILLISECONDS)
    }

    fun commit(from: String = "..\\src\\generated\\resources", to: String = "..\\src\\main\\resources"): Long {
        val stopwatch = Stopwatch.createStarted()

        val file1 = File(from)
        val file2 = File(to)
        try {
            file1.copyRecursively(file2, true)
            file2.resolve(".cache").deleteRecursively()
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

        return stopwatch.elapsed(TimeUnit.MILLISECONDS)
    }

}