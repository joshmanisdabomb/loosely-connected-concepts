package com.joshmanisdabomb.lcc.data.generators.file

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter


class WinscpFileExporter(private val site: String, private val from: String, private val to: String, val da: DataAccessor) : DataProvider {

    override fun run(writer: DataWriter) {
        println("Running file export on site '$site' copying '$from' to '$to'.")
        val rt = Runtime.getRuntime()
        val command = "cmd /C \"\"%ProgramFiles(x86)%\\WinSCP\\WinSCP.exe\" \"$site\" /command \"put \"\"$from\"\" \"\"$to\"\"\" \"exit\"\""
        rt.exec(command)
    }

    override fun getName() = "${da.modid} WinSCP File Export"

}