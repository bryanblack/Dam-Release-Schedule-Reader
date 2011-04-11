/**
 * 
 */
package com.bryanblack.drr;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.bryanblack.parser.Parser;
import com.bryanblack.parser.TvaParser;
import com.bryanblack.parser.Release;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Bryan
 *
 */
public class ReleaseViewer extends ListActivity {
	
	private TvaParser mParser; 
	private String mReleaseUrl; 

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.release_list); 
			mParser = new TvaParser();
			Bundle extras = getIntent().getExtras(); 
			setTitle(extras.getString(Parser.LAKE)); 
			mReleaseUrl = extras.getString(Parser.RELEASE); 
			populateReleases(); 
		}catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	private void populateReleases() throws SAXException, IOException, ParserConfigurationException{
		ArrayList<Release> releases = mParser.getReleaseForLake(mReleaseUrl);
		ReleaseAdapter adapter = new ReleaseAdapter(this, R.layout.release_item, releases); 
		setListAdapter(adapter); 
	}
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	
	private class ReleaseAdapter extends ArrayAdapter<Release> {

		private ArrayList<Release> mItems; 
		
		public ReleaseAdapter(Context context, int textViewResourceId, ArrayList<Release> objects) {
			super(context, textViewResourceId, objects);
			mItems = objects; 
		}
		
		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View v = convertView;
	        if(v == null){
	        	LayoutInflater vi = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE); 
	        	v = vi.inflate(R.layout.release_item, null);
	        }
	        
	        Release release = mItems.get(position); 
	        if(release != null){
	        	TextView date = (TextView)v.findViewById(R.id.date); 
	        	TextView time = (TextView)v.findViewById(R.id.time); 
	        	TextView generators = (TextView)v.findViewById(R.id.generators); 
	        	if(date != null){
	        		date.setText(release.getReleaseDate());
	        	}
	        	
	        	if(time != null){
	        		time.setText(release.getTimePeriod());
	        	}
	        	
	        	if(generators != null){
	        		generators.setText(release.getGenerators().toString()); 
	        	}
	        	
	        }
	        return v;
		}
	}
	

}
