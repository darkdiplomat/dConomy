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
package net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet;

import net.visualillusionsent.minecraft.server.mod.interfaces.Mod_User;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.dCoBase;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.accounting.wallet.Wallet;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.accounting.wallet.WalletHandler;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.dConomyCommand;

public final class WalletBaseCommand extends dConomyCommand{

    public WalletBaseCommand(){
        super(0);
    }

    protected final void execute(Mod_User user, String[] args){
        Wallet theWallet;
        if (args.length == 1 && (user.hasPermission("dconomy.admin.wallet") || !dCoBase.getProperties().getBooleanValue("adminonly.balance.check"))) {
            Mod_User theUser = args[0].toUpperCase().equals("SERVER") ? null : dCoBase.getServer().getUser(args[0]);
            if (theUser == null && !args[0].toUpperCase().equals("SERVER")) {
                user.error("error.404.user", args[0]);
                return;
            }
            if (!args[0].toUpperCase().equals("SERVER") && !WalletHandler.verifyAccount(theUser.getName())) {
                user.error("error.404.wallet", theUser.getName());
                return;
            }
            theWallet = WalletHandler.getWalletByName(theUser == null ? "SERVER" : theUser.getName());
            if (theWallet.isLocked()) {
                user.message("error.lock.out", theUser == null ? "SERVER" : theUser.getName());
            }
            else {
                user.message("account.balance.other", theUser == null ? "SERVER" : theUser.getName(), theWallet.getBalance());
            }
        }
        else {
            theWallet = WalletHandler.getWalletByName(user.getName());
            if (theWallet.isLocked()) {
                user.message("error.lock.out", user.getName());
            }
            else {
                user.message("account.balance", Double.valueOf(theWallet.getBalance()));
            }
        }
    }
}
