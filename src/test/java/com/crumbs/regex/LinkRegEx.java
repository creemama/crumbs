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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Chris Topher
 * @version 0.0, May 6, 2012
 */
public class LinkRegEx extends RegExExample {
	public static void main(String[] args) {
		final LinkRegEx re = new LinkRegEx(new JRegExBuilderFactory());

		final Pattern pattern = Pattern.compile(re.create().toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		final Matcher matcher = pattern
				.matcher("<html ><Link rel=\"alternate\" type=\"application/rss+xml\" title=\"Engadget\" href=\"http://www.engadget.com/rss.xml\">");

		System.out.println(pattern);
		if (matcher.find()) {
			for (int i = 0; i <= matcher.groupCount(); i++) {
				System.out.println(matcher.group(i));
			}
		}
	}

	public LinkRegEx(RegExBuilderFactory f) {
		super(f);
	}

	public RegExBuilder create() {
		// [a-z\-]+\s=\s("[^"]*"|[^"=\s]+)
		// type\s*=\s*"?application/rss\\+(xml|atom)"?
		// href\s*=\s*(?:"[^"]+"|)

		final RegExBuilder oneOrMoreWhitespace = r().oneOrMore(r().whitespace());
		final RegExBuilder attributeName = r().oneOrMore(r().charClass(c().range('a', 'z').c('-')));

		final RegExBuilder genericQuotedValue = r().re("\"").zeroOrMore(r().notCharClass(c().c('"'))).re("\"");
		final RegExBuilder quotedValue = r().re("\"").group(r().zeroOrMore(r().notCharClass(c().c('"')))).re("\"");

		final RegExBuilder genericAttribute = r().re(oneOrMoreWhitespace).re(attributeName).re("=")
				.re(genericQuotedValue);
		final RegExBuilder href = r().re(oneOrMoreWhitespace).re("href=").re(quotedValue);

		final RegExBuilder xmlOrAtom = r().orNoCaptureGroup(r().re("xml"), r().re("atom"));
		final RegExBuilder typeValue = r().re("application/rss+").re(xmlOrAtom);
		final RegExBuilder type = r().re(oneOrMoreWhitespace).re("type=\"").re(typeValue).re("\"");

		final RegExBuilder attributes = r().zeroOrMore(r().orNoCaptureGroup(href, genericAttribute)).re(type)
				.zeroOrMoreLazy(r().orNoCaptureGroup(href, genericAttribute));

		return r().re("<link").re(attributes).zeroOrMore(r().whitespace()).optional(r().re("/")).re(">");
	}
}
