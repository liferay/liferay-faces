var exampleNamespace = {
	exampleFunction : function(id) {
		document.getElementById(id).innerHTML = 'A script has called <code>exampleNamespace.exampleFunction(id)</code> '
				+ 'which resides in the JavaScript resouce file named <code>example.js</code>.';
	}
};