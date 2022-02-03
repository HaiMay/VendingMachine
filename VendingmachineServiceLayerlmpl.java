/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m3ml.vendingmachine.Service;

import com.m3ml.vendingmachine.dao.VendingMachineDao;
import com.m3ml.vendingmachine.dao.VendingmachineAuditDao;
import com.m3ml.vendingmachine.dao.VendingmachinePersistenceException;
import com.m3ml.vendingmachine.dto.Item;
import com.m3ml.vendingmachine.dto.change;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author quang
 */
public class VendingmachineServiceLayerlmpl implements VendingmachineServiceLayer {

    private VendingmachineAuditDao auditDao;
    private VendingMachineDao dao;

    public VendingmachineServiceLayerlmpl(VendingmachineAuditDao auditDao, VendingMachineDao dao) {
        this.auditDao = auditDao;
        this.dao = dao;
    }

    @Override
    public Map<String, BigDecimal> getItemsInStockWithCosts() throws VendingmachinePersistenceException {
        Map<String, BigDecimal> itemsInStockWithCosts = dao.getMapOfItemNamesInStockWithCosts();
        return itemsInStockWithCosts;
    }

    @Override
    public Item getItem(String code, BigDecimal inputMoney) throws InsufficientFundsException,
            NoInventoryException,
            VendingmachinePersistenceException {
        Item wantedItem = dao.getItem(code);
        if (wantedItem == null) {
            throw new NoInventoryException(
                    "ERROR: there are no " + code + "'s in the vending machine.");
        }
        checkIfEnoughMoney(wantedItem, inputMoney);
        int inventory = 0;

        removeOneItemFromInventory(code, inventory);
        return wantedItem;
    }

    @Override
    public Map<BigDecimal, BigDecimal> getChangePerCoin(Item items, BigDecimal money) {
        BigDecimal itemPrice = items.getPrice();
        Map<BigDecimal, BigDecimal> changeDuePerCoin = change.changeDuePerCoin(itemPrice, money);
        return changeDuePerCoin;
    }

    @Override
    public void checkIfEnoughMoney(Item items, BigDecimal inputMoney) throws InsufficientFundsException {
        if (items.getPrice().compareTo(inputMoney) == 1) {
            throw new InsufficientFundsException(
                    "ERROR: insufficient funds, you have only input " + inputMoney);
        }
    }

  

    @Override
    public void removeOneItemFromInventory(String code, int inventory) throws NoInventoryException, VendingmachinePersistenceException {
 if (dao.getItemInventory(code) > 0) {
            dao.removeOneItemFromInventory(code);

            auditDao.writeAuditEntry(" One " + code + " removed");
        } else {

            throw new NoInventoryException(
                    "ERROR: " + code + " is out of stock.");
        }    }

}
