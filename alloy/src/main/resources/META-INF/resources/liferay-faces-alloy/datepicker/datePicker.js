// This is a javascript function that is used by DatePicker.java to conditionally fire a change event on the input which
// triggers the datePicker.
function datePickerDefaultOnDateClick(newDate, input, calendar) {
	var fireChange = true;
	if (calendar.get('selectionMode') === 'single') {
		var oldDate = calendar.get('oldDate');
		if (oldDate) {
			// Reset the time to midnight because we want to compare dates below and not times.
			oldDate.setHours(0, 0, 0, 0);
			newDate.setHours(0, 0, 0, 0);
			fireChange = (oldDate.getTime() !== newDate.getTime());
		}
		calendar.set('oldDate', newDate);
	}

	// If there is not an old date or it is different than the new date, then simulate an onchange event. This is
	// necessary when there is an f:ajax child on the datePicker's input textbox.
	if (fireChange) {
		input.simulate('change');
	}
}

// This is a javascript function that is used by InputDateRenderer.java to set the value of the input to the new date,
// and fire a change event on the input.
function inputDateButtonOnDateClick(input, formattedDate) {
	input.set('value', formattedDate);
	input.simulate('change');
}