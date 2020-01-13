<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	
	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="main.heading"/>
	</sakai:group_heading>
	
	<p>
	<table>
		<tr> 
			<td> &nbsp;</td>
			
			<td>
				<html:link href="maintenance.do?act=txtDisplay">
					<fmt:message key="main.txt"/>
				</html:link>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr> 
			<td> &nbsp;</td>
			<td>
				<html:link href="maintenance.do?act=placementDisplay">
					<fmt:message key="plc.heading"/>
				</html:link>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr> 
			<td> &nbsp;</td>
			<td>
				<html:link href="maintenance.do?act=vendorDisplay">
					<fmt:message key="vendor.heading"/>
				</html:link>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr> 
			<td> &nbsp;</td>
			<td>
				<html:link href="maintenance.do?act=subjectDisplay">
					<fmt:message key="subj.heading"/>
				</html:link>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr> 
			<td> &nbsp;</td>
			<td>
				<html:link href="maintenance.do?act=newsTitleDisplay">
					<fmt:message key="news.heading"/>
				</html:link>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr> 
			<td> &nbsp;</td>
			<td>
				<html:link href="maintenance.do?act=highlightDisplay">
					<fmt:message key="high.heading"/>
				</html:link>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr> 
			<td> &nbsp;</td>
			<td>
				<html:link href="maintenance.do?act=resourceDisplay">
					<fmt:message key="res.heading"/>
				</html:link>
			</td>
		</tr>
	</table>
</html:form>
</sakai:html>