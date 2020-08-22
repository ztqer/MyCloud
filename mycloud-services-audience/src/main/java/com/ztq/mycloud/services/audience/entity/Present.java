package com.ztq.mycloud.services.audience.entity;

public class Present {
	private String name;
	private int price;
	private String specialEffects;
	
	public Present() {
		
	}
	
	public Present(String name, int price, String specialEffects) {
		super();
		this.name = name;
		this.price = price;
		this.specialEffects = specialEffects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSpecialEffects() {
		return specialEffects;
	}

	public void setSpecialEffects(String specialEffects) {
		this.specialEffects = specialEffects;
	}

	@Override
	public String toString() {
		return "Present [name=" + name + ", price=" + price + ", specialEffects=" + specialEffects + "]";
	}
}
