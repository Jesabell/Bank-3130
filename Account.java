import java.util.Calendar;
import java.util.Comparator;
import java.util.ArrayList;

public class Account implements Comparable<Account> {
	private Depositor depositor;
	private int number;
	private String type;
	private String status = "open";
	private double balance;
	private ArrayList<TransactionReceipt> receipts;
	
	public Account() {
		depositor = new Depositor();
		number = 0;
		type = "";
		status = "open";
		balance = 0.00;
		receipts = new ArrayList<>();
	}
	public Account(Account myAccount) {
		this.depositor = new Depositor(myAccount.depositor);
		this.number = myAccount.number;
		this.type = myAccount.type;
		this.status = myAccount.status;
		this.balance = myAccount.balance;
		this.receipts = new ArrayList<>(myAccount.receipts);
	}
	public Account(Depositor depositor, int number, String type, double balance) {
		this.depositor = depositor;
		this.number = number;
		this.type = type;
		this.balance = balance;
		receipts = new ArrayList<>();
	}

	public TransactionReceipt getBalance(TransactionTicket tick) {
		TransactionReceipt receipt = new TransactionReceipt(tick, balance);
		return receipt;
	}
	
	public TransactionReceipt makeDeposit(TransactionTicket tick) {
		double hold = balance;
		balance += tick.getTransactionAmount();
		TransactionReceipt receipt = new TransactionReceipt(tick, hold, balance);
		addTransaction(receipt);
		return receipt;
	}
	public TransactionReceipt makeWithdrawal(TransactionTicket tick) {
		double hold = balance;
		balance -= tick.getTransactionAmount();
		TransactionReceipt receipt = new TransactionReceipt(tick, hold, balance);
		addTransaction(receipt);

		return receipt;
	}
	public TransactionReceipt clearCheck(TransactionTicket tick) {
		double pre = getBalance(tick).getPostTransactionBalance();
		double post = getBalance(tick).getPostTransactionBalance();
		post -= tick.getCheck().getAmount();
		setBalance(post);
		TransactionReceipt receipt = new TransactionReceipt(tick, pre, post);
		addTransaction(receipt);
		return receipt;
	}
	
	public TransactionReceipt closeAcct(TransactionTicket tick) {
		status = "closed";
		TransactionReceipt receipt = new TransactionReceipt(tick, balance, balance);
		return receipt;
		
	}
	
	public TransactionReceipt reopenAcct(TransactionTicket tick) {
		status = "open";
		TransactionReceipt receipt = new TransactionReceipt(tick, balance, balance);
		return receipt;
	}
	
	public ArrayList<TransactionReceipt> getTransactionHistory(){
		return receipts;
	}
	
	public Depositor getDepositor() {
		Depositor depositorCopy = new Depositor(depositor);
		return depositorCopy;
	}
	
	public int getAcctNumber() {
		 int numCopy = number;
		return numCopy;
	}
	
	public String getAcctType() {
		String typeCopy = type;
		return typeCopy;
	}
	
	public String getStatus(){
		String statusCopy = status;
		return statusCopy;
	}
	public void setBalance(double bal) {
		balance = bal;
	}
	public void addTransaction(TransactionReceipt receipt) {
		receipts.add(receipt);
	}

	public int hashCode() {
		@SuppressWarnings("removal")
		Integer num = new Integer(number);
		return num.hashCode();
	}

	public boolean equals(Account account) {
		if (depositor.equals(account.depositor) && number == account.number && balance == account.balance && type.equals(account.type) && status.equals(account.status) /*&& day.equals(account.day)*/ && receipts.equals(account.receipts))
			return true;			
		else
			return false;			
	}
	
	
	public String toString() {
		String str = String.format("%-10s %-10s %-10s %-10s %-10s", depositor.toString(),number, type, status, "$:" + String.format("%.2f", balance) /*turnDate()*/);
		return str;
	}
	public int compare(Account acct1, Account acct2) {
		Integer acctNum1 = acct1.getAcctNumber();
		Integer acctNum2 = acct2.getAcctNumber();
		return acctNum1.compareTo(acctNum2);

	}
	public int compareTo(Account o) {
		Integer a = new Integer(number);
		Integer b = new Integer(o.getAcctNumber());
		return a.compareTo(b);
	}


	
}
