LF = window.LF || {};

(function(A, LF) {
	var Lang = A.Lang;
	var components = {};
	var componentsFn = {};

	LF.component = function(id, value) {
		var retVal;

		if (arguments.length === 1) {
			var component = components[id];

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

	LF._components = components;
	LF._componentsFn = components;
})(AUI(), LF);