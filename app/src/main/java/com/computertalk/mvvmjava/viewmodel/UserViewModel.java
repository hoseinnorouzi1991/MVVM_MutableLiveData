package com.computertalk.mvvmjava.viewmodel;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.computertalk.mvvmjava.BR;
import com.computertalk.mvvmjava.model.User;
import com.computertalk.mvvmjava.remote.user.UsersRepository;
import com.computertalk.mvvmjava.view.adapter.UserAdapter;

import java.util.ArrayList;

public class UserViewModel extends BaseObservable {

    private ArrayList<UserViewModel> arrayList = new ArrayList<>();
    private String name;
    private String phone;

    private Context context;

    public UserViewModel(User user) {
        this.name = user.getName();
        this.phone = user.getPhone();
    }

    public UserViewModel(Context context) {

        this.context = context;

        //connect to server API

        UsersRepository repository = new UsersRepository(context);
        repository.getUsers();

        repository.getMutableLiveData().observe((LifecycleOwner) context, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                for(int i =0; i<users.size(); i++)
                {
                    UserViewModel userViewModel = new UserViewModel(users.get(i));
                    arrayList.add(userViewModel);
                }
                notifyPropertyChanged(BR.arrayList);
            }
        });
    }

    @BindingAdapter("bind:recyclerUser")
    public static void recyclerViewBinder(final RecyclerView recyclerView, ArrayList<UserViewModel> arrayList) {

                UserAdapter userAdapter = new UserAdapter(arrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL,false));
                recyclerView.setAdapter(userAdapter);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public ArrayList<UserViewModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<UserViewModel> arrayList) {
        this.arrayList = arrayList;
        notifyPropertyChanged(BR.arrayList);
    }
}
