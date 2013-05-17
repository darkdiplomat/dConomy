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
package net.visualillusionsent.minecraft.server.mod.plugin.dconomy.data;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.accounting.Account;

public final class OutputQueue{

    private LinkedList<Account> queue;

    public OutputQueue(){
        queue = new LinkedList<Account>();
    }

    public final void add(Account account){
        synchronized (queue) {
            queue.add(account);
            queue.notify();
        }
    }

    public final Account next(){
        Account account = null;
        if (queue.isEmpty()) {
            synchronized (queue) {
                try {
                    queue.wait();
                }
                catch (InterruptedException e) {
                    return null;
                }
            }
        }
        try {
            account = queue.getFirst();
            queue.removeFirst();
        }
        catch (NoSuchElementException nsee) {
            throw new InternalError("Race hazard in LinkedList object.");
        }
        return account;
    }

    public final void clear(){
        synchronized (queue) {
            queue.clear();
        }
    }
}
