package model;

public class Item {
	public boolean inList;
	public String name;
	public float quantity; 
	public String unit;
	public Store store;
	public float price;
	public boolean checked = false;
	
	public Item(String name, String unit)
	{
		this.name = name;
		this.unit = unit;
	}
	
	public Item(Item i)
	{
		this.inList = i.inList;
		this.name = i.name;
		this.quantity = i.quantity;
		this.unit = i.unit;
		this.store = i.store;
		this.price = i.price;
	}
}
