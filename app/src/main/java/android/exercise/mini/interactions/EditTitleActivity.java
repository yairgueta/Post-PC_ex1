package android.exercise.mini.interactions;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
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



  private int i = 0, j = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    i++;
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

    Button tests = findViewById(R.id.tests);

    tests.setOnClickListener(v ->{
    });

    // handle clicks on "done edit"
    fabEditDone.setOnClickListener(v -> {
      /*
      TODO:
      1. animate out the "done edit" FAB
      2. animate in the "start edit" FAB

      to complete (1.) & (2.), start by just changing visibility. only add animations after everything else is ready
       */

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
    /*
    TODO:
    if user is now editing, tap on BACK will revert the edit. do the following:
    3. animate out the "done-edit" FAB
    4. animate in the "start-edit" FAB

     */
    if (isEditing){
      toggleViewMode();
      textViewTitle.setText(currentTitle);
    }else{
      super.onBackPressed();
    }

  }

  private void toggleViewMode(){
    editTextTitle.setVisibility(View.GONE);
    textViewTitle.setVisibility(View.VISIBLE);

    fabEditDone.setVisibility(View.GONE);
    fabStartEdit.setVisibility(View.VISIBLE);

    isEditing = false;
  }

  private void toggleEditMode(){
    fabStartEdit.setVisibility(View.GONE);
    fabEditDone.setVisibility(View.VISIBLE);

    textViewTitle.setVisibility(View.GONE);
    editTextTitle.setVisibility(View.VISIBLE);

    isEditing = true;
  }

}