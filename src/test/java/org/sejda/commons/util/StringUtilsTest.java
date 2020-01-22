package org.sejda.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class StringUtilsTest {
    @Test
    public void testNbsp() {
        assertNotEquals("result", ((char) 160 + "result").trim());
        assertEquals("result", StringUtils.normalizeWhitespace((char) 160 + "result").trim());
        assertEquals("result", StringUtils.normalizeWhitespace("result" + (char) 160).trim());
        assertEquals("", StringUtils.normalizeWhitespace("" + (char) 160).trim());
        assertEquals("", StringUtils.normalizeWhitespace((char) 160 + "" + (char) 160).trim());
        assertEquals("Foo bar", StringUtils.normalizeWhitespace("Foo" + (char) 160 + "bar"));
    }

    @Test
    public void difference() {
        Set<Character> expected = new LinkedHashSet<>();
        expected.add('\uFE0F');
        expected.add('\uEF0F');

        assertEquals(expected, StringUtils.difference("a\uFE0Fb\uEF0Fc மி", "abc மி"));
    }

    @Test
    public void doesNotReplaceMultipleWhitespaceWithOne() {
        assertEquals("T  H", StringUtils.normalizeWhitespace("T  H"));
    }

    @Test
    public void asUnicodes() {
        assertEquals("\\U+20\\U+E6\\U+65\\U+5EA", StringUtils.asUnicodes(" æeת"));
    }

    @Test
    public void isEmpty() {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));
        assertFalse(StringUtils.isEmpty(" "));
        assertFalse(StringUtils.isEmpty("Chuck"));
        assertFalse(StringUtils.isEmpty("  Chuck  "));
    }

    @Test
    public void isNotEmpty() {
        assertFalse(StringUtils.isNotEmpty(null));
        assertFalse(StringUtils.isNotEmpty(""));
        assertTrue(StringUtils.isNotEmpty(" "));
        assertTrue(StringUtils.isNotEmpty("Chuck"));
        assertTrue(StringUtils.isNotEmpty("  Chuck  "));
    }
}
