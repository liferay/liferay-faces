if (A.Uploader.TYPE != 'none' && !A.UA.ios) {

	var uploadComplete = false, _clientVarName_ = Liferay.component('_clientKey_'), contentTypes = A
			.Array(_contentTypes_);
	A.Uploader.HTML5FILEFIELD_TEMPLATE = "<input type='file' style='visibility:hidden; width:0.1px; height: 0px;'>";
	_clientVarName_.render('#_escapedClientId__selectFilesBox');
	A.one('#_escapedClientId__selectFilesBox').one('>div').removeAttribute('style');
	A.one('#_escapedClientId__selectFilesBox').one('>div>div>button').removeAttribute('style');
	A.one('#_escapedClientId__uploadFilesButton').on(
			'click',
			function(e) {
				var fileList = _clientVarName_.get('fileList'), formNode, viewStateNode;
				formNode = A.one('#_escapedFormClientId_');
				viewStateNode = formNode.one('>input[name="javax.faces.ViewState"]');
				if (!viewStateNode) {
					viewStateNode = formNode.one('>input[name="_namingContainerId_javax.faces.ViewState"]');
				}
				if (!uploadComplete && fileList.length > 0) {
					e.preventDefault();
					_clientVarName_.uploadAll('_partialActionURL_', {
						'_namingContainerId__formClientId_' : '_formClientId_',
						'_namingContainerId_Faces-Request' : 'partial/ajax',
						'_namingContainerId_javax.faces.behavior.event' : 'valueChange',
						'_namingContainerId_javax.faces.partial.ajax' : 'true',
						'_namingContainerId_javax.faces.partial.event' : 'click',
						'_namingContainerId_javax.faces.partial.execute' : '_execute_',
						'_namingContainerId_javax.faces.partial.render' : '',
						'_namingContainerId_javax.faces.source' : '_clientId_',
						'_namingContainerId_javax.faces.ViewState' : viewStateNode.get('value') });
				}
			});

	_clientVarName_.on('uploadcomplete', function(event) {
		jsf.ajax.request('_clientId_', 'valueChange', { execute : '@none',
		render : '_render_' });
	});

	_clientVarName_.on('uploadprogress', function(event) {
		var tableRow = A.one('#' + event.file.get('id') + '_row');
		tableRow.one('.percent-complete').set('text', event.percentLoaded + '%');
	});

	_clientVarName_.on('uploadstart', function(event) {
		_clientVarName_.set('enabled', false);
		A.one('#_escapedClientId__uploadFilesButton').addClass('yui3-button-disabled').detach('click');
	});

	_clientVarName_.after('fileselect', function(event) {
		var fileList = event.fileList, fileTable = A.one('#_escapedClientId__table'), fileTableBody = fileTable
				.one('tbody'), appendNewFiles = _clientVarName_.get('appendNewFiles');
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
			if ((contentTypes.length == 0) || contentTypes.indexOf(curFile.get('type')) >= 0) {
				tableRow += '<td class="yui3-datatable-cell">'
			} else {
				tableRow += '<td class="yui3-datatable-cell text-error">';
			}
			tableRow += curFile.get('type') + '</td>';
			if (curFile.get('size') <= _maxFileSize_) {
				tableRow += '<td class="yui3-datatable-cell">'
			} else {
				tableRow += '<td class="yui3-datatable-cell text-error">'
			}
			tableRow += curFile.get('size') + '</td>';
			tableRow += '<td class="yui3-datatable-cell percent-complete">_notStartedMessage_</td>';
			tableRow += '</tr>';
			fileTableBody.append(tableRow);
		});
		fileTable.one('tfoot').setStyle('display', 'none');
		if (_auto_) {
			A.one('#_escapedClientId__uploadFilesButton').simulate('click');
		}
	});
}
