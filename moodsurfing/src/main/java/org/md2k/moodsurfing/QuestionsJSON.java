package org.md2k.moodsurfing;

import android.os.Parcel;
import android.os.Parcelable;
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
public class QuestionsJSON implements Parcelable{
    String exercise_type;
    long start_time;
    long end_time;
    String status;
    ArrayList<QuestionJSON> questions;
    QuestionsJSON(Questions questions, int exerciseType){
        start_time=questions.startTime;
        end_time=questions.endTime;
        status=questions.status;
        if(exerciseType==Constants.NOTICE_AND_ACCEPT){
            exercise_type="Notice and Accept";
            prepareQuestion(questions.getQuestions(exerciseType));
        }else if(exerciseType==Constants.SURF_THE_MOOD){
            exercise_type="Surf the Mood";
            prepareQuestion(questions.getQuestions(exerciseType));
        }else if(exerciseType==Constants.USE_YOUR_IMAGINATION){
            exercise_type="Use Your Imagination";
            prepareQuestion(questions.getQuestions(exerciseType));
        }
    }

    protected QuestionsJSON(Parcel in) {
        exercise_type = in.readString();
        start_time = in.readLong();
        end_time = in.readLong();
        questions = in.createTypedArrayList(QuestionJSON.CREATOR);
    }

    public static final Creator<QuestionsJSON> CREATOR = new Creator<QuestionsJSON>() {
        @Override
        public QuestionsJSON createFromParcel(Parcel in) {
            return new QuestionsJSON(in);
        }

        @Override
        public QuestionsJSON[] newArray(int size) {
            return new QuestionsJSON[size];
        }
    };

    void prepareQuestion(Question question[]){
        questions=new ArrayList<>();
        for (Question aQuestion : question) {
            questions.add(new QuestionJSON(aQuestion));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exercise_type);
        dest.writeLong(start_time);
        dest.writeLong(end_time);
        dest.writeTypedList(questions);
    }
}
