import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;

public class pgmHW21  {

	public static void main(String[] args) throws IOException
	{
		//variable declarations
		Bank bank = new Bank();								//instantiate the Bank
	    char choice;										//menu item selected
	    boolean notDone = true;								//loop control flag

	    // open input test cases file
	    File testFile = new File("C:\\Users\\nades\\eclipse-workspace\\hw0\\src\\myTestCases.txt");
	    
	    //create Scanner object
	    Scanner kybd = new Scanner(testFile);
	    //Scanner kybd = new Scanner(System.in);

	    // open the output file
	    File file = new File("pgmOutput.txt");
	    FileWriter out = new FileWriter(file);
	    PrintWriter outFile = new PrintWriter(out);

	    /* first part */
	    /* fill and print initial database */
	    readAccts(bank);
	    printAccts(bank, outFile);
	    testContains(bank, outFile);

	    /* second part */
	    /* prompts for a transaction and then */
	    /* call functions to process the requested transaction */
	    do
	    {
	        menu();
	        
	        choice = kybd.next().charAt(0) ;
	        try
	        {
	        	switch(choice)
	        	{
	            	case 'q':
	            	case 'Q':
	            		notDone = false;
	            		printAccts(bank, outFile);
	            		printAcct3(bank, outFile);
	            		break;
	            	case 'b':
	            	case 'B':
	            		balance(bank,outFile,kybd);
	            		break;
	            	case 'd':
	            	case 'D':
	            		deposit(bank,outFile,kybd);
	            		break;
	            	case 'w':
	            	case 'W':
	            		withdrawal(bank,outFile,kybd);
	            		break;
	            	case 'c':
	            	case 'C':
	            		clearCheck(bank,outFile,kybd);
	            		break;
	            	case 'n':
	            	case 'N':
	            		newAcct(bank,outFile,kybd);
	            		break;
	            	case 's':
	            	case 'S':
	            		closeAcct(bank,outFile,kybd);
	            		break;
	            	case 'r':
	            	case 'R':
	            		reopenAcct(bank,outFile,kybd);
	            		break;
	            	case 'x':
	            	case 'X':
	            		deleteAcct(bank,outFile,kybd);
	            		break;
	            	case 'i':
	            	case 'I':
	            		acctInfo(bank,outFile,kybd);
	            		break;
	            	case 'h':
	            	case 'H':
	            		acctInfoWithTransactionHistory(bank,outFile,kybd);
	            		break;
	            	default:
	            		throw new InvalidMenuSelectionException(choice);
	            
        		}
	        }
	        catch (InvalidMenuSelectionException ex) {
	        	outFile.println("Error: " + choice + " is an invalid selection -  try again");
	        	outFile.println(ex.getMessage());
	        	outFile.println();
	        }
	        // give user a chance to look at output before printing menu
	        pause(kybd);
	    } while (notDone);
	    
	    //close the output file
	    
	    //close the test cases input file
	    kybd.close();
	    
	    outFile.println();
	    outFile.println("The program is terminating");
	    outFile.close();
	}

	/* Method menu()
	 * Input:
	 *  none
	 * Process:
	 *  Prints the menu of transaction choices
	 * Output:
	 *  Prints the menu of transaction choices
	 */
	public static void menu()
	{
		 System.out.println();
		    System.out.println("Select one of the following transactions:");
		    System.out.println("\t     W -- Withdrawal");
		    System.out.println("\t     D -- Deposit");
		    System.out.println("\t     C - Clear Check");
		    System.out.println("\t     N -- New Account");
		    System.out.println("\t     B -- Balance Inquiry");
		    System.out.println("\t     I -- Account Info");
		    System.out.println("\t     H -- Account Info plus Account Transaction History");
		    System.out.println("\t     S -- Close Account");
		    System.out.println("\t     R -- Reopen a Closed Account");
		    System.out.println("\t     X -- Delete Account");
		    System.out.println("\t     Q -- Quit");
		    System.out.println();
		    System.out.print("\tEnter your selection: ");
	}

	/* Method readAccts()
	 * Input:
	 *  bank - reference to the Bank object
	 *  maxAccts - maximum number of active accounts allowed
	 * Process:
	 *  Reads the initial database of accounts
	 * Output:
	 *  Fills in the initial array of Account objects within the Bank object
	 *  and also set the inital number of active accounts (stored in the Bank object)
	 */
	public static void readAccts(Bank bank) throws IOException
	{
		ArrayList<Account> accounts = new ArrayList<>();
		ArrayList<String> hold = new ArrayList<>();
		RandomAccessFile randomFile = new RandomAccessFile("C:\\Users\\nades\\eclipse-workspace\\hw0\\src\\initAccounts.txt", "r");
		String tmp;
		while((tmp = randomFile.readLine()) != null) {
			hold.add(tmp);
		}
		for(int i = 0; i < hold.size(); i++) {
			String space = " ";
			Calendar date = Calendar.getInstance();
			String[] x = hold.get(i).split(space);
			Name name = new Name(x[1], x[0]);
			Depositor depositor = new Depositor(name, x[2]);
			int y = Integer.parseInt(x[3]);
			double z = Double.parseDouble(x[5]);
			if(x[4].equals("CD") ) {
				date = convertToCalendar(x[6]);
			}
			TransactionTicket ticket = new TransactionTicket("Open account", z);
			TransactionReceipt receipt = new TransactionReceipt(ticket);
			if(x[4].equals("Savings")) {
				SavingAccount account = new SavingAccount(depositor, y, x[4], z);
				account.addTransaction(receipt);
				accounts.add(account);
			}
			if(x[4].equals("Checking")) {
				CheckingAccount account = new CheckingAccount(depositor, y, x[4], z);
				account.addTransaction(receipt);
				accounts.add(account);
			}
			if(x[4].equals("CD")) {
				CDAccount account = new CDAccount(depositor, y, x[4], z, date);
				account.addTransaction(receipt);
				accounts.add(account);
			}
		}
		
		bank = new Bank(accounts);
		 
		for(int i = 0; i < bank.getNumAccts(); i++) {
			if(Bank.getAcct(bank.getNum().get(i)).getAcctType().equals("CD")){
				int acctNum = Bank.getAcct(bank.getNum().get(i)).getAcctNumber();
				Bank.addCD(acctNum);
			}
			if(Bank.getAcct(bank.getNum().get(i)).getAcctType().equals("Savings")){
				int acctNum = Bank.getAcct(bank.getNum().get(i)).getAcctNumber();
				Bank.addSavings(acctNum);
			}
			if(Bank.getAcct(bank.getNum().get(i)).getAcctType().equals("Checking")){
				int acctNum = Bank.getAcct(bank.getNum().get(i)).getAcctNumber();
				Bank.addCheckings(acctNum);
			}
		}
		 
		randomFile.close();
	}

	/* Method printAccts:
	 * Input:
	 *  bank - reference to the Bank object
	 *  outFile - reference to the output file
	 * Process:
	 *  Prints the database of accounts
	 * Output:
	 *  Prints the database of accounts
	*/
	public static void printAccts(Bank bank, PrintWriter outFile) throws IOException
	{
		 
		outFile.println();
		outFile.println("\t\t\t\t\tDatabase of Bank Accounts");
		String s = String.format("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s","Last","First","SSN","Number","Type","Status","Balance","Maturity date" );
		outFile.println(s);
		outFile.println("---------------------------------------------------------------------------");
		for(int i = 0; i < bank.getNumAccts(); i++) {
			outFile.println(Bank.getAcct(bank.getNum().get(i)).toString());
		}
		outFile.println();
		outFile.printf("%-17s %-17s %-17s %-17s%n", "Total Savings", "Total Checkings", "Total CDs", "Sum of all account");
		outFile.println("----------------------------------------------------------------------");
		outFile.printf("%-17s %-17s %-17s %-17s%n", "$"+Bank.totalSavings(), "$" + Bank.totalCheckings(), "$" + Bank.totalCD(), "$" + Bank.total());
		outFile.println();
		printAcct2(bank, outFile);
		//printAcctsBySet(bank, outFile);
		//printAcctsBySortedSet(bank, outFile);

	}
	
	/* Method closeAcct():
	 * Input:
	 *  bank - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 * Process:
	 *  Prompts for the requested account
	 *  Creates TransactionTicket object
	 *  Calls bank.closeAccount() to execute the transaction
	 *  If the account exists, the account is closed (or stays closed)
	 *  Otherwise, an error message is printed
	 * Output:
	 *  If the account exists, the account is closed (or stays closed)
	 *  Otherwise, an error message is printed
	 */
	public static void closeAcct(Bank bank, PrintWriter outFile, Scanner kybd) throws IOException
	{
		System.out.println("What account to close");
		try {
			boolean t = false;
			int acctNum = kybd.nextInt();
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getAcctNumber() == acctNum) {
					t = true;
				}
			}	
				if(t == false) {
					throw new InvalidAccountException("Account " + acctNum + " not found");
				}
			Account account = Bank.getAcct(acctNum);
			TransactionTicket ticket = new TransactionTicket("Closing Account", 0);
			bank.getQueue().enqueue(ticket);
			account.closeAcct(bank.getQueue().dequeue());
			bank.setAccount(account, acctNum);
			outFile.println();
			outFile.println("Transaction Type: " + ticket.getTransactionType());
			outFile.println("Account Number: " + Bank.getAcct(acctNum).getAcctNumber());
			outFile.println();
		}		catch(InvalidAccountException e) {
			outFile.println(e.getMessage());
			outFile.println();
		}
	}
	
	/* Method ReopenAcct():
	 * Input:
	 *  bank - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 * Process:
	 *  Prompts for the requested account
	 *  Creates TransactionTicket object
	 *  Calls bank.reopenAccount() to execute the transaction
	 *  If the account exists, the account is reopened (or remains open)
	 *  Otherwise, an error message is printed
	 * Output:
	 *  If the account exists, the account is reopened (or remains open)
	 *  Otherwise, an error message is printed
	 */
	public static void reopenAcct(Bank bank, PrintWriter outFile, Scanner kybd) throws IOException
	{
		 
		System.out.println("What account to reopen");
		try {
			boolean t = false;
			int acctNum = kybd.nextInt();
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getAcctNumber() == acctNum) {
					t = true;
				}
			}	
				if(t == false) {
					throw new InvalidAccountException("Account " + acctNum + " not found");
				}
			Account account = Bank.getAcct(acctNum);	
			TransactionTicket ticket = new TransactionTicket("Opening Account", 0);
			bank.getQueue().enqueue(ticket);
			account.reopenAcct(bank.getQueue().dequeue());
			bank.setAccount(account, acctNum);
			outFile.println();
			outFile.println("Transaction Type: " + ticket.getTransactionType());
			outFile.println("Account Number:" + Bank.getAcct(acctNum).getAcctNumber());
			outFile.println();
		}catch(InvalidAccountException e) {
			outFile.println(e.getMessage());
			outFile.println();
		}
		 
	}
	
	/* Method balance:
	 * Input:
	 *  bank - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 * Process:
	 *  Prompts for the requested account
	 *  Creates TransactionTicket object
	 *  Calls bank.getBalance() to execute the transaction
	 *  If the account exists, the balance is printed
	 *  Otherwise, an error message is printed
	 * Output:
	 *  If the account exists, the balance is printed
	 *  Otherwise, an error message is printed
	 */
	public static void balance(Bank bank, PrintWriter outFile, Scanner kybd) throws IOException
	{
		 

		System.out.println("Which account balance to check");
		try {
			boolean t = false;
			int acctNum = kybd.nextInt();
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getAcctNumber() == acctNum) {
					t = true;
				}
			}	
				if(t == false) {
					outFile.println("Transaction Type: Check Bal");
					throw new InvalidAccountException("Account " + acctNum + " not found");
				}
			TransactionTicket ticket = new TransactionTicket("Check Bal");
			bank.getQueue().enqueue(ticket);
			Bank.getAcct(acctNum).getBalance(ticket).getPostTransactionBalance();
			outFile.println();
			outFile.println("Transaction Type: " + ticket.getTransactionType());
			outFile.println("Account Number: " + Bank.getAcct(acctNum).getAcctNumber());
			outFile.println("Current Balance: $" + String.format("%.2f", Bank.getAcct(acctNum).getBalance(bank.getQueue().dequeue()).getPostTransactionBalance()));	
			outFile.println();
		} 
		catch(InvalidAccountException e) {
			outFile.println(e.getMessage());
			outFile.println();
			}
		 
		

	}

	/* Method deposit:
	 * Input:
	 *  bank - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 *  Process:
	 *  Prompts for the requested account
	 *  Prompts for the amount to deposit
	 *  Creates TransactionTicket object
	 *  Calls bank.makeDeposit() to execute the transaction
	 *  If the transaction is valid, it makes the deposit and prints the new balance
	 *  Otherwise, an error message is printed
	 * Output:
	 *  For a valid deposit, the deposit transaction is printed
	 *  Otherwise, an error message is printed
	 */
	public static void deposit(Bank bank, PrintWriter outFile, Scanner kybd) throws IOException
	{
		 

		System.out.println("What account are you depositing to");
		try {
			boolean t = false;
			int acctNum = kybd.nextInt();
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getAcctNumber() == acctNum) {
					t = true;
				}
			}	
				if(t == false) {
					outFile.println("Transaction Type: Deposit");
					throw new InvalidAccountException("Account " + acctNum + " not found");
				}
				
			try {
				Account account = Bank.getAcct(acctNum);
				if(account.getStatus().equals("closed")) {
					TransactionTicket tre = new TransactionTicket("Deposit");
					bank.getQueue().enqueue(tre);
					TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "Account " + acctNum + " is closed");
					red.setTransactionSuccessIndicatorFlag();
					account.addTransaction(red);
					bank.setAccount(account, acctNum);
					outFile.println("Transaction Type: Deposit");
					throw new AccountClosedException("Account " + acctNum + " is closed");
				}
			
			try {
					if(account.getAcctType().equals("CD")) {
						CDAccount acct = (CDAccount) Bank.getAcct(acctNum);
						boolean x = false;
						Calendar todayCalendar = Calendar.getInstance();
						if(acct.getMaturityDate().before(todayCalendar)) {
							x = true;
						}
						if(x == false) {
							TransactionTicket tre = new TransactionTicket("deposit");
							bank.getQueue().enqueue(tre);
							TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "CD maturity date not reached");
							red.setTransactionSuccessIndicatorFlag();
							account.addTransaction(red);
							bank.setAccount(account, acctNum);
							outFile.println("Transaction Type: Deposit");
							outFile.println("Account " + acctNum);
							outFile.println("Maturity Date " + acct.turnDate());
							throw new CDMaturityDateException();
						}
					}
			System.out.println("How much are you depositing");
			double deposit = kybd.nextDouble();
			try {
				if(deposit < 0) {
					TransactionTicket tre = new TransactionTicket("deposit", deposit);
					bank.getQueue().enqueue(tre);
					TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "Invalid deposit");
					red.setTransactionSuccessIndicatorFlag();
					account.addTransaction(red);
					bank.setAccount(account, acctNum);
					outFile.println("Transaction Type: Deposit");
					throw new InvalidAmountException("$" + deposit + " is a negative number. Invalid amount to deposit");
				}
			TransactionTicket tick = new TransactionTicket("Check Bal");
			TransactionTicket ticket = new TransactionTicket("Deposit", deposit);
			bank.getQueue().enqueue(ticket);
			bank.getQueue().enqueue(tick);
			if(account.getAcctType().equals("CD")) {
				System.out.println("How long till the next maturity date?");
				int mat = kybd.nextInt();
				ticket = new TransactionTicket("Deposit", deposit, mat);
				bank.getQueue().dequeue();
				bank.getQueue().dequeue();
				bank.getQueue().enqueue(ticket);
				bank.getQueue().enqueue(tick);
			}
			outFile.println();
			outFile.println("Transaction Type: " + bank.getQueue().dequeue().getTransactionType());
			outFile.println("Account Number " + account.getAcctNumber());
			outFile.println("Current Balance :$" + String.format("%.2f",account.getBalance(bank.getQueue().dequeue()).getPostTransactionBalance()));
			outFile.println("Depositing :$" + String.format("%.2f",deposit));
			if(account.getAcctType().equals("CD")){
				Bank.minusCD(acctNum);
			}
			if(account.getAcctType().equals("Savings")){
				Bank.minusSavings(acctNum);
			}
			if(account.getAcctType().equals("Checking")){
				Bank.minusCheckings(acctNum);
			}
			account.makeDeposit(ticket);
			bank.setAccount(account, acctNum);
			outFile.println("New Balance :$" + String.format("%.2f",Bank.getAcct(acctNum).getBalance(tick).getPostTransactionBalance()));
			outFile.println();
			if(Bank.getAcct(acctNum).getAcctType().equals("CD")){
				Bank.addCD(acctNum);
			}
			if(Bank.getAcct(acctNum).getAcctType().equals("Savings")){
				Bank.addSavings(acctNum);
			}
			if(Bank.getAcct(acctNum).getAcctType().equals("Checking")){
				Bank.addCheckings(acctNum);
			}
			} catch(InvalidAmountException v) {
				outFile.println(v.getMessage());
				outFile.println();
			}
		} catch(CDMaturityDateException g) {
			outFile.println(g.getMessage());
			outFile.println();
		}
		}catch(AccountClosedException l) {
			outFile.println(l.getMessage());
			outFile.println();
		}

	}	catch(InvalidAccountException e) {
		outFile.println(e.getMessage());
		outFile.println();
	}
		 
	}

	
	public static void withdrawal(Bank bank, PrintWriter outFile, Scanner kybd) throws IOException
	{
		 

		System.out.println("What account are you withdrawing from");
		try {
			boolean t = false;
			int acctNum = kybd.nextInt();
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getAcctNumber() == acctNum) {
					t = true;
				}
			}	
				if(t == false) {
					outFile.println("Transaction Type: Withdraw");
					throw new InvalidAccountException("Account " + acctNum + " not found");
				}
				try {
					Account account = Bank.getAcct(acctNum);
					if(account.getStatus().equals("closed")) {
						TransactionTicket tre = new TransactionTicket("withdrawal");
						bank.getQueue().enqueue(tre);
						TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "Account " + acctNum + " is closed");
						red.setTransactionSuccessIndicatorFlag();
						account.addTransaction(red);
						bank.setAccount(account, acctNum);
						outFile.println("Transaction Type: Withdraw");
						throw new AccountClosedException("Account " + acctNum + " is closed");
					}
				
				try {
					TransactionTicket tick = new TransactionTicket("Check Bal");
					bank.getQueue().enqueue(tick);
					boolean d = false;
					System.out.println("How much are you withdrawing");
					double withdraw = kybd.nextDouble();
					if(withdraw > account.getBalance(bank.getQueue().dequeue()).getPostTransactionBalance() || withdraw < 0) {
						d = true;
					}
					if(d == true) {
						TransactionTicket tre = new TransactionTicket("withdrawal", withdraw);
						bank.getQueue().enqueue(tre);
						TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "Invalid withdrawal");
						red.setTransactionSuccessIndicatorFlag();
						account.addTransaction(red);
						bank.setAccount(account, acctNum);
						if(withdraw > account.getBalance(tick).getPostTransactionBalance()) {
							outFile.println("Transaction Type: Withdraw");
							outFile.println("Account " + acctNum);
							outFile.println("Current Balance :$" + String.format("%.2f",account.getBalance(tick).getPostTransactionBalance()));
							throw new InsufficientFundsException(String.format("%.2f", withdraw) + " is greater than the amount in the account. Insufficient Funds Available");
						}
						else {
							outFile.println("Transaction Type: Withdraw");
							throw new InvalidAmountException(String.format("%.2f", withdraw) + " is a negative number. Invalid amount to withdraw");
						}
						
					}
					try {
						if(account.getAcctType().equals("CD")) {
							boolean x = false;
							Calendar todayCalendar = Calendar.getInstance();
							CDAccount acct = new  CDAccount(account);
							if(acct.getMaturityDate().before(todayCalendar)) {
								x = true;
							}
							if(x == false) {
								TransactionTicket tre = new TransactionTicket("deposit");
								bank.getQueue().enqueue(tre);
								TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "CD maturity date not reached");
								red.setTransactionSuccessIndicatorFlag();
								account.addTransaction(red);
								bank.setAccount(account, acctNum);
								outFile.println("Transaction Type: Withdraw");
								outFile.println("Account " + acctNum);
								outFile.println("Maturity Date " + acct.turnDate());
								throw new CDMaturityDateException();
							}
						}
						TransactionTicket ticket = new TransactionTicket("Withdraw", withdraw);
						bank.getQueue().enqueue(ticket);
						if(account.getAcctType().equals("CD")) {
							System.out.println("How long till the next maturity date?");
							int mat = kybd.nextInt();
							bank.getQueue().dequeue();
							ticket = new TransactionTicket("Deposit", withdraw, mat);
							bank.getQueue().enqueue(ticket);
						}
						outFile.println("Transaction Type: " + bank.getQueue().dequeue().getTransactionType());
						outFile.println("Account Number " + account.getAcctNumber());
						outFile.println("Current Balance :$" + String.format("%.2f", account.getBalance(tick).getPostTransactionBalance()));
						outFile.println("Withdrawing :$" + String.format("%.2f",withdraw));
						if(account.getAcctType().equals("CD")){
							Bank.minusCD(acctNum);
						}
						if(account.getAcctType().equals("Savings")){
							Bank.minusSavings(acctNum);
						}
						if(account.getAcctType().equals("Checking")){
							Bank.minusCheckings(acctNum);
						}
						account.makeWithdrawal(ticket);
						bank.setAccount(account, acctNum);
						outFile.println("New Balance :$" + String.format("%.2f", Bank.getAcct(acctNum).getBalance(tick).getPostTransactionBalance()));
						outFile.println();
						if(Bank.getAcct(acctNum).getAcctType().equals("CD")){
							Bank.addCD(acctNum);
						}
						if(Bank.getAcct(acctNum).getAcctType().equals("Savings")){
							Bank.addSavings(acctNum);
						}
						if(Bank.getAcct(acctNum).getAcctType().equals("Checking")){
							Bank.addCheckings(acctNum);
						}
					}catch(CDMaturityDateException g) {
						outFile.println(g.getMessage());
						outFile.println();
					}

				} 
				catch(InsufficientFundsException h) {
					outFile.println(h.getMessage());
					outFile.println();
				}
				catch(InvalidAmountException e) {
					outFile.println(e.getMessage());
					outFile.println();
			
			}
			} catch(AccountClosedException l) {
				outFile.println(l.getMessage());
				outFile.println();
			}

			}
				catch(InvalidAccountException e) {
					outFile.println(e.getMessage());
					outFile.println();
		
				}
		 
	}
			
				

	/* Method clearCheck:
	 * Input:
	 *  bank - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 *  Process:
	 *  Prompts for the requested account
	 *  Prompts for the amount to withdraw
	 *  Creates a Check object
	 *  Creates TransactionTicket object
	 *  Calls bank.clearCheck() to execute the transaction
	 *  If the transaction is valid, it makes the withdrawal and prints the new balance
	 *  Otherwise, an error message is printed
	 * Output:
	 *  For a valid withdrawal, the withdrawal transaction is printed
	 *  Otherwise, an error message is printed
	 */
	public static void clearCheck(Bank bank, PrintWriter outFile, Scanner kybd) throws IOException
	{
		 

		System.out.println("What account are you withdrawing from");
		try {
			boolean t = false;
			int acctNum = kybd.nextInt();
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getAcctNumber() == acctNum) {
					t = true;
				}
			}	
				if(t == false) {
					outFile.println("Transaction Type: Check");
					throw new InvalidAccountException("Account " + acctNum + " not found");
				}
				Account account = Bank.getAcct(acctNum);
			if(Bank.getAcct(acctNum).getAcctType().equals("Savings") || Bank.getAcct(acctNum).getAcctType().equals("CD")) {
				TransactionTicket tre = new TransactionTicket("check");
				bank.getQueue().enqueue(tre);
				TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "Not a Checking account");
				red.setTransactionSuccessIndicatorFlag();
				account.addTransaction(red);
				bank.setAccount(account, acctNum);
				outFile.println("Transaction Type: Check");
				throw new InvalidAccountException( acctNum + " not a Checking account");
			}
			try {
				if(account.getStatus().equals("closed")) {
					TransactionTicket tre = new TransactionTicket("check");
					bank.getQueue().enqueue(tre);
					TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "Account " + acctNum + " is closed");
					red.setTransactionSuccessIndicatorFlag();
					account.addTransaction(red);
					bank.setAccount(account, acctNum);
					outFile.println("Transaction Type: Check");
					throw new AccountClosedException("Account " + acctNum + " is closed");
				}
			
			try {
				System.out.println("How much is the check for");
				double withdrawal = kybd.nextDouble();
				try {
					if(withdrawal < 0) {
						TransactionTicket tre = new TransactionTicket("check", withdrawal);
						bank.getQueue().enqueue(tre);
						TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "Invalid ammount");
						red.setTransactionSuccessIndicatorFlag();
						account.addTransaction(red);
						bank.setAccount(account, acctNum);
						outFile.println("Transaction Type: Check");
						throw new InvalidAmountException(String.format("%.2f", withdrawal) + " is a negative number. Invalid amount to withdraw");
						}
				TransactionTicket tick = new TransactionTicket("Check Bal");
				bank.getQueue().enqueue(tick);
				boolean d = false;
				if(withdrawal > account.getBalance(tick).getPostTransactionBalance()) {
					d = true;
				}
				if(d == true) {
					double m =	account.getBalance(bank.getQueue().dequeue()).getPostTransactionBalance() - 2.50;
					account.setBalance(m);
					Bank.checkBounce();
					TransactionTicket tre = new TransactionTicket("check", withdrawal);
					bank.getQueue().enqueue(tre);
					TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "Insufficient funds - bounce fee applied");
					red.setTransactionSuccessIndicatorFlag();
					account.addTransaction(red);
					bank.setAccount(account, acctNum);
					outFile.println("Transaction Type: Check");
					outFile.println("Account " + acctNum);
					outFile.println("Current Balance :$" + String.format("%.2f",(account.getBalance(tick).getPostTransactionBalance() + 2.50)));
					outFile.println("Check value :$" + String.format("%.2f", withdrawal));
					throw new InsufficientFundsException("Insufficient funds - bounce fee applied");
				}
				System.out.println("What day is the check from");
				try {
					String s = kybd.next();
					Calendar date = convertToCalendar(s);;
					Check check = new Check(acctNum, withdrawal, date);
					CheckingAccount acct =(CheckingAccount) Bank.getAcct(acctNum);
					if(acct.checkTooOld(check)) {
						TransactionTicket tre = new TransactionTicket("check", withdrawal);
						bank.getQueue().enqueue(tre);
						TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "Check too old");
						red.setTransactionSuccessIndicatorFlag();
						account.addTransaction(red);
						bank.setAccount(account, acctNum);
						outFile.println("Transaction Type: Check");
						throw new CheckTooOldException("Check dated " + s + " has passed. Check too old");
					}
					if(acct.postDatedCheck(check)) {
						TransactionTicket tre = new TransactionTicket("check", withdrawal);
						bank.getQueue().enqueue(tre);
						TransactionReceipt red = new TransactionReceipt(bank.getQueue().dequeue(), "post dated check");
						red.setTransactionSuccessIndicatorFlag();
						account.addTransaction(red);
						bank.setAccount(account, acctNum);
						outFile.println("Transaction Type: Check");
						outFile.println("Check date " + s);
						throw new PostDatedCheckException();
					}
					
					TransactionTicket ticket = new TransactionTicket("Clear Check", withdrawal);
					bank.getQueue().enqueue(ticket);
					outFile.println();
					outFile.println("Transaction Type: " + bank.getQueue().dequeue().getTransactionType());
					outFile.println("Account Number :" + account.getAcctNumber());
					outFile.println("Current Balance :$" + String.format("%.2f",account.getBalance(tick).getPostTransactionBalance()));
					outFile.println("Withdrawing :$" + String.format("%.2f",withdrawal));
					if(account.getAcctType().equals("CD")){
						Bank.minusCD(acctNum);
					}
					if(account.getAcctType().equals("Savings")){
						Bank.minusSavings(acctNum);
					}
					if(account.getAcctType().equals("Checking")){
						Bank.minusCheckings(acctNum);
					}
					CheckingAccount accnt = new CheckingAccount(account);
					TransactionTicket td = new TransactionTicket("Clear Check", check);
					accnt.clearCheck(td);
					bank.setAccount(accnt, acctNum);
					outFile.println("New Balance :$" + String.format("%.2f",Bank.getAcct(acctNum).getBalance(tick).getPostTransactionBalance()));
					outFile.println();
					if(Bank.getAcct(acctNum).getAcctType().equals("CD")){
						Bank.addCD(acctNum);
					}
					if(Bank.getAcct(acctNum).getAcctType().equals("Savings")){
						Bank.addSavings(acctNum);
					}
					if(Bank.getAcct(acctNum).getAcctType().equals("Checking")){
						Bank.addCheckings(acctNum);
					}
				} 
				catch(PostDatedCheckException k) {
					outFile.println(k.getMessage());
					outFile.println();
				}
				catch(CheckTooOldException f) {
					outFile.println(f.getMessage());
					outFile.println();

				}
			}catch(InvalidAmountException e) {
				outFile.println(e.getMessage());
				outFile.println();
				
		}
			}catch(InsufficientFundsException h) {
				outFile.println(h.getMessage());
				outFile.println();
				
				}
			}catch(AccountClosedException l) {
				outFile.println(l.getMessage());
				outFile.println();
			}
		}
		catch(InvalidAccountException e) {
			outFile.println(e.getMessage());
			outFile.println();
			
			}
		 

		
	}

	/* Method newAcct:
	 * Input:
	 *  bank - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 *  Process:
	 *  Prompts for the new account info
	 *  Calls bank.openNewAccount() to execute the transaction
	 *  If the transaction is valid, a confirmation message is printed  
	 *  Otherwise, an error message is printed
	 * Output:
	 *  For a valid transaction, a confirmation message is printed  
	 *  Otherwise, an error message is printed
	 */
	public static void newAcct(Bank bank, PrintWriter outFile, Scanner kybd) throws IOException
	{
		 
		System.out.println("What is the account number");
		try {
			TransactionTicket tick = new TransactionTicket("Check Bal");
			boolean t = false;
			int acctNum = kybd.nextInt();
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getAcctNumber() == acctNum) {
					t = true;
			
				}
				if(t == true) {
					outFile.println("Transaction Type: Open New Account");
					throw new InvalidAccountException("Account " + acctNum + " already exist");
				}
			}
			System.out.println("What is the depositor first name");
			String first = kybd.next();
			System.out.println("What is the depositor last name");
			String last = kybd.next();
			Name name = new Name(last, first);
			System.out.println("What is the SSN");
			String ssn = kybd.next();
			Depositor depositor = new Depositor(name, ssn);
			System.out.println("What is the account type");
			String type = kybd.next();
			System.out.println("What is the initial deposit");
			double deposit = kybd.nextDouble();;
			outFile.println();
			outFile.println("Transaction Type: New Account" );
			if(type.equals("CD")) {
				
				System.out.println("What is the maturity date");
				String s = kybd.next();
				Calendar date = convertToCalendar(s);
				CDAccount account = new CDAccount(depositor, acctNum, type, deposit, date);
				TransactionTicket ticket = new TransactionTicket("New Account", deposit, account);
				bank.getQueue().enqueue(ticket);
				bank.openNewAcct(bank.getQueue().dequeue());
				outFile.println("Account Number :" + Bank.getAcct(acctNum).getAcctNumber());
				outFile.println("New Balance :$" + String.format("%.2f",Bank.getAcct(acctNum).getBalance(tick).getPostTransactionBalance()));
				Bank.addCD(acctNum);
			}
			else if(type.equals("Savings")) {
				SavingAccount account = new SavingAccount(depositor, acctNum, type, deposit);
				TransactionTicket ticket = new TransactionTicket("New Account", deposit, account);
				bank.getQueue().enqueue(ticket);
				bank.openNewAcct(bank.getQueue().dequeue());
				outFile.println("Account Number :" + Bank.getAcct(acctNum).getAcctNumber());
				outFile.println("New Balance :$" + String.format("%.2f",Bank.getAcct(acctNum).getBalance(tick).getPostTransactionBalance()));
				Bank.addSavings(acctNum);
			}
			else if(type.equals("Checking")){
				CheckingAccount account = new CheckingAccount(depositor, acctNum, type, deposit);
				TransactionTicket ticket = new TransactionTicket("New Account", deposit, account);
				bank.getQueue().enqueue(ticket);
				bank.openNewAcct(bank.getQueue().dequeue());
				outFile.println("Account Number :" + Bank.getAcct(acctNum).getAcctNumber());
				outFile.println("New Balance :$" + String.format("%.2f",Bank.getAcct(acctNum).getBalance(tick).getPostTransactionBalance()));
				Bank.addCheckings(acctNum);
			}

		}
		catch(InvalidAccountException e) {
			outFile.println(e.getMessage());
			outFile.println();
			
			}
		 
	}

	/* Method deleteAcct:
	 * Input:
	 *  bank - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 *  Process:
	 *  Prompts for the requested account
	 *  Creates TransactionTicket object
	 *  Calls bank.deleteAccount() to execute the transaction
	 *  If the transaction is valid, a confirmation message is printed  
	 *  Otherwise, an error message is printed
	 * Output:
	 *  For a valid transaction, the transaction result is printed
	 *  Otherwise, an error message is printed
	 */
	public static void deleteAcct(Bank bank, PrintWriter outFile, Scanner kybd) throws IOException
	{
		 
		System.out.println("What account to delete");
		try {
			TransactionTicket tick = new TransactionTicket("Check Bal");
			boolean t = false;
			int acctNum = kybd.nextInt();
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getAcctNumber() == acctNum && Bank.getAcct(acctNum).getBalance(tick).getPostTransactionBalance() == 0.0) {
					t = true;
				}
			}	
				if(t == false) {
					outFile.println("Transaction Type: Deleting Account");
					throw new InvalidAccountException("Account " + acctNum + " not found");
				}
			try	{
				boolean k = false;
				for(int i = 0; i < bank.getNumAccts(); i++) {
					if(Bank.getAcct(acctNum).getBalance(tick).getPostTransactionBalance() == 0.0) {
						k = true;
					}
				}	
					if(k == false) {
						outFile.println("Transaction Type: Deleting Account");
						outFile.println("Account " + acctNum);
						throw new InvalidAmountException("Account cannot be deleted because balance is not 0.00");
					}
			TransactionTicket ticket = new TransactionTicket("Delete Account");
			bank.getQueue().enqueue(ticket);
			outFile.println();
			outFile.println("Transaction Type: " + ticket.getTransactionType());
			outFile.println("Account Number :" + Bank.getAcct(acctNum).getAcctNumber());
			bank.deleteAcct(bank.getQueue().dequeue(), Bank.getAcct(acctNum));
			outFile.println("Account deleted");
			outFile.println();
		}
		catch(InvalidAmountException e) {
			outFile.println(e.getMessage());
			outFile.println();
			
			}
		}catch(InvalidAccountException f) {
			outFile.println(f.getMessage());
			outFile.println();
			
			}
		 
	}

	/* Method acctInfo:
	 * Input:
	 *  bank - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	 *  Process:
	 *  Prompts for the requested SSN
	 *  searches the account database for matching accounts
	 *  and prints the account info for each matching account
	 *  If there are no matching accounts, an error message is printed
	 * Output:
	 *  For a valid transaction, the transaction result is printed
	 *  Otherwise, an error message is printed
	 */
	public static void acctInfo(Bank bank, PrintWriter outFile, Scanner kybd) throws IOException
	{
		 
		System.out.println("What is your ssn");
		try {
			boolean t = false;
			String ssn = kybd.next();
			TransactionTicket ticket = new TransactionTicket("Account Info", ssn);
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getDepositor().getSSN().equals(ticket.getSSN())) {
					t = true;
				}
			}	
				if(t == false) {
					outFile.println("Transaction Type: Account Info");
					throw new InvalidAccountException("Serial # " + ticket.getSSN() + " not found");
				}
				outFile.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s%n","First","Last","SSN","Account #","Type","Status","Balance","Maturity Date");
				outFile.println("---------------------------------------------------------------------------");			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getDepositor().getSSN().equals(ticket.getSSN())) {
					outFile.println( Bank.getAcct(bank.getNum().get(i)).toString());
				}
			}
			outFile.println();


		}		catch(InvalidAccountException e) {
			outFile.println(e.getMessage());
			outFile.println();
		}
		 
	}
	
	/* Method acctInfoWithTransactionHistory:
	 * Input:
	 *  bank - reference to the Bank object
	 *  outFile - reference to the output file
	 *  kybd - reference to the "test cases" input file
	* Process:d
	 *  Prompts for the requested SSN
	 *  searches the account database for matching accounts
	 *  and prints the account info for each matching account
	 *  If there are no matching accounts, an error message is printed
	 * Output:
	 *  For a valid transaction, the transaction result is printed
	 *  Otherwise, an error message is printed
	 */
	public static void acctInfoWithTransactionHistory(Bank bank, PrintWriter outFile, Scanner kybd) 
													throws IOException
	{
		 
		System.out.println("What is your ssn");
		try {
			boolean t = false;
			String ssn = kybd.next();
			TransactionTicket ticket = new TransactionTicket("Account Info", ssn);
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getDepositor().getSSN().equals(ticket.getSSN())) {
					t = true;
				}
			}	
				if(t == false) {
					outFile.println("Transaction Type: Account Info with History");
					throw new InvalidAccountException("Serial # " + ticket.getSSN() + " not found");
				}
			for(int i = 0; i < bank.getNumAccts(); i++) {
				if(Bank.getAcct(bank.getNum().get(i)).getDepositor().getSSN().equals(ticket.getSSN())) {
					outFile.println(Bank.getAcct(bank.getNum().get(i)).toString());
					outFile.println();
					outFile.println("******Account Transactions******\n");
					String str = String.format("%-11s %-11s %-11s %-11s %-11s %-11s %-11s", "Date", "Type", "Status", "Amount", "pre-balance", "post-balance", "Reason");
					outFile.println(str);
					outFile.println();
					for(int j = 0; j < Bank.getAcct(bank.getNum().get(i)).getTransactionHistory().size(); j++) {
						outFile.println(Bank.getAcct(bank.getNum().get(i)).getTransactionHistory().get(j).toString());
					} outFile.println();
				}
			}
		}catch(InvalidAccountException e) {
			outFile.println(e.getMessage());
			outFile.println();
		}
		 
	
}
	public static void printAcct2(Bank bank, PrintWriter outFile) {
		outFile.println("\t\t\t\t\tDatabase of Bank Accounts sorted by Account # with auxiliary heap ");
		String s = String.format("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s","Last","First","SSN","Number","Type","Status","Balance","Maturity date" );
		outFile.println(s);
		outFile.println("---------------------------------------------------------------------------");
		//outFile.println(bank.removeMin().toString());
		bank.sortHeap(bank.getHeap());
		for(int i = 0; i < bank.getNumAccts(); i++) {
			outFile.println(bank.removeMin(bank.getHeap()).toString());
		}
		bank.sortHeap(bank.getAccount(),new AccountComparatorBySSN());
		outFile.println();
		outFile.println("\t\t\t\t\tDatabase of Bank Accounts sorted by SSN ");
		outFile.println(s);
		outFile.println("---------------------------------------------------------------------------");
		for(int i = 0;i<bank.getNumAccts();i++) {
			outFile.println(bank.removeMin(bank.getAccount(), new AccountComparatorBySSN()).toString());
		}
		bank.sortHeap(bank.getAccount(),new AccountComparatorByName());
		outFile.println();
		outFile.println("\t\t\t\t\tDatabase of Bank Accounts sorted by Name ");
		outFile.println(s);
		outFile.println("---------------------------------------------------------------------------");
		for(int i = 0;i<bank.getNumAccts();i++) {
			outFile.println(bank.removeMin(bank.getAccount(), new AccountComparatorByName()).toString());
		}
		bank.sortHeap(bank.getAccount(),new AccountComparatorByBalance());
		outFile.println();
		outFile.println("\t\t\t\t\tDatabase of Bank Accounts sorted by Balance ");
		outFile.println(s);
		outFile.println("---------------------------------------------------------------------------");
		for(int i = 0;i<bank.getNumAccts();i++) {
			outFile.println(bank.removeMin(bank.getAccount(), new AccountComparatorByBalance()).toString());
		}
		outFile.println();

	}
	public static void printAcct3(Bank bank, PrintWriter outFile) {
		outFile.println("\t\t\t\t\tDatabase of Bank Accounts sorted by Account # with primary heap");
		String s = String.format("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s","Last","First","SSN","Number","Type","Status","Balance","Maturity date" );
		outFile.println(s);
		outFile.println("---------------------------------------------------------------------------");
		//outFile.println(bank.removeMin().toString());
		bank.sortHeap(bank.getAccount());
		for(int i = 0; i < bank.getNumAccts(); i++) {
			outFile.println(bank.removeMin(bank.getAccount()).toString());
		}	
		outFile.println();
	}
	
	public static void testContains(Bank bank, PrintWriter outFile) {
		Name me = new Name("Rodler", "Jean");
		Depositor rodler = new Depositor(me, "000000000");
		Set<Account> accountSet = new HashSet<>(bank.createHashSet());
		Account a = bank.getAcct2(0);
		Account b = bank.getAcct2(4);
		Account c = bank.getAcct2(6);
		Account d = new Account(rodler, 777888, "Savings", 35.89);
		ArrayList<Account> test = new ArrayList<>();
		test.add(a); test.add(b); test.add(c); test.add(d);
		for(Account account : test) {
			if(accountSet.contains(account)) {
				outFile.println("Account #" + account.getAcctNumber() + " was found in the set");
				outFile.println("Account hashcode is "+account.hashCode());
				outFile.println();
			}
			else {
				outFile.println("Account #" + account.getAcctNumber() + " was not found");
			}
		}
		outFile.println();
		
	}
	public static void pause(Scanner keyboard)
	{
		String tempstr;
		tempstr = keyboard.nextLine();		
	}
	public static Calendar convertToCalendar(String date){
		String[] dates = date.split("/");
		Calendar d = Calendar.getInstance();
		int month = Integer.parseInt(dates[0]);
		int day = Integer.parseInt(dates[1]);
		int year = Integer.parseInt(dates[2]);
		d.set(year, month, day);
		return d;
	}

}