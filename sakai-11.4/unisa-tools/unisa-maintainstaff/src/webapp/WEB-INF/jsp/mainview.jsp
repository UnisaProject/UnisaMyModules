<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<script language="JavaScript">
	function setActionCourse() {
		document.mainForm.action = 'main.do?act=courseDisplay';
		document.mainForm.submit();
	}
	function setActionCourse1() {
		document.mainForm.action = 'main.do?act=courseDisplay1';
		document.mainForm.submit();
	}
	function setActionPerson() {
		document.mainForm.action = 'main.do?act=personDisplay';
		document.mainForm.submit();
	}
	function setActionPerson1() {
		document.mainForm.action = 'main.do?act=personDisplay1';
		document.mainForm.submit();
	}
</script>
<sakai:html>

<html:form action="/main"> 
<html:hidden property="display" value=""/>
<html:hidden property="changeview" value=""/>
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<table>
		<tr> 
			<td><fmt:message key="main.info.course"/></td>
			<td><html:text property="course" size="15"/></td>
			<td>
				<sakai:actions>
					<html:submit onclick="setActionCourse();">
						<fmt:message key="button.displaycourse"/>
					</html:submit>
				</sakai:actions>
			</td>
		</tr>
		<tr> 
			<td><fmt:message key="main.info.person"/></td>
			<td><html:text property="person" size="15"/></td>
			<td>
				<sakai:actions>
					<html:submit onclick="setActionPerson();">
						<fmt:message key="button.displayperson"/>
					</html:submit>
				</sakai:actions>
			</td>			
		</tr>
	</table>
	
	<logic:equal name="mainForm" property="userPermission" value="MAINTAIN">
		<fmt:message key="main.info1"/><br><br>
		<fmt:message key="main.info2"/>
		<fmt:message key="main.info3"/>
		<fmt:message key="main.info4"/>
		<p>
		<table border=1 cellspacing=0>
			<tr>
				<td  valign='top' width='30%' align='center'>
					<table border='1' width='75%' cellspacing=0 >
						<tr><td>
							<fmt:message key="main.mngpeople"/> <br>
							<ul>
								<li><fmt:message key="main.mngpeople1"/></li>
								<li><fmt:message key="main.mngpeople2"/></li>
								<li><fmt:message key="main.mngpeople3"/></li>
							</ul>
						</td></tr>
					</table>
					<html:text property="person1" size="15"/> <br>
					<sakai:actions>
						<html:submit onclick="setActionPerson1();">
							<fmt:message key="button.displayperson"/>
						</html:submit>
					</sakai:actions>
				</td>
				<td  valign='top' width='30%' align='center'>
					<table border='1' width='75%' cellspacing=0>
						<tr><td>
							<fmt:message key="main.mngcourse"/> <br>
							<ul>
								<li><fmt:message key="main.mngcourse1"/></li>
								<li><fmt:message key="main.mngcourse2"/></li>
								<li><fmt:message key="main.mngcourse3"/></li>
								<li><fmt:message key="main.mngcourse4"/></li>
							</ul>
						</td></tr>
					</table>
					<html:text property="course1" size="15"/><br>
					<sakai:actions>
						<html:submit onclick="setActionCourse1();">
							<fmt:message key="button.displaycourse"/>
						</html:submit>
					</sakai:actions>
				</td>
				
				<td  valign='top' width='30%' align='center'>
					<table border='1' width='75%' cellspacing=0 >
						<tr><td>
							<fmt:message key="main.mngcopy"/> <br>
							<ul>
								<li><fmt:message key="main.mngcopy1"/></li>
							</ul>
						</td></tr>
					</table>				
					<sakai:actions>
						<html:submit property="act">
							<fmt:message key="button.copysite"/>
						</html:submit>
					</sakai:actions>
					
					<p>
					<table border='1' width='75%'cellspacing=0>
						<tr><td>
							<fmt:message key="main.mngcreate"/> <br>
						</td></tr>
					</table>
					<sakai:actions>
						<html:submit property="act">
							<fmt:message key="button.createsite"/>
						</html:submit>
					</sakai:actions>
					
					<p>
				</td>			
			</tr>
		</table>
		</p>
	</logic:equal>
</html:form>

</sakai:html>
