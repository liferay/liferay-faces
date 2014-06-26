// This is a javascript function that is used by PickDate.java to conditionally fire a change event on the input which
// triggers the pickDate.
function pickDateDefaultOnDateClick(event, input) {
	var fireChange = true;
	var calendar = event.currentTarget;
	if (calendar.get('selectionMode') === 'single') {
		var oldDate = calendar.get('oldDate');
		if (oldDate) {
			// Reset the time to midnight because we want to compare dates below and not times.
			var newDate = event.date;
			newDate.setHours(0, 0, 0, 0);
			oldDate.setHours(0, 0, 0, 0);
			fireChange = (oldDate.getTime() !== newDate.getTime());
		}
		calendar.set('oldDate', newDate);
	}

	// If there is not an old date or it is different than the new date, then simulate an onchange event. This is
	// necessary when there is an f:ajax child on the pickDate's input textbox.
	if (fireChange) {
		input.simulate('change');
	}
}