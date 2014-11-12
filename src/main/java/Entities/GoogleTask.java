package Entities;

import org.joda.time.DateTime;

public class GoogleTask {
	
	public static final String kind = "tasks#task";
	public final String title;
	public final String status;
	public final DateTime completed;
	
	public GoogleTask(String title, String status, DateTime completed){
		this.title = title;
		this.status = status;
		this.completed = completed;
	}
	
	public String toString(){
		return "{ kind = " + kind +
				" , title = " + title +
				" , status = " + status + 
				(status.equals("completed") ? " , completed = " + completed : "") + 
				" }"; 
	}
	
	public boolean equals(Object obj){
		if (obj != null){
			if (obj instanceof GoogleTask){
				GoogleTask taskObj = (GoogleTask) obj;
				
				return (this.title==null ? this.title == taskObj.title : this.title.equals(taskObj.title));// &&
						//(this.status == null ? this.status == taskObj.status : this.status.equals(taskObj.status)) &&
						//(this.completed==null ? this.completed == taskObj.completed : this.completed.equals(taskObj.completed));
			}
		}
		return false;
	}
}
