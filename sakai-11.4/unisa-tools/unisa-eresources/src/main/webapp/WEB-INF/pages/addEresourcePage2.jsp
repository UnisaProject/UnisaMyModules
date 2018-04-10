<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="contentTypeDetail.title"/></title>
    <!--  <meta name="menu" content="ContentTypeMenu"/> -->
</head>
 
<div class="span2">
    <h3><fmt:message key='allpages.description'/></h3>
    <h3><fmt:message key='placement.heading'/></h3>
</div>
<div class="span7">
    <form:errors path="*" cssClass="alert alert-error fade in" element="div"/>
    <form:form commandName="addEresourcePage2" method="post" action="resource" id="eresource2"
               cssClass="well form-horizontal">
    <form:hidden path="eresourceId"/>
     
     <p><b>Tick the boxes next to the Placements that need to be linked.</b></p>
	   
	<display:table name="placementList" class="table table-condensed table-striped table-hover" requestURI="" id="placements" export="true" pagesize="25">
	      <display:column property="eresourcePlacement" media="csv excel xml pdf" titleKey="placement.id"/>
	       <display:column property="eresourcePlacement" sortable="true" titleKey="placement.description"/>
	       
	       <display:column titleKey="check.select">
		    	<input type="checkbox" name="checklist"> <c:out value="${eresourceId}"></c:out>
		   </display:column>   
	</display:table>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="_target2">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.continue"/>
        </button>
        <button type="submit" class="btn" name="_target0">
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