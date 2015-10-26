package com.arik.commit;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;;

public class CommitData extends JSONObject{

    String commitText;
    String reminder;
    Integer currentDays;

    public String getCommitText(){
        return commitText;
    }

    public String getReminder(){
        return reminder;
    }

    public Integer getCurrentDays(){
        return currentDays;
    }

    public void setCommitText(String commit){
        commitText = commit;
        try{
            this.put("Commit", commit);
        }catch(JSONException e){

        }
    }

    public void setReminder(String reminderSet){
        reminder = reminderSet;
        try{
            this.put("Reminder", reminderSet);
        }catch(JSONException e){

        }
    }

    public void setCurrentDays(int days){
        currentDays = days;
        try{
            this.put("Days", days);
        }catch(JSONException e){

        }
    }

	/*@Override
	public String toString(){
		return new StringBuffer(" Commit: ").append(commitText)
		.append(" Reminder: ").append(reminder)
		.append(" Days: ").append(currentDays).toString();
	}*/

}

