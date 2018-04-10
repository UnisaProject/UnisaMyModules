<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="subjecteDetail.title"/></title>
    <!--  <meta name="menu" content="ContentTypeMenu"/> -->
</head>
 
<div class="span2">
    <h3><fmt:message key='allpages.description'/></h3>
    <h3><fmt:message key='subjectDetail.heading.edit'/></h3>
</div>
<div class="span7">
    <form:errors path="*" cssClass="alert alert-error fade in" element="div"/>
    <form:form commandName="editSubject" method="post" action="subjectform" id="subjectForm"
               cssClass="well form-horizontal" onsubmit="return validateContentType(this)">
    <form:hidden path="SubjectId"/>
     <spring:bind path="subjectName">
    <div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
     </spring:bind>
        <appfuse:label styleClass="control-label" key="subjects.Description"/>
        <div class="controls">
            <form:input path="subjectName" id="description" maxlength="50"/>
            <form:errors path="subjectName" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="subjects.isEnabled"/>
        <div class="controls">
             <form:select path="enabled">  
                <form:option value="1">Yes</form:option>  
             	<form:option value="0">No</form:option>  
            </form:select>           
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty subject.subjectId}">
          <button type="submit" class="btn" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
              <i class="icon-trash"></i> <fmt:message key="button.delete"/>
          </button>
        </c:if>
        <button type="submit" class="btn" name="cancel" onclick="bCancel=true">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div>
    </form:form>
</div>
<v:javascript formName="contentType" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['subjectForm']).focus();
    });
</script>
