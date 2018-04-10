<%@ include file="/common/taglibs.jsp"%>
<head>    
	<title><fmt:message key="eresources.title"/></title>    
	<meta name="menu" content="eresourcesMenu"/>
</head>
<div class="span10">  

	<h3><fmt:message key='allpages.description'/></h3>
	<h3><fmt:message key='eresources.heading'/></h3> 
	<p>Subject Database/Eresources can be grouped by Placement OR vendor. just select a placement or vendor from the dropdown boxes</p>
	
	<%-- <div id="actions" class="form-actions">  
	 <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.Vendor"/>
        <div class="controls">
            <form:select path="vendor.vendorName" items="${vendorList}" />
            <form:errors path="vendor.vendorName" cssClass="help-inline"/>           
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.Vendor"/>
        <div class="controls">
            <form:select path="vendor.vendorName" items="${vendorList}" />
            <form:errors path="vendor.vendorName" cssClass="help-inline"/>           
        </div>
    </div>
	</div> --%>
	<div id="actions" class="form-actions">        
		<a class="btn btn-primary" href="<c:url value='/addEresourcePage1'/>">            
			<i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>  
			      
		<a class="btn" href="<c:url value='/delete_eresources'/>">            
			<i class="icon-ok"></i> <fmt:message key="button.delete"/></a>  
			
		<a class="btn" href="<c:url value='/edit_eresources'/>">            
			<i class="icon-ok"></i> <fmt:message key="button.edit"/></a>  
			
		<a class="btn" href="<c:url value='/mainMenu'/>">            
			<i class="icon-ok"></i> <fmt:message key="button.main"/></a>    
	</div> 
	   
	<display:table name="eresourcesList" class="table table-condensed table-striped table-hover" requestURI="" id="eresources" export="true" pagesize="25">
	       <display:column property="eresourceId" sortable="true" href="eresourcesForm" media="html"  paramId="eresourceId" paramProperty="eresourceId" titleKey="eresources.id"/>
	       <display:column property="eresourceId" media="csv excel xml pdf" titleKey="eresources.id"/>
	       <display:column property="resourceName" sortable="true" titleKey="eresources.name"/>
	       <display:column property="vendor.vendorName" titleKey="eresources.vendor"/> 
	       
	       <display:column property="highlightNote.highlightNote" titleKey="eresources.placement"/> 
<%-- 	       <display:column property="subjects.subjectName" titleKey="eresources.placement"/>   --%> 
	       <display:column property="mobileAccessAvailability" titleKey="eresources.enabled"/> 
	       <display:column titleKey="check.name">
		    	<input type="checkbox" name="checklist"> <c:out value="${eresourceId}"></c:out>
			</display:column>
	       <%-- <<display:setProperty name="paging.banner.item_name"><fmt:message key="placementeList.placement"/></display:setProperty>
	       <display:setProperty name="paging.banner.items_name"><fmt:message key="placementeList.placement"/></display:setProperty>        
	       <display:setProperty name="export.excel.filename"><fmt:message key="contentTypeList.title"/>.xls</display:setProperty>        
	       <display:setProperty name="export.csv.filename"><fmt:message key="placementeList.placement"/>.csv</display:setProperty>        
	       <display:setProperty name="export.pdf.filename"><fmt:message key="placementeList.placement"/>.pdf</display:setProperty> --%>
	</display:table>
	
		<div id="actions" class="form-actions">        
		<a class="btn btn-primary" href="<c:url value='/addEresourcePage1'/>">            
			<i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>  
			      
		<a class="btn" href="<c:url value='/delete_eresources'/>">            
			<i class="icon-ok"></i> <fmt:message key="button.delete"/></a>  
			
		<a class="btn" href="<c:url value='/edit_eresources'/>">            
			<i class="icon-ok"></i> <fmt:message key="button.edit"/></a>  
			
		<a class="btn" href="<c:url value='/mainMenu'/>">            
			<i class="icon-ok"></i> <fmt:message key="button.main"/></a>    
	</div> 
	
</div>