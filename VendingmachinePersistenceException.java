/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m3ml.vendingmachine.dao;

/**
 *
 * @author quang
 */
public class VendingmachinePersistenceException extends Exception{
   public VendingmachinePersistenceException (String message){
   super(message);
   }
    public VendingmachinePersistenceException (String message, Throwable cause){
        super(message, cause);
    }
}
