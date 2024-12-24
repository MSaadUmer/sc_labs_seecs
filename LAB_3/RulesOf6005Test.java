/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package rules;

import static org.junit.Assert.*;

import org.junit.Test;
public class RulesOf6005Test {
    
    @Test
    public void testMayUseCodeInAssignment() {
        assertFalse("Expected false: un-cited publicly-available code",
                RulesOf6005.mayUseCodeInAssignment(false, true, false, false, false));
        assertTrue("Expected true: self-written required code",
                RulesOf6005.mayUseCodeInAssignment(true, false, true, true, true));
        
        // Additional Test Cases
        
        // Case 1: Self-written code not required for coursework
        assertTrue("Expected true: self-written code not required for coursework",
                RulesOf6005.mayUseCodeInAssignment(true, false, false, false, false));

        // Case 2: Publicly available code cited and implementation required
        assertTrue("Expected true: cited publicly-available code with implementation required",
                RulesOf6005.mayUseCodeInAssignment(false, true, false, true, true));

        // Case 3: Publicly available code cited but implementation not required
        assertFalse("Expected false: cited publicly-available code but implementation not required",
                RulesOf6005.mayUseCodeInAssignment(false, true, false, true, false));
    }
}