/**
 * Copyright 2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.yaft.tool.entityprovider;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sakaiproject.entitybroker.entityprovider.capabilities.AutoRegisterEntityProvider;
import org.sakaiproject.entitybroker.entityprovider.capabilities.Statisticable;
import org.sakaiproject.entitybroker.util.AbstractEntityProvider;
import org.sakaiproject.util.ResourceLoader;
import org.sakaiproject.yaft.api.YaftForumService;

public class StatisticableEntityProvider extends AbstractEntityProvider implements AutoRegisterEntityProvider,Statisticable
{
	protected final Logger LOG = Logger.getLogger(getClass());
	
	public final static String ENTITY_PREFIX = "yaft-forum";
	//unisa-change: Changed the event names took out the _ss for the events
	private static final String[] EVENT_KEYS
		= new String[] {
			YaftForumService.YAFT_FORUM_CREATED,
			YaftForumService.YAFT_FORUM_DELETED,
			YaftForumService.YAFT_DISCUSSION_CREATED,
			YaftForumService.YAFT_DISCUSSION_DELETED,
			YaftForumService.YAFT_MESSAGE_CREATED,
			YaftForumService.YAFT_MESSAGE_DELETED
			};

	public String getEntityPrefix()
	{
		return ENTITY_PREFIX;
	}
	
	/**
	 * From Statisticable
	 */
	public String getAssociatedToolId()
	{
		return "sakai.yaft";
	}

	/**
	 * From Statisticable
	 */
	public String[] getEventKeys()
	{
		String[] temp = new String[EVENT_KEYS.length];
		System.arraycopy(EVENT_KEYS, 0, temp, 0, EVENT_KEYS.length);
		return temp;
	}

	/**
	 * From Statisticable
	 */
	public Map<String, String> getEventNames(Locale locale)
	{
		Map<String, String> localeEventNames = new HashMap<String, String>();
		ResourceLoader msgs = new ResourceLoader("YaftEvents");
		msgs.setContextLocale(locale);
		for (int i = 0; i < EVENT_KEYS.length; i++)
		{
			localeEventNames.put(EVENT_KEYS[i], msgs.getString(EVENT_KEYS[i]));
		}
		return localeEventNames;
	}
}
