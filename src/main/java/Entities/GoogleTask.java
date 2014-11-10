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
			if (obj instanceof GoogleTaskList){
				GoogleTask taskObj = (GoogleTask) obj;
				
				return this.title.equals(taskObj.title) &&
						this.status.equals(taskObj.status) &&
						this.completed.equals(taskObj.completed);
			}
		}
		return false;
	}
}
