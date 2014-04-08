// <![CDATA[

Liferay.Portlet
		.onLoad({
			canEditTitle : false,
			columnPos : 0,
			isStatic : 'end',
			namespacedId : 'p_p_id_1_WAR_showcaseportlet_',
			portletId : '1_WAR_showcaseportlet',
			refreshURL : '\x2fc\x2fportal\x2frender_portlet\x3fp_l_id\x3d12503\x26p_p_id\x3d1_WAR_showcaseportlet\x26p_p_lifecycle\x3d0\x26p_t_lifecycle\x3d0\x26p_p_state\x3dnormal\x26p_p_mode\x3dview\x26p_p_col_id\x3dcolumn-1\x26p_p_col_pos\x3d0\x26p_p_col_count\x3d1\x26p_p_isolated\x3d1\x26currentURL\x3d\x252Fweb\x252Fguest\x252Fshowcase\x252F-\x252Fcomponent\x252Faui\x252Fthumbrating\x252Fgeneral\x253Fstrip\x253D0\x26_1_WAR_showcaseportlet_componentUseCase\x3dgeneral\x26_1_WAR_showcaseportlet_componentName\x3dthumbrating\x26_1_WAR_showcaseportlet_componentPrefix\x3daui\x26strip\x3d0'
		});

Liferay.Portlet
		.onLoad({
			canEditTitle : false,
			columnPos : 0,
			isStatic : 'end',
			namespacedId : 'p_p_id_145_',
			portletId : '145',
			refreshURL : '\x2fc\x2fportal\x2frender_portlet\x3fp_l_id\x3d12503\x26p_p_id\x3d145\x26p_p_lifecycle\x3d0\x26p_t_lifecycle\x3d0\x26p_p_state\x3dnormal\x26p_p_mode\x3dview\x26p_p_col_id\x3dnull\x26p_p_col_pos\x3dnull\x26p_p_col_count\x3dnull\x26p_p_static\x3d1\x26p_p_isolated\x3d1\x26currentURL\x3d\x252Fweb\x252Fguest\x252Fshowcase\x252F-\x252Fcomponent\x252Faui\x252Fthumbrating\x252Fgeneral\x253Fstrip\x253D0\x26_1_WAR_showcaseportlet_componentUseCase\x3dgeneral\x26_1_WAR_showcaseportlet_componentName\x3dthumbrating\x26_1_WAR_showcaseportlet_componentPrefix\x3daui\x26strip\x3d0'
		});
AUI()
		.use(
				'aui-ace-editor',
				'aui-base',
				'aui-rating',
				'aui-tabview',
				'event-outside',
				'liferay-menu',
				'liferay-notice',
				'liferay-poller',
				function(A) {
					(function() {
						var _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt25;
						Liferay
								.component(
										'_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt25',
										function() {
											if (!_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt25) {
												_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt25 = new A.ThumbRating(
														{
															disabled : false,
															id : "j_idt25",
															label : "Liferay Faces Alloy, in general, is: ",
															rendered : true,
															render : true,
															after : {},
															on : {}
														});
											}
											return _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt25;
										});
						Liferay
								.component('_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt25');
						var _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt25 = Liferay
								.component('_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt25');
						var defaultSelectedIndex = _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt25
								.get('selectedIndex');
						document
								.getElementsByName('_1_WAR_showcaseportlet_:j_idt6:j_idt19:j_idt24:j_idt25')[0].value = '';
						_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt25
								.on(
										'click',
										function(event) {
											var newValue = (event.target
													.get('value') == '-1') ? ''
													: event.target.get('value');
											document
													.getElementsByName('_1_WAR_showcaseportlet_:j_idt6:j_idt19:j_idt24:j_idt25')[0].value = newValue;
										});
					})();
					(function() {
						var _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34_0_j_idt36;
						Liferay
								.component(
										'_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34_0_j_idt36',
										function() {
											if (!_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34_0_j_idt36) {
												_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34_0_j_idt36 = new A.AceEditor(
														{
															boundingBox : "#_1_WAR_showcaseportlet_\\:j_idt6\\:j_idt19\\:j_idt34\\:0\\:j_idt36",
															id : "j_idt36",
															mode : "xml",
															readOnly : true,
															rendered : true,
															showPrintMargin : false,
															useWrapMode : false,
															value : "<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"\n	xmlns:aui=\"http://liferay.com/faces/aui\"\n	xmlns:h=\"http://java.sun.com/jsf/html\"\n	xmlns:ui=\"http://java.sun.com/jsf/facelets\"\n	xmlns:f=\"http://java.sun.com/jsf/core\"\n	xmlns:c=\"http://java.sun.com/jsp/jstl/core\" >\n\n	<h:form>\n		<aui:thumbRating value=\"#{ratingModelBean.like}\" label=\"Liferay Faces Alloy, in general, is: \">\n			<f:selectItem itemLabel=\"liked\" itemValue=\"0\" />\n			<f:selectItem itemLabel=\"disliked\" itemValue=\"1\" />\n		</aui:thumbRating>\n		<br />\n		<h:outputText value=\"#{i18n['model-value']}: \" /><h:outputText value=\"#{ratingModelBean.like}\" />\n		<br />\n		<br />\n		<h:commandButton value=\"Submit\">\n			<f:ajax execute=\"@form\" render=\"@form\" />\n		</h:commandButton>\n	</h:form>\n</ui:composition>",
															render : true,
															width : 900,
															after : {},
															on : {}
														});
											}
											return _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34_0_j_idt36;
										});
						Liferay
								.component('_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34_0_j_idt36');
					})();
					(function() {
						var _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34;
						Liferay
								.component(
										'_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34',
										function() {
											if (!_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34) {
												_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34 = new A.TabView(
														{
															id : "j_idt34",
															rendered : true,
															srcNode : "#_1_WAR_showcaseportlet_\\:j_idt6\\:j_idt19\\:j_idt34",
															render : true,
															after : {},
															on : {}
														});
											}
											return _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34;
										});
						Liferay
								.component('_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt34');
					})();
					(function() {
						Liferay.Util.addInputType();

						Liferay.Portlet.ready(function(portletId, node) {
							Liferay.Util.addInputType(node);
						});

						if (A.UA.mobile) {
							Liferay.Util.addInputCancel();
						}
					})();
					(function() {
						new Liferay.Menu();

						var liferayNotices = Liferay.Data.notices;

						for (var i = 1; i < liferayNotices.length; i++) {
							new Liferay.Notice(liferayNotices[i]);
						}

					})();
					(function() {
						A
								.one('#_145_dockbar')
								.delegate(
										'click',
										function(event) {
											var btnNavbar = event.currentTarget;

											var navId = btnNavbar
													.attr('data-navId');

											var navbarCollapse = A.one('#'
													+ navId + 'NavbarCollapse');

											if (navbarCollapse) {
												var handle = Liferay.Data['_145_dockbarHandle'];

												if (navbarCollapse
														.hasClass('open')
														&& handle) {
													handle.detach();

													handle = null;
												} else {
													handle = navbarCollapse
															.on(
																	'mousedownoutside',
																	function(
																			event) {
																		if (!btnNavbar
																				.contains(event.target)) {
																			Liferay.Data['_145_dockbarHandle'] = null;

																			handle
																					.detach();

																			navbarCollapse
																					.removeClass('open');
																		}
																	});
												}

												navbarCollapse
														.toggleClass('open');

												Liferay.Data['_145_dockbarHandle'] = handle;
											}
										}, '.btn-navbar');
					})();
				});
// ]]>
