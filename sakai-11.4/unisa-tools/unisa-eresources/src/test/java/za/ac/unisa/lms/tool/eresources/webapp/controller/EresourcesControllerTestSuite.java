/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.webapp.controller;

import org.appfuse.dao.BaseDaoTestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;




/**
 * @author TMasibm
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
   //BaseControllerTestCase.class,
   ContentTypeControllerTest.class,
   EresourcesControllerTest.class,
   HighlightNotesControllerTest.class,
   NewsletterControllerTest.class,
   PasswordHintControllerTest.class,
  
   SignupControllerTest.class,
   SubjectControllerTest.class,
   UserControllerTest.class,
   UserFormControllerTest.class,
   VendorControllerTest.class
})
 	
public class EresourcesControllerTestSuite extends BaseDaoTestCase{
	
}

