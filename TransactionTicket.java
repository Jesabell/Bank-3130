import java.util.Calendar;

public class TransactionTicket {
	private Calendar day = Calendar.getInstance();
	private double amount;
	private int CD;
	private String type;
	private Account acct;
	private Check check;
	private String ssn;
	
	
	public TransactionTicket() {
		day = Calendar.getInstance();
		amount = 0.0;
		CD = 0;
		type = "";
		acct = new Account();
		check = new Check();
		ssn = "";
	}
	
	public TransactionTicket(TransactionTicket myTicket) {
		this.day = myTicket.day;
		this.amount = myTicket.amount;	
		this.CD = myTicket.CD;
		this.type = myTicket.type;
		this.acct = myTicket.acct;
		this.check = myTicket.check;
		this.ssn = myTicket.ssn;
	}
	
	public TransactionTicket(String type) {
        this.type = type;
	}
	
	public TransactionTicket(String type, double amount) {
        this.type = type;
        this.amount = amount;
	}
	public TransactionTicket(String type, String ssn) {
        this.type = type;
        this.ssn = ssn;
	}
	public TransactionTicket(String type, Check check) {
        this.type = type;
        this.check = check;
	}
	public TransactionTicket(String type, double amount, Account acct) {
        this.type = type;
        this.amount = amount;
        this.acct = acct;
	}
	
	public TransactionTicket(String type, double amount, int CD) {
        this.type = type;
        this.CD = CD;
        this.amount = amount;
	}
	
	public Calendar getDateOfTransaction() {
		Calendar date = day;
		return date;
	}
	
	public String getTransactionType() {
		return type;
	}
	public String getSSN() {
		return ssn;
	}
	public double getTransactionAmount() {
		return amount;
	}
	
	public int gettermOfCD() {
		return CD;
	}
	public Account getAccount(){
		return acct;
	}
	public Check getCheck() {
		return check;
	}
	public String turnDate(){
		String s = day.get(Calendar.MONTH) + "/" + day.get(Calendar.DATE) + "/" + day.get(Calendar.YEAR);
	return s;
	}
	
	public String toString() {
		String str = String.format("%-11s %-11s", turnDate(), type);
		return str;
	}
	
}

