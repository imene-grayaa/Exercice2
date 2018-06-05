package com.training.todo_list.utils;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Todo> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mcContext;


    public MyRecyclerViewAdapter(Context pContext, List<Todo> pData) {
        mInflater = LayoutInflater.from(pContext);
        mData = pData;
        mcContext = pContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.todo_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo mTodoItem = mData.get(position);
        holder.mDescription.setText(mTodoItem.description());

        Date tDate = mTodoItem.timeCreation();

        SimpleDateFormat tFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.FRANCE);
        holder.mTodoDate.setText(tFormat.format(tDate));

        TodoTypeManager mtTodoTypeManager = new TodoTypeManager();
        TodoType tTodoType = mtTodoTypeManager.todoTypeFor(mTodoItem.idTodoType());
        holder.mToDoType.setText(tTodoType.name());
        holder.mToDoType.setTextColor(Color.parseColor(tTodoType.color()));

        if (mTodoItem.isDone())
            holder.mStatus.setImageResource(R.drawable.ic_done);
        else
            holder.mStatus.setImageResource(R.drawable.ic_todo);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mDescription, mToDoType, mTodoDate;
        ImageView mStatus;


        ViewHolder(View itemView) {
            super(itemView);
            mDescription = itemView.findViewById(R.id.description_text_view);
            mToDoType = itemView.findViewById(R.id.todo_type_text);
            mTodoDate = itemView.findViewById(R.id.date_todo_text);

            mStatus = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Todo getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
