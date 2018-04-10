package za.ac.unisa.lms.tool.eresources.webapp.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder; 
import org.springframework.web.servlet.ModelAndView;

import za.ac.unisa.lms.tool.eresources.model.ContentType;
import static org.junit.Assert.*; 

public class ContentTypeFormControllerTest extends BaseControllerTestCase {
	@Autowired    
	private ContentTypeFormController form;
	private ContentType contentType;    
	private MockHttpServletRequest request;     
	
	@Test   
	public void testEdit() throws Exception {
		log.debug("testing edit...");
		request = newGet("/contentTypeForm");
		request.addParameter("id", "1L"); 
		
		//contentType = form.showForm(request); 
		ModelAndView modelAndView = form.showForm(request);
		contentType = (ContentType) modelAndView.getModel().get("contentType");
		assertNotNull(modelAndView); 
		}
	
	@Test
	public void testSave() throws Exception {
		request = newGet("/contentTypeForm");
		request.addParameter("id", "1L"); 
		
		ModelAndView modelAndView = form.showForm(request);
		contentType = (ContentType) modelAndView.getModel().get("contentType");
		assertNotNull(contentType);
		
		request = newPost("/contentTypeform");
		modelAndView = form.showForm(request);
		contentType = (ContentType) modelAndView.getModel().get("contentType"); 
		
		// update required fields
		contentType.setLibTxtID(2L);
		contentType.setFullTxtDescr("This is Mock Content");
		
		BindingResult errors = new DataBinder(contentType).getBindingResult();
		form.onSubmit(contentType, errors, request, new MockHttpServletResponse());
		assertFalse(errors.hasErrors());
		assertNotNull(request.getSession().getAttribute("successMessages"));
		}     
	
	@Test    
	public void testRemove() throws Exception {
		request = newPost("/contentTypeform");
		request.addParameter("delete", "");
		contentType = new ContentType();
		contentType.setLibTxtID(2L);
		BindingResult errors = new DataBinder(contentType).getBindingResult();
		form.onSubmit(contentType, errors, request, new MockHttpServletResponse());
		assertNotNull(request.getSession().getAttribute("successMessages"));    
		}
	
}