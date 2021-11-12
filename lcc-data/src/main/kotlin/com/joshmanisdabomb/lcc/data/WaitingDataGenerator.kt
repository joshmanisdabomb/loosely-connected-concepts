package com.joshmanisdabomb.lcc.data

import com.google.common.base.Stopwatch
import net.minecraft.data.DataCache
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import org.apache.logging.log4j.LogManager
import java.io.IOException
import java.nio.file.Path
import java.util.concurrent.TimeUnit

class WaitingDataGenerator(private val output: Path, private val inputs: Collection<Path>) {

    private val logger = LogManager.getLogger()
    private val providers = mutableListOf<DataProvider>()

    private val dataCache = DataCache(output, "cache").apply { ignore(output.resolve("version.json")) }
    private var index = 0

    private val all = Stopwatch.createUnstarted()
    private var current = Stopwatch.createUnstarted()

    @Throws(IOException::class)
    fun run() : Boolean {
        if (!all.isRunning) all.start()

        val dataProvider = providers.getOrNull(index) ?: return true
        if (!current.isRunning) {
            current.start()
            logger.info("Starting provider: {}", dataProvider.name)
        }

        dataProvider.run(dataCache)
        if ((dataProvider as? WaitingDataProvider)?.done != false) {
            current.stop()
            logger.info("{} finished after {} ms", dataProvider.name, current.elapsed(TimeUnit.MILLISECONDS))

            index += 1
            current = Stopwatch.createUnstarted()
        }

        if (index > providers.lastIndex) {
            logger.info("All providers took: {} ms", all.elapsed(TimeUnit.MILLISECONDS))
            dataCache.write()
            return true
        }
        return false
    }

    fun install(provider: DataProvider) {
        providers.add(provider)
    }

}