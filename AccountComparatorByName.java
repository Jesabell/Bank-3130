import java.util.Comparator;

public class AccountComparatorByName implements Comparator<Account> {

	public int compare(Account acct1, Account acct2) {
		Name name1 = acct1.getDepositor().getName();
		Name name2 = acct2.getDepositor().getName();
		if(name1.equals(name2)) {
			Integer acctNum1 = acct1.getAcctNumber();
			Integer acctNum2 = acct2.getAcctNumber();
			return (acctNum1.compareTo(acctNum2));

		}
		else
			return name1.compareTo(name2);

	}

}
