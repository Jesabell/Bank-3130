import java.util.Calendar;

public class CheckingAccount extends Account{

	public CheckingAccount() {
		super();
	}
	public CheckingAccount(CheckingAccount myChecking) {
		super(myChecking); 
	}
	public CheckingAccount(Account myChecking) {
		super(myChecking); 
	}
	public CheckingAccount(Depositor depositor, int number, String type, double balance) {
		super(depositor, number, type, balance);
	}
	
	public TransactionReceipt clearCheck(TransactionTicket tick) {
		double pre = getBalance(tick).getPostTransactionBalance();
		double post = getBalance(tick).getPostTransactionBalance();
		post -= tick.getCheck().getAmount();
		if(pre < 2500) {
			post -= 1.50;
		}
		setBalance(post);
		TransactionReceipt receipt = new TransactionReceipt(tick, pre, post);
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
	public TransactionReceipt makeWithdrawal(TransactionTicket tick) {
		TransactionTicket ticket = new TransactionTicket("Checking balance");
		double held = getBalance(ticket).getPostTransactionBalance();
		double hold = getBalance(ticket).getPostTransactionBalance();
		hold = hold - tick.getTransactionAmount();
		if(held < 2500) {
			hold = hold - 1.50;
		}
		setBalance(hold);
		TransactionReceipt receipt = new TransactionReceipt(tick, held, hold);
		addTransaction(receipt);

		return receipt;
	}
	public boolean checkTooOld(Check check) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.MONTH, -6);
		if(check.getDate().before(today)) {
			return true;
		}
		else 
			return false;
	}
	public boolean postDatedCheck(Check check) {
		Calendar today = Calendar.getInstance();
		if(check.getDate().after(today)) {
			return true;
		}
		else 
			return false;
	}
	
	public boolean equals(CheckingAccount account) {
		if(getAcctNumber() == account.getAcctNumber())	
			return true;			
			else
				return false;			
		}

	public String toString() {
		TransactionTicket tick = new TransactionTicket("Checking balance");
		String str = String.format("%-10s %-10s %-10s %-10s %-10s", getDepositor().toString(), getAcctNumber(), getAcctType(), getStatus(),"$:" + String.format("%.2f",getBalance(tick).getPostTransactionBalance()));
		return str;
	}

	
}
