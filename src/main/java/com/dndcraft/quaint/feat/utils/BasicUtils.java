package com.dndcraft.quaint.feat.utils;

import com.dndcraft.atlas.util.ItemUtil;
import com.dndcraft.quaint.feat.Feat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BasicUtils {
    public static boolean isGrabber(ItemStack stack) {
        if (stack == null) return false;
        if (! stack.getType().equals(Material.TRIDENT)) return false;
        return ItemUtil.getIntTag(stack, "isGrabber") == 1;
    }

    public static void putGrabber(Player player, int slot) {
        ItemStack stack = new ItemStack(Material.TRIDENT, 1);
        ItemUtil.setTag(stack, "isGrabber", 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setCustomModelData(1234567);
        stack.setItemMeta(meta);

        player.getInventory().setItem(slot, stack);
    }

    public static void putGrabberHand(Player player) {
        putGrabber(player, player.getInventory().getHeldItemSlot());
    }

    public static void removeAllGrabbers(Player player) {
        int i = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (isGrabber(stack)) player.getInventory().setItem(i, new ItemStack(Material.AIR));
            i ++;
        }
    }

    public static void startTimerRepeating(Runnable runnable, long delay, long period) {
        Feat.get().getServer().getScheduler().scheduleSyncRepeatingTask(Feat.get(), runnable, delay, period);
    }

    public static void registerListener(Listener listener) {
        Feat.get().getServer().getPluginManager().registerEvents(listener, Feat.get());
    }

    public static void info(String message) {
        Feat.get().getLogger().info(message);
    }

    public static void warn(String message) {
        Feat.get().getLogger().warning(message);
    }

    public static void error(String message) {
        Feat.get().getLogger().severe(message);
    }
}
