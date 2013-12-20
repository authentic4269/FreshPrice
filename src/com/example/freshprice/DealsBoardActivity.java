package com.example.freshprice;

import java.util.ArrayList;
import java.util.Iterator;

import model.Deal;
import model.Item;
import model.Store;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class DealsBoardActivity extends Activity {
	ArrayList<Deal> deals;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dealsboard_stores);
		FreshpriceApplication state = ((FreshpriceApplication) getApplicationContext());
		ListView list = (ListView) findViewById(android.R.id.list);
		int i = 0;
		final ArrayList<Store> alist = new ArrayList<Store>();
		Iterator<Store> listiter = state.stores.iterator();
		while (listiter.hasNext())
		{
			alist.add(listiter.next());
		}
	    ArrayAdapter<Store> adapter = new StoreArrayAdapter(this.getApplicationContext(), alist);
		list.setAdapter(adapter);
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deals_board, menu);
		return true;
	}
	
	protected void showDeals(int storeIdx)
	{
		deals = new ArrayList<Deal>();
		setContentView(R.layout.dealsboard);
		FreshpriceApplication state = ((FreshpriceApplication) getApplicationContext());
		ListView list = (ListView) findViewById(android.R.id.list);
		int i = 0;
		Iterator<Deal> listiter = state.deals.get(state.stores.get(storeIdx)).iterator();
		while (listiter.hasNext())
		{
			deals.add(listiter.next());
		}
	    ArrayAdapter<Deal> adapter = new DealArrayAdapter(this.getApplicationContext(), deals);
		list.setAdapter(adapter);
	}
	
	
	
	  public class StoreArrayAdapter extends ArrayAdapter<Store> {
		  private final Context context;
		private FreshpriceApplication state;
		
		   public StoreArrayAdapter(Context context, ArrayList<Store> values)
		   {
			   super(context, R.layout.dealsboard_stores, values);
			  this.state = ((FreshpriceApplication) getApplicationContext());
			  this.context = context;
		   }
		   
		   @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			   LayoutInflater inflater = (LayoutInflater) context
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View rowView = inflater.inflate(R.layout.dealsboard_storerow, parent, false);
			    TextView storename = (TextView) rowView.findViewById(R.id.storename);
			    TextView distance = (TextView) rowView.findViewById(R.id.distance);
			    storename.setText(state.stores.get(position).name);
			    distance.setText(String.format("%.2f", state.stores.get(position).distance) + " mi");
			    final int pos = position;
			 rowView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showDeals(pos);
				}
				 
			 });
			return rowView;
			   
		   }

		  }
	  
	  
	  
	  
	  public class DealArrayAdapter extends ArrayAdapter<Deal> {
		  private final Context context;
		private FreshpriceApplication state;
		
		   public DealArrayAdapter(Context context, ArrayList<Deal> values)
		   {
			   super(context, R.layout.dealsboard, values);
			  this.state = ((FreshpriceApplication) getApplicationContext());
			  this.context = context;
		   }
		   
		   @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			   LayoutInflater inflater = (LayoutInflater) context
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View rowView = inflater.inflate(R.layout.dealsboard_dealrow, parent, false);
			    TextView itemname = (TextView) rowView.findViewById(R.id.itemname);
			    TextView description = (TextView) rowView.findViewById(R.id.description);
			    TextView price = (TextView) rowView.findViewById(R.id.price);
			    final ImageButton btn = (ImageButton) rowView.findViewById(R.id.imageButton1);
			    itemname.setText(deals.get(position).item.name);
			    description.setText(deals.get(position).description);
			    price.setText(String.format("%.2f", deals.get(position).item.price));
			    final int pos = position;
			    btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (deals.get(pos).item.inList)
					{
						btn.setImageResource(android.R.drawable.btn_plus);
						deals.get(pos).item.inList = false;
						state.mylist.remove(deals.get(pos).item);
					}
					else
					{
						btn.setImageResource(android.R.drawable.btn_minus);
						deals.get(pos).item.inList = true;
						state.mylist.add(deals.get(pos).item);
					}
				}
				 
			 });
			return rowView;
			   
		   }

		  }

}
