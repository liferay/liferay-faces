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
package com.liferay.faces.util.application;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.util.io.Filterable;
import com.liferay.faces.util.io.ResourceOutputStream;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This is an abstract class provides base functionality for a {@link ResourceHandler} that can write the contents of a
 * {@link Resource} to the underlying response.
 *
 * @author  Neil Griffin
 */
public class ResourceHandlerWrapperBase extends ResourceHandlerWrapper {

	// Public Constants
	public static final int DEFAULT_BUFFER_SIZE = 1024;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceHandlerWrapperBase.class);

	// Private Data Members
	private ResourceHandler wrappedResourceHandler;

	public ResourceHandlerWrapperBase(ResourceHandler resourceHandler) {
		this.wrappedResourceHandler = resourceHandler;
	}

	protected void handleResource(FacesContext facesContext, Resource resource) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		String resourceName = resource.getResourceName();
		String libraryName = resource.getLibraryName();
		boolean needsUpdate = resource.userAgentNeedsUpdate(facesContext);

		if (!isAbleToSetHttpStatusCode(facesContext)) {

			if (!needsUpdate) {
				needsUpdate = true;
				logger.debug(
					"Unable to set the status code to HttpServletResponse.SC_NOT_MODIFIED ({0}) for resourceName=[{1}]",
					HttpServletResponse.SC_NOT_MODIFIED, resourceName);
			}
		}

		if (needsUpdate) {
			logger.trace("Handling - Resource was either modified or has not yet been downloaded.");

			ReadableByteChannel readableByteChannel = null;
			WritableByteChannel writableByteChannel = null;
			InputStream inputStream = null;
			int bufferSize = getBufferSize(facesContext);
			ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);

			try {

				// Open an input stream in order to read the resource's contents/data.
				inputStream = resource.getInputStream();

				if (inputStream != null) {

					// Set the response headers by copying them from the resource.
					Map<String, String> responseHeaderMap = resource.getResponseHeaders();

					if (responseHeaderMap != null) {
						Iterator<Map.Entry<String, String>> itr = responseHeaderMap.entrySet().iterator();

						while (itr.hasNext()) {
							Map.Entry<String, String> mapEntry = itr.next();
							String name = mapEntry.getKey();
							String value = mapEntry.getValue();
							externalContext.setResponseHeader(name, value);

							if (logger.isDebugEnabled()) {

								// Surround with isDebugEnabled check in order to avoid unnecessary creation
								// of object array.
								logger.debug("Handling - COPIED resource header name=[{0}] value=[{1}]",
									new Object[] { name, value });
							}
						}
					}

					// Set the response Content-Type header.
					String responseContentType = resource.getContentType();
					logger.trace("Handling - responseContentType=[{0}]", responseContentType);

					if (responseContentType != null) {
						externalContext.setResponseContentType(responseContentType);
					}

					// Rather than write the input stream directly to the response, write it to an
					// buffered output stream so that the length can be calculated for the
					// Content-Length header. See: http://issues.liferay.com/browse/FACES-1207
					ResourceOutputStream resourceOutputStream = getResourceOutputStream(resource, bufferSize);

					int responseContentLength = 0;
					readableByteChannel = Channels.newChannel(inputStream);
					writableByteChannel = Channels.newChannel(resourceOutputStream);

					int bytesRead = readableByteChannel.read(byteBuffer);

					if (logger.isTraceEnabled()) {

						// Surround with isTraceEnabled check in order to avoid unnecessary conversion of
						// int to String.
						logger.trace("Handling - bytesRead=[{0}]", Integer.toString(bytesRead));
					}

					int bytesWritten = 0;

					while (bytesRead != -1) {
						byteBuffer.rewind();
						byteBuffer.limit(bytesRead);

						do {
							bytesWritten += writableByteChannel.write(byteBuffer);
						}
						while (bytesWritten < responseContentLength);

						byteBuffer.clear();
						responseContentLength += bytesRead;
						bytesRead = readableByteChannel.read(byteBuffer);

						if (logger.isTraceEnabled()) {

							// Surround with isTraceEnabled check in order to avoid unnecessary conversion
							// of int to String.
							logger.trace("Handling - MORE bytesRead=[{0}]", Integer.toString(bytesRead));
						}
					}

					if (resourceOutputStream instanceof Filterable) {
						Filterable filterable = (Filterable) resourceOutputStream;
						filterable.filter();
					}

					responseContentLength = resourceOutputStream.size();

					// Now that we know how big the file is, set the response Content-Length header and the status.
					externalContext.setResponseContentLength(responseContentLength);
					externalContext.setResponseStatus(HttpServletResponse.SC_OK);

					// Set the response buffer size.
					externalContext.setResponseBufferSize(responseContentLength);

					if (logger.isTraceEnabled()) {

						// Surround with isTraceEnabled check in order to avoid unnecessary conversion of
						// int to String.
						logger.trace("Handling - responseBufferSize=[{0}]", Integer.toString(responseContentLength));
					}

					// Write the data to the response.
					resourceOutputStream.writeTo(externalContext.getResponseOutputStream());
					resourceOutputStream.flush();
					resourceOutputStream.close();

					if (logger.isDebugEnabled()) {
						logger.debug(
							"HANDLED (SC_OK) resourceName=[{0}], libraryName[{1}], responseContentType=[{4}], responseContentLength=[{5}]",
							new Object[] { resourceName, libraryName, responseContentType, responseContentLength });
					}
				}
				else {
					externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
					logger.error(
						"NOT HANDLED (SC_NOT_FOUND) because InputStream was null - resourceName=[{0}], libraryName[{1}]",
						new Object[] { resourceName, libraryName });
				}
			}
			catch (IOException e) {
				externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
				logger.error("NOT HANDLED (SC_NOT_FOUND) resourceName=[{0}], libraryName[{1}], errorMessage=[{4}]",
					new Object[] { resourceName, libraryName, e.getMessage() }, e);
			}
			finally {

				if (writableByteChannel != null) {
					writableByteChannel.close();
				}

				if (readableByteChannel != null) {
					readableByteChannel.close();
				}

				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		else {

			externalContext.setResponseStatus(HttpServletResponse.SC_NOT_MODIFIED);

			if (logger.isDebugEnabled()) {

				// Surround with isDebugEnabled check in order to avoid unnecessary creation of object array.
				logger.debug("HANDLED (SC_NOT_MODIFIED) resourceName=[{0}], libraryName[{1}]",
					new Object[] { resourceName, libraryName });
			}

		}
	}

	protected int getBufferSize(FacesContext facesContext) {
		return DEFAULT_BUFFER_SIZE;
	}

	protected boolean isAbleToSetHttpStatusCode(FacesContext facesContext) {
		return true;
	}

	/**
	 * This is a factory-style method that returns a {@link ResourceOutputStream} for the specified {@link Resource}.
	 * The default implementation in this class simply returns an instance of {@link ResourceOutputStream}. Subclasses
	 * that {@link Override} this method can return instances that implement the optional {@link Filterable} interface.
	 *
	 * @param   resource  The resource that is being requested.
	 * @param   size      The size of the buffer.
	 *
	 * @return  The output stream.
	 */
	protected ResourceOutputStream getResourceOutputStream(Resource resource, int size) {
		return new ResourceOutputStream(resource, size);
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrappedResourceHandler;
	}
}
