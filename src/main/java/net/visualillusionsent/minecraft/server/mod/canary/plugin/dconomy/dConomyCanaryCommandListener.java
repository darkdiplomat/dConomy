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

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandDependencyException;
import net.canarymod.commandsys.CommandListener;
import net.visualillusionsent.minecraft.server.mod.interfaces.IModUser;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.dCoBase;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.InformationCommand;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.dConomyCommand;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet.WalletAddCommand;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet.WalletBaseCommand;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet.WalletLockCommand;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet.WalletPayCommand;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet.WalletReloadCommand;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet.WalletRemoveCommand;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet.WalletResetCommand;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet.WalletSetCommand;

public final class dConomyCanaryCommandListener implements CommandListener{
    private final dConomyCommand infoCmd, walletbase, walletadd, walletremove, walletpay, walletset, walletreset, walletreload, walletlock;

    dConomyCanaryCommandListener(dConomy dCo) throws CommandDependencyException{
        infoCmd = new InformationCommand();
        walletbase = new WalletBaseCommand();
        walletadd = new WalletAddCommand();
        walletpay = new WalletPayCommand();
        walletremove = new WalletRemoveCommand();
        walletset = new WalletSetCommand();
        walletreset = new WalletResetCommand();
        walletreload = new WalletReloadCommand();
        walletlock = new WalletLockCommand();
        Canary.commands().registerCommands(this, dCo, false);
    }

    @Command(aliases = { "dconomy" },
            description = "dConomy Information Command",
            permissions = { "dconomy" },
            toolTip = "/dconomy")
    public final void information(MessageReceiver msgrec, String[] args){
        IModUser user = msgrec instanceof Player ? new Canary_User((Player) msgrec) : (IModUser) dCoBase.getServer();
        infoCmd.parseCommand(user, args, true);
    }

    @Command(aliases = { "wallet" },
            description = "Wallet Base",
            permissions = { "dconomy.wallet" },
            toolTip = "/wallet [subcommand] [args]")
    public final void walletBase(MessageReceiver msgrec, String[] args){
        IModUser user = msgrec instanceof Player ? new Canary_User((Player) msgrec) : (IModUser) dCoBase.getServer();

        if (!walletbase.parseCommand(user, args, true)) {
            msgrec.notice("/wallet [subcommand] [args]");
        }
    }

    @Command(aliases = { "add" },
            description = "Adds money to user's wallet, use -force to create an account",
            permissions = { "dconomy.admin.wallet.add" },
            toolTip = "/wallet add <amount> <user>",
            parent = "wallet")
    public final void walletAdd(MessageReceiver msgrec, String[] args){
        IModUser user = msgrec instanceof Player ? new Canary_User((Player) msgrec) : (IModUser) dCoBase.getServer();

        if (!walletadd.parseCommand(user, args, true)) {
            msgrec.notice("/wallet add <amount> <user> [-force]");
        }
    }

    @Command(aliases = { "pay" },
            description = "Used to pay another user",
            permissions = { "dconomy.wallet.pay" },
            toolTip = "/wallet pay <amount> <user>",
            parent = "wallet")
    public final void walletPay(MessageReceiver msgrec, String[] args){
        IModUser user = msgrec instanceof Player ? new Canary_User((Player) msgrec) : (IModUser) dCoBase.getServer();

        if (!walletpay.parseCommand(user, args, true)) {
            msgrec.notice("/wallet pay <amount> <user>");
        }
    }

    @Command(aliases = { "remove" },
            description = "Used to remove money from a user's wallet",
            permissions = { "dconomy.admin.wallet.remove" },
            toolTip = "/wallet remove <user> <amount>",
            parent = "wallet")
    public final void walletRemove(MessageReceiver msgrec, String[] args){
        IModUser user = msgrec instanceof Player ? new Canary_User((Player) msgrec) : (IModUser) dCoBase.getServer();

        if (!walletremove.parseCommand(user, args, true)) {
            msgrec.notice("/wallet remove <amount> <user>");
        }
    }

    @Command(aliases = { "set" },
            description = "Used to set the money of a user's wallet, use -force to create an account",
            permissions = { "dconomy.admin.wallet.set" },
            toolTip = "/wallet set <amount> <user> [-force]",
            parent = "wallet")
    public final void walletSet(MessageReceiver msgrec, String[] args){
        IModUser user = msgrec instanceof Player ? new Canary_User((Player) msgrec) : (IModUser) dCoBase.getServer();

        if (!walletset.parseCommand(user, args, true)) {
            msgrec.notice("/wallet set <amount> <user>");
        }
    }

    @Command(aliases = { "reset" },
            description = "Used to reset the money of a user's wallet",
            permissions = { "dconomy.admin.wallet.reset" },
            toolTip = "/wallet reset <user>",
            parent = "wallet")
    public final void walletReset(MessageReceiver msgrec, String[] args){
        IModUser user = msgrec instanceof Player ? new Canary_User((Player) msgrec) : (IModUser) dCoBase.getServer();

        if (!walletreset.parseCommand(user, args, true)) {
            msgrec.notice("/wallet reset <user>");
        }
    }

    @Command(aliases = { "reload" },
            description = "Used to reload a user's wallet from the datasource",
            permissions = { "dconomy.admin.wallet.reload" },
            toolTip = "/wallet reload <user>",
            parent = "wallet")
    public final void walletReload(MessageReceiver msgrec, String[] args){
        IModUser user = msgrec instanceof Player ? new Canary_User((Player) msgrec) : (IModUser) dCoBase.getServer();

        if (!walletreload.parseCommand(user, args, true)) {
            msgrec.notice("/wallet reload <user>");
        }
    }

    @Command(aliases = { "lock" },
            description = "Used to lock/unlock a user's wallet from the datasource",
            permissions = { "dconomy.admin.wallet.lock" },
            toolTip = "/wallet lock <yes|no (Or other boolean values)> <user>",
            parent = "wallet")
    public final void walletLock(MessageReceiver msgrec, String[] args){
        IModUser user = msgrec instanceof Player ? new Canary_User((Player) msgrec) : (IModUser) dCoBase.getServer();

        if (!walletlock.parseCommand(user, args, true)) {
            msgrec.notice("/wallet lock <yes|no> <user>");
        }
    }
}
