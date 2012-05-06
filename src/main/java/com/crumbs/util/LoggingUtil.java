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

import java.text.ParseException;

/**
 * @author Chris Topher
 * @version 0.0, Aug 29, 2009
 */
public interface LoggingUtil {
	String getString(String patternKey, Object... arguments);

	IllegalArgumentException illegalNullArg(Class<?> argType, String argName);

	IllegalArgumentException illegalNullArrayItemArg(Class<?> argType,
			String argName);

	IllegalArgumentException illegalOutsideSetArg(Class<?> argType,
			String argName, Object invalidArgValue, String validSetOfValues);

	IllegalArgumentException illegalArg(String patternKey, Object... arguments);

	ParseException parseException(String patternKey, Object... arguments);

	Error error();
}
