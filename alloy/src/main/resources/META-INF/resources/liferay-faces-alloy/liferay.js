;(function() {
	window.YUI_config = {
		base: "#{resource['liferay-faces-alloy:script']}&",
		combine: true,
		comboBase: "#{resource['liferay-faces-alloy:combo']}&",
		root: 'build/',
	};
})();

var AUI = YUI;
Liferay = window.Liferay || {};

(function(A, Liferay) {
	var Lang = A.Lang;
	var components = {};
	var componentsFn = {};

	Liferay.component = function(id, value) {
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

	Liferay._components = components;
	Liferay._componentsFn = components;
})(AUI(), Liferay);