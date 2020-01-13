 <!DOCTYPE html>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ page import="java.util.*" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tracking.ApplicationResources"/>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset='utf-8'>
    <meta content='maximum-scale=1.0, initial-scale=1.0, width=device-width' name='viewport'>
    <meta name="description" content="">
    <meta name="author" content="">

	<!-- Sakai CSS -->
	<link href="/library/skin/tool_base.css" type="text/css" rel="stylesheet" media="all" />
	<link href="/library/skin/neo-unisa-fixed/tool.css" type="text/css" rel="stylesheet" media="all" />
	<!-- Bootstrap core CSS -->
	<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value="/resources/css/bootstrap-theme.min.css" />" rel="stylesheet"  type="text/css" />


  <style type="text/css">
    	 body {
	        width: 100%;
	        height: 100%;
	        margin: 0;
	        padding: 0;
	        background-color: #FAFAFA;
	        font: 12pt "Tahoma";
	    }
	    * {
	        box-sizing: border-box;
	        -moz-box-sizing: border-box;
	    }
	    .page {
	        width: 210mm;
	        min-height: 297mm;
	        padding: 20mm;
	        margin: 10mm auto;
	        border: 1px #D3D3D3 solid;
	        border-radius: 5px;
	        background: white;
	        box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
	    }
		@media print {
			html, body {
    	        width: 210mm;
        	    height: 297mm;        
        	}
		    .panel-heading {
		        display:none
		    }
		    .noPrint{
		    	display:none
		    }
        	.page {
            	margin: 0;
	            border: initial;
	            border-radius: initial;
	            width: initial;
	            min-height: initial;
	            box-shadow: initial;
	            background: initial;
	            page-break-after: always;
	        }
	    }
		@page {
	        /*size: A4;*/
	        margin: 0;
	    }
    	.equal, .equal > div[class*='col-'] {  
		    display: -webkit-flex;
		    display: flex;
		    flex:1 1 auto;
		}
		
		.panel {
	        /*height : 300px;*/
	        margin-bottom:0;
	        position: relative;
	    }
	
	    .panel .panel-footer {
	        position : absolute;
	        bottom : 0;
	        margin-bottom:1px;
	        width : 100%;
	    }
	    .paddRight{
	    	padding-right: 60px; /*used to align list with checkboxes */
	    }
	    .paddRightSmall{
	    	padding-right: 5px; /*used to align list with checkboxes */
	    }
	    .topAlign{
	    	vertical-align:top;
	    	/*text-align:center;*/
	    }
	    
	    #activity {  
	        text-align:center;border:1px solid #ccc;  
    	}  
    	#activity td{  
        	text-align:center;border:1px solid #ccc;  
    	}  
     	#footerExport td{  
	       	cursor:pointer;  
    		text-align:center;border:1px solid #ccc;  
       		border:none;  
    	}  

  </style>
	
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
	
	<!--  PDF -->
	<script type='text/javascript' src="<c:url value="/resources/js/pdfmake.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/pdfmake.min.js" />"></script>
 	<script type="text/javascript" src="<c:url value="/resources/js/vfs_fonts.js" />"></script>
 	
 	<!-- Browser Version -->
 	<script type="text/javascript" src="<c:url value='/resources/js/ua-parser.js' />"></script>
 	
	<script type="text/javascript" lang="JavaScript">
		
    $(document).ready(function(){  
    	
    	//If IE, Reset main tab to Main Menu.
		//Detect for IE and Edge
		var parser = new UAParser();
		var bName = parser.getResult().browser.name;
		var bVersion = parser.getResult().browser.version;
    	//Reset Main window to Main Menu so user cannot return to previous session.
    	if (bName === "IE"){
    		window.opener.location.href = "tracking.do";
    		$("#closeButton").prop('value', 'CLOSE');
    	}else{
    		$("#closeButton").prop('value', 'BACK');
    	}
    	
    	var docDefinition = {
    			pageSize: 'A4',
    			pageOrientation: 'portrait',
    			pageMargins: [ 40, 40, 40, 40 ],
    			content: [
    				{text: 'No Data was availble for the PDF', fontSize: 22, bold: true}
    			]
    	};
    	
    	// open the PDF in a new window
    	$('#printPDFButton').click(function () {
    		var reportErr = false;
    		var url = 'tracking.do?act=reportPDF';
    		$.ajaxSetup( { "async": false } );
			$.getJSON(url, function(data) {
				cache : false,
				$.each(data, function(key, val) {
					//alert("Report Key="+key);
					if (key === "Error"){
						showError("Error", val);
						reportErr = true;
						return false;
					}else if (key === "Empty"){
						showError("Empty", val);
						reportErr = true;
						return false;
					}else if (key === "Report"){
						//alert("Report="+key+": Data="+val);
						docDefinition = eval("(" + val + ")");
					}else{
						showError("Error", "An error occured while retrieving the PDF information.<BR>Please try again or reeport the problem if it continues.");
						reportErr = true;
						return false;
					}
				});
			});
			$.ajaxSetup( { "async": true } );
			if (!reportErr){
				var shipListNo = $('input[name=shipListNo]').val();
				var shipListDate = new Date();
				var dMonth = shipListDate.getMonth()+1; 
					if (dMonth < 10){
						dMonth = '0' + dMonth;
					}
				var dDate = shipListDate.getDate();
					if (dDate < 10){
						dDate = '0' + dDate;
					}
				var dYear = shipListDate.getYear();
				var dHour = shipListDate.getHours();
					if (dHour < 10){
						dHour = '0' + dHour;
					}
				//var dHour = ((shipListDate.getHours()+1)<12 ? shipListDate.getHours() : shipListDate()-12);
				var dMinutes = shipListDate.getMinutes();
					if (dMinutes < 10){
						dMinutes = '0' + dMinutes;
					}
				//var dM = ((shipListDate.getHours()+1)<12 ? 'AM' : 'PM');
				
				//Concatenate Date and Time for use in PDF File Name
				var shipListDate2 = dYear.toString()+dMonth.toString()+dDate.toString()+'_'+dHour.toString()+dMinutes.toString(); //+' '+dM
				var newFileName = "Unisa_Tracking_("+shipListNo+")_"+shipListDate2+".pdf";
	
				//Use Download instead of Open. Open doesn't work in IE.
				pdfMake.createPdf(docDefinition).download(newFileName);
			}else{
				alert("There was an error - No PDF will be produced. Please try again.");
			}
    	});
        
        /*Normal Print*/
        $('#printButton').click(function () {
		    print();
		});
        
        /*Close Tab/Window*/
        $('#closeButton').click(function () {
        	//For IE we close the current tab, for Chrome or FF, we just go back.
        	if (bName === "IE"){
        		window.close();
        	}else{
	        	var url="tracking.do";
	        	window.location.href =url;
        	}
		});
        
     });
         
	function showError(errorTitle, errorText) {

	    // show the actual error modal
	    $.unblockUI();		    
	    $('#dialogContent').html(errorText);
		$('#dialogHolder').dialog({
		  autoOpen: true,
		  title: errorTitle,
		  modal: true,
		  buttons: {
		    "Ok": function() {
		      $(this).dialog("close");
		    }
		  }
		});
	}
	
	    function setAction() {
			document.trackingForm.action = 'tracking.do?act=report';
			document.trackingForm.submit();
		}		
	
		function frmSubmitSameWin(){
			document.trackingForm.target = '';
		}
		
            
	</script>
</head>
	<html:form action="/tracking" >
    <input type='hidden' id='prevID' value='DontDelete' />
    <input type='hidden' id='shipListNo' name='shipListNo' value='<bean:write name="trackingForm" property="displayShipListNumber"/>' />

<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

<div id="content" class="page">
	<!--panel-->
	    <logic:notEmpty property="displayShipListDate" name="trackingForm">
			<div class="panel panel-default">
		  		<div class="panel-body" style="padding-top:0px !important; padding-bottom:0px !important">
			  		<div class="table-responsive">
						<table style="width:100%">
							<tr>
								<td style="width:100%">
									<logic:equal name="trackingForm" property="userSelection" value="in">
										<table style="width:100%">
											<tr>
												<td style="width:50%">
													<h3>RECEIPT <bean:write name="trackingForm" property="displayShipListNumber"/></h3>
												</td>
												<td align="right">
													<img src='<c:url value="/resources/images/UNISA Logo CMYK_Small.jpg" />' alt='Unisa' style='width: 60%; height: 60%' />
												</td>
											</tr>
										</table>
									</logic:equal>
									<logic:notEqual name="trackingForm" property="userSelection" value="in">
										<table style="width:100%">
											<tr>
												<td style="width:50%">
													<h3>CONSIGNMENT LIST <bean:write name="trackingForm" property="displayShipListNumber"/></h3>
												</td>
												<td align="right">
													<img src='<c:url value="/resources/images/UNISA Logo CMYK_Small.jpg" />' alt='Unisa' style='width: 60%; height: 60%' />   
												</td>
											</tr>
										</table>
									</logic:notEqual>
								</td>
							</tr><tr>
								<td>
									Booked&nbsp;<bean:write name="trackingForm" property="userSelection"/>&nbsp;on&nbsp;<bean:write name="trackingForm" property="displayShipListDate"/>
								</td>
							</tr><tr>
								<td>
									By User:&nbsp;<bean:write name="trackingForm" property="novelUserId"/>
								</td>
							</tr><tr>
								<td>&nbsp;</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<logic:notEmpty property="displayAddress1" name="trackingForm">
				<logic:notEqual name="trackingForm" property="displayAddress1" value=" ">
					<div class="panel panel-default">
				  		<div class="panel-body" style="padding-top:0px !important; padding-bottom:0px !important">
					  		<div class="table-responsive">
								<table style="width:100%">			
										<tr>
											<td colspan="2">&nbsp;</td>
										</tr><tr>
											<td colspan="2" style="width:100%">
												<table style="width:100%">
													<tr>
														<td colspan="2" valign="top">Destination Address:&nbsp;&nbsp;<td>
													</tr><tr>
														<td>
															<table style="width:100%">
																<logic:notEmpty property="displayAddress1" name="trackingForm">
																	<tr><td><bean:write name="trackingForm" property="displayAddress1"/></td></tr>
																</logic:notEmpty>
																<logic:notEmpty property="displayAddress2" name="trackingForm">
																	<tr><td><bean:write name="trackingForm" property="displayAddress2"/></td></tr>
																</logic:notEmpty>
																<logic:notEmpty property="displayAddress3" name="trackingForm">
																	<tr><td><bean:write name="trackingForm" property="displayAddress3"/></td></tr>
																</logic:notEmpty>
																<logic:notEmpty property="displayAddress4" name="trackingForm">
																	<tr><td><bean:write name="trackingForm" property="displayAddress4"/></td></tr>
																</logic:notEmpty>
																<logic:notEmpty property="displayPostal" name="trackingForm">
																	<logic:notEqual name="trackingForm" property="displayPostal" value="0">
																		<tr><td><bean:write name="trackingForm" property="displayPostal"/></td></tr>
																	</logic:notEqual>
																</logic:notEmpty>
																<logic:notEmpty property="displayEmail" name="trackingForm">
																		<tr><td><bean:write name="trackingForm" property="displayEmail"/></td></tr>
																</logic:notEmpty>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr><tr>
											<td colspan="2">&nbsp;</td>
										</tr>
								</table>
							</div>
						</div>
					</div>	
				</logic:notEqual>			
			</logic:notEmpty>
			<logic:notEmpty property="displayDocketsForConsignment" name="trackingForm">
				<div class="panel panel-default">
			  		<div class="panel-body" style="padding-top:0px !important; padding-bottom:0px !important">
				  		<div class="table-responsive">
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<%int dctCounter=0; %>
								<bean:define id="docCompare" name="trackingForm" property="docCompare"/>
								<logic:notEmpty property="displayDocketsForConsignment" name="trackingForm">
									<logic:iterate id="listOfRecords" name="trackingForm" property="displayDocketsForConsignment" indexId="index">
					               		<tr BGCOLOR='<%= (dctCounter++ % 2==0 ?"#EEFAF6":"#FFFFFF") %>'>
											<td><%if(dctCounter<=1){%>Cover Dockets<%}%>&nbsp;</td>
					           				<td align="right">
					           					<table style="width:100%">
													<tr>
														<td style="width:50%" align="right">
															<bean:define id="docID" name="listOfRecords" property="docketNumber"/>	
																<%if (!docCompare.toString().equalsIgnoreCase(docID.toString())){ %>
																	<bean:write name="listOfRecords" property="docketNumber"/>
																	<bean:define id="docCompare" name="listOfRecords" property="docketNumber"/>
																<%}else{ %>
																	&nbsp;
																<%} %>
														</td>
														<td style="width:50%" align="right"><bean:write name="listOfRecords" property="studentNumber"/>----<bean:write name="listOfRecords" property="uniqueAssignmentNumber"/></td>
													</tr>
												</table>
					              			</td>
					                	</tr>  
					               	</logic:iterate> 
				             	</logic:notEmpty>
							</table>
						</div>
					</div>
				</div>
			</logic:notEmpty>
			<logic:notEmpty property="displayUniqueNumbersForConsignment" name="trackingForm">
				<div class="panel panel-default">
			  		<div class="panel-body" style="padding-top:0px !important; padding-bottom:0px !important">
				  		<div class="table-responsive">
							<table style="width:100%">
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
				             	<%int stuCounter=0; %>
								<logic:notEmpty property="displayUniqueNumbersForConsignment" name="trackingForm">
									<logic:iterate id="listOfRecords2" name="trackingForm" property="displayUniqueNumbersForConsignment" indexId="index">
					               		<tr BGCOLOR='<%= (stuCounter++ % 2==0 ?"#EEFAF6":"#FFFFFF") %>'>
											<td><%if(stuCounter<=1){%>Unique Assignments<%}%>&nbsp;</td>
					           				<td align="right">
								               	<bean:write name="listOfRecords2" property="studentNumber"/>----<bean:write name="listOfRecords2" property="uniqueAssignmentNumber"/>
					              			</td>
					                	</tr>         
					               	</logic:iterate> 
				             	</logic:notEmpty>
				             	<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</logic:notEmpty>
		</logic:notEmpty>
	<!--end of panel-->
</div>
	
	<!-- Actions -->
	<div class="noPrint">
		<div class="panel panel-default">
		  	<div class="panel-body" style="padding-top:0px !important; padding-bottom:0px !important">
								<!--<html:submit property="act" style="width: 50px; height: 80px" onclick="frmSubmitSameWin();"><fmt:message key="button.back" /></html:submit>-->
				  					<input type="button" id="closeButton" value="CLOSE" />
				  				&nbsp;<input type="button" id="printButton" value="PRINT" />
				  				&nbsp;<input type="button" id="printPDFButton" value="PRINT PDF FORM" />
						<div align="right"><font color="gray">&nbsp;<fmt:message key="user.name"></fmt:message><bean:write name="trackingForm" property="novelUserId"/></font></div>
			</div>
		</div>
	</div>
</html:form>

<script type="text/javascript">
/* IE11 Fix for SP2010 */
    if (typeof(UserAgentInfo) != 'undefined' && !window.addEventListener) 
    {
        UserAgentInfo.strBrowser=1; 
    } 
	
var unisaImageDataUrl = 'data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAeAB4AAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCACXAoADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD36iiikMKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigDgG1S+HxwTTBdzfY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IbnJGMdqx5fhrPF4T0GzsNTEes6I5mtbt0JQsTllIzwpOB3OB05xXo1FAHnt74Z8VeLbqxg8TS6Za6VazLPJb2JdmuXXoCWAwvXjn+RFrWfDuv2vjceJ/D0llK01qLW5tbxmVSAchlZQeenGO3fPHcUUAcL4Z8Ka5p/jjU/EOr3tpcNfWqRlYAwEbAj5QCPugAAHOT1IFHhzw94j8N6/qUUL6bNol9fyXrSOzidd45UADHBxyT0Hvx3VFAHL+BfDt34Z0CWwvJInla7lmBiJI2s2R1A5xR4D8O3fhfw42n3skLzG5llzCSVwzZAyQDn8K6iigDj/ABn4a1DWb7RdU0x7V7rSrhpRbXefKlBAB5AOCMZBweaq2HhzxHJ8QbfxPqsuniMWT2xt7dnJiBOQASBu55JOOvA457qigDkPD3ha90qTxS08kDf2tey3EGxj8qsCAGyOD64zVzwLoN14Z8G2GkXjxST24YM0RJU5YkYyAeh9K6OigDz2z+Hck3hPxJoeqTxhdU1OW8ilgyxjDbCpIIHIK8jp71oaJF49s3tLLUTotxaREJLdq8glkjHfbjG4j3xmuyooA8403wl4o0Rda03T59MNnqVzJOL+YsZ4w/UFAMMQOmSBk59q6PwLoN14Z8HWGkXjxPPbBwzRElTl2YYyAeh9K6SigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA//9k=';
</script>

</html>