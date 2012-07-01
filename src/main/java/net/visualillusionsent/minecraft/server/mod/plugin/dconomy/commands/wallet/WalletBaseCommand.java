package net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet;

import net.visualillusionsent.minecraft.server.mod.interfaces.Mod_Caller;
import net.visualillusionsent.minecraft.server.mod.interfaces.Mod_User;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.dCoBase;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.accounting.wallet.Wallet;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.accounting.wallet.WalletHandler;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.dConomyCommand;

public final class WalletBaseCommand extends dConomyCommand{

    public WalletBaseCommand(){
        super(0);
    }

    protected final void execute(Mod_Caller caller, String[] args){
        Wallet theWallet;
        if (args.length == 1) {
            Mod_User theUser = args[0].toUpperCase().equals("SERVER") ? null : dCoBase.getServer().getUser(args[0]);
            if (theUser == null && !args[0].toUpperCase().equals("SERVER")) {
                caller.sendError("error.404.user", args[0]);
                return;
            }
            if (!args[0].toUpperCase().equals("SERVER") && !WalletHandler.verifyAccount(theUser.getName())) {
                caller.sendError("error.404.wallet", theUser.getName());
                return;
            }
            theWallet = WalletHandler.getWalletByName(theUser == null ? "SERVER" : theUser.getName());
            caller.sendMessage("account.balance.other", theUser == null ? "SERVER" : theUser.getName(), theWallet.getBalance());
        }
        else {
            theWallet = WalletHandler.getWalletByName(caller.getName());
            caller.sendMessage("account.balance", Double.valueOf(theWallet.getBalance()));
        }
    }
}