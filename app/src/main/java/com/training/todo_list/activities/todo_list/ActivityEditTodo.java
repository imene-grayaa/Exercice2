package com.training.todo_list.activities.todo_list;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;
import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ActivityEditTodo extends AppCompatActivity {


    Spinner mTypesListSpinner;
    RelativeLayout mAddNewTypePanel, mSelectColorTextPanel;
    EditText mDescriptionEdit, mTodoTypeNameEdit;
    CheckBox mIsTodoDoneCheckBox;
    String mSColorType = "#858585";
    UUID mTodoItemId;


    TodoTypeManager mtTodoTypeManager = new TodoTypeManager();
    TodoManager mtTodoManager = new TodoManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        Intent mSource = getIntent();
        if (null != mSource) {
            Log.d("extra", "***" + mSource.getStringExtra("itemId"));
            mTodoItemId = UUID.fromString(mSource.getStringExtra("itemId"));
        }

        Todo tToEditTodoItem = mtTodoManager.todoFor(mTodoItemId);

        mSelectColorTextPanel = findViewById(R.id.select_color_panel);
        mTypesListSpinner = findViewById(R.id.todo_type_spinner);
        mAddNewTypePanel = findViewById(R.id.new_type_panel);
        mDescriptionEdit = findViewById(R.id.description_edit);
        mTodoTypeNameEdit = findViewById(R.id.todo_type_name_edit);
        mIsTodoDoneCheckBox = findViewById(R.id.todo_is_done);

        mDescriptionEdit.setText(tToEditTodoItem.description());
        mIsTodoDoneCheckBox.setChecked(tToEditTodoItem.isDone());


        List<TodoType> mTodoTypesData = mtTodoTypeManager.all();


        final List<String> spinnerArray = new ArrayList<>();

        spinnerArray.add(getString(R.string.type_hint));

        for (TodoType tTodoTypeItem : mTodoTypesData) {
            spinnerArray.add(tTodoTypeItem.name());

        }
        spinnerArray.add(getString(R.string.add_new_type));


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mTypesListSpinner.setAdapter(adapter);

        mTypesListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String tSelected = mTypesListSpinner.getSelectedItem().toString();
                if (tSelected.equals(getString(R.string.add_new_type))) {

                    showNewTypePanel(true);
                } else showNewTypePanel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int tTypePosition = 0, i = 0;
        for (TodoType tTodoType : mtTodoTypeManager.all()) {
            if (tTodoType.id() == tToEditTodoItem.idTodoType())
                tTypePosition = i + 1;
            else
                i++;
        }

        mTypesListSpinner.setSelection(tTypePosition);

    }

    void showNewTypePanel(Boolean tVisibility) {
        if (tVisibility)
            mAddNewTypePanel.setVisibility(View.VISIBLE);
        else
            mAddNewTypePanel.setVisibility(View.GONE);
    }


    public void selectColor(View pView) {
        final ColorPicker mColorPicker = new ColorPicker(ActivityEditTodo.this, 45, 100, 180);
        mColorPicker.show();
        mColorPicker.setCancelable(true);


        /* Set a new Listener called when user click "select" */
        mColorPicker.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {

                mSelectColorTextPanel.setBackgroundColor(color);
                mSColorType = String.format("#%06X", (0xFFFFFF & color));
                Log.d("color", mSColorType);
                mColorPicker.dismiss();


            }
        });
    }

    public void saveTodo(View pView) {


        if (mDescriptionEdit.getText().toString().length() < 1)
            Toast.makeText(getApplicationContext()
                    , getString(R.string.empty_description), Toast.LENGTH_LONG).show();

        else if (mTypesListSpinner.getSelectedItem().toString().equals(getString(R.string.type_hint)))
            Toast.makeText(getApplicationContext()
                    , getString(R.string.empty_type), Toast.LENGTH_LONG).show();
        else if (mTypesListSpinner.getSelectedItem().toString().equals(getString(R.string.add_new_type)) && mTodoTypeNameEdit.getText().toString().length() < 1)
            Toast.makeText(getApplicationContext()
                    , getString(R.string.empty_type_name), Toast.LENGTH_LONG).show();
        else {
            UUID mTypeTodoId;
            String tSelected = mTypesListSpinner.getSelectedItem().toString();
            if (tSelected.equals(getString(R.string.add_new_type))) {
                TodoType tNewTodoType = mtTodoTypeManager.create(mTodoTypeNameEdit.getText().toString(), mSColorType);
                mTypeTodoId = tNewTodoType.id();
            } else {
                mTypeTodoId = mtTodoTypeManager.todoTypeFor(tSelected).id();
            }

            Todo tTodo = new Todo(mDescriptionEdit.getText().toString()
                    , new Date(), mTypeTodoId, mIsTodoDoneCheckBox.isChecked(), mTodoItemId);
            mtTodoManager.edit(tTodo);

            Log.d("edited", "todo" + tTodo.toString());
            this.finish();
        }
    }

}
