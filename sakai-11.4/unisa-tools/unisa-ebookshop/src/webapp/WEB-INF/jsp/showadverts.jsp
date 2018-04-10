<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.ebookshop.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:tool_bar>
        <html:link href="eshop.do?action=addAdvert">
            <fmt:message key="eshop.showadverts.toplink"/>
        </html:link>
    </sakai:tool_bar>

	<sakai:heading>
	<fmt:message key="eshop.showadverts.title"/>
	</sakai:heading>
	<sakai:instruction>
		<fmt:message key="eshop.showadverts.description"/>
	</sakai:instruction>

	<sakai:flat_list>
		<tr>
			<td>
				<html:form action="eshop.do?action=showAdverts">
					<b><fmt:message key="eshop.showadverts.view"/></b>
					<html:select property="orderBy" onchange="submit()">
						<html:option value="COURSE_CODE">Arrange by Course Code</html:option>
						<html:option value="CATCHPHRASE">Arrange by Heading</html:option>
						<html:option value="SUBMIT_DATE">Arrange by Date Added</html:option>
					</html:select>
				</html:form>
			</td>
		</tr>
		<logic:iterate id="advertlist" name="advertlist">
		<tr>
			<td>
				<c_rt:choose>
					<c_rt:when test="${eshopform.orderBy != 'SUBMIT_DATE'}">
						<c_rt:if test="${eshopform.orderBy == 'CATCHPHRASE'}">
						<!--  (<c_rt:out value="${advertlist.courseDescription}"/>),&nbsp; -->
							<b><c_rt:out value="${advertlist.addHeading}"/></b>, &nbsp;<c_rt:out value="${advertlist.courseCode}"/>&nbsp; <html:link action="eshop.do?action=reviseLink" paramId="bookId" paramName="advertlist" paramProperty="bookId">Revise</html:link>
						</c_rt:if>
						<c_rt:if test="${eshopform.orderBy == 'COURSE_CODE'}">
							<c_rt:out value="${advertlist.addHeading}"/>, &nbsp;<b><c_rt:out value="${advertlist.courseCode}"/></b>&nbsp; <html:link action="eshop.do?action=reviseLink" paramId="bookId" paramName="advertlist" paramProperty="bookId">Revise</html:link>
						</c_rt:if>
					</c_rt:when>
					<c_rt:otherwise>
						<c_rt:out value="${advertlist.addHeading}"/>, &nbsp;<c_rt:out value="${advertlist.courseCode}"/>&nbsp; <html:link action="eshop.do?action=reviseLink" paramId="bookId" paramName="advertlist" paramProperty="bookId">Revise</html:link>
					</c_rt:otherwise>
				</c_rt:choose>
			</td>
		</tr>
		<tr>
			<td>
				<font size="-2"><c_rt:out value="${advertlist.addText}"/></font>
			</td>
		</tr>
		<tr>
			<td>
				<font size="-2"><c_rt:out value="${advertlist.contactDetails}"/></font>
			</td>
		</tr>
		<tr>
			<td><font size="-2">
				<c_rt:if test="${eshopform.orderBy == 'SUBMIT_DATE'}">
					<b><fmt:message key="eshop.showadverts.added"/>&nbsp;<c_rt:out value="${advertlist.dateAdded}"/></b>
				</c_rt:if>
				<c_rt:if test="${eshopform.orderBy != 'SUBMIT_DATE'}">
					<fmt:message key="eshop.showadverts.added"/>&nbsp;<c_rt:out value="${advertlist.dateAdded}"/>
				</c_rt:if>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		</logic:iterate>
	</sakai:flat_list>
</sakai:html>