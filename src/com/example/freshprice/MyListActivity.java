package com.example.freshprice;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.freshprice.CompareItemActivity.StableArrayAdapter;

import model.Item;
import model.Store;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
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
import android.widget.TextView;

public class MyListActivity extends ListActivity {

	private ArrayList<Item> data;
	private MylistArrayAdapter adapter;
	FreshpriceApplication state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylist);
		state = ((FreshpriceApplication) getApplicationContext());
		ListView list = (ListView) findViewById(android.R.id.list);
		data = new ArrayList<Item>();
		
		Iterator<Item> listiter = state.mylist.iterator();
		int i = 0;
		float total = (float) 0.0;
		String storename = "Wegmans";
		while (listiter.hasNext())
		{
			Item cur= listiter.next();
			data.add(cur);
			if (storename != null)
				storename = cur.store.name;
			total += cur.price;
		}
		adapter = new MylistArrayAdapter(this.getApplicationContext(), data);
		list.setAdapter(adapter);
		TextView totalprice = (TextView) findViewById(R.id.totalprice);
	    totalprice.setText(String.format("%.2f", total));

		TextView store = (TextView) findViewById(R.id.store);
		store.setText("Total cost at " + storename + ": ");
		
		ImageButton home = (ImageButton) findViewById(R.id.home);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
					Intent intent = new Intent(state, FreshpriceActivity.class);
					startActivity(intent);
			}
			
		});
	}
	
	  public class MylistArrayAdapter extends ArrayAdapter<Item> {
		  private final Context context;
		private FreshpriceApplication state;
		
		   public MylistArrayAdapter(Context context, ArrayList<Item> values)
		   {
			   super(context, R.layout.item_compare_row, values);
			  this.state = ((FreshpriceApplication) getApplicationContext());
			  this.context = context;
		   }
		   
		   @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			   LayoutInflater inflater = (LayoutInflater) context
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View rowView = inflater.inflate(R.layout.mylist_row, parent, false);
			    TextView itemname = (TextView) rowView.findViewById(R.id.itemname);
			    TextView price = (TextView) rowView.findViewById(R.id.price);
			    itemname.setText(state.mylist.get(position).name);
			    price.setText(String.format("%.2f", state.mylist.get(position).price));
			    final ImageButton btn = (ImageButton) rowView.findViewById(R.id.imageButton1);
			    final int pos = position;
			 btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					state.mylist.remove(pos);
					data.remove(pos);
					adapter.notifyDataSetChanged();
				}
				 
			 });
			return rowView;
			   
		   }

		  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
