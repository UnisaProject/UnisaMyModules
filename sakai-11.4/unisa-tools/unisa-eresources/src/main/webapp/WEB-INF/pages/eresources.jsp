<%@ include file="/common/taglibs.jsp"%>
<head>    
	<title><fmt:message key="eresources.title"/></title>    
	<meta name="menu" content="eresourcesMenu"/>
</head>
<div class="span10">  

	<h3><fmt:message key='allpages.description'/></h3>
	<h3><fmt:message key='eresources.heading'/></h3> 
	<p>Subject Database/Eresources can be grouped by Placement OR vendor. just select a placement or vendor from the dropdown boxes</p>

	<div id="actions" class="form-actions">        
		<a class="btn btn-primary" href="<c:url value='eresourceformPage1'/>">            
			<i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>  
			      
		<a class="btn" onclick="deleletconfig()">            
			<i class="icon-ok"></i> <fmt:message key="button.delete"/></a>  
			
		<a class="btn" href="<c:url value='/mainMenu'/>">            
			<i class="icon-ok"></i> <fmt:message key="button.main"/></a>    
	</div> 
	
	<display:table name="eresourcesList" class="table table-condensed table-striped table-hover" requestURI="" id="eresources" export="true" pagesize="25">
	       <display:column property="eresourceId" sortable="true" media="html"  paramId="eresourceId" paramProperty="eresourceId" titleKey="eresources.id"/>
	       <display:column property="eresourceId" media="csv excel xml pdf" titleKey="eresources.id"/>
	       <display:column property="resourceName" sortable="true" titleKey="eresources.name"/>
	       <display:column property="vendor.vendorName" titleKey="eresources.vendor"/> 
	       
	       <display:column property="highlightNote.highlightNote" titleKey="eresources.placement"/> 
<%-- 	       <display:column property="subjects.subjectName" titleKey="eresources.placement"/>   --%> 
	       <display:column property="enabled" titleKey="eresources.enabled"/> 
	       <display:column titleKey="check.name">
		    	<a id="checkboxlist"><input type="checkbox" name='${eresources.enabled}' id='+eresources.enabled+' value='${eresources.eresourceId}'></a>
			</display:column>
			
			 <display:column titleKey="Edit">
		    	<a href="<c:url value="editEresources?eresourceId=${eresources.eresourceId}"/>">Edit</a>
			</display:column>
			
	       <%-- <<display:setProperty name="paging.banner.item_name"><fmt:message key="placementeList.placement"/></display:setProperty>
	       <display:setProperty name="paging.banner.items_name"><fmt:message key="placementeList.placement"/></display:setProperty>        
	       <display:setProperty name="export.excel.filename"><fmt:message key="contentTypeList.title"/>.xls</display:setProperty>        
	       <display:setProperty name="export.csv.filename"><fmt:message key="placementeList.placement"/>.csv</display:setProperty>        
	       <display:setProperty name="export.pdf.filename"><fmt:message key="placementeList.placement"/>.pdf</display:setProperty> --%>
	</display:table>

		<div id="actions" class="form-actions">        
		<a class="btn btn-primary" href="<c:url value='eresourceformPage1'/>">            
			<i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>  
			      
		<a class="btn" onclick="deleletconfig()">            
			<i class="icon-ok"></i> <fmt:message key="button.delete"/></a>  
			
		<a class="btn" href="<c:url value='/mainMenu'/>">            
			<i class="icon-ok"></i> <fmt:message key="button.main"/></a>    
	</div> 
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.min.js" type="text/javascript"></script>
<script>

function getValueUsingParentTag(){
	
	    var chkArray = [];
	     
	    /* look for all checkboes that have a parent id called 'checkboxlist' attached to it and check if it was checked */
	    $("#checkboxlist input:checked").each(function() {
	        chkArray.push($(this).val());
	    });
	     
	    /* we join the array separated by the comma for the diplay*/
	    var selectedSubjectIDs  = chkArray.join(',') + ",";

	   //  alert(selectedSubjectIDs);
	

	    location.href = '/deleteEresources?selectedSubjectIDs=' + selectedSubjectIDs;

	}
function deleletconfig(){

	var chkArray = [];
	     
	    /* look for all checkboes that have a parent id called 'checkboxlist' attached to it and check if it was checked */
	    $("#checkboxlist input:checked").each(function() {
	        chkArray.push($(this).val());
	    });
	     
	    /* we join the array separated by the comma for the diplay*/
	    var selectedSubjectIDs  = chkArray.join(',') + ",";
	
		if(selectedSubjectIDs.length == 1)
		{
			alert("Please select record(s) to be deleted");
		}
		else
		{
			var del=confirm("Are you sure you want to delete this record(s)?");
			if (del==true){
				getValueUsingParentTag();
			}else{
			    alert("Record(s) not deleted")
			}
			return del;
		}

}
window.setInterval(function(){
	disableCheckbox();
	}, 50);
function disableCheckbox(){
	var inUse = document.getElementsByName("true")[0].id;
	for (var i = 0; i < inUse.length; i++) {
		 
		 document.getElementsByName('true')[i].disabled = true;
	}
}
</script>