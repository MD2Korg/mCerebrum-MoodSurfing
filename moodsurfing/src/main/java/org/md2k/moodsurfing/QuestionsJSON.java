package org.md2k.moodsurfing;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by smhssain on 9/21/2015.
 */
public class QuestionsJSON implements Serializable{
    String exercise_type;
    long start_time;
    long end_time;
    ArrayList<QuestionJSON> questions;
    QuestionsJSON(Questions questions, int exerciseType){
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
    void prepareQuestion(Question question[]){
        for(int i=0;i<question.length;i++){

        }

    }
}
