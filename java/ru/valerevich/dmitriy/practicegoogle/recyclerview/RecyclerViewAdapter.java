package ru.valerevich.dmitriy.practicegoogle.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.valerevich.dmitriy.practicegoogle.R;

/**
 * Чтобы создать адаптер для RecyclerView, наследуемся от RecyclerView.Adapter. Этот адаптер
 * представляет шаблон проектирования viewholder, подразумевающий использование пользовательского
 * класса, который расширяет RecyclerView.ViewHolder. Эта паттерн сводит к минимуму количество
 * обращений к дорогостоящему в плане ресурсов методу findViewById.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NumberViewHolder> {

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    /*
    *   Количество ViewHolders, которые были созданы. Как правило, вы можете выяснить, сколько
    * необходимо определить, сколько элементов списка умещаются на экране сразу и добавить 2 до 4
    * в это число. Это не точная формула, но даст вам представление о том, сколько
    * ViewHolders были созданы для отображения любой RecyclerView.
    *
    *       ViewHolders на экране:
    *
    *        *-----------------------------*
    *        |         ViewHolder index: 0 |
    *        *-----------------------------*
    *        |         ViewHolder index: 1 |
    *        *-----------------------------*
    *        |         ViewHolder index: 2 |
    *        *-----------------------------*
    *        |         ViewHolder index: 3 |
    *        *-----------------------------*
    *        |         ViewHolder index: 4 |
    *        *-----------------------------*
    *        |         ViewHolder index: 5 |
    *        *-----------------------------*
    *        |         ViewHolder index: 6 |
    *        *-----------------------------*
    *        |         ViewHolder index: 7 |
    *        *-----------------------------*
    *
    *    Дополнительная ViewHolders (за экраном):
    *
    *        *-----------------------------*
    *        |         ViewHolder index: 8 |
    *        *-----------------------------*
    *        |         ViewHolder index: 9 |
    *        *-----------------------------*
    *        |         ViewHolder index: 10|
    *        *-----------------------------*
    *        |         ViewHolder index: 11|
    *        *-----------------------------*
    *
    *    Общее число ViewHolders = 11
    */
    private static int viewHolderCount;

    private int mNumberItems;

    final private ListItemClickListener mOnClickListener;

    /**
     * Интерфейс слушателя.
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickItemIndex);
    }

    /**
     * Конструктор для RecyclerViewAdapter, который принимает количество элементов для
     * отображения/
     */
    public RecyclerViewAdapter(int numberOfItems, ListItemClickListener listener) {
        mNumberItems = numberOfItems;

        //Слушатель нажатия на пункт
        mOnClickListener = listener;

        //При создании нового Adapter устанавливаем viewHolderCount = 0
        viewHolderCount = 0;
    }

    /**
     * Как следует из названия, этот метод вызывается, когда кастомный ViewHolder должен быть
     * инициализирован. Мы указываем макет для каждого элемента RecyclerView. Затем
     * LayoutInflater заполняет макет, и передает его в конструктор ViewHolder.
     * <p>
     * Возвращает объект ViewHolder, который будет хранить данные по одному объекту.
     */

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item_lesson03;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);


        // цвет фона
        int backgroundColorForViewHolder = ColorUtils
                .getViewHolderBackgroundColorFromInstance(context, viewHolderCount);

        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        viewHolderCount++;

        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);

        return viewHolder;
    }

    /**
     * Определим содержание каждого элемента из RecyclerView.Здесь должны установить
     * значения полей
     * <p>
     * Заменяет контент отдельного view (вызывается layout manager-ом)
     */
    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    /**
     * Он вернет количество элементов, присутствующих в данных.
     */
    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    /**
     * Класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
     * отдельного пункта списка
     * <p>
     * Виджет  заново использует уже созданные ViewHolder'ы и удаляет уже не используемые
     * (отсюда и название), что благоприятно сказывается на быстродействии и размере
     * используемой памяти.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Будет отображать позицию в списке, т. е. от 0 до getItemCount() - 1
        TextView listItemNumberView;

        TextView viewHolderIndex;

        /**
         * Внутри конструктора нашего кастомного ViewHolder, инициализируем View, входящие
         * в RecyclerView.
         */
        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);

            itemView.setOnClickListener(this);
        }

        /**
         * Если отдельный пункт вашего списка имеет несколько составных элементов
         * (т.е. не только TextView), то нужно добавить инициализацию этих элементов во
         * внутренний класс ViewHolder
         */
        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex));
        }

        /**
         * Вызывается каждый раз, когда пользователь нажимает на элемент в списке.
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

}
