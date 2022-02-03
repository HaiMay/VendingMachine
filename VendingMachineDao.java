/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m3ml.vendingmachine.dao;

import com.m3ml.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author quang
 */
public interface VendingMachineDao {

    void removeOneItemFromInventory(String code) throws VendingmachinePersistenceException;

    List<Item> getAllItems() throws VendingmachinePersistenceException;

    int getItemInventory(String code) throws VendingmachinePersistenceException;

    Item getItem(String code) throws VendingmachinePersistenceException;

    Map<String, BigDecimal> getMapOfItemNamesInStockWithCosts() throws VendingmachinePersistenceException;

}
