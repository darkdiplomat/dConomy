/*
 * This file is part of dConomy.
 *
 * Copyright © 2011-2014 Visual Illusions Entertainment
 *
 * dConomy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/gpl.html.
 */
package net.visualillusionsent.dconomy.canary;

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandDependencyException;
import net.canarymod.commandsys.TabComplete;
import net.visualillusionsent.dconomy.TabCompleter;
import net.visualillusionsent.dconomy.api.dConomyUser;
import net.visualillusionsent.dconomy.canary.api.Canary_User;
import net.visualillusionsent.dconomy.commands.dConomyCommand;
import net.visualillusionsent.dconomy.commands.wallet.WalletAddCommand;
import net.visualillusionsent.dconomy.commands.wallet.WalletBaseCommand;
import net.visualillusionsent.dconomy.commands.wallet.WalletLockCommand;
import net.visualillusionsent.dconomy.commands.wallet.WalletPayCommand;
import net.visualillusionsent.dconomy.commands.wallet.WalletReloadCommand;
import net.visualillusionsent.dconomy.commands.wallet.WalletRemoveCommand;
import net.visualillusionsent.dconomy.commands.wallet.WalletResetCommand;
import net.visualillusionsent.dconomy.commands.wallet.WalletSetCommand;
import net.visualillusionsent.dconomy.dCoBase;
import net.visualillusionsent.minecraft.plugin.canary.VisualIllusionsCanaryPluginInformationCommand;

import java.util.List;

public final class CanaryConomyCommandListener extends VisualIllusionsCanaryPluginInformationCommand {
    private final dConomyCommand[] cmds = new dConomyCommand[8];

    CanaryConomyCommandListener(CanaryConomy dCo) throws CommandDependencyException {
        super(dCo);
        cmds[0] = new WalletBaseCommand(dCo.getBaseInstance().getWalletHandler());
        cmds[1] = new WalletAddCommand(dCo.getBaseInstance().getWalletHandler());
        cmds[2] = new WalletPayCommand(dCo.getBaseInstance().getWalletHandler());
        cmds[3] = new WalletRemoveCommand(dCo.getBaseInstance().getWalletHandler());
        cmds[4] = new WalletSetCommand(dCo.getBaseInstance().getWalletHandler());
        cmds[5] = new WalletResetCommand(dCo.getBaseInstance().getWalletHandler());
        cmds[6] = new WalletReloadCommand(dCo.getBaseInstance().getWalletHandler());
        cmds[7] = new WalletLockCommand(dCo.getBaseInstance().getWalletHandler());
        Canary.commands().registerCommands(this, dCo, false);
    }

    @Command(aliases = { "dconomy" },
            description = "dConomy Information Command",
            permissions = { "" },
            toolTip = "/dconomy")
    public final void information(MessageReceiver msgrec, String[] args) {
        this.sendInformation(msgrec);
    }

    @Command(aliases = { "wallet" },
            description = "Wallet Base",
            permissions = { "dconomy.wallet" },
            toolTip = "/wallet [subcommand] [args]",
            tabCompleteMethod = "walletTabComplete"
    )
    public final void walletBase(MessageReceiver msgrec, String[] args) {
        if (!cmds[0].parseCommand(getUser(msgrec), args, true)) {
            msgrec.notice("/wallet [subcommand] [args]");
        }
    }

    @Command(aliases = { "add" },
            description = "Adds money to user's wallet, use -force to create an account",
            permissions = { "dconomy.admin.wallet.add" },
            toolTip = "/wallet add <amount> <user>",
            parent = "wallet")
    public final void walletAdd(MessageReceiver msgrec, String[] args) {
        if (!cmds[1].parseCommand(getUser(msgrec), args, true)) {
            msgrec.notice("/wallet add <amount> <user> [-force]");
        }
    }

    @Command(aliases = { "pay" },
            description = "Used to pay another user",
            permissions = { "dconomy.wallet.pay" },
            toolTip = "/wallet pay <amount> <user>",
            parent = "wallet")
    public final void walletPay(MessageReceiver msgrec, String[] args) {
        if (!cmds[2].parseCommand(getUser(msgrec), args, true)) {
            msgrec.notice("/wallet pay <amount> <user>");
        }
    }

    @Command(aliases = { "remove" },
            description = "Used to remove money from a user's wallet",
            permissions = { "dconomy.admin.wallet.remove" },
            toolTip = "/wallet remove <user> <amount>",
            parent = "wallet")
    public final void walletRemove(MessageReceiver msgrec, String[] args) {
        if (!cmds[3].parseCommand(getUser(msgrec), args, true)) {
            msgrec.notice("/wallet remove <amount> <user>");
        }
    }

    @Command(aliases = { "set" },
            description = "Used to set the money of a user's wallet, use -force to create an account",
            permissions = { "dconomy.admin.wallet.set" },
            toolTip = "/wallet set <amount> <user> [-force]",
            parent = "wallet")
    public final void walletSet(MessageReceiver msgrec, String[] args) {
        if (!cmds[4].parseCommand(getUser(msgrec), args, true)) {
            msgrec.notice("/wallet set <amount> <user>");
        }
    }

    @Command(aliases = { "reset" },
            description = "Used to reset the money of a user's wallet",
            permissions = { "dconomy.admin.wallet.reset" },
            toolTip = "/wallet reset <user>",
            parent = "wallet")
    public final void walletReset(MessageReceiver msgrec, String[] args) {
        if (!cmds[5].parseCommand(getUser(msgrec), args, true)) {
            msgrec.notice("/wallet reset <user>");
        }
    }

    @Command(aliases = { "reload" },
            description = "Used to reload a user's wallet from the datasource",
            permissions = { "dconomy.admin.wallet.reload" },
            toolTip = "/wallet reload <user>",
            parent = "wallet")
    public final void walletReload(MessageReceiver msgrec, String[] args) {
        if (!cmds[6].parseCommand(getUser(msgrec), args, true)) {
            msgrec.notice("/wallet reload <user>");
        }
    }

    @Command(aliases = { "lock" },
            description = "Used to lock/unlock a user's wallet from the datasource",
            permissions = { "dconomy.admin.wallet.lock" },
            toolTip = "/wallet lock <yes|no (Or other boolean values)> <user>",
            parent = "wallet")
    public final void walletLock(MessageReceiver msgrec, String[] args) {
        if (!cmds[7].parseCommand(getUser(msgrec), args, true)) {
            msgrec.notice("/wallet lock <yes|no> <user>");
        }
    }

    @TabComplete
    public final List<String> walletTabComplete(MessageReceiver msgrec, String[] args) {
        return TabCompleter.match(getUser(msgrec), args);
    }

    private dConomyUser getUser(MessageReceiver msgrec) {
        return msgrec instanceof Player ? new Canary_User((Player) msgrec) : (dConomyUser) dCoBase.getServer();
    }
}
