package net.seliba.taelytra.listener

import net.seliba.taelytra.utils.ItemStackComparator.Companion.isAboutSimilar
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack

class PlayerDeathListener(private val elytraItem: ItemStack) : Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        event.drops.removeIf {
            itemStack: ItemStack? -> isElytraItem(itemStack)
        }
    }

    private fun isElytraItem(itemStack: ItemStack?) : Boolean {
        return itemStack?.isAboutSimilar(elytraItem) ?: false
    }

}