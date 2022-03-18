import java.util.Comparator;

public class AccountComparatorByBalance implements Comparator<Account> {
	
	public int compare(Account acct1, Account acct2) {
		TransactionTicket ticket = new TransactionTicket("Checking balance");
		Double bal1 = acct1.getBalance(ticket).getPostTransactionBalance();
		Double bal2 = acct2.getBalance(ticket).getPostTransactionBalance();
		if(bal1.equals(bal2)) {
			Integer acctNum1 = acct1.getAcctNumber();
			Integer acctNum2 = acct2.getAcctNumber();
			return (acctNum1.compareTo(acctNum2));
		}
		else
			return bal1.compareTo(bal2);	
	}
}
