package visitorman;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author chenh
 * The VisitorLog class is to represent information of all visits, which are included in
 * different LogEntry objects.
 * 
 * 
 */
public class VisitorLog {
	//Below is the list of LogEntry field, which is meant to store all visits.
	private List<LogEntry> _VisitorGroup = new ArrayList<LogEntry>();
	private int _NumVisitors = 0;
	
	/**
	 * Create a VisitorLog default object.
	 */
	public VisitorLog(){
	}
	/**
	 * Store a single LogEntry object representing a single visit.
	 * @param Visitor A single LogEntry object representing a single visit.
	 */
	public void StoreVisitor (LogEntry Visitor){
		_VisitorGroup.add(_NumVisitors, Visitor);
		_NumVisitors++;
	}
	/**
	 * 
	 * Return the list storing all visits.
	 * @return The list of LogEntry field.
	 */
	public List<LogEntry> GetVisitorLog (){
		return _VisitorGroup;
	}
	/**
	 * Change the state of a specified visit if the visitor is recorded to be checked out.
	 * @param i The index of the specified visits in the list.
	 * @param CheckOutTime The check-out time of the visitor.
	 */
	public void ChangeState(int i, String CheckOutTime) {
		LogEntry Visitor = _VisitorGroup.get(i);
		Visitor.IfCheckOut();
		Visitor.AddLeftTime(CheckOutTime);
		_VisitorGroup.set(i, Visitor);
	}
}
