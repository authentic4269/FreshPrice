package com.example.freshprice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.example.freshprice.MyListActivity.MylistArrayAdapter;

import model.Item;
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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends Activity {

	protected ArrayList<Item> ar;
	FreshpriceApplication state;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop);
		state = ((FreshpriceApplication) getApplicationContext());
		ListView list = (ListView) findViewById(android.R.id.list);		
		Iterator<Item> listiter = state.mylist.iterator();
		ar = new ArrayList<Item>();
		while (listiter.hasNext())
		{
			Item cur = listiter.next();
			cur.checked = false;
			ar.add(cur);
		}
		ShopAdapter adapter = new ShopAdapter(this.getApplicationContext(), ar);
		list.setAdapter(adapter);
		
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
		getMenuInflater().inflate(R.menu.shop, menu);
		return true;
	}
	
	public class ShopAdapter extends ArrayAdapter<Item> {
		  private final Context context;
		private FreshpriceApplication state;
		private int count = 0;
		private int len = 0;
		   public ShopAdapter(Context context, ArrayList<Item> values)
		   {
			   super(context, R.layout.shop, values);
			  this.state = ((FreshpriceApplication) getApplicationContext());
			  this.context = context;
			  len = values.size();
		   }
		   
		   @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			   LayoutInflater inflater = (LayoutInflater) context
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View rowView = inflater.inflate(R.layout.shop_row, parent, false);
			    TextView itemname = (TextView) rowView.findViewById(R.id.itemname);
			    itemname.setText(state.mylist.get(position).name);
			    final CheckBox btn = (CheckBox) rowView.findViewById(R.id.check);
			    final int pos = position;
			 btn.setOnClickListener(new OnClickListener() {
				 
				@Override
				public void onClick(View v) {
					if (ar.get(pos).checked)
					{
						count--;
					}
					else
					{
						count++;
					}
					if (count == len)
					{
						Context context = getApplicationContext();
						CharSequence text = "User would take a picture of reciept and upload it";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					}
					ar.get(pos).checked = !ar.get(pos).checked;
					Iterator<Item> it = ar.iterator(); 
					
				}
				 
			 });
			return rowView;
			   
		   }

		  }

}
