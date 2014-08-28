var LFA = {
	escapeClientId: function(clientId) {
		return clientId.replace(/:/g, '\\:');
	}
};

var LFAI = {

	TOKEN_REGEX: new RegExp('\\{0\\}'),

	// This is a replacement function for the timePicker's built-in private _setValues() method. Since the values have been
	// processed and formatted server-side, this function simply passes values through to the autocomplete without
	// processing them.
	timePickerSetValues: function(values) {
		return values;
	},

	initPreviewUploader: function(A, contentTypes, clientId, maxFileSize) {

		var contentTypeArray = A.Array(contentTypes),
			escapedClientId = LFA.escapeClientId(clientId);

		A.one('#' + escapedClientId).one('>input[type=\"file\"]').on('change', function(e) {
			var fileList = e.target.getDOMNode().files,
				fileTable = A.one('#' + escapedClientId + '_table'),
				fileTableBody = fileTable.one('tbody');
			fileTableBody.setHTML('');
			fileTable.one('tfoot').setStyle('display', 'none');
			A.each(fileList, function(curFile) {
				var tableRow = '<tr id="' + curFile.id + '_row">';
				tableRow += '<td class="yui3-datatable-cell">' + curFile.name + '</td>';

				if ((contentTypeArray.length === 0) || contentTypeArray.indexOf(curFile.type) >=
					0) {
					tableRow += '<td class="yui3-datatable-cell">';
				}
				else {
					tableRow += '<td class="yui3-datatable-cell text-error">';
				}

				tableRow += curFile.type + '</td>';

				if (curFile.size <= maxFileSize) {
					tableRow += '<td class="yui3-datatable-cell">';
				}
				else {
					tableRow += '<td class="yui3-datatable-cell text-error">';
				}

				tableRow += curFile.size + '</td>';
				tableRow += '</tr>';
				fileTableBody.append(tableRow);
			});
		});
	},

	initProgressUploader: function(A, clientComponent, contentTypes, clientId, formClientId, namingContainerId,
		auto, execute, render, partialActionURL, maxFileSize, notStartedMessage) {
		if (A.Uploader.TYPE !== 'none' && !A.UA.ios) {
			var contentTypeArray = A.Array(contentTypes),
				escapedClientId = LFA.escapeClientId(clientId),
				escapedFormClientId = LFA
				.escapeClientId(formClientId),
				uploadComplete = false;

			A.Uploader.HTML5FILEFIELD_TEMPLATE =
				"<input type='file' style='visibility:hidden; width:0.1px; height: 0px;'>";
			clientComponent.render('#' + escapedClientId + '_selectFilesBox');
			A.one('#' + escapedClientId + '_selectFilesBox').one('>div').removeAttribute('style');
			A.one('#' + escapedClientId + '_selectFilesBox').one('>div>div>button').removeAttribute('style');
			A.one('#' + escapedClientId + '_uploadFilesButton').on('click', function(e) {
				var fileList = clientComponent.get('fileList'),
					formNode, requestParams, viewStateNode;
				formNode = A.one('#' + escapedFormClientId);
				viewStateNode = formNode.one('>input[name="javax.faces.ViewState"]');

				if (!viewStateNode) {
					viewStateNode = formNode.one('>input[name="' + namingContainerId +
						'javax.faces.ViewState"]');
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
					requestParams[namingContainerId + 'javax.faces.ViewState'] = viewStateNode.get(
						'value');
					clientComponent.uploadAll(partialActionURL, requestParams);
				}
			});

			clientComponent.on('uploadcomplete', function(event) {
				jsf.ajax.request(clientId, 'valueChange', {
					execute: '@none',
					render: render
				});
			});

			clientComponent.on('uploadprogress', function(event) {
				var tableRow = A.one('#' + event.file.get('id') + '_row');
				tableRow.one('.percent-complete').set('text', event.percentLoaded + '%');
			});

			clientComponent.on('uploadstart', function(event) {
				clientComponent.set('enabled', false);
				A.one('#' + escapedClientId + '_uploadFilesButton').addClass('yui3-button-disabled').detach(
					'click');
			});

			clientComponent.after('fileselect', function(event) {

				var fileList = event.fileList,
					fileTable = A.one('#' + escapedClientId + '_table'),
					fileTableBody = fileTable
					.one('tbody'),
					appendNewFiles = clientComponent.get('appendNewFiles');

				if (!appendNewFiles) {
					fileTableBody.setHTML('');
				}

				if (uploadComplete) {
					uploadComplete = false;
					fileTableBody.setHTML('');
				}

				A.each(fileList, function(curFile) {
					var tableRow = '<tr id="' + curFile.get('id') + '_row">';
					tableRow += '<td class="yui3-datatable-cell">' + curFile.get('name') +
						'</td>';

					if ((contentTypeArray.length === 0) || contentTypeArray.indexOf(curFile.get(
							'type')) >= 0) {
						tableRow += '<td class="yui3-datatable-cell">';
					}
					else {
						tableRow += '<td class="yui3-datatable-cell text-error">';
					}

					tableRow += curFile.get('type') + '</td>';

					if (curFile.get('size') <= maxFileSize) {
						tableRow += '<td class="yui3-datatable-cell">';
					}
					else {
						tableRow += '<td class="yui3-datatable-cell text-error">';
					}

					tableRow += curFile.get('size') + '</td>';
					tableRow += '<td class="yui3-datatable-cell percent-complete">' +
						notStartedMessage + '</td>';
					tableRow += '</tr>';
					fileTableBody.append(tableRow);
				});

				fileTable.one('tfoot').setStyle('display', 'none');

				if (auto) {
					A.one('#' + escapedClientId + '_uploadFilesButton').simulate('click');
				}
			});
		}
	},

	initAutoCompleteServerMode: function(autoComplete, hiddenClientId, clientId) {

		// When the autoComplete is cleared, set querying to false in order to cancel any queries that have been sent.
		autoComplete.on('clear', function(event) {
			autoComplete.set('source', []);
			autoComplete.set('querying', false);
		});

		// On query set querying to true, put the query in the hidden input, and send an ajax request to re-render the
		// component.
		autoComplete.on('query', function(event) {
			autoComplete.set('querying', true);
			document.getElementById(hiddenClientId).value = event.query;

			// jsf.ajax is a global javascript object in JSF.
			jsf.ajax.request(clientId, null, {
				render: clientId
			});
		});
	},

	setAutoCompleteServerResults: function(autoComplete, results, hiddenClientId) {

		// Make sure that a 'clear' event hasn't occured since the query was sent.
		if (autoComplete.get('querying')) {
			autoComplete.set('source', results);

			// This function causes the autoComplete to repopulate its list with the current results, and since the
			// source is in the client, a request is not sent to the server.
			// http://yuilibrary.com/yui/docs/api/classes/AutoCompleteList.html#method_sendRequest
			autoComplete.sendRequest();
		}

		// Set the value of the hidden input to null so that there is no query value if the component is re-rendered.
		document.getElementById(hiddenClientId).value = '';
	},

	initDateTimePickerMobile: function(dateTimePicker, inputClientId, max, min) {

		dateTimePicker.brokenDestroy = dateTimePicker.destroy;
		dateTimePicker.destroy = function(destroyAllNodes) {

			try {
				dateTimePicker.brokenDestroy(destroyAllNodes);
			}
			catch (e) {
				if (e instanceof TypeError) {
					// Workaround for "Cannot read property 'detach' of undefined" TypeError which is throw every
					// time a DatePickerNative is destroyed.
				}
				else {
					throw e;
				}
			}
		};

		if (max || min) {
			var input = document.getElementById(inputClientId);

			if (max) {
				input.setAttribute('max', max);
			}

			if (min) {
				input.setAttribute('min', min);
			}
		}
	},

	inputDateTimePickerSelect: function(A, escapeInputClientId, selectable, selection, valueChangeClientBehaviors) {

		var inputNode = A.one('#' + escapeInputClientId);

		if (selectable && selection) {
			inputNode.set('value', selection);
		}

		if (selectable && valueChangeClientBehaviors) {
			inputNode.simulate('change');
		}
	},

	initProgressBarServerMode: function(progressBar, clientId, pollingDelay, clientBehaviorsForPolling) {

		var hiddenClientId = clientId + '_hidden';

		// Define the start() method for progressBar which starts polling the server for progress.
		progressBar.startPolling = function() {

			var hiddenInput = document.getElementById(hiddenClientId),
			value = progressBar.get('value'),
			max = progressBar.get('max');

			// If the component is rendered and the component is not already polling and the component has not reached
			// the maximum value, then begin polling.
			if (hiddenInput && (hiddenInput.value !== 'true') && (value < max)) {

				hiddenInput.value = 'true';

				setTimeout(function poll() {

					setTimeout(function () {

						// Redefine hiddenInput and value because each time this function is run, current data
						// needs to be used.
						var hiddenInput = document.getElementById(hiddenClientId);

						// If the component is still rendered and hasn't completed polling, poll the server for
						// the current progress.
						if (hiddenInput && hiddenInput.value === 'true') {
							clientBehaviorsForPolling(poll);
						}
					}, pollingDelay);
				}, pollingDelay);
			}
		};

		// Define the stop() method for progressBar which stops polling the server for progress.
		progressBar.stopPolling = function() {

			var hiddenInput = document.getElementById(hiddenClientId);

			if (hiddenInput) {
				hiddenInput.value = '';
			}
		};

		// Stop polling if the progressBar is being destroyed.
		progressBar.on('destroy', progressBar.stopPolling);

		// Stop polling if the progress has completed.
		progressBar.on('complete', progressBar.stopPolling);
	},

	setProgressBarServerValue: function(hiddenClientId, progressBar, value) {

		var hiddenInput = document.getElementById(hiddenClientId);

		if (hiddenInput && hiddenInput.value === 'true') {
			progressBar.set('value', value);
		}
	},

	// Initilize a consistent API for progressBar when ajax is not enabled.
	initProgressBarClientMode: function(progressBar) {

		progressBar.startPolling = function() {
			// no-op
		};

		progressBar.stopPolling = function() {
			// no-op
		};
	}
};
