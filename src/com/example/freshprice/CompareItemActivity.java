package com.example.freshprice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import model.Item;
import model.Store;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class CompareItemActivity extends Activity {
	ArrayList<Item> alist;
	ArrayList<Item> data;
	StableArrayAdapter adapter;
	String query = null;
	FreshpriceApplication state;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_compare);
		state = ((FreshpriceApplication) getApplicationContext());
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			query = intent.getStringExtra(SearchManager.QUERY);
			
		}
		ListView list = (ListView) findViewById(android.R.id.list);
		alist = new ArrayList<Item>();
		data = new ArrayList<Item>();
		Iterator<Store> storeiter = state.stores.iterator();
		Iterator<Item> it;
		int i = 0;
		while (storeiter.hasNext())
		{
			it = storeiter.next().inventory.keySet().iterator();
			i = 0;
			while (it.hasNext())
			{
				data.add(it.next());
			}
		}
		for (i = 0; i < 20; i++)
		{
			alist.add(data.get(i));
		}
		adapter = new StableArrayAdapter(this.getApplicationContext(), alist);
		list.setAdapter(adapter);
		
	    SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView sv = (SearchView) findViewById(R.id.editText1);
	    sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
	    sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				sortalist(query);
				return true;
			}

	    	
	    }); 
	    ImageButton home = (ImageButton) findViewById(R.id.home);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
					Intent intent = new Intent(state, FreshpriceActivity.class);
					startActivity(intent);
			}
			
		});
	}

	private void sortalist(final String query) {
		Iterator<Item> it = data.iterator();
		String q = query.toLowerCase();
		Item c;
		alist.clear();
		while (it.hasNext())
		{
			c = it.next();
			if (c.name.toLowerCase().contains(q))
				alist.add(c);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compare_item, menu);
		return true;
	}
	
	  public class StableArrayAdapter extends ArrayAdapter<Item> {
		  private final Context context;
		  protected ArrayList<Item> values;
		private FreshpriceApplication state;
		   public StableArrayAdapter(Context context, ArrayList<Item> values)
		   {
			   super(context, R.layout.item_compare_row, values);
			  this.state = ((FreshpriceApplication) getApplicationContext());
			  this.context = context;
			  this.values = values;
		   }
		   
		   @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			   LayoutInflater inflater = (LayoutInflater) context
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View rowView = inflater.inflate(R.layout.item_compare_row, parent, false);
			    TextView itemname = (TextView) rowView.findViewById(R.id.itemname);
			    TextView price = (TextView) rowView.findViewById(R.id.price);
			    TextView storename = (TextView) rowView.findViewById(R.id.storename);
			    storename.setText(values.get(position).store.name);
			    itemname.setText(values.get(position).name);
			    price.setText(String.format("%.2f", values.get(position).price));
			    final ImageButton btn = (ImageButton) rowView.findViewById(R.id.imageButton1);
			    final int pos = position;
			    if (values.get(position).inList)
			    {
			    	btn.setImageResource(android.R.drawable.btn_minus);
			    }
			    btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (values.get(pos).inList)
						{
							btn.setImageResource(android.R.drawable.btn_plus);
							values.get(pos).inList = false;
							state.mylist.remove(values.get(pos));
						}
						else
						{
							btn.setImageResource(android.R.drawable.btn_minus);
							values.get(pos).inList = true;
							state.mylist.add(values.get(pos));
						}
					}
			    	
			    });
			    

			    
			return rowView;
			   
		   }

		  }

}
