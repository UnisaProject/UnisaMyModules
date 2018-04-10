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
	<logic:equal name="monthlyForm" property="systemID" value="1">
		<fmt:message key="dailyview.heading.sat"/>
	</logic:equal>
	<logic:equal name="monthlyForm" property="systemID" value="2">
		<fmt:message key="dailyview.heading.venue"/>
		<sakai:tool_bar>	 
       	 <html:link  href="HelpForVenueBooking.pdf" target="_Blank"><img align="right" src="/library/skin/unisa/images/help.gif"></html:link> 
	    </sakai:tool_bar>
			</logic:equal>
	</sakai:heading>
 	<logic:equal name="monthlyForm" property="userPermission" value="MAINTAIN">
		<sakai:tool_bar>
			<html:link href="satbookMonthly.do?action=adminLink">
				<fmt:message key="button.monthlyview.adminview"/>
			</html:link>
			<html:link href="satbookDaily.do?action=bookingsreport">
				<fmt:message key="booking.report.heading"/>
			</html:link>
		</sakai:tool_bar>
	</logic:equal>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:heading>
		<fmt:message key="monthlyview.heading"/>
	</sakai:heading>
	<sakai:instruction>
		<fmt:message key="monthlyview.instruction1"/>
		<br/><br/>
		<fmt:message key="monthlyview.instruction2"/>
	</sakai:instruction>

	<html:form action="satbookMonthly.do" method="POST">

	<table><tr><td>
	<select name="selectMonth">
    	<option value="01">January</option>
    	<option value="02">February</option>
	    <option value="03">March</option>
	    <option value="04">April</option>
	    <option value="05">May</option>
	    <option value="06">June</option>
	    <option value="07">July</option>
	    <option value="08">August</option>
	    <option value="09">September</option>
	    <option value="10">October</option>
	    <option value="11">November</option>
	    <option value="12">December</option>
	</select>
	<select name="selectYear">
		<option value="${prevYear}"><c_rt:out value="${prevYear}"/></option>
		<option value="${currentYear}" selected="selected"><c_rt:out value="${currentYear}"/></option>
		<option value="${nextYear}"><c_rt:out value="${nextYear}"/></option>
	</select>
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.monthlyview.buildcalendar"/>
		</html:submit>
	</sakai:actions>
	</td></tr></table>

	<logic:equal name="monthlyForm" property="systemID" value="1">
	<font color="#F88017">
		<fmt:message key="info.unisa"/>
	</font>
	</logic:equal>

	<sakai:group_table>
		<tr>
			<td colspan="8" style="font-size: large;font-weight: bold;"><c_rt:out value="${satbookMonthlyForm.monthName}"/>  <c_rt:out value="${satbookMonthlyForm.yearName}"/></td>
		</tr>
		<tr>
			<th>
				<fmt:message key="montlhyview.weeknum"/>
			</th>
			<th>
				<fmt:message key="montlhyview.sun"/>
			</th>
			<th>
				<fmt:message key="montlhyview.mon"/>
			</th>
			<th>
				<fmt:message key="montlhyview.tue"/>
			</th>
			<th>
				<fmt:message key="montlhyview.wed"/>
			</th>
			<th>
				<fmt:message key="montlhyview.thu"/>
			</th>
			<th>
				<fmt:message key="montlhyview.fri"/>
			</th>
			<th>
				<fmt:message key="montlhyview.sat"/>
			</th>

		</tr>


		<c_rt:forEach begin="0" end="5" var="loopcounter">

			<tr>
				<td>
						<c_rt:choose>
							<c_rt:when test="${loopcounter == 5}">
								<c_rt:if test="${(numberOfDaysInTheMonth == 30 && firstDayOfTheMonth == 6) || (numberOfDaysInTheMonth == 31 && firstDayOfTheMonth == 5) || (numberOfDaysInTheMonth == 31 && firstDayOfTheMonth == 6) }">
									<c_rt:out value="${weeknumber + loopcounter}"/>
								</c_rt:if>
							</c_rt:when>
							<c_rt:otherwise>
								<c_rt:out value="${weeknumber + loopcounter}"/>
							</c_rt:otherwise>
						</c_rt:choose>
				</td>
					<c_rt:choose>
					<c_rt:when test="${loopcounter == 0}">
						<c_rt:forEach begin="0" end="6" var="firstweek">
							<td>
								<c_rt:if test="${firstweek == firstDayOfTheMonth}">
										<c_rt:set var="instcolor" scope="page" value="#FFFFFF" />
										<c_rt:forEach begin="0" step="1" end="${noOfDays}" varStatus="loopStatus">
											<c_rt:if test="${systemId == 1}">
												<c_rt:if test="${unisaDays[loopStatus.index] == 1}">
													<c_rt:set var="instcolor" scope="page" value="#F88017" />
												</c_rt:if>
											</c_rt:if>
										</c_rt:forEach>
									<table width='100%'> <tr> <td bgcolor="${instcolor}" >
										<html:link href="satbookMonthly.do?action=linkDay&day_selected=1" >1</html:link>
									</td></tr></table>
								</c_rt:if>
								<c_rt:if test="${firstweek > firstDayOfTheMonth}">
									<c_rt:set var="instcolor" scope="page" value="#FFFFFF" />
									<c_rt:forEach begin="0" step="1" end="${noOfDays}" varStatus="loopStatus">
										<c_rt:if test="${systemId == 1}">
											<c_rt:if test="${unisaDays[loopStatus.index] == ((firstweek + 1) - firstDayOfTheMonth)}">
												<c_rt:set var="instcolor" scope="page" value="#F88017" />
											</c_rt:if>
										</c_rt:if>
									</c_rt:forEach>
									<table width='100%'> <tr> <td bgcolor="${instcolor}" >
										<html:link href="satbookMonthly.do?action=linkDay&day_selected=${(firstweek + 1) - firstDayOfTheMonth}"><c_rt:out value="${(firstweek + 1) - firstDayOfTheMonth}"/></html:link>
									</td></tr></table>
								</c_rt:if>
							</td>
						</c_rt:forEach>
					</c_rt:when>
					<c_rt:when test="${loopcounter == 1}">
						<c_rt:forEach begin="0" end="6" var="secondweek">
							<td>
								<c_rt:set var="instcolor" scope="page" value="#FFFFFF" />
								<c_rt:forEach begin="0" step="1" end="${noOfDays}" varStatus="loopStatus">
									<c_rt:if test="${systemId == 1}">
										<c_rt:if test="${unisaDays[loopStatus.index] == ((8 + secondweek) - firstDayOfTheMonth)}">
											<c_rt:set var="instcolor" scope="page" value="#F88017" />
										</c_rt:if>
									</c_rt:if>
								</c_rt:forEach>
								<table width='100%'> <tr> <td bgcolor="${instcolor}" >
									<html:link href="satbookMonthly.do?action=linkDay&day_selected=${(8 + secondweek) - firstDayOfTheMonth}"><c_rt:out value="${(8 + secondweek) - firstDayOfTheMonth}"/></html:link>
								</td></tr></table>
							</td>
						</c_rt:forEach>
					</c_rt:when>

					<c_rt:when test="${loopcounter == 2}">

						<c_rt:forEach begin="0" end="6" var="thirdweek">
							<td>
								<c_rt:set var="instcolor" scope="page" value="#FFFFFF" />
								<c_rt:forEach begin="0" step="1" end="${noOfDays}" varStatus="loopStatus">
									<c_rt:if test="${systemId == 1}">
										<c_rt:if test="${unisaDays[loopStatus.index] == ((15 + thirdweek) - firstDayOfTheMonth)}">
											<c_rt:set var="instcolor" scope="page" value="#F88017" />
										</c_rt:if>
									</c_rt:if>
								</c_rt:forEach>
								<table width='100%'> <tr> <td bgcolor="${instcolor}" >
									<html:link href="satbookMonthly.do?action=linkDay&day_selected=${(15 + thirdweek) - firstDayOfTheMonth}"><c_rt:out value="${(15 + thirdweek) - firstDayOfTheMonth}"/></html:link>
								</td></tr></table>
							</td>
						</c_rt:forEach>
					</c_rt:when>

					<c_rt:when test="${loopcounter == 3}">

						<c_rt:forEach begin="0" end="6" var="fourthweek">
							<td>
								<c_rt:set var="instcolor" scope="page" value="#FFFFFF" />
								<c_rt:forEach begin="0" step="1" end="${noOfDays}" varStatus="loopStatus">
									<c_rt:if test="${systemId == 1}">
										<c_rt:if test="${unisaDays[loopStatus.index] == ((22 + fourthweek) - firstDayOfTheMonth)}">
											<c_rt:set var="instcolor" scope="page" value="#F88017" />
										</c_rt:if>
									</c_rt:if>
								</c_rt:forEach>
								<table width='100%'> <tr> <td bgcolor="${instcolor}" >
									<html:link href="satbookMonthly.do?action=linkDay&day_selected=${(22 + fourthweek) - firstDayOfTheMonth}"><c_rt:out value="${(22 + fourthweek) - firstDayOfTheMonth}"/></html:link>
								</td></tr></table>
							</td>
						</c_rt:forEach>
					</c_rt:when>

					<c_rt:when test="${loopcounter == 4}">
						<c_rt:forEach begin="0" end="6" var="fithweek">
								<c_rt:if test="${((29 + fithweek) - firstDayOfTheMonth) <= numberOfDaysInTheMonth}">
									<td>
										<c_rt:set var="instcolor" scope="page" value="#FFFFFF" />
										<c_rt:forEach begin="0" step="1" end="${noOfDays}" varStatus="loopStatus">
											<c_rt:if test="${systemId == 1}">
												<c_rt:if test="${unisaDays[loopStatus.index] == ((29 + fithweek) - firstDayOfTheMonth)}">
													<c_rt:set var="instcolor" scope="page" value="#F88017" />
												</c_rt:if>
											</c_rt:if>
										</c_rt:forEach>
										<table width='100%'> <tr> <td bgcolor="${instcolor}" >
											<html:link href="satbookMonthly.do?action=linkDay&day_selected=${(29 + fithweek) - firstDayOfTheMonth}"><c_rt:out value="${(29 + fithweek) - firstDayOfTheMonth}"/></html:link>
										</td></tr></table>
									</td>
								</c_rt:if>
								<c_rt:if test="${((29 + fithweek) - firstDayOfTheMonth) > numberOfDaysInTheMonth}">
									<td></td>
								</c_rt:if>

						</c_rt:forEach>
					</c_rt:when>
					<c_rt:otherwise>
						<c_rt:if test="${(numberOfDaysInTheMonth == 30 && firstDayOfTheMonth == 6) || (numberOfDaysInTheMonth == 31 && firstDayOfTheMonth == 5) || (numberOfDaysInTheMonth == 31 && firstDayOfTheMonth == 6) }">
							<c_rt:forEach begin="0" end="6" var="sixthweek">
								<c_rt:if test="${((36 + sixthweek) - firstDayOfTheMonth) <= numberOfDaysInTheMonth}">
									<td>
										<c_rt:set var="instcolor" scope="page" value="#FFFFFF" />
										<c_rt:forEach begin="0" step="1" end="${noOfDays}" varStatus="loopStatus">
											<c_rt:if test="${systemId == 1}">
												<c_rt:if test="${unisaDays[loopStatus.index] == ((36 + sixthweek) - firstDayOfTheMonth)}">
													<c_rt:set var="instcolor" scope="page" value="#F88017" />
												</c_rt:if>
											</c_rt:if>
										</c_rt:forEach>
										<table width='100%'> <tr> <td bgcolor="${instcolor}" >
											<html:link href="satbookMonthly.do?action=linkDay&day_selected=${(36 + sixthweek) - firstDayOfTheMonth}"><c_rt:out value="${(36 + sixthweek) - firstDayOfTheMonth}"/></html:link>
										</td></tr></table>
									</td>
								</c_rt:if>
								<c_rt:if test="${((36 + sixthweek) - firstDayOfTheMonth) > numberOfDaysInTheMonth}">
									<td></td>
								</c_rt:if>
						</c_rt:forEach>
						</c_rt:if>
					</c_rt:otherwise>
				</c_rt:choose>
			</tr>
		</c_rt:forEach>
	</sakai:group_table>

	<input type="button" value="Print" onClick="window.print()" />
	</html:form>
</sakai:html>
