function _functionNamespace_blur() {
	var inputField = document.getElementById('_clientId__input');
	if ((inputField.onchange) && (inputField.title === 'valueChange')) {
		inputField.onchange();
	}
	if (inputField.onblur) {
		inputField.onblur();
	}
}

function _functionNamespace_change(text) {
	var inputField = document.getElementById('_clientId__input');
	var oldText = inputField.value;
	if (oldText === text) {
		inputField.title = null;
	} else {
		inputField.title = 'valueChange';
	}
	inputField.value = text;
}

function _functionNamespace_focus() {
	var inputField = document.getElementById('_clientId__input');
	inputField.title = null;
	if (inputField.onfocus) {
		inputField.onfocus();
	}
}

function _functionNamespace_init() {
	return document.getElementById('_clientId__input').value;
}
