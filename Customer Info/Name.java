
public class Name implements Comparable<Name> {
	private String last, first;
	
	public Name() {
		last = "";
		first = "";
	}
	
	public Name(String lastName, String firstName) {
		last = lastName;
		first = firstName;
	}
	public Name(Name myName) {
		this.last = myName.last;
		this.first = myName.first;	
	}
	
	public void setLast(String lastName) {
		last = lastName;
	}

	public void setFirst(String firstName) {
		first = firstName;
	}

	public String getLast() {
		return last;
	}

	public String getFirst() {
		return first;
	}
	
	public Name getName() {
		Name nameCopy = new Name(last, first);
		return nameCopy;
	}
	
	
	public String toString() {
		String str = String.format("%-10s %-10s" , last, first);
		return str;
	}

	public boolean equals(Name myName) {
		if(this.last.equals(myName.last) && this.first.equals(myName.first))
			return true;			
		else
			return false;			
	}

	public int compareTo(Name name) {
		if(this.equals(name)) {
			return 0;
		} 
		else if(!(this.last.equals(name.last))) {
			return (this.last.compareTo(name.last));
		}
		else {
			return (this.first.compareTo(name.first));
		}
	}

}