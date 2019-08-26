package org.sejda.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
}
