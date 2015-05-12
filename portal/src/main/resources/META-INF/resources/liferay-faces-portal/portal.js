LFPI = {

	inputRichTextInit: function(clientId) {
		return document.getElementById(clientId + '_input').value;
	},

	inputRichTextBlur: function(clientId) {
		var inputField = document.getElementById(clientId + '_input');
		if ((inputField.onchange) && (inputField.title === 'valueChange')) {
			inputField.onchange();
		}
		if (inputField.onblur) {
			inputField.onblur();
		}
	},

	inputRichTextChange: function(clientId, text) {
		var inputField = document.getElementById(clientId + '_input');
		var oldText = inputField.value;
		if (oldText === text) {
			inputField.title = null;
		}
		else {
			inputField.title = 'valueChange';
		}
		inputField.value = text;
	},

	inputRichTextFocus: function(clientId) {
		var inputField = document.getElementById(clientId + '_input');
		inputField.title = null;
		if (inputField.onfocus) {
			inputField.onfocus();
		}
	}
};
