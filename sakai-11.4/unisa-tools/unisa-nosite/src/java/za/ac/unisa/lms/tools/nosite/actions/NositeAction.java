//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.nosite.actions;

import java.lang.Integer;
import java.util.Calendar;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.actions.LookupDispatchAction;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import za.ac.unisa.lms.tools.nosite.dao.NositeDAO;
import za.ac.unisa.lms.tools.nosite.dao.NositeDetails;
import za.ac.unisa.lms.tools.nosite.forms.NositeForm;

/**
 * MyEclipse Struts
 * Creation date: 09-08-2006
 *
 * XDoclet definition:
 * @struts.action parameter="action" validate="true"
 */
public class NositeAction extends LookupDispatchAction  {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/**
	 * Method view
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	protected Map getKeyMethodMap() {
	       Map map = new HashMap();
	       map.put("button.back","back");
	       map.put("view", "view");
	       map.put("viewnosites", "viewnosites");
	       map.put("gofoward", "goforth");
	       map.put("goback", "goback");
	       map.put("verystart", "begin");
	       map.put("verylast", "verylast");
	       map.put("button.display", "view");



	       return map;
	  }
	protected ActionForward unspecified(ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response)
     throws java.lang.Exception{
		
		return mapping.findForward("viewnosites");

	}
	public ActionForward view(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
			  NositeDAO dao = new NositeDAO();

			  NositeForm nositeForm = (NositeForm) form;
			  Calendar calendar = Calendar.getInstance();
			  Integer thisYear;
			  Integer lastYear;
			  Integer nextYear;
			  Integer year;
			  String collegecode ="";
			  String def="-1";//default
			  String col=nositeForm.getCollege();
              nositeForm.setCollegeMap(dao.getColleges());
			  nositeForm.setCollegecodeMap(dao.getCollegeMap());
              SortedSet collegeSet = Collections.synchronizedSortedSet(new TreeSet(dao.getColleges().values()));
              nositeForm.setCollegeMap(dao.getColleges());
			  nositeForm.setCollegecodeMap(dao.getCollegeMap());
			  thisYear = new Integer(calendar.get(Calendar.YEAR));
			  lastYear = new Integer(calendar.get(Calendar.YEAR)-1);
			  nextYear = new Integer(calendar.get(Calendar.YEAR)+1);
			  if (nositeForm.getYear() != null){
			   if(nositeForm.getYear().equalsIgnoreCase(nextYear.toString())){
			    year = nextYear;
			   } else if (nositeForm.getYear().equalsIgnoreCase(lastYear.toString())){
			    year = lastYear;
			   } else {
			    year = thisYear;
			   }
			  } else {
			   nositeForm.setYear(thisYear.toString());
			   year = thisYear;
			  }
			  nositeForm.setLastYear(lastYear.toString());
			  nositeForm.setCurrentYear(thisYear.toString());
			  nositeForm.setNextYear(nextYear.toString());


			 if ((col==null)||(def.equals(col)))
			 {nositeForm.setColleges(collegeSet);

			 nositeForm.setCollegeDetails(collegeSet);
			 List collegesites =dao.getCollegeinfo(dao.getActivesites(year.toString()),dao.getInctiveTotals(year.toString())
					 ,year.toString());
			 nositeForm.setCollegeinfo(dao.sortCollegedisplay(nositeForm.getCollegeDetails(),collegesites));

			 }
		 else
			 {
			  collegecode =(String)nositeForm.getCollegecodeMap().get(col);
			  nositeForm.setSchoollist(dao.getAllSchools(collegecode));
			  SortedSet deptSet = Collections.synchronizedSortedSet(new TreeSet(dao.collegeDepts(collegecode).values()));
			  nositeForm.setDeptMap(dao.getDeptMap());
			  nositeForm.setDeptcodeMap(dao.getDepts(collegecode));
			  SortedSet schoolSet = Collections.synchronizedSortedSet(new TreeSet(dao.getCollegeSchools(collegecode)));
			  nositeForm.setSchools(schoolSet);
			  nositeForm.setSchools(schoolSet);
			  nositeForm.setDepartments(deptSet);
			  nositeForm.setInactivesites(dao.getInctivesites(collegecode,year.toString()));
			  nositeForm.setSomeinactives(dao.pager(0,nositeForm.getRecords(), nositeForm.getInactivesites()));
			  nositeForm.setStart(1);
			  nositeForm.setEnd(Math.min(nositeForm.getRecords(), nositeForm.getNumitems()));
	    if (nositeForm.getNumitems()<1)
	    {nositeForm.setStart(0);
         nositeForm.setEnd(0);
	    }
    return mapping.findForward("viewnosites");
  }
return mapping.findForward("viewforward");
 }

	public ActionForward display(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		 	  NositeForm nositeForm = (NositeForm) form;
		 	  String def="-1";
		 	  if((!def.equals(nositeForm.getCollege()))||(nositeForm.getCollege()!=null))
			  {return mapping.findForward("viewnosites");}
		 	  else
		      {return mapping.findForward("viewforward");}
	}
	public ActionForward back(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
			  NositeForm nositeForm = (NositeForm) form;
              nositeForm.setCollege("-1");
		      nositeForm.setSchool("-1");
		      nositeForm.setDepartment("-1");
		      nositeForm.setStart(-1);
		      nositeForm.setEnd(nositeForm.getRecords()-1);

		return mapping.findForward("viewforward");
	}

	public  ActionForward viewnosites(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      NositeDAO dao = new NositeDAO();
		 	  NositeForm nositeForm = (NositeForm) form;

		 	  ActionMessages messages = new ActionMessages();

		 	  String college=nositeForm.getCollege();
		 	  String dept=nositeForm.getDepartment();
		 	  String sch=nositeForm.getSchool();
		 	  String collegecode =(String)nositeForm.getCollegecodeMap().get(college);
			  String test = (String)nositeForm.getCollegeMap().get(college);

			  String deptcode =(String)nositeForm.getDeptMap().get(dept);
			  String schcode =dao.getSchoolcode(nositeForm.getSchoollist(),nositeForm.getSchool());
			  SortedSet deptSet = Collections.synchronizedSortedSet(new TreeSet(dao.collegeDepts(collegecode).values()));
			  SortedSet schoolSet = Collections.synchronizedSortedSet(new TreeSet(dao.getCollegeSchools(collegecode)));
			  nositeForm.setSchools(schoolSet);
			  String year=nositeForm.getYear();
			  //System.out.println("The department code is "+deptcode+" and the actual dept is "+nositeForm.getDeptcodeMap().get(dept));

		 	  String def="-1";
              nositeForm.setInactivesites(dao.getInctivesites(collegecode,year.toString()));

            if ((def.equals(sch))&(def.equals(dept)))
               {schoolSet = Collections.synchronizedSortedSet(new TreeSet(dao.getSchools(nositeForm.getCollege()).values()));
                deptSet=Collections.synchronizedSortedSet(new TreeSet(dao.collegeDepts(collegecode).values()));
                nositeForm.setDepartments(deptSet);
			    nositeForm.setInactivesites(dao.getInctivesites(collegecode,year));
			    nositeForm.setStart(1);
			    nositeForm.setEnd(Math.min(nositeForm.getRecords(), nositeForm.getNumitems()));
            	nositeForm.setSomeinactives(dao.pager(nositeForm.getStart(),nositeForm.getRecords(), nositeForm.getInactivesites()));

            	}
            else if ((!def.equals(sch))& (def.equals(dept)))
            {   nositeForm.setInactivesites(dao.getDepsites(schcode, nositeForm.getInactivesites()));
             	nositeForm.setDepartments(dao.getdepartments(nositeForm.getInactivesites(),schcode,nositeForm.getDeptcodeMap()));
                nositeForm.setStart(1);
                nositeForm.setEnd(Math.min(nositeForm.getRecords(), nositeForm.getNumitems()));
            	nositeForm.setSomeinactives(dao.pager(nositeForm.getStart(),nositeForm.getRecords(), nositeForm.getInactivesites()));

            }
            else if (def.equals(sch)&(!def.equals(dept)))
            		{messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("nosite.noschool"));
            		nositeForm.setInactivesites(dao.getDepsites(dao.getInctivesites(collegecode,year.toString()), deptcode));

                    nositeForm.setStart(1);
                    nositeForm.setEnd(Math.min(nositeForm.getRecords(), nositeForm.getNumitems()));
                    nositeForm.setSomeinactives(dao.pager(nositeForm.getStart(),nositeForm.getRecords(), nositeForm.getInactivesites()));
            		}
            else if (!def.equals(sch)&(!def.equals(dept)))
            	{nositeForm.setInactivesites(dao.getDepsites(schcode,deptcode, nositeForm.getInactivesites()));
                 nositeForm.setDepartments(dao.getdepartments(dao.getInctivesites(collegecode,year.toString()),schcode,nositeForm.getDeptcodeMap()));
                 nositeForm.setStart(1);
                 nositeForm.setEnd(Math.min(nositeForm.getRecords(), nositeForm.getNumitems()));
                 nositeForm.setSomeinactives(dao.pager(nositeForm.getStart(),nositeForm.getRecords(), nositeForm.getInactivesites()));


               }
            if (nositeForm.getNumitems()<1)
             {nositeForm.setStart(0);
              nositeForm.setEnd(0); }

		return mapping.findForward("viewnosites");
	}
	public synchronized ActionForward goforth(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      NositeForm nositeForm = (NositeForm) form;
		      NositeDAO dao = new NositeDAO();

		      int start=nositeForm.getStart();
		      int records=nositeForm.getRecords();

		      if (start+records>(nositeForm.getNumitems()))
              {nositeForm.setStart(start);
               nositeForm.setSomeinactives(dao.pager(start, records, nositeForm.getInactivesites()));
               nositeForm.setEnd(nositeForm.getNumitems());
              }
		      else if (start+records-1<=(nositeForm.getNumitems()))
		      {  start=nositeForm.getEnd()+1;
		    	 int end= start+records-1;
		    	 nositeForm.setStart(start);
		    	 nositeForm.setSomeinactives(dao.pager(start, records, nositeForm.getInactivesites()));

                 end=Math.min(end,nositeForm.getNumitems());
                 nositeForm.setEnd(end);
  		      }

		      else
		    	 {start=nositeForm.getEnd();
		    	 int end= start+records-1;
		    	  nositeForm.setStart(start);
		    	  nositeForm.setSomeinactives(dao.pager(start, records, nositeForm.getInactivesites()));
                  end=Math.min(end,nositeForm.getNumitems());
                  nositeForm.setEnd(end);
   		          }
		      if ((nositeForm.getStart()+nositeForm.getRecords()-1)==nositeForm.getNumitems())
		      {
		    	  start=nositeForm.getStart();
		    	 int end=nositeForm.getNumitems();
	              end=Math.min(end,nositeForm.getNumitems());
	              nositeForm.setEnd(end);
 		    	  nositeForm.setSomeinactives(nositeForm.getInactivesites().subList(start, end));
	          }
		      if (nositeForm.getNumitems()<1){nositeForm.setStart(0);}
		return mapping.findForward("viewnosites");
	}

	public synchronized  ActionForward goback(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      NositeDAO dao = new NositeDAO();

		      NositeForm nositeForm = (NositeForm) form;

		      int start=nositeForm.getStart();
		      int records=nositeForm.getRecords();

		      if (start-records<=0)
              {nositeForm.setSomeinactives(dao.pager(0, records, nositeForm.getInactivesites()));
               nositeForm.setStart(1);
               nositeForm.setEnd(Math.min(nositeForm.getRecords(), nositeForm.getNumitems()));
              }
		    else if (start+records<nositeForm.getNumitems())
		     {int end=nositeForm.getStart()-1;
		      start=nositeForm.getStart()-nositeForm.getRecords();
              nositeForm.setSomeinactives(dao.pager(start, records, nositeForm.getInactivesites()));
              nositeForm.setStart(start);
              nositeForm.setEnd(Math.min(end, nositeForm.getNumitems()));
             }
		    else
		    {int end=nositeForm.getStart()-1;
		    start=nositeForm.getStart()-nositeForm.getRecords();
            nositeForm.setSomeinactives(dao.pager(start, records, nositeForm.getInactivesites()));
            nositeForm.setStart(start);
            nositeForm.setEnd(Math.min(end, nositeForm.getNumitems()));
            }
		      if (nositeForm.getNumitems()<1){nositeForm.setStart(0);}
		      return mapping.findForward("viewnosites");
	}

	public synchronized ActionForward begin(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      NositeDAO dao = new NositeDAO();

		      NositeForm nositeForm = (NositeForm) form;

		      int records=nositeForm.getRecords();
               nositeForm.setSomeinactives(dao.pager(0, records, nositeForm.getInactivesites()));
               nositeForm.setStart(1);
               nositeForm.setEnd(Math.min(nositeForm.getRecords(), nositeForm.getNumitems()));
               if (nositeForm.getNumitems()<1){nositeForm.setStart(0);}

		      return mapping.findForward("viewnosites");
	}

	public synchronized ActionForward verylast(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      NositeDAO dao = new NositeDAO();

		      NositeForm nositeForm = (NositeForm) form;
		      int numitems=nositeForm.getNumitems();
		      int start;

		      if (nositeForm.getNumitems()==0) {return mapping.findForward("viewnosites");}

		      if ((nositeForm.getStart()+nositeForm.getRecords()-1)==nositeForm.getNumitems())
		      {
		    	  start=nositeForm.getStart();
		    	 int end=nositeForm.getNumitems();
	              end=Math.min(end,nositeForm.getNumitems());
	              nositeForm.setEnd(end);
		    	  nositeForm.setSomeinactives(nositeForm.getInactivesites().subList(start, end));
    	      }
		      else
		      {start=1+nositeForm.getNumitems()-(nositeForm.getNumitems()%nositeForm.getRecords());
		       nositeForm.setSomeinactives(dao.pager(start,nositeForm.getRecords(), nositeForm.getInactivesites()));
		       nositeForm.setStart(start);
		       nositeForm.setEnd(numitems);
		      }

		      if ((nositeForm.getStart()-1)==nositeForm.getNumitems())
		        {start=nositeForm.getNumitems()-nositeForm.getRecords()+1;
	   	        nositeForm.setSomeinactives(nositeForm.getInactivesites().subList(start,nositeForm.getNumitems()));
		        nositeForm.setStart(nositeForm.getNumitems()-nositeForm.getRecords()+1);
		        }
		      if (nositeForm.getNumitems()<1){nositeForm.setStart(0);}
		      if (nositeForm.getStart()>nositeForm.getNumitems()){nositeForm.setStart(nositeForm.getNumitems());}

		      return mapping.findForward("viewnosites");
	}
	




}

