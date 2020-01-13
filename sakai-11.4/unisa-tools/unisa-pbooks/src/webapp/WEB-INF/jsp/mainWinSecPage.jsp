<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page   import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>
<sakai:html>
	<sakai:messages/>
	<sakai:tool_bar>
		<!-- Sifiso Changes:2017/07/14:LU_MA_BLU_06:Requirement 54:Diss Authorisation -->
		<logic:notEqual name = "bookMenuForm" property = "isDiss" value="true">
			<ul>
				<li>
					<html:link href="authorisation.do?action=codAuthRequestList" >
						<fmt:message key="link.codAuthorisation"/>
					</html:link>
				</li>
				<li>
					<html:link href="authorisation.do?action=schoolDirectorAuthRequestList" >
						<fmt:message key="link.schoolsdirectorAuthirization"/>
					</html:link>
				</li>
				<li>
					<html:link href="deanAuthorisation.do?action=deanview" >
						<fmt:message key="link.deanAuthorisation"/>
					</html:link>
				</li>
			</ul>
		</logic:notEqual>
		
		<!-- Sifiso Changes START:2017/07/14:LU_MA_BLU_06:Requirement 54:Diss Authorisation -->
		<logic:equal name = "bookMenuForm" property = "isDiss" value="true">
			<ul>
				<li>
					<html:link href="authorisation.do?action=codAuthRequestList" >
						<fmt:message key="link.codAuthorisation"/>
					</html:link>
				</li>
				<li>
					<html:link href="authorisation.do?action=schoolDirectorAuthRequestList" >
						<fmt:message key="link.schoolsdirectorAuthirization"/>
					</html:link> 
				</li>
				<li><html:link href="deanAuthorisation.do?action=deanview" >
						<fmt:message key="link.deanAuthorisation"/>
					</html:link>
				</li>
				<li><html:link href="authorisation.do?action=dissAuthRequestList">
						<fmt:message key="link.dissAuthorisation"/>
					</html:link>
				</li>
			</ul>
		</logic:equal>
		<!-- Sifiso Changes END:2017/07/14:LU_MA_BLU_06:Requirement 54:Diss Authorisation -->	
	</sakai:tool_bar>
	<sakai:instruction>
		<fmt:message key="function.instructiontext"/><br/>
		<fmt:message key="function.instructiontext1"/>
	</sakai:instruction>
	<html:form action="/prebook">
		   <p/>
		   <sakai:group_heading>
			    <fmt:message key="label.selecteditcoursemsg"/>
		   </sakai:group_heading>
	       <sakai:instruction>
		         <fmt:message key="label.frmbookmenuinstr"/>
	       </sakai:instruction>
	       	<html:select name="bookMenuForm"  property="acadyear" onchange = "submit();">
			            <html:option  value="Select Academic Year">Select Academic Year</html:option>
					    <html:options  collection="yearsList" property="value" labelProperty="label" />
	   		</html:select>&nbsp;&nbsp;<fmt:message key="function.and"/><BR><BR>
	   		<html:hidden property="action" value="processAcadyear"/>
	   		
        </html:form>
    	<html:form action="/prebook">
			<html:select name="bookMenuForm" property="selectedCourse" onchange = "submit();" >
					<html:option  value="Select Course Code">Select Course Code</html:option>
					<html:options  collection = "crs" property="value" labelProperty ="label"/>
		   </html:select>
		   <html:hidden property="action" value="prebooksContinue"/>
		   <html:hidden property="continueOption" value="MAINVIEWCONTINUE"/>
	     <p/>
	 </html:form>
   
</sakai:html>