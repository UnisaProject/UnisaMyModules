<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.broadbandrequest.ApplicationResources"/>

<sakai:html>
	<html:form action="/broadbandRequest">
		<html:hidden property="currentPage" value="paymentInformation"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.paymentInformation"/>
		</sakai:heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.student"/>&nbsp;</td>
				<td><bean:write name="broadbandRequestForm" property="student.number"/>&nbsp;(<bean:write name="broadbandRequestForm" property="student.printName"/>)</td>		
			</tr>
			<tr>
				<td><fmt:message key="prompt.cellPhoneNr"/>&nbsp;</td>			
				<td><bean:write name="broadbandRequestForm" property="student.contactInfo.cellNumber"/></td>
				<td><fmt:message key="general.note1.cellPhoneCorrect"/></td>
			</tr>
		</sakai:group_table>		
		<hr/>			
		<sakai:group_table>	
			<tr>
				<th><fmt:message key="prompt.serviceProvider"/>
					<logic:equal name="broadbandRequestForm" property="request.serviceProviderCost.spCode" value="XXX">
						<br/><fmt:message key="prompt.selectedRegionalCentre"/>
					</logic:equal>
				</th>	
				<td><bean:write name="broadbandRequestForm" property="request.serviceProviderCost.spDescription"/>
					<logic:equal name="broadbandRequestForm" property="request.serviceProviderCost.spCode" value="XXX">
						<br/><bean:write name="broadbandRequestForm" property="request.regionalCentre.engDescription"/>
					</logic:equal>
				</td>		
			</tr>			
			<tr>
				<th colspan="2"><fmt:message key="prompt.packageSelected"/>&nbsp;</th>				
			</tr>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.3GDataBundle"/><br/>
					<logic:equal name="broadbandRequestForm" property="request.modemFlag" value="Y">
						<fmt:message key="prompt.modem"/><br/>
						<fmt:message key="prompt.totalDue"/>	
					</logic:equal>						
				</td>	
				<td style="text-align: left"><bean:write name="broadbandRequestForm" property="request.serviceProviderCost.simFeeStr"/><br/>
					<logic:equal name="broadbandRequestForm" property="request.modemFlag" value="Y">
						<bean:write name="broadbandRequestForm" property="request.serviceProviderCost.modemFeeStr"/><br/>					
						<bean:write name="broadbandRequestForm" property="amountDueStr"/>
					</logic:equal>		
				</td>
			</tr>			
			<!--<logic:equal name="broadbandRequestForm" property="request.modemFlag" value="Y">
				<tr>
					<td>&nbsp;</td>
					<td><fmt:message key="prompt.modem"/></td>
					<td style="text-align: right"><bean:write name="broadbandRequestForm" property="request.serviceProviderCost.modemFeeStr"/></td>
				</tr>				
			</logic:equal>	
			<tr>
				<td>&nbsp;</td>
				<td><fmt:message key="prompt.totalDue"/>&nbsp;</td>
				<td style="text-align: right"><bean:write name="broadbandRequestForm" property="amountDueStr"/></td>
			</tr>-->			
		</sakai:group_table>
		
		<!--<fmt:message key="paymentInformation.par1.line1"/><br/>
		<fmt:message key="paymentInformation.par1.line2"/><br/>
		<fmt:message key="paymentInformation.par1.line3"/><br/>
		<fmt:message key="paymentInformation.par1.line4"/><br/><br/>-->
		
		<fmt:message key="paymentInformation.par2.line1"/><br/>
		
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
	<td>
		<H1>Payment methods</H1>
		<P>For your convenience, we provide a variety of different payment methods. The following table details ways to pay, payment channels and other information. In the case of electronic transfers, please ensure that the reference field is completed correctly. No cash or cheques will be accepted at any Unisa office. Cash can, however, be paid into the university’s bank account at any Standard Bank branch.</P>
		<table width="100%" cellpadding="2" cellspacing="1" border="2">
			<TR>
				<TH width="15%">
					<P>Debit cards </P>
				</TH>
				<TD width="60%">
					<P>Debit card payments are only accepted if you pay in person at Unisa in Sunnyside/Florida or at one of the regional offices.</P>
				</TD>
				<TD width="25%">
					<P>&nbsp;</P>
				</TD>
			</TR><TR>
				<TH>
					<P>Credit cards </P>
				</TH>
				<TD>
					<P>Credit cards are restricted to Visa and Mastercard. Credit card payments are accepted if you pay in person at Unisa in Sunnyside/Florida or at one of the regional offices.</P>
				</TD>
				<TD>
					<P>Please note that these are real-time transactions.</P>
				</TD>
			</TR><TR>
				<TH>
					<P>Unisa web credit<br/>card payments </P>
				</TH>
				<TD>
					<P>Unisa web payment link: <STRONG>https://registration.unisa.ac.za</STRONG> (this is a secure site) </P>
				</TD>
				<TD>
					<P>Please note that these are real-time transactions.</P>
				</TD>
			</TR><TR>
				<TH>
					<P>Postal orders,<br/>money orders &amp;<br/>bank drafts </P>
				</TH>
				<TD>
					<P>Postal orders, money orders and bank drafts should be made payable to Unisa. </P>
				</TD>
				<TD>
					<P>This method of payment will only be accepted if posted to the university.</P></TD>
			</TR><TR>
				<TH>
					<P>Telegraphic money<br/>order (TMO)</P></TH>
				<TD>
					<P>Students must state surname, first names, address, student number (if known) and for what purpose the payment is made. The TMO must be addressed to Unisa Unisarand for delivery and payment by the Unisarand Post Office.</P></TD>
				<TD>
					<P>&nbsp;</P>
				</TD>
			</TR><TR>
				<TH>
					<P>Bank deposit</P></TH>
				<TD>
					<P>Bank deposits can be made at any Standard Bank branch in South Africa</P>
					<UL>
					<LI>provided that the deposit slip is correctly filled in as follows: 
					<UL>
					<LI>Deposit reference: first eight (8) blocks are for the student number (If the student number consists of only 7 digits, then a 0 must be filled in as the first digit of the student number) leave one block open, then fill in the code number 5400315318 for 3G data bundle.</LI></UL>
					<LI>Ensure that the deposit slip is correctly filled in as follows: 
					<UL>
					<LI>Deposit to: Unisa student deposits 
					<LI>Bank account number: 096R 
					<LI>Deposit reference: first eight (8) blocks are for the student number (If the student number consists of only 7 digits, then a 0 must be filled in as the first digit of the student number). Leave one block open, then fill in the following code: 
					<UL>
					<LI>5400315318 for 3G data bundle
					</UL></LI></UL>
					<LI>If a cheque is deposited, write your student number on the back of the cheque </LI></UL>
				</TD>
				<TD>
					<P>Should any of the information on the bank deposit slip be incomplete or incorrect, the transaction will not be processed by the bank and/or may cause a delay in the allocation or transfer of funds to your account and as such the university cannot be held liable for delays caused.<br/>
					Payments will be allocated within two days, except over weekends, to the study fees account provided that all the information on the deposit slip is correct.<br/>
					Only bank guaranteed cheques will be accepted for deposit. You must write your student number on the back of the cheque.</P>
				</TD>
			</TR><TR>
				<TH>
					<P>Internet/electronic<br/>payments</P>
				</TH>
				<TD>
					<P>Please note that Unisa is a pre-approved beneficiary.</P>
					<UL>
					<LI>Select Unisa on the beneficiary field. 
					<LI>You will not be required to complete the bank account or branch code details. 
					<LI>Ensure that the reference field is completed correctly. 
					<UL>
					<LI>Deposit reference: first eight (8) blocks are for the student number (if the student number consists of only 7 digits, then a 0 must be filled in as the first digit of the student number). Leave one block open, then fill in the following code: 
					<UL>
					<LI>5400315318 for 3G data bundle
					</LI></UL>
				</TD>
				<TD>
					<P>Should any of the information on the bank deposit slip be incomplete or incorrect, the transaction will not be processed by the bank and/or may cause a delay in the allocation or transfer of funds to your account.<br/>
					Payments will be allocated within two days, except over weekends, to the study fees account provided that all the information on the deposit slip is correct.</P>
				</TD>
			</TR>
		</table>
	</td>
</tr><tr>
	<td>	
		<H2><BR>Bank payments</H2>
		<P>Please note that Unisa now banks with Standard Bank. Should you need to make payments at a Standard Bank branch, please refer to the following sample deposit slip.<br/>
		<!--<IMG alt="Standard Bank deposit slip for Unisa payments" align=left src="http://www.unisa.ac.za/myUnisa/images/standard-bank-deposit-slip.jpg">-->
		<IMG alt="Standard Bank deposit slip for Unisa payments" align=left src="https://my.unisa.ac.za/broadband/standard-bank-deposit-slip.jpg">
		</SPAN></P>
		<TABLE class=reg border=0 cellSpacing=0 cellPadding=0 width=290 align=left>
			<TBODY>
				<TR>
					<TD colSpan=2>Complete the deposit slip according to this table:</TD>
				</TR><TR>
					<TD>1&nbsp;&nbsp;-&nbsp;&nbsp;</TD>
					<TD>Unisa student deposits</TD>
				</TR><TR>
					<TD>2&nbsp;&nbsp;-&nbsp;&nbsp;</TD>
					<TD>096R</TD>
				</TR><TR>
					<TD>3&nbsp;&nbsp;-&nbsp;&nbsp;</TD>
					<TD>8 digit student number</TD>
				</TR><TR>
					<TD>4&nbsp;&nbsp;-&nbsp;&nbsp;</TD>
					<TD>Leave a block empty after your student number</TD>
				</TR><TR>
					<TD>5&nbsp;&nbsp;-&nbsp;&nbsp;</TD>
					<TD><STRONG>Reference number</STRONG><br>
				</TR><TR>
					<TD>&nbsp</TD>	
					<TD>5400315318 for 3G data bundle</TD>					
				</TR>
			</TBODY>
		</TABLE>
	</td>
</tr>
</table>		
		<sakai:actions>			
			<html:submit property="act">
					<fmt:message key="button.ok"/>
			</html:submit>		
		</sakai:actions>	
	</html:form>
</sakai:html>		