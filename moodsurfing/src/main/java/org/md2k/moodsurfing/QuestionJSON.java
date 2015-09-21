package org.md2k.moodsurfing;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by smhssain on 9/21/2015.
 */
public class QuestionJSON implements Serializable{
    String question;
    ArrayList<String> response;
    long prompt_time;
}
