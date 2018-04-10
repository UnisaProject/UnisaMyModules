<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources"/>

<sakai:html>
<h3><fmt:message key="admin.heading"/></h3>
	<hr/>
	<sakai:messages/>
	<sakai:group_heading>
	       <fmt:message key="function.releasedateheading"/>
	  </sakai:group_heading>
	  <br>
	<sakai:instruction>
	   <fmt:message key="function.completeoptions"/>            
	</sakai:instruction>
	<br>
	<sakai:group_heading>
	     <fmt:message key="function.currentsettings"/>        
	</sakai:group_heading>
	<br>
	<html:form action="booklistadmin">
	 	<sakai:flat_list>
		   <logic:notEmpty name="booklistAdminForm" property="booklist">
			<tr>
			<th><fmt:message key="function.academicyear"/></th>
	 	   	    <th><fmt:message key="function.releasedate"/></th>
	 	   	    <th><fmt:message key="function.remove"/></th>
			</tr>
			
				<logic:iterate name="booklistAdminForm" property="booklist" id="d" indexId="dindex">				
		    <tr>
		              <td>
				          <bean:write name="d" property="academicYear"/>
			          </td>
			          <td>
				           <bean:write name="d" property="releaseDate"/>
			          </td>	
			          <td>
				     	<html:checkbox name="booklistAdminForm" property='<%="bookIndex["+dindex+"].remove" %>' />
			          </td>
			</tr>
			</logic:iterate>			
			</logic:notEmpty>			
		 </sakai:flat_list>
		  <br>
	  <sakai:group_heading>
	       <fmt:message key="function.updaterelease"/>
	  </sakai:group_heading>
	  	    <sakai:group_table>
	            <tr>
			           <td>
				            <fmt:message key="function.selectacademicyear"/> 
				            <html:select name="booklistAdminForm"  property="academicYear">
					               <html:options  collection="yearsList" property="value" labelProperty="label" />
    		                </html:select>
			           </td>
			   </tr>
			    <tr>
			       <td><fmt:message key="function.selectreleasedate"/> 
			          <html:select name="booklistAdminForm"  property="openingMonth">
					               <html:options  collection="monthList" property="value" labelProperty="label" />
    		          </html:select>
    		          <html:select name="booklistAdminForm"  property="openingDay">
					               <html:options  collection="dayList" property="value" labelProperty="label" />
    		          </html:select>
    		          <html:select name="booklistAdminForm"  property="openingYear">
					               <html:options  collection="yearsList" property="value" labelProperty="label" />
    		          </html:select>
    		       </td>
    		       
			   </tr>
		  </sakai:group_table>
	  	  <sakai:actions>
			           <html:submit styleClass="button" property="action">
			                    <fmt:message key="button.savereleasedates"/>
			           </html:submit>
			           <html:submit styleClass="button" property="action">
			                <fmt:message key="button.removedatesconfirm"/>
			           </html:submit>
			           <html:submit styleClass="button" property="action">
			                <fmt:message key="button.cancelreleasedates"/>
			           </html:submit>
		 </sakai:actions>
	 </html:form>
</sakai:html>
		 
		

