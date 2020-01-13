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
    <!-- <form:errors path="*" cssClass="alert alert-error fade in" element="div"/> -->
    <form:form commandName="confirnDeleteContentType" method="post" action="contenttypeform" id="contentTypeForm"
               cssClass="well form-horizontal">
    <form:hidden path="id"/>
 	<h2><fmt:message key='contentType.confirmation.description'/></h2>
    <h3><fmt:message key='contentType.confirmation.list1'/></h3>  
    <h3><fmt:message key='contentType.confirmation.list2'/></h3>
    
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="remove">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.remove"/>
        </button>
        <c:if test="${not empty contentType.id}">
          <button type="submit" class="btn" name="delete">
              <i class="icon-trash"></i> <fmt:message key="button.delete"/>
          </button>
        </c:if>
        <button type="submit" class="btn" name="back">
            <i class="icon-remove"></i> <fmt:message key="button.back"/>
        </button>
        <button type="submit" class="btn" name="cancel">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div>
    </form:form>
</div>