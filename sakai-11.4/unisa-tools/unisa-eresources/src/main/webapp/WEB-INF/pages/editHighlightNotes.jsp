<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="highlightnotesDetail.title"/></title>
    <!--  <meta name="menu" content="ContentTypeMenu"/> -->
</head>
 
<div class="span2">
    <h3><fmt:message key='allpages.description'/></h3>
    <h3><fmt:message key='highlightnotesDetail.heading.edit'/></h3>
</div>
<div class="span7">
    <form:errors path="*" cssClass="alert alert-error fade in" element="div"/>
    <form:form commandName="editHighlightNotes" method="post" action="highlightnotesform" id="highlightnotesform"
               cssClass="well form-horizontal">
    <form:hidden path="highlightNotesID"/>
     
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="highlightnotes.Description"/>
        <div class="controls">
            <form:input path="highlightNote" id="description" maxlength="50"/>
            <form:errors path="highlightNote" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="highlightnotes.isEnabled"/>
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
          <c:if test="${not empty highlightnote.highlightNotesID}">
          <button type="submit" class="btn" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
              <i class="icon-trash"></i> <fmt:message key="button.delete"/>
          </button>
        </c:if>
        <button type="submit" class="btn" name="cancel" onclick="bCancel=true">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div>
    
        <%-- <button type="submit" class="btn btn-primary" name="save">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty highlightnotes.highlightNotesID}">
          <button type="submit" class="btn" name="delete">
              <i class="icon-trash"></i> <fmt:message key="button.delete"/>
          </button>
        </c:if>
        <button type="submit" class="btn" name="cancel">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div> --%>
    </form:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['highlightNotesForm']).focus();
    });
</script>
