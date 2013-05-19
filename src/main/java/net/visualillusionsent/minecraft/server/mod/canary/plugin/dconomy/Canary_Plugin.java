/* 
 * Copyright 2011 - 2013 Visual Illusions Entertainment.
 *  
 * This file is part of dConomy.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/gpl.html
 * 
 * Source Code available @ https://github.com/Visual-Illusions/dConomy
 */
package net.visualillusionsent.minecraft.server.mod.canary.plugin.dconomy;

import net.canarymod.plugin.Plugin;
import net.visualillusionsent.minecraft.server.mod.interfaces.ModType;
import net.visualillusionsent.minecraft.server.mod.interfaces.IModUser;

/**
 * Canary Plugin wrapper for Mod_User implementation
 * 
 * @author darkdiplomat
 * 
 */
public final class Canary_Plugin implements IModUser{
    private final Plugin plugin;

    /**
     * Constructs a new Canary_Plugin wrapper
     * 
     * @param plugin
     *            the {@link Plugin} to wrap
     */
    public Canary_Plugin(Plugin plugin){
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName(){
        return plugin.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void error(String key, Object... args){}

    /**
     * {@inheritDoc}
     */
    @Override
    public final void message(String key, Object... args){}

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isConsole(){
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ModType getModType(){
        return ModType.CANARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(String perm){
        return true;
    }
}
