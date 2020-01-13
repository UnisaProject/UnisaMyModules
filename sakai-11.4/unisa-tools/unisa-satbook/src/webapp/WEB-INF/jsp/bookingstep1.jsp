<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

	<sakai:heading>
		<logic:equal name="bookingForm" property="systemID" value="1">
			<fmt:message key="dailyview.heading.sat"/>			
		</logic:equal>
		<logic:equal name="bookingForm" property="systemID" value="2">
			<fmt:message key="dailyview.heading.venue"/>	
		</logic:equal>
	</sakai:heading>

	<!-- logic:equal name="bookingForm" property="systemID" value="1" -->
	<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}">
		<sakai:heading>
			<fmt:message key="book.add"/>
		</sakai:heading>
		<b><fmt:message key="satbook.step1"/> </b><br></br>
		<fmt:message key="satbook.step2"/><br></br>
		<fmt:message key="satbook.step3"/><br></br>
		<fmt:message key="satbook.step4"/><br></br>
		<fmt:message key="satbook.step5"/><br></br>
		<fmt:message key="satbook.step6"/><br></br>
	</logic:equal>
	<logic:equal name="bookingForm" property="systemID" value="${bookingForm.venbook}" >
		<sakai:heading>
			<fmt:message key="book.add"/>
		</sakai:heading>
		<b><fmt:message key="venbook.step1"/></b><br></br>
		<fmt:message key="venbook.step2"/><br></br>
		<fmt:message key="venbook.step3"/><br></br>
		<fmt:message key="venbook.step4"/><br></br>	
	</logic:equal>

	<sakai:instruction><fmt:message key="bookingstep1.instruction"/></sakai:instruction>
		<sakai:instruction> <fmt:message key="info.required"/><sakai:required/> </sakai:instruction>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<html:form action="satbookBooking.do">
	<script type="text/javascript">

			function makeChoice()

			{
						
			if( document.bookingForm.rebroadcast[0].checked)
			{
			
			document.bookingForm.rebroadDay.disabled=false;
			document.bookingForm.rebroadDay.focus();
			document.bookingForm.rebroadMonth.disabled=false;
			document.bookingForm.rebroadYear.disabled=false;
			
			}
			else
			{
			document.bookingForm.rebroadDay.disabled=true;
			document.bookingForm.rebroadMonth.disabled=true;
			document.bookingForm.rebroadYear.disabled=true;
			}
			
			}
				
		</script>

		<html:hidden property="atstep" value="1"/>
				
		<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}">				
			<sakai:group_heading><fmt:message key="bookingstep1.tableheading.information"/></sakai:group_heading>
			<sakai:group_table>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.heading"/><sakai:required/></td>
					<td>
						<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
							<html:text property="heading" size="30" maxlength="30"/>
							<!--<bean:write name="bookingForm" property="heading"/>-->
						</logic:equal>
						<logic:equal name="bookingForm" property="pageStatus" value="ADD">
							<html:text property="heading" size="30" maxlength="30"/>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.createdby"/><sakai:required/></td>
					<td>
						<bean:write name="bookingForm" property="createdBy"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.lecturer"/><sakai:required/></td>
					<td>
						<logic:equal name="bookingForm" property="pageStatus" value="ADD">
							<html:text property="lecturerNovellId"/>
						</logic:equal>
						<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
							<bean:write name="bookingForm" property="lecturerNovellId"/>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.startdate"/><sakai:required/></td>
					<td>
						<logic:equal name="bookingForm" property="pageStatus" value="ADD">
							<html:text property="startDay" size="2" maxlength="2"/>-
							<html:select property="startMonth">
								<html:options collection="monthList" property="value" labelProperty="label"/>
							</html:select>
							-
							<html:select property="startYear">
								<html:options collection="yearList" property="value" labelProperty="label"/>
							</html:select>
						</logic:equal>
						<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
							<bean:write name="bookingForm" property="startDate"/>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.starttime"/><sakai:required/></td>
					<td>
						<html:text property="startHH" size="2" maxlength="2"/>:
						<html:text property="startMM" size="2" maxlength="2"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.endtime"/><sakai:required/></td>
					<td>
						<html:text property="endHH" size="2" maxlength="2"/>:
						<html:text property="endMM" size="2" maxlength="2"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.registration"/><sakai:required/></td>
					<td>
						<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
							<bean:write name="bookingForm" property="registrationPeriodDesc"/>
						</logic:equal>
						<logic:equal name="bookingForm" property="pageStatus" value="ADD">
							<html:select property="registrationPeriod">
								<html:options collection="registrationPeriodList" property="value" labelProperty="label"/>
							</html:select>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.registrationyear"/> <sakai:required/></td>
					<td>
						<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
							<bean:write name="bookingForm" property="registrationYear"/>
						</logic:equal>
						<logic:equal name="bookingForm" property="pageStatus" value="ADD">
							<html:select property="registrationYear">
								<html:options collection="yearList" property="value" labelProperty="label"/>
							</html:select>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.broadcastdescription"/> <sakai:required/></td>
					<td>
						<html:textarea property="description" cols="25" rows="4"></html:textarea>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.isrebroadcast"/> <sakai:required/></td>
					<td>
						<html:radio name="bookingForm" property="rebroadcast" value="true" 
							onclick="makeChoice();"> <fmt:message key="bookingstep1.rebr"/>
						</html:radio>
						<html:radio name="bookingForm" property="rebroadcast" value="false"
							onclick="makeChoice();"> <fmt:message key="bookingstep1.livebr"/>
						</html:radio>
					</td>
				</tr>
				
				<tr>
					<td>
						<fmt:message key="bookingstep1.tableheading.info"/><br></br>
						<fmt:message key="bookingstep1.tableheading.rebroadcastdate"/><sakai:required/>
					</td>
					<td>
						<logic:equal name="bookingForm" property="pageStatus" value="ADD">
							<html:text property="rebroadDay" size="2" maxlength="2"/>-
							<html:select property="rebroadMonth">
							<html:options collection="monthList" property="value" labelProperty="label"/>
							</html:select>
							-
							<html:select property="rebroadYear">
							<html:options collection="yearList" property="value" labelProperty="label"/>
							</html:select>
						</logic:equal>
						
						<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
							<html:text property="rebroadDay" size="2" maxlength="2"/>-
							<html:select property="rebroadMonth">
							<html:options collection="monthList" property="value" labelProperty="label"/>
							</html:select>
							-
							<html:select property="rebroadYear">
							<html:options collection="yearList" property="value" labelProperty="label"/>
							</html:select>
							
						</logic:equal>
					</td>
				</tr>
			</sakai:group_table>
		</logic:equal>
		
		<logic:equal name="bookingForm" property="systemID" value="${bookingForm.venbook}">				
			<sakai:group_heading><fmt:message key="bookingstep1.tableheading.information"/></sakai:group_heading>
			<sakai:group_table>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.heading"/><sakai:required/></td>
					<td>
						<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
							<bean:write name="bookingForm" property="heading"/>
						</logic:equal>
						<logic:equal name="bookingForm" property="pageStatus" value="ADD">
							<html:text property="heading" size="30" maxlength="30"/></td>
						</logic:equal>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.createdby"/><sakai:required/></td>
					<td>
						<bean:write name="bookingForm" property="createdBy"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.startdate"/><sakai:required/></td>
					<td>
						<logic:equal name="bookingForm" property="pageStatus" value="ADD">
							<html:text property="startDay" size="2" maxlength="2"/>-
							<html:select property="startMonth">
								<html:options collection="monthList" property="value" labelProperty="label"/>
							</html:select>
							-
							<html:select property="startYear">
								<html:options collection="yearList" property="value" labelProperty="label"/>
							</html:select>
						</logic:equal>
						<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
							<bean:write name="bookingForm" property="startDate"/>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.starttime"/><sakai:required/></td>
					<td>
						<html:text property="startHH" size="2" maxlength="2"/>:
						<html:text property="startMM" size="2" maxlength="2"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.endtime"/><sakai:required/></td>
					<td>
						<html:text property="endHH" size="2" maxlength="2"/>:
						<html:text property="endMM" size="2" maxlength="2"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.typebkng"/> <sakai:required/></td>
					<td>
						<html:select property="bkngType">
								<html:options collection="bkngTypeList" property="value" labelProperty="label"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="bookingstep1.tableheading.broadcastdescription"/> <sakai:required/></td>
					<td>
						<html:textarea property="description" cols="25" rows="4"></html:textarea>
					</td>
				</tr>			
			</sakai:group_table>
		</logic:equal>
	
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.continue"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
		<script type="text/javascript">

							
			if( document.bookingForm.rebroadcast[0].checked)
			{
			
			document.bookingForm.rebroadDay.disabled=false;
			document.bookingForm.rebroadDay.focus();
			document.bookingForm.rebroadMonth.disabled=false;
			document.bookingForm.rebroadYear.disabled=false;
			
			}
			else
			{
			document.bookingForm.rebroadDay.disabled=true;
			document.bookingForm.rebroadMonth.disabled=true;
			document.bookingForm.rebroadYear.disabled=true;
			}
			
				
		</script>
	</html:form>
</sakai:html>
