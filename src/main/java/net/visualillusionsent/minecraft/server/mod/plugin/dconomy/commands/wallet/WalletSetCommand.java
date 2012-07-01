package net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.wallet;

import net.visualillusionsent.minecraft.server.mod.interfaces.Mod_Caller;
import net.visualillusionsent.minecraft.server.mod.interfaces.Mod_User;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.dCoBase;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.accounting.AccountingException;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.accounting.wallet.WalletHandler;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.accounting.wallet.WalletTransaction;
import net.visualillusionsent.minecraft.server.mod.plugin.dconomy.commands.dConomyCommand;

public final class WalletSetCommand extends dConomyCommand{

    public WalletSetCommand(){
        super(2);
    }

    protected final void execute(Mod_Caller caller, String[] args){
        Mod_User theUser = args[1].toUpperCase().equals("SERVER") ? null : dCoBase.getServer().getUser(args[1]);
        if (theUser == null && !args[1].toUpperCase().equals("SERVER")) {
            caller.sendError("error.404.user", args[1]);
            return;
        }
        if (!args[1].toUpperCase().equals("SERVER") && !WalletHandler.verifyAccount(theUser.getName())) {
            if (!(args.length > 2) || !args[2].equals("-force")) {
                caller.sendError("error.404.wallet", theUser.getName());
                return;
            }
        }
        try {
            double newbal = WalletHandler.getWalletByName(theUser == null ? "SERVER" : theUser.getName()).setBalance(args[0]);
            caller.sendError("admin.set.balance", theUser == null ? "SERVER" : theUser.getName(), newbal);
            dCoBase.getServer().newTransaction(new WalletTransaction(caller, theUser == null ? dCoBase.getServer().getServerUser() : theUser, WalletTransaction.ActionType.ADMIN_SET, Double.parseDouble(args[0])));
        }
        catch (AccountingException ae) {
            caller.sendError(ae.getMessage());
        }
    }
}