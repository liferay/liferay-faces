var iceFacesChat = {
	scrollToBottom : function(clientId) {
		var panelGroupDiv = document.getElementById(clientId);
		panelGroupDiv.scrollTop = panelGroupDiv.scrollHeight;
	},

	setFocus : function(clientId) {
		var inputText = document.getElementById(clientId);
		inputText.focus();
	}
};
