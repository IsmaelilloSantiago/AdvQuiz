package es.ulpgc.eite.da.quiz.question;

import java.util.Objects;

public class QuestionViewModel {

  // put the view state here

  public String question;
  public String option1;
  public String option2;
  public String option3;
  public String answer = "";


  public boolean optionEnabled= true;
  public boolean nextEnabled = false;
  public boolean cheatEnabled = true;

  public boolean optionClicked;
  public int option;

  public boolean answerCheated;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    QuestionViewModel that = (QuestionViewModel) obj;
    return optionEnabled == that.optionEnabled &&
            nextEnabled == that.nextEnabled &&
            cheatEnabled == that.cheatEnabled &&
            answer == that.answer&&
            Objects.equals(question, that.question) &&
            Objects.equals(answer, that.answer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
            question, answer, optionClicked, cheatEnabled, nextEnabled
    );
  }

  @Override
  public String toString() {
    return
            "question: " + question + ", " +
                    "answer: " +answer + ", " +
                    "optionEnabled" + optionEnabled + ", " +

                    "cheatButton: " + cheatEnabled + ", " +
                    "nextButton: " + nextEnabled;
  }


}
