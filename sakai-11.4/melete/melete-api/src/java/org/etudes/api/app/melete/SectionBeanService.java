package org.etudes.api.app.melete;

public interface SectionBeanService
{

	/**
	 * Get the display sequence of a section based on user preference and sub section level.
	 * 
	 * @return
	 */
	public abstract String getDisplaySequence();

	/**
	 * Get the section.
	 * 
	 * @return
	 */
	public abstract SectionObjService getSection();

	/**
	 * Get the title truncated to 30 characters. Note:Not in use anymore
	 * 
	 * @return
	 */
	public abstract String getTruncTitle();

	/**
	 * Checks if section is selected by the user.
	 * 
	 * @return
	 */
	public abstract boolean isSelected();

	/**
	 * Set the display sequence.
	 * 
	 * @param displaySequence
	 *        the sub section level sequence
	 */
	public abstract void setDisplaySequence(String displaySequence);

	/**
	 * Set the section.
	 * 
	 * @param section
	 *        The section
	 */
	public abstract void setSection(SectionObjService section);

	/**
	 * Set the user selection
	 * 
	 * @param selected
	 *        selected
	 */
	public abstract void setSelected(boolean selected);

	/**
	 * Set the title truncated to 30 characters.
	 * 
	 * @return
	 */
	public abstract void setTruncTitle(String truncTitle);

}
