package com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.Realm;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.R;

import io.realm.Realm;
import io.realm.RealmResults;

// Класс фрагмента, который вызывается при выборе пунтка "база данных Realm" в NavigationDrawer
public class RealmListFragment extends ListFragment {

    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Получаем обьект Realm
        realm = Realm.getInstance(getActivity());
        // Делаем запрос на все элементы базы данных
        RealmResults<User> realmQuery = realm.where(User.class).findAll();

        // Проверяем размер размер результатов запроса, если равно 0,
        // то заполняем базу данных значениями
        if (realmQuery.size() == 0) {
            // Блок транзакций, который сам будет автоматически обрабатывать
            // состояний(начало, фиксация или отмена, если произошла ошибка)
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for (int i = 1; i < 20; i++) {
                        User user = realm.createObject(User.class);
                        user.setName("Person" + i);
                        user.setAge(20 + i);
                        user.setEmail("Person" + i + "@gmail.com");
                    }
                }
            });

        }
        // Создаем и присваиваем обработчик
        MyRealmAdapter myRealmAdapter = new MyRealmAdapter(getActivity(), realmQuery, true);
        setListAdapter(myRealmAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение при выходе
        realm.close();
    }
}
