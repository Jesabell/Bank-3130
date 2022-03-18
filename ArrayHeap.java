import java.util.ArrayList;
import java.util.Comparator;

public class ArrayHeap {
	private ArrayList<Account> arrayHeap = new ArrayList<>();
	
	 private void siftUp() {
		 int p = arrayHeap.size()-1; // Position to sift up
		 while (p != 0)
		 {
		 int parent = (p-1) / 2;
		 if (valueAt(p).compareTo(valueAt(parent)) >= 0)
		 {
			 // We are done
			 break;
		 }
		 else
		 {
			 // Do a swap
			 Account temp = arrayHeap.get(parent);
			 arrayHeap.set(parent, arrayHeap.get(p));
			 arrayHeap.set(p, temp);

		 // Move up
			 p = parent;
		 	}
		 }

	 }
	 private void siftUp(Comparator<Account> comp) {
		 int p = arrayHeap.size()-1; // Position to sift up
		 while (p != 0)
		 {
		 int parent = (p-1) / 2;
		 if (comp.compare(valueAt(p), valueAt(parent)) > 0)
		 {
			 // We are done
			 break;
		 }
		 else
		 {
			 // Do a swap
			 Account temp = arrayHeap.get(parent);
			 arrayHeap.set(parent, arrayHeap.get(p));
			 arrayHeap.set(p, temp);

		 // Move up
			 p = parent;
		 	}
		 }
 
	 }
	 private void siftDown(){
		 int p = 0; // Position to sift down
		 int size = arrayHeap.size();
		 while (2*p + 1 < size)
		 {
			 int leftChildPos = 2*p + 1;
			 int rightChildPos = leftChildPos + 1;
			 int minChildPos = leftChildPos;

		 // Is there a right child?
		 if (rightChildPos < size)
		 {
			 // Which child is smaller
			 if (valueAt(rightChildPos).compareTo(valueAt(leftChildPos)) < 0)
			 {
				 minChildPos = rightChildPos;
			 }
		 }
		 // If less than children we are done,
		// otherwise swap node with smaller child
		 if (valueAt(p).compareTo(valueAt(minChildPos)) <= 0)
			 break;
		 else
		 {
		 // Do the swap
			 Account temp = arrayHeap.get(p);
			 arrayHeap.set(p, arrayHeap.get(minChildPos));
			 arrayHeap.set(minChildPos, temp);
		 }
		 // Go down to the child position
		 p = minChildPos;
		 }

	 }
	 private void siftDown(Comparator<Account> comp) {
		 int p = 0; // Position to sift down
		 int size = arrayHeap.size();
		 while (2*p + 1 < size)
		 {
			 int leftChildPos = 2*p + 1;
			 int rightChildPos = leftChildPos + 1;
			 int minChildPos = leftChildPos;

		 // Is there a right child?
		 if (rightChildPos < size)
		 {
			 // Which child is smaller
			 if (comp.compare(valueAt(rightChildPos), valueAt(leftChildPos)) < 0)
			 {
				 minChildPos = rightChildPos;
			 }
		 }
		 // If less than children we are done,
		// otherwise swap node with smaller child
		 if (comp.compare(valueAt(p),valueAt(minChildPos)) <= 0)
			 break;
		 else
		 {
		 // Do the swap
			 Account temp = arrayHeap.get(p);
			 arrayHeap.set(p, arrayHeap.get(minChildPos));
			 arrayHeap.set(minChildPos, temp);
		 }
		 // Go down to the child position
		 p = minChildPos;
		 }

	 }
	 public boolean add(Account acct) {
		// Add x at the end of the array list
		 arrayHeap.add(acct);
		 // Sift up
		 siftUp();
		 return true;
	 }
	 public boolean add(Account acct, Comparator<Account> comp) {
		if(isEmpty()) {
			add(acct);
			return true;
		}
		else {
		int i =0;
		while(i < size()) {
			 if(comp.compare(acct,valueAt(i)) < 0) {
				 arrayHeap.add(i, acct);
				 siftUp(comp);
				 return true;
			 }
			 i++;
		}
		}
		add(acct);
		return true;
		
	 }
	 public Account removeMin() {
		 if (isEmpty())
			 throw new RuntimeException("Priority Queue is empty.");
		else{
			 Account val = arrayHeap.get(0);
			 // Replace root by last leaf
			 int lastPos = arrayHeap.size()-1;
			 arrayHeap.set(0, arrayHeap.get(lastPos));
			 // Remove the last leaf
			 arrayHeap.remove(arrayHeap.size()-1);
			 siftDown();
			 return val;
			 }

	 }
	 public Account removeMin(Comparator<Account> comp) {
		 if (isEmpty())
			 throw new RuntimeException("Priority Queue is empty.");
		else{
			 Account min = arrayHeap.get(0);
			 int hold = 0;
			 for(int i = 1; i< size(); i++) {
				 if(comp.compare(min, valueAt(i)) > 0) {
					 min = valueAt(i);
					 hold = i;
				 }
			 }
			 // Replace root by last leaf
			 int lastPos = arrayHeap.size()-1;
			 arrayHeap.set(hold, arrayHeap.get(lastPos));
			 // Remove the last leaf
			 arrayHeap.remove(arrayHeap.size()-1);
			 siftDown(comp);
			 return min;
			 }

	 } 
	 public boolean isEmpty() {
		 return arrayHeap.size() == 0;
	 }
	 private Account valueAt(int pos) {
		return arrayHeap.get(pos); 
	 }
	 public Account remove(int pos) {
		Account hold = valueAt(pos);
		arrayHeap.remove(pos);
		return hold;
	 }
	 public Account remove(int index, Comparator<Account> comp) {
		 Account hold = new Account();
		 ArrayHeap arrayHeap2 = new ArrayHeap();
		 for(int i = 0;i<size();i++) {
			 arrayHeap2.add(arrayHeap.get(i), comp);
		 }
		 int j = 0;
		 boolean x = false;
		 while(j < size() && x == false) {
			 if(arrayHeap2.get(index).equals(arrayHeap.get(j))) {
				hold = arrayHeap.get(j);
				arrayHeap.remove(j);
				x = true;
			 }
			 j++;
		 }
		 return hold;
	 }
	 public Account get(int pos) {
		return arrayHeap.get(pos); 
	 }
	 public int size() {
		return arrayHeap.size();
	 }
	 public void set(int pos, Account acct) {
		 
	 }


	 

}
