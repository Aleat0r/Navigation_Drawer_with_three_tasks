package com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview;

import android.os.Bundle;
import android.app.Fragment;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.Realm.RealmListFragment;
import com.kovalenko.aleksandr.aleat0r.navigationdrawercamerarealmcustomimageview.customImageView.CustomImageViewFragment;

public class MainActivity extends ActionBarActivity {

    public static final String LOG_TAG = "myLogs";

    private DrawerLayout DrawerLayout;
    private ListView DrawerList;
    private ActionBarDrawerToggle DrawerToggle;

    // Заголовок для NavigationDrawer
    private CharSequence DrawerTitle;
    // Название приложения/открытого экрана
    private CharSequence appTitle;
    // Массив названий пуктов меню NavigationDrawer
    private String[] viewsNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Инициализируем нужные переменные, получаем доступ к элементов экрана
        appTitle = getTitle();
        DrawerTitle = getResources().getString(R.string.menu);
        viewsNames = getResources().getStringArray(R.array.views_array);
        DrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerList = (ListView) findViewById(R.id.left_drawer);

        //Задаем список пунктов меню для NavigationDrawer с помощью ArrayAdapter
        DrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, viewsNames));

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        // Теперь значек в ActionBar будет вести себя как кнопка-переключатель
        actionBar.setDisplayHomeAsUpEnabled(true);

        DrawerToggle = new ActionBarDrawerToggle(this, DrawerLayout,
                R.string.open_menu,R.string.close_menu) {

            public void onDrawerClosed(View view) {
                // устанавливаем название при закрытом NavigationDrawer
                getSupportActionBar().setTitle(appTitle);
                // Вызов onPrepareOptionsMenu(), чтобы показать значек меню в ActionBar
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                // устанавливаем название при открытом NavigationDrawer
                getSupportActionBar().setTitle(DrawerTitle);
                // Вызов onPrepareOptionsMenu(), чтобы спрятать значек меню в ActionBar
                invalidateOptionsMenu();
            }
        };
        DrawerLayout.setDrawerListener(DrawerToggle);

        // При первой загрузке приложения задаем номер отображаемого фрагмента,
        // т.к. пользователь еще ничего не успел выбрать
        if (savedInstanceState == null) {
            displayView(0);
        }
        // Присваиваем обработчик для списка пунктов в NavigationDrawer
        DrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }
    // Обработчик клика по пунктам меню NavigationDrawer
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             displayView(position);
        }
    }

    // Метод получает id кликнутого пункта, а далее блок switch определяет,
    // каким именно фрагментом нужно заполнить FrameLayout
    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
//                fragment = new FirstFragment();
                break;
            case 1:
                fragment = new RealmListFragment();
                break;
            case 2:
                fragment = new CustomImageViewFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            // Обновляем выбранный пункт и название, затем закрываем drawer
            DrawerList.setItemChecked(position, true);
            setTitle(viewsNames[position]);
            DrawerLayout.closeDrawer(DrawerList);

        } else {
            Log.e(LOG_TAG, "Ошибка при создании фрагмента");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting actionBar app icon/title
        if (DrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Если NavigationDrawer открыт, скрываем значек меню
        boolean drawerOpen = DrawerLayout.isDrawerOpen(DrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    // Переопределяем метод setTitle, чтобы значение названия в ActionBar изменилось
    // с названия приложения на название выбраного пунтка в NavigationDrawer
    @Override
    public void setTitle(CharSequence title) {
        appTitle = title;
        getSupportActionBar().setTitle(appTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        DrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        DrawerToggle.onConfigurationChanged(newConfig);
    }
}
