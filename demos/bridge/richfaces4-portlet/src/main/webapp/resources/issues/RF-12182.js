var rf12182 = {
	workaround : function (event, id) {
		var v = RichFaces.$(id);
		var domElement = RichFaces.getDomElement(v.id);
		v.invokeEvent("change", domElement, event, v.selectedDate);
	}
};