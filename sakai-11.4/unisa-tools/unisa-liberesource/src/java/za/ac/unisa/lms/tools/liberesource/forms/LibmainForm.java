package za.ac.unisa.lms.tools.liberesource.forms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.liberesource.dao.ResourceDAO;//fl


public class LibmainForm extends ValidatorForm {

	private final String database = "libresource"; 
	private ArrayList dataList;
	private ArrayList vendordataList;
	private String selectedSubjectId;
	private String selectedVendorId;
	private ArrayList selectedSubjectList;
	private ArrayList selectedVendortList;
	private String alpha;
	private String heading;
	private String prevHeading = "";
	private String placeId;
	private ArrayList selectSpecificdbList;
	private ArrayList<Tab> tabsList = new ArrayList<Tab>();
	private ArrayList<Tab> moreTabsList = new ArrayList<Tab>();
	private ArrayList featuredDatabase = new ArrayList();
	private ArrayList<Alphabet> alphabets = new ArrayList<Alphabet>();
	
	private int selectedTab; //FL
	
	public ArrayList getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList dataList) {
		this.dataList = dataList;
	}


	public String getDatabase() { 
		return database;
		
	}  
	
	public void setFeaturedDatabase(ArrayList featuredDatabase)
	{
		this.featuredDatabase = featuredDatabase;
	}
	
	public ArrayList getFeaturedDatabase() throws Exception
	{
		return featuredDatabase;
	}
	
	public ArrayList<Tab> getTabs() 
	{
		return tabsList;
	}
	
	public ArrayList<Tab> getMoreTabsList()
	{
		return moreTabsList;
	}
	
	public ArrayList<Alphabet> getAlphabets() throws Exception
	{
		return alphabets;
	}

	public ArrayList getSelectedSubjectList() {
		return selectedSubjectList;
	}
	
	public void setAlphabets(ArrayList<Alphabet> alphabets)
	{
		this.alphabets = alphabets;
	}

	public void setSelectedSubjectList(ArrayList selectedSubjectList) {
		this.selectedSubjectList = selectedSubjectList;
	}

	public ArrayList getVendordataList() {
		return vendordataList;
	}

	public void setVendordataList(ArrayList vendordataList) {
		this.vendordataList = vendordataList;
	}

	public String getSelectedSubjectId() {
		return selectedSubjectId;
	}

	public void setSelectedSubjectId(String selectedSubjectId) {
		this.selectedSubjectId = selectedSubjectId;
	}

	public String getAlpha() {
		return alpha;
	}

	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	public String getSelectedVendorId() {
		return selectedVendorId;
	}

	public void setSelectedVendorId(String selectedVendorId) {
		this.selectedVendorId = selectedVendorId;
	}

	public ArrayList getSelectedVendortList() {
		return selectedVendortList;
	}

	public void setSelectedVendortList(ArrayList selectedVendortList) {
		this.selectedVendortList = selectedVendortList;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public ArrayList getSelectSpecificdbList() {
		return selectSpecificdbList;
	}

	public void setSelectSpecificdbList(ArrayList selectSpecificdbList) {
		this.selectSpecificdbList = selectSpecificdbList;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}
	
	public String getPrevHeading() {
		return prevHeading;
	}

	public void setPrevHeading(String prevHeading) {
		this.prevHeading = prevHeading;
	}
	
	public void setTabs(ArrayList<Tab> tabsList)
	{
		this.tabsList = tabsList;
	}
	
	public void setMoreTabsList(ArrayList<Tab> moreTabsList)
	{
		this.moreTabsList = moreTabsList;
	}

	public void setSelectedTab(int tabID)
	{
		this.selectedTab = tabID;
	}


}

