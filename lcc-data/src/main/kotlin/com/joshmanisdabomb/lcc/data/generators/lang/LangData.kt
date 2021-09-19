package com.joshmanisdabomb.lcc.data.generators.lang

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.DataCache
import net.minecraft.data.DataProvider
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper
import java.nio.file.Files

class LangData(val da: DataAccessor, val locale: String = defaultLocale) : DataProvider {

    val translations = mutableMapOf<String, String>()

    override fun run(cache: DataCache) {
        val string: String = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(da.gson.toJson(translations))
        val string2 = DataProvider.SHA1.hashUnencodedChars(string).toString()
        val path = da.dg.output.resolve("assets/${da.modid}/lang/$locale.json")
        if (cache.getOldSha1(path) != string2 || !Files.exists(path, *arrayOfNulls(0))) {
            Files.createDirectories(path.parent)
            val bufferedWriter = Files.newBufferedWriter(path)
            var var7: Throwable? = null
            try {
                bufferedWriter!!.write(string)
            } catch (var16: Throwable) {
                var7 = var16
                throw var16
            } finally {
                if (bufferedWriter != null) {
                    if (var7 != null) {
                        try {
                            bufferedWriter.close()
                        } catch (var15: Throwable) {
                            var7.addSuppressed(var15)
                        }
                    } else {
                        bufferedWriter.close()
                    }
                }
            }
        }

        cache.updateSha1(path, string2)
    }

    override fun getName() = "${da.modid} $locale Language Data"

    //Translators

    operator fun get(key: String) = translations[key]

    operator fun set(key: String, value: String) {
        translations[key] = value
    }

    companion object {
        const val defaultLocale = "en_us"
    }

}