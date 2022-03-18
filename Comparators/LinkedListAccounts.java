import java.util.ArrayList;

public class LinkedListAccounts {
	private ArrayList<Integer> i = new ArrayList<>();
	private ArrayList<String> s = new ArrayList<>();
	private ArrayList<Name> n = new ArrayList<>();
	private ArrayList<Double> d = new ArrayList<>();
	private Node last;
	private Node first;
	
	private class Node {
		private Account account;
		private Node next;
		
		public Node(Account acct) {
			this.account = acct;
		}
		
		public Node(Account acct, Node n) {
			this.account = acct;
			this.next = n;
		}
		
	}
	
	public LinkedListAccounts() {
		last = null;
		first = null;
	}
	public boolean isEmpty() {
		return first == null;
	}
	public int size() {
		int count = 0;
		 Node p = first;
		 while (p != null)
		 {
		 count++;
		 p = p.next;
		 }
		 return count;
	}
	public void add(Account acct)
	{
		 if (isEmpty()){
			 first = new Node(acct);
			 last = first;
		 }
		 else{
			 last.next = new Node(acct);
			 last = last.next;
		 } 
	}
	private <E extends Comparable<E>> void addSortedGeneric(Account acct, E e2, ArrayList<E> e) {
	if(isEmpty()) {
		first = new Node(acct);
   		last = first;
   		e.add(e2);
   		 return;
	}
	if (e2.compareTo(e.get(0)) < 0) {
   		 first = new Node(acct, first);
   		 E hold = e.get(0);
   		 if(e.size() == 1) {
   			 e.set(0, e2);
   			 e.add(hold);
   			 return;
   		 }
   		 else {
   			 e.set(0, e2);
   	   		 e.add(1, hold);;
   	   		 return;
   		 }
   	 }
		int i = 1;
		Node pred = first;
		boolean a = false;
		while(i < e.size() && pred != null && a == false) {
			if(e2.compareTo(e.get(i)) < 0) {
				 pred.next = new Node(acct, pred.next);
				 E hold = e.get(i);
		   		 e.set(i, e2);
		   		 e.add(i+1, hold);;
				 a = true;
		    	 if (pred.next.next == null)
		    		 last = pred.next;
		    	 return;
			}
			i++;
			pred = pred.next;
		}
		 last.next = new Node(acct);
		 last = last.next;
		e.add(e2);
	}
	public void addSortedByAcctNum(Account acct) {
		addSortedGeneric(acct, acct.getAcctNumber(), i);
	}
	public void addSortedBySSN(Account acct) {
		addSortedGeneric(acct, acct.getDepositor().getSSN(), s);
	}
	public void addSortedByName(Account acct) {
		addSortedGeneric(acct, acct.getDepositor().getName(), n);	
	}
	public void addSortedByBalance(Account acct) {
		TransactionTicket ticket = new TransactionTicket("Checking balance");
		addSortedGeneric(acct, acct.getBalance(ticket).getPostTransactionBalance(), d);	
	}
    public void add(int index, Account acct) {
    	if (index < 0 || index > size())
    	 {
    		String message = String.valueOf(index);
    			throw new IndexOutOfBoundsException(message);
    	 }

    	 if (index == 0)
    	 {
    		 first = new Node(acct, first);
    		 if (last == null)
    			last = first;
    		 	return;
    	 }
    	 Node pred = first;
    	 for (int k = 1; k <= index-1; k++)
    	 {
    		 pred = pred.next;
    	 }
    	 pred.next = new Node(acct, pred.next);
    	 if (pred.next.next == null)
    		 last = pred.next;
    }
    public Account get(int index) {
    	if (index < 0 || index >= size())
   	 {
   	 String message = String.valueOf(index);
   	 throw new IndexOutOfBoundsException(message);
   	 }
    	if(index == 0) {
    		return first.account;
    	}
    	Node pred = first;
    	for (int k = 1; k <= index-1; k++) {
   		 pred = pred.next;
    	}
    	return pred.next.account;	
    }
    public void set(int index, Account acct) {
    	if (index < 0 || index >= size()){
    		String message = String.valueOf(index);
    		throw new IndexOutOfBoundsException(message);
   	 	}
    	Node pred = new Node(acct);
    	if (index == 0){
    		first = pred;
    		if (first == null)
    			last = null;
    	}
    	else {
    		Node prede = first;
    		for (int k = 1; k <= index-1; k++) {
    			prede = prede.next;
    		}	
    		prede.next = pred;
    		if (prede.next == null)
    			last = prede;
    	}    	
    }
    public void setSortedByAcctNumber (Account acct) {
    	if(first.account.getAcctNumber() == acct.getAcctNumber()) {
    		set(0, acct);
    	}
    	Node pred = first;
    	int i = 1;
    	boolean x = true;
    	while(pred != null && x == true) {
    		if(pred.next.account.getAcctNumber() == acct.getAcctNumber()) {
    			set(i, acct);
    			x = false;
    		}
    		pred = pred.next;
    		i++;
    	}
    	
    }
    public Account remove(int index) {
    	if (index < 0 || index >= size()) {
    		String message = String.valueOf(index);
    		throw new IndexOutOfBoundsException(message);
    	 }

    	 Account element; 
    	 if (index == 0){
    		 element = first.account;
    		 first = first.next;
    	 if (first == null)
    		 last = null;
    	 }
    	 else{
    		 Node pred = first;
    		 for (int k = 1; k <= index-1; k++) {
    		 pred = pred.next;
    		 }	
    		 element = pred.next.account;
    		 pred.next = pred.next.next;
    		 if (pred.next == null)
    			 last = pred;
    	 }
    	 return element; 
    }
    public void removeSorted(Account acct) {
    	if(isEmpty()) {
    		return;
    	}
    	if(first.account.getAcctNumber() == acct.getAcctNumber()) {
    		remove(0);
    		return;
    	}
    	Node pred = first;
    	int i = 1;
    	while(pred.next != null) {
    		if(pred.next.account.getAcctNumber() == acct.getAcctNumber()) {
    			remove(i);
    			return;
    		}
    		pred = pred.next;
    		i++;
    		if(pred == null) {
    			pred = last;
    		}
    	}
    }
    public boolean remove(Account acct) {
    	if(isEmpty()) {
    		return false;
    	}
    	
    	if(first.account.getAcctNumber() == acct.getAcctNumber()) {
    		first = first.next;
    		if (first == null) {
    			last = null;
    		}
    		return true;
    	}
    	else {
        	Node pred = first;
        	while(pred.next != null) {
        		if(pred.next.account.getAcctNumber() == acct.getAcctNumber()) {
        			pred.next = pred.next.next;
        			return true;
        		}
        		pred = pred.next;
        	}
    	}
    		return false;
    }
    public void clear() {
    	while(!isEmpty()) {
    		remove(0);
    	}
    }
    
}