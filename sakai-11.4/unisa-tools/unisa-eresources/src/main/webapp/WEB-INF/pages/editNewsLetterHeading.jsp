<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="newsletterDetail.title"/></title>
    <!--  <meta name="menu" content="ContentTypeMenu"/> -->
</head>
 
<div class="span2">
    <h3><fmt:message key='allpages.description'/></h3>
    <h3><fmt:message key='newsLetterDetail.edit'/></h3>
</div>
<div class="span7">
    <form:errors path="*" cssClass="alert alert-error fade in" element="div"/>
    <form:form commandName="editNewsLetterHeading" method="post" action="newsletterform" id="newsLetterForm"
               cssClass="well form-horizontal">
    <form:hidden path="newsTitleID"/>
     
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="newsletter.description"/>
        <div class="controls">
            <form:input path="newsTitle" id="description" maxlength="50"/>
            <form:errors path="newsTitle" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="newsLetter.isEnabled"/>
        <div class="controls">
             <form:select path="enabled">  
                <form:option value="1">Yes</form:option>  
             	<form:option value="0">No</form:option>  
            </form:select>           
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="save">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty newsletter.newsTitleID}">
          <button type="submit" class="btn" name="delete">
              <i class="icon-trash"></i> <fmt:message key="button.delete"/>
          </button>
        </c:if>
        <button type="submit" class="btn" name="cancel">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div>
    </form:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['newsLetterForm']).focus();
    });
</script>
