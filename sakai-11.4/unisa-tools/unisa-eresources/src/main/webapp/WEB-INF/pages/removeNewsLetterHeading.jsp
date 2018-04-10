<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="allpages.description" /></title>
<meta name="menu" content="newsLetterMenu" />
</head>

<div class="span2">
	<h2>
		<fmt:message key='removeNewsLetterDetail.title' />
	</h2>
</div>
<div class="span7">
	<form:errors path="*" cssClass="alert alert-error fade in"
		element="div" />
	<form:form commandName="newsletter" method="post"
		action="newsletterform" id="newsLetterForm"
		cssClass="well form-horizontal">
		<form:hidden path="id" />

		<fmt:message key='removeNewsLetterDetail.instruction' />
		<div class="form-actions">
			<logic:iterate name="newsLetterForm" property="description"
				id="description">
				<logic:equal name="newsLetterForm" property="remove" value="true">
					<tr>
						<td><bean:write name="newsLetterForm" property="description" /></td>
						<td><bean:write name="newsLetterForm" property="description" /></td>

					</tr>
				</logic:equal>
			</logic:iterate>
		</div>

		<div class="form-actions">
			<button type="submit" class="btn btn-primary" name="remove">
				<i class="icon-remove icon-white"></i>
				<fmt:message key="button.remove" />
			</button>
			<button type="submit" class="btn" name="back">
				<i class="icon-back"></i>
				<fmt:message key="button.back" />
			</button>
		</div>
	</form:form>
</div>