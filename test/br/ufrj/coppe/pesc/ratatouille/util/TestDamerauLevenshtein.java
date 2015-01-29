package br.ufrj.coppe.pesc.ratatouille.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestDamerauLevenshtein {

	@Test
	public void testDamerauLevenshtein() {
		
		assertEquals(1, DamerauLevenshtein.calculateOptimalStringAlignmentDistance("parmesão".toCharArray(), "parmesao".toCharArray()));
		assertEquals(2, DamerauLevenshtein.calculateOptimalStringAlignmentDistance("parmesão".toCharArray(), "parmessao".toCharArray()));
	}

}
