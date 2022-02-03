/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m3ml.vendingmachine.Controller;

import com.m3ml.vendingmachine.Service.InsufficientFundsException;
import com.m3ml.vendingmachine.Service.NoInventoryException;
import com.m3ml.vendingmachine.Service.VendingmachineServiceLayer;
import com.m3ml.vendingmachine.View.UserIO;
import com.m3ml.vendingmachine.View.UserIOConsoleelmpl;
import com.m3ml.vendingmachine.View.View;
import com.m3ml.vendingmachine.dao.VendingmachinePersistenceException;
import com.m3ml.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author quang
 */
public class Controller {

    private UserIO io = new UserIOConsoleelmpl();
    private View view;
    private VendingmachineServiceLayer service;

    public Controller(View view, VendingmachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        String itemSelection = "";
        BigDecimal inputMoney;
        view.displayMenuBanner();
        try {
            getMenu();
        } catch (VendingmachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        inputMoney = getMoney();
        while (keepGoing) {
            try {

                itemSelection = getItemSelection();

                if (itemSelection.equalsIgnoreCase("Exit")) {
                    keepGoing = false;
                    break;
                }
                getItem(itemSelection, inputMoney);
                keepGoing = false;
                break;

            } catch (InsufficientFundsException | NoInventoryException | VendingmachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
                view.displayPleaseTryAgainMsg();
            }
        }
        exitMessage();

    }

    private void getMenu() throws VendingmachinePersistenceException {
        Map<String, BigDecimal> itemsInStockWithCosts = service.getItemsInStockWithCosts();
        view.displayMenu(itemsInStockWithCosts);
    }

    private BigDecimal getMoney() {
        return view.getMoney();
    }

    private String getItemSelection() {
        return view.getItemSelection();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void getItem(String code, BigDecimal money) throws InsufficientFundsException, NoInventoryException, VendingmachinePersistenceException {
        Item wantedItem = service.getItem(code, money);
        Map<BigDecimal, BigDecimal> changeDuePerCoin = service.getChangePerCoin(wantedItem, money);
        view.displayChangeDuePerCoin(changeDuePerCoin);
        view.displayEnjoyBanner(code);
    }
}
