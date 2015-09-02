package org.md2k.moodsurfing;

import java.util.ArrayList;
import java.util.Arrays;


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

public class Questions {
    public static final String MULTIPLE_CHOICE = "multiple_choice";
    public static final String MULTIPLE_SELECT = "multiple_select";
    public static final String IMAGE = "image";
    public static final String COLOR = "color";


    public static final String MULTIPLE_SELECT_SPECIAL = "multiple_select_special";

    private static Questions instance=null;
    public static Questions getInstance(){
        if(instance==null)
            instance=new Questions();
        return instance;
    }
    void clear(int exerciseType){
        Question[] questions=getQuestions(exerciseType);
        for(int i=0;i<questions.length;i++) {
            questions[i].setQuestion_responses_selected(new ArrayList<String>());
            questions[i].setQuestion_responses_selected_random("");
            questions[i].setPrompt_time(-1);
        }
    }
    private Questions(){

    }
    public void destroy(){
        instance=null;
    }

    private Question[] useYourImagination = new Question[]{
            new Question(0, "You can use this exercise to help you use your imagination to manage your unpleasant thoughts or emotions. You can press the [speaker icon] on any screen to have the instructions read to you.", null, null, null, -1),
            new Question(1, "We all experience unpleasant thoughts or emotions from time to time. Sometimes it is possible to change them, and sometimes it’s not.\n\n\nHave you ever noticed that sometimes trying to get rid of a thought or an emotion actually makes it stronger?\n", MULTIPLE_CHOICE, new ArrayList<String>(Arrays.asList(new String[]{"Yes", "No"})), null,R.raw.uyi_01),
            new Question(2, "So try not to fight them or change the way you feel. Here is an exercise that can be used instead.", null, null, new ArrayList<String>(Arrays.asList(new String[] {"1:Yes"})),R.raw.uyi_02),
            new Question(3, "People manage unpleasant thoughts or emotions in various ways. The following is a useful exercise that can help people notice how they feel when they are stressed without trying to change it or make it go away.", null, null, new ArrayList<String>(Arrays.asList(new String[] {"1:No"})),R.raw.uyi_03),
            new Question(4, "Take a moment and try to visualize a thought or an emotion that you are currently having as a tangible object.\n\nImagine it placed on a table before you. Look at it for a couple of seconds.\n\nWhat does it look like?\n\n Continue observing it and answer the following questions:", null, null, null,R.raw.uyi_04),
            new Question(5, "Is it...\n", MULTIPLE_SELECT_SPECIAL, null, null,R.raw.uyi_05),
            new Question(6, "What color is it?\n", COLOR, null, null,R.raw.uyi_06),
            new Question(7, "Does it have a shape?\n", MULTIPLE_CHOICE, new ArrayList<String>(Arrays.asList(new String[]{"Yes", "No", "NA"})), null,R.raw.uyi_07),
            new Question(8, "Continue observing and studying your object for another minute or so. \n" +
                    "\n" +
                    "Focusing on the details and trying to describe them can help you distance yourself from negative thoughts or emotions. You can use this strategy at anytime to accept the existence of your negative thoughts or feelings and distance yourself from them.", null, null, null,R.raw.uyi_08),
            new Question(9, "Do you think that this exercise can be useful in dealing with you stress?\n", MULTIPLE_CHOICE, new ArrayList<String>(Arrays.asList(new String[]{"Yes", "No"})), null,-1),
            new Question(10, "Congratulations.\n\n You have finished \"Use your Imagination\" Exercise", null, null, null,-1),

    };
    private Question[] noticeAndAccept = new Question[]{
            new Question(0, "You can use this exercise to help you become aware of and accept any physical sensations caused by stress. You can press the [speaker icon] on any screen to have the instructions read to you.", null, null, null,-1),
            new Question(1, "Sometimes, you may experience stress physically. Try taking a moment to just notice what is happening to your body.", null, null, null,R.raw.naa_01),
            new Question(2, "Are you experiencing any ... (select all that apply)", MULTIPLE_SELECT, new ArrayList<String>(Arrays.asList(new String[]{"Tension", "Shakiness", "Pain", "Cigarette craving", "Restlessness", "Irritability", "Other physical sensations", "None of the above"})), null,R.raw.naa_02),
            new Question(3, "Different people experience stress in different ways; for some people the physical manifestations of stress can be as unpleasant as their stressful thoughts.  \n" +
                    "\n" +
                    "Next time you experience any of these physical sensations, come back to this exercise and try it out.", null, null,new ArrayList<String>(Arrays.asList(new String[] {"2:None of the above"})),R.raw.naa_03),
            new Question(4, "Take a couple minutes and try to focus on your _____\n\nRemember, these are just sensations, even though your mind may label them as 'good' or 'bad'; you can simply allow them to be present, notice them and accept them for what they are, without judgement.\n\n\nTake a moment to answer the following questions about your _____", null, null,new ArrayList<String>(Arrays.asList(new String[] {"2:Tension","2:Shakiness", "2:Pain", "2:Cigarette craving", "2:Restlessness", "2:Irritability", "2:Other physical sensations"})),R.raw.naa_04_pain),
            new Question(5, "Is your _____ in one place or in multiple places in your body?\n", MULTIPLE_CHOICE, new ArrayList<String>(Arrays.asList(new String[]{"One", "Multiple"})),new ArrayList<String>(Arrays.asList(new String[] {"2:Tension","2:Shakiness", "2:Pain", "2:Cigarette craving", "2:Restlessness", "2:Irritability", "2:Other physical sensations"})),R.raw.naa_05_pain),
            new Question(6, "Is it changing or does it always stay the same?", MULTIPLE_CHOICE, new ArrayList<String>(Arrays.asList(new String[]{"Changing", "Always the Same"})),new ArrayList<String>(Arrays.asList(new String[] {"2:Tension","2:Shakiness", "2:Pain", "2:Cigarette craving", "2:Restlessness", "2:Irritability", "2:Other physical sensations"})),R.raw.naa_06),
            new Question(7, "Is it strong or weak?", MULTIPLE_CHOICE, new ArrayList<String>(Arrays.asList(new String[]{"Strong", "Weak"})),new ArrayList<String>(Arrays.asList(new String[] {"2:Tension","2:Shakiness", "2:Pain", "2:Cigarette craving", "2:Restlessness", "2:Irritability", "2:Other physical sensations"})),R.raw.naa_07),
            new Question(8, "Are there any places in your body where you don't feel it?", MULTIPLE_CHOICE, new ArrayList<String>(Arrays.asList(new String[]{"Yes", "No"})),new ArrayList<String>(Arrays.asList(new String[] {"2:Tension","2:Shakiness", "2:Pain", "2:Cigarette craving", "2:Restlessness", "2:Irritability", "2:Other physical sensations"})),R.raw.naa_08),
            new Question(9, "Physical sensations are an important part of how we experience stress and the acceptance of those sensations can help us deal with that stress in a healthy way.", null, null,new ArrayList<String>(Arrays.asList(new String[] {"2:Tension","2:Shakiness", "2:Pain", "2:Cigarette craving", "2:Restlessness", "2:Irritability", "2:Other physical sensations"})),R.raw.naa_09),
            new Question(10, "Did you find this exercise useful in dealing with your stress?", MULTIPLE_CHOICE, new ArrayList<String>(Arrays.asList(new String[]{"Yes", "No"})),new ArrayList<String>(Arrays.asList(new String[] {"2:Tension","2:Shakiness", "2:Pain", "2:Cigarette craving", "2:Restlessness", "2:Irritability", "2:Other physical sensations"})),-1),
            new Question(11, "Congratulations.\n\n You have finished \"Notice and Accept\" Exercise", null, null, null,-1),
    };

    private Question[] surfTheMood = new Question[]{
            new Question(0, "You can use this exercise to manage stress through the use of guided imagery. You can press the [speaker icon] on any screen to have the instructions read to you.", null, null, null,-1),
            new Question(1, "Sometimes unpleasant emotions can become very intense and seem somewhat overpowering. \n" +
                    "\n" +
                    "If this happens, you can imagine these emotions as a wave. Let me guide you through this exercise.", null, null, null,R.raw.ms_01),
            new Question(2, "Begin by relaxing and just focusing on the presence of your emotions. Do not pass judgment; just acknowledge them.\n\n\nImagine your unpleasant emotions as a wave, rising, peaking, and then fading away.", null, null, null,R.raw.ms_02),
            new Question(3, "Imagine yourself riding that wave. Simply be aware of the movement as it washes through you.\n" +
                    "\n" +
                    "Be aware of the sensations without reacting.", IMAGE, new ArrayList<String>(Arrays.asList(new String[]{"react"})), null,R.raw.ms_03),
            new Question(4, "Continue being aware of your emotions as they ebb and flow like waves.\n\nImagine yourself riding those waves, being present and not reacting as they peak and then fade.", Questions.IMAGE,new ArrayList<String>(Arrays.asList( new String[]{"peak"})), null,R.raw.ms_04),
            new Question(5, "Do you think this exercise can be useful in dealing with your unpleasant emotions?", MULTIPLE_CHOICE, new ArrayList<String>(Arrays.asList(new String[]{"Yes", "No"})), null,-1),
            new Question(6, "Congratulations.\n\n You have finished \"Surf the Mood\" Exercise", null, null, null,-1)
    };
    public Question[] getQuestions(int exerciseType){
        switch(exerciseType){
            case Constants.NOTICE_AND_ACCEPT:
                return noticeAndAccept;
            case Constants.SURF_THE_MOOD:
                return surfTheMood;
            case Constants.USE_YOUR_IMAGINATION:
                return useYourImagination;
        }
        return null;
    }
    public Question getQuestion(int exerciseType, int id){
        switch(exerciseType){
            case Constants.NOTICE_AND_ACCEPT:
                return noticeAndAccept[id];
            case Constants.SURF_THE_MOOD:
                return surfTheMood[id];
            case Constants.USE_YOUR_IMAGINATION:
                return useYourImagination[id];
        }
        return null;
    }

}
