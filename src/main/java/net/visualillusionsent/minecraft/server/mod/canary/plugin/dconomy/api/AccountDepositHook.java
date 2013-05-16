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
package net.visualillusionsent.minecraft.server.mod.canary.plugin.dconomy.api;

import net.canarymod.hook.Hook;
import net.canarymod.plugin.Plugin;
import net.visualillusionsent.minecraft.server.mod.canary.plugin.dconomy.Canary_Plugin;
import net.visualillusionsent.minecraft.server.mod.interfaces.Mod_User;

/**
 * Account Deposit Hook<br>
 * dConomy Add-on should extend this class for their own Account instances
 * 
 * @author Jason (darkdiplomat)
 * 
 */
public abstract class AccountDepositHook extends Hook{

    private final Mod_User caller;
    private final String username;
    private final double deposit;
    private String error;

    /**
     * Constructs a new AccountDepositHook
     * 
     * @param caller
     *            the {@link Plugin} giving money
     * @param username
     *            the user's name who is having money deposited
     * @param deposit
     *            the amount to be deposited
     */
    public AccountDepositHook(Plugin caller, String username, double deposit){
        this.caller = new Canary_Plugin(caller);
        this.username = username;
        this.deposit = deposit;
    }

    /**
     * Gets the {@link Mod_User}(plugin) asking to take money
     * 
     * @return the {@link Mod_User}(plugin)
     */
    public final Mod_User getCaller(){
        return caller;
    }

    /**
     * Gets the user's name who is having money taken
     * 
     * @return the user's name
     */
    public final String getUserName(){
        return username;
    }

    /**
     * Gets the amount being deposited
     * 
     * @return the deposit amount
     */
    public final double getDeposit(){
        return deposit;
    }

    /**
     * Gets the error message if an error has occurred
     * 
     * @return {@code null} if no error occurred; The error message otherwise
     */
    public final String getErrorMessage(){
        return error;
    }

    /**
     * Internal use method to set the error message should one have occurred
     */
    public final void setErrorMessage(String error){
        this.error = error;
    }
}
