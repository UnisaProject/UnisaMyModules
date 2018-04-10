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
   <!-- Sifiso Changes:2016/08/19:Added logic below to display toolbar view for regional staff only-->
   <logic:equal name="telecentreForm" property="isRegionalStaff" value="1">
	   <logic:equal name="telecentreForm" property="isAdmin" value="1">
	   		<sakai:tool_bar>
			   	<html:link href="telecentre.do?action=displayStartingPage" >
					<fmt:message key="link.displayVisit.unselected"/>
				</html:link>
				<html:link href="telecentre.do?action=exportVisit" >
					<fmt:message key="link.exportVisitList.selected"/>
				</html:link>
			</sakai:tool_bar>
	   </logic:equal>
   </logic:equal>
   <!-- Sifiso Changes:2016/08/19:Added logic below to display toolbar view for regional staff only-->
   <logic:equal name="telecentreForm" property="isRegionalStaff" value="1">
	   <logic:notEqual name="telecentreForm" property="isAdmin" value="1">
	   		<sakai:tool_bar>
			   	<html:link href="telecentre.do?action=displayStartingPage" >
					<fmt:message key="link.displayVisit.unselected"/>
				</html:link>
				<html:link href="telecentre.do?action=exportVisit" >
					<fmt:message key="link.exportVisitList.selected"/>
				</html:link>
			</sakai:tool_bar>
	   </logic:notEqual>
   </logic:equal>
   <!-- Sifiso Changes:2016/08/19:Added logic below to display toolbar view for regional staff only-->
   <logic:notEqual name="telecentreForm" property="isRegionalStaff" value="1">
   		<logic:equal name="telecentreForm" property="isAdmin" value="1">
			  <sakai:tool_bar>
					<html:link href="telecentre.do?action=displayStartingPage" >
					<!-- Sifiso Changes:2016/08/05:Changed 'link.displayVisit' to 'link.displayVisit.unselected'-->
						<fmt:message key="link.displayVisit.unselected"/>
					</html:link>
					<!-- Sifiso Changes:2016/08/05:Added the toolbar tab below to display as selected tab-->
					<html:link href="telecentre.do?action=exportVisit" >
						<fmt:message key="link.exportVisitList.selected"/>
					</html:link>
						<html:link href="telecentre.do?action=extendHours" >
						<fmt:message key="link.extendHours.unselected"/>
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
		</logic:equal>
	</logic:notEqual>
	<!-- Sifiso Changes:2016/08/05:Changed below tabs from sakai:instruction to sakai:heading-->
	<sakai:heading>
	   <fmt:message key="exportVisit.heading"/>
	</sakai:heading>
	<logic:notEqual name="telecentreForm" property="customvisittracker" value="1">
	      <sakai:instruction><fmt:message key="exportVisit.instruction"/></sakai:instruction>
	</logic:notEqual>   
	<logic:equal name="telecentreForm" property="customvisittracker" value="1">
	      <sakai:instruction><fmt:message key="exportVisit.date.instruction"/></sakai:instruction>
    </logic:equal>        
	<html:form action="/telecentre.do">	
	
	  <sakai:group_table>
	        <tr>
	             <logic:notEqual name="telecentreForm" property="customvisittracker" value="1">
	                 <td>
	                    <fmt:message key="telecentre.selectperiod"/>
	                 </td>
			         <td>
			               <html:select  name= "telecentreForm" property="visitPeriod"  onchange="submit();">
			                         <html:option value="-1">Choose  Period</html:option>	 
					                 <html:options  collection="periodList" property="value" labelProperty="label" />
    		              </html:select>
				     </td>	
				</logic:notEqual>
				</tr>
				<logic:equal name= "telecentreForm"  property="customvisittracker" value="1">
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
			   </logic:equal>
			<logic:notEqual name="telecentreForm" property="customvisittracker" value="1">
			    <tr>
			    <td><fmt:message key="telecentre.fromperiod"/></td>
			       <td>
				      <html:select name= "telecentreForm" property="fromMonth" >
					         <html:options collection="monthList" property="value" labelProperty="label" />
    		          </html:select>
    		       </td>
    		    <td>
				   <html:select name= "telecentreForm" property="fromYear" >
					        <html:options  collection="yearsList" property="value" labelProperty="label" />
    		      </html:select>
    		    </td>
			  </tr>
			  <tr>
			    <td><fmt:message key="telecentre.toperiod"/></td>
			    <td>
				   <html:select name= "telecentreForm" property="toMonth" >
					         <html:options  collection="toMonthsList" property="value" labelProperty="label" />
    		      </html:select>
    		    </td>
    		    <td>
				   <html:select name= "telecentreForm"  property="toYear" >
					          <html:options  collection="toYearsList" property="value" labelProperty="label" />

    		      </html:select>
    		    </td>
			  </tr>
			</logic:notEqual>
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
	   <html:hidden property="action" value="handleExportListBoxAction"/>
	    <html:hidden property="handleExtendeHoursDownloadAction" value="0"/>
	  </html:form>
</sakai:html>
