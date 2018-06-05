package com.training.todo_list.activities.todo_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.utils.MyRecyclerViewAdapter;

import java.util.List;

public class ActivityTodoList extends Activity implements MyRecyclerViewAdapter.ItemClickListener {
    RecyclerView mTodoList;
    List<Todo> mTodoListData;
    MyRecyclerViewAdapter mAdapter;
    TodoManager todoManager = new TodoManager();

    final static int RESULT_EDIT_CODE = 543;
    final static int RESULT_ADD_CODE = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lt_act_todo_list);

        mTodoListData = todoManager.all();
        mTodoList = findViewById(R.id.list);


        mTodoList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecyclerViewAdapter(this, mTodoListData);
        mAdapter.setClickListener(this);
        mTodoList.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                (mTodoList.getContext(), LinearLayoutManager.VERTICAL);
        mTodoList.addItemDecoration(dividerItemDecoration);

    }

    @Override
    public void onItemClick(View view, int position) {

        Todo tTodo = mAdapter.getItem(position);
        Intent editToDoActivity = new Intent(getApplicationContext(), ActivityEditTodo.class);
        editToDoActivity.putExtra("itemId", tTodo.id() + "");
        startActivityForResult(editToDoActivity, RESULT_EDIT_CODE);
    }


    public void askAddTodo(View pView) {

        Intent addToDoActivity = new Intent(getApplicationContext(), AddTodoActivity.class);
        startActivityForResult(addToDoActivity, RESULT_ADD_CODE);
    }


    public void askSurprise(View pView) {
        AlertDialog.Builder tBuilder = new AlertDialog.Builder(this);
        tBuilder.setTitle(R.string.dialog_title_surprise);
        tBuilder.setMessage(R.string.dialog_message_surprise);
        tBuilder.setPositiveButton(R.string.confirm, null);
        tBuilder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mTodoListData.clear();
        mTodoListData = todoManager.all();
        mAdapter = new MyRecyclerViewAdapter(this, mTodoListData);
        mAdapter.setClickListener(this);
        mTodoList.setAdapter(mAdapter);
    }
}
