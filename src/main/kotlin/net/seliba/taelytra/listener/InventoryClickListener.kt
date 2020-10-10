package net.seliba.taelytra.listener

import net.seliba.taelytra.utils.ItemStackComparator.Companion.isAboutSimilar
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class InventoryClickListener(private val elytraItem: ItemStack) : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.currentItem != null && event.currentItem!!.isAboutSimilar(elytraItem)) {
            event.isCancelled = true
        }
    }

}
