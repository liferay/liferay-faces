// Case-insensitive phrase match filter
function clientCustomFilter(query, source) {

	var length = source.length;
	var results = [];
	var lowerCaseQuery = query.toLowerCase();

	for (var i = 0, j = 0; i < length; i++) {

		var lowerCaseSource = source[i].text.toLowerCase();

		if (lowerCaseSource.indexOf(lowerCaseQuery) > -1) {
			results[j] = source[i];
			j++;
		}
	}

	return results;
}