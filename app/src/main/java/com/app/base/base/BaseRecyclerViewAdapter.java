package com.app.base.base;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by j3p0n on 4/3/2017.
 */

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private int mModelLayout;
    private Class<VH> mViewHolderClass;
    private List<T> mList = new ArrayList<>();

    public BaseRecyclerViewAdapter(Class<VH> viewHolderClass, int modelLayout) {
        mModelLayout = modelLayout;
        mViewHolderClass = viewHolderClass;
    }

    public void setmList(List<T> mList) {
        this.mList = mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT) @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        try {
            Constructor<VH> constructor = mViewHolderClass.getConstructor(View.class);
            return constructor.newInstance(view);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
        T model = getItem(position);
        populateViewHolder(viewHolder, model, position);
    }

    @Override
    public int getItemViewType(int position) {
        return mModelLayout;
    }

    abstract protected void populateViewHolder(VH viewHolder, T model, int position);

    public void addItem(T t) {
        int index = mList.size();
        mList.add(index, t);
        notifyItemInserted(index);
    }

    public void updateItem(int position, T t) {
        mList.set(position, t);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void setItems(List<T> items) {
        mList.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }
}