/*
 * This file is part of dConomy.
 *
 * Copyright © 2011-2013 Visual Illusions Entertainment
 *
 * dConomy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * dConomy is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with dConomy.
 * If not, see http://www.gnu.org/licenses/gpl.html.
 */
package net.visualillusionsent.dconomy.bukkit.api;

import net.canarymod.api.OfflinePlayer;
import net.visualillusionsent.dconomy.MessageTranslator;
import net.visualillusionsent.dconomy.dCoBase;
import net.visualillusionsent.dconomy.modinterface.ModType;
import net.visualillusionsent.dconomy.modinterface.ModUser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Bukkit Offline User implementation
 *
 * @author Jason (darkdiplomat)
 */
public final class Bukkit_User implements ModUser {

    private final Player player;

    /**
     * Constructs a new Bukkit_User
     *
     * @param player the {@link OfflinePlayer} to wrap
     */
    public Bukkit_User(Player player) {
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName() {
        return player.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasPermission(String perm) {
        return player.hasPermission(perm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void error(String key, Object... args) {
        player.sendMessage(ChatColor.RED + MessageTranslator.translate(key, getUserLocale(), args));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void message(String key, Object... args) {
        player.sendMessage(MessageTranslator.translate(key, getUserLocale(), args));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isConsole() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ModType getModType() {
        return ModType.BUKKIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserLocale() {
        return dCoBase.getServerLocale(); //Bukkit doesn't give access to player locale at this time
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Bukkit_User) {
            return obj == this;
        }
        return obj == player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return player.hashCode();
    }

}