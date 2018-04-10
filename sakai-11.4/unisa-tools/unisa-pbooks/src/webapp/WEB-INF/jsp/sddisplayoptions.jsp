<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

 <sakai:html>
 <sakai:messages/>
  <html:form action="authorisation">
   <sakai:heading>
		<fmt:message key="function.sdauthsdheading"/> <b>	
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="P">	<fmt:message key="function.booklists"/> </logic:equal>
    	<logic:equal name="bookMenuForm" property="typeOfBookList" value="R">	<fmt:message key="function.booklistsforR"/> </logic:equal>
    	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	<fmt:message key="function.booklistsforE"/> </logic:equal></b><br/><p/>
   </sakai:heading><p/>
		 <sakai:instruction>
		<fmt:message key="function.schdirinstructiontext"/><br/>
		</sakai:instruction>
		<sakai:group_table>  
		<tr> <td> <fmt:message key="function.deandepartments" /></td>
     <td><html:select name="bookMenuForm"  property="department" onchange = "submit();">
     	           <html:option value="-1">Select a Department</html:option>
        		    <html:options  collection="schdirdepts" property="value" labelProperty="label" />
    	</html:select>   
	 	</td></tr> 
	 	</sakai:group_table>
	 	
	 		  									
              <ul type="disc">
                <li> <html:link href="authorisation.do?action=displayoptions&searchOption=notsubmitted"><fmt:message key="link.modulesoutstanding"/></html:link> </li>
                <li> <html:link href="authorisation.do?action=displayoptions&searchOption=0"><fmt:message key="link.modulesstarted"/></html:link> </li>
            	<li> <html:link href="authorisation.do?action=displayoptions&searchOption=1"><fmt:message key="link.moduleswaitingforcod"/></html:link> </li>
            	<li> <html:link href="authorisation.do?action=displayoptions&searchOption=2"><fmt:message key="link.moduleswaitingforSD"/></html:link> </li>
            	 <li><html:link href="authorisation.do?action=displayoptions&searchOption=3"><fmt:message key="link.nobooksdeclared"/></html:link> </li>
            	 <li><html:link href="authorisation.do?action=displayoptions&searchOption=4"><fmt:message key="link.moduleswaitingfordean"/></html:link> </li>
            	  <li><html:link href="authorisation.do?action=displayoptions&searchOption=5"><fmt:message key="link.modulesauthorizedbydean"/></html:link> </li>
            	  <li><html:link href="authorisation.do?action=displayoptions&searchOption=allmodules"><fmt:message key="link.allmodulesperdept"/></html:link> </li></ui>

		<sakai:flat_list>
		<html:hidden property="cancelOption" value="mainview"/>
      <tr><td><sakai:actions>
		<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions></td></tr>
		      
			</sakai:flat_list>
		
  <html:hidden property="action" value="displayoptions"/>
</html:form>
 </sakai:html> 