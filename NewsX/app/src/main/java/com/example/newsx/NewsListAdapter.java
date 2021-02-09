package com.example.newsx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    public ArrayList<News> itemList = new ArrayList<>();
    private NewsItemClicked Alistener;


    public NewsListAdapter(NewsItemClicked listener,ArrayList<News> newsList){
        this.Alistener = listener;
        this.itemList.addAll(newsList);

    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        final NewsViewHolder viewHolder = new NewsViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Alistener.onItemClicked(itemList.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News CurrentItem = itemList.get(position);
        TextView title = holder.titleView.findViewById(R.id.title);
        title.setText(CurrentItem.title);
        holder.author.setText(CurrentItem.author);
        Glide.with(holder.itemView.getContext()).load(CurrentItem.imgUrl).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateNewsItems(ArrayList<News> updatedItems){
        itemList.clear();
        itemList.addAll(updatedItems);

        notifyDataSetChanged();
    }
}



class NewsViewHolder extends RecyclerView.ViewHolder{
    TextView titleView ;
    ImageView image;
    TextView author;

    public NewsViewHolder( View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.title);
        image = itemView.findViewById(R.id.image);
        author = itemView.findViewById(R.id.author);
    }
}
interface NewsItemClicked{
    void onItemClicked(News item);
}
