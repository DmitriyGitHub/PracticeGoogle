package ru.valerevich.dmitriy.practicegoogle.DataBinding;

import android.support.v7.widget.RecyclerView;

import ru.valerevich.dmitriy.practicegoogle.DataBinding.model.User;
import ru.valerevich.dmitriy.practicegoogle.databinding.ListItemDataBindBinding;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private ListItemDataBindBinding mBinding;

    public RecyclerViewHolder(ListItemDataBindBinding binding) {
        super(binding.getRoot());
        mBinding = binding;

    }

    public void bind(User user){
        mBinding.setUser(user);
        mBinding.executePendingBindings();
    }
}
