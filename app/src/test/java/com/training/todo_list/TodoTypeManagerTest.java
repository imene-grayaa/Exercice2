package com.training.todo_list;


import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.TodoType;


import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TodoTypeManagerTest {


    private TodoTypeManager mTodoTypeManager = new TodoTypeManager();

    @Test
    public void testGetAll() {

        List<TodoType> allTodoType = mTodoTypeManager.all();
        Assert.assertTrue(allTodoType.size() == 4);

    }

    @Test
    public void testAddEmpty() {

        int dataSetSize = mTodoTypeManager.all().size();
        TodoType newTodoType = mTodoTypeManager.create(null, null);
        Assert.assertNotNull(newTodoType);
        Assert.assertTrue(mTodoTypeManager.all().size() == dataSetSize + 1);
    }

    @Test
    public void testAddNewTodoType() {

        String tName = "typeName", tColor = "#258258";
        UUID tTypeId = UUID.randomUUID();
        boolean tIsDone = true;
        int dataSetSize = mTodoTypeManager.all().size();
        TodoType newTodoType = mTodoTypeManager.create(tName, tColor);
        Assert.assertNotNull(newTodoType);
        Assert.assertTrue(mTodoTypeManager.all().size() == dataSetSize + 1);
        Assert.assertEquals(newTodoType, new TodoType(tName, tColor, newTodoType.id()));
        Assert.assertEquals((mTodoTypeManager.todoTypeFor(newTodoType.id())).name(), tName);

    }

}
