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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author Chris Topher
 * @version 0.0, Sep 15, 2009
 */
public class AbstractBuilderTest {

	private static class TestBaseCommonBuilder extends BaseCommonBuilder<TestBaseCommonBuilder> {
		public TestBaseCommonBuilder() {
			super();
		}

		@Override
		protected TestBaseCommonBuilder thiz() {
			return this;
		}
	}

	static private TestBaseCommonBuilder createAbstractBuilder() {
		return new TestBaseCommonBuilder();
	}

	/**
	 * Tests {@link BaseCommonBuilder#t(String)}.
	 */
	@Test
	public void testT() {
		TestBaseCommonBuilder regEx = createAbstractBuilder();

		regEx.t(null);
		assertEquals("null", regEx.toString());

		try {
			regEx.t((Object) null);
			assertEquals("null", regEx.toString());
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		regEx.t("dog");
		assertEquals("nulldog", regEx.toString());

		regEx.t('a');
		assertEquals("nulldoga", regEx.toString());

		regEx.t(1).t(2345);
		assertEquals("nulldoga12345", regEx.toString());
	}

	@Test
	public void characterTest() {
		TestBaseCommonBuilder regEx = createAbstractBuilder();

		regEx.lineFeed();
		assertEquals("\\n", regEx.toString());

		regEx.carriageReturn();
		assertEquals("\\n\\r", regEx.toString());

		regEx.formFeed();
		assertEquals("\\n\\r\\f", regEx.toString());

		regEx.bell();
		assertEquals("\\n\\r\\f\\a", regEx.toString());

		regEx.escape();
		assertEquals("\\n\\r\\f\\a\\e", regEx.toString());

		regEx.verticalTab();
		assertEquals("\\n\\r\\f\\a\\e\\v", regEx.toString());

		regEx.tab();
		assertEquals("\\n\\r\\f\\a\\e\\v\\t", regEx.toString());
	}

	@Test
	public void controlCharTest() {
		TestBaseCommonBuilder regEx = createAbstractBuilder();

		regEx.control('A');
		assertEquals("\\cA", regEx.toString());

		regEx.control('z');
		assertEquals("\\cA\\cZ", regEx.toString());

		// \u0065 is Unicode for lowercase e
		regEx.control('\u0065');
		assertEquals("\\cA\\cZ\\cE", regEx.toString());

		try {
			regEx.control('0');
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.control('\u00ED');
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void asciiTest() {
		TestBaseCommonBuilder regEx = createAbstractBuilder();

		try {
			regEx.ascii(-1);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.ascii(0xFF + 1);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		regEx.ascii(0x01);
		assertEquals("\\x01", regEx.toString());

		regEx.ascii(0x6A);
		assertEquals("\\x01\\x6A", regEx.toString());
	}

	@Test
	public void unicodeTest() {
		TestBaseCommonBuilder regEx = createAbstractBuilder();

		try {
			regEx.unicode(-1);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.unicode(0xFFFF + 1);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		regEx.unicode(0x0001);
		assertEquals("\\u0001", regEx.toString());

		regEx.unicode(0x00FA);
		assertEquals("\\u0001\\u00FA", regEx.toString());

		regEx.unicode(0x0ABC);
		assertEquals("\\u0001\\u00FA\\u0ABC", regEx.toString());

		regEx.unicode(0x1234);
		assertEquals("\\u0001\\u00FA\\u0ABC\\u1234", regEx.toString());
	}

	/**
	 * <ul>
	 * <li>See {@link BaseCommonBuilder#unicode(UnicodeBlock)}</li>
	 * <li>See {@link BaseCommonBuilder#unicode(UnicodeCharacterProperty)}</li>
	 * <li>See {@link BaseCommonBuilder#notUnicode(UnicodeBlock)}</li>
	 * <li>See {@link BaseCommonBuilder#notUnicode(UnicodeCharacterProperty)}</li>
	 * </ul>
	 */
	@Test
	public void unicodePropertyOrScriptTest() {
		TestBaseCommonBuilder regEx = createAbstractBuilder();

		try {
			regEx.unicode((UnicodeBlock) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.notUnicode((UnicodeBlock) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		regEx = createAbstractBuilder();
		regEx.unicode(UnicodeBlock.InArrows);
		assertEquals("\\p{InArrows}", regEx.toString());

		regEx.unicode(UnicodeCharacterProperty.Letter_Number);
		assertEquals("\\p{InArrows}\\p{Letter_Number}", regEx.toString());

		regEx.notUnicode(UnicodeBlock.InLatin_Extended_A);
		assertEquals("\\p{InArrows}\\p{Letter_Number}\\P{InLatin_Extended-A}", regEx.toString());

		regEx.notUnicode(UnicodeCharacterProperty.LAmpersand);
		assertEquals("\\p{InArrows}\\p{Letter_Number}\\P{InLatin_Extended-A}\\P{L&}", regEx.toString());
	}

	/**
	 * <ul>
	 * <li>See {@link BaseCommonBuilder#whitespace()}</li>
	 * <li>See {@link BaseCommonBuilder#digit()}</li>
	 * <li>See {@link BaseCommonBuilder#wordCharacter()}</li>
	 * <li>See {@link BaseCommonBuilder#notWhitespace()}</li>
	 * <li>See {@link BaseCommonBuilder#notDigit()}</li>
	 * <li>See {@link BaseCommonBuilder#notWordCharacter()}</li>
	 * </ul>
	 */
	@Test
	public void shorthandCharacterClassTest() {
		TestBaseCommonBuilder regEx = createAbstractBuilder();

		regEx.whitespace();
		assertEquals("\\s", regEx.toString());

		regEx.digit();
		assertEquals("\\s\\d", regEx.toString());

		regEx.wordCharacter();
		assertEquals("\\s\\d\\w", regEx.toString());

		regEx.notDigit();
		assertEquals("\\s\\d\\w\\D", regEx.toString());

		regEx.notWordCharacter();
		assertEquals("\\s\\d\\w\\D\\W", regEx.toString());

		regEx.notWhitespace();
		assertEquals("\\s\\d\\w\\D\\W\\S", regEx.toString());
	}

}
