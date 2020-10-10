package net.seliba.taelytra.utils

import org.bukkit.inventory.ItemStack

class ItemStackComparator {

    companion object {
        fun ItemStack.isAboutSimilar(otherItem: ItemStack) : Boolean {
            return itemMeta?.displayName == otherItem.itemMeta?.displayName && itemMeta?.lore == otherItem.itemMeta?.lore
        }
    }

}