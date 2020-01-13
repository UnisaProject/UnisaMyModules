<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>
        <sakai:messages/>
	        <sakai:tool_bar>
			     <html:link href="telecentre.do?action=displayStartingPage" >
			            <fmt:message key="link.displayVisit.unselected"/>
		         </html:link>
			<html:link href="telecentre.do?action=extendHours" >
			         <fmt:message key="link.extendHours.selected"/>
		    </html:link>
		    <!-- Sifiso Changes:Added below:2016-07-27:Manage telecentres links -->
			<html:link href="telecentre.do?action=createCentres" >
				<fmt:message key="profile.create.link.unselected"/>
			</html:link>
			<html:link href="telecentre.do?action=updateCentres" >
				<fmt:message key="profile.update.link.unselected"/>
			</html:link>
			<html:link href="telecentre.do?action=removeCentres" >
				<fmt:message key="profile.remove.link.unselected"/>
			</html:link>
			<html:link href="telecentre.do?action=activateCentres" >
				<fmt:message key="profile.activate.link.unselected"/>
			</html:link>
			<html:link href="telecentre.do?action=manageAdmins" >
				<fmt:message key="profile.manage.admin.unselected"/>
			</html:link>	
	        </sakai:tool_bar>
	        <sakai:instruction>
	                  <fmt:message key="extendHoursDownload.heading"/>
	        </sakai:instruction>
	        <html:form action="/telecentre.do">	
	          <sakai:group_table>
	                  <tr>
	                        <td>
			                   <fmt:message key="telecentre.enterStartDate"/>
			                </td>
			                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                   <html:select name="telecentreForm"  property="fromMonth">
					               <html:options  collection="monthList" property="value" labelProperty="label" />
    		                   </html:select>
    		                   <html:select name="telecentreForm"  property="fromDay">
					               <html:options  collection="daysList" property="value" labelProperty="label" />
    		                   </html:select>
    		                   <html:select name="telecentreForm"  property="fromYear">
					               <html:options  collection="yearsList" property="value" labelProperty="label" />
    		                   </html:select>
    		               </td>
			          </tr>
			          <tr>
			               <td>
			                      <fmt:message key="telecentre.enterEndDate"/>
			               </td>
			               <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                     <html:select name="telecentreForm"  property="toMonth"  disabled="false">
					                   <html:options  	collection="toMonthsList" property="value" labelProperty="label" />
    		                     </html:select>
    		                     <html:select name="telecentreForm"  property="endDay" disabled="false">
					                    <html:options  collection="endDaysList" property="value" labelProperty="label" />
    		                     </html:select>
    		                     <html:select name="telecentreForm"  property="toYear" disabled="false">
					                    <html:options  collection="yearsList" property="value" labelProperty="label" />
    		                     </html:select>
    		               </td>
    		       </tr>
				   <tr><td>
		                <sakai:actions>
			                <html:submit styleClass="button" property="action">
			                     <fmt:message key="telecentre.download"/>
			                </html:submit>
			                <html:submit styleClass="button" property="action">
			                    <fmt:message key="telecentre.main"/>
			                </html:submit>
		                </sakai:actions>
		          </td>
		      </tr>
	      </sakai:group_table>
	   <html:hidden property="downloadExtendedHrs" value="1"/>
	 </html:form>
</sakai:html>
