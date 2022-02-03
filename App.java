/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m3ml.vendingmachine;

import com.m3ml.vendingmachine.Controller.Controller;
import com.m3ml.vendingmachine.Service.VendingmachineServiceLayer;
import com.m3ml.vendingmachine.Service.VendingmachineServiceLayerlmpl;
import com.m3ml.vendingmachine.View.UserIO;
import com.m3ml.vendingmachine.View.UserIOConsoleelmpl;
import com.m3ml.vendingmachine.View.View;
import com.m3ml.vendingmachine.dao.VendingMachineDao;
import com.m3ml.vendingmachine.dao.VendingMachineDaoFile;
import com.m3ml.vendingmachine.dao.VendingmachineAuditDao;
import com.m3ml.vendingmachine.dao.VendingmachineAuditDaoFileImpl;

/**
 *
 * @author quang
 */
public class App {
     public static void main(String[] args) {
        UserIO io = new UserIOConsoleelmpl();
        View view = new View(io);
        VendingmachineAuditDao auditDao = new VendingmachineAuditDaoFileImpl();
        VendingMachineDao dao = new VendingMachineDaoFile();
        VendingmachineServiceLayer service = new VendingmachineServiceLayerlmpl(auditDao, dao);
        
        Controller controller = new Controller(view, service);
        
        controller.run();
}
}
