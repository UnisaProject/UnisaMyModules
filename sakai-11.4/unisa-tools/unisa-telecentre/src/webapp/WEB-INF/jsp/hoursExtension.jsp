<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>
	<sakai:tool_bar>
			<html:link href="telecentre.do?action=displayStartingPage" >
			<fmt:message key="link.displayVisit.unselected"/>
		</html:link>
			<html:link href="telecentre.do?action=exportVisit" >
			<fmt:message key="link.exportVisitList.unselected"/>
		</html:link>
		<!-- Sifiso Changes:2016/08/05:Added the toolbar tab below to display as selected tab-->
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
	<sakai:messages/>
	<!-- Sifiso Changes:2016/08/05:Changed below tabs from sakai:instruction to sakai:heading-->
	<sakai:heading>
	   <fmt:message key="telecentre.extendHours.heading"/>
	</sakai:heading>
	<sakai:group_heading>
	   <fmt:message key="telecentre.extendStudentHours"/>
	</sakai:group_heading>                
	<html:form action="/telecentre.do">	
	  <sakai:group_table>
	         <tr>
	             <td><fmt:message key="telecentre.enterStudentNumber"/></td>
			     <td><html:text property="studentNr" size="30" maxlength="8" value=""></html:text></td>
			     <td></td>	
			</tr>
			<tr>
			    <td>
    		       <fmt:message key="telecentre.enterNrOfHours"/>
    		       &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
    		       &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;
    		       &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;
    		    </td>
			    <td><html:text property="extensionHours" size="5" ></html:text></td>
    		    <td></td>
			</tr>
			<tr>
			    <td><fmt:message key="telecentre.selectMonthAndYear"/></td>
			    <td>
				   <html:select name="telecentreForm"  property="month">
					      <html:options  collection="monthList" property="value" labelProperty="label" />
    		      </html:select>
    		    </td>
    		    <td>
    		    &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
    		       &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;
    		       &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
    		       <html:select name="telecentreForm" property="year">
					         <html:options  collection="yearsList" property="value" labelProperty="label" />
    		      </html:select>
    		    </td>
			</tr>
			
			<tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.extend.hrs"/>
			</html:submit>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.main"/>
			</html:submit>
		     </sakai:actions>
		     </td>
		 </tr>
		  </sakai:group_table>
	   <sakai:group_heading>
	   	   <fmt:message key="telecenter.selectCenter"/>
	   </sakai:group_heading>                
	 <sakai:group_table>
	         <tr>
	             <td><fmt:message key="telecentre.selectTelecenterName"/></td>
	             <td>
			           <html:select name="telecentreForm" property="telecentreCode">
					        <html:option value="-1">choose telecentre </html:option>	 
        		            <html:options  collection="telecentres" property="value" labelProperty="label" />
    		           </html:select>
			     </td>	
			</tr>
			<tr>
			    <td><fmt:message key="telecentre.enterNrOfHours"/></td>
			    <td><html:text property="extensionHoursForCentre" size="5" ></html:text></td>
    		    <td></td>
			</tr>
			<tr>
			    <td><fmt:message key="telecentre.selectMonthAndYear"/></td>
			    <td>
				   <html:select name="telecentreForm" property="monthForTelecenter">
					          <html:options  collection="monthList" property="value" labelProperty="label" />
    		      </html:select>
    		    </td>
    		    <td>
				   <html:select name="telecentreForm"  property="yearForTelecenter">
					       <html:options  collection="yearsList" property="value" labelProperty="label" />
    		      </html:select>
    		    </td>
			</tr>
			
			<tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.extend.centre.hrs"/>
			</html:submit>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.extend.records"/>
			</html:submit>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.main"/>
			</html:submit>
		     </sakai:actions>
		     </td><td></td><td></td>
		 </tr>
	  </sakai:group_table>
	  </html:form>
</sakai:html>
