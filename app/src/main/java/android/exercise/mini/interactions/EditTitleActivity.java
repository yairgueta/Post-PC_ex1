package android.exercise.mini.interactions;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditTitleActivity extends AppCompatActivity {

  // TODO:
  //  you can add fields to this class. those fields will be accessibly inside any method
  //  (like `onCreate()` and `onBackPressed()` methods)
  // for any field, make sure to set it's initial value. You CAN'T write a custom constructor
  // for example, you can add this field:
  // `private boolean isEditing = false;`
  // in onCreate() set `this.isEditing` to `true` once the user starts editing, set to `false` once done editing
  // in onBackPressed() check `if(this.isEditing)` to understand what to do

  private boolean isEditing = false;
  private CharSequence currentTitle;

  private FloatingActionButton fabStartEdit;
  private FloatingActionButton fabEditDone;
  private TextView textViewTitle;
  private EditText editTextTitle;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
    setContentView(R.layout.activity_edit_title);

    // find all views
    fabStartEdit = findViewById(R.id.fab_start_edit);
    fabEditDone = findViewById(R.id.fab_edit_done);
    textViewTitle = findViewById(R.id.textViewPageTitle);
    editTextTitle = findViewById(R.id.editTextPageTitle);

    // setup - start from static title with "edit" button
    fabStartEdit.setVisibility(View.VISIBLE);
    fabEditDone.setVisibility(View.GONE);
    textViewTitle.setText("Page title here");
    textViewTitle.setVisibility(View.VISIBLE);
    editTextTitle.setText("");
    editTextTitle.setVisibility(View.GONE);

    // handle clicks on "start edit"
    fabStartEdit.setOnClickListener(v -> {
      /*
      TODO:
       */

      toggleEditMode();

      currentTitle = textViewTitle.getText();
      editTextTitle.setText(currentTitle);

      editTextTitle.requestFocus();
      editTextTitle.setSelection(currentTitle.length());
      imm.showSoftInput(editTextTitle, InputMethodManager.SHOW_IMPLICIT);    // 6
    });

//    Button tests = findViewById(R.id.tests);
//
//    tests.setOnClickListener(v ->{
//      tests.animate()
//              .alpha(0)
//              .start();
//
//    });

    // handle clicks on "done edit"
    fabEditDone.setOnClickListener(v -> {
      toggleViewMode();

      textViewTitle.setText(editTextTitle.getText());
      imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

      String newTitle = editTextTitle.getText().toString();
      if(newTitle.isEmpty()){
        textViewTitle.setText("Page title here");
      }else{
        textViewTitle.setText(newTitle);
      }
    });
  }


  @Override
  public void onBackPressed() {
    // BACK button was clicked
    if (isEditing){
      toggleViewMode();
      textViewTitle.setText(currentTitle);
    }else{
      super.onBackPressed();
    }

  }

  private void animateFadeOutFab(FloatingActionButton fab){
    fab.setClickable(false);

    fab.clearAnimation();
    fab.animate()
            .scaleX(0f)
            .scaleY(0f)
            .setDuration(200L)
            .withEndAction(() -> {
              fab.setVisibility(View.GONE);
              fab.setScaleX(1f);
              fab.setScaleY(1f);
            })
            .start();
  }

  private void animateFadeInFab(FloatingActionButton fab){
    fab.clearAnimation();
    fab.setVisibility(View.VISIBLE);
    fab.setAlpha(0f);


    fab.animate()
            .alpha(1f)
            .setDuration(400L)
            .withEndAction(()->fab.setClickable(true))
            .start();
  }

  private void toggleViewMode(){
    editTextTitle.setVisibility(View.GONE);
    textViewTitle.setVisibility(View.VISIBLE);


    animateFadeOutFab(fabEditDone);
    animateFadeInFab(fabStartEdit);

    isEditing = false;
  }

  private void toggleEditMode(){
    animateFadeOutFab(fabStartEdit);
    animateFadeInFab(fabEditDone);

    textViewTitle.setVisibility(View.GONE);
    editTextTitle.setVisibility(View.VISIBLE);

    isEditing = true;
  }

}