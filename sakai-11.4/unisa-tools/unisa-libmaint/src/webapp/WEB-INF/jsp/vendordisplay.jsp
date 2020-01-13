<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="vendor"/>
	
	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="main.heading1"/><fmt:message key="vendor.heading"/>
	</sakai:group_heading>
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<p>
	<sakai:instruction><fmt:message key="info1"/></sakai:instruction>
	<p>
		</p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.add"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.remove"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.edit"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.back"/>
		</html:submit>
	</sakai:actions>
	<p>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=a">
		a
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=b">
		b
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=c">
		c
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=d">
		d
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=e">
		e
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=f">
		f
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=g">
		g
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=h">
		h
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=i">
		i
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=j">
		j
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=k">
		k
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=l">
		l
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=m">
		m
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=n">
		n
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=o">
		o
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=p">
		p
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=q">
		q
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=r">
		r
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=s">
		s
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=t">
		t
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=u">
		u
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=v">
		v
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=w">
		w
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=x">
		x
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=y">
		y
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=z">
		z
	</html:link>
	
	<P>
	<sakai:flat_list>
		<tr>
			<th><fmt:message key="remove"/></th>
			<th><fmt:message key="id"/></th>
			<th><fmt:message key="vendor"/></th>
			<th><fmt:message key="vendor.url"/></th>
			<th>&nbsp;</th>
		</tr>
		<logic:notEmpty name="maintenanceForm" property="dataList">
			<logic:iterate name="maintenanceForm" property="dataList" id="record" indexId="i">
				<tr>
					<td><html:checkbox name="maintenanceForm" property='<%= "recordIndexed["+i+"].checked" %>'/></td>
					<td><bean:write name="record" property="vendorId"/></td>
					<td><bean:write name="record" property="vendor"/></td>
					<td>
						<table>
							<tr><td><fmt:message key="url.oncampus"/>:</td><td> <a href="${record.onCampusURL}" target="_blank"><bean:write name="record" property="onCampusURL"/> </td></tr>
							<tr><td><fmt:message key="url.offcampus"/>:</td><td><a href="${record.offCampusURL}" target="_blank"><bean:write name="record" property="offCampusURL"/></td></tr>
							<tr><td><fmt:message key="logo"/>:</td><td><fmt:message key="logo.url"/> <bean:write name="record" property="logoURL"/><bean:write name="record" property="logoFileName"/></td></tr>
							<tr><td><fmt:message key="vendor.logo"/>:</td><td><bean:write name="record" property="logoFileName"/></td></tr>
							<tr><td><fmt:message key="enabled"/>:</td><td><bean:write name="record" property="enabled"/></td></tr> 
						</table>
					</td>
					<td>
						<logic:notEmpty name="record" property="logoFileName">				
							<img name=record src="${record.fullLogoURL}"/>
						</logic:notEmpty>
					</td>
				</tr>
				<tr><td colspan=5><hr></hr></td></tr>
			</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="maintenanceForm" property="dataList">
			<tr> <td colspan='6'>
				<fmt:message key="nodata"/>
			</td></tr>
		</logic:empty>
	</sakai:flat_list>
	
		<p>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=a">
		a
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=b">
		b
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=c">
		c
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=d">
		d
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=e">
		e
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=f">
		f
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=g">
		g
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=h">
		h
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=i">
		i
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=j">
		j
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=k">
		k
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=l">
		l
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=m">
		m
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=n">
		n
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=o">
		o
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=p">
		p
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=q">
		q
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=r">
		r
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=s">
		s
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=t">
		t
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=u">
		u
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=v">
		v
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=w">
		w
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=x">
		x
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=y">
		y
	</html:link>
	<html:link href="maintenance.do?act=vendorDisplay&alpha=z">
		z
	</html:link>
	
	</p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.add"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.remove"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.edit"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.back"/>
		</html:submit>
	</sakai:actions>
	
</html:form>
</sakai:html>