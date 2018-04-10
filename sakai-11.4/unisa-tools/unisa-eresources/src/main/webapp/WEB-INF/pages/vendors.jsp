<%@ include file="/common/taglibs.jsp"%>
<head>    
	<title><fmt:message key="vendors.title"/></title>    
	<meta name="menu" content="vendorsMenu"/>
</head>
<div class="span10">  

	<h2><fmt:message key='allpages.description'/></h2>
	<h2><fmt:message key='vendor.heading'/></h2> 
	
	<title><fmt:message key="vendors.instruction"/></title>  
	<div id="actions" class="form-actions">        
		<a class="btn btn-primary" href="<c:url value='/vendorform'/>">            
			<i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>  
			      
		<a class="btn" onclick="deleletconfig()">            
			<i class="icon-ok"></i> <fmt:message key="button.delete"/></a>  
 
			
		<a class="btn" href="<c:url value='/mainMenu'/>">            
			<i class="icon-ok"></i> <fmt:message key="button.main"/></a>    
	</div> 
	
	<title><fmt:message key="vendors.instruction"/></title>  
	<h2>A B C D E F G H I J K L M N O P Q R S T U V W X Y N Z</h2>
	
	<table id="vendors" class="table table-condensed table-striped table-hover">
	
	 <display:table name="vendorList" class="table table-condensed table-striped table-hover" requestURI="" id="vendors" export="true" pagesize="25">
	       <display:column property="vendorId" sortable="true" media="html"  paramId="vendorId" paramProperty="vendorId" titleKey="vendors.id"/>
	       <display:column property="vendorId" media="csv excel xml pdf" titleKey="vendors.id"/>
	       <display:column property="vendorName" sortable="true" titleKey="vendor.description"/>
	       <display:column property="onCampusURL" sortable="true" titleKey="vendor.urlOnCampus"/>
	       <display:column property="offCampusURL" sortable="true" titleKey="vendor.urlOffCampus"/>
	       <display:column property="logoFile" sortable="true" titleKey="vendor.logoFilename"/>
	       <display:column property="logoURL" sortable="true" titleKey="vendor.url"/>
	       <display:column property="enabled" titleKey="vendor.isEnabled"/> 
	       
	       <display:column titleKey="check.name">
		    	<a id="checkboxlist"><input type="checkbox" name='${vendors.enabled}' id='+vendors.enabled+' value='${vendors.vendorId}'></a>
			</display:column>
		   <display:column titleKey="Edit">
		    	<a href="<c:url value="editVendors?vendorId=${vendors.vendorId}"/>">Edit</a>
			</display:column>
		   <display:setProperty name="paging.banner.item_name"><fmt:message key="vendorList.vendors"/></display:setProperty>
	       <display:setProperty name="paging.banner.items_name"><fmt:message key="vendorList.vendors"/></display:setProperty>        
	       <display:setProperty name="export.excel.filename"><fmt:message key="vendorList.title"/>.xls</display:setProperty>        
	       <display:setProperty name="export.csv.filename"><fmt:message key="vendorList.vendors"/>.csv</display:setProperty>        
	       <display:setProperty name="export.pdf.filename"><fmt:message key="vendorList.vendors"/>.pdf</display:setProperty> 
		   
	</display:table> 
	
	<div id="actions" class="form-actions">        
		<a class="btn btn-primary" href="<c:url value='/vendorform'/>">            
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
	    var selectedVendorsIDs  = chkArray.join(',') + ",";

	   //  alert(selectedSubjectIDs);
	

	    location.href = '/deleteVendors?selectedVendorsIDs=' + selectedVendorsIDs;

	}
function deleletconfig(){

	var chkArray = [];
	     
	    /* look for all checkboes that have a parent id called 'checkboxlist' attached to it and check if it was checked */
	    $("#checkboxlist input:checked").each(function() {
	        chkArray.push($(this).val());
	    });
	     
	    /* we join the array separated by the comma for the diplay*/
	    var selectedVendorsIDs  = chkArray.join(',') + ",";
	
		if(selectedVendorsIDs.length == 1)
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