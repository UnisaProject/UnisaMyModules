package org.sakaiproject.yaft.impl;

import org.sakaiproject.util.ResourceLoader;
import org.sakaiproject.util.UserNotificationPreferencesRegistrationImpl;

public class YaftUserNotificationPreferencesRegistrationImpl extends UserNotificationPreferencesRegistrationImpl  {
	
	public ResourceLoader getResourceLoader(String location) {
		return new ResourceLoader(location);
	}
}
