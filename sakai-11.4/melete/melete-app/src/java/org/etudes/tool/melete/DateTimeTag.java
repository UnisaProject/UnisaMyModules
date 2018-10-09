/*******************************************************************************
 *
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.1forSakai/melete-app/src/java/org/etudes/tool/melete/DateTimeTag.java $
 *
 * **********************************************************************************
 *
 * Copyright (c) 2012 Etudes, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 ******************************************************************************/

package org.etudes.tool.melete;

import javax.faces.webapp.ConverterTag;
import javax.faces.convert.Converter;
//import javax.servlet.jsp.JspException;
import javax.servlet.jsp.*;

public class DateTimeTag extends ConverterTag
{

	private boolean multiLine = false;

	public DateTimeTag()
	{
		super();
		setConverterId(DateTimeConverter.CONVERTER_ID);
	}

	public boolean getMultiLine()
	{
		return multiLine;
	}

	public void release()
	{
		super.release();
		multiLine = false;
	}

	public void setMultiLine(boolean multiLine)
	{
		this.multiLine = multiLine;
	}

	protected Converter createConverter() throws JspException
	{
		DateTimeConverter converter = (DateTimeConverter) super.createConverter();
		converter.setMultiLine(multiLine);
		return converter;
	}

}
