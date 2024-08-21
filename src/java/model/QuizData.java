package model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class QuizData {
    public class Question {
        public class Option {
            @SerializedName("option_id")
            private int id;
    
            @SerializedName("option_text")
            private String text;
    
            @SerializedName("is_correct")
            private boolean correct;

            private boolean selected;
    
            public int getId() {
                return id;
            }
    
            public void setId(int id) {
                this.id = id;
            }
    
            public String getText() {
                return text;
            }
    
            public void setText(String text) {
                this.text = text;
            }
    
            public boolean isCorrect() {
                return correct;
            }
    
            public void setCorrect(boolean correct) {
                this.correct = correct;
            }

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }
        }
    
        @SerializedName("question_id")
        private int id;
    
        @SerializedName("question_text")
        private String text;
    
        @SerializedName("explanation")
        private String explanation;
    
        @SerializedName("options")
        private List<Option> options;
    
        @SerializedName("multiple_answers")
        private boolean multipleAnswers;
    
        public int getId() {
            return id;
        }
    
        public void setId(int id) {
            this.id = id;
        }
    
        public String getText() {
            return text;
        }
    
        public void setText(String text) {
            this.text = text;
        }
    
        public String getExplanation() {
            return explanation;
        }
    
        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }
    
        public List<Option> getOptions() {
            return options;
        }
    
        public void setOptions(List<Option> options) {
            this.options = options;
        }
    
        public boolean isMultipleAnswers() {
            return multipleAnswers;
        }
    
        public void setMultipleAnswers(boolean multipleAnswers) {
            this.multipleAnswers = multipleAnswers;
        }
    }    

    @SerializedName("pass_grade")
    private int passGrade;

    @SerializedName("questions")
    private List<Question> questions;

    public int getPassGrade() {
        return passGrade;
    }

    public void setPassGrade(int passGrade) {
        this.passGrade = passGrade;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}