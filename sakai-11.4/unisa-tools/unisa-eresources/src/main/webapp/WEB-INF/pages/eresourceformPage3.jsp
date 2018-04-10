<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="contentTypeDetail.title"/></title>
    <!--  <meta name="menu" content="ContentTypeMenu"/> -->
</head>
 
<div class="span2">
    <h3><fmt:message key='allpages.description'/></h3>
    <h3><fmt:message key='contentTypeDetail.heading'/></h3>
</div>
<div class="span7">
    <form:errors path="*" cssClass="alert alert-error fade in" element="div"/>
    <form:form commandName="eresource" method="post" action="submitEResourceForm3"  id="eresource3"
               cssClass="well form-horizontal">
    <form:hidden path="eresourceId"/>
     
  
    <p><b>Tick the boxes next to the Subjects that need to be linked.</b></p>
	 
	 
	  <fieldset class="control-group">
         <c:forEach items="${subjects}" var="subject">
            <div class="controls">
            	<label class="checkbox inline">
            		<form:checkbox path="eresourceSubjectIds" value="${subject.subjectId}"/>${subject.subjectName}
            	</label>
            </div>
         </c:forEach>
        </fieldset>
	<%--    
	<display:table name="placementList" class="table table-condensed table-striped table-hover" requestURI="" id="placements" export="true" pagesize="25">
	      <display:column property="subject" media="csv excel xml pdf" titleKey="subjects.id"/>
	       <display:column property="subject" sortable="true" titleKey="subjects.description"/>
	       
	       <display:column titleKey="check.select">
		    	<input type="checkbox" name="checklist"> <c:out value="${eresourceId}"></c:out>
		   </display:column>   
	</display:table> --%>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="_target3">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.continue"/>
        </button>
          <button type="submit" class="btn" name="_target1">
            <i class="icon-remove"></i> <fmt:message key="button.previous"/>
        </button>
        <c:if test="${not empty eresources.eresourceId}">
          <button type="submit" class="btn" name="delete">
              <i class="icon-trash"></i> <fmt:message key="button.delete"/>
          </button>
        </c:if>
        <button type="submit" class="btn" name="_cancel">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div>
    </form:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['resource']).focus();
    });
</script>