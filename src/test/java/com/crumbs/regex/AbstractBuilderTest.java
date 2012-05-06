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

	@SuppressWarnings("unchecked")
	private AbstractBuilder<?> createAbstractBuilder() {
		return new AbstractBuilder(new StringBuilder()) {

			@Override
			protected AbstractBuilder<?> thiz() {
				return this;
			}
		};
	}


	/**
	 * Tests {@link AbstractBuilder#t(String)}.
	 */
	@Test
	public void testT()
	{
		AbstractBuilder<?> regEx = createAbstractBuilder();

		regEx.t(null);
		assertEquals("null", regEx.toString());

		try
		{
			regEx.t((Object)null);
			assertEquals("null", regEx.toString());
			fail();
		}
		catch (IllegalArgumentException e)
		{
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
		AbstractBuilder<?> regEx = createAbstractBuilder();

		regEx.lineFeed();
		assertEquals("\\n", regEx.toString()); //$NON-NLS-1$

		regEx.carriageReturn();
		assertEquals("\\n\\r", regEx.toString()); //$NON-NLS-1$

		regEx.formFeed();
		assertEquals("\\n\\r\\f", regEx.toString()); //$NON-NLS-1$

		regEx.bell();
		assertEquals("\\n\\r\\f\\a", regEx.toString()); //$NON-NLS-1$

		regEx.escape();
		assertEquals("\\n\\r\\f\\a\\e", regEx.toString()); //$NON-NLS-1$

		regEx.verticalTab();
		assertEquals("\\n\\r\\f\\a\\e\\v", regEx.toString()); //$NON-NLS-1$

		regEx.tab();
		assertEquals("\\n\\r\\f\\a\\e\\v\\t", regEx.toString()); //$NON-NLS-1$
	}

	@Test
	public void controlCharTest() {
		AbstractBuilder<?> regEx = createAbstractBuilder();

		regEx.control('A');
		assertEquals("\\cA", regEx.toString()); //$NON-NLS-1$

		regEx.control('z');
		assertEquals("\\cA\\cZ", regEx.toString()); //$NON-NLS-1$

		// \u0065 is Unicode for lowercase e
		regEx.control('\u0065');
		assertEquals("\\cA\\cZ\\cE", regEx.toString()); //$NON-NLS-1$

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
		AbstractBuilder<?> regEx = createAbstractBuilder();

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
		assertEquals("\\x01", regEx.toString()); //$NON-NLS-1$

		regEx.ascii(0x6A);
		assertEquals("\\x01\\x6A", regEx.toString()); //$NON-NLS-1$
	}

	@Test
	public void unicodeTest() {
		AbstractBuilder<?> regEx = createAbstractBuilder();

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
		assertEquals("\\u0001", regEx.toString()); //$NON-NLS-1$

		regEx.unicode(0x00FA);
		assertEquals("\\u0001\\u00FA", regEx.toString()); //$NON-NLS-1$

		regEx.unicode(0x0ABC);
		assertEquals("\\u0001\\u00FA\\u0ABC", regEx.toString()); //$NON-NLS-1$

		regEx.unicode(0x1234);
		assertEquals("\\u0001\\u00FA\\u0ABC\\u1234", regEx.toString()); //$NON-NLS-1$
	}

	/**
	 * <ul>
	 * <li>See {@link AbstractBuilder#unicode(UnicodeBlock)}</li>
	 * <li>See {@link AbstractBuilder#unicode(UnicodeCharacterProperty)}</li>
	 * <li>See {@link AbstractBuilder#notUnicode(UnicodeBlock)}</li>
	 * <li>See {@link AbstractBuilder#notUnicode(UnicodeCharacterProperty)}</li>
	 * </ul>
	 */
	@Test
	public void unicodePropertyOrScriptTest() {
		AbstractBuilder<?> regEx = createAbstractBuilder();

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
		assertEquals("\\p{InArrows}", regEx.toString()); //$NON-NLS-1$

		regEx.unicode(UnicodeCharacterProperty.Letter_Number);
		assertEquals("\\p{InArrows}\\p{Letter_Number}", regEx.toString()); //$NON-NLS-1$

		regEx.notUnicode(UnicodeBlock.InLatin_Extended_A);
		assertEquals(
				"\\p{InArrows}\\p{Letter_Number}\\P{InLatin_Extended-A}", regEx.toString()); //$NON-NLS-1$

		regEx.notUnicode(UnicodeCharacterProperty.LAmpersand);
		assertEquals(
				"\\p{InArrows}\\p{Letter_Number}\\P{InLatin_Extended-A}\\P{L&}", regEx.toString()); //$NON-NLS-1$
	}

	/**
	 * <ul>
	 * <li>See {@link AbstractBuilder#whitespace()}</li>
	 * <li>See {@link AbstractBuilder#digit()}</li>
	 * <li>See {@link AbstractBuilder#wordCharacter()}</li>
	 * <li>See {@link AbstractBuilder#notWhitespace()}</li>
	 * <li>See {@link AbstractBuilder#notDigit()}</li>
	 * <li>See {@link AbstractBuilder#notWordCharacter()}</li>
	 * </ul>
	 */
	@Test
	public void shorthandCharacterClassTest() {
		AbstractBuilder<?> regEx = createAbstractBuilder();

		regEx.whitespace();
		assertEquals("\\s", regEx.toString()); //$NON-NLS-1$

		regEx.digit();
		assertEquals("\\s\\d", regEx.toString()); //$NON-NLS-1$

		regEx.wordCharacter();
		assertEquals("\\s\\d\\w", regEx.toString()); //$NON-NLS-1$

		regEx.notDigit();
		assertEquals("\\s\\d\\w\\D", regEx.toString()); //$NON-NLS-1$

		regEx.notWordCharacter();
		assertEquals("\\s\\d\\w\\D\\W", regEx.toString()); //$NON-NLS-1$

		regEx.notWhitespace();
		assertEquals("\\s\\d\\w\\D\\W\\S", regEx.toString()); //$NON-NLS-1$
	}

}
