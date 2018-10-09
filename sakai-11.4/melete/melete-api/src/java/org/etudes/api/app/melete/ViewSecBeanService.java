package org.etudes.api.app.melete;

import java.util.Date;

public interface ViewSecBeanService
{

	/**
	 * @return section content type string
	 */
	public abstract String getContentType();

	/**
	 * return the section display class
	 * @return
	 */
	public abstract String getDisplayClass();
	/**
	 * @return displaySequence string
	 */
	public abstract String getDisplaySequence();

	/**
	 * @return get section id
	 */
	public abstract int getSectionId();

	/**
	 * @return get section title
	 */
	public abstract String getTitle();
	
	/**
	 * @return view date
	 */
	public abstract Date getViewDate();

	/**
	 * @return selected flag
	 */
	public abstract boolean isSelected();

	/**
	 * @param contentType
	 *        set section content type
	 */
	public abstract void setContentType(String contentType);

	/**
	 * 
	 * @param displayClass
	 */
	public abstract void setDisplayClass(String displayClass);
	
	/**
	 * @param displaySequence
	 *        set displaySequence
	 */
	public abstract void setDisplaySequence(String displaySequence);

	/**
	 * @param sectionId
	 *        set section id
	 */
	public abstract void setSectionId(int sectionId);

	/**
	 * @param selected
	 *        set selected flag
	 */
	public abstract void setSelected(boolean selected);

	/**
	 * @param title
	 *        set section title
	 */
	public abstract void setTitle(String title);
	
	
	/**
	 * @param viewDate
	 *        view date
	 */
	public abstract void setViewDate(Date viewDate);

}
