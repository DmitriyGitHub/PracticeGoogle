package ru.valerevich.dmitriy.practicegoogle.DataBinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ru.valerevich.dmitriy.practicegoogle.DataBinding.model.User;
import ru.valerevich.dmitriy.practicegoogle.R;

public class MainActivityDataBinding extends AppCompatActivity{

    private RecyclerViewAdapter mAdapter;
    private RecyclerView mNumbersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lesson03);

        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);

         /*
         *  Менеджер компоновки для управления позиционированием своих элементов.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);

        /*
         *  Если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
         *  передаем параметр true - это увеличивает производительность
         */
        mNumbersList.setHasFixedSize(true);

        createAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lesson3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.action_refresh:
                createAdapter();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createAdapter(){
        mAdapter = new RecyclerViewAdapter(getData(10));
        mNumbersList.setAdapter(mAdapter);
    }

    private ArrayList<User> getData(int count){
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++){
            User user = new User("Name " + i, 155 + (int)(Math.random() * 4));
            users.add(user);
        }
        return users;
    }
}