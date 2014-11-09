package Entities;

import org.joda.time.DateTime;

public class GoogleTask {
	
	public static final String kind = "tasks#task";
	public final String title;
	public final String notes;
	public final String status;
	public final DateTime completed;
	
	public GoogleTask(String title, String notes, String status, DateTime completed){
		this.title = title;
		this.notes = notes;
		this.status = status;
		this.completed = completed;
	}
	
	public String toString(){
		return "{ kind = " + kind +
				" , title = " + title +
				" , notes = " + notes +
				" , status = " + status + 
				(status.equals("completed") ? " , completed = " + completed : "") + 
				" }"; 
	}

}
