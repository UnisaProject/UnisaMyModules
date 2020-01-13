/**
 * Copyright (c) 2007-2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.qna.logic.impl;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.qna.logic.QnaBundleLogic;
import org.sakaiproject.util.ResourceLoader;

/**
 *  Shamelessly stolen from Assignment2
 */
public class QnaBundleLogicImpl implements QnaBundleLogic {

	private static ResourceLoader rb = null;
	private static Log log = LogFactory.getLog(QnaBundleLogicImpl.class);
	
	public void init()
	{
		if (log.isDebugEnabled())
			log.debug("init");
		// since the field is static, only instantiate of not previously populated
		// this bean should only be created once but this will ensure an overwritten
		// assignment doesn't occur.
		if (rb == null)
			rb = new ResourceLoader(QNA_BUNDLE);
	}
	
	/**
	 * @see QnaBundleLogic#getString(String)
	 */
	public String getString(String key)
	{
		return rb.getString(key);
	}
	
	/**
	 * @see QnaBundleLogic#getFormattedMessage(String, Object[])
	 */
	public String getFormattedMessage(String key, Object[] parameters) {
		return rb.getFormattedMessage(key, parameters);
	}
	
	/**
	 * @see QnaBundleLogic#getLocale()
	 */
	public Locale getLocale() {
		return rb.getLocale();
	}

}
