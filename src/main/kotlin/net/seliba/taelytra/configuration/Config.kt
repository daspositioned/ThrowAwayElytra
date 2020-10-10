package net.seliba.taelytra.configuration

import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class Config(private val fileName: String, private val javaPlugin: JavaPlugin) : YamlConfiguration() {

    private var file: File? = null

    init {
        reload()
    }

    private fun reload() {
        file = File(javaPlugin.dataFolder, fileName)
        try {
            if (!file!!.exists()) {
                if (!file!!.createNewFile()) {
                    throw RuntimeException("Could not create $fileName")
                }
            }
            load(file!!)
        } catch (e: IOException) {
            // Ignore this
        } catch (e: InvalidConfigurationException) {
            throw RuntimeException("Could not parse $fileName. Please make sure it is valid YAML.")
        }
    }

    fun setDefault(path: String?, value: Any?) {
        if (!isSet(path!!)) {
            set(path, value)
        }
    }

    fun save() {
        try {
            save(file!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}