<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="newsletter.title" /></title>
<meta name="menu" content="newsLetterMenu" />
</head>
<div class="span10">

	<h2>
		<fmt:message key='allpages.description' />
	</h2>
	<h2>
		<fmt:message key='newsLetter.heading' />
	</h2>


	<div id="actions" class="form-actions">
		<a class="btn btn-primary" href="<c:url value='/newsletterform'/>">
			<i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
		</a> <a class="btn" onclick="deleletconfig()"> <i
			class="icon-ok"></i> <fmt:message key="button.delete" />
			</a><a class="btn"
			href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i>
			<fmt:message key="button.main" /></a>
	</div>

	<display:table name="newsletterList" class="table table-condensed table-striped table-hover" requestURI="" id="newsLetter" export="true" pagesize="25">
		<display:column property="newsTitleID" sortable="true" media="html" paramId="newsTitleID" paramProperty="newsTitleID" titleKey="newsLetter.id" />
		<display:column property="newsTitleID" media="csv excel xml pdf" titleKey="newsLetter.id" />
		<display:column property="newsTitle" sortable="true" titleKey="newsLetter.title" />
		<display:column property="enabled" titleKey="newsLetter.isEnabled" />
		
		<display:column titleKey="check.name">
		    	<a id="checkboxlist"><input type="checkbox" name='${newsLetter.enabled}' id='+newsLetter.enabled+' value='${newsLetter.newsTitleID}'></a>
			</display:column>
			
		   <display:column titleKey="Edit">
		    	<a href="<c:url value="editNewsLetterHeading?newsTitleID=${newsLetter.newsTitleID}"/>">Edit</a>
			</display:column>
		   <display:setProperty name="paging.banner.item_name"><fmt:message key="newsLetterList.newsletter"/></display:setProperty>
	       <display:setProperty name="paging.banner.items_name"><fmt:message key="newsLetterList.newsletter"/></display:setProperty>        
	       <display:setProperty name="export.excel.filename"><fmt:message key="newsLetterList.title"/>.xls</display:setProperty>        
	       <display:setProperty name="export.csv.filename"><fmt:message key="newsLetterList.newsletter"/>.csv</display:setProperty>        
	       <display:setProperty name="export.pdf.filename"><fmt:message key="newsLetterList.newsletter"/>.pdf</display:setProperty>
	</display:table>

	<div id="actions" class="form-actions">
		<a class="btn btn-primary" href="<c:url value='/newsletterform'/>">
			<i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
		</a> <a class="btn" onclick="deleletconfig()"> <i
			class="icon-ok"></i> <fmt:message key="button.delete" /></a>
			<a class="btn"
			href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i>
			<fmt:message key="button.main" /></a>
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
	    var selectedNewsLetterIDs  = chkArray.join(',') + ",";

	   //  alert(selectedSubjectIDs);
	

	    location.href = '/deleteNewsLetters?selectedNewsLetterIDs=' + selectedNewsLetterIDs;

	}
function deleletconfig(){

	var chkArray = [];
	     
	    /* look for all checkboes that have a parent id called 'checkboxlist' attached to it and check if it was checked */
	    $("#checkboxlist input:checked").each(function() {
	        chkArray.push($(this).val());
	    });
	     
	    /* we join the array separated by the comma for the diplay*/
	    var selectedNewsLetterIDs  = chkArray.join(',') + ",";
	
		if(selectedNewsLetterIDs.length == 1)
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
