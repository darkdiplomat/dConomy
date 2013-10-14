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
package net.visualillusionsent.dconomy.accounting.wallet;

import net.visualillusionsent.dconomy.dCoBase;
import net.visualillusionsent.dconomy.data.DataSourceType;
import net.visualillusionsent.dconomy.data.wallet.WalletDataSource;
import net.visualillusionsent.dconomy.data.wallet.WalletMySQLSource;
import net.visualillusionsent.dconomy.data.wallet.WalletSQLiteSource;
import net.visualillusionsent.dconomy.data.wallet.WalletXMLSource;
import net.visualillusionsent.dconomy.modinterface.ModUser;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Wallet Handler class<br>
 * manages Wallets
 *
 * @author Jason (darkdiplomat)
 */
public final class WalletHandler {

    private final ConcurrentHashMap<String, Wallet> wallets;
    private static final WalletHandler $;
    private final ServerWallet servwallet;
    private final WalletDataSource source;
    private static boolean init;

    static {
        $ = new WalletHandler(dCoBase.getDataHandler().getDataSourceType());
    }

    private WalletHandler(DataSourceType type) {
        wallets = new ConcurrentHashMap<String, Wallet>();
        servwallet = new ServerWallet(dCoBase.getProperties().getBooleanValue("server.max.always"));
        if (type == DataSourceType.MYSQL) {
            source = new WalletMySQLSource();
        } else if (type == DataSourceType.SQLITE) {
            source = new WalletSQLiteSource();
        } else {
            source = new WalletXMLSource();
        }
    }

    /**
     * Gets a {@link Wallet} by a user's name
     *
     * @param username the user's name to get a wallet for
     * @return the {@link Wallet} for the user, creating a new one if nessary
     */
    public static final Wallet getWalletByName(String username) {
        if (username.equals("SERVER")) {
            return $.servwallet;
        } else if (verifyAccount(username)) {
            return $.wallets.get(username);
        }
        return newWallet(username);
    }

    /**
     * Gets a {@link Wallet} for a {@link ModUser}
     *
     * @param user the {@link ModUser} to get a wallet for
     * @return the {@link Wallet} for the user if found; {@code null} if not found
     */
    public static final Wallet getWallet(ModUser user) {
        return getWalletByName(user.getName());
    }

    /**
     * Adds a {@link Wallet} to the manager
     *
     * @param wallet the {@link Wallet} to be added
     */
    public static final void addWallet(Wallet wallet) {
        $.wallets.put(wallet.getOwner(), wallet);
    }

    /**
     * Checks if a {@link Wallet} exists
     *
     * @param username the user's name to check Wallet for
     * @return {@code true} if the wallet exists; {@code false} otherwise
     */
    public static final boolean verifyAccount(String username) {
        return $.wallets.containsKey(username);
    }

    /**
     * Creates a new {@link Wallet} with default balance
     *
     * @param username the user's name to create a wallet for
     * @return the new {@link Wallet}
     */
    public static final Wallet newWallet(String username) {
        Wallet wallet = new UserWallet(username, dCoBase.getProperties().getDouble("default.balance"), false, $.source);
        addWallet(wallet);
        return wallet;
    }

    /**
     * Initializer method
     */
    public static final void initialize() {
        if (!init) {
            $.source.load();
            init = true;
        }
    }

    /**
     * Cleans up
     */
    public static final void cleanUp() {
        $.wallets.clear();
    }
}
