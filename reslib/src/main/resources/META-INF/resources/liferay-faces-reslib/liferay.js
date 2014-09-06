;(function() {
	window.YUI_config = {
		base: "#{resource['liferay-faces-reslib:script']}&",
		combine: true,
		comboBase: "#{resource['liferay-faces-reslib:combo']}&",
		root: 'build/',
	};
})();

var AUI = YUI;
Liferay = window.Liferay || {};
if (!Liferay.zIndex) {
	Liferay.zIndex = {
			DOCK: 10,
			DOCK_PARENT: 20,
			ALERT: 430,
			DROP_AREA: 440,
			DROP_POSITION: 450,
			DRAG_ITEM: 460,
			OVERLAY: 1000,
			WINDOW: 1200,
			MENU: 5000,
			TOOLTIP: 10000
	};
}

(function(A, Liferay) {
	var Lang = A.Lang,
		components = {},
		componentsFn = {};

	Liferay.component = function(id, value) {
		var retVal,
			component;

		if (arguments.length === 1) {
			component = components[id];

			if (component && Lang.isFunction(component)) {
				componentsFn[id] = component;

				component = component();

				components[id] = component;
			}

			retVal = component;
		}
		else {
			retVal = (components[id] = value);
		}

		return retVal;
	};

	Liferay._components = components;
	Liferay._componentsFn = components;
})(AUI(), Liferay);