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
package net.visualillusionsent.minecraft.server.mod.plugin.dconomy.accounting.wallet;

import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.dCoBase;

/**
 * Server Wallet container
 * 
 * @author Jason (darkdiplomat)
 * 
 */
public final class ServerWallet extends Wallet{

    private final boolean maxAlways;

    public ServerWallet(boolean maxAlways){
        super("SERVER", maxAlways ? 999999999999999999D : dCoBase.getProperties().getDouble("server.balance"), null);
        this.maxAlways = maxAlways;
    }

    /**
     * {@inheritDoc}<br>
     * Preforms checks on if the SERVER has MAX MONEY ALWAYS
     */
    @Override
    public final double getBalance(){
        return maxAlways ? 999999999999999999D : super.getBalance();
    }

    /**
     * {@inheritDoc}<br>
     * Preforms checks on if the SERVER has MAX MONEY ALWAYS
     */
    @Override
    public final double deposit(double add){
        return maxAlways ? 999999999999999999D : super.deposit(add);
    }

    /**
     * {@inheritDoc}<br>
     * Preforms checks on if the SERVER has MAX MONEY ALWAYS
     */
    @Override
    public final double deposit(String add){
        return maxAlways ? 999999999999999999D : super.deposit(add);
    }

    /**
     * {@inheritDoc}<br>
     * Preforms checks on if the SERVER has MAX MONEY ALWAYS
     */
    @Override
    public final double debit(double remove){
        return maxAlways ? 999999999999999999D : super.debit(remove);
    }

    /**
     * {@inheritDoc}<br>
     * Preforms checks on if the SERVER has MAX MONEY ALWAYS
     */
    @Override
    public final double debit(String remove){
        return maxAlways ? 999999999999999999D : super.debit(remove);
    }

    /**
     * {@inheritDoc}<br>
     * Preforms checks on if the SERVER has MAX MONEY ALWAYS
     */
    @Override
    public final double setBalance(double set){
        return maxAlways ? 999999999999999999D : super.setBalance(set);
    }

    /**
     * {@inheritDoc}<br>
     * Preforms checks on if the SERVER has MAX MONEY ALWAYS
     */
    @Override
    public final double setBalance(String set){
        return maxAlways ? 999999999999999999D : super.setBalance(set);
    }

    /**
     * {@inheritDoc}<br>
     * Preforms checks on if the SERVER has MAX MONEY ALWAYS and stores it to the config
     */
    @Override
    protected final void save(){
        if (!maxAlways) {
            dCoBase.getProperties().setServerBalance(getBalance());
        }
    }
}
