package com.bryanblack.drr;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bryanblack.parser.*;

public class DamReleaseReader extends ListActivity {
	
	private TvaParser mParser; 

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	mParser = new TvaParser();
    	super.onCreate(savedInstanceState);
    	new RetrieveLakesTask().execute(); 


        
    }

	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String name = ((TextView)v).getText().toString();
		Intent i = new Intent(this,ReleaseViewer.class); 
		i.putExtra(Parser.LAKE, name); 
		i.putExtra(Parser.RELEASE, mParser.getmMenu().get(name));
		try{
			startActivity(i);
		}catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	
	private class RetrieveLakesTask extends AsyncTask<Void,Void,String[]>{
		private ProgressDialog dialog = new ProgressDialog(DamReleaseReader.this); 

		protected void onPreExecute(){
			dialog.setMessage(getString(R.string.loading_message)); 
			dialog.show(); 
		}
		
		protected void onPostExecute(String[] lakes){
			if(lakes != null){
				DamReleaseReader.this.setListAdapter(new ArrayAdapter<String>(DamReleaseReader.this, R.layout.lake_item,lakes));	
			}else{
				DamReleaseReader.this.setListAdapter(new ArrayAdapter<String>(DamReleaseReader.this, R.layout.lake_item)); 
			}
			dialog.dismiss(); 
		}


		@Override
		protected String[] doInBackground(Void... arg0) {
			String[] lakes = null; 
			try {
				lakes = mParser.getLakes();
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
			return lakes;
		}
		
		
	}
	
	
	
}









