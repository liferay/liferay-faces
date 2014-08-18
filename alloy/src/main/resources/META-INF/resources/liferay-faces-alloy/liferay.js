Liferay = window.Liferay || {};

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