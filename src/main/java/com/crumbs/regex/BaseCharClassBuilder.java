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
package com.crumbs.regex;

import static com.crumbs.util.Logging.illegalNullArg;
import static com.crumbs.util.Logging.illegalOutsideSetArg;

/**
 * Base class for {@link CharClassBuilder} realizations. This code is common to
 * many different regular-expression engines.
 * 
 * @author Chris Topher
 * @version 0.0, Sep 5, 2009
 */
abstract class BaseCharClassBuilder extends BaseCommonBuilder<CharClassBuilder> implements CharClassBuilder {
	@Override
	public CharClassBuilder range(char min, char max) {
		if (min > max) {
			throw illegalOutsideSetArg( //
					char.class, "min", new Character(min), "[0, max]=[0," + max + "]");
		}
		return u(min).u("-").t(max);
	}

	@Override
	public CharClassBuilder c(char c0, char... cN) {
		if (cN == null) {
			throw illegalNullArg(char[].class, "cN");
		}
		charClass(c0);
		for (char c : cN) {
			charClass(c);
		}
		return thiz();
	}

	@Override
	public CharClassBuilder str(String str) {
		if (str == null) {
			throw illegalNullArg(String.class, "str");
		}
		for (char c : str.toCharArray()) {
			charClass(c);
		}
		return thiz();
	}
}
