package za.ac.unisa.lms.tools.findtool.actions;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.site.api.SiteService;

public class DefaultAction extends DispatchAction {
	
	private SiteService siteService;

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods
	Logger log = LoggerFactory.getLogger(DefaultAction.class);

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		siteService = (SiteService) ComponentManager.get(SiteService.class);
        Site toolShareSite = null;
        SitePage toolSharePage = null;
        try {
        	toolShareSite = siteService.getSite("ToolShare");
        	log.debug("found Site ToolShare...");
        } catch(IdUnusedException e) {
        	log.debug("can't get site ToolShare");
        	log.debug(e.getMessage());
    		return mapping.findForward("default");
        } catch(Exception e) {
        	log.debug("can't get site ToolShare");
        	log.debug(e.getMessage());
    		return mapping.findForward("default");
		}
        try {
        	List<?> toolSharePages = toolShareSite.getPages();
        	Iterator<?> piter = toolSharePages.iterator();
        	for(;piter.hasNext();) {
        		SitePage sp = (SitePage) piter.next();
        		log.debug("got page "+sp.getId()+": "+sp.getTitle());
        		toolSharePage = sp;
        	}
        } catch(Exception e) {
        	log.debug("can't get page Shared Tools");
        	log.debug(e.getMessage());
    		return mapping.findForward("default");
		}
		List<?> tools = null;
		try {
			tools = toolSharePage.getTools();
			log.debug("got tools on share page");
		} catch(NullPointerException e) {
        	log.debug("can't get tools on page Shared Tools");
        	log.debug(e.getMessage());
			return mapping.findForward("default");
		}

        Iterator<?> i = tools.iterator(); 
        for (; i.hasNext();)
        {
            ToolConfiguration tr = (ToolConfiguration) i.next();
            log.debug("request.getParameter(sharedTool)"+ request.getParameter("sharedTool"));
            log.debug("request.getParameter(context)"+request.getParameter("context"));
            log.debug("tr.getToolId"+tr.getToolId());
            if (tr.getTool()!=null) {
              log.debug("tr.getTool().getId()"+tr.getTool().getId());
              log.debug("Looking for "+request.getParameter("sharedTool")+" in context "+request.getParameter("context")+", found "+tr.getTool().getId());
            }
            
            
            if(request.getParameter("sharedTool") != null && request.getParameter("sharedTool").equalsIgnoreCase(tr.getToolId())) {
            	if(request.getParameter("sharedTool").equalsIgnoreCase("unisa.proxy")) {
            		log.debug("got the tool you're looking for, now check the context");
            		if(request.getParameter("context").equalsIgnoreCase("cmsys")) {
            			ActionForward af = new ActionForward("cmsys",true);
            			log.debug("returning cmsys forward");
            			//http://localhost:8080/unisa-findtool/default.do?shareTool=unisa.proxy&context=cmsys&contentPath=17190
            			log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
            			af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/Default.asp?Cmd=ViewContent&ContentID="+request.getParameter("contentPath"));
            			return af;
            		} else {
            			ActionForward af = new ActionForward("external",true);
            			//http://localhost:8080/unisa-findtool/default.do?shareTool=unisa.proxy&context=external&contentPath=Default.asp%3FCmd=ViewContent%26ContentID=17190
            			log.debug("returning external forward");
            			log.debug("ServerConfigurationService.getServerUrl()"+ServerConfigurationService.getServerUrl());
            			af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/"+request.getParameter("contentPath"));
            			return af;
            		}
            	} else if(request.getParameter("sharedTool").equalsIgnoreCase("unisa.studentregistration") && "doc".equalsIgnoreCase(request.getParameter("context"))) {
            		log.debug("got the tool you're looking for, now check the context");
            		// go to document upload part of the tool
            		ActionForward af = new ActionForward("cmsys",true);
            		log.debug("returning document upload forward");
            		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
            		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?context=doc");
            		return af;
            
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.acadhistory") && !"".equalsIgnoreCase(request.getParameter("SYSTEM"))){
            	log.debug("got the tool you're looking for, now check the SYSTEM");
        		// go to document upload part of the tool
        		ActionForward af = new ActionForward("cmsys",true);
        		log.debug("returning document upload forward");
        		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
        		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?SYSTEM=" + request.getParameter("SYSTEM") + "&studNr="+request.getParameter("studNr"));
        		return af;
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.results") && !"".equalsIgnoreCase(request.getParameter("SYSTEM"))){
                	log.debug("got the tool you're looking for, now check the SYSTEM");
            		// go to document upload part of the tool
            		ActionForward af = new ActionForward("cmsys",true);
            		log.debug("returning document upload forward");
            		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
            		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?SYSTEM=" + request.getParameter("SYSTEM"));
            	return af;
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.exampaperonline") && !"".equalsIgnoreCase(request.getParameter("System"))){
            	log.debug("got the tool you're looking for, now check the SYSTEM");
        		// go to document upload part of the tool
        		ActionForward af = new ActionForward("cmsys",true);
        		log.debug("returning document upload forward");
        		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
        		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?SYSTEM=" + request.getParameter("System") + "&userId="+request.getParameter("UserId") + "&fn="+request.getParameter("fn"));
        		return af;
            }
			else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.unmarkassfollowup") && !"".equalsIgnoreCase(request.getParameter("System"))){
            	log.debug("got the tool you're looking for, now check the SYSTEM");
        		// go to document upload part of the tool
        		ActionForward af = new ActionForward("cmsys",true);
        		log.debug("returning document upload forward");
        		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
        		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?SYSTEM=" + request.getParameter("System") + "&userId="+request.getParameter("UserId") + "&report="+request.getParameter("report")+"&acadYear="+request.getParameter("acadYear")+"&semester="+request.getParameter("semester")+"&break="+request.getParameter("break")+"&days="+request.getParameter("days")+"&assType="+request.getParameter("assType")+"&modeSub="+request.getParameter("modeSub")+"&modeMark="+request.getParameter("modeMark")+"&collegeCode="+request.getParameter("collegeCode")+"&schoolCode="+request.getParameter("schoolCode")+"&dptCode="+request.getParameter("dptCode")+"&studyUnit="+request.getParameter("studyUnit")+"&assNumber="+request.getParameter("assNumber")+"&markerPersno="+request.getParameter("markerPersno")+"&sortOn="+request.getParameter("sortOn")+"&summariseOn="+request.getParameter("summariseOn")+"&toDays="+request.getParameter("toDays")+"&key1="+request.getParameter("key1")+"&key2="+request.getParameter("key2"));
        		return af;
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.exampapers") && !"".equalsIgnoreCase(request.getParameter("System"))){
            	log.debug("got the tool you're looking for, now check the SYSTEM");
        		// go to document upload part of the tool
        		ActionForward af = new ActionForward("cmsys",true);
        		log.debug("returning document upload forward");
        		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
        		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?SYSTEM=" + request.getParameter("System") + "&userId="+request.getParameter("UserId"));
        		return af;
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.assessmentcriteria") && !"".equalsIgnoreCase(request.getParameter("SYSTEM"))){
            	log.debug("got the tool you're looking for, now check the SYSTEM");
        		// go to document upload part of the tool
        		ActionForward af = new ActionForward("cmsys",true);
        		log.debug("returning document upload forward");
        		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
        		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?SYSTEM=" + request.getParameter("SYSTEM") + "&YEAR="+request.getParameter("YEAR") + "&PERIOD="+request.getParameter("PERIOD") + "&MODULE="+request.getParameter("MODULE"));
        		return af;
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.booklistadmin")){
            	log.debug("got the tool you're looking for, now check the type of booklist");
            	ActionForward af = new ActionForward("internal",true);        		
        		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
        		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?UserID="+request.getParameter("u")+"&"+"TYPE="+request.getParameter("t")); //t=type of book list
        		return af;
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.satbook")){
            	log.debug("got the tool you're looking for, now check the type of booking system");
            	ActionForward af = new ActionForward("internal",true);        		
        		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
        		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?UserID="+request.getParameter("u")+"&"+"systemID="+request.getParameter("s")); //t=type of book list
        		return af;
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.biodetails") &&
            		  "unisa.regdetails".equalsIgnoreCase(request.getParameter("originatedFrom"))) {
    			ActionForward af = new ActionForward("external",true);
    			//http://localhost:8080/unisa-findtool/default.do?shareTool=unisa.proxy&context=external&contentPath=Default.asp%3FCmd=ViewContent%26ContentID=17190
    			log.debug("returning external forward");
    			log.debug("ServerConfigurationService.getServerUrl()"+ServerConfigurationService.getServerUrl());
    			af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?originatedFrom=" + request.getParameter("originatedFrom"));
    			return af;
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.studentstatus") &&
          		  "unisa.studentoffer".equalsIgnoreCase(request.getParameter("originatedFrom"))) {
	  			ActionForward af = new ActionForward("external",true);
	  			//http://localhost:8080/unisa-findtool/default.do?shareTool=unisa.proxy&context=external&contentPath=Default.asp%3FCmd=ViewContent%26ContentID=17190
	  			log.debug("returning external forward");
	  			log.debug("ServerConfigurationService.getServerUrl()"+ServerConfigurationService.getServerUrl());
	  			af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?originatedFrom=" + request.getParameter("originatedFrom") + "&acaYear=" + request.getParameter("acaYear") + "&acaPeriod=" + request.getParameter("acaPeriod") + "&nr=" + request.getParameter("nr") + "&surname=" + request.getParameter("surname") + "&firstNames=" + request.getParameter("firstNames") + "&birthDay=" + request.getParameter("birthDay") + "&birthMonth="+request.getParameter("birthMonth")+"&birthYear="+request.getParameter("birthYear"));
	  			return af;	
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.studentoffer") &&
            		  "unisa.studentstatus".equalsIgnoreCase(request.getParameter("originatedFrom"))) {
    			ActionForward af = new ActionForward("external",true);
    			//http://localhost:8080/unisa-findtool/default.do?shareTool=unisa.proxy&context=external&contentPath=Default.asp%3FCmd=ViewContent%26ContentID=17190
    			log.debug("returning external forward");
    			log.debug("ServerConfigurationService.getServerUrl()"+ServerConfigurationService.getServerUrl());
    			af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?originatedFrom=" + request.getParameter("originatedFrom") + "&acaYear=" + request.getParameter("acaYear") + "&acaPeriod=" + request.getParameter("acaPeriod") + "&nr=" + request.getParameter("nr") + "&surname=" + request.getParameter("surname") + "&firstNames=" + request.getParameter("firstNames") + "&birthDay=" + request.getParameter("birthDay") + "&birthMonth="+request.getParameter("birthMonth")+"&birthYear="+request.getParameter("birthYear"));
    			return af;	
            }else if (request.getParameter("sharedTool").equalsIgnoreCase("upmg") && 
            		!"".equalsIgnoreCase(request.getParameter("u"))&& !"".equalsIgnoreCase(request.getParameter("f"))){
               	    log.debug("entered uploadmanager ");
            		// go to document upload part of the tool
            		ActionForward af = new ActionForward("cmsys",true);
            		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
            		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?userId=" + request.getParameter("userId") + "&from="+request.getParameter("from"));
            		return af;
    		}else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.regdetails")) {
    			ActionForward af = new ActionForward("external",true);
    			//http://localhost:8080/unisa-findtool/default.do?shareTool=unisa.proxy&context=external&contentPath=Default.asp%3FCmd=ViewContent%26ContentID=17190
    			log.debug("returning external forward");
    			log.debug("ServerConfigurationService.getServerUrl()"+ServerConfigurationService.getServerUrl());
    			
//    			if ("unisa.biodetails".equalsIgnoreCase(request.getParameter("returnedFrom")))  {  				
//    				af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/additions.do?action=displayContactDetails&returnedFrom=unisa.biodetails");
//    			} else {
    			
    				af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId());
//    			}
    			return af;    			
    		}
	           else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.assmarkerreallocation")){
                        log.debug("got the tool you're looking for, now check book out or book in");
                        ActionForward af = new ActionForward("external",true);
                        log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
                        af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?USER=" + request.getParameter("USER")+"&"+"FUNCTION=" + request.getParameter("FUNCTION")+"&"+"MACCODE=" + request.getParameter("MACCODE"));
                       //log.info("ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?USER=" + request.getParameter("USER")+"&"+"FUNCTION=" + request.getParameter("FUNCTION")+"&"+"MACCODE=" + request.getParameter("MACCODE"));
                        return af;
            }
         		//else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.tracking")){
                  //      log.debug("got the tool you're looking for, now check book out or book in");
                    //    ActionForward af = new ActionForward("external",true);
                      //  log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
                      // af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?NETWORKID=" + request.getParameter("NETWORKID")+"&FUNCTION=" + request.getParameter("FUNCTION")+"&MACCODE=" + request.getParameter("MACCODE")+"&USER="+ request.getParameter("USER"));
                      //  log.info("The Student System Tracking URL is " + ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?NETWORKID="+  request.getParameter("NETWORKID")+"&FUNCTION=" + request.getParameter("FUNCTION")+"&MACCODE=" + request.getParameter("MACCODE")+"&USER="+ request.getParameter("USER"));
                      //  return af;

            //}
			 else if (request.getParameter("sharedTool").equalsIgnoreCase("unisa.tracking")){
                        log.debug("got the tool you're looking for, now check book out or book in");
                        ActionForward af = new ActionForward("external",true);
                        log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
                       af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?NETWORKID=" + request.getParameter("NETWORKID")+"&FUNCTION=" + request.getParameter("FUNCTION")+"&MACCODE=" + request.getParameter("MACCODE")+"&USER="+ request.getParameter("USER"));
                        log.info("The Student System Tracking URL is " + ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?NETWORKID="+  request.getParameter("NETWORKID")+"&FUNCTION=" + request.getParameter("FUNCTION")+"&MACCODE=" + request.getParameter("MACCODE")+"&USER="+ request.getParameter("USER"));
                        return af;

            }


            else {            
            		ActionForward af = new ActionForward("internal",true);
            		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId());
            		return af;
            	}
            } else if (request.getQueryString().indexOf("uploadmanager") > -1  && request.getParameter("uploadmanager") != null && "unisa.uploadmanager".equalsIgnoreCase(tr.getToolId()) ) {
        		//not configured to be used
            	
              	log.debug("entered uploadmanager ");
        		// go to document upload part of the tool
        		ActionForward af = new ActionForward("cmsys",true);
        		log.debug("ServerConfigurationService.getServerUrl() = " +ServerConfigurationService.getServerUrl());
        		af.setPath(ServerConfigurationService.getServerUrl()+"/tool/"+tr.getId()+"/default.do?userId=" + request.getParameter("uploadmanager") + "&from=staff");
        		return af;
            }
        }
        log.debug("returning default forward after not finding the right tool");
		return mapping.findForward("default");
	}
	

}
