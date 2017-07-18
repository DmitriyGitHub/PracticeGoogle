package ru.valerevich.dmitriy.practicegoogle.lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ru.valerevich.dmitriy.practicegoogle.R;

public class LifeCycleActivity extends AppCompatActivity {

    /*
     * Этот тег будет использоваться для входа. Рекомендуется использовать имя класса, используя
     * getSimpleName.
     */
    private static final String TAG = LifeCycleActivity.class.getSimpleName();

    /*
     *  Эта константа будет использоваться для хранения контента в поле textview. Будет отображать
     * список обратного вызовова.
     */
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

    /* Константы этапов жизненного цикла*/
    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";

    /*
     * Отображает жизненный цикл
     */
    private TextView mLifecycleDisplay;

    /*
     *Список этапов
     */
    private static final ArrayList<String> mLifecycleCallbacks = new ArrayList<>();

    /**
     * Вызывается при создании или перезапуска активности. Система может запускать и останавливать
     * текущие окна в зависимости от происходящих событий. Внутри данного метода настраивают
     * статический интерфейс активности. Инициализирует статические данные активности, связывают
     * данные со списками и т.д. Связывает с необходимыми данными и ресурсами. Задаёт внешний вид
     * через метод setContentView().
     *
     * В этом методе загружайте пользовательский интерфейс, размещайте ссылки на свойства класса,
     * связывайте данные с элементами управления, создавайте сервисы и потоки. Метод onCreate()
     * принимает объект Bundle, содержащий состояние пользовательского интерфейса, сохранённое в
     * последнем вызове обработчика onSaveInstanceState. Для восстановления графического интерфейса
     * в его предыдущем состоянии нужно задействовать эту переменную: внутри onCreate() или
     * переопределив метод onRestoreInstanceState().
     *
     * Операции по инициализации, занимающие много времени, следует выполнять в фоновом процессе,
     * а не с помощью метода onCreate(). В противном случае можно получить диалоговое окно ANR
     * (Application Not Responding, приложение не отвечает).
     *
     * В методе можно сделать проверку, запущено ли приложение впервые или восстановлено из памяти.
     * Если значение переменной savedInstanceState будет null, приложение запускается первый раз.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);

        mLifecycleDisplay = (TextView) findViewById(R.id.tv_lifecycle_events_display);

        /*
         * Если savedInstanceState не NULL, это означает, что Activity запущенна первый раз
         */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)) {
                // Поле LIFECYCLE_CALLBACKS_TEXT_KEY не пустое то извлек данные и заполняем TextView
                String allPreviousLifecycleCallbacks = savedInstanceState
                        .getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
                mLifecycleDisplay.setText(allPreviousLifecycleCallbacks);
            }
        }

        /*
         *Выводим снизу вверх то что находится в массиве
         */
        for (int i = mLifecycleCallbacks.size() - 1; i >= 0; i--) {
            mLifecycleDisplay.append(mLifecycleCallbacks.get(i) + "\n");
        }

        mLifecycleCallbacks.clear();

        logAndAppend(ON_CREATE);
    }

    /**
     * Вызывается, когда активность становится видимой пользователю.
     * При вызове onStart() окно еще не видно пользователю, но вскоре будет видно.
     */
    @Override
    protected void onStart() {
        super.onStart();

        logAndAppend(ON_START);
    }

    /**
     * Вызывается, когда активность начнет взаимодействовать с пользователем.
     * Таким образом, вы должны реализовать onResume() для инициализации компонентов, регистрации
     * любых широковещательных приемников или других процессов, которые вы освободили/приостановили
     * в onPause() и выполнять любые другие инициализации, которые должны происходить, когда
     * активность вновь активна.
     *
     * Нужно размещать относительно быстрый и легковесный код, чтобы ваше приложение оставалось
     * отзывчивым при скрытии с экрана или выходе на передний план.
     */
    @Override
    protected void onResume() {
        super.onResume();

        logAndAppend(ON_RESUME);
    }

    /**
     * Вызывается перед тем, как будет показано другое Activity.
     *
     * Когда пользователь решает перейти к работе с новым окном, система вызовет для прерываемого
     * окна метод onPause().
     *  - Сохраняет незафиксированные данные.
     *  - Деактивирует и выпускает монопольные ресурсы.
     *  - Останавливает воспроизведение видео, аудио и анимацию.
     *
     *  В этом методе необходимо остановить анимацию и другие действия, которые загружают процессор.
     * Зафиксировать несохранённые данные, например, черновик письма, потому как после его
     * выполнения работа активности может прерваться без предупреждения. Освободить системные
     * ресурсы, например, обработку данных от GPS.
     *
     * Размещение относительно быстрого и легковесного кода, чтобы приложение оставалось отзывчивым
     * при скрытии с экрана или выходе на передний план.
     *
     * Исходя из архитектуры приложения, также можно приостановить выполнение потоков, процессов
     * или широковещательных приемников, пока активность не появится на переднем плане.
     */
    @Override
    protected void onPause() {
        super.onPause();

        logAndAppend(ON_PAUSE);
    }

    /**
     * вызывается, когда окно становится невидимым для пользователя. Это может произойти при её
     * уничтожении, или если была запущена другая активность (существующая или новая), перекрывшая
     * окно текущей активности.
     */
    @Override
    protected void onStop() {
        super.onStop();

        // COMPLETED (2) Add the ON_STOP String to the front of mLifecycleCallbacks
        /*
         * Since any updates to the UI we make after onSaveInstanceState (onStop, onDestroy, etc),
         * we use an ArrayList to track if these lifecycle events had occurred. If any of them have
         * occurred, we append their respective name to the TextView.
         */
        mLifecycleCallbacks.add(0, ON_STOP);

        logAndAppend(ON_STOP);
    }

    /**
     *
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        logAndAppend(ON_RESTART);
    }

    /**
     * Вызывается перед тем, как Activity будет уничтожено.
     * Метод вызывается по окончании работы активности, при вызове метода finish() или в случае,
     * когда система уничтожает этот экземпляр активности для освобождения ресурсов. Эти два
     * сценария уничтожения можно определить вызовом метода isFinishing(). Вызывается перед
     * уничтожением активности. Это последний запрос, который получает активность от системы.
     * Если определённое окно находится в верхней позиции в стеке, но невидимо пользователю и
     * система решает завершить это окно, вызывается метод onDestroy(). В этом случае метод удаляет
     * все статические данные активности. Отдаёт все используемые ресурсы.
     *
     * Так как все необходимые операции по освобождению ресурсов вы сделали в методе onStop(), то в
     * этом методе вы можете подстраховаться и проверить ещё раз все неосвобождённые ресурсы.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        mLifecycleCallbacks.add(0, ON_DESTROY);

        logAndAppend(ON_DESTROY);
    }

    /**
     * Когда система завершает активность в принудительном порядке, чтобы освободить ресурсы для
     * других приложений, пользователь может снова вызвать эту активность с сохранённым предыдущим
     * состоянием. Чтобы зафиксировать состояние активности перед её уничтожением, в классе
     * активности необходимо реализовать метод onSaveinstancestate().
     *
     * Сам метод вызывается прямо перед методом onPause(). Он предоставляет возможность сохранять
     * состояние пользовательского интерфейса активности в объект Bundle, который потом будет
     * передаваться в методы onCreate() и onRestoreInstanceState(). В объект Bundle можно записать
     * параметры, динамическое состояние активности как пары "ключ-значение".
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logAndAppend(ON_SAVE_INSTANCE_STATE);
        String lifecycleDisplayTextViewContents = mLifecycleDisplay.getText().toString();
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY, lifecycleDisplayTextViewContents);
    }

    /**
     * Показывает данные о Жизненном цикле
     */
    private void logAndAppend(String lifecycleEvent) {
        Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);

        mLifecycleDisplay.append(lifecycleEvent + "\n");
    }

    /**
     * Этот метод сбрасывает содержимое textview
     */
    public void resetLifecycleDisplay(View view) {
        mLifecycleDisplay.setText("Lifecycle callbacks:\n");
    }
}