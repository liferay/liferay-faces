function iceExtScrollPanelGroupToBottom(clientId, totalMessages){
	/* totalMessages is only passed here so that the script element in the page will participate in the DOM-diff */
	var panelGroupDiv = document.getElementById(clientId); 
	panelGroupDiv.scrollTop = panelGroupDiv.scrollHeight; 
} 
