/**
 * Copyright 2005 Sakai Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
//Unisa changes: This file was added from the SakaiRSFComponents for Sakai 10: SakaiRSFComponents-10.0/evolvers/src/java/uk/ac/cam/caret/sakai/rsf/util
//package uk.ac.cam.caret.sakai.rsf.util;   		//Unisa changes: changed package path to the below
package org.sakaiproject.evaluation.tool.utils;		//Unisa changes: original file was moved to 'evaluation' folder

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The new date widget always sends back a ISO8601 formatted string. If the user
 * doesn't have JavaScript they can't enter a date so we don't need to worry about
 * falling back to doing locale based parsing of dates.
 */
public class ISO8601FieldDateTransit {

	public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getISO8601() {
		return (date == null)?null:new SimpleDateFormat(ISO_FORMAT).format(date);
	}

	public void setISO8601(String source) {
		try {
			// This shouldn't ever fail as we should be getting it from the widget.
			date = new SimpleDateFormat(ISO_FORMAT).parse(source);
		} catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
	}
}
