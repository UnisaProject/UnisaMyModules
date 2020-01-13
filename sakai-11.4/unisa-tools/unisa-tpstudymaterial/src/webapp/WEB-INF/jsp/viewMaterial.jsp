<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tpstudymaterial.ApplicationResources" />
<sakai:html>
<html:form action="/tpStudyMaterial.do">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.10.2.min.js"></script>     
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery/jquery-ui-1.10.2.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/tpstudymaterial.css"></link>
<style>



</style>


<script language="javascript" type="text/javascript">
   function redirect(URL) {
      window.open(URL,'mywindow')
       return false;
    }
    </script>
    
    
    		<sakai:messages/>
    <sakai:messages message="true"/>
	<sakai:heading>

		<fmt:message key="tpstudymaterial.material.heading"/>
		<bean:write name="tpStudyMaterialForm" property="courseCode"/>-
				<bean:write name="tpStudyMaterialForm" property="academicYear"/>-
				<bean:write name="tpStudyMaterialForm" property="semister"/> for Student: <bean:write name="tpStudyMaterialForm" property="studentNr"/>
	</sakai:heading><br>
    <fmt:message key="tpstudymaterial.material.info1"/><br><br>
    <fmt:message key="tpstudymaterial.material.note"/><br><br>
    <fmt:message key="tpstudymaterial.courses.info1"/> <b><bean:write name="tpStudyMaterialForm" property="listdate"/></b><br>

	 <sakai:flat_list>
	<logic:notEmpty name="tpStudyMaterialForm" property="studyMaterialList">
	    <sakai:heading>
		<fmt:message key="tpstudymaterial.material.info2" />
	</sakai:heading>
	<tr>
			<th><fmt:message key="label.tableheaderdisc"/></th>		
		    <th><fmt:message key="label.tableheaderfilesize"/></th>
			<th><fmt:message key="label.tableheaderavailabledate"/></th>	
			</tr>
			
	<logic:iterate name="tpStudyMaterialForm" property="studyMaterialList" id="d" indexId="dindex">	
	     <tr>
			<td>
			<input type="image" align="left" src="/library/image/sakai/pdf.gif"></input> &nbsp;&nbsp;
		     <html:link	 href="javascript:void(0);" onclick="loadIframe('tpStudyMaterial.do?action=viewStudyMaterial&itemshortdesc=${d.shortDescription}&courseCode=${d.courseCode}','${d.shortDescription}','${d.courseCode}')">
		     <bean:write name="d" property="discription"/>	
		     </html:link>
		</td>
						
			<td><bean:write name="d" property="filesize"/></td>
			<td><bean:write name="d" property="implementationDate"/></td>
		</tr>
	</logic:iterate>
	</logic:notEmpty>
	 <logic:empty name="tpStudyMaterialForm" property="studyMaterialList">
	 <br/>
	  <p>
	   There is no Study Materials Available
	   </p>
	 </logic:empty>
	</sakai:flat_list>
	<html:hidden property="backOption" value="TOVIEWCOURSE"/>
	<!-- View Study Material in Iframe -->
<div id="detailID" title="Study Material Preview" style="overflow:scroll;display: none;height: 900px;">
<div>
<div class="btn_small btn_small_outer paddLR10" style="width:80px;float:right;margin-top:01px;margin-right:10px;margin-bottom:10px;">
<div>
<!-- class="btn_orange   -->
<!-- <a onclick="downloadstudyMaterial();" href="javascript:void(0);">Download</a> -->

<button id="button-1" onclick="downloadstudyMaterial();">Download</button>

      <script>
         $(function() {
            $("#button-1").button();
         });
      </script>

</div>
</div>

<iframe  style="width:100%;height:900px;" src="" id="iframeId"></iframe>
</div>


</div>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.cancel"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="button.goprevpage"/>
		</html:submit>		
	</sakai:actions>
	 <script>
   
      var loadIframe = function (url,shortDesc,modCode) {
    	  shortDesc1 = shortDesc;
    	  modCode1 = modCode;
    	   var $iframe = $('#iframeId');
    	    if ( $iframe.length ) {
    	        $iframe.attr('src',url);
    	         $('#detailID')
    	        .dialog({ 
    	          autoOpen: true, 
    	          width: 700, 
    	          height: 700, 
    	          position: 'center', 
    	          resizable: true, 
    	          draggable: true
    	         });
    	         
    	       
    	        
    	        return false;
    	    } 
    	    return true;
    	}
      
    var downloadstudyMaterial = function () {
    	window.location
    	
    	 var url = 'tpStudyMaterial.do?action=download&itemshortdesc='+shortDesc1+'&courseCode='+modCode1;
    	$(location).attr('href',url);
    	
    	//window.location = 'tpStudyMaterial.do?action=download&itemshortdesc='+shortDesc1+'&courseCode='+modCode1;
    	
       /*  $.ajax({
            url: 'tpStudyMaterial.do?action=download&itemshortdesc='+shortDesc+'&courseCode='+modCode; */
     /*       type: "post",
            success: function(data){
                    //here response data will come and we need to update in the page.
            },
            beforeSend: function(){
                  // it will execute before Ajax call start
            },
            complete: function() {
           // this method will execute after success method is completed.                 
            },
            error: function(){
            	
             }
          }); */
    	
    }   


</script>
</html:form>
</sakai:html>
