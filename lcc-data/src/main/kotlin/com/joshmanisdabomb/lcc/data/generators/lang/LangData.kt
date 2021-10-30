package com.joshmanisdabomb.lcc.data.generators.lang

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.batches.LangBatch
import net.minecraft.data.DataCache
import net.minecraft.data.DataProvider
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper
import java.nio.file.Files

class LangData(val batch: LangBatch, val da: DataAccessor) : DataProvider {

    override fun run(cache: DataCache) {
        batch.getTranslationsByLocale().forEach { (l, t) ->
            val json = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(da.gson.toJson(t))
            val hash = DataProvider.SHA1.hashUnencodedChars(json).toString()
            val path = da.path.resolve("assets/${da.modid}/lang/$l.json")

            if (cache.getOldSha1(path) != hash || !Files.exists(path)) {
                Files.createDirectories(path.parent)
                val bufferedWriter = Files.newBufferedWriter(path)

                var e: Throwable? = null
                try {
                    bufferedWriter!!.write(json)
                } catch (e2: Throwable) {
                    e = e2
                    throw e2
                } finally {
                    if (bufferedWriter != null) {
                        if (e != null) {
                            try {
                                bufferedWriter.close()
                            } catch (var15: Throwable) {
                                e.addSuppressed(var15)
                            }
                        } else {
                            bufferedWriter.close()
                        }
                    }
                }
            }

            cache.updateSha1(path, hash)
        }
    }

    override fun getName() = "${da.modid} Language Data"

}