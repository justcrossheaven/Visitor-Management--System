package visitorman;

import java.util.ArrayList;
import java.util.List;

/**
 * This provides a simple test system for the VisitorMan system. 
 * 
 * "Testing" fundamentally means checking that the internal state of the 
 * system matches what we expect it to be. How we check the internal state 
 * depends on various things, including our ability to see what the internal 
 * state is (called "observability"). We also need to change the internal 
 * state in a well-defined way so we can predict what the internal state is
 * supposed to be (called "controllability").
 * 
 * Here we are doing the testing by converting the internal state we are 
 * interested (usually referred to as "actual") in into a string, and comparing 
 * result with a string representing what it is supposed to be ("expected"). 
 * 
 * This is not the best way, but it is good enough. With the right tools, 
 * this can be made easier (but not always easy!). At this point we don't 
 * have these tools so we do everything with strings.
 * 
 * It is usually not possible to test a single method without the right tools
 * (and even then may not be). Typically you call one method to set or change
 * the internal state, and then another method to report on the current internal 
 * state. What this means is that if the current internal state does not match
 * what was expected, there are TWO possibilities:
 * - The method(s) used to set/change the internal state are faulty
 * - The method(s) used to report the current internal state are faulty
 * 
 * Actually there is a third possibility - the stated expected value is in fact incorrect
 * (e.g. the test states that the expect result of 1+1 is 3)!
 * 
 * So whenever a test fails you have to look beyond what the report says. In
 * this class the report will be in terms of the method(s) reporting the
 * internal state, but be aware the problem must be elsewhere. You need to
 * examine what the test method is doing to determine where the problem lies.
 * 
 * More details on formal testing will come later (e.g. SOFTENG254).
 * 
 */
public class Checker {
	/**
	 * Sometimes we need full details to understand what went wrong. But 
	 * always reporting full details of a test can sometimes be unhelpful.
	 * This flag allows easily changing from reporting full details to just
	 * minimal details. Change the value as you need.
	 */
	private static final boolean VERBOSE = true;
	
	/**
	 * This saves typing (because my IDE has auto-complete)
	 */
	private static final String COMPANY_NAME = "251 Corp";

	/**
	 * The following provide values for parties for use in the testing, called
	 * "Specs" below. The format is:
	 * 0. Given name
	 * 1. Family name
	 * 2. Email account
	 * 3. Organisation (only used for visitors - the organisation name and domain for hosts
	 *    is set in the constants above.)
	 * There are methods below for converting these arrays into other values (usually strings)
	 * useful for the actual tests.
	 */
	private static final String[] BaytaDarell =	 { "Bayta", "Darell", "b.darrell@code.us", "Code R Us" };
	private static final String[] GaalDornick =  { "Gaal", "Dornick", "gaal.dornick@corpltd.com", "Corp Ltd" };
	private static final String[] SalvorHardin = { "Salvor", "Hardin", "mayor@terminuscity.govt", "Terminus Corp" };
	private static final String[] EblingMis =    { "Ebling", "Mis", "ebling@ab.org", "AB Ltd" };
	private static final String[] PreemPalver =  { "Preem", "Palver", "speaker@second.foundation.govt", "Foundation 2" };
	private static final String[] HariSheldon =  { "Hari", "Sheldon", "h.sheldon@softeng.org", "Softeng Tech" };
	private static final String[] DorsVenabili = { "Dors", "Venabili", "d.venabili@corp.com", "Corp AB" };
	/**
	 * The following provide symbolic constants for the party values.
	 */
	private static final int FAMILY_INDEX = 1;
	private static final int GIVEN_INDEX = 0;
	private static final int EMAIL_INDEX = 2;
	private static final int ORG_INDEX = 3;
	
	/**
	 * The following provide values for visits. The format is:
	 * 0. Email for the host
	 * 1. Date of visit (ISO8601 format)
	 * 2. Time of visit (ISO8601 format)
	 * 3. Time of leaving (ISO8601 format)
	 */
	private static final String[] Visit_BaytaDarrell1 = { BaytaDarell[EMAIL_INDEX], "2019-08-02", "09:00", "10:00" };
	private static final String[] Visit_BaytaDarrell2 = { BaytaDarell[EMAIL_INDEX], "2019-08-02", "15:00", "16:00" };
	private static final String[] Visit_GaalDornick1 = { GaalDornick[EMAIL_INDEX], "2019-08-02", "13:00", "14:30" };
	private static final String[] Visit_GaalDornick2 = { GaalDornick[EMAIL_INDEX], "2019-10-12", "10:00", "12:00" };
	private static final String[] Visit_DorsVenabili1 = { DorsVenabili[EMAIL_INDEX], "2019-10-12", "07:30", "08:30" };
	private static final String[] Visit_PreemPalver1 = { PreemPalver[EMAIL_INDEX], "2019-10-12", "11:30", "13:30" };
	/**
	 * The following provide symbolic constants for the party values.
	 */
	private static final int VISITING_HOST_INDEX = 0;
	private static final int VISIT_DATE_INDEX = 1;
	private static final int VISIT_TIME_INDEX = 2;
	private static final int LEAVE_TIME_INDEX = 3;
	
	/**
	 * This is the main method that calls the different tests. Tests can be turned off or on by
	 * commenting/uncommenting
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		System.out.println("****Version: Checker ****");
		testSingleHostRegistration(BaytaDarell);
		testTwoHostRegistration(HariSheldon, EblingMis);
		testRegisteredHost(BaytaDarell);
		testRegisteredHosts(BaytaDarell, DorsVenabili );
		testCheckInOneVisitorForOneHost(EblingMis, Visit_BaytaDarrell1, BaytaDarell);
		testCheckOutOneVisitorForOneHost(HariSheldon, Visit_GaalDornick1, GaalDornick);
		testVisitorLogMultipleHostsNoVisitor(EblingMis); // Just one host to begin with
		testVisitorLogMultipleHostsNoVisitor(EblingMis, DorsVenabili, BaytaDarell); // now multiple hosts
		testVisitorLogSingleVisitorCheckedIn(GaalDornick, Visit_BaytaDarrell2, BaytaDarell, DorsVenabili, EblingMis);
		testMultipleVisitors(
				new String[][] { EblingMis, HariSheldon, HariSheldon }, 
				new String[][] { Visit_BaytaDarrell1, Visit_GaalDornick1, Visit_BaytaDarrell2 }, 
				new boolean[] {true, true, false}, 
				new String[][] { GaalDornick, DorsVenabili, BaytaDarell });

		// This tests visitors on multiple days
		testMultipleVisitors(
				new String[][] { EblingMis, SalvorHardin, HariSheldon, EblingMis }, 
				new String[][] { Visit_BaytaDarrell1, Visit_GaalDornick2, Visit_DorsVenabili1, Visit_PreemPalver1 }, 
				new boolean[] {true, false, true, false }, 
				new String[][] { GaalDornick, DorsVenabili, PreemPalver, BaytaDarell });
	}
		
	/* *******
	 * Methods with names beginning with 'test' directly provide test cases
	 *********/
	
	/**
	 * Register a single host and check that it is present using showHostDetails().
	 */
	private static void testSingleHostRegistration(String[] host) {
		System.out.println("==testSingleHostRegistration: Register one host==");
		VisitorMan visitorManager = new VisitorMan(COMPANY_NAME);
		checkRegisterAndShowHost(visitorManager, host);
	}
	
	/**
	 * Register two hosts and check they are present using showHostDetails().
	 */
	private static void testTwoHostRegistration(String[] host1, String[] host2) {
		System.out.println("==testTwoHostRegistration: Register two hosts==");
		VisitorMan visitorManager = new VisitorMan(COMPANY_NAME);
		checkRegisterAndShowHost(visitorManager, host1);
		checkRegisterAndShowHost(visitorManager, host2);
	}
	
	/**
	 * Register a single host and check that {@link VisitorMan#getRegisteredHosts()}
	 * reports only that host. Note that {@link #testRegisteredHosts(String[], String[]...)} 
	 * can do the same thing, but this is a little simpler to use, and is provided for
	 * illustration purposes.
	 * @param expectedArray
	 * @param host
	 */
	private static void testRegisteredHost(String[] host) {
		String iut = "getRegisteredHosts()";
		System.out.println("==testRegisteredHost: Register a single host and check that it is the only one registered==");
		VisitorMan visitorManager = new VisitorMan(COMPANY_NAME);
		checkRegisterAndShowHost(visitorManager, host);
		List<String> actual = visitorManager.getRegisteredHosts();
		List<String> expected = new ArrayList<String>();
		expected.add(constructHostFormalDetails(host));
		checkAndReport(iut, expected, actual);
	}

	/**
	 * Register more than one host and check that {@link VisitorMan#getRegisteredHosts()}
	 * reports only those hosts.
	 * @param expectedArray The details of the hosts in the standard format in the order
	 * expected. There must be exactly as many entries in this array as in the hosts parameter.
	 * @param hosts The specs for the hosts. 
	 * https://docs.oracle.com/javase/8/docs/technotes/guides/language/varargs.html
	 * 
	 */
	private static void testRegisteredHosts(String[]... hosts) {
		String iut = "getRegisteredHosts()";
		System.out.println("==testRegisteredHosts: Register multiple hosts and check that they are the only ones registered==");
		VisitorMan visitorManager = new VisitorMan(COMPANY_NAME);
		checkRegisterMultipleHosts(visitorManager, hosts);
		List<String> actual = visitorManager.getRegisteredHosts();
		List<String> expected = new ArrayList<String>();
		for (String[] host: hosts) {
			expected.add(constructHostFormalDetails(host));
		}
		
		checkAndReport(iut, expected, actual);
	}
	
	private static void testCheckInOneVisitorForOneHost(String[] visitor, String[] visitInfo, String[] host) {
		String iut = "getVisitorsOnSite()";
		System.out.println("==testCheckInOneVisitorForOneHost: Register one host, one visitor visits==");
		VisitorMan visitorManager = new VisitorMan(COMPANY_NAME);
		checkRegisterAndShowHost(visitorManager, host);
		visitorManager.checkIn(visitor[FAMILY_INDEX], visitor[GIVEN_INDEX], 
				visitor[ORG_INDEX], visitor[EMAIL_INDEX],
				visitInfo[VISITING_HOST_INDEX], visitInfo[VISIT_DATE_INDEX], visitInfo[VISIT_TIME_INDEX]);
		List<String> actual = visitorManager.getVisitorsOnSite();
		List<String> expected = new ArrayList<String>();
		expected.add(constructVisitorDetails(visitor));
		checkAndReport(iut, expected, actual);
	}

	private static void testCheckOutOneVisitorForOneHost(String[] visitor, String[] visitInfo, String[] host) {
		String iut = "getVisitorsOnSite()";
		System.out.println("==testCheckOutOneVisitorForOneHost: Register one host, one visitor visits then leaves==");
		VisitorMan visitorManager = new VisitorMan(COMPANY_NAME);
		checkRegisterAndShowHost(visitorManager, host);
		visitorManager.checkIn(visitor[FAMILY_INDEX], visitor[GIVEN_INDEX], 
				visitor[ORG_INDEX], visitor[EMAIL_INDEX],
				visitInfo[VISITING_HOST_INDEX], visitInfo[VISIT_DATE_INDEX], visitInfo[VISIT_TIME_INDEX]);
		visitorManager.checkOut(visitor[EMAIL_INDEX], visitInfo[LEAVE_TIME_INDEX]);
		List<String> actual = visitorManager.getVisitorsOnSite();
		List<String> expected = new ArrayList<String>(); // Should be no one visiting now
		checkAndReport(iut, expected, actual);
	}

	private static void testVisitorLogMultipleHostsNoVisitor(String[]... hosts) {
		String iut = "getVisitorLogReport()";
		System.out.println("==testVisitorLogMultipleHostsNoVisitor: Visitor log when multiple hosts but no visitors==");
		VisitorMan visitorManager = new VisitorMan(COMPANY_NAME);
		checkRegisterMultipleHosts(visitorManager, hosts);
		List<String> actual = visitorManager.getVisitorLogReport();
		List<String> expected = new ArrayList<String>();
		expected.add(COMPANY_NAME);
		checkAndReport(iut, expected, actual);
	}

	/**
	 * A visitor checks in with the first host in the list and does not check out
	 * @param hosts
	 */
	private static void testVisitorLogSingleVisitorCheckedIn(String[] visitor, String[] visitInfo, String[]... hosts) {
		String iut = "getVisiterLogReport()";
		System.out.println("==testVisitorLogSingleVisitorCheckedIn: Visitor log when only one visitor checked in, multiple hosts==");
		VisitorMan visitorManager = new VisitorMan(COMPANY_NAME);
		checkRegisterMultipleHosts(visitorManager, hosts);
		visitorManager.checkIn(visitor[FAMILY_INDEX], visitor[GIVEN_INDEX], 
				visitor[ORG_INDEX], visitor[EMAIL_INDEX], 
				visitInfo[VISITING_HOST_INDEX], visitInfo[VISIT_DATE_INDEX], visitInfo[VISIT_TIME_INDEX]);
		List<String> actual = visitorManager.getVisitorLogReport();
		List<String> expected = new ArrayList<String>();
		expected.add(COMPANY_NAME);
		expected.add(constructLogEntry(visitor, visitInfo, hosts[0], false));
		checkAndReport(iut, expected, actual);
	}
	
	private static void testMultipleVisitors(String[][] visitors, String[][] visitsInfo, boolean[] checkedOuts, String[][] hosts) {
		String iut = "getVisiterLogReport()";
		System.out.println("==testMultipleVisitors: Multiple visitors, some on site some not==");
		VisitorMan visitorManager = new VisitorMan(COMPANY_NAME);
		checkRegisterMultipleHosts(visitorManager, hosts);
		
		List<String> expected = new ArrayList<String>();
		expected.add(COMPANY_NAME);
		for (int i = 0; i < visitors.length; i++) {
			String[] visitor = visitors[i];
			String[] visitInfo = visitsInfo[i];
			boolean checkedOut = checkedOuts[i];
			visitorManager.checkIn(visitor[FAMILY_INDEX], visitor[GIVEN_INDEX], 
					visitor[ORG_INDEX], visitor[EMAIL_INDEX],
					visitInfo[VISITING_HOST_INDEX], visitInfo[VISIT_DATE_INDEX], visitInfo[VISIT_TIME_INDEX]);
			if (checkedOut) {
				visitorManager.checkOut(visitor[EMAIL_INDEX], visitInfo[LEAVE_TIME_INDEX]);
			}
			String[] host = getPartySpecFromEmail(visitInfo[VISITING_HOST_INDEX], hosts);
			expected.add(constructLogEntry(visitor, visitInfo, host, checkedOut));
		}
		List<String> actual = visitorManager.getVisitorLogReport();
		if (VERBOSE) {
			System.out.println("testMultipleVisitors - output from getVisitorLogReport()");
			printListFormatted(actual);
		}
		checkAndReport(iut, expected, actual);
	}
	
	/* ******************************************************************
	 * 'check' methods do the actual checking that what was provided
	 * matches what we expected.
	 */
	
	/**
	 * Regsiter a single host and check that it is present by calling {@link VisitorMan#showHostDetails(String)})
	 * @param visitorManager The visitor manager to register with
	 * @param host The spec for the host.
	 */
	private static void checkRegisterAndShowHost(VisitorMan visitorManager, String[] host) {
		visitorManager.registerHost(host[FAMILY_INDEX], host[GIVEN_INDEX], host[EMAIL_INDEX]);
		String actual = visitorManager.showHostDetails(host[EMAIL_INDEX]);
		String expected = constructHostFormalDetails(host);
		checkAndReport("showHostDetails()", expected, actual);
	}
	
	/**
	 * Regsiter multiple hosts and check that they are all present by calling {@link VisitorMan#showHostDetails(String)}).
	 * Note that this does NOT check ordering.
	 * @param visitorManager The visitor manager to register with
	 * @param host An array of host specs. Note that this uses the Java 'varargs' syntax.
	 * See https://docs.oracle.com/javase/8/docs/technotes/guides/language/varargs.html (and lots of other places)
	 */
	private static void checkRegisterMultipleHosts(VisitorMan visitorManager, String[]... hosts) { 
		for (String[] host: hosts) {
			checkRegisterAndShowHost(visitorManager, host);
		}
	}	

	/**
	 * Check that the actual list of strings provided by the implementation under test (iut) matches the expected
	 * list of strings (including order). Report the result (note use of VERBOSE).
	 * @param iut The implementation under test (use for reporting)
	 * @param expected The list of expected strings
	 * @param actual The list of actual strings.
	 */
	private static void checkAndReport(String iut, List<String> expected, List<String> actual) {
		if (!expected.equals(actual)) {
			System.out.println(iut + " expected:\n\t" + expected + "\n  returned incorrect actual:\n\t" + actual);
		} else if (VERBOSE) {
			System.out.println(iut + " for " + expected + " ---> PASS");
		}		
	}
	
	/**
	 * Check that the actual string provided by the implementation under test (iut) matches the expected
	 * string. Report the result (note use of VERBOSE).
	 * @param iut The implementation under test (use for reporting)
	 * @param expected The expected string
	 * @param actual The actual string.
	 */
	private static void checkAndReport(String iut, String expected, String actual) {
		if (!expected.equals(actual)) {
			System.out.println(iut + " expected:\n\t" + expected + "\n  returned incorrect actual:\n\t" + actual);
		} else if (VERBOSE) {
			System.out.println(iut + " for " + expected + " ---> PASS");
		}		
	}		
	
	/* **********************************************************
	 * Various utility methods, including for generating expected 
	 * values in the required format
	 */
	
	/**
	 * Convert a spec to a string with the format corresponding to what is
	 *  required for providing host details with the name in the formal format.
	 * 
	 * @param host The spec
	 * @return A string representing the host specified in the required format.
	 */
	private static String constructHostFormalDetails(String[] host) {
		String result = host[FAMILY_INDEX] + ", " + host[GIVEN_INDEX] + ". " + host[EMAIL_INDEX];
		return result;
	}

	/**
	 * Convert a spec to a string with the format that is required for 
	 * providing visitor details with the name in the formal format.
	 * 
	 * @param visitor The spec for the visitor
	 * @return A string representing the visitor specified in the required format.
	 */
	private static String constructVisitorFormalDetails(String[] visitor) {
		String result = visitor[FAMILY_INDEX] + ", " + visitor[GIVEN_INDEX] + " (" + visitor[ORG_INDEX] + "). " + 
				visitor[EMAIL_INDEX];
		return result;
	}
	
	/**
	 * Convert a spec to a string with the format that is required for 
	 * providing visitor details with the name in the informal format.
	 * 
	 * @param visitor The spec for the visitor
	 * @return A string representing the visitor specified in the required format.
	 */
	private static String constructVisitorDetails(String[] visitor) {
		String result = visitor[GIVEN_INDEX] + " " + visitor[FAMILY_INDEX] + " (" + visitor[ORG_INDEX] + "). " + 
				visitor[EMAIL_INDEX];
		return result;
	}
	
	/**
	 * Construct a string in the expected format for a log entry with the provided information.
	 * @param visitor The spec for the visitor
	 * @param visitInfo The details of the visit (host, check in time, check out time)
	 * @param host The spec for the host
	 * @param checkedOut Whether or not the visitor has checked out
	 * @return A string in the required format.
	 */
	private static String constructLogEntry(String[] visitor, String[] visitInfo, String[] host, boolean checkedOut) {
		String result = constructVisitorFormalDetails(visitor) + " visiting " + host[FAMILY_INDEX] + ", " + host[GIVEN_INDEX] +
				". Arrived:" + visitInfo[VISIT_DATE_INDEX] + "T" + visitInfo[VISIT_TIME_INDEX] + ".";
		if (checkedOut) {
			result += " Left:" + visitInfo[LEAVE_TIME_INDEX];
		} else {
			result += " On site.";
		}
		return result;
	}
	
	/**
	 * Provide the spec for the Party that has the specified email address, where the spec should be
	 * found in the array of specs provided (note use of varargs)
	 * @param emailAddress The email address to look up
	 * @param possibleSpecs The list of specs that should contain a spec with the specified email address
	 * @return
	 */
	private static String[] getPartySpecFromEmail(String emailAddress, String[]... possibleSpecs) {
		for (String[] partySpec: possibleSpecs) {
			if (partySpec[EMAIL_INDEX].equals(emailAddress)) {
				return partySpec;
			}
		}
		// If execution reaches this point something has gone wrong. Exceptions will be explained at some point.
		throw new RuntimeException("Problem with test. Cannot find spec for email address " + emailAddress);
	}
	
	/**
	 * Output the list provided to stdout in a format that might be easier to read
	 * @param list The list to print
	 */
	private static void printListFormatted(List<String> list) {
		System.out.println("---------");
		for (String line: list) {
			System.out.println(line);
		}
		System.out.println("---------");
	}
}
