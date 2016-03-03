package org.md2k.moodsurfing;


import android.os.Parcel;
import android.os.Parcelable;

import org.md2k.utilities.Report.Log;

import java.util.ArrayList;


/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

public class Question implements Parcelable {
    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
    private static final String TAG = Question.class.getSimpleName();
    private int question_id;
    private String question_type;
    private String question_text;
    private ArrayList<String> question_responses;
    private ArrayList<String> condition;
    private ArrayList<String> question_responses_selected;
    private String question_responses_selected_random;
    private long prompt_time;
    private long completion_time;
    private int audio_R_raw;

    protected Question(Parcel in) {
        question_id = in.readInt();
        question_type = in.readString();
        question_text = in.readString();
        question_responses = in.createStringArrayList();
        condition = in.createStringArrayList();
        question_responses_selected = in.createStringArrayList();
        question_responses_selected_random = in.readString();
        prompt_time = in.readLong();
        completion_time = in.readLong();
        audio_R_raw = in.readInt();
    }

    public Question(int question_id, String question_text, String question_type, ArrayList<String> question_responses, ArrayList<String> condition, int audio_R_raw) {
        this.question_responses_selected_random = "";
        this.question_id = question_id;
        this.question_type = question_type;
        this.question_text = question_text;
        this.question_responses = question_responses;
        this.condition = condition;
        this.question_responses_selected = new ArrayList<>();
        prompt_time = -1;
        this.audio_R_raw = audio_R_raw;
    }

    private boolean hasResponseSelected(String response) {
        if(question_responses_selected==null) return false;
        for(int i=0;i<question_responses_selected.size();i++)
            if(question_responses_selected.get(i).equals(response)) return true;
        return false;
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

    public void setQuestion_responses(ArrayList<String> question_responses) {
        this.question_responses = question_responses;
    }

    public boolean isType(String TYPE){
        return TYPE.equals(question_type);
    }

    public int getAudio_R_raw() {
        return audio_R_raw;
    }

    public void setAudio_R_raw(int audio_R_raw) {
        this.audio_R_raw = audio_R_raw;
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

    protected boolean isResponseExist(String option) {
        if(question_responses_selected==null) return false;
        for (int i = 0; i < question_responses_selected.size(); i++)
            if (question_responses_selected.get(i).equals(option)) return true;
        return false;
    }

    protected boolean isValidCondition(Question[] questions) {
        if (condition == null) return true;
        for(int i=0;i<condition.size();i++) {
            String[] separated = condition.get(i).split(":");
            int qid = Integer.valueOf(separated[0]);
            if (questions[qid].hasResponseSelected(separated[1])) return true;
        }
        return false;
    }

    protected boolean isValid() {
        Log.d(TAG,"isValid: question_type="+question_type+" selected="+question_responses_selected);
        if(question_responses_selected!=null)
            Log.d(TAG,"isValid: "+question_responses_selected.toString());

        if (question_type == null) return true;

        if(question_type.equals(Questions.MULTIPLE_SELECT_SPECIAL)){
            if(question_responses_selected==null)
                return false;
            else
                return question_responses_selected.size() == 4;
        }
        if (question_type.equals(Questions.MULTIPLE_CHOICE)){
            if(question_responses_selected==null) return false;
            else
                return question_responses_selected.size() == 1;
        }
        if (question_type.equals(Questions.MULTIPLE_SELECT)){
            if(question_responses_selected==null) return false;
            else
                return question_responses_selected.size() > 0;
        }

        return true;
    }

    public long getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(long completion_time) {
        this.completion_time = completion_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(question_id);
        dest.writeString(question_type);
        dest.writeString(question_text);
        dest.writeStringList(question_responses);
        dest.writeStringList(condition);
        dest.writeStringList(question_responses_selected);
        dest.writeString(question_responses_selected_random);
        dest.writeLong(prompt_time);
        dest.writeLong(completion_time);
        dest.writeInt(audio_R_raw);
    }
}

