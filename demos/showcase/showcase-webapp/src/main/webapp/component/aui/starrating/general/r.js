Liferay.Portlet
		.onLoad({
			canEditTitle : false,
			columnPos : 0,
			isStatic : "end",
			namespacedId : "p_p_id_1_WAR_showcaseportlet_",
			portletId : "1_WAR_showcaseportlet",
			refreshURL : "\x2fc\x2fportal\x2frender_portlet\x3fp_l_id\x3d12503\x26p_p_id\x3d1_WAR_showcaseportlet\x26p_p_lifecycle\x3d0\x26p_t_lifecycle\x3d0\x26p_p_state\x3dnormal\x26p_p_mode\x3dview\x26p_p_col_id\x3dcolumn-1\x26p_p_col_pos\x3d0\x26p_p_col_count\x3d1\x26p_p_isolated\x3d1\x26currentURL\x3d\x252Fweb\x252Fguest\x252Fshowcase\x252F-\x252Fcomponent\x252Faui\x252Fstarrating\x252Fgeneral\x26_1_WAR_showcaseportlet_componentUseCase\x3dgeneral\x26_1_WAR_showcaseportlet_componentName\x3dstarrating\x26_1_WAR_showcaseportlet_componentPrefix\x3daui"
		});
Liferay.Portlet
		.onLoad({
			canEditTitle : false,
			columnPos : 0,
			isStatic : "end",
			namespacedId : "p_p_id_145_",
			portletId : "145",
			refreshURL : "\x2fc\x2fportal\x2frender_portlet\x3fp_l_id\x3d12503\x26p_p_id\x3d145\x26p_p_lifecycle\x3d0\x26p_t_lifecycle\x3d0\x26p_p_state\x3dnormal\x26p_p_mode\x3dview\x26p_p_col_id\x3dnull\x26p_p_col_pos\x3dnull\x26p_p_col_count\x3dnull\x26p_p_static\x3d1\x26p_p_isolated\x3d1\x26currentURL\x3d\x252Fweb\x252Fguest\x252Fshowcase\x252F-\x252Fcomponent\x252Faui\x252Fstarrating\x252Fgeneral\x26_1_WAR_showcaseportlet_componentUseCase\x3dgeneral\x26_1_WAR_showcaseportlet_componentName\x3dstarrating\x26_1_WAR_showcaseportlet_componentPrefix\x3daui"
		});
AUI()
		.use(
				"aui-ace-editor",
				"aui-base",
				"aui-rating",
				"aui-tabview",
				"event-outside",
				"liferay-menu",
				"liferay-notice",
				"liferay-poller",
				function(a) {
					(function() {
						var c;
						Liferay
								.component(
										"_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt45",
										function() {
											if (!c) {
												c = new a.Rating(
														{
															boundingBox : "#_1_WAR_showcaseportlet_\\:j_idt6\\:j_idt19\\:j_idt24\\:j_idt45",
															defaultSelected : 3,
															disabled : false,
															id : "j_idt45",
															label : "Liferay Faces Alloy, by default, is: ",
															rendered : true,
															render : true,
															after : {},
															on : {}
														})
											}
											return c
										});
						Liferay
								.component("_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt45");
						var c = Liferay
								.component("_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt24_j_idt45");
						var b = c.get("selectedIndex");
						document
								.getElementsByName("_1_WAR_showcaseportlet_:j_idt6:j_idt19:j_idt24:j_idt45")[0].value = "Powerful Integration";
						c
								.on(
										"click",
										function(d) {
											var e = (d.target.get("value") == "-1") ? ""
													: d.target.get("value");
											document
													.getElementsByName("_1_WAR_showcaseportlet_:j_idt6:j_idt19:j_idt24:j_idt45")[0].value = e
										})
					})();
					(function() {
						var b;
						Liferay
								.component(
										"_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt58_0_j_idt60",
										function() {
											if (!b) {
												b = new a.AceEditor(
														{
															boundingBox : "#_1_WAR_showcaseportlet_\\:j_idt6\\:j_idt19\\:j_idt58\\:0\\:j_idt60",
															id : "j_idt60",
															mode : "xml",
															readOnly : true,
															rendered : true,
															showPrintMargin : false,
															useWrapMode : false,
															value : '<ui:composition xmlns="http://www.w3.org/1999/xhtml"\n	xmlns:aui="http://liferay.com/faces/aui"\n	xmlns:h="http://java.sun.com/jsf/html"\n	xmlns:ui="http://java.sun.com/jsf/facelets"\n	xmlns:f="http://java.sun.com/jsf/core"\n	xmlns:showcase="http://java.sun.com/jsf/composite/showcase"\n	xmlns:c="http://java.sun.com/jsp/jstl/core" >\n\n	<h:form>\n		<showcase:componentRenderedCheckbox />\n		<showcase:componentRequiredCheckbox />\n		<br />\n		<aui:starRating id="asdf" rendered="false"\n			required="#{showcaseModelBean.selectedComponent.required}" \n			value="#{ratingModelBean.favoriteId}" label="Liferay Faces Alloy, in general, is: ">\n			<f:converter converterId="javax.faces.Long" />\n			<f:selectItem itemLabel="Compatible" itemValue="10" />\n			<f:selectItem itemLabel="Enterprise Ready" itemValue="20" />\n			<f:selectItem itemLabel="Powerful Integration" itemValue="30" />\n			<f:selectItem itemLabel="Lightweight" itemValue="40" />\n			<f:selectItem itemLabel="Open Source" itemValue="50" />\n			<f:selectItem itemLabel="Complete Platform" itemValue="60" />\n		</aui:starRating>\n		<br /><br />\n		<h:message for="asdf" />\n		<h:outputText value="#{i18n[\'model-value\']}: " />\n		<h:outputText value="#{ratingModelBean.favoriteId}" />\n		<br />\n		<br />\n		\n		<aui:starRating defaultSelected="3" value="#{ratingModelBean.favorite}"\n			label="Liferay Faces Alloy, by default, is: ">\n			<f:selectItem itemLabel="Compatible" itemValue="Compatible" />\n			<f:selectItem itemLabel="Enterprise Ready" itemValue="Enterprise Ready" />\n			<f:selectItem itemLabel="Powerful Integration" itemValue="Powerful Integration" />\n			<f:selectItem itemLabel="Lightweight" itemValue="Lightweight" />\n			<f:selectItem itemLabel="Open Source" itemValue="Open Source" />\n			<f:selectItem itemLabel="Complete Platform" itemValue="Complete Platform" />\n		</aui:starRating>\n		<br /><br />\n		<h:outputText value="#{i18n[\'model-value\']}: " />\n		<h:outputText value="#{ratingModelBean.favorite}" />\n		<br />\n		<br />\n		\n		<h:commandButton value="Submit">\n			<f:ajax execute="@form" render="@form" />\n		</h:commandButton>\n		\n	</h:form>\n</ui:composition>',
															render : true,
															width : 900,
															after : {},
															on : {}
														})
											}
											return b
										});
						Liferay
								.component("_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt58_0_j_idt60")
					})();
					(function() {
						var b;
						Liferay
								.component(
										"_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt58_1_j_idt60",
										function() {
											if (!b) {
												b = new a.AceEditor(
														{
															boundingBox : "#_1_WAR_showcaseportlet_\\:j_idt6\\:j_idt19\\:j_idt58\\:1\\:j_idt60",
															id : "j_idt60",
															mode : "java",
															readOnly : true,
															rendered : true,
															showPrintMargin : false,
															useWrapMode : false,
															value : 'package com.liferay.faces.demos.bean;\n\nimport java.util.List;\n\nimport javax.faces.bean.ManagedBean;\nimport javax.faces.bean.ManagedProperty;\nimport javax.faces.bean.RequestScoped;\n\nimport com.liferay.faces.demos.dto.LiferayBenefit;\nimport com.liferay.faces.demos.service.LiferayBenefitService;\n\n@ManagedBean\n@RequestScoped\npublic class RatingModelBean {\n	\n	private Boolean like;\n	\n	public Boolean getLike() {\n		return like;\n	}\n\n	public void setLike(Boolean like) {\n		this.like = like;\n	}\n\n	private String favorite;\n	\n	public String getFavorite() {\n		return favorite;\n	}\n\n	public void setFavorite(String favorite) {\n		this.favorite = favorite;\n	}\n	\n	private Long favoriteId;\n\n	public Long getFavoriteId() {\n		return favoriteId;\n	}\n	\n	// a long line a long line a long line a long line a long line a long line a long line a long line a long line a long line a long line a long line\n\n	public void setFavoriteId(Long favoriteId) {\n		this.favoriteId = favoriteId;\n	}\n\n	@ManagedProperty(name = "liferayBenefitService", value = "#{liferayBenefitService}")\n	private LiferayBenefitService liferayBenefitService;\n\n	public List<LiferayBenefit> getLiferayBenefits() {\n		return liferayBenefitService.getLiferayBenefits();\n	}\n\n	public void setLiferayBenefitService(LiferayBenefitService liferayBenefitService) {\n		this.liferayBenefitService = liferayBenefitService;\n	}\n}',
															render : true,
															width : 900,
															after : {},
															on : {}
														})
											}
											return b
										});
						Liferay
								.component("_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt58_1_j_idt60")
					})();
					(function() {
						var b;
						Liferay
								.component(
										"_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt58",
										function() {
											if (!b) {
												b = new a.TabView(
														{
															id : "j_idt58",
															rendered : true,
															srcNode : "#_1_WAR_showcaseportlet_\\:j_idt6\\:j_idt19\\:j_idt58",
															render : true,
															after : {},
															on : {}
														})
											}
											return b
										});
						Liferay
								.component("_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt58")
					})();
					(function() {
						Liferay.Util.addInputType();
						Liferay.Portlet.ready(function(b, c) {
							Liferay.Util.addInputType(c)
						});
						if (a.UA.mobile) {
							Liferay.Util.addInputCancel()
						}
					})();
					(function() {
						new Liferay.Menu();
						var b = Liferay.Data.notices;
						for (var c = 1; c < b.length; c++) {
							new Liferay.Notice(b[c])
						}
					})();
					(function() {
						a
								.one("#_145_dockbar")
								.delegate(
										"click",
										function(d) {
											var c = d.currentTarget;
											var f = c.attr("data-navId");
											var b = a.one("#" + f
													+ "NavbarCollapse");
											if (b) {
												var e = Liferay.Data._145_dockbarHandle;
												if (b.hasClass("open") && e) {
													e.detach();
													e = null
												} else {
													e = b
															.on(
																	"mousedownoutside",
																	function(g) {
																		if (!c
																				.contains(g.target)) {
																			Liferay.Data._145_dockbarHandle = null;
																			e
																					.detach();
																			b
																					.removeClass("open")
																		}
																	})
												}
												b.toggleClass("open");
												Liferay.Data._145_dockbarHandle = e
											}
										}, ".btn-navbar")
					})()
				});