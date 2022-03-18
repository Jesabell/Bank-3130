
public class Depositor {
	private Name name;
	private String social;
	
	public Depositor(){
		name = new Name();
		social = "";
	}
	
	public Depositor(Name nam, String ssn) {
		name = nam;
		social = ssn;
	}
	public Depositor(Depositor depositor) {
		this.name = new Name(depositor.name);
		this.social = depositor.social;
	}

	public Name getName(){
		Name nameCopy = new Name(name);
		return nameCopy;
	}
	
	public String getSSN() {
		return social;
	}
	public void setName(Name myName) {
		name = myName;
	}
	public void setSSN(String mySSN) {
		social = mySSN;
	}
	public boolean equals(Depositor depositor) {
		if(name.equals(depositor.name) && social.equals(depositor.social))
			return true;			//myName found
		else
			return false;			//myName not found
	}

	public String toString() {
		String str = String.format("%-10s %-10s", name.toString(), social);
		return str;
	}
}
