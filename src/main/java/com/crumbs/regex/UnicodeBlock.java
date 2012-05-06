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
 * @author Chris Topher
 * @version 0.0, Jul 21, 2009
 */
// http://www.unicode.org/Public/3.0-Update/Blocks-3.txt
// There may need to be some manipulation stuff here
// http://www.regular-expressions.info/unicode.html#prop
public enum UnicodeBlock {
	InBasic_Latin, InLatin_1_Supplement("InLatin-1_Supplement"), //$NON-NLS-1$
	InLatin_Extended_A("InLatin_Extended-A"), //$NON-NLS-1$
	InLatin_Extended_B("InLatin_Extended-B"), //$NON-NLS-1$
	InIPA_Extensions,
	InSpacing_Modifier_Letters,
	InCombining_Diacritical_Marks,
	InGreek_and_Coptic,
	InCyrillic,
	InCyrillic_Supplementary,
	InArmenian,
	InHebrew,
	InArabic,
	InSyriac,
	InThaana,
	InDevanagari,
	InBengali,
	InGurmukhi,
	InGujarati,
	InOriya,
	InTamil,
	InTelugu,
	InKannada,
	InMalayalam,
	InSinhala,
	InThai,
	InLao,
	InTibetan,
	InMyanmar,
	InGeorgian,
	InHangul_Jamo,
	InEthiopic,
	InCherokee,
	InUnified_Canadian_Aboriginal_Syllabics,
	InOgham,
	InRunic,
	InTagalog,
	InHanunoo,
	InBuhid,
	InTagbanwa,
	InKhmer,
	InMongolian,
	InLimbu,
	InTai_Le,
	InKhmer_Symbols,
	InPhonetic_Extensions,
	InLatin_Extended_Additional,
	InGreek_Extended,
	InGeneral_Punctuation,
	InSuperscripts_and_Subscripts,
	InCurrency_Symbols,
	InCombining_Diacritical_Marks_for_Symbols,
	InLetterlike_Symbols,
	InNumber_Forms,
	InArrows,
	InMathematical_Operators,
	InMiscellaneous_Technical,
	InControl_Pictures,
	InOptical_Character_Recognition,
	InEnclosed_Alphanumerics,
	InBox_Drawing,
	InBlock_Elements,
	InGeometric_Shapes,
	InMiscellaneous_Symbols,
	InDingbats,
	InMiscellaneous_Mathematical_Symbols_A(
			"InMiscellaneous_Mathematical_Symbols-A"), //$NON-NLS-1$
	InSupplemental_Arrows_A("InSupplemental_Arrows-A"), //$NON-NLS-1$
	InBraille_Patterns,
	InSupplemental_Arrows_B("InSupplemental_Arrows-B"), //$NON-NLS-1$
	InMiscellaneous_Mathematical_Symbols_B(
			"InMiscellaneous_Mathematical_Symbols-"), //$NON-NLS-1$
	InSupplemental_Mathematical_Operators,
	InMiscellaneous_Symbols_and_Arrows,
	InCJK_Radicals_Supplement,
	InKangxi_Radicals,
	InIdeographic_Description_Characters,
	InCJK_Symbols_and_Punctuation,
	InHiragana,
	InKatakana,
	InBopomofo,
	InHangul_Compatibility_Jamo,
	InKanbun,
	InBopomofo_Extended,
	InKatakana_Phonetic_Extensions,
	InEnclosed_CJK_Letters_and_Months,
	InCJK_Compatibility,
	InCJK_Unified_Ideographs_Extension_A,
	InYijing_Hexagram_Symbols,
	InCJK_Unified_Ideographs,
	InYi_Syllables,
	InYi_Radicals,
	InHangul_Syllables,
	InHigh_Surrogates,
	InHigh_Private_Use_Surrogates,
	InLow_Surrogates,
	InPrivate_Use_Area,
	InCJK_Compatibility_Ideographs,
	InAlphabetic_Presentation_Forms,
	InArabic_Presentation_Forms_A("InArabic_Presentation_Forms-A"), //$NON-NLS-1$
	InVariation_Selectors,
	InCombining_Half_Marks,
	InCJK_Compatibility_Forms,
	InSmall_Form_Variants,
	InArabic_Presentation_Forms_B("InArabic_Presentation_Forms-B"), //$NON-NLS-1$
	InHalfwidth_and_Fullwidth_Forms,
	InSpecials;

	private final String mName;

	private UnicodeBlock() {
		this.mName = null;
	}

	private UnicodeBlock(String name) {
		this.mName = name;
	}

	@Override
	public String toString() {
		return this.mName == null ? name() : this.mName;
	}
}
