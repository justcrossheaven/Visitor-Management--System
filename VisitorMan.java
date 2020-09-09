package visitorman;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage visitors. When a visitor arrives, he must check in by providing his name, the organisation
 * he belongs to, his email address, and specify who he is visiting. 
 * In order to receive a visitor, the host must be registered, which is done by providing her name
 * and email address.
 * All names and email addresses must be unique.
 * All names consist of a given name and a family name. A name may be formatted as either
 * "formal" or "informal"
 * The formal format is family name followed by comma, followed by a space, followed by the given name.
 * The informal format is given name followed by space, followed family name.
 * 
 * <P>SOFTENG251 2020 Assignment 2. Copyright Ewan Tempero, The University of Auckland, 2020.
 */
public class VisitorMan {
	private String _organisationName;
	private List<Party> _hosts = new ArrayList<Party>();
	private int _NumVisitors;
	
	//Below are fields that created for convenience and storing values.
	private VisitorLog _LogVisitorsGroups = new VisitorLog();
	private List<Party> _VisitorParty = new ArrayList<Party>();
	private List<Party> _VisitorsPartyOnSite = new ArrayList<Party>();
	/**
	 * Create a VisitorMan object for the specified organisation.
	 * @param organisationName The name of the organisation whose visitors are being managed.
	 */
	public VisitorMan(String organisationName) {
		_organisationName = organisationName;
		System.out.println("A VisitorMan object was created for organisation:" + _organisationName);
	}
	/**
	 * Register someone as able to host a visitor. It is assumed that there is always 
	 * both a given and a family name, that the host's name is unique (no other hosts 
	 * with the same name), and the email address is unique.
	 * @param familyName The family name of the host
	 * @param givenName The given name of the host
	 * @param email The email address of the host.
	 */
	public void registerHost(String familyName, String givenName, String email) {
		Party host = new Party(familyName, givenName, email, null);
		_hosts.add(host);
	}

	/**
	 * Provide a string that describes the host with the specified email address.
	 * @param emailAddress The email address of the host to describe.
	 * @return A string describing the host, with format FORMAL_NAME ". " EMAIL_ADDRESS. 
	 * Returns null if there is no host with the email address.
	 */
	public String showHostDetails(String emailAddress) {
		for(int i = 0; i<_hosts.size(); i++) {
			Party host = _hosts.get(i);
			if(host.AccessEmail() == emailAddress) {
				return host.AccessFname() + ", " + host.AccessGname() + ". " + emailAddress;
			}
		}
		return null;
	}
	
	/**
	 * Provide a list of strings describing all the registered hosts.
	 * Each string should use the same format as {@link #showHostDetails(String)} 
	 * and in the order that the hosts were registered.
	 * @return A list of string with host details.
	 */
	public List<String> getRegisteredHosts() {
		PartytoString HostsGroup = new PartytoString(_hosts);
		return HostsGroup.GetHostsDetail();
	}

	/**
	 * Record that a visitor with the specified details is visiting the
	 * host with the specified email address on the date given and starting
	 * at the specified time. There are always both given and family names,
	 * and names and email addresses are unique.
	 * @param familyName The family name of the visitor
	 * @param givenName The given name of the visitor
	 * @param organisation The organisation the visitor is from
	 * @param visitorEmail The email address of the visitor
	 * @param hostEmail The email address of the host the visitor is visiting
	 * @param visitDate The date of the visit (ISO8601 format)
	 * @param visitStartTime The time of the start of the visit (ISO8601 format)
	 */
	public void checkIn(String familyName, String givenName, String organisation, String visitorEmail, 
			String hostEmail, String visitDate, String visitStartTime) {
		Party CurrentVisitor= new Party(familyName, givenName, visitorEmail, organisation);
		_VisitorParty.add(_NumVisitors, CurrentVisitor); 
		_NumVisitors++;
		LogEntry AVisitor = new LogEntry (CurrentVisitor, hostEmail, visitDate, visitStartTime, false, null);
		_LogVisitorsGroups.StoreVisitor(AVisitor);
	}
	
	/**
	 * Record that the visitor with the specified email address checked
	 * out at the specified time.
	 * @param emailAddress The visitor's email address.
	 * @param checkOutTime The checkout time (ISO8601 format)
	 */
	public void checkOut(String emailAddress, String checkOutTime) {
		for (int i = 0; i<_NumVisitors; i++ ) {
			Party LeftVisitor = _VisitorParty.get(i);
			if(LeftVisitor.AccessEmail() == emailAddress) {  //Change the corresponding visitor's state if he/she has checked out.
				_LogVisitorsGroups.ChangeState(i, checkOutTime);
			}
		}
	}

	/**
	 * Return a report of all the visitors current on site, that is,
	 * those that have checked in but not checked out.
	 * The report is a list of strings (one string per visitor, with the format 
	 * INFORMAL_NAME " (" ORGANISATION "). " EMAIL_ADDRESS
	 * @return A list of strings with the visitors on site.
	 */
	public List<String> getVisitorsOnSite() {
		List<LogEntry> LogEntryVisitorList = _LogVisitorsGroups.GetVisitorLog();
		for (int i = 0; i<_NumVisitors; i++){
			Party CurrentVisitor = _VisitorParty.get(i);
			LogEntry CurrentVisitorInfo = LogEntryVisitorList.get(i);
			if(!CurrentVisitorInfo.Checkout()) {     //Check whether the visitor is checked out the report those who is not.
				CurrentVisitor = CurrentVisitorInfo.AccessVisitor();
				_VisitorsPartyOnSite.add(CurrentVisitor);
			}
		}
		PartytoString VisitorsInfo = new PartytoString(_VisitorsPartyOnSite);
		return VisitorsInfo.GetVisitorssDetail();
	}
	/**
	 * Return a report of the complete visitor log as a list of strings (one string
	 * per line in the report). The first line of the report is the company name. The
	 * remaining lines are, in order of arrival, one line per visitor giving full details
	 * of visitor, their check in time, and their check out time (or empty if they are
	 * still on site).
	 * @return The report for the visitor log.
	 */
	public List<String> getVisitorLogReport() {
		List<LogEntry> VisitorsList = _LogVisitorsGroups.GetVisitorLog();
		List<String> result = new ArrayList<String>();
		for (int i = 0; i<_NumVisitors; i++ ) {
			PartytoString VisitorInfo = new PartytoString(_VisitorParty.get(i));
			LogEntry CurrentVisitor = VisitorsList.get(i);
			String resultindex = new String();
			resultindex = VisitorInfo.GetOneVisitorDetail();
			for (int j = 0; j<_hosts.size(); j++) {    //Report the corresponding host's name of the visitor.
				Party CurrentHost = _hosts.get(j);
				if(CurrentVisitor.AccesshostEmail() == CurrentHost.AccessEmail()) { 
					resultindex += " visiting " + CurrentHost.AccessFname() +  ", "  + CurrentHost.AccessGname();
					break;
				}
			}
			resultindex += ". Arrived:" + CurrentVisitor.AccessDate() + "T" + CurrentVisitor.AccessTime() + ".";
			if (CurrentVisitor.Checkout()) {            //Check whether the visitor have checked out and report the state.
				resultindex += " Left:" + CurrentVisitor.AccessLeftTime();
			} else {
				resultindex += " On site.";
			}
			result.add(i, resultindex);
		}
		result.add(0, _organisationName);
		return result;
	}
}
