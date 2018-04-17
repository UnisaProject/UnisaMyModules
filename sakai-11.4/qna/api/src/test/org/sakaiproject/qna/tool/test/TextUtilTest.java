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
package org.sakaiproject.qna.tool.test;

import junit.framework.TestCase;

import org.sakaiproject.qna.utils.TextUtil;


public class TextUtilTest extends TestCase {
	
	/**
	 * Test various Strings which should be regarded as empty without tags
	 */
	public void testIsEmptyWithoutTags() {
		assertTrue(TextUtil.isEmptyWithoutTags(null));
		assertTrue(TextUtil.isEmptyWithoutTags(""));
		assertTrue(TextUtil.isEmptyWithoutTags("   "));
		assertTrue(TextUtil.isEmptyWithoutTags("\n"));
		assertTrue(TextUtil.isEmptyWithoutTags("<p></p>"));
		assertTrue(TextUtil.isEmptyWithoutTags("&nbsp;&nbsp;"));
		assertTrue(TextUtil.isEmptyWithoutTags("  <p>&nbsp;</p>   "));
	}
	
	/**
	 * Test various Strings which should NOT be regarded as empty without tags
	 */
	public void testIsEmptyWithoutTagsFalse() {
		assertFalse(TextUtil.isEmptyWithoutTags("just text"));
		assertFalse(TextUtil.isEmptyWithoutTags("<p>Text here</p>"));
		assertFalse(TextUtil.isEmptyWithoutTags(" 1  "));
		assertFalse(TextUtil.isEmptyWithoutTags("&nbsp;1&nbsp;"));
	}
	
	/**
	 * Test tag stripping method
	 */
	public void testStripTags() {
		assertEquals("", TextUtil.stripTags("<html><body></body></html>"));
		assertEquals("Text", TextUtil.stripTags("<html><body>Text</body></html>"));
		assertEquals("<", TextUtil.stripTags("&lt;"));
		assertEquals(">", TextUtil.stripTags("&gt;"));
		assertEquals("<Hi!>", TextUtil.stripTags("&lt;Hi!&gt;"));
		assertEquals("&", TextUtil.stripTags("&amp;"));
		assertEquals(" ", TextUtil.stripTags("&nbsp;"));
		assertEquals(" <Q&A> ", TextUtil.stripTags("&nbsp;&lt;Q&amp;A&gt;&nbsp;"));
		assertEquals("", TextUtil.stripTags("<br/>"));
	}
	
}
