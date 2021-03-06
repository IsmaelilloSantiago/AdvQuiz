package es.ulpgc.eite.da.quiz.question;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.quiz.R;
import es.ulpgc.eite.da.quiz.app.AppMediator;
import es.ulpgc.eite.da.quiz.app.CheatToQuestionState;
import es.ulpgc.eite.da.quiz.app.QuestionToCheatState;

public class QuestionPresenter implements QuestionContract.Presenter {

  public static String TAG = QuestionPresenter.class.getSimpleName();

  private AppMediator mediator;
  private WeakReference<QuestionContract.View> view;
  private QuestionState state;
  private QuestionContract.Model model;

  public QuestionPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getQuestionState();
  }

  @Override
  public void onStart() {
    Log.e(TAG, "onStart()");

    // call the model
    state.question = model.getQuestion();
    state.option1 = model.getOption1();
    state.option2 = model.getOption2();
    state.option3 = model.getOption3();

    // reset state to tests
    state.answerCheated = false;
    state.optionClicked = false;
    state.option = 0;

    // update the view
    disableNextButton();
    view.get().resetReply();

  }


  @Override
  public void onRestart() {
    Log.e(TAG, "onRestart()");

    //TODO: falta implementacion
    int cont = 0;
    model.setQuizIndex(state.quizIndex);
    if (state.optionClicked == false && cont == 0) {
      view.get().resetReply();
      cont++;

    } else if (state.optionClicked == true && cont == 0){
      onOptionButtonClicked(state.option);
      cont ++;
  }
}



  //@Override
  public void onResume() {
    Log.e(TAG, "onResume()");

    //TODO: falta implementacion

    // use passed state if is necessary
    CheatToQuestionState savedState = getStateFromCheatScreen();


    if (savedState != null) {

      // fetch the model
      Log.e(TAG, "llega je");
      if(savedState.answerCheated){
        if(!model.hasQuizFinished()) {
          onNextButtonClicked();
        }else{
            state.nextEnabled = false;
            state.optionEnabled = false;
        }


      }

    }

    // update the view

    view.get().displayQuestion(state);

  }


  @Override
  public void onDestroy() {
    Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onOptionButtonClicked(int option) {
    Log.e(TAG, "onOptionButtonClicked()");

    //TODO: falta implementacion
  if ( option == 1){
    state.option =1;
  }else if (option == 2){
    state.option =2;
  }else{
    state.option = 3;
  }

  state.optionClicked = true;
    boolean isCorrect = model.isCorrectOption(option);
    if(isCorrect) {
      view.get().updateReply(isCorrect);
      state.answer = model.getAnswer();
      state.cheatEnabled=false;

    } else {
      view.get().updateReply(isCorrect);
      state.answer = model.getAnswer();
      state.cheatEnabled=true;
    }
    enableNextButton();

    view.get().displayQuestion(state);


  }

  @Override
  public void onNextButtonClicked() {
    Log.e(TAG, "onNextButtonClicked()");
    Log.e(TAG, model.getQuizIndex() + "");

    //TODO: falta implementacion
    //model.updateQuizIndex();
    state.quizIndex = state.quizIndex + 5;
    model.setQuizIndex(state.quizIndex);
    Log.e(TAG, model.getQuizIndex() + "");
    state.question = model.getQuestion();
    state.option1 = model.getOption1();
    state.option2 = model.getOption2();
    state.option3 = model.getOption3();
    state.optionClicked = false;
    view.get().resetReply();
    disableNextButton();

    view.get().displayQuestion(state);

  }

  @Override
  public void onCheatButtonClicked() {
    Log.e(TAG, "onCheatButtonClicked()");

    //TODO: falta implementacion
    String respuesta = model.getAnswer();
    Log.e(TAG, respuesta);
    QuestionToCheatState savedstate = new QuestionToCheatState(respuesta);
    passStateToCheatScreen(savedstate);
    Log.e(TAG, savedstate.answer);
    view.get().navigateToCheatScreen();
  }

  private void passStateToCheatScreen(QuestionToCheatState state) {

    //TODO: falta implementacion
    mediator.setQuestionToCheatState(state);


  }

  private CheatToQuestionState getStateFromCheatScreen() {

    //TODO: falta implementacion

    return mediator.getCheatToQuestionState();
  }

  private void disableNextButton() {
    state.optionEnabled=true;
    state.cheatEnabled=true;
    state.nextEnabled=false;

  }

  private void enableNextButton() {
    state.optionEnabled=false;
    state.nextEnabled = true;
    //state.cheatEnabled = false;

    if(!model.hasQuizFinished()) {
      state.nextEnabled=true;
    }else{
      state.nextEnabled = false;
    }
  }

  @Override
  public void injectView(WeakReference<QuestionContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(QuestionContract.Model model) {
    this.model = model;
  }

}
