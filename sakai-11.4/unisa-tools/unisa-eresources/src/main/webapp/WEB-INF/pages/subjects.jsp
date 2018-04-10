<%@ include file="/common/taglibs.jsp"%>
<head>    
	<title><fmt:message key="subjects.title"/></title>    
	<meta name="menu" content="subjectsMenu"/>
</head>
<div class="span10">  

	<h3><fmt:message key='allpages.description'/></h3>
	<h3><fmt:message key='subjects.heading'/></h3> 
	
	<div id="actions" class="form-actions">        
		<a class="btn btn-primary" href="<c:url value='/subjectform'/>">            
			<i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>  
			      
		<a class="btn" onclick="deleletconfig()">            
			<i class="icon-ok"></i> <fmt:message key="button.delete"/></a>   
			
		<a class="btn" href="<c:url value='/mainMenu'/>">            
			<i class="icon-ok"></i> <fmt:message key="button.main"/></a>    
	</div> 
	
	<h5>A B C D E F G H I J K L M N O P Q R S T U V W X Y N Z</h5>
	   
	<display:table name="subjectsList" class="table table-condensed table-striped table-hover" requestURI="" id="subjects" export="true" pagesize="25">
	       <display:column property="subjectId" sortable="true" media="html"  paramId="subjectId" paramProperty="subjectId" titleKey="subjects.id"/>
	       <display:column property="subjectId" media="csv excel xml pdf" titleKey="subjects.id"/>
	       <display:column property="subjectName" sortable="true" titleKey="subjects.description"/>
	       <display:column property="enabled" titleKey="subjects.isEnabled"/> 
	       
	       <display:column titleKey="check.name">
		    	<a id="checkboxlist"><input type="checkbox" name='${subjects.enabled}' id='+subjects.enabled+' value='${subjects.subjectId}'></a>
			</display:column>
			
		   <display:column titleKey="Edit">
		    	<a href="<c:url value="editSubject?subjectId=${subjects.subjectId}"/>">Edit</a>
			</display:column>
		   <display:setProperty name="paging.banner.item_name"><fmt:message key="subjectList.subjects"/></display:setProperty>
	       <display:setProperty name="paging.banner.items_name"><fmt:message key="subjectList.subjects"/></display:setProperty>        
	       <display:setProperty name="export.excel.filename"><fmt:message key="subjectList.title"/>.xls</display:setProperty>        
	       <display:setProperty name="export.csv.filename"><fmt:message key="subjectList.subjects"/>.csv</display:setProperty>        
	       <display:setProperty name="export.pdf.filename"><fmt:message key="subjectList.subjects"/>.pdf</display:setProperty>
		   
	</display:table>
	
	<div id="actions" class="form-actions">        
		<a class="btn btn-primary" href="<c:url value='/subjectform'/>">            
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
	    var selectedSubjectsIDs  = chkArray.join(',') + ",";

	   //  alert(selectedSubjectIDs);
	

	    location.href = '/deleteSubjects?selectedSubjectsIDs=' + selectedSubjectsIDs;

	}
function deleletconfig(){

	var chkArray = [];
	     
	    /* look for all checkboes that have a parent id called 'checkboxlist' attached to it and check if it was checked */
	    $("#checkboxlist input:checked").each(function() {
	        chkArray.push($(this).val());
	    });
	     
	    /* we join the array separated by the comma for the diplay*/
	    var selectedSubjectsIDs  = chkArray.join(',') + ",";
	
		if(selectedSubjectsIDs.length == 1)
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