package visitorman;

/**
 * 
 * @author chenh
 * The Party class is meant to store the basic information for both visitors
 * and hosts, including family name, given name, email address and organisation.
 *
 *
 *
 */
public class Party {
	private String _Fname;
	private String _Gname;
	private String _email;
	private String _Organisation;
	/**
	 * 
	 * Create a Party object to store those information mentioned above.
	 * @param Fname: Family name of a visitor or a host.
	 * @param Gname: Given name of a visitor or a host.
	 * @param email: Email address of a visitor or a host.
	 * @param Organisation: Organisation name of a visitor.
	 * 
	 */
	public Party(String Fname, String Gname, String email, String Organisation) {
		_Fname=Fname;
		_Gname=Gname;
		_email=email;
		_Organisation=Organisation;
	}
	/**
	 * Access the email address of the party object.
	 * @return a string of the email address.
	 */
	public String AccessEmail () {
		return _email;
	}
	/**
	 * Access the family name of the party object.
	 * @return a string of the family name.
	 */
	public String AccessFname() {
		return _Fname;
	}
	/**
	 * Access the given name of the party object.
	 * @return a string of the given name.
	 */
	public String AccessGname() {
		return _Gname;
	}
	/**
	 * Access the organisation of the party object.
	 * @return a string of the organisation.
	 */
	public String AccessOrganisation() {
		return _Organisation;
	}
}
