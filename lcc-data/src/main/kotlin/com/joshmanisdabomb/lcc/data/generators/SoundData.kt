package com.joshmanisdabomb.lcc.data.generators

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.data.json.sound.SoundProperties
import net.minecraft.data.DataCache
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper
import java.nio.file.Files

class SoundData(val dg: DataGenerator, val modid: String) : DataProvider {

    val sounds = mutableListOf<SoundProperties>()

    override fun run(cache: DataCache) {
        val string: String = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(DataUtils.gson.toJson(sounds.map { it.name to it.serialise(JsonObject()) }.toMap()))
        val string2 = DataProvider.SHA1.hashUnencodedChars(string).toString()
        val path = dg.output.resolve("assets/$modid/sounds.json")
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

    override fun getName() = "$modid Sound Data"

}