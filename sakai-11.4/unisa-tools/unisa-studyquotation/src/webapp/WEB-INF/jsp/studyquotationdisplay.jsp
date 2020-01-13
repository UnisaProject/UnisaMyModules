<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.studyquotation.ApplicationResources"/>
<sakai:html>
	<script type="text/javascript">
		
		window.onload = function() {
		var flag = document.getElementById('showDisclaimer').value;	
		if (flag=='true') alert(document.getElementById('disclaimerMessage').value);
		}
	
	</script>	
	
	<sakai:heading><fmt:message key="studyquote.heading"/></sakai:heading>
	<sakai:instruction>
		<fmt:message key="studyquote.userinstruction4" />
	</sakai:instruction>
	<html:form action="studyquotation">
	<input type="hidden" id="showDisclaimer" name="showDisclaimer" value="<bean:write name="studyquotationform" property="showDisclaimer"/>" />
	<input type="hidden" id="disclaimerMessage" name="disclaimerMessage" value="<bean:write name="studyquotationform" property="disclaimerMessage"/>" />
	<sakai:flat_list>
		<tr>
			<td width="25%">
				<b><fmt:message key="studyquote.field.academicyear"/></b>
			</td>
			<td colspan="2" width="75%">
				<bean:write name="studyquotationform" property="studyQuotation.academicYear"/>
			</td>
		<tr>
			<td width="25%">
				<b><fmt:message key="studyquote.field.qualificationcode"/></b>
			</td>

			<logic:equal name="studyquotationform" property="studyQuotation.qualification" value="02011">
			<td colspan="2" width="75%">
				<fmt:message key="studyquote.label.undergraduate"/>
			</td>
			</logic:equal>

			<logic:notEqual name="studyquotationform" property="studyQuotation.qualification" value="02011">
			<td colspan="2" width="75%">
				<fmt:message key="studyquote.label.postgraduate"/>: <bean:write name="studyquotationform" property="studyQuotation.qualification"/>
			</td>
			</logic:notEqual>
		</tr>
		<tr>
			<td  colspan="3" width="100%">
				<b><fmt:message key="studyquote.field.studyunit"/></b>
			</td>
		</tr>

		<logic:iterate name="studyquotationform" property="studyQuotation.studyUnits" id="record" indexId="i">
		<tr>
			<td width="25%">
				<bean:write name="record" property="studyUnitcode"/>
			</td>
			<logic:match name="record" property="description" value="*" >
			<td width="60%">
				<font color="red"><bean:write name="record" property="description"/></font>
			</td>
			</logic:match>
			<logic:notMatch name="record" property="description" value="*" >
			<td width="60%">
				<bean:write name="record" property="description"/>
			</td>
			</logic:notMatch>
			<td align="right" width="15%">
				<bean:write name="record" property="fee" format="0.00"/>
			</td>
		</tr>
		</logic:iterate>
		<tr>
			<td width="25%"><br></td><td width="60%"><br></td><td align="right" width="15%">-----------</td>
		</tr>
		<!-- Johanet Change 20181129 - Start -->
		<logic:greaterThan name="studyquotationform" property="studyQuotation.foodAccommodationFee" value="0">
			<tr>
				<td width="25%">
					<br>
				</td>
				<td width="60%">
					<b><fmt:message key="studyquote.label.foodAccommodation"/></b>
				</td>
				<td align="right" width="15%">
					<bean:write name="studyquotationform" property="studyQuotation.foodAccommodationFee" format="0.00"/>
				</td>
			</tr>	
		</logic:greaterThan>
		<logic:greaterThan name="studyquotationform" property="studyQuotation.nursingCouncilRegfee" value="0">
			<tr>
				<td width="25%">
					<br>
				</td>
				<td width="60%">
					<b><fmt:message key="studyquote.label.nursingCouncilReg"/></b>
				</td>
				<td align="right" width="15%">
					<bean:write name="studyquotationform" property="studyQuotation.nursingCouncilRegfee" format="0.00"/>
				</td>
			</tr>	
		</logic:greaterThan>
		<!-- Johanet Change 20181129 - End -->
		<tr>
			<td width="25%">
				<br>
			</td>
			<td width="60%">
				<b><fmt:message key="studyquote.label.prescribedBooks"/></b>
			</td>
			<logic:greaterThan name="studyquotationform" property="studyQuotation.prescribedBooks" value="0">
			<td align="right" width="15%">
				<bean:write name="studyquotationform" property="studyQuotation.prescribedBooks" format="0.00"/>
			</td>
			</logic:greaterThan>
			<logic:equal name="studyquotationform" property="studyQuotation.prescribedBooks" value="0">
			<td align="right" width="15%">
				N/A
			</td>
			</logic:equal>
		</tr>
		<tr>
			<td width="25%">
				<br>
			</td>
			<td width="60%">
				<b><fmt:message key="studyquote.label.countrylevy"/></b>
			</td>

			<logic:greaterThan name="studyquotationform" property="studyQuotation.foreignLevy" value="0">
			<td align="right" width="15%">
				<bean:write name="studyquotationform" property="studyQuotation.foreignLevy" format="0.00"/>
			</td>
			</logic:greaterThan>

			<logic:equal name="studyquotationform" property="studyQuotation.foreignLevy" value="0">
			<td align="right" width="15%">
				N/A
			</td>
			</logic:equal>
		</tr>
		<tr>
			<td width="25%">
				<br>
			</td>
			<td width="60%">
				<b><fmt:message key="studyquote.label.libaccess"/></b>
			</td>

			<logic:greaterThan name="studyquotationform" property="studyQuotation.libraryCardCost" value="0">
			<td align="right" width="15%">
				<bean:write name="studyquotationform" property="studyQuotation.libraryCardCost" format="0.00"/>
			</td>
			</logic:greaterThan>

			<logic:equal name="studyquotationform" property="studyQuotation.libraryCardCost" value="0">
			<td align="right"  width="15%">
				N/A
			</td>
			</logic:equal>
		</tr>
		<tr>
			<td width="25%">
				<br>
			</td>
			<td width="60%">
				<b><fmt:message key="studyquote.label.matricexempt"/></b>
			</td>
			<logic:greaterThan name="studyquotationform" property="studyQuotation.matricExemptionCost" value="0">
			<td align="right" width="15%">
				<bean:write name="studyquotationform" property="studyQuotation.matricExemptionCost" format="0.00"/>
			</td>
			</logic:greaterThan>

			<logic:equal name="studyquotationform" property="studyQuotation.matricExemptionCost" value="0">
			<td align="right" width="15%">
				N/A
			</td>
			</logic:equal>
		</tr>
		<tr>
			<td width="25%"><br></td><td width="60%"><br></td><td align="right" width="15%">-----------</td>
		</tr>
		<tr>

			<td width="25%"><br></td>
			<td align="right"  width="60%">
				<b><fmt:message key="studyquote.label.total"/></b>
			</td>
			<td align="right" width="15%">
				<bean:write name="studyquotationform" property="studyQuotation.totalFee" format="0.00"/>
			</td>
		</tr>
		<tr>
			<td width="25%"><br></td><td width="60%"><br></td><td align="right" width="15%">-----------</td>
		</tr>
		<tr>
			<td width="25%">
				<br>
			</td>
			<td align="right"  width="60%">
				<b><fmt:message key="studyquote.label.paymentdue"/></b>
			</td>
			<td align="right"  width="15%">
				<bean:write name="studyquotationform" property="studyQuotation.paymentDue" format="0.00"/>
			</td>
		</tr><tr>
			<td colspan="3" width="100%"><br/><fmt:message key="studyquote.userinstruction1"/></td>
		</tr><tr>
			<td colspan="3" width="100%"><br/><fmt:message key="studyquote.userinstruction2"/></td>
		</tr>		
	</sakai:flat_list>
	<logic:equal name="studyquotationform" property="showDisclaimer" value="true">
		<sakai:instruction>
			<bean:write name="studyquotationform" property="disclaimerMessage"/>
		</sakai:instruction>
	</logic:equal>
	
		<sakai:actions>
			<html:submit property="action">
				<fmt:message key="studyquote.button.back"/>
			</html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>