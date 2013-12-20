package model;

import java.util.HashMap;

public class Recipe {
	public HashMap<Item, Integer> ingredients;
	public String instructions;
	public String description;
	public String title;
	public int rating;
	public Recipe(String t)
	{
		title = t;
		ingredients = new HashMap<Item, Integer>();
	}
}
