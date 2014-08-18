A.one('#_escapedClientId_').one('>input[type=\"file\"]').on('change', function(e) {
	var fileList = e.target.getDOMNode().files, fileTable = A.one('#_escapedClientId__table'), fileTableBody = fileTable.one('tbody'), contentTypes = A.Array(_contentTypes_);
	fileTableBody.setHTML('');
	fileTable.one('tfoot').setStyle('display', 'none');
	A.each(fileList, function(curFile) {
		var tableRow = '<tr id="' + curFile.id + '_row">';
		tableRow += '<td class="yui3-datatable-cell">' + curFile.name + '</td>';
		console.log('contentTypes.length=' + contentTypes.length);
		if ((contentTypes.length == 0) || contentTypes.indexOf(curFile.type) >= 0) {
			tableRow += '<td class="yui3-datatable-cell">';
		}
		else {
			tableRow += '<td class="yui3-datatable-cell text-error">'
		}
		tableRow += curFile.type + '</td>';
		if (curFile.size <= _maxFileSize_) {
			tableRow += '<td class="yui3-datatable-cell">';
		}
		else {
			tableRow += '<td class="yui3-datatable-cell text-error">';
		}
		tableRow +=  curFile.size + '</td>';
		tableRow += '</tr>';
		fileTableBody.append(tableRow);
	});
});
