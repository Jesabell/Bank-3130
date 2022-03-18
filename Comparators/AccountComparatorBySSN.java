import java.util.Comparator;

public class AccountComparatorBySSN implements Comparator<Account> {
	
	public int compare(Account acct1, Account acct2) {
		String ssn1 = acct1.getDepositor().getSSN();
		String ssn2 = acct2.getDepositor().getSSN();
		if(ssn1.equals(ssn2)) {
			Integer acctNum1 = acct1.getAcctNumber();
			Integer acctNum2 = acct2.getAcctNumber();
			return (acctNum1.compareTo(acctNum2));
		}
		else
			return ssn1.compareTo(ssn2);
	}

}
