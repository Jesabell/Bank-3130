import java.util.Comparator;

public class AccountComparatorByAcctNumber implements Comparator<Account> {
	
	public int compare(Account acct1, Account acct2) {
		Integer acctNum1 = acct1.getAcctNumber();
		Integer acctNum2 = acct2.getAcctNumber();
		return acctNum1.compareTo(acctNum2);

	}

}
