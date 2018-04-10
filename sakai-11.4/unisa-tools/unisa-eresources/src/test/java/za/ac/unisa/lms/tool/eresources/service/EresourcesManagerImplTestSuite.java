/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.service;

import org.appfuse.service.impl.BaseManagerMockTestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;




/**
 * @author TMasibm
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	ContentTypeManagerImplTest.class,
	EresourceManagerImplTest.class,
	EresourcePlacementManagerImplTest.class,
	HighlightNotesManagerImplTest.class,
	NewsletterManagerImplTest.class,
	PlacementManagerImplTest.class,
	SubjectManagerImplTest.class,
	VendorManagerImplTest.class
})
 	
public class EresourcesManagerImplTestSuite extends BaseManagerMockTestCase{
	
}

