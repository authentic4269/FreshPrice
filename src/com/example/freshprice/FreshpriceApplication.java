package com.example.freshprice;

import java.util.HashMap;
import java.util.LinkedList;

import android.app.Application;
import model.Deal;
import model.Item;
import model.Recipe;
import model.Store;

public class FreshpriceApplication extends Application {
	
	public LinkedList<Store> stores = new LinkedList<Store>();
	public LinkedList<Recipe> recipes = new LinkedList<Recipe>();
	public LinkedList<Item> mylist = new LinkedList<Item>();
	public HashMap<Store, LinkedList<Deal>> deals = new HashMap<Store, LinkedList<Deal>>();
	
	

}
