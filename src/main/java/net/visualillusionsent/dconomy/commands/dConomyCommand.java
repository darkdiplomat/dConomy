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
package net.visualillusionsent.dconomy.commands;

import net.visualillusionsent.dconomy.MessageTranslator;
import net.visualillusionsent.minecraft.server.mod.interfaces.ModUser;

public abstract class dConomyCommand{
    private final int minArgs;

    protected dConomyCommand(int minArgs){
        this.minArgs = minArgs;
    }

    public final boolean parseCommand(ModUser caller, String[] args, boolean adjust){
        if (args == null || caller == null) {
            return false;
        }
        String[] cmdArgs = adjust ? adjustedArgs(args, 1) : args;

        if (cmdArgs.length < minArgs) {
            caller.error(MessageTranslator.transMessage("error.args"));
            return false;
        }
        else {
            execute(caller, cmdArgs);
            return true;
        }
    }

    private final String[] adjustedArgs(String[] args, int start){
        if (args.length == 0) {
            return args;
        }
        String[] toRet = new String[args.length - start];
        try {
            System.arraycopy(args, start, toRet, 0, toRet.length);
        }
        catch (IndexOutOfBoundsException ioobe) {
            return new String[0];
        }
        return toRet;
    }

    protected abstract void execute(ModUser caller, String[] args);
}