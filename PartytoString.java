package visitorman;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author chenh
 * The PartytoString class is meant to provide strings  or list of strings in different formatting 
 * of a given party object or a list of party objects. 2 objects are included. 
 * 
 * 
 *
 */
public class PartytoString {
	
	//Below are fields including List<Party> and String types.
	private List<String> _VisitorsDetail = new ArrayList<String>();
	private List<String> _HostsDetail = new ArrayList<String>();
	private List<Party> _PartyDetail = new ArrayList<Party>();
	private Party _Party;
	private String _OneVisitor;
	/**
	 * 
	 * Create a PartytoString object for a specified list of party.
	 * For each of the object of the list, their states needs to be reported.
	 * 
	 * @param PartyDetail A list of Party objects. 
	 */
	public PartytoString(List<Party> PartyDetail) {
		_PartyDetail = PartyDetail;
	}
	
	/**
	 * Provide a list of string describing the host.
	 * @return A list of string describing the host in the formatting "with format FORMAL_NAME ". " EMAIL_ADDRESS."
	 */
	public List<String> GetHostsDetail (){
		for (int i = 0; i<_PartyDetail.size(); i++) {
			Party CurrentDetail = _PartyDetail.get(i);
			_HostsDetail.add(i, CurrentDetail.AccessFname() + ", " + CurrentDetail.AccessGname() + ". " + CurrentDetail.AccessEmail());
		}
		return _HostsDetail;
	}
	/**
	 * Provide a list of string describing the visitors.
	 * 
	 * @return A list of string describing a list of visitor party with the formatting of :
	 * One string per visitor, with the format 
	 * INFORMAL_NAME " (" ORGANISATION "). " EMAIL_ADDRESS
	 * 	 
	 * 
	 */
	public List<String> GetVisitorssDetail (){
		for (int i = 0; i<_PartyDetail.size(); i++) {
			Party CurrentDetail = _PartyDetail.get(i);
			_VisitorsDetail.add(i, CurrentDetail.AccessGname()+ " " + CurrentDetail.AccessFname()+ " (" +
					CurrentDetail.AccessOrganisation() + "). "+ CurrentDetail.AccessEmail());
		}
		return _VisitorsDetail;
	}
	/**
	 * Create a PartytoString object for a specified party.
	 * @param PartyDetail A party object whose states needs to be reported.
	 */
	public PartytoString (Party PartyDetail) {
		_Party = PartyDetail;
	}
	/**
	 * Provide a string describing a single visitor.
	 * The formatting is the same as {@link #GetVisitorssDetail()}
	 * @return A string representing the state of PartyDetail.
	 */
	public String GetOneVisitorDetail() {
		_OneVisitor = _Party.AccessFname()+ ", " + _Party.AccessGname()+ " (" +
				_Party.AccessOrganisation() + "). "+ _Party.AccessEmail();
		return _OneVisitor;
	}
}
