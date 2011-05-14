/**
 * 
 */
package com.bryanblack.parser;
import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.net.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.*;

/**
 * @author Bryan Black
 *
 */
public class TvaParser extends Parser {
	
	  
	
	public static final String JUMP_MENU = "jumpMenu"; 
	
	public static final String ROW_TAG = "ROW";
	public static final String RELEASE_TAG = "RELEASEDATE"; 
	public static final String TIME_TAG = "TIMEPERIOD"; 
	public static final String GENERATOR_TAG = "GENERATORS"; 
	
	public static final String RELEASE_NOTATION = "_F";
	public static final String OBSERVATION_NOTATION = "_R"; 
	
	public static final String ROOT_URL = "http://www.tva.gov/river/lakeinfo/index.htm"; 
	public static final String XML_URL = "http://lakeinfo.tva.gov/xml/";
	public static final String HTML_ROOT = "http://www.tva.gov/lakes/";
	
	/**
	 * 
	 */
	public TvaParser(){
		super(); 
	}



	@Override
	public String[] getLakes() throws SAXException, IOException, ParserConfigurationException {
		if(this.mMenu  == null){
			this.mMenu = new Hashtable<String, String>();
		}
		
		mBuilder = mFactory.newDocumentBuilder();
		this.setmRootUrl(new URL(TvaParser.ROOT_URL));
		Document tva = mBuilder.parse(this.mRootUrl.openStream());
		NodeList jumpMenu = tva.getElementById(JUMP_MENU).getChildNodes(); 
		ArrayList<String> menuNames = new ArrayList<String>(); 
	
		for(int i =0; i < jumpMenu.getLength(); i++){
			Node current_i = jumpMenu.item(i); 
			if(current_i.getNodeType() == Node.ELEMENT_NODE && ((Element)current_i).getTagName().equals("option")){
				Element option = (Element)current_i; 
				String value = option.getAttribute("value"); 
				if(value != null && !value.equals("#")){
					if(!mMenu.containsKey(option.getTextContent())){
						mMenu.put(option.getTextContent(), value.replace("\t", "")); 
						menuNames.add(option.getTextContent()); 
					}
				}
			}
		}
	
		return Parser.toStringArray(menuNames); 
	}



	@Override
	public ArrayList<Release> getReleaseForLake(String url) throws SAXException, IOException, ParserConfigurationException {
		String releaseUrlString = this.getXmlUrl(url, true); 
		if(releaseUrlString != ""){
			URL releaseUrl = new URL(releaseUrlString); 
			mBuilder = mFactory.newDocumentBuilder(); 
			Document release = mBuilder.parse(releaseUrl.openStream());
			NodeList rows = release.getElementsByTagName(ROW_TAG);
			ArrayList<Release> releases = new ArrayList<Release>(); 
			for(int i =0; i < rows.getLength(); i++){
				Node current_i = rows.item(i); 
				if(current_i.getNodeType() == Node.ELEMENT_NODE && ((Element)current_i).getTagName().equals(ROW_TAG)){
					NodeList current_j = current_i.getChildNodes(); 
					Release newRelease = new TvaRelease(); 
					for(int j = 0; j < current_j.getLength(); j++){
						Node rowChild = current_j.item(j); 
						if(rowChild.getNodeType() == Node.ELEMENT_NODE){
							Element element = (Element)rowChild; 
							if(element.getTagName().equals(RELEASE_TAG)){
								newRelease.setReleaseDate(element.getTextContent()); 
							}else if(element.getTagName().equals(TIME_TAG)){
								newRelease.setTimePeriod(element.getTextContent()); 
							}else if(element.getTagName().equals(GENERATOR_TAG)){
								newRelease.setGenerators(element.getTextContent()); 
							}
						}
					}
					if(newRelease != null){
						releases.add(newRelease); 
					}
				}
				
			}
			return releases; 
		}
		return null; 
	}
	
	
	
	private String getXmlUrl(String url,boolean release){
		String xmlUrl = ""; 
		if(HTML_ROOT.length() <= url.length()){
			String xml = url.substring(HTML_ROOT.length(),url.length()); 
			xml = xml.replace(".htm", ""); 
			if(release){
				xml = xml.replace(OBSERVATION_NOTATION.toLowerCase(), RELEASE_NOTATION.toLowerCase());
			}	
			xmlUrl = XML_URL + xml.toUpperCase() + ".xml"; 
		}
		return xmlUrl;
	}

	
	
	
	
}
