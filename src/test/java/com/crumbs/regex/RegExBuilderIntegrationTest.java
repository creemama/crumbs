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

import static java.text.MessageFormat.format;
import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.UNIX_LINES;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Chris Topher
 * @version 0.0, Aug 11, 2009
 */
public class RegExBuilderIntegrationTest {

	private final RegExBuilderFactory factory;

	public RegExBuilderIntegrationTest() {
		this.factory = new RegExBuilderFactoryImpl();
	}

	@Test
	public void regExTest() {
		String str;

		str = "\\[Regul*r+expre$$|ons }]^are^{0,5} awesome.\t?{!}]()"; //$NON-NLS-1$
		testMatch(this.factory.regEx(str).compile(), str);
		str = "\\QI use \\Q...\\E in my regular expressions.\\E"; //$NON-NLS-1$
		testMatch(this.factory.regEx(str).compile(), str);
	}

	@Test
	public void quoteTest() {
		String str;

		str = "\\[Regul*r+expre$$|ons }]^are^{0,5} awesome.\t?{!}]()"; //$NON-NLS-1$
		testMatch(this.factory.quote(str).compile(), str);
		str = "\\QI use \\Q...\\E in my regular expressions.\\E"; //$NON-NLS-1$
		testMatch(this.factory.quote(str).compile(), str);
	}

	@Test
	public void charactersTest() {
		Pattern pattern;

		pattern = this.factory.bell().compile();
		testMatch(pattern, "\u0007"); //$NON-NLS-1$

		pattern = this.factory.tab().compile();
		testMatch(pattern, "\t"); //$NON-NLS-1$
		testMatch(pattern, "\u0009"); //$NON-NLS-1$

		// It is illegal to use\u000A
		pattern = this.factory.lineFeed().compile();
		testMatch(pattern, "\n"); //$NON-NLS-1$
		testMatch(pattern, "\12"); //$NON-NLS-1$

		pattern = this.factory.verticalTab().compile();
		testMatch(pattern, "\u000B"); //$NON-NLS-1$

		pattern = this.factory.formFeed().compile();
		testMatch(pattern, "\f"); //$NON-NLS-1$
		testMatch(pattern, "\u000C"); //$NON-NLS-1$

		// It is illegal to use\u000D
		pattern = this.factory.carriageReturn().compile();
		testMatch(pattern, "\r"); //$NON-NLS-1$
		testMatch(pattern, "\15"); //$NON-NLS-1$

		pattern = this.factory.escape().compile();
		testMatch(pattern, "\u001B"); //$NON-NLS-1$
	}

	@Test
	public void controlCharactersTest() {
		char i = 'A';
		int j = 0;
		for (; i < 'Z'; i++, j++) {
			assertTrue(this.factory.control(i).compile().matcher(
					"" + (char) ('\1' + j)).matches()); //$NON-NLS-1$
		}
	}

	@Test
	public void asciiTest() {
		Pattern pattern;
		char i = 0x00;
		for (; i < 0xFF + 1; i++) {
			pattern = this.factory.ascii(i).compile();
			testMatch(pattern, Character.toString(i));
		}
	}

	@Test
	public void digitsTest() {
		Pattern pattern = this.factory.digit().compile();
		for (int i = 0; i < 10; i++) {
			testMatch(pattern, Integer.toString(i));
		}
		testNonMatch(pattern, "A"); //$NON-NLS-1$
		testNonMatch(pattern, " "); //$NON-NLS-1$
	}

	private void testMatch(Pattern pattern, String str) {
		String msg = "Expected \"{0}\" to match \"{1}\"";
		assertTrue(format(msg, pattern, str), pattern.matcher(str).matches());
	}

	private void testNonMatch(Pattern pattern, String str) {
		String msg = "Did not expect \"{0}\" to match \"{1}\"";
		assertFalse(format(msg, pattern, str), pattern.matcher(str).matches());
	}

	@Test
	public void nonDigitsTest() {
		Pattern pattern = this.factory.notDigit().compile();
		for (int i = 0; i < 10; i++) {
			testNonMatch(pattern, Integer.toString(i));
		}
		testMatch(pattern, "A"); //$NON-NLS-1$
		testMatch(pattern, " "); //$NON-NLS-1$
	}

	@Test
	public void whitespaceTest() {
		Pattern pattern = this.factory.whitespace().compile();

		// space
		testMatch(pattern, " "); //$NON-NLS-1$
		testMatch(pattern, "\40"); //$NON-NLS-1$

		// tab
		testMatch(pattern, "\t"); //$NON-NLS-1$
		testMatch(pattern, "\11"); //$NON-NLS-1$

		// new line
		testMatch(pattern, "\n"); //$NON-NLS-1$
		testMatch(pattern, "\12"); //$NON-NLS-1$

		// vertical tab
		testMatch(pattern, "\13"); //$NON-NLS-1$

		// form feed
		testMatch(pattern, "\f"); //$NON-NLS-1$
		testMatch(pattern, "\14"); //$NON-NLS-1$

		// carriage return
		testMatch(pattern, "\r"); //$NON-NLS-1$
		testMatch(pattern, "\15"); //$NON-NLS-1$

		testNonMatch(pattern, "a"); //$NON-NLS-1$
		testNonMatch(pattern, "0"); //$NON-NLS-1$
	}

	@Test
	public void nonWhitespaceTest() {
		Pattern pattern = this.factory.notWhitespace().compile();

		// space
		testNonMatch(pattern, " "); //$NON-NLS-1$
		testNonMatch(pattern, "\40"); //$NON-NLS-1$

		// tab
		testNonMatch(pattern, "\t"); //$NON-NLS-1$
		testNonMatch(pattern, "\11"); //$NON-NLS-1$

		// new line
		testNonMatch(pattern, "\n"); //$NON-NLS-1$
		testNonMatch(pattern, "\12"); //$NON-NLS-1$

		// vertical tab
		testNonMatch(pattern, "\13"); //$NON-NLS-1$

		// form feed
		testNonMatch(pattern, "\f"); //$NON-NLS-1$
		testNonMatch(pattern, "\14"); //$NON-NLS-1$

		// carriage return
		testNonMatch(pattern, "\r"); //$NON-NLS-1$
		testNonMatch(pattern, "\15"); //$NON-NLS-1$

		testMatch(pattern, "a"); //$NON-NLS-1$
		testMatch(pattern, "0"); //$NON-NLS-1$
	}

	@Test
	public void wordCharactersTest() {
		Pattern pattern = this.factory.wordCharacter().compile();
		for (int i = 0; i < 10; i++) {
			testMatch(pattern, Integer.toString(i));
		}
		for (char i = 'A'; i < 'Z'; i++) {
			testMatch(pattern, Character.toString(i));
		}
		for (char i = 'a'; i < 'z'; i++) {
			testMatch(pattern, Character.toString(i));
		}
		testMatch(pattern, "_"); //$NON-NLS-1$
		testNonMatch(pattern, "\t"); //$NON-NLS-1$
		testNonMatch(pattern, "+"); //$NON-NLS-1$
	}

	@Test
	public void nonWordCharactersTest() {
		Pattern pattern = this.factory.notWordCharacter().compile();
		for (int i = 0; i < 10; i++) {
			testNonMatch(pattern, Integer.toString(i));
		}
		for (char i = 'A'; i < 'Z'; i++) {
			testNonMatch(pattern, Character.toString(i));
		}
		for (char i = 'a'; i < 'z'; i++) {
			testNonMatch(pattern, Character.toString(i));
		}
		testNonMatch(pattern, "_"); //$NON-NLS-1$
		testMatch(pattern, "\t"); //$NON-NLS-1$
		testMatch(pattern, "+"); //$NON-NLS-1$
	}

	@Test
	public void anyCharTest() {
		// do not match recognized line terminators
		Pattern pattern = this.factory.anyChar().compile();
		testMatch(pattern, "\t"); //$NON-NLS-1$
		testNonMatch(pattern, "\n"); //$NON-NLS-1$
		testNonMatch(pattern, "\r"); //$NON-NLS-1$
		testNonMatch(pattern, "\r\n"); //$NON-NLS-1$

		// match everything
		pattern = this.factory.anyChar().compile(DOTALL);
		testMatch(pattern, "\t"); //$NON-NLS-1$
		testMatch(pattern, "\n"); //$NON-NLS-1$
		testMatch(pattern, "\r"); //$NON-NLS-1$
		testNonMatch(pattern, "\r\n"); //$NON-NLS-1$

		// do not match \n
		pattern = this.factory.anyChar().compile(UNIX_LINES);
		testMatch(pattern, "\t"); //$NON-NLS-1$
		testNonMatch(pattern, "\n"); //$NON-NLS-1$
		testMatch(pattern, "\r"); //$NON-NLS-1$
		testNonMatch(pattern, "\r\n"); //$NON-NLS-1$

		pattern = this.factory.anyChar().anyChar().compile();
		testNonMatch(pattern, "\r\n"); //$NON-NLS-1$

		pattern = this.factory.anyChar().anyChar().compile(DOTALL);
		testMatch(pattern, "\r\n"); //$NON-NLS-1$
	}


	@Test
	public void test()
	{
		Assert.assertTrue("\\".matches("[+-^]"));
	}
}
