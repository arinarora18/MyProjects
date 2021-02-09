package com.example.todov2;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todov2.Database.TodoEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private static final String DATE_FORMAT = "dd/mm/yyyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    private List<TodoEntry> mTodoEntry;
    final private ItemClickListener onItemClickListener;
    private Context mContext;

    public TodoAdapter(ItemClickListener onItemClickListener, Context mContext) {
        this.onItemClickListener = onItemClickListener;
        this.mContext = mContext;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_layout, parent, false);
        return new TodoViewHolder(view);
    }

    public List<TodoEntry> getmTodoEntry(){
        return mTodoEntry;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        TodoEntry todoEntry = mTodoEntry.get(position);
        String description = todoEntry.getDescription();
        int priority = todoEntry.getPriority();
        String updatedAt = dateFormat.format(todoEntry.getUpdatedAt());
        String priorityString = "" + priority;

        holder.description.setText(description);
        holder.updatedAt.setText(updatedAt);
        holder.priority.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priority.getBackground();
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }

        public int getPriorityColor(int priority){
            int priorityColor = 0;

            switch (priority){
                case 1 : priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                        break;
                case 2 : priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                        break;
                case 3 :  priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                        break;
                default:
                        break;
            }

            return priorityColor;
        }
    @Override
    public int getItemCount() {
        if(mTodoEntry == null){
            return 0;
        }
        else return mTodoEntry.size();
    }

    public void setTodo(List<TodoEntry> todoEntry){
        mTodoEntry = todoEntry;
        notifyDataSetChanged();
    }


    public interface ItemClickListener{
        void onItemClickListener(int id);
    }


    class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView description ;
        TextView updatedAt;
        TextView priority;

        public TodoViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            updatedAt = itemView.findViewById(R.id.updatedAt);
            priority = itemView.findViewById(R.id.priority);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId = mTodoEntry.get(getAdapterPosition()).getId();
            onItemClickListener.onItemClickListener(elementId);
        }
    }
}
