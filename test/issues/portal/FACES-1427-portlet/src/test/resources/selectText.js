selectText = {
	setSelection : function (id, start, end) {
		var field = document.getElementById(id);
		if ( field.createTextRange ) {
			var selRange = field.createTextRange();
			selRange.collapse(true);
			selRange.moveStart('character', start);
			selRange.moveEnd('character', end);
			selRange.select();
			field.focus();
		} else if ( field.setSelectionRange ) {
			field.focus();
			field.setSelectionRange(start, end);
		} else if ( typeof field.selectionStart != 'undefined' ) {
			field.selectionStart = start;
			field.selectionEnd = end;
			field.focus();
		}
	},
	
	getSelection : function (id) {
		var field = document.getElementById(id);
		return field.value.substring(field.selectionStart, field.selectionEnd);
	}
}
