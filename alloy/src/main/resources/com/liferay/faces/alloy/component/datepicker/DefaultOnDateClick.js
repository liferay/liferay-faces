// This is a javascript template that is used by DatePicker.java
var fireChange=true;
if(this.get('selectionMode')==='single') {
	var oldDate = this.get('oldDate');
	if (oldDate) {
		newDate = event.date;
		// Reset the time to midnight because we want to compare dates below and
		// not times.
		oldDate.setHours(0, 0, 0, 0);
		newDate.setHours(0, 0, 0, 0);
		fireChange = (oldDate.getTime() !== newDate.getTime());
	}
	this.set('oldDate', event.date);
}

// If there is not an old date or it is different than the new date, then
// simulate an onchange event. This is necessary when there is an f:ajax child
// on the datePicker's input textbox.
if (fireChange) {
	var input = A.one('#{0}');
	input.simulate('change');
}