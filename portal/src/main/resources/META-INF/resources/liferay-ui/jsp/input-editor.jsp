<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%
java.util.Map<String,String> requestDispatcherParams = new java.util.HashMap<String, String>();
String queryString = (String) request.getAttribute("javax.servlet.include.query_string");
if (queryString != null) {
	String[] nameValuePairs = queryString.split("[&]");
	if (nameValuePairs != null) {
		for (String nameValuePair: nameValuePairs) {
			String[] nameAndValue = nameValuePair.split("=");
			requestDispatcherParams.put(nameAndValue[0], nameAndValue[1]);
		}
	}
}
String cssClasses = requestDispatcherParams.get("cssClasses");
String editorImpl = requestDispatcherParams.get("editorImpl");
String height = requestDispatcherParams.get("height");
String initMethod = requestDispatcherParams.get("initMethod");
String name = requestDispatcherParams.get("name");
String onBlurMethod = requestDispatcherParams.get("onBlurMethod");
String onChangeMethod = requestDispatcherParams.get("onChangeMethod");
String onFocusMethod = requestDispatcherParams.get("onFocusMethod");
boolean skipEditorLoading = Boolean.TRUE.toString().equals(requestDispatcherParams.get("skipEditorLoading"));
String toolbarSet = requestDispatcherParams.get("toolbarSet");
String width = requestDispatcherParams.get("width");
%>
<liferay-ui:input-editor
	editorImpl="<%= editorImpl %>"
	height="<%= height %>"
	initMethod="<%= initMethod %>"
	name="<%= name %>"
	onBlurMethod="<%= onBlurMethod %>"
	onChangeMethod="<%= onChangeMethod %>"
	onFocusMethod="<%= onFocusMethod %>"
	skipEditorLoading="<%= skipEditorLoading %>"
	toolbarSet="<%= toolbarSet %>"
	width="<%= width %>" />