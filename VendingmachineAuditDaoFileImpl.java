/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m3ml.vendingmachine.dao;

import java.io.FileWriter;
 import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author quang
 */
public class VendingmachineAuditDaoFileImpl implements VendingmachineAuditDao {

    private final String AUDIT_FILE;

    
    public VendingmachineAuditDaoFileImpl() {
        this.AUDIT_FILE = "audit.txt";
    }

 
    public VendingmachineAuditDaoFileImpl(String auditTest) {
        this.AUDIT_FILE = auditTest;

    }

    @Override
    public void writeAuditEntry(String entry) throws VendingmachinePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingmachinePersistenceException("Could not persist audit information", e);
        }
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }
}
