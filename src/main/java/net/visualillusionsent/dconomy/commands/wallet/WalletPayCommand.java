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

import net.visualillusionsent.dconomy.accounting.AccountingException;
import net.visualillusionsent.dconomy.accounting.wallet.Wallet;
import net.visualillusionsent.dconomy.accounting.wallet.WalletHandler;
import net.visualillusionsent.dconomy.accounting.wallet.WalletTransaction;
import net.visualillusionsent.dconomy.commands.dConomyCommand;
import net.visualillusionsent.dconomy.dCoBase;
import net.visualillusionsent.dconomy.modinterface.ModUser;

public final class WalletPayCommand extends dConomyCommand {

    public WalletPayCommand() {
        super(2);
    }

    @Override
    protected final void execute(ModUser user, String[] args) {
        ModUser theUser = args[1].toUpperCase().equals("SERVER") ? (ModUser)dCoBase.getServer() : dCoBase.getServer().getUser(args[1]);
        if (theUser == null) {
            user.error("error.404.user", args[1]);
            return;
        }
        if (!args[1].toUpperCase().equals("SERVER") && !WalletHandler.verifyAccount(theUser.getName())) {
            user.error("error.404.wallet", theUser.getName(), "WALLET");
            return;
        }
        Wallet userWallet = WalletHandler.getWalletByName(user.getName());
        Wallet payeeWallet = WalletHandler.getWalletByName(theUser.getName());
        try {
            userWallet.testDebit(args[0]);
            payeeWallet.testDeposit(args[0]);
            payeeWallet.deposit(args[0]);
            userWallet.debit(args[0]);
            user.message("paid.user", theUser.getName(), Double.parseDouble(args[0]));
            dCoBase.getServer().newTransaction(new WalletTransaction(user, theUser, WalletTransaction.ActionType.PAY, Double.parseDouble(args[0])));
        } catch (AccountingException ae) {
            user.error(ae.getMessage());
        }
    }
}
