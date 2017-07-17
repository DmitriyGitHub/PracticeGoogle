package ru.valerevich.dmitriy.practicegoogle.DataBinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.valerevich.dmitriy.practicegoogle.DataBinding.model.User;
import ru.valerevich.dmitriy.practicegoogle.R;
import ru.valerevich.dmitriy.practicegoogle.databinding.ListItemDataBindBinding;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context mContext;
    private List<User> mUsers = new ArrayList<>();
    private LayoutInflater mInflater;

    public RecyclerViewAdapter(@NonNull List<User> users) {
        mUsers.addAll(users);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item_data_bind;
        mInflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        ListItemDataBindBinding binding = DataBindingUtil.inflate(mInflater,layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);


        RecyclerViewHolder viewHolder = new RecyclerViewHolder(binding);


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.bind(user);

    }

    /**
     * Он вернет количество элементов, присутствующих в данных.
     */
    @Override
    public int getItemCount() {
        return mUsers.size();
    }

}