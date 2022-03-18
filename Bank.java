import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Bank {
	public class GenQueue<T> {
		private class Node
		 {
			T value;
			Node next;
			
			Node(T val, Node n)
			{
				value = val;
				next = n;
			}
		 }
		private Node front = null;
		private Node rear = null; 
		
		public boolean empty(){
			return front == null;
		}
		public void enqueue(T s){
			if (!empty()){
				rear.next = new Node(s, null);
				rear = rear.next;
			}
			else{
				front = rear = new Node(s, null);
			}
		}
		public T dequeue(){
			if (empty())
				return null;
			else{
				T value = front.value;
				front = front.next;
				if (front == null) rear = null; //test if queue is empty
				return value;
			}
		 }
		 public T peek(){
			 if (empty())
				 return null;
			 else
				 return front.value;
		 }
	}
	private ArrayHeap account = new ArrayHeap();
	private ArrayHeap heap = new ArrayHeap();
	private static List<Account> accounts; 
	private GenQueue<TransactionTicket> tickets = new GenQueue<>();
	private static double totalAmountInSavingsAccts, totalAmountInCheckingAccts, totalAmountInCDAccts, totalAmountInAllAccts;
	
	public Bank() {
		accounts = new ArrayList<>();
		totalAmountInSavingsAccts = 0.0;
		totalAmountInCheckingAccts = 0.0;
		totalAmountInCDAccts = 0.0;
		totalAmountInAllAccts = 0.0;
	}

	public Bank(ArrayList<Account> myaccounts) {
		Bank.accounts = myaccounts;
	}
	public static double totalSavings() {

		return Math.round(totalAmountInSavingsAccts * 100)/100.0;
		
	}
	
	public void sortHeap(ArrayHeap x) {
		for(int i = 0; i< accounts.size(); i++) {
			x.add(accounts.get(i));
		}
	}
	public Account removeMin(ArrayHeap x) {
		return x.removeMin();
	}
	public void sortHeap(ArrayHeap x, Comparator<Account> comp) {
		for(int i = 0; i< accounts.size(); i++) {
		x.add(accounts.get(i), comp);
		}
	}
	public Account removeMin(ArrayHeap x,Comparator<Account> comp) {
		return x.removeMin(comp);
	}
	public ArrayHeap getAccount() {
		return account;
	}
	public ArrayHeap getHeap() {
		return heap;
	}
	public GenQueue<TransactionTicket> getQueue() {
		return tickets;
	}
	public static void minusSavings(int acctNum){
		TransactionTicket tick = new TransactionTicket("Checking balance");
		totalAmountInSavingsAccts -= getAcct(acctNum).getBalance(tick).getPostTransactionBalance();
	}
	
	public static void addSavings(int acctNum){
		TransactionTicket tick = new TransactionTicket("Checking balance");
		totalAmountInSavingsAccts += getAcct(acctNum).getBalance(tick).getPostTransactionBalance();
	}
	public static void checkBounce() {
		totalAmountInCheckingAccts = totalAmountInCheckingAccts - 2.50;
	}
	public static double totalCheckings() {

		return Math.round(totalAmountInCheckingAccts * 100)/100.0;
		
	}
	public static void minusCheckings(int acctNum){
		TransactionTicket tick = new TransactionTicket("Checking balance");
		totalAmountInCheckingAccts -= getAcct(acctNum).getBalance(tick).getPostTransactionBalance();
	}
	
	public static void addCheckings(int acctNum){
		TransactionTicket tick = new TransactionTicket("Checking balance");
		totalAmountInCheckingAccts += getAcct(acctNum).getBalance(tick).getPostTransactionBalance();
	}
	
	public static double totalCD() {

		return Math.round(totalAmountInCDAccts * 100)/100.0;
		
	}
	public static void minusCD(int acctNum){
		TransactionTicket tick = new TransactionTicket("Checking balance");
		totalAmountInCDAccts -= getAcct(acctNum).getBalance(tick).getPostTransactionBalance();
	}
	
	public static void addCD(int acctNum){
		TransactionTicket tick = new TransactionTicket("Checking balance");
		totalAmountInCDAccts += getAcct(acctNum).getBalance(tick).getPostTransactionBalance();
	}
	
	
	public static double total() {
		totalAmountInAllAccts = totalCD() + totalSavings() + totalCheckings();
		return Math.round(totalAmountInAllAccts * 100)/100.0;
	}

	public void setAccount(Account a, int acctNum) {
		for(int i = 0; i< getNumAccts(); i++) {
			if(accounts.get(i).getAcctNumber() == acctNum) {
				accounts.set(i, a);
			}
	}
	}
 ArrayList<Integer> getNum(){
		ArrayList<Integer> num1 = new ArrayList<>();
		for(int i = 0; i<accounts.size();i++) {
			num1.add(accounts.get(i).getAcctNumber());
		}
		return num1;
	}
	public TransactionReceipt openNewAcct(TransactionTicket ticket) {
		Account a = ticket.getAccount();
		TransactionReceipt receipt = new TransactionReceipt(ticket, 0, ticket.getTransactionAmount());
		a.addTransaction(receipt);
		accounts.add(a);	
		return receipt;
	}
	
	public TransactionReceipt deleteAcct(TransactionTicket ticket, Account account) {
		for(int i = 0; i<accounts.size(); i++) {
			if(accounts.get(i).getAcctNumber() == account.getAcctNumber()) {
				accounts.remove(accounts.get(i));
			}
		}
		TransactionReceipt receipt = new TransactionReceipt(ticket, 0, ticket.getTransactionAmount());
		return receipt;
	}
		public void sortAccountList(Comparator<Account> comp) {
		accounts.sort(comp);
	}
	public Set<Account> createHashSet() {
		Set<Account> accountSet = new HashSet<>(accounts);
		return accountSet;
	}
	public Set<Account> createLinkedHashSet() {
		Set<Account> accountSet = new LinkedHashSet<>(accounts);
		return accountSet;
	}
	public Iterator<Account> gestHashSetIterator(Set<Account> accountSet) {
		Iterator<Account> acctIterator = accountSet.iterator();
		return acctIterator;
	}
	public SortedSet<Account> createTreeSet() {
		Set<Account> acctSet = new LinkedHashSet<>(accounts);
		Iterator<Account> acctIterator = acctSet.iterator();
		SortedSet<Account> acctSortedSet = new TreeSet<>();
		while(acctIterator.hasNext()) {
			acctSortedSet.add(acctIterator.next());
		}
		return acctSortedSet;
	}
	public SortedSet<Account> createTreeSet(Comparator<Account> comp) {
		Set<Account> acctSet = new LinkedHashSet<>(accounts);
		Iterator<Account> acctIterator =acctSet.iterator();
		SortedSet<Account> acctSortedSet = new TreeSet<>(comp);
		while(acctIterator.hasNext()) {
			acctSortedSet.add(acctIterator.next());
		}
		return acctSortedSet;
	}

	public Account getAcct2(int number) {
		return accounts.get(number);
	}
	public static Account getAcct(int number) {
		for(int i = 0; i<accounts.size(); i++) {
			if(accounts.get(i).getAcctNumber() == number && accounts.get(i).getAcctType().equals("CD")) {
				CDAccount account = (CDAccount) accounts.get(i); 
				return account;
			}
			else if(accounts.get(i).getAcctNumber() == number && accounts.get(i).getAcctType().equals("Checking")) {
				CheckingAccount account = (CheckingAccount) accounts.get(i); 
				return account;
			}
			else if(accounts.get(i).getAcctNumber() == number && accounts.get(i).getAcctType().equals("Savings")) {
				SavingAccount account = (SavingAccount) accounts.get(i); 
				return account;
		}
		}
		return null;
	}
	public int getNumAccts() {
		return accounts.size();
	}


}
