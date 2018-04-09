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
package org.sakaiproject.yaft.api;

public class Attachment
{
	private String url = "";
	private String mimeType = "";
	private byte[] data;
	private String name = "";
	private String resourceId = "";
	
	public Attachment(String name,String contentType, byte[] bs)
	{
		this.name = name;
		mimeType = contentType;
		
		if(name.endsWith(".doc")) mimeType = "application/msword";
		data = bs;
	}
	
	public Attachment() {} 
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getUrl()
	{
		return url;
	}
	public void setMimeType(String mimeType)
	{
		this.mimeType = mimeType;
	}
	public String getMimeType()
	{
		return mimeType;
	}
	public void setData(byte[] data)
	{
		this.data = data;
	}
	public byte[] getData()
	{
		return data;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public void setResourceId(String resourceId)
	{
		this.resourceId = resourceId;
	}
	public String getResourceId()
	{
		return resourceId;
	}
}
