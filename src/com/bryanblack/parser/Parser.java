/**
 * 
 */
package com.bryanblack.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @author Bryan Black 
 *
 */
public abstract class Parser {
	
	protected URL mRootUrl; 
	protected DocumentBuilder mBuilder;  
	protected DocumentBuilderFactory mFactory; 
	protected Hashtable<String, String> mMenu;
	
	public static final String RELEASE = "release"; 
	public static final String LAKE = "lake"; 
	public static final String RELEASE_DATE = "releaseDate"; 
	
	public Parser(){
		mFactory = DocumentBuilderFactory.newInstance(); 
	}
	
	
	
	public abstract String[] getLakes() throws SAXException, IOException, ParserConfigurationException; 
	public abstract ArrayList<Release> getReleaseForLake(String url) throws MalformedURLException, SAXException, IOException, ParserConfigurationException;
	
	public Hashtable<String, ArrayList<Release>> groupByDate(ArrayList<Release> releases){
		
		if(releases.size() > 0) {
			Hashtable<String, ArrayList<Release>> group = new Hashtable<String, ArrayList<Release>>(); 
			
			for(Release release : releases){
				if(group.containsKey(release.getReleaseDate())){
					group.get(release.getReleaseDate()).add(release); 
				}else {
					ArrayList<Release> newGroup = new ArrayList<Release>(); 
					newGroup.add(release); 
					group.put(release.getReleaseDate(), newGroup); 
				}
			}
			return group; 
		}
		 return null; 
	}
	
	protected static String[] toStringArray(ArrayList<String> list){
		String[] stringArray = new String[list.size()]; 
		for(int i = 0; i < list.size() ; i++){
			stringArray[i] = list.get(i); 
		}
		return stringArray; 
	}
	/**
	 * @return the mRoot
	 */
	public URL getmRootUrl() {
		return mRootUrl;
	}
	/**
	 * @param mRoot the mRoot to set
	 */
	public void setmRootUrl(URL mRoot) {
		this.mRootUrl = mRoot;
	}
	/**
	 * @return the mBuilder
	 */
	public DocumentBuilder getmBuilder() {
		return mBuilder;
	}
	/**
	 * @param mBuilder the mBuilder to set
	 */
	public void setmBuilder(DocumentBuilder mBuilder) {
		this.mBuilder = mBuilder;
	}
	/**
	 * @return the mFactory
	 */
	public DocumentBuilderFactory getmFactory() {
		return mFactory;
	}
	/**
	 * @param mFactory the mFactory to set
	 */
	public void setmFactory(DocumentBuilderFactory mFactory) {
		this.mFactory = mFactory;
	}

	/**
	 * @return the mMenu
	 */
	public Hashtable<String, String> getmMenu() {
		return mMenu;
	}

	

}
