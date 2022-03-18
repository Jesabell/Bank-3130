import java.util.Calendar;

public class CDAccount extends Account {
	private Calendar day;
	public CDAccount() {
		super();
		this.day = Calendar.getInstance();
	}
	public CDAccount(CDAccount myCD) {
		super(myCD); 
		this.day = myCD.getMaturityDate();
	}
	public CDAccount(Account myAccount) {
		super(myAccount);
		this.day = day;
	}
	public CDAccount(Depositor depositor, int number, String type, double balance, Calendar day) {
		super(depositor, number, type, balance);
		this.day = day;
	}
	
	public Calendar getMaturityDate() {
		return day;
	}
	public TransactionReceipt makeDeposit(TransactionTicket tick) {
		TransactionTicket ticket = new TransactionTicket("Checking balance");
		double hold = getBalance(ticket).getPostTransactionBalance();
		hold += tick.getTransactionAmount();
		setBalance(hold);
		day.add(Calendar.MONTH, tick.gettermOfCD());
		TransactionReceipt receipt = new TransactionReceipt(tick, getBalance(ticket).getPostTransactionBalance(), hold);
		addTransaction(receipt);
		return receipt;
		}
	
	public TransactionReceipt makeWithdrawal(TransactionTicket tick) {
		TransactionTicket ticket = new TransactionTicket("Checking balance");
		double hold = getBalance(ticket).getPostTransactionBalance();
		hold -= tick.getTransactionAmount();
		day.add(Calendar.MONTH, tick.gettermOfCD());
		setBalance(hold);
		TransactionReceipt receipt = new TransactionReceipt(tick, getBalance(ticket).getPostTransactionBalance(), hold);
		addTransaction(receipt);

		return receipt;
	}
	public String turnDate(){
		String s = day.get(Calendar.MONTH) + "/" + day.get(Calendar.DATE) + "/" + day.get(Calendar.YEAR);
	return s;
	}
	public boolean equals(CheckingAccount account) {
		if(getAcctNumber() == account.getAcctNumber())	
			return true;			
			else
				return false;			
		}


	public String toString() {
		TransactionTicket tick = new TransactionTicket("Checking balance");
		String str = String.format("%-10s %-10s %-10s %-10s %-10s %-10s",getDepositor().toString(), getAcctNumber(), getAcctType(), getStatus(), "$:" + String.format("%.2f",getBalance(tick).getPostTransactionBalance()), turnDate());
		return str;
	}

}

