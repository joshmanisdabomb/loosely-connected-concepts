package com.joshmanisdabomb.aimagg.util;

public enum ModifierOperation {

	/**The float is added to the base value.
	 * All ADD modifiers are applied first.**/
	ADD,

	/**Sums the modifiers up and then multiplies the result by the base value.
	 * All ADDITIVE_MULTIPLY modifiers are applied after ADD modifiers.**/
	ADDITIVE_MULTIPLY,

	/**Multiplies the modifiers up and then multiplies the result by the base value.
	 * All MULTIPLICATIVE_MULTIPLY modifiers are applied after ADD and MULTIPLICATIVE modifiers.**/
	MULTIPLICATIVE_MULTIPLY;
	
}
