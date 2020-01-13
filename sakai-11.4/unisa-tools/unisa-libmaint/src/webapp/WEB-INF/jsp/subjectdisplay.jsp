<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="subject"/>
	
	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="main.heading1"/><fmt:message key="subj.heading"/>
	</sakai:group_heading>
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<p>
	<sakai:instruction><fmt:message key="info1"/></sakai:instruction>
	
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
	</p>
		<p>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=a">
		a
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=b">
		b
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=c">
		c
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=d">
		d
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=e">
		e
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=f">
		f
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=g">
		g
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=h">
		h
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=i">
		i
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=j">
		j
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=k">
		k
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=l">
		l
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=m">
		m
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=n">
		n
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=o">
		o
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=p">
		p
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=q">
		q
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=r">
		r
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=s">
		s
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=t">
		t
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=u">
		u
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=v">
		v
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=w">
		w
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=x">
		x
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=y">
		y
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=z">
		z
	</html:link>
	</P>
	<sakai:flat_list>
		<tr>
			<th></th>
			<th><fmt:message key="id"/></th>
			<th><fmt:message key="subj.subject"/></th>
			<th><fmt:message key="enabled"/></th>
		</tr>
		<logic:notEmpty name="maintenanceForm" property="dataList">
			<logic:iterate name="maintenanceForm" property="dataList" id="record" indexId="i">
				<tr>
					<td><html:checkbox name="maintenanceForm" property='<%= "recordIndexed["+i+"].checked" %>'/></td>
					<td><bean:write name="record" property="subjectId"/></td>
					<td><bean:write name="record" property="subject"/></td>
					<td><bean:write name="record" property="enabled"/></td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</sakai:flat_list>
	
			<p>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=a">
		a
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=b">
		b
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=c">
		c
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=d">
		d
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=e">
		e
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=f">
		f
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=g">
		g
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=h">
		h
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=i">
		i
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=j">
		j
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=k">
		k
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=l">
		l
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=m">
		m
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=n">
		n
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=o">
		o
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=p">
		p
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=q">
		q
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=r">
		r
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=s">
		s
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=t">
		t
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=u">
		u
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=v">
		v
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=w">
		w
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=x">
		x
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=y">
		y
	</html:link>
	<html:link href="maintenance.do?act=subjectDisplay&alpha=z">
		z
	</html:link>
	</P>
	
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