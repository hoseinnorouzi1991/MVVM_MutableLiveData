package com.computertalk.mvvmjava.remote.user;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.computertalk.mvvmjava.model.User;
import com.computertalk.mvvmjava.remote.APIService;
import com.computertalk.mvvmjava.remote.RetroClass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersRepository {

    MutableLiveData<ArrayList<User>> mutableLiveData = new MutableLiveData<>();
    ArrayList<User> userArrayList = new ArrayList<>();
    Context context;

    public MutableLiveData<ArrayList<User>> getMutableLiveData() {
        return mutableLiveData;
    }

    public UsersRepository(Context context){

        this.context = context;
    }

    public void getUsers()
    {
        APIService apiService = RetroClass.getApiService();
        Call<UsersList> call = apiService.getUsersList("hosein");

        call.enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {

                for(int i=0;i<response.body().getArrayList().size(); i++)
                {
                    userArrayList.add(response.body().getArrayList().get(i));
                }
                mutableLiveData.setValue(userArrayList);
            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {

                Toast.makeText(context,t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
