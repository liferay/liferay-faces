var LFA = { escapeClientId : function(clientId) {
	return clientId.replace(/:/g, '\\:');
} };

var LFAI = {

	// This is a replacement function for the timePicker's built-in private _setValues() method. Since the values have been
	// processed and formatted server-side, this function simply passes values through to the autocomplete without
	// processing them.
	setValues : function(values) {
		return values;
	},

	initPreviewUploader : function(A, contentTypes, clientId, maxFileSize) {

		var contentTypeArray = A.Array(contentTypes), escapedClientId = LFA.escapeClientId(clientId), uploadComplete = false;
		A.one('#' + escapedClientId).one('>input[type=\"file\"]')
				.on(
						'change',
						function(e) {
							var fileList = e.target.getDOMNode().files, fileTable = A.one('#' + escapedClientId
									+ '_table'), fileTableBody = fileTable.one('tbody');
							fileTableBody.setHTML('');
							fileTable.one('tfoot').setStyle('display', 'none');
							A.each(fileList, function(curFile) {
								var tableRow = '<tr id="' + curFile.id + '_row">';
								tableRow += '<td class="yui3-datatable-cell">' + curFile.name + '</td>';
								if ((contentTypeArray.length == 0) || contentTypeArray.indexOf(curFile.type) >= 0) {
									tableRow += '<td class="yui3-datatable-cell">';
								} else {
									tableRow += '<td class="yui3-datatable-cell text-error">'
								}
								tableRow += curFile.type + '</td>';
								if (curFile.size <= maxFileSize) {
									tableRow += '<td class="yui3-datatable-cell">';
								} else {
									tableRow += '<td class="yui3-datatable-cell text-error">';
								}
								tableRow += curFile.size + '</td>';
								tableRow += '</tr>';
								fileTableBody.append(tableRow);
							});
						});
	},

	initProgressUploader : function(A, clientComponent, contentTypes, clientId, formClientId, namingContainerId, auto,
			execute, render, partialActionURL, maxFileSize, notStartedMessage) {

		if (A.Uploader.TYPE != 'none' && !A.UA.ios) {

			var contentTypeArray = A.Array(contentTypes), escapedClientId = LFA.escapeClientId(clientId), escapedFormClientId = LFA
					.escapeClientId(formClientId), uploadComplete = false;
			A.Uploader.HTML5FILEFIELD_TEMPLATE = "<input type='file' style='visibility:hidden; width:0.1px; height: 0px;'>";
			clientComponent.render('#' + escapedClientId + '_selectFilesBox');
			A.one('#' + escapedClientId + '_selectFilesBox').one('>div').removeAttribute('style');
			A.one('#' + escapedClientId + '_selectFilesBox').one('>div>div>button').removeAttribute('style');
			A.one('#' + escapedClientId + '_uploadFilesButton').on('click', function(e) {
				var fileList = clientComponent.get('fileList'), formNode, requestParams, viewStateNode;
				formNode = A.one('#' + escapedFormClientId);
				viewStateNode = formNode.one('>input[name="javax.faces.ViewState"]');
				if (!viewStateNode) {
					viewStateNode = formNode.one('>input[name="' + namingContainerId + 'javax.faces.ViewState"]');
				}
				if (!uploadComplete && fileList.length > 0) {
					e.preventDefault();
					requestParams = {};
					requestParams[namingContainerId + formClientId] = formClientId;
					requestParams[namingContainerId + 'Faces-Request'] = 'partial/ajax';
					requestParams[namingContainerId + 'javax.faces.behavior.event'] = 'valueChange';
					requestParams[namingContainerId + 'javax.faces.partial.ajax'] = 'true';
					requestParams[namingContainerId + 'javax.faces.partial.event'] = 'click';
					requestParams[namingContainerId + 'javax.faces.partial.execute'] = execute;
					requestParams[namingContainerId + 'javax.faces.partial.render'] = '';
					requestParams[namingContainerId + 'javax.faces.source'] = clientId;
					requestParams[namingContainerId + 'javax.faces.ViewState'] = viewStateNode.get('value');
					clientComponent.uploadAll(partialActionURL, requestParams);
				}
			});

			clientComponent.on('uploadcomplete', function(event) {
				jsf.ajax.request(clientId, 'valueChange', { execute : '@none',
				render : render });
			});

			clientComponent.on('uploadprogress', function(event) {
				var tableRow = A.one('#' + event.file.get('id') + '_row');
				tableRow.one('.percent-complete').set('text', event.percentLoaded + '%');
			});

			clientComponent.on('uploadstart', function(event) {
				clientComponent.set('enabled', false);
				A.one('#' + escapedClientId + '_uploadFilesButton').addClass('yui3-button-disabled').detach('click');
			});

			clientComponent
					.after(
							'fileselect',
							function(event) {
								var fileList = event.fileList, fileTable = A.one('#' + escapedClientId + '_table'), fileTableBody = fileTable
										.one('tbody'), appendNewFiles = clientComponent.get('appendNewFiles');
								if (!appendNewFiles) {
									fileTableBody.setHTML('');
								}
								if (uploadComplete) {
									uploadComplete = false;
									fileTableBody.setHTML('');
								}
								A.each(fileList, function(curFile) {
									var tableRow = '<tr id="' + curFile.get('id') + '_row">';
									tableRow += '<td class="yui3-datatable-cell">' + curFile.get('name') + '</td>';
									if ((contentTypeArray.length == 0)
											|| contentTypeArray.indexOf(curFile.get('type')) >= 0) {
										tableRow += '<td class="yui3-datatable-cell">'
									} else {
										tableRow += '<td class="yui3-datatable-cell text-error">';
									}
									tableRow += curFile.get('type') + '</td>';
									if (curFile.get('size') <= maxFileSize) {
										tableRow += '<td class="yui3-datatable-cell">'
									} else {
										tableRow += '<td class="yui3-datatable-cell text-error">'
									}
									tableRow += curFile.get('size') + '</td>';
									tableRow += '<td class="yui3-datatable-cell percent-complete">' + notStartedMessage
											+ '</td>';
									tableRow += '</tr>';
									fileTableBody.append(tableRow);
								});
								fileTable.one('tfoot').setStyle('display', 'none');
								if (auto) {
									A.one('#' + escapedClientId + '_uploadFilesButton').simulate('click');
								}
							});
		}
	}
};