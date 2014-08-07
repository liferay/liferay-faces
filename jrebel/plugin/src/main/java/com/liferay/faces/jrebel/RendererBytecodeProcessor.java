/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.jrebel;

import org.zeroturnaround.bundled.javassist.ClassPool;
import org.zeroturnaround.bundled.javassist.CtClass;
import org.zeroturnaround.bundled.javassist.CtMethod;
import org.zeroturnaround.bundled.javassist.NotFoundException;

import org.zeroturnaround.javarebel.integration.support.JavassistClassBytecodeProcessor;


/**
 * This class will add template reloading functionality to classes that extend {@link javax.faces.render.Renderer} and
 * that also implement {@link javax.faces.event.SystemEventListener}.
 *
 * @author  Neil Griffin
 */
public class RendererBytecodeProcessor extends JavassistClassBytecodeProcessor {

	@Override
	public void process(ClassPool classPool, ClassLoader classLoader, CtClass ctClass) throws Exception {

		try {

			CtClass rendererCtClass = classPool.get("javax.faces.render.Renderer");

			if (ctClass.subclassOf(rendererCtClass)) {

				CtClass[] ctInterfaces = ctClass.getInterfaces();
				boolean hasInterface = false;

				for (CtClass ctInterface : ctInterfaces) {

					if ("javax.faces.event.SystemEventListener".equals(ctInterface.getName())) {
						hasInterface = true;

						break;
					}
				}

				if (hasInterface) {
					CtClass facesContextCtClass = classPool.get("javax.faces.context.FacesContext");
					CtClass uiComponentCtClass = classPool.get("javax.faces.component.UIComponent");

					try {
						CtMethod encodeBeginMethod = ctClass.getDeclaredMethod("encodeBegin",
								new CtClass[] { facesContextCtClass, uiComponentCtClass });
						encodeBeginMethod.insertBefore(
							"com.liferay.faces.jrebel.RendererReloader.reloadTemplates($0,$1);");
					}
					catch (NotFoundException e) {
						CtMethod encodeMarkupBeginMethod = ctClass.getDeclaredMethod("encodeMarkupBegin",
								new CtClass[] { facesContextCtClass, uiComponentCtClass });
						encodeMarkupBeginMethod.insertBefore(
							"com.liferay.faces.jrebel.RendererReloader.reloadTemplates($0,$1);");
					}
				}
			}
		}
		catch (NotFoundException e) {
			// ignore
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
