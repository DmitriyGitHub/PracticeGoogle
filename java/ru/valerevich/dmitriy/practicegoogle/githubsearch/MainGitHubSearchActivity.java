package ru.valerevich.dmitriy.practicegoogle.githubsearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import ru.valerevich.dmitriy.practicegoogle.R;
import ru.valerevich.dmitriy.practicegoogle.utilities.NetworkUtils;

public class MainGitHubSearchActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    //Для сохранения URL при пересоздании активити
    private static final String SEARCH_QUERY_URL_EXTRA = "query";
    //ID загрузчика
    private static final int GITHUB_SEARCH_LOADER = 22;

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;
    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_git_hub_search);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        if (savedInstanceState != null) {
            String queryUrl = savedInstanceState.getString(SEARCH_QUERY_URL_EXTRA);

            mUrlDisplayTextView.setText(queryUrl);
        }


        //Инициализация загрузчика (loader).
        getSupportLoaderManager().initLoader(GITHUB_SEARCH_LOADER, null, this);
    }

    /**
     *
     */
    private void makeGithubSearchQuery() {
        //Сброс поля
        mSearchResultsTextView.setText(" ");
        //Получение параметра поиска
        String githubQuery = mSearchBoxEditText.getText().toString();

        if (TextUtils.isEmpty(githubQuery)) {
            mUrlDisplayTextView.setText("Нечего искать =( ");
            return;
        }

        //Формирование URL
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        //Отображение URL В текстовом поле
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        //Создаем Bundle
        Bundle queryBundle = new Bundle();
        // Используем putString с SEARCH_QUERY_URL_EXTRA как ключ и Строковое значение URL-адреса в
        //качестве значение(Key - Value)
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, githubSearchUrl.toString());


        // Получаем LoaderManager
        LoaderManager loaderManager = getSupportLoaderManager();
        // Получаем Loader по ID
        Loader<String> githubSearchLoader = loaderManager.getLoader(GITHUB_SEARCH_LOADER);
        // Если загрузчик запускается в первый раз то создаем загрузку если нет то продалжаем загрузку
        if (githubSearchLoader == null) {
            loaderManager.initLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        }
    }

    /**
     * Показываем окно с информацией
     */
    private void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Показываем окно ошибки
     */
    private void showErrorMessage() {
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String queryUrl = mUrlDisplayTextView.getText().toString();

        outState.putString(SEARCH_QUERY_URL_EXTRA, queryUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_github, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        switch (itemThatWasClickedId) {
            case R.id.menu_search:
                makeGithubSearchQuery();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*-------------------- Начало методов  LoaderManager.LoaderCallbacks--------------------- */
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        // Возвращает анонимный внутренний клас
        return new AsyncTaskLoader<String>(this) {

            String mGithubJson;

            @Override
            protected void onStartLoading() {

                //If args is null, return.
                if (args == null) {
                    return;
                }

                //  Show the loading indicator
                mLoadingIndicator.setVisibility(View.VISIBLE);

                // COMPLETED (2) If mGithubJson is not null, deliver that result. Otherwise, force a load
                /*
                 * Если у нас уже есть закэшированные результаты, просто подставляем их. Если у нас
                 * нет никаких кэшированных результаты, начинаем нагрузку.
                 */
                if (mGithubJson != null) {
                    deliverResult(mGithubJson);
                } else {
                    forceLoad();
                }
            }
            
            @Override
            public String loadInBackground() {

                // COMPLETED (10) Get the String for our URL from the bundle passed to onCreateLoader
                /* Extract the search query from the args using our constant */
                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);

                // COMPLETED (11) If the URL is null or empty, return null
                /* If the user didn't enter anything, there's nothing to search for */
                if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
                    return null;
                }

                // COMPLETED (12) Copy the try / catch block from the AsyncTask's doInBackground method
                /* Parse the URL from the passed in String and perform the search */
                try {
                    URL githubUrl = new URL(searchQueryUrlString);
                    String githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubUrl);
                    return githubSearchResults;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            // COMPLETED (3) Override deliverResult and store the data in mGithubJson
            // COMPLETED (4) Call super.deliverResult after storing the data
            @Override
            public void deliverResult(String githubJson) {
                mGithubJson = githubJson;
                super.deliverResult(githubJson);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        // COMPLETED (14) Hide the loading indicator
        /* When we finish loading, we want to hide the loading indicator from the user. */
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        // COMPLETED (15) Use the same logic used in onPostExecute to show the data or the error message
        /*
         * If the results are null, we assume an error has occurred. There are much more robust
         * methods for checking errors, but we wanted to keep this particular example simple.
         */
        if (null == data) {
            showErrorMessage();
        } else {
            mSearchResultsTextView.setText(data);
            showJsonDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        /*
         * We aren't using this method in our example application, but we are required to Override
         * it to implement the LoaderCallbacks<String> interface
         */
    }

    /*-----------------Конец методов  LoaderManager.LoaderCallbacks --------------------------*/


}
