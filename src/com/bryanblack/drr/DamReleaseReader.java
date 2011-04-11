package com.bryanblack.drr;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.ListActivity;
import android.content.Intent;
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
		try {
        	mParser = new TvaParser();
        	super.onCreate(savedInstanceState);
			setListAdapter(new ArrayAdapter<String>(this, R.layout.lake_item,mParser.getLakes()));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace(); 
		}


        
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
}









