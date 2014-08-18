var LFA = {
	getAccordionEventTabIndex : function(event, clientKey) {
		var togglerDelegate = Liferay.component(clientKey),
			togglers = togglerDelegate.items,
			total = togglers.length,
			i = 0;
		for (i = 0; i < total; i++) {
			if (togglers[i] == event.target) {
				return i;
			}
		}
		return -1;
	}
}