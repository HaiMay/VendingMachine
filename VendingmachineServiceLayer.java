/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m3ml.vendingmachine.Service;

import com.m3ml.vendingmachine.dao.VendingmachinePersistenceException;
import com.m3ml.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author quang
 */
public interface VendingmachineServiceLayer {

    void checkIfEnoughMoney(Item items, BigDecimal inputMoney) throws
            InsufficientFundsException;

    void removeOneItemFromInventory(String code, int inventory) throws
            NoInventoryException,
            VendingmachinePersistenceException;

    Map<String, BigDecimal> getItemsInStockWithCosts() throws
            VendingmachinePersistenceException;

    Item getItem(String code, BigDecimal inputMoney) throws
            InsufficientFundsException,
            NoInventoryException,
            VendingmachinePersistenceException;

    Map<BigDecimal, BigDecimal> getChangePerCoin(Item items, BigDecimal money);

}
