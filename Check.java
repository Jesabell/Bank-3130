
import java.util.Calendar;

public class Check {

	private int acct;
	private double amount;
	private Calendar day;
	
	public Check() {
		acct = 0;
		amount = 0.0;
		day = Calendar.getInstance();
	}
	//Constructor with Parameters
	public Check(int acctNum, double am, Calendar date) {
		acct = acctNum;
		amount = am;
		day = date;
	}
	public Check(Check myCheck) {
		this.acct = myCheck.acct;
		this.amount = myCheck.amount;
		this.day = myCheck.day;
	}
	
	public double getAmount() {
		return amount;
	}
	public Calendar getDate() {
		return day;
	}
	
	public Check getCheck() {
		Check checkCopy = new Check(acct, amount, day);
		return checkCopy;
	}
	public String toString() {
		String str = String.format("%-10s %-10s %-10s %-10s", acct, amount, day.getTime());
		return str;
	}

}
