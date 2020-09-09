package visitorman;
/**
 * 
 * @author chenh
 * The LogEntry class is meant to store and display all information
 * of a single visit, including the information of visitor, which could be stored by party object,
 * the email of the host the visitor is going to visit, the check-in date and time, and check-out time.
 * Dates and time are recorded in ISO8601 format.
 * Assuming the visitor is going to leave in check-in date.
 *
 */
public class LogEntry {
	
	//Bellow are fields that created for representing all the information.
	private String _hostEmail;
	private String _visitDate;
	private String _visitStartTime;
	private Party _Visitor;
	private boolean _CheckOut = false;
	private String _visitEndTime;
	
	/**
	 * An LogEntry object is created for the information of a visit.
	 * @param Visitor A party representing the visitor.
	 * @param hostEmail The email address of the host.
	 * @param visitDate The check-in date.
	 * @param visitStartTime The check-in time.
	 * @param CheckOut A boolean varaible representing whether the visitor is checked out.
	 * @param visitEndTime The check-out time if the visitor is checked out.
	 */
	public LogEntry(Party Visitor, String hostEmail, String visitDate, String visitStartTime, boolean CheckOut, String visitEndTime) {
		_Visitor = Visitor;
		_hostEmail = hostEmail;
		_visitDate = visitDate;
		_visitStartTime = visitStartTime;
		_CheckOut = CheckOut;
		_visitEndTime = visitEndTime;
	}
	/**
	 * Access the basic information of a visitor in Party type.
	 * @return a party object of the visitor.
	 */
	public Party AccessVisitor () {
		return _Visitor;
	}
	/**
	 * Access the email address of the host.
	 * @return a string of the email address.
	 */
	public String AccesshostEmail() {
		return _hostEmail;
	}
	/**
	 * Access the date of entry.
	 * @return a string of date (ISO8601 format).
	 */
	public String AccessDate() {
		return _visitDate;
	}
	/**
	 * Access the check-in time.
	 * @return a string of check-in time (ISO8601 format).
	 */
	public String AccessTime() {
		return _visitStartTime;
	}
	/**
	 * Change the state of the visitor if he/she is checked-out.
	 *
	 */
	public void IfCheckOut() {
		_CheckOut = true;
	}
	/**
	 * Access the the state representing whether the visitor is checked-out.
	 * @return a boolean variable of the state.
	 */
	public boolean Checkout() {
		return _CheckOut;
	}
	/**
	 * Add the left time to the object.
	 *
	 */
	public void AddLeftTime(String LeftTime) {
		_visitEndTime = LeftTime;
	}
	/**
	 * Access the left time (check-out time) of the object.
	 * @return a string of check-out time.
	 */
	public String AccessLeftTime() {
		return _visitEndTime;
	}
}
