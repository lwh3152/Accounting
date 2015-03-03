package com.lwh.accounting.entity;

import java.io.Serializable;
import java.util.Date;

public class Accounting implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1536472639350026646L;
	
	private int id;
	private int price;
	private int type;
	private int status;
	private int year;
	private int month;
	private int day;
	private String note;
	private String ctm;
	private String utm;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCtm() {
		return ctm;
	}
	public void setCtm(String ctm) {
		this.ctm = ctm;
	}
	public String getUtm() {
		return utm;
	}
	public void setUtm(String utm) {
		this.utm = utm;
	}
	
}
