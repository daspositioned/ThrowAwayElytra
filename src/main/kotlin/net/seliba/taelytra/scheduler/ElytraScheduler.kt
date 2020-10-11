package net.seliba.taelytra.scheduler

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import com.sk89q.worldguard.protection.regions.RegionContainer
import com.sk89q.worldguard.protection.regions.RegionQuery
import net.seliba.taelytra.utils.ItemStackComparator.Companion.isAboutSimilar
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin


class ElytraScheduler(private val javaPlugin: JavaPlugin, private val delay: Long, private val permission: String?, private val regionName: String, private val elytraItem: ItemStack, private val particle: Particle?) {

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
                if (hasElytraPermission(it)) {
                    performElytraCheck(it)
                }
            }
        }, delay, delay)

        return true
    }

    private fun hasElytraPermission(player: Player) : Boolean {
        return permission == null || player.hasPermission(permission)
    }

    private fun performElytraCheck(player: Player) {
        val query: RegionQuery = regionContainer!!.createQuery()
        val applicableRegions = query.getApplicableRegions(BukkitAdapter.adapt(player.location))
        if (applicableRegions.contains(region)) {
            giveElytraItem(player)
        } else {
            handlePlayerLanding(player)
        }
    }

    private fun giveElytraItem(player: Player) {
        if (player.inventory.chestplate == null) {
            player.inventory.chestplate = elytraItem
        }
    }

    private fun handlePlayerLanding(player: Player) {
        if (!hasElytra(player)) {
            return
        }

        if (!isOnGround(player)) {
            return
        }

        if (particle != null) {
            player.spawnParticle(particle, player.location.clone().add(0.0, 1.0, 0.0), 50)
        }
        player.inventory.chestplate = null
    }

    private fun hasElytra(player: Player) : Boolean {
        return player.inventory.chestplate != null && player.inventory.chestplate!!.isAboutSimilar(elytraItem)
    }

    private fun isOnGround(player: Player) : Boolean {
        return player.location.clone().subtract(0.0, 1.0, 0.0).block.type != Material.AIR
    }

}