package net.seliba.taelytra

import net.seliba.taelytra.configuration.Config
import net.seliba.taelytra.listener.InventoryClickListener
import net.seliba.taelytra.listener.PlayerDeathListener
import net.seliba.taelytra.scheduler.ElytraScheduler
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class ThrowAwayElytra : JavaPlugin() {

    private lateinit var config: Config

    private var permission: String? = null
    private var regionName: String? = null
    private var elytraCheckInterval: Long? = null
    private var itemStack: ItemStack? = null
    private var particle: Particle? = null

    override fun onEnable() {
        initializeConfig()
        parseConfig()
        startScheduler()
        if (ElytraScheduler(this, elytraCheckInterval!!, permission, regionName!!, itemStack!!, particle).start()) {
            logger.info("Started successfully!")
        }
    }

    private fun initializeConfig() {
        config = Config("config.yml", this)
        config.setDefault("spawn-worldguard-region", "spawn")
        config.setDefault("elytra-check-interval-in-ticks", 20L)
        config.setDefault("permission.enable", false)
        config.setDefault("permission.name", "throwawayelytra.use")
        config.setDefault("item.name", "&6Throw-Away Elytra")
        config.setDefault("item.lore", listOf("&aElytra which will &cdisappear &aafter", "&ayou &cland &aoutside of spawn"))
        config.setDefault("on-landing.particle.enable", true)
        config.setDefault("on-landing.particle.name", "CLOUD")
        config.save()
    }

    private fun parseConfig() {
        if (config.getBoolean("permission.enable")) {
            permission = config.getString("permission.name")
        }

        if (config.getBoolean("on-landing.particle.enable")) {
            try {
                particle = Particle.valueOf(config.getString("on-landing.particle.name")!!.toUpperCase())
            } catch(exception: IllegalArgumentException) {
                logger.warning("Could not parse particle name!")
                logger.warning("You can find a list of all particles on https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html")
                logger.warning("Particles on landing will be disabled")
            }
        }

        regionName = config.getString("spawn-worldguard-region")
        elytraCheckInterval = config.getLong("elytra-check-interval-in-ticks")

        itemStack = ItemStack(Material.ELYTRA)
        val itemMeta = itemStack!!.itemMeta!!
        itemMeta.setDisplayName(config.getString("item.name")!!.replace('&', '§'))
        itemMeta.lore = config.getStringList("item.lore").map { string -> string.replace('&', '§') }
        itemStack!!.itemMeta = itemMeta
    }

    private fun startScheduler() {
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(InventoryClickListener(itemStack!!), this)
        pluginManager.registerEvents(PlayerDeathListener(itemStack!!), this)
    }

}