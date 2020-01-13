package za.ac.unisa.lms.tool;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import za.ac.unisa.lms.logic.SakaiProxy;
/*import lombok.Getter;
import lombok.Setter;*/

public class HelloWorldController implements Controller {

	/**
	 * Hello World Controller
	 * 
	 * @author Mike Jennings (mike_jennings@unc.edu)
	 * 
	 */
/*	@Setter
	@Getter*/
	//private SakaiProxy sakaiProxy = null;
	
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		Map<String, Object> map = new HashMap<String,Object>();
	/*	map.put("currentSiteId", sakaiProxy.getCurrentSiteId());
		map.put("userDisplayName", sakaiProxy.getCurrentUserDisplayName());*/
		
		return new ModelAndView("index", map);
	}

}
