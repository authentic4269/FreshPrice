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
import android.widget.Toast;

public class CompareStoresActivity extends Activity {

	ArrayList<StoreSum> alist;
	ArrayList<StoreSum> data;
	SumarrayAdapter adapter;
	String query = null;
	FreshpriceApplication state;
	
	private class StoreSum {
		public Store store;
		public float sum;
		
		public StoreSum(Store st, float su)
		{
			store = st; sum = su;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_compare);
		state = ((FreshpriceApplication) getApplicationContext());
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			query = intent.getStringExtra(SearchManager.QUERY);
		}
		ListView list = (ListView) findViewById(android.R.id.list);
		Iterator<Item> itemiter;
		Store cur;
		data = new ArrayList<StoreSum>();
		alist = new ArrayList<StoreSum>();
		Iterator<Store> storeiter;
		Iterator<Item> s;
		storeiter = state.stores.iterator();
		float total;
		Item cit;
		Item cip;
		while (storeiter.hasNext())
		{
			itemiter = state.mylist.iterator();
			cur = storeiter.next();
			total = (float) 0.0;
			while (itemiter.hasNext())
			{
				cit = itemiter.next();
				s = cur.inventory.keySet().iterator();
				while (s.hasNext())
				{
					cip = s.next();
					if (cip.name == cit.name)
					{
						total += cip.price;
					}
				}
			}
			total = (float) (8.00 * alist.size() + Math.random() * 6.0 - 3.0);
			data.add(new StoreSum(cur, total));
		}
		for (int i = 0; i < data.size(); i++)
		{
			alist.add(data.get(i));
		}
		adapter = new SumarrayAdapter(this.getApplicationContext(), alist);
		list.setAdapter(adapter);
		
	    SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView sv = (SearchView) findViewById(R.id.search);
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
		Iterator<StoreSum> it = data.iterator();
		String q = query.toLowerCase();
		StoreSum c;
		alist.clear();
		while (it.hasNext())
		{
			c = it.next();
			if (c.store.name.toLowerCase().contains(q))
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
	
	  public class SumarrayAdapter extends ArrayAdapter<StoreSum> {
		  private final Context context;
		  protected ArrayList<StoreSum> values;
		private FreshpriceApplication state;
		   public SumarrayAdapter(Context context, ArrayList<StoreSum> values)
		   {
			   super(context, R.layout.store_row, values);
			  this.state = ((FreshpriceApplication) getApplicationContext());
			  this.context = context;
			  this.values = values;
		   }
		   
		   @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			   LayoutInflater inflater = (LayoutInflater) context
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View rowView = inflater.inflate(R.layout.store_row, parent, false);
			    TextView itemname = (TextView) rowView.findViewById(R.id.itemname);
			    TextView cost = (TextView) rowView.findViewById(R.id.cost);
			    TextView storename = (TextView) rowView.findViewById(R.id.storename);
			    TextView distance = (TextView) rowView.findViewById(R.id.distance);
			    distance.setText(String.format("%.2f", values.get(position).store.distance));
			    storename.setText(values.get(position).store.name);
			    cost.setText(String.format("%.2f", values.get(position).sum));
			    final ImageButton btn = (ImageButton) rowView.findViewById(R.id.directions);
			    final int pos = position;
			    btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Context context = getApplicationContext();
						CharSequence text = "Directions to the store would be given here";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					}
			    	
			    });
			    

			    
			return rowView;
			   
		   }

		  }

}
