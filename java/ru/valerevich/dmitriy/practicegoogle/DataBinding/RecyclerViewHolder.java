package ru.valerevich.dmitriy.practicegoogle.databinding;

import android.support.v7.widget.RecyclerView;

import ru.valerevich.dmitriy.practicegoogle.databinding.model.User;

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
