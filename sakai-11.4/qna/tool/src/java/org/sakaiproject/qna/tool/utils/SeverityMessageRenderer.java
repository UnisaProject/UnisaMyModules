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
package org.sakaiproject.qna.tool.utils;

import uk.org.ponder.messageutil.MessageLocator;
import uk.org.ponder.messageutil.TargettedMessage;
import uk.org.ponder.messageutil.TargettedMessageList;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.stringutil.StringList;

public class SeverityMessageRenderer extends uk.org.ponder.rsf.renderer.message.MessageRenderer
{
	private String infoStyleClass;
	public void setInfoStyleClass(String styleClass) {
		this.infoStyleClass = styleClass;
	}
	private String errorStyleClass;
	public void setErrorStyleClass(String styleClass) {
		this.errorStyleClass = styleClass;
	}
	
  private MessageLocator messagelocator;
  public void setMessageLocator(MessageLocator messagelocator) {
    this.messagelocator = messagelocator;
  }
  
  /**
   * @see MessageRenderer#renderMessage(String)
   */
  public UIMessage renderMessage(String key) {
    UIMessage togo = UIMessage.make(key);
    togo.setValue(messagelocator.getMessage(togo.messagekeys,
        togo.arguments));
    return togo;
  }
  
  /**
   * @see MessageRenderer#renderMessageList(TargettedMessageList)
   */
  public UIBranchContainer renderMessageList(TargettedMessageList messagelist) {
    UIBranchContainer togo = new UIBranchContainer();
    StringList renderered = messagelist == null? new StringList() : 
      messagelist.render(messagelocator);
    for (int i = 0; i < renderered.size(); ++ i) {
      UIOutput output = UIOutput.make(togo, "message:", renderered.stringAt(i));
      if (messagelist.messageAt(i) != null){
    	  TargettedMessage message = messagelist.messageAt(i);
    	  if (message.severity == TargettedMessage.SEVERITY_INFO){
    		  output.decorate(new UIStyleDecorator(infoStyleClass));
    	  } else {
    		  output.decorate(new UIStyleDecorator(errorStyleClass));
    	  }
      }
    }
    return togo;
  }
}