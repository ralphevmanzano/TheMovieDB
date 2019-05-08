package propnex.com.seraphic.ideahub.core.module.chope;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import propnex.com.seraphic.ideahub.core.module.chope.utils.OnItemClickListener;

public abstract class BaseAdapter<T> extends ListAdapter<T, BaseViewHolder<T>> {

    protected OnItemClickListener onItemClickListener;
    protected ViewDataBinding binding;

    protected BaseAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        return new BaseViewHolder<>(binding, onItemClickListener);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        if (listener != null)
            this.onItemClickListener = listener;
    }

    public abstract void bind(ViewDataBinding binding, int position);

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        bind(holder.binding, holder.getAdapterPosition());
    }

    @Override
    public abstract int getItemViewType(int position);
}
