
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentregistration.ApplicationResources"/>

<sakai:html>

<%-- Toolbar --%>
<sakai:tool_bar>
	<html:link href="studentRegistration.do?action=applyForStudentNr">
		<fmt:message key="page.main.link.apply"/>
	</html:link>
	<html:link href="studentRegistration.do?action=walkThroughStep1&type=U">
		<fmt:message key="page.main.link.undergrad"/>
	</html:link>
	<html:link href="studentRegistration.do?action=walkThroughStep1&type=P">
		<fmt:message key="page.main.link.postgrad"/>
	</html:link>
	<html:link href="studentRegistration.do?action=walkThroughStep1&type=S">
		<fmt:message key="page.main.link.short"/>
	</html:link>
</sakai:tool_bar>


<br/>

	<html:form action="/studentRegistration">

		<sakai:messages/>
		<sakai:messages message="true"/>

		<html:hidden property="action" value="studentRegistration"/>

		<sakai:heading>
			<center>
			<fmt:message key="page.main.heading1"/><br/>
			<fmt:message key="page.main.heading2"/><br/><br/>
			<fmt:message key="page.main.heading3"/>
			</center>
		</sakai:heading>
<br/>

	<sakai:heading><fmt:message key="page.main.subheading1"/></sakai:heading>
		<sakai:flat_list>
		<tr>
		 	<td align="left"><fmt:message key="page.main.info1"/>&nbsp;</td>
		</tr><tr>
		 	<td align="left"><fmt:message key="page.main.info2"/>&nbsp;</td>
		</tr><tr>
		 	<td align="left"><fmt:message key="page.main.info3a"/><a HREF='https://my.unisa.ac.za/portal/tool/f23111a5-f20f-492c-80f3-a777e11d801d/Default.asp?Cmd=ViewContent&ContentID=16586&P_XSLFile=unisa/lms.xsl'>
			<fmt:message key="page.main.info3b"/></a>&nbsp;<fmt:message key="page.main.info3c"/></td>
		</tr>
		</sakai:flat_list>
	<br/>
	<sakai:heading><fmt:message key="page.main.subheading2"/></sakai:heading>
		<sakai:flat_list>
		<tr>
		 	<td align="left"><fmt:message key="page.main.info.dot"/>&nbsp;
		 		<a HREF='https://my.unisa.ac.za/portal/tool/f23111a5-f20f-492c-80f3-a777e11d801d/Default.asp?Cmd=ViewContent&ContentID=16586&P_XSLFile=unisa/lms.xsl'>
				<fmt:message key="page.main.link.admission"/></a>
		 	 </td>
		</tr><tr>
		 	<td align="left"><fmt:message key="page.main.info.dot"/>&nbsp;
		 		<a HREF='https://my.unisa.ac.za/portal/tool/f23111a5-f20f-492c-80f3-a777e11d801d/Default.asp?Cmd=ViewContent&ContentID=17231&P_XSLFile=unisa/lms.xsl'>
				<fmt:message key="page.main.link.registration"/></a>
		 	 </td>
		</tr><tr>
		 	<td align="left"><fmt:message key="page.main.info.dot"/>&nbsp;
		 		<a HREF='http://brochure.unisa.ac.za/brochure/'>
				<fmt:message key="page.main.link.course"/></a>
		 	 </td>
		</tr><tr>
		 	<td align="left"><fmt:message key="page.main.info.dot"/>&nbsp;
		 		<a HREF='https://my.unisa.ac.za/portal/tool/f23111a5-f20f-492c-80f3-a777e11d801d/Default.asp?Cmd=ViewContent&ContentID=17176&P_XSLFile=unisa/lms.xsl'>
				<fmt:message key="page.main.link.docs"/></a>
		 	 </td>
		</tr><tr>
		 	<td align="left"><fmt:message key="page.main.info.dot"/>&nbsp;
		 		<a HREF='https://my.unisa.ac.za/portal/tool/f23111a5-f20f-492c-80f3-a777e11d801d/Default.asp?Cmd=ViewContent&ContentID=17190&P_XSLFile=unisa/lms.xsl'>
				<fmt:message key="page.main.link.fees"/></a>
		 	 </td>
		</tr><tr>
		 	<td align="left"><fmt:message key="page.main.info.dot"/>&nbsp;
		 		<a HREF='https://my.unisa.ac.za/portal/tool/f23111a5-f20f-492c-80f3-a777e11d801d/Default.asp?Cmd=ViewContent&ContentID=17029&P_XSLFile=unisa/lms.xsl'>
				<fmt:message key="page.main.link.examdates1"/></a>&nbsp;<fmt:message key="page.main.link.examdates2"/>
		 	 </td>
		</tr>
		</sakai:flat_list>
	<br/>

	</html:form>
</sakai:html>