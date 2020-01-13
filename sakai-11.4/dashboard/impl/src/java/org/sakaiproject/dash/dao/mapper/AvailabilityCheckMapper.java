/********************************************************************************** 
 * $URL$ 
 * $Id$ 
 *********************************************************************************** 
 * 
 * Copyright (c) 2011 The Sakai Foundation 
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.osedu.org/licenses/ECL-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 * 
 **********************************************************************************/ 

package org.sakaiproject.dash.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.sakaiproject.dash.model.AvailabilityCheck;
import org.springframework.jdbc.core.RowMapper;

/**
 * 
 *
 */
public class AvailabilityCheckMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AvailabilityCheck availabilityCheck = new AvailabilityCheck();
		
		availabilityCheck.setId(rs.getLong("id"));
		availabilityCheck.setEntityReference(rs.getString("entity_ref"));
		availabilityCheck.setEntityTypeId(rs.getString("entity_type_id"));
		availabilityCheck.setScheduledTime(rs.getDate("scheduled_time"));
		
		return availabilityCheck;
	}

}
