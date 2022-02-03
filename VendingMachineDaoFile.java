/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m3ml.vendingmachine.dao;

import com.m3ml.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author quang
 */
public class VendingMachineDaoFile implements VendingMachineDao {

    private Map<String, Item> items = new HashMap<>();
    public static final String DELIMITER = "::";

    private final String VM_FILE;

    public VendingMachineDaoFile() {
        VM_FILE = "VendingMachine.txt";
    }

    public VendingMachineDaoFile(String itemtextFile) {
        VM_FILE = itemtextFile;
    }

    @Override
    public void removeOneItemFromInventory(String code) throws VendingmachinePersistenceException {
        loadMachine();
        int prevInventory = items.get(code).getInventory();
        items.get(code).setInventory(prevInventory - 1);
        try {
            writeMachine();
        } catch (IOException ex) {
            Logger.getLogger(VendingMachineDaoFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Item> getAllItems() throws VendingmachinePersistenceException {
        loadMachine();
        return new ArrayList(items.values());
    }

    @Override
    public int getItemInventory(String code) throws VendingmachinePersistenceException {
        loadMachine();
        return items.get(code).getInventory();
    }

    @Override
    public Map<String, BigDecimal> getMapOfItemNamesInStockWithCosts() throws VendingmachinePersistenceException {
        loadMachine();

        Map<String, BigDecimal> itemsInStockWithCosts = items.entrySet()
                .stream()
                .filter(map -> map.getValue().getInventory() > 0)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue().getPrice()));

        return itemsInStockWithCosts;
    }

    private String marshallItem(Item anItem) {
        String itemAsText = anItem.getCode() + DELIMITER;
        itemAsText += anItem.getName() + DELIMITER;
        itemAsText += anItem.getPrice() + DELIMITER;
        itemAsText += anItem.getInventory();
        return itemAsText;
    }

    private Item unmarshallItem(String itemAsText) {

        String[] itemTokens = itemAsText.split("::");
        String code = itemTokens[0];
        Item itemFromFile = new Item(code);
        itemFromFile.setName(itemTokens[1]);
        BigDecimal bigDecimal = new BigDecimal(itemTokens[2]);
        itemFromFile.setPrice(bigDecimal);
        itemFromFile.setInventory(Integer.parseInt(itemTokens[3]));
        return itemFromFile;
    }

    private void writeMachine() throws VendingmachinePersistenceException, IOException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VM_FILE));
        } catch (IOException e) {
            throw new VendingmachinePersistenceException("Could not save the items.", e);
        }
        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

    private void loadMachine() throws VendingmachinePersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(VM_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingmachinePersistenceException(
                    "-_- Could not load item data into memory.", e);
        }
        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getName(), currentItem);
        }
        scanner.close();
    }

    @Override
    public Item getItem(String code) throws VendingmachinePersistenceException {
        loadMachine();
        return items.get(code);
    }

}
