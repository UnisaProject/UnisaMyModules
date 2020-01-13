/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.dao;

import org.appfuse.dao.BaseDaoTestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;




/**
 * @author TMasibm
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
   ContentTypeDaoTest.class,
   EresourceDaoTest.class,
   HighlightNotesDaoTest.class,
   NewsletterDaoTest.class,
   PlacementDaoTest.class,
   SubjectDaoTest.class,
   VendorDaoTest.class
})
 	
public class EresourcesDaoTestSuite extends BaseDaoTestCase{
	
}

