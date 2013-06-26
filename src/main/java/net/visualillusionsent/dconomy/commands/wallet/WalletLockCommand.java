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
package net.visualillusionsent.dconomy.commands.wallet;

import net.visualillusionsent.dconomy.dCoBase;
import net.visualillusionsent.dconomy.accounting.wallet.WalletHandler;
import net.visualillusionsent.dconomy.commands.dConomyCommand;
import net.visualillusionsent.minecraft.server.mod.interfaces.ModUser;
import net.visualillusionsent.utils.BooleanUtils;

public final class WalletLockCommand extends dConomyCommand{

    public WalletLockCommand(){
        super(1);
    }

    protected final void execute(ModUser user, String[] args){
        ModUser theUser = args[1].toUpperCase().equals("SERVER") ? null : dCoBase.getServer().getUser(args[1]);
        if (theUser == null && !args[1].toUpperCase().equals("SERVER")) {
            user.error("error.404.user", args[1]);
            return;
        }
        if (!args[1].toUpperCase().equals("SERVER") && !WalletHandler.verifyAccount(theUser.getName())) {
            user.error("error.404.account", theUser.getName(), "WALLET");
            return;
        }
        boolean locked = BooleanUtils.parseBoolean(args[0]);
        WalletHandler.getWalletByName(theUser == null ? "SERVER" : theUser.getName()).setLockOut(locked);
        if (locked) {
            user.error("admin.account.locked", theUser == null ? "SERVER" : theUser.getName(), "WALLET");
        }
        else {
            user.error("admin.account.unlocked", theUser == null ? "SERVER" : theUser.getName(), "WALLET");
        }
    }
}