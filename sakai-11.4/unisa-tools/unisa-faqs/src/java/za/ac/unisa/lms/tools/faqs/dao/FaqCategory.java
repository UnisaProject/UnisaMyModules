package za.ac.unisa.lms.tools.faqs.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FaqCategory {

	private String siteId;
	private Integer categoryId;
	private String description;
	private Timestamp modifiedOn;
	private boolean remove;
	private List contents;
	private boolean expanded;
	
	public FaqCategory() {
		this.modifiedOn = new Timestamp(new Date().getTime());
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public List getContents() {
		return contents;
	}
	public void setContents(List contents) {
		this.contents = contents;
	}
	public Object getContentIndexed(int index) {
		return contents.get(index);
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public Integer getContentsSize() {
		
		if (contents == null) return new Integer(0);
		
		return new Integer(contents.size());
	}
	
	public void setContentsCategoryId(Integer categoryId) {
		Iterator i = contents.iterator();
		while (i.hasNext()) {
			FaqContent content = (FaqContent) i.next();
			content.setCategoryId(categoryId);
		}
	}


}
