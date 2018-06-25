
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- Sakai Themes -->
	<link href="<c:url value='/resources/css/tool_base.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/tool.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/portal.css' />" rel="stylesheet"  type="text/css" />

	<!-- Bootstrap core CSS -->
	<link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value='/resources/css/bootstrap-theme.css' />" rel="stylesheet"  type="text/css" />
	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />
	
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js' />"></script>  
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<style>
	   <!-- CSS code from Bootply.com editor -->
    	.equal, .equal > div[class*='col-'] {  
		    display: -webkit-flex;
		    display: flex;
		    flex:1 1 auto;
		}
		.panel {
	        /*height : 300px;*/
	        margin-bottom:1px;
	        position: relative;
	    }
	    .panel .panel-footer {
	        position : relative;
	        /*bottom : 0;*/
	        margin-bottom:1px;
	        width : 100%;
	    }
	    <!-- Bootply end -->
	    
		.light {
			opacity : 0.5;
		    filter: alpha(opacity=50);   /* For IE8 and earlier */
		}
		.stretch {
		    width: 100%;
		
		    padding: 0 5px;
		    /*border: 0;    */
		    /*margin: 0 -5px;  compensate horizontal padding and border-width with negative horizontal marfin */
		
		    /* small styling 
		    border-radius: 5px; 
		    box-shadow: 0 0 5px #555 inset;*/
		}
		#stretch {
		    width: 100%;
		    padding: 0 5px;
		}
		input[type="text"] {
			width: 100%;
			padding: 0 5px;
		}
	</style>
	
	<script type="text/javascript"> 

		//Call when document is ready
		$(document).ready(function() {
	
			$("input[value='Add']").click(function(){
	            $.blockUI({ message: '<h3><img src="/unisa-mdapplications/resources/images/indicator.gif" alt=" * " /> File upload in progress. Please wait...</h3>' });
	        }); 

		});
	
		function doCancel(){
			$.blockUI({ message: '<h3><img src="/unisa-mdapplications/resources/images/indicator.gif" alt=" * " /> Processing. Please wait...</h3>' });
			window.location.href = "mdapplications.do?action=walkthrough";
			return false;
		}
		
	</script>
	
</head>

<html:form action="/mdapplications.do" method="post" enctype="multipart/form-data">
	<html:hidden property="upload" value="upload"/>	
	<html:hidden property="page" value="upload"/>
	
	<input type="hidden" name="hidden" value="<%=request.getParameter("hidden") %>"/>
	
	<BR/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<sakai:messages/>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="md.add.file"/></h3>
				</div>
				<div class="panel-body clearfix">
					<sakai:instruction>
						<fmt:message key="md.file.instruction1"/><br/>
						<fmt:message key="md.file.instruction2"/><br/><br/>
					</sakai:instruction>
				
					<sakai:group_table>
						<tr>
							<td>
								<fmt:message key="md.upload.file"/>
							</td>
							<td>					
								<html:file name="mdApplicationsForm"  property="theFile"/>
							</td>
						</tr>
						
					</sakai:group_table>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:hidden name="mdApplicationsForm" property="cancel" value="messages"/>	
						<html:submit property="action"><fmt:message key="button.add"/></html:submit>
						<html:submit property="Cancel" value="Cancel" onclick="return doCancel();" />
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>
</html:form>
</sakai:html>