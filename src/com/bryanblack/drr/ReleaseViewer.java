/**
 * 
 */
package com.bryanblack.drr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.bryanblack.parser.Parser;
import com.bryanblack.parser.TvaParser;
import com.bryanblack.parser.Release;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Bryan
 *
 */
public class ReleaseViewer extends ListActivity {
	
	private TvaParser mParser; 
	private Hashtable<String, ArrayList<Release>> mReleases; 
	private String mReleaseUrl; 
	private MergeAdapter mAdapter; 

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
			new RetrieveReleasesTask().execute(mReleaseUrl); 
		}catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	private void populateReleases(ArrayList<Release> releases) throws SAXException, IOException, ParserConfigurationException{
		mAdapter = new MergeAdapter();
		if(releases != null){
			mReleases = mParser.groupByDate(releases);  
			ArrayList<String> dates = new ArrayList<String>(mReleases.keySet()); 
			Collections.sort(dates); 
			for(int i = 0; i < dates.size(); i++){
				String key = dates.get(i); 
				mAdapter.addView(this.createDateLabel(key));
				mAdapter.addAdapter(new ReleaseItemAdapter(this, R.layout.release_item, this.mReleases.get(key))); 
			}
		}
		setListAdapter(mAdapter); 
	}
	
	private TextView createDateLabel(String date){
		TextView dateLabel = new TextView(this); 
		dateLabel.setBackgroundResource(R.color.date_label_bg); 
		dateLabel.setTextSize(28);
		dateLabel.setPadding(5, 1, 1, 5); 
		dateLabel.setText(date); 
		return dateLabel; 
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
		super.onSaveInstanceState(outState);
	}

	private class RetrieveReleasesTask extends AsyncTask<String, Void, ArrayList<Release>>{
		private ProgressDialog dialog = new ProgressDialog(ReleaseViewer.this); 
		
		protected void onPreExecute(){
			dialog.setMessage(getString(R.string.release_loading_message)); 
			dialog.show(); 
		}
		
		protected void onPostExecute(ArrayList<Release> releases){
			try {
				populateReleases(releases);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			dialog.dismiss(); 
		}
		
		@Override
		protected ArrayList<Release> doInBackground(String... url) {
			ArrayList<Release> releases = null; 
			try {
				releases = mParser.getReleaseForLake(url[0]);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return releases; 
		}
		
	}
	
	private class ReleaseItemAdapter extends ArrayAdapter<Release> {

		private ArrayList<Release> mItems; 
		
		public ReleaseItemAdapter(Context context, int textViewResourceId, ArrayList<Release> objects) {
			super(context,textViewResourceId,objects); 
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
	        	TextView releaseTime = (TextView)v.findViewById(R.id.time); 
	        	TextView generatorCount = (TextView)v.findViewById(R.id.generators); 
	        	
	        	if(releaseTime != null){
	        		releaseTime.setText(release.getTimePeriod());
	        	}
	        	
	        	if(generatorCount != null){
	        		generatorCount.setText(release.getGenerators()); 
	        	}
	        	
	        }
	        return v;
		}
	}
}
