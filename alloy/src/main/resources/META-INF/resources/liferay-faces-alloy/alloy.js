var LFA = {
	getAccordionEventTabIndex : function(event, clientKey) {
		var togglerDelegate = Liferay.component(clientKey);
		var togglers = togglerDelegate.items;
		var total = togglers.length;
		for (var i = 0; i < total; i++) {
			if (togglers[i] == event.target) {
				return i;
			}
		}
		return -1;
	}
}