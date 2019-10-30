package org.sakaiproject.qna.tool.test;

import org.sakaiproject.qna.utils.QNAUtils;

import junit.framework.TestCase;

public class QNAUtilsTest extends TestCase {
	
	public static void testInValidEmail() {
		assertTrue(QNAUtils.isValidEmail("deck@local"));
		assertTrue(QNAUtils.isValidEmail("deck@uct.ac.za"));
		assertTrue(QNAUtils.isValidEmail("012349@uct.ac.za"));
		assertFalse(QNAUtils.isValidEmail(""));
		assertFalse(QNAUtils.isValidEmail(" "));
		assertFalse(QNAUtils.isValidEmail("deck"));
	}

}
