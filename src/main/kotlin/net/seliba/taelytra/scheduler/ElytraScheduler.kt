package net.seliba.taelytra.scheduler

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import com.sk89q.worldguard.protection.regions.RegionContainer
import com.sk89q.worldguard.protection.regions.RegionQuery
import net.seliba.taelytra.utils.ItemStackComparator.Companion.isAboutSimilar
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin


class ElytraScheduler(private val javaPlugin: JavaPlugin, private val delay: Long, private val regionName: String, private val elytraItem: ItemStack) {

    private var hasStarted = false
    private var regionContainer: RegionContainer? = null
    private var region: ProtectedRegion? = null

    init {
        regionContainer = WorldGuard.getInstance().platform.regionContainer
        Bukkit.getWorlds().forEach {
            val regionManager = regionContainer?.get(BukkitAdapter.adapt(it))
            if (regionManager?.getRegion(regionName) != null) {
                region = regionManager.getRegion(regionName)
                return@forEach
            }
        }
    }

    fun start() : Boolean {
        // Check if the region has been found successfully
        if (region == null) {
            javaPlugin.logger.warning("Region could not be found!")
            javaPlugin.logger.warning("Disabling now...")
            Bukkit.getPluginManager().disablePlugin(javaPlugin)
            return false
        }

        if (hasStarted) {
            return false
        }
        hasStarted = true

        Bukkit.getScheduler().scheduleSyncRepeatingTask(javaPlugin, Thread {
            Bukkit.getOnlinePlayers().forEach {
                val query: RegionQuery = regionContainer!!.createQuery()
                val applicableRegions = query.getApplicableRegions(BukkitAdapter.adapt(it.location))
                if (applicableRegions.contains(region)) {
                    if (it.inventory.chestplate == null) {
                        it.inventory.chestplate = elytraItem
                    }
                } else {
                    if (it.inventory.chestplate != null && it.inventory.chestplate!!.isAboutSimilar(elytraItem)) {
                        if (it.location.clone().subtract(0.0, 1.0, 0.0).block.type != Material.AIR) {
                            it.inventory.chestplate = null
                        }
                    }
                }
            }
        }, delay, delay)

        return true
    }

}