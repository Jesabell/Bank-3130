
public class SavingAccount extends Account {

	public SavingAccount() {
		super();
	}
	
	public SavingAccount(SavingAccount mySaving) {
		super(mySaving); 
	}
	public SavingAccount(Account myAccount) {
		super(myAccount);
	}
	public SavingAccount(Depositor depositor, int number, String type, double balance) {
		super(depositor, number, type, balance);
	}
	public TransactionReceipt makeWithdrawal(TransactionTicket tick) {
		TransactionTicket ticket = new TransactionTicket("Checking balance");
		double held = getBalance(ticket).getPostTransactionBalance();
		double hold = getBalance(ticket).getPostTransactionBalance();
		hold = hold - tick.getTransactionAmount();
		setBalance(hold);
		TransactionReceipt receipt = new TransactionReceipt(tick, held, hold);
		addTransaction(receipt);

		return receipt;
	}
	public TransactionReceipt makeDeposit(TransactionTicket tick) {
		TransactionTicket ticket = new TransactionTicket("Checking balance");
		double held = getBalance(ticket).getPostTransactionBalance();
		double hold = getBalance(ticket).getPostTransactionBalance();;
		hold += tick.getTransactionAmount();
		setBalance(hold);
		TransactionReceipt receipt = new TransactionReceipt(tick, held, hold);
		addTransaction(receipt);
		return receipt;
	}
	public String toString() {
		TransactionTicket tick = new TransactionTicket("Checking balance");
		String str = String.format("%-10s %-10s %-10s %-10s %-10s", getDepositor().toString(), getAcctNumber(), getAcctType(), getStatus(), "$:" + String.format("%.2f",getBalance(tick).getPostTransactionBalance()));
		return str;
	}
	public boolean equals(SavingAccount account) {
	if(getAcctNumber() == account.getAcctNumber())	
		return true;			
		else
			return false;			
	}
	
	
}
