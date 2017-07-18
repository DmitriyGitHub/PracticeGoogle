package ru.valerevich.dmitriy.practicegoogle.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ru.valerevich.dmitriy.practicegoogle.R;

public class MainActivityLessonThree extends AppCompatActivity implements RecyclerViewAdapter.ListItemClickListener{

    private static final int NUM_LIST_ITEMS = 500;


    private RecyclerViewAdapter mAdapter;
    private RecyclerView mNumbersList;
    private Toast mToast;

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

        mAdapter = new RecyclerViewAdapter(NUM_LIST_ITEMS, this);
        mNumbersList.setAdapter(mAdapter);
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
                mAdapter = new RecyclerViewAdapter(NUM_LIST_ITEMS, this);
                mNumbersList.setAdapter(mAdapter);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickItemIndex) {

        if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = "Item #" + clickItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }
}
