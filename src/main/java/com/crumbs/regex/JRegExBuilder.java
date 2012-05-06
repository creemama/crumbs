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

/**
 * Regular-expression builder for Java's {@code java.util.regex} library
 * 
 * @author Chris Topher
 * @version 0.0, May 6, 2012
 * @see java.util.regex.Pattern
 */
public class JRegExBuilder extends BaseRegExBuilder {
	@Override
	protected JRegExBuilder newInstance() {
		return new JRegExBuilder();
	}

	@Override
	protected JRegExBuilder thiz() {
		return this;
	}

	@Override
	public JRegExBuilder unicode(UnicodeScript category) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JRegExBuilder notUnicode(UnicodeScript category) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String asCode() {
		final StringBuilder builder = new StringBuilder();
		builder.append("final String regex = \"");
		builder.append(toString().replace("\\", "\\\\").replace("\"", "\\\""));
		builder.append("\";");
		builder.append(System.getProperty("line.separator"));
		builder.append("final Pattern pattern = Pattern.compile(regex);");
		return builder.toString();
	}
}