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

import uk.org.ponder.mapping.DARReshaper;
import uk.org.ponder.mapping.DataAlterationRequest;

/**
 * Used to reshape input value to integer 
 *
 */
public class IntegerReshaper implements DARReshaper {
	
	public static final String REFERENCE = "integerReshaper";
	
	public DataAlterationRequest reshapeDAR(DataAlterationRequest toshape) {
		Object data = toshape.data;
		Integer toReturn;
		
		if (data instanceof Integer) {
			toReturn = (Integer)data;
		} else {
			try {
				toReturn = Integer.parseInt(data.toString());	
			} catch (NumberFormatException nfe) {
				toReturn = 0;
			}
		}
		
	    DataAlterationRequest togo = new DataAlterationRequest(toshape.path, toReturn
	            , toshape.type);
	        return togo;
	}

}
