package Entities;

public class GitHubIssue {
	
	public final String title;
	public final String state;
	public final String closed_at;
	public final String body;
	
	public GitHubIssue(String title, String state, String closed_at , String body){
		this.title = title;
		this.state = state;
		this.closed_at = closed_at;
		this.body = body;
	}
	
	public String toString(){
		return "{ title = " + title
				+ " , state = " + state
				+ " , body = " + body
				+ (!state.equals("open") ? " , closed_at = " + closed_at : "")  
				+ " }";
	}

}
