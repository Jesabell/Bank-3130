import java.util.Calendar;

public class TransactionReceipt {
	private TransactionTicket ticket;
	private String status = "Done";
	private String reason = "";
	private double pre, post;
	private Calendar day;

	public TransactionReceipt() {
		ticket = new TransactionTicket();
		status = "";
		reason = "";
		pre = 0.0;
		post = 0.0;
		day = ticket.getDateOfTransaction();
;		
	}
	
	public TransactionReceipt(TransactionReceipt myReceipt) {
		this.ticket = new TransactionTicket(myReceipt.ticket);
		this.status = myReceipt.status;
		this.reason = myReceipt.reason;
		this.pre = myReceipt.pre;
		this.post = myReceipt.post;
		this.day = myReceipt.day;
	}
	public TransactionReceipt(TransactionTicket tick) {
		ticket = tick;
		day = tick.getDateOfTransaction();
	}
	
	public TransactionReceipt(TransactionTicket tick, double po) {
		ticket = tick;
		post = po;
		day = ticket.getDateOfTransaction();

	}
	public TransactionReceipt(TransactionTicket tick,  String reas) {
		ticket = tick;
		day = ticket.getDateOfTransaction();
		reason = reas;
	} 
	public TransactionReceipt(TransactionTicket tick, double pr, double po, String reas) {
		ticket = tick;
		day = ticket.getDateOfTransaction();
		reason = reas;
		pre = pr;
		post = po;
	}
	public TransactionReceipt(TransactionTicket tick, double pr, double po) {
		ticket = tick;
		pre = pr;
		post = po;
		day = ticket.getDateOfTransaction();

	}
	public TransactionTicket getTransactionTicket() {
		TransactionTicket ticketCopy = new TransactionTicket(ticket);
		return ticketCopy;
	}
	
	public String getTransactionSuccessIndicatorFlag() {
		return status;
	}
	public String setTransactionSuccessIndicatorFlag() {
		status = "Failed";
		return status;
	}
	
	public String getTransactionFailureReason() {
		return reason;
	}
	
	public double getPreTransactionBalance() {
		return pre;
	}
	
	public double getPostTransactionBalance() {
		return post;
	}
	
	public Calendar getPostTransactionMaturityDate() {
		day.add(Calendar.MONTH, ticket.gettermOfCD());
		return day;
	}
	public String toString() {
		String str = String.format("%-11s %-11s %-11s %-11s %-11s %-11s",ticket.toString(), status, "$:" + String.format("%.2f",ticket.getTransactionAmount()),"$:" + String.format("%.2f",pre), "$:" + String.format("%.2f",post), reason);
		return str;
	}
	
	
}
