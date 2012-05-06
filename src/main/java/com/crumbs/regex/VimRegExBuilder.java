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

import static com.crumbs.util.Logging.illegalOutsideSetArg;

/**
 * @author Chris Topher
 * @version 0.0, May 6, 2012
 */
public class VimRegExBuilder extends BaseRegExBuilder {

	@Override
	protected VimRegExBuilder newInstance() {
		return new VimRegExBuilder();
	}

	@Override
	protected VimRegExBuilder thiz() {
		return this;
	}

	@Override
	public RegExBuilder optional(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("\\=");
	}

	@Override
	public RegExBuilder oneOrMore(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("\\+");
	}

	@Override
	protected RegExBuilder repeat(int nTimes) {
		if (nTimes < 0) {
			throw illegalOutsideSetArg( //
					int.class, "nTimes", new Integer(nTimes), "[0,\u221E)");
		}
		return u("\\{").u(nTimes).t("}");
	}

	@Override
	protected RegExBuilder repeatAtLeast(int nTimes) {
		return u("\\{").u(nTimes).t(",}");
	}

	@Override
	protected RegExBuilder repeat(int nTimes, int toMTimes) {
		if (nTimes < 0) {
			throw illegalOutsideSetArg( //
					int.class, "nTimes", new Integer(nTimes), "[0,\u221E)");
		}
		if (toMTimes < 0) {
			throw illegalOutsideSetArg( //
					int.class, "toMTimes", new Integer(toMTimes), "[0,\u221E)");
		}
		if (nTimes > toMTimes) {
			throw illegalOutsideSetArg( //
					int.class, "nTimes", new Integer(nTimes), "[0,toMTimes]=[0," + toMTimes + "]");
		}
		return u("\\{").u(nTimes).u(",").u(toMTimes).t("}");
	}

	@Override
	public String asCode() {
		return toString();
	}
}
