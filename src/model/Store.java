package model;

import java.util.HashMap;
import java.util.LinkedList;

public class Store {
	public String name;
	public float distance;
	public HashMap<Item, Float> inventory = new HashMap<Item, Float>();
	public Store(String n)
	{
		name = n;
	}
}
