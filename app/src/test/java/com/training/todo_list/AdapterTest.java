package com.training.todo_list;


import android.content.Context;

import com.training.todo_list.model.models.Todo;
import com.training.todo_list.utils.MyRecyclerViewAdapter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class AdapterTest {

    private static final String FAKE_STRING = "HELLO WORLD";

    @Mock
    private Context mMockContext;
    List<Todo> mTodoListData = new ArrayList<>();

    @Test
    public void testRecyclerAdapter() {

        String tDescription = "typeDescription";
        Date tDate = new Date();
        UUID tTypeId = UUID.randomUUID();
        UUID tId = UUID.randomUUID();
        boolean tIsDone = true;
        Todo mTodoItem = new Todo(tDescription, tDate, tTypeId, tIsDone, tId);
        mTodoListData.add(mTodoItem);

        MyRecyclerViewAdapter mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(mMockContext, mTodoListData);

        Assert.assertNotNull(mMyRecyclerViewAdapter);
        Assert.assertEquals(mMyRecyclerViewAdapter.getItemCount(), mTodoListData.size());
        Assert.assertEquals(mMyRecyclerViewAdapter.getItem(1), mTodoItem);


    }
}
