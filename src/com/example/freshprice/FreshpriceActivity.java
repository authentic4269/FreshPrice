package com.example.freshprice;

import java.util.ArrayList;
import java.util.LinkedList;

import model.Deal;
import model.Item;
import model.Recipe;
import model.Store;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class FreshpriceActivity extends Activity {

	
	ImageButton homeButton;
	ImageButton compareItemsButton;
	ImageButton myListButton;
	ImageButton dealsBoardButton;
	ImageButton goShoppingButton;
	ImageButton compareStoresButton;
	ImageButton recipesButton;
	ImageButton historyButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_freshprice);
		
		compareItemsButton = (ImageButton) findViewById(R.id.compareItemsButton);
		myListButton = (ImageButton) findViewById(R.id.myListButton);
		dealsBoardButton = (ImageButton) findViewById(R.id.dealsBoardButton);
		goShoppingButton = (ImageButton) findViewById(R.id.goShoppingButton);
		recipesButton = (ImageButton) findViewById(R.id.recipesButton);
		historyButton = (ImageButton) findViewById(R.id.historyButton);
		compareStoresButton = (ImageButton) findViewById(R.id.compareStoresButton);
		FreshpriceApplication state = ((FreshpriceApplication) getApplicationContext());
		Store stores[] = new Store[5];
		ArrayList<LinkedList<Deal>> deals = new ArrayList<LinkedList<Deal>>();
		for (int i = 0; i < 5; i++)
		{
			deals.add(new LinkedList<Deal>());
		}
		stores[0] = new Store("Wegmans");
		stores[1] = new Store("Topps");	
		stores[2] = new Store("Wal-Mart");
		stores[3] = new Store("Target");		
		stores[4] = new Store("Greenstar");		
		stores[0].distance = (float) 0.61;
		stores[1].distance = (float) 0.76;
		stores[2].distance = (float) 0.896;
		stores[3].distance = (float) 1.23;
		stores[4].distance = (float) 2.88;
		Recipe pie = new Recipe("Apple Pie");
		Recipe pasta = new Recipe("Linguine Alfredo");
		Recipe eggp = new Recipe("Eggplant Parmersan");
		pie.rating = 3;
		pasta.rating = 4;
		eggp.rating = 5;
		pie.description = "A delicious traditional Dutch Apple Pie";
		pasta.description = "Rich Tuscan Alfredo Linguine";
		eggp.description = "Italian style, baked and not fried";
		pie.instructions = "1. Beat all ingredients together\n2. Add spices and herbs\n3. Bake at 350 F for 30 minutes\n4. Serve warm with ice cream";
		eggp.instructions = "1. Beat all ingredients together\n2. Add spices and herbs\n3. Bake at 350 F for 30 minutes\n4. Serve warm with ice cream";
		pasta.instructions = "1. Beat all ingredients together\n2. Add spices and herbs\n3. Bake at 350 F for 30 minutes\n4. Serve warm with ice cream";

		float p = (float) 0.0;
		for (int i = 0; i < stores.length; i++)
			state.stores.add(stores[i]);
		String defaultItems[] = {"Gala Apples", "Halibut", "Shittake Mushrooms", "Pitted Olives", "Parmersan Cheese", "Kraft Macaroni", "Hummus", "Lettuce", "2% Milk", "Heavy Cream", "Sour Cream", "Flour", "Grape Juice", "Franzia House Red", "Simply Apple"
				, "Cinnamon", "Shortening", "Pie Crust", "Linguini", "Chicken Breasts", "Ragu Marinara", "Eggplant"};
		float defaultPrices[] = {(float) 1.95
				, (float) 5.66, 
 (float) 5.25, (float) 6.50, (float) 7.99, (float) 11.00, (float) 1.99, (float) 2.50, (float) 3.50, (float) 1.99, (float) 2.55, (float) 1.00, (float) 3.99, (float) 4.99, (float) 1.50, (float) 3.50, (float) 2.50, (float) 15.50
 , (float) 3.50, (float) 1.99, (float) 2.55, (float) 1.00, (float) 3.99, (float) 4.99, (float) 1.50, (float) 3.50, (float) 2.50, (float) 15.50};
		
		for (int i = 0; i < defaultItems.length; i++)
		{
			Item thisitem = new Item(defaultItems[i], "units");
			if (defaultItems[i] == "Gala Apples")
			{
				thisitem.unit = "Cups";
				pie.ingredients.put(thisitem,  1);
			}
			else if (defaultItems[i] == "Cinnamon")
			{
				thisitem.unit = "Tbsp";
				pie.ingredients.put(thisitem,  2);
			}
			else if (defaultItems[i] == "Shortening")
			{
				thisitem.unit = "Tbsp";
				pie.ingredients.put(thisitem,  1);
			}
			else if (defaultItems[i] == "Pie Crust")
			{
				thisitem.unit = "Crust";
				pie.ingredients.put(thisitem,  1);
			}
			else if (defaultItems[i] == "Heavy Cream")
			{
				thisitem.unit = "Pint";
				pasta.ingredients.put(thisitem,  3);
			}
			else if (defaultItems[i] == "Linguini")
			{
				thisitem.unit = "Box";
				pasta.ingredients.put(thisitem,  1);
			}
			else if (defaultItems[i] == "Chicken Breasts")
			{
				thisitem.unit = "Lb";
				pasta.ingredients.put(thisitem,  1);
			}
			else if (defaultItems[i] == "Parmersan Cheese")
			{
				thisitem.unit = "Tbsp";
				pasta.ingredients.put(thisitem,  1);
				eggp.ingredients.put(thisitem, 2);
			}
			else if (defaultItems[i] == "Ragu Marinara")
			{
				thisitem.unit = "Cup";
				eggp.ingredients.put(thisitem, 3);
			}
			else if (defaultItems[i] == "Eggplant")
			{
				thisitem.unit = "Cup";
				eggp.ingredients.put(thisitem, 2);
			}
			for (int j = 0; j < stores.length; j++)
			{
				Item cp = new Item(thisitem);
				cp.store = stores[j];
				double rand = Math.random();
				if (Math.random() > 0.5)
				{
					p = defaultPrices[i] + (float) rand;
					cp.price = p;
					stores[j].inventory.put(cp, p);
				}
				else
				{
					p = defaultPrices[i] - (float) rand;
					cp.price = p;
					stores[j].inventory.put(cp, p);
				}
				if (j == 0 && i == 0)
				{
					deals.get(j).add(new Deal(cp, "Two for One"));
				}
				else if (j == 1 && i == 1)
				{
					deals.get(j).add(new Deal(cp, "Two for One"));
				}
				else if (j == 2 && i == 2)
				{
					deals.get(j).add(new Deal(cp, "Two for One"));
				}
				else if (j == 3 && i == 3)
				{
					deals.get(j).add(new Deal(cp, "Two for One"));
				}
				else if (j == 4 && i == 4)
				{
					deals.get(j).add(new Deal(cp, "Two for One"));
				}
				else if (j == 0 && i == 5)
				{
					deals.get(j).add(new Deal(cp, "Buy 3 Get One Free"));
				}
				else if (j == 1 && i == 6)
				{
					deals.get(j).add(new Deal(cp, "Buy 3 Get One Free"));
				}
				else if (j == 2 && i == 7)
				{
					deals.get(j).add(new Deal(cp, "Buy 3 Get One Free"));
				}
				else if (j == 3 && i == 8)
				{
					deals.get(j).add(new Deal(cp, "Buy 3 Get One Free"));
				}
				else if (j == 4 && i == 9)
				{
					deals.get(j).add(new Deal(cp, "Buy 3 Get One Free"));
				}
			}
		}
		for (int i = 0; i < stores.length; i++)
		{
			state.deals.put(stores[i],  deals.get(i));
		}
		state.recipes.add(eggp);
		state.recipes.add(pasta);
		state.recipes.add(pie);
		compareItemsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				compareItems(arg0);
				
			}
			
		});
		compareStoresButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				compareStores(v);
				
			}
		});
		myListButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				myList(arg0);
				
			}
			
		});
		dealsBoardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dealsBoard(arg0);
				
			}
			
		});
		goShoppingButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				shop(arg0);
				
			}
			
		});
		recipesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				recipes(arg0);
				
			}
			
		});
		historyButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				history(arg0);
				
			}
			
		});
	}


	protected void compareItems(View arg0) {
		Intent intent = new Intent(this, CompareItemActivity.class);
		startActivity(intent);
		
	}
	
	protected void compareStores(View arg0) {
		Intent intent = new Intent(this, CompareStoresActivity.class);
		startActivity(intent);
	}


	protected void myList(View arg0) {
		Intent intent = new Intent(this, MyListActivity.class);
		startActivity(intent);
	}


	protected void dealsBoard(View arg0) {
		Intent intent = new Intent(this, DealsBoardActivity.class);
		startActivity(intent);
	}


	protected void shop(View arg0) {
		Intent intent = new Intent(this, ShopActivity.class);
		startActivity(intent);
	}


	protected void recipes(View arg0) {
		Intent intent = new Intent(this, RecipeActivity.class);
		startActivity(intent);
	}


	protected void history(View arg0) {
		Context context = getApplicationContext();
		CharSequence text = "The users history would be shown here";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.freshprice, menu);
		return true;
	}

}
