<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<script language="JavaScript">
	function setAction() {
		document.maintenanceForm.action = 'maintenance.do?act=resourceDisplay';
		document.maintenanceForm.submit();
	}
	function setActionPerVendor() {
		document.maintenanceForm.action = 'maintenance.do?act=resourceDisplayPerVendor';
		document.maintenanceForm.submit();
	}
</script>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="resource"/>
	
	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="main.heading1"/><fmt:message key="res.heading"/>
	</sakai:group_heading>
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>

	<p>
	<sakai:instruction><fmt:message key="res.info1"/></sakai:instruction>
	</p>
	
	<p>
	<sakai:flat_list>
		<tr>
			<td>
				<fmt:message key="res.heading"/> <fmt:message key="res.perplacement"/> 
			</td>
			<td>
				<html:select property="resourcesPerPlacement" onchange="setAction();">
					<html:options collection="placementOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="res.heading"/> <fmt:message key="res.pervendor"/> <br>
			</td>
			<td>
				<html:select property="resourcesPerVendor" onchange="setActionPerVendor();">
					<html:options collection="vendorOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
	</sakai:flat_list>
	</p>
	<p>
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
			<fmt:message key="button.view"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.editdates"/> 
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.back"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.update"/>
		</html:submit>
	</sakai:actions>
	</p>
			
	<p>
	<table><tr><td>
	<font size=2>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=a">
		a
	</html:link> &nbsp;
	<html:link href="maintenance.do?act=resourceDisplay&alpha=b">
		b
	</html:link>&nbsp;
	<html:link href="maintenance.do?act=resourceDisplay&alpha=c">
		c
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=d">
		d
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=e">
		e
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=f">
		f
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=g">
		g
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=h">
		h
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=i">
		i
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=j">
		j
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=k">
		k
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=l">
		l
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=m">
		m
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=n">
		n
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=o">
		o
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=p">
		p
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=q">
		q
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=r">
		r
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=s">
		s
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=t">
		t
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=u">
		u
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=v">
		v
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=w">
		w
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=x">
		x
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=y">
		y
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=z">
		z
	</html:link>
	</font>
	</td></tr></table>
	</P>
	
	<p>
	<sakai:instruction><fmt:message key="info1"/></sakai:instruction>
	</p>
	
	<sakai:flat_list>
		<tr>
			<th></th>
			<th><fmt:message key="id"/></th>
			<th><fmt:message key="res.subj.db"/></th>
			<th><fmt:message key="vendor"/></th>
			<th><fmt:message key="plc.placement"/></th>
			<th><fmt:message key="enabled"/></th>
		</tr>
		<logic:notEmpty name="maintenanceForm" property="dataList">
			<logic:iterate name="maintenanceForm" property="dataList" id="record" indexId="i">
				<tr>
					<td><html:checkbox name="maintenanceForm" property='<%= "recordIndexed["+i+"].checked" %>'/></td>
					<td><bean:write name="record" property="resourceId"/></td>
					<td><bean:write name="record" property="resourceName"/></td>
					<td><bean:write name="record" property="vendorDesc"/></td>
					<td>
						<logic:notEmpty name="record" property="placements">
							<logic:iterate name="record" property="placements" id="record2" indexId="j">
								<bean:write name="record2" property="label"/> <br>
							</logic:iterate>
						</logic:notEmpty>
					</td>
					<td><bean:write name="record" property="enabled"/></td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</sakai:flat_list>
	
				<p>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=a">
		a
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=b">
		b
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=c">
		c
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=d">
		d
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=e">
		e
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=f">
		f
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=g">
		g
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=h">
		h
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=i">
		i
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=j">
		j
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=k">
		k
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=l">
		l
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=m">
		m
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=n">
		n
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=o">
		o
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=p">
		p
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=q">
		q
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=r">
		r
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=s">
		s
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=t">
		t
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=u">
		u
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=v">
		v
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=w">
		w
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=x">
		x
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=y">
		y
	</html:link>
	<html:link href="maintenance.do?act=resourceDisplay&alpha=z">
		z
	</html:link>
	</P>
	
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