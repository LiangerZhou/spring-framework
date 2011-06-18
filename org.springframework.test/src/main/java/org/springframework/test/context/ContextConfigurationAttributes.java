/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.test.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.ObjectUtils;

/**
 * TODO [SPR-8386] Document ContextConfigurationAttributes.
 * 
 * @author Sam Brannen
 * @since 3.1
 * @see ContextConfiguration
 */
public class ContextConfigurationAttributes {

	private static final Log logger = LogFactory.getLog(ContextConfigurationAttributes.class);

	private final Class<?> declaringClass;

	private final boolean inheritLocations;

	private final Class<? extends ContextLoader> contextLoaderClass;

	private String[] locations;

	private Class<?>[] classes;


	/**
	 * Resolves resource locations from the {@link ContextConfiguration#locations() locations}
	 * and {@link ContextConfiguration#value() value} attributes of the supplied
	 * {@link ContextConfiguration} annotation.
	 * 
	 * @throws IllegalStateException if both the locations and value attributes have been declared
	 */
	static String[] resolveLocations(Class<?> declaringClass, ContextConfiguration contextConfiguration) {

		String[] locations = contextConfiguration.locations();
		String[] valueLocations = contextConfiguration.value();

		if (!ObjectUtils.isEmpty(valueLocations) && !ObjectUtils.isEmpty(locations)) {
			String msg = String.format("Test class [%s] has been configured with @ContextConfiguration's 'value' [%s] "
					+ "and 'locations' [%s] attributes. Only one declaration of resource "
					+ "locations is permitted per @ContextConfiguration annotation.", declaringClass,
				ObjectUtils.nullSafeToString(valueLocations), ObjectUtils.nullSafeToString(locations));
			logger.error(msg);
			throw new IllegalStateException(msg);
		}
		else if (!ObjectUtils.isEmpty(valueLocations)) {
			locations = valueLocations;
		}

		return locations;
	}

	/**
	 * TODO Document ContextConfigurationAttributes constructor.
	 */
	public ContextConfigurationAttributes(Class<?> declaringClass, ContextConfiguration contextConfiguration) {
		this(declaringClass, resolveLocations(declaringClass, contextConfiguration), contextConfiguration.classes(),
			contextConfiguration.inheritLocations(), contextConfiguration.loader());
	}

	/**
	 * TODO Document ContextConfigurationAttributes constructor.
	 */
	public ContextConfigurationAttributes(Class<?> declaringClass, String[] locations, Class<?>[] classes,
			boolean inheritLocations, Class<? extends ContextLoader> contextLoaderClass) {
		this.declaringClass = declaringClass;
		this.locations = locations;
		this.classes = classes;
		this.inheritLocations = inheritLocations;
		this.contextLoaderClass = contextLoaderClass;
	}

	/**
	 * TODO Document getDeclaringClass().
	 */
	public Class<?> getDeclaringClass() {
		return this.declaringClass;
	}

	/**
	 * TODO Document isInheritLocations().
	 */
	public boolean isInheritLocations() {
		return this.inheritLocations;
	}

	/**
	 * TODO Document getContextLoaderClass().
	 */
	public Class<? extends ContextLoader> getContextLoaderClass() {
		return this.contextLoaderClass;
	}

	/**
	 * TODO Document getLocations().
	 */
	public String[] getLocations() {
		return this.locations;
	}

	/**
	 * TODO Document setLocations().
	 */
	public void setLocations(String[] locations) {
		this.locations = locations;
	}

	/**
	 * TODO Document getClasses().
	 */
	public Class<?>[] getClasses() {
		return this.classes;
	}

	/**
	 * TODO Document setClasses().
	 */
	public void setClasses(Class<?>[] classes) {
		this.classes = classes;
	}

	/**
	 * TODO Document overridden toString().
	 */
	@Override
	public String toString() {
		return new ToStringCreator(this)//
		.append("declaringClass", this.declaringClass)//
		.append("locations", ObjectUtils.nullSafeToString(this.locations))//
		.append("classes", ObjectUtils.nullSafeToString(this.classes))//
		.append("inheritLocations", this.inheritLocations)//
		.append("contextLoaderClass", this.contextLoaderClass)//
		.toString();
	}

}
