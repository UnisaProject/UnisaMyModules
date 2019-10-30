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
package org.sakaiproject.qna.tool.otp;

import java.util.HashMap;
import java.util.Map;

import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.model.QnaOptions;

import uk.org.ponder.beanutil.BeanLocator;
import uk.org.ponder.messageutil.TargettedMessage;
import uk.org.ponder.messageutil.TargettedMessageList;

public class OptionsLocator implements BeanLocator {

	private OptionsLogic optionsLogic;
	private TargettedMessageList messages;
	
	
	/**
	 * @param optionsLogic the optionsLogic to set
	 */
	public void setOptionsLogic(OptionsLogic optionsLogic) {
		this.optionsLogic = optionsLogic;
	}
	
	
	public void setMessages(TargettedMessageList messages) {
		this.messages = messages;
	}
	
	private Map<String, QnaOptions>  delivered = new HashMap<String,QnaOptions>();
	
	/**
	 * Locate bean
	 * 
	 * @return {@link QnaOptions}
	 */
	public Object locateBean(String name) {
		QnaOptions togo = delivered.get(name);
		if (togo == null) {
			togo = optionsLogic.getOptionsById(name);
			delivered.put(name, togo);
		}
		return togo;
	}
	
	/**
	 * Saves all
	 * @return return key
	 */
	public String saveAll() {
		for (QnaOptions options : delivered.values()) {
			optionsLogic.saveOptions(options, options.getLocation());
	        messages.addMessage(new TargettedMessage("qna.options.save-success",null, 
	                TargettedMessage.SEVERITY_INFO));
			
			// Only persist if it has changed
			if (options.getCommaSeparated() != null) {
				boolean error = optionsLogic.setCustomMailList(options.getLocation(), options.getCommaSeparated());
				if (error) {
					messages.addMessage(new TargettedMessage("qna.options.custom-mail-error",null,TargettedMessage.SEVERITY_ERROR));
				}
			}
		}
		return "saved";
	}

}
