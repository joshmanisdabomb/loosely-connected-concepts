package com.joshmanisdabomb.lcc.data.base

import com.joshmanisdabomb.lcc.data.LCCData
import net.minecraft.block.Block
import net.minecraft.data.DataCache
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper
import java.nio.file.Files

abstract class BaseLangData(val dg: DataGenerator, val modid: String, val locale: String = "en_US") : DataProvider {

    val translations = mutableMapOf<String, String>()

    abstract fun createTranslations()

    override fun run(cache: DataCache) {
        this.createTranslations()

        val string: String = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(LCCData.gson.toJson(translations))
        val string2 = DataProvider.SHA1.hashUnencodedChars(string).toString()
        val path = dg.output.resolve("assets/" + modid + "/lang/" + locale + ".json")
        if (cache.getOldSha1(path) != string2 || !Files.exists(path, *arrayOfNulls(0))) {
            Files.createDirectories(path.getParent())
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

    override fun getName() = modid + " Language Data"

    //Translators

    fun tkey(key: Block): String {
        return basic(Registry.BLOCK.getId(key).path)
    }

    fun tkey(key: Item): String {
        return basic(Registry.ITEM.getId(key).path)
    }

    fun last(key: String): String {
        return basic(key.split("\\.").last())
    }

    fun basic(str: String): String {
        val builder = StringBuilder()
        for (s in str.replace('_', ' ').split(" ")) {
            builder.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).append(' ')
        }
        return builder.toString().trim()
    }

    //Adders

    fun add(key: String, value: String) {
        translations.putIfAbsent(key, value)
    }

    fun add(key: Block, value: String) {
        add(key.translationKey, value)
    }

    fun add(key: Item, value: String) {
        add(key.translationKey, value)
    }

    fun addAll(translator: (key: String) -> String = this::last, vararg keys: String) {
        keys.forEach { this.add(it, translator(it)) }
    }

    fun <T> addAll(adder: (key: T, value: String) -> Unit, translator: (key: T) -> String, vararg keys: T) {
        keys.forEach { adder(it, translator(it)) }
    }

}
