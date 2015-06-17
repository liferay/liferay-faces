/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.alloy.component.media;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class MediaBase extends UIComponentBase implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.media.Media";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.media.MediaRenderer";

	// Protected Enumerations
	protected enum MediaPropertyKeys {
		autoplay,
		contentType,
		controls,
		degrade,
		flashPlayer,
		flashPlayerVersion,
		library,
		loop,
		muted,
		name,
		onabort,
		onblur,
		oncanplay,
		oncanplaythrough,
		onclick,
		ondblclick,
		ondurationchange,
		onemptied,
		onended,
		onerror,
		onfocus,
		onkeydown,
		onkeypress,
		onkeyup,
		onloadeddata,
		onloadedmetadata,
		onloadstart,
		onmousedown,
		onmousemove,
		onmouseout,
		onmouseover,
		onmouseup,
		onpause,
		onplay,
		onplaying,
		onprogress,
		onratechange,
		onseeked,
		onseeking,
		onstalled,
		onsuspend,
		ontimeupdate,
		onvolumechange,
		onwaiting,
		preload,
		style,
		styleClass,
		value
	}

	public MediaBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isAutoplay() {
		return (Boolean) getStateHelper().eval(MediaPropertyKeys.autoplay, false);
	}

	public void setAutoplay(boolean autoplay) {
		getStateHelper().put(MediaPropertyKeys.autoplay, autoplay);
	}

	public String getContentType() {
		return (String) getStateHelper().eval(MediaPropertyKeys.contentType, null);
	}

	public void setContentType(String contentType) {
		getStateHelper().put(MediaPropertyKeys.contentType, contentType);
	}

	public boolean isControls() {
		return (Boolean) getStateHelper().eval(MediaPropertyKeys.controls, false);
	}

	public void setControls(boolean controls) {
		getStateHelper().put(MediaPropertyKeys.controls, controls);
	}

	public boolean isDegrade() {
		return (Boolean) getStateHelper().eval(MediaPropertyKeys.degrade, true);
	}

	public void setDegrade(boolean degrade) {
		getStateHelper().put(MediaPropertyKeys.degrade, degrade);
	}

	public Object getFlashPlayer() {
		return (Object) getStateHelper().eval(MediaPropertyKeys.flashPlayer, null);
	}

	public void setFlashPlayer(Object flashPlayer) {
		getStateHelper().put(MediaPropertyKeys.flashPlayer, flashPlayer);
	}

	public String getFlashPlayerVersion() {
		return (String) getStateHelper().eval(MediaPropertyKeys.flashPlayerVersion, "9,0,0,0");
	}

	public void setFlashPlayerVersion(String flashPlayerVersion) {
		getStateHelper().put(MediaPropertyKeys.flashPlayerVersion, flashPlayerVersion);
	}

	public String getLibrary() {
		return (String) getStateHelper().eval(MediaPropertyKeys.library, null);
	}

	public void setLibrary(String library) {
		getStateHelper().put(MediaPropertyKeys.library, library);
	}

	public boolean isLoop() {
		return (Boolean) getStateHelper().eval(MediaPropertyKeys.loop, false);
	}

	public void setLoop(boolean loop) {
		getStateHelper().put(MediaPropertyKeys.loop, loop);
	}

	public boolean isMuted() {
		return (Boolean) getStateHelper().eval(MediaPropertyKeys.muted, false);
	}

	public void setMuted(boolean muted) {
		getStateHelper().put(MediaPropertyKeys.muted, muted);
	}

	public String getName() {
		return (String) getStateHelper().eval(MediaPropertyKeys.name, null);
	}

	public void setName(String name) {
		getStateHelper().put(MediaPropertyKeys.name, name);
	}

	public String getOnabort() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onabort, null);
	}

	public void setOnabort(String onabort) {
		getStateHelper().put(MediaPropertyKeys.onabort, onabort);
	}

	public String getOnblur() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onblur, null);
	}

	public void setOnblur(String onblur) {
		getStateHelper().put(MediaPropertyKeys.onblur, onblur);
	}

	public String getOncanplay() {
		return (String) getStateHelper().eval(MediaPropertyKeys.oncanplay, null);
	}

	public void setOncanplay(String oncanplay) {
		getStateHelper().put(MediaPropertyKeys.oncanplay, oncanplay);
	}

	public String getOncanplaythrough() {
		return (String) getStateHelper().eval(MediaPropertyKeys.oncanplaythrough, null);
	}

	public void setOncanplaythrough(String oncanplaythrough) {
		getStateHelper().put(MediaPropertyKeys.oncanplaythrough, oncanplaythrough);
	}

	public String getOnclick() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onclick, null);
	}

	public void setOnclick(String onclick) {
		getStateHelper().put(MediaPropertyKeys.onclick, onclick);
	}

	public String getOndblclick() {
		return (String) getStateHelper().eval(MediaPropertyKeys.ondblclick, null);
	}

	public void setOndblclick(String ondblclick) {
		getStateHelper().put(MediaPropertyKeys.ondblclick, ondblclick);
	}

	public String getOndurationchange() {
		return (String) getStateHelper().eval(MediaPropertyKeys.ondurationchange, null);
	}

	public void setOndurationchange(String ondurationchange) {
		getStateHelper().put(MediaPropertyKeys.ondurationchange, ondurationchange);
	}

	public String getOnemptied() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onemptied, null);
	}

	public void setOnemptied(String onemptied) {
		getStateHelper().put(MediaPropertyKeys.onemptied, onemptied);
	}

	public String getOnended() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onended, null);
	}

	public void setOnended(String onended) {
		getStateHelper().put(MediaPropertyKeys.onended, onended);
	}

	public String getOnerror() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onerror, null);
	}

	public void setOnerror(String onerror) {
		getStateHelper().put(MediaPropertyKeys.onerror, onerror);
	}

	public String getOnfocus() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onfocus, null);
	}

	public void setOnfocus(String onfocus) {
		getStateHelper().put(MediaPropertyKeys.onfocus, onfocus);
	}

	public String getOnkeydown() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onkeydown, null);
	}

	public void setOnkeydown(String onkeydown) {
		getStateHelper().put(MediaPropertyKeys.onkeydown, onkeydown);
	}

	public String getOnkeypress() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onkeypress, null);
	}

	public void setOnkeypress(String onkeypress) {
		getStateHelper().put(MediaPropertyKeys.onkeypress, onkeypress);
	}

	public String getOnkeyup() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onkeyup, null);
	}

	public void setOnkeyup(String onkeyup) {
		getStateHelper().put(MediaPropertyKeys.onkeyup, onkeyup);
	}

	public String getOnloadeddata() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onloadeddata, null);
	}

	public void setOnloadeddata(String onloadeddata) {
		getStateHelper().put(MediaPropertyKeys.onloadeddata, onloadeddata);
	}

	public String getOnloadedmetadata() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onloadedmetadata, null);
	}

	public void setOnloadedmetadata(String onloadedmetadata) {
		getStateHelper().put(MediaPropertyKeys.onloadedmetadata, onloadedmetadata);
	}

	public String getOnloadstart() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onloadstart, null);
	}

	public void setOnloadstart(String onloadstart) {
		getStateHelper().put(MediaPropertyKeys.onloadstart, onloadstart);
	}

	public String getOnmousedown() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onmousedown, null);
	}

	public void setOnmousedown(String onmousedown) {
		getStateHelper().put(MediaPropertyKeys.onmousedown, onmousedown);
	}

	public String getOnmousemove() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onmousemove, null);
	}

	public void setOnmousemove(String onmousemove) {
		getStateHelper().put(MediaPropertyKeys.onmousemove, onmousemove);
	}

	public String getOnmouseout() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onmouseout, null);
	}

	public void setOnmouseout(String onmouseout) {
		getStateHelper().put(MediaPropertyKeys.onmouseout, onmouseout);
	}

	public String getOnmouseover() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onmouseover, null);
	}

	public void setOnmouseover(String onmouseover) {
		getStateHelper().put(MediaPropertyKeys.onmouseover, onmouseover);
	}

	public String getOnmouseup() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onmouseup, null);
	}

	public void setOnmouseup(String onmouseup) {
		getStateHelper().put(MediaPropertyKeys.onmouseup, onmouseup);
	}

	public String getOnpause() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onpause, null);
	}

	public void setOnpause(String onpause) {
		getStateHelper().put(MediaPropertyKeys.onpause, onpause);
	}

	public String getOnplay() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onplay, null);
	}

	public void setOnplay(String onplay) {
		getStateHelper().put(MediaPropertyKeys.onplay, onplay);
	}

	public String getOnplaying() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onplaying, null);
	}

	public void setOnplaying(String onplaying) {
		getStateHelper().put(MediaPropertyKeys.onplaying, onplaying);
	}

	public String getOnprogress() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onprogress, null);
	}

	public void setOnprogress(String onprogress) {
		getStateHelper().put(MediaPropertyKeys.onprogress, onprogress);
	}

	public String getOnratechange() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onratechange, null);
	}

	public void setOnratechange(String onratechange) {
		getStateHelper().put(MediaPropertyKeys.onratechange, onratechange);
	}

	public String getOnseeked() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onseeked, null);
	}

	public void setOnseeked(String onseeked) {
		getStateHelper().put(MediaPropertyKeys.onseeked, onseeked);
	}

	public String getOnseeking() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onseeking, null);
	}

	public void setOnseeking(String onseeking) {
		getStateHelper().put(MediaPropertyKeys.onseeking, onseeking);
	}

	public String getOnstalled() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onstalled, null);
	}

	public void setOnstalled(String onstalled) {
		getStateHelper().put(MediaPropertyKeys.onstalled, onstalled);
	}

	public String getOnsuspend() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onsuspend, null);
	}

	public void setOnsuspend(String onsuspend) {
		getStateHelper().put(MediaPropertyKeys.onsuspend, onsuspend);
	}

	public String getOntimeupdate() {
		return (String) getStateHelper().eval(MediaPropertyKeys.ontimeupdate, null);
	}

	public void setOntimeupdate(String ontimeupdate) {
		getStateHelper().put(MediaPropertyKeys.ontimeupdate, ontimeupdate);
	}

	public String getOnvolumechange() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onvolumechange, null);
	}

	public void setOnvolumechange(String onvolumechange) {
		getStateHelper().put(MediaPropertyKeys.onvolumechange, onvolumechange);
	}

	public String getOnwaiting() {
		return (String) getStateHelper().eval(MediaPropertyKeys.onwaiting, null);
	}

	public void setOnwaiting(String onwaiting) {
		getStateHelper().put(MediaPropertyKeys.onwaiting, onwaiting);
	}

	public String getPreload() {
		return (String) getStateHelper().eval(MediaPropertyKeys.preload, null);
	}

	public void setPreload(String preload) {
		getStateHelper().put(MediaPropertyKeys.preload, preload);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(MediaPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(MediaPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(MediaPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(MediaPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-media");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(MediaPropertyKeys.styleClass, styleClass);
	}

	public Object getValue() {
		return (Object) getStateHelper().eval(MediaPropertyKeys.value, null);
	}

	public void setValue(Object value) {
		getStateHelper().put(MediaPropertyKeys.value, value);
	}
}
//J+
