package com.example.freshprice;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.freshprice.DealsBoardActivity.DealArrayAdapter;

import model.Item;
import model.Deal;
import model.Recipe;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RecipeActivity extends Activity {

	ArrayList<Recipe> recipes;
	RecipeArrayAdapter adapter;
	Recipe focusedRecipe = null;
	TextView fis = null;
	TextView fig = null;
	TextView ft = null;
	ImageButton lstbtn = null;
	FreshpriceApplication state = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recipes);
		
		recipes = new ArrayList<Recipe>();
		state = ((FreshpriceApplication) getApplicationContext());
		ListView list = (ListView) findViewById(android.R.id.list);
		Iterator<Recipe> listiter = state.recipes.iterator();
		while (listiter.hasNext())
		{
			recipes.add(listiter.next());
		}
	    adapter = new RecipeArrayAdapter(this.getApplicationContext(), recipes);
		list.setAdapter(adapter);
		fis = (TextView) findViewById(R.id.focusinstructions);
		fig = (TextView) findViewById(R.id.focusingredients);
		ft = (TextView) findViewById(R.id.focustitle);
		lstbtn = (ImageButton) findViewById(R.id.listbutton);
		focusRecipe(recipes.get(0));
		
		ImageButton home = (ImageButton) findViewById(R.id.home);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
					Intent intent = new Intent(state, FreshpriceActivity.class);
					startActivity(intent);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipe, menu);
		return true;
	}

	private class RecipeArrayAdapter extends ArrayAdapter<Recipe> {
		private final Context context;
		private FreshpriceApplication state;

		public RecipeArrayAdapter(Context context, ArrayList<Recipe> values) {
			super(context, R.layout.recipes, values);
			this.state = ((FreshpriceApplication) getApplicationContext());
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.recipe_row,
					parent, false);
			TextView recipename = (TextView) rowView.findViewById(R.id.recipename);
			TextView description = (TextView) rowView
					.findViewById(R.id.description);
			RatingBar rating = (RatingBar) rowView.findViewById(R.id.rating);
			rating.setNumStars(5);
			rating.setNumStars(recipes.get(position).rating);
			recipename.setText(recipes.get(position).title);
			description.setText(recipes.get(position).description);
			final int pos = position;
			rowView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (focusedRecipe == recipes.get(pos))
					{
						return;
					}
					else
					{
						focusRecipe(recipes.get(pos));
					}
				}

			});
			return rowView;

		}

		

	}
	
	protected void focusRecipe(Recipe recipe) {
		focusedRecipe = recipe;
		String ingredients = "";
		fis.setText(recipe.instructions);
		Iterator<Item> it = recipe.ingredients.keySet().iterator();
		while (it.hasNext())
		{
			Item i = it.next();
			ingredients = ingredients + recipe.ingredients.get(i) + " " + i.unit + "s " + i.name + "\n";
		}
		fig.setText(ingredients);
		ft.setText(recipe.title);
		if (isSelected(focusedRecipe))
		{
			lstbtn.setImageResource(android.R.drawable.btn_minus);
		}
		else
		{
			lstbtn.setImageResource(android.R.drawable.btn_plus);
		}
		lstbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isSelected(focusedRecipe))
				{
					lstbtn.setImageResource(android.R.drawable.btn_plus);
					removeFromList(focusedRecipe);
				}
				else
				{
					addToList(focusedRecipe);
					lstbtn.setImageResource(android.R.drawable.btn_minus);
				}
			}
			
		});
	}
	
	protected void addToList(Recipe focusedRecipe2) {
		Iterator<Item> it = focusedRecipe2.ingredients.keySet().iterator();
		while (it.hasNext())
		{
			state.mylist.add(it.next());
		}
	}

	protected void removeFromList(Recipe focusedRecipe2) {
		Iterator<Item> it = focusedRecipe2.ingredients.keySet().iterator();
		while (it.hasNext())
		{
			state.mylist.remove(it.next());
		}
	}

	public boolean isSelected(Recipe r)
	{
		Iterator<Item> it = focusedRecipe.ingredients.keySet().iterator();
		boolean selected = true;
		while (it.hasNext())
		{
			Item i = it.next();
			if (!state.mylist.contains(i))
			{
				selected = false;
			}
		}
		return selected;
	}
	
}