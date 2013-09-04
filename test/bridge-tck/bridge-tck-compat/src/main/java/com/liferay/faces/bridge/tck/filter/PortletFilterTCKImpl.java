package com.liferay.faces.bridge.tck.filter;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;

import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;

public class PortletFilterTCKImpl implements RenderFilter {

	// Private Constants
	private static final boolean RESIN_DETECTED = ProductMap.getInstance().get(ProductConstants.RESIN).isDetected();

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws PortletException {
	}

	public void doFilter(RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain) throws IOException, PortletException {
		
		if (RESIN_DETECTED) {
			
			// Workaround for FACES-1629
			renderRequest = new RenderRequestResinImpl(renderRequest);
		}

		filterChain.doFilter(renderRequest, renderResponse);
	}

}
