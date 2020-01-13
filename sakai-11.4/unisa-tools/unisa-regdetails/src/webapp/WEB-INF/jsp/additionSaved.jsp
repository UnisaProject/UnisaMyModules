<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<sakai:messages/>
<sakai:messages message="true"/>
<h3><fmt:message key="page.heading.additions"/></h3>

<!-- Form -->
<html:form action="/additions" >
	<html:hidden property="goto" value="home"/>


 <p><strong><fmt:message key="page.additions.saved"/></strong><br/><br/>
	<logic:present name="stamp">
		<strong><fmt:message key="page.step4.date"/>&nbsp;<bean:write name="stamp"/></strong><br/><br/>
	</logic:present>
 </p>

 <!-- Show quotation -->
 <logic:equal name="regDetailsForm" property="showQuote" value="true">
 <logic:present name="quote">
 	<sakai:group_heading><fmt:message key="page.heading.additions.fees"/></sakai:group_heading>
 	<br/>
	<sakai:heading><fmt:message key="page.heading.additions.fees2"/></sakai:heading>
 	<sakai:flat_list>
 		<tr>
 			<td colspan="2"><strong><fmt:message key="page.step4.important"/></strong>&nbsp;</br>
				<fmt:message key="page.additions.fees.info1"/>
				<a HREF='http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17231' onClick="window.open('http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17231', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;" target="_blank">
				<fmt:message key="page.step4.closingdate"/></a>
				<fmt:message key="page.step4.important.line2"/>
			<br/><br/></td>
		</tr><tr>
			<td colspan="2">
				<b><fmt:message key="page.header.quote"/></b>
			</td>
		</tr><tr>
			<td colspan="2">&nbsp;
			</td>
		</tr>
		<logic:iterate name="quote" property="studyUnits" id="record" indexId="i">
		<tr>
			<td>
				<bean:write name="record" property="studyUnitcode"/>
			<logic:match name="record" property="description" value="*" >
				<font color="red">&nbsp;-&nbsp;<bean:write name="record" property="description"/></font>
			</logic:match>
			<logic:notMatch name="record" property="description" value="*" >
				&nbsp;-&nbsp;<bean:write name="record" property="description"/>
			</logic:notMatch>
			</td>
			<td align="right">
				<bean:write name="record" property="fee" format="0.00"/>
			</td>
		</tr>
		</logic:iterate>
		<logic:greaterThan name="quote" property="prescribedBooks" value="0">
		<tr>
			<td align="right"><strong>Prescribed Books</strong></td>
			<td align="right">
				<bean:write name="quote" property="prescribedBooks" format="0.00"/>
			</td>
		<tr>
		</logic:greaterThan>
		<tr>
			<td><br/></td><td align="right">-----------</td>
		</tr><tr>
			<td align="right"><strong>Total amount</strong></td>
			<td align="right"><strong>
				R&nbsp;<bean:write name="quote" property="totalFee" format="0.00"/></strong>
			</td>
		</tr><tr>
			<td><br/></td><td align="right">-----------</td>
		</tr><tr>
			<td align="right">
				<strong><fmt:message key="page.quote.paymentdue"/></strong>
			</td>
			<td align="right"><strong>
				R&nbsp;<bean:write name="quote" property="paymentDue" format="0.00"/></strong>
			</td>
		</tr><tr>
			<td><br/></td><td align="right">-----------</td>
		</tr><tr>
			<td colspan="2">&nbsp;</td>
		</tr>
  		</sakai:flat_list>
		<sakai:heading><fmt:message key="page.heading.additions.payment"/></sakai:heading>
		<p>
			<fmt:message key="page.fees.creditcard"/><br/><br/>
			<fmt:message key="page.additions.payment.general1"/>
			<a HREF='http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17193' onClick="window.open('http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17193', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;" target="_blank">
			<u><fmt:message key="page.general.clickhere"/></u></a>.
		</p>
 </logic:present>
 </logic:equal>

 <sakai:actions>
		<html:submit property="action"><fmt:message key="button.regdetails"/></html:submit>
 </sakai:actions>

</html:form>

</sakai:html>