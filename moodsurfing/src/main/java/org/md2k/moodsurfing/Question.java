package org.md2k.moodsurfing;


import org.md2k.utilities.Report.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private static final String TAG = Question.class.getSimpleName();
    private int question_id;
    private String question_type;
    private String question_text;
    private ArrayList<String> question_responses;
    private ArrayList<String> condition;
    private ArrayList<String> question_responses_selected;
    private String question_responses_selected_random;
    private long prompt_time;
    boolean hasResponseSelected(String response){
        if(question_responses_selected==null) return false;
        for(int i=0;i<question_responses_selected.size();i++)
            if(question_responses_selected.get(i).equals(response)) return true;
        return false;
    }

    public Question(int question_id, String question_text, String question_type, ArrayList<String> question_responses, ArrayList<String> condition) {
        this.question_responses_selected_random="";
        this.question_id = question_id;
        this.question_type = question_type;
        this.question_text = question_text;
        this.question_responses = question_responses;
        this.condition = condition;
        this.question_responses_selected = new ArrayList<>();
        prompt_time = -1;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public ArrayList<String> getQuestion_responses() {
        return question_responses;
    }
    public boolean isType(String TYPE){
        if(question_type==null) return false;
        if(question_type.equals(TYPE)) return true;
        return false;
    }

    public void setQuestion_responses(ArrayList<String> question_responses) {
        this.question_responses = question_responses;
    }

    public ArrayList<String> getCondition() {
        return condition;
    }

    public void setCondition(ArrayList<String> condition) {
        this.condition = condition;
    }

    public ArrayList<String> getQuestion_responses_selected() {
        return question_responses_selected;
    }

    public void setQuestion_responses_selected(ArrayList<String> question_responses_selected) {
        this.question_responses_selected = question_responses_selected;
    }

    public String getQuestion_responses_selected_random() {
        return question_responses_selected_random;
    }

    public void setQuestion_responses_selected_random(String question_responses_selected_random) {
        this.question_responses_selected_random = question_responses_selected_random;
    }

    public long getPrompt_time() {
        return prompt_time;
    }

    public void setPrompt_time(long prompt_time) {
        this.prompt_time = prompt_time;
    }
    boolean isResponseExist(String option) {
        if(question_responses_selected==null) return false;
        for (int i = 0; i < question_responses_selected.size(); i++)
            if (question_responses_selected.get(i).equals(option)) return true;
        return false;
    }
    boolean isValidCondition(Question[] questions) {
        if (condition == null) return true;
        for(int i=0;i<condition.size();i++) {
            String[] separated = condition.get(i).split(":");
            int qid = Integer.valueOf(separated[0]);
            if (questions[qid].hasResponseSelected(separated[1])) return true;
        }
        return false;
    }
    boolean isValid() {
        Log.d(TAG,"isValid: question_type="+question_type+" selected="+question_responses_selected);
        if(question_responses_selected!=null)
            Log.d(TAG,"isValid: "+question_responses_selected.toString());

        if (question_type == null) return true;

        if(question_type.equals(Questions.MULTIPLE_SELECT_SPECIAL)){
            if(question_responses_selected==null)
                return false;
            else if(question_responses_selected.size()==4) return true;
            else return false;
        }
        if (question_type.equals(Questions.MULTIPLE_CHOICE)){
            if(question_responses_selected==null) return false;
            else if(question_responses_selected.size()==1) return true;
            else return false;
        }
        if (question_type.equals(Questions.MULTIPLE_SELECT)){
            if(question_responses_selected==null) return false;
            else if(question_responses_selected.size()>0) return true;
            else return false;
        }

        return true;
    }

}

