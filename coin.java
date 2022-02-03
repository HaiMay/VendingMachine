/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m3ml.vendingmachine.dto;

/**
 *
 * @author quang
 */
public enum coin {
    NICKLE("NICKLE",5), DIME("DIME",10), QUARTER("QUARTER",25), DOLLAR("DOLLAR",100); 
	private int value; 
	private final String code;
	private coin(String code, int value){ 
		this.code = code; this.value = value; 
	} 

    @Override
   public String toString(){
       switch (this) {
            case QUARTER:
                return "25";
            case DIME:
                return "10";
            case NICKLE:
                return "5";
            case DOLLAR:
                return "1";
        }
        return null;
   }

    public void setValue(int value) {
        this.value = value;
    }
	
        public int getvalue(){ 
		return value; 
		} 
	public String getCode(){ 
		return code; 
	} 
}
