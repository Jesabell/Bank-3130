# Bank-3130
Bank project for data structures 
You have been hired as a programmer by a major bank. Your first project is a small banking transaction system. The user of the
program (the teller) can create a new account, as well as perform deposits, withdrawals, balance inquiries, close accounts, etc.
For this assignment, you must have at least the following classes:
1. A Bank class which consists of an ArrayList of Accounts currently active or closed.
In addition, the Bank class has several static member variables and methods:
totalAmountInSavingsAccts - sum total of balances in all Savings accounts
totalAmountInCheckingAccts - sum total of balances in all Checking accounts
totalAmountInCDAccts - total - sum total of balances in all CD accounts
totalAmountInAllAccts - total - sum total of balances in all accounts
Make sure to provide appropriate methods so as to allow fo r the addition to, s ubtraction from, and reading of, the
current values of each of these static variables.
Be sure to print the values of all of these static variables when you print the database of accounts.
The Bank class does not override either the toString() or the equals() method.
*2. An Account class which consists of a Depositor, an account number, an account type, account status (open or closed),
account b alance, a n ArrayList of T ransactionReceipts performed on the account. (Note: c reating a n account i s
considered a transaction.)
The Account class must have a copy constructor and override both the toString() and the equals() methods.
The Account class has several subclasses:
2a.The SavingsAccount class is a subclass of the Account class.
For Saving Accounts, deposits and withdrawals are allowed at any time.
The SavingsAccount class must have a copy constructor and overload both the toString() and the equals() methods.
2b.The CheckingAccount class is a subclass of the Account class.
For Checking Accounts, de posits, withdrawals, and c heck clearing are allowed at any time. Remember, you may only
clear a check if the date on the check is no m ore than s ix months ago. No post-dated checks (checks with a f uture date)
may be cleared. Use the Calendar class to implement this. In addition, a check will clear only if there is sufficient funds
in t he account. I f the account l acks sufficient funds, the check will not clear a nd the account will be charged a $2.50
Service Fee for “ bouncing” a check. I n a ddition to the previous rules, i f the current balance of the account i s below
$2500, each withdrawal or cleared check is charged a fee of $1.50.
The CheckingAccount class must have a copy constructor and override both the toString() and the equals() methods.
2c.The CDAccount class is a subclass of the Account class.
The class has a data member: a maturityDate which is a Calendar class object.
As before, deposits and withdrawals will be allowed only on or after the maturity date. When a deposit or withdrawal is
made, have the user select a new maturity date for the CD. The choices are either 6, 12, 18, or 24 months from the date of
the deposit or withdrawal. Again, use the Calendar class to implement this.
The CDAccount class must have a copy constructor and override both the toString() and the equals() methods.
3. A Depositor class which has a Name and a social security number.
The Depositor class must have a copy constructor and override both the toString() and the equals() methods.
4. A Name class which consists of first and last names.
The Name class must have a copy constructor and override both the toString() and the equals() methods.
5. A Check class with data fields consisting of an account number, the check amount, and a dateOfCheck
The Check class must have a copy constructor and override the toString() method.
6. A TransactionTicket class with data fiel ds co nsisting of a dateOfTransaction, typeOfTransaction (deposit, withdrawal,
balance inquiry, n ew ac count, delete account, etc.), account n umber, a mountOfTransaction (for deposits and
withdrawals), termOfCD (6, 12, 18, or 24 months - see below).
The TransactionTicket class must have a copy constructor and override the toString() method.
7. A TransactionReceipt class with data fields co nsisting of a Tr ansactionTicket, su ccessIndicatorFlag, rea sonForFailure
String, preTransactionBalance, postTransactionBalance, postTransactionMaturityDate (for CDs).
The TransactionReceipt class must have a copy constructor and override the toString() method.
You must implement appropriate methods in each class so as to implement the functionality required.
The data members of each class must be private (or protected when providing subclass access).
Provide accessor and mutator methods as necessary.
An Account object should access subclass methods using polymorphism.
Remember, all I/O should be done only in the methods of the class that contains the main() method.
