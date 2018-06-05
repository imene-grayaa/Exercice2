package com.training.todo_list;


import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.models.Todo;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TodoManagerTest {

    private TodoManager mTodoManager = new TodoManager();

    @Test
    public void testGetAll() {

        List<Todo> allTodo = mTodoManager.all();
        Assert.assertTrue(allTodo.size() == 4);

    }

    @Test
    public void testAddEmpty() {

        int dataSetSize= mTodoManager.all().size();
        Todo newTodo = mTodoManager.create(null, null, null, false);
        Assert.assertNotNull(newTodo);
        Assert.assertTrue( mTodoManager.all().size()==dataSetSize+1);
    }

    @Test
    public void testAddNewTodo() {

        String tDescription="description";
        Date tDate=new Date();
        UUID tTypeId= UUID.randomUUID();
        boolean tIsDone=true;
        int dataSetSize= mTodoManager.all().size();
        Todo newTodo = mTodoManager.create(tDescription, tDate, tTypeId, tIsDone);
        Assert.assertNotNull(newTodo);
        Assert.assertTrue( mTodoManager.all().size()==dataSetSize+1);
        Assert.assertEquals(newTodo, new Todo(tDescription, tDate, tTypeId, tIsDone,newTodo.id()));
        Assert.assertEquals((mTodoManager.todoFor(newTodo.id())).description(),tDescription);

    }


}
