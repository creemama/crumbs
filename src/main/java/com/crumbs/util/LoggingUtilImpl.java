/**
 * Copyright 2009 Creemama
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crumbs.util;

import static java.text.MessageFormat.format;

import java.text.ParseException;
import java.util.ResourceBundle;

/**
 * @author Chris Topher
 * @version 0.0, Aug 29, 2009
 */
class LoggingUtilImpl implements LoggingUtil {
	private final ResourceBundle bundle;

	public LoggingUtilImpl(ResourceBundle bundle_) {
		super();
		this.bundle = bundle_;
	}

	public String getString(String patternKey, Object... arguments) {
		return format(this.bundle.getString(patternKey), arguments);
	}

	public IllegalArgumentException illegalNullArg(Class<?> argType,
			String argName) {
		return illegalArg("illegal.argument.null", //$NON-NLS-1$
				argType.getSimpleName(), argName);
	}

	public IllegalArgumentException illegalNullArrayItemArg(Class<?> argType,
			String argName) {
		return illegalArg("illegal.argument.null.array.item", //$NON-NLS-1$
				argType.getSimpleName(), argName);
	}

	public IllegalArgumentException illegalOutsideSetArg(Class<?> argType,
			String argName, Object invalidArgValue, String validSetOfValues) {
		return illegalArg(
				"illegal.argument.outside.set", //$NON-NLS-1$
				argType.getSimpleName(), argName, invalidArgValue,
				validSetOfValues);
	}

	public IllegalArgumentException illegalArg(String patternKey,
			Object... arguments) {
		return new IllegalArgumentException(getString(patternKey, arguments));
	}

	public ParseException parseException(String patternKey, Object... arguments) {
		return new ParseException(getString(patternKey, arguments), 0);
	}

	public Error error() {
		return new Error(this.bundle.getString("error")); //$NON-NLS-1$
	}

}
