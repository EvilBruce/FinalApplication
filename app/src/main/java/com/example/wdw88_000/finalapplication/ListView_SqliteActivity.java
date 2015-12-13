package com.example.wdw88_000.finalapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ListView_SqliteActivity extends Activity {

    SQLiteDatabase mDb;
    SQLiteDatabaseDao dao;

    ArrayList<HashMap<String, Object>> listData;

    SimpleAdapter listItemAdapter;

    private ArrayList<String> mList;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view__sqlite);
        dao = new SQLiteDatabaseDao();

        ListView list = (ListView) findViewById(R.id.list_items);
        listItemAdapter = new SimpleAdapter(ListView_SqliteActivity.this,
                listData,
                R.layout.item,

                new String[] { "image", "username", "birthday" },

                new int[] { R.id.image, R.id.username, R.id.birthday });

        list.setAdapter(listItemAdapter);
        list.setOnCreateContextMenuListener(listviewLongPress);
    }

    class SQLiteDatabaseDao {

        public SQLiteDatabaseDao() {
            mDb = openOrCreateDatabase("users.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

            createTable(mDb, "student");
            insert(mDb, "student");
            getAllData("student");
        }

        public void createTable(SQLiteDatabase mDb, String table) {
            try {
                mDb.execSQL("create table if not exists "
                        + table
                        + " (id integer primary key autoincrement, "
                        + "username text not null, birthday text not null,image text);");
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "create database failed",
                        Toast.LENGTH_LONG).show();
            }
        }


        public void insert(SQLiteDatabase mDb, String table) {

            String[] usersname =getResources().getStringArray(R.array.names);
            mList = new ArrayList();
            mList.add("Birthday:6-18");
            mList.add("Birthday:8-22");
            mList.add("Birthday:10-30");
            mList.add("Birthday:6-12");
            mList.add("Birthday:9-2");
            mList.add("Birthday:10-1");
            mList.add("Birthday:1-15");
            mList.add("Birthday:4-26");
            mList.add("Birthday:3-9");
            mList.add("Birthday:10-25");


            ContentValues values = new ContentValues();
            values.put("username", usersname[0]);
            values.put("birthday", mList.get(0));
            values.put("image", R.drawable.o);
            mDb.insert(table, null, values);

            values.put("username", usersname[1]);
            values.put("birthday", mList.get(1));
            values.put("image", R.drawable.t);
            mDb.insert(table, null, values);

            values.put("username", usersname[2]);
            values.put("birthday", mList.get(2));
            values.put("image", R.drawable.f);
            mDb.insert(table, null, values);

            values.put("username", usersname[3]);
            values.put("birthday", mList.get(3));
            values.put("image", R.drawable.o);
            mDb.insert(table, null, values);

            values.put("username", usersname[4]);
            values.put("birthday", mList.get(4));
            values.put("image", R.drawable.t);
            mDb.insert(table, null, values);

            values.put("username", usersname[5]);
            values.put("birthday", mList.get(5));
            values.put("image", R.drawable.o);
            mDb.insert(table, null, values);

            values.put("username", usersname[6]);
            values.put("birthday", mList.get(6));
            values.put("image", R.drawable.o);
            mDb.insert(table, null, values);

            values.put("username", usersname[7]);
            values.put("birthday", mList.get(7));
            values.put("image", R.drawable.t);
            mDb.insert(table, null, values);

            values.put("username", usersname[8]);
            values.put("birthday", mList.get(8));
            values.put("image", R.drawable.o);
            mDb.insert(table, null, values);

        }


        public void getAllData(String table) {
            Cursor c = mDb.rawQuery("select * from " + table, null);
            int columnsSize = c.getColumnCount();
            listData = new ArrayList<HashMap<String, Object>>();
            // 获取表的内容
            while (c.moveToNext()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < columnsSize; i++) {
                    map.put("id", c.getString(0));
                    map.put("username", c.getString(1));
                    map.put("birthday", c.getString(2));
                    map.put("image", c.getString(3));
                }
                listData.add(map);
            }
        }

        public boolean delete(SQLiteDatabase mDb, String table, int id) {
            String whereClause = "id=?";
            String[] whereArgs = new String[] { String.valueOf(id) };
            try {
                mDb.delete(table, whereClause, whereArgs);
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "delete failed",
                        Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
    }
    // long press to delete
    OnCreateContextMenuListener listviewLongPress = new OnCreateContextMenuListener(){
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenuInfo menuInfo) {
            // TODO Auto-generated method stub
            final AdapterView.AdapterContextMenuInfo info =
                    (AdapterView.AdapterContextMenuInfo) menuInfo;
            new AlertDialog.Builder(ListView_SqliteActivity.this)

                    .setTitle("del current data")

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage("sure")
                    .setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {

                                    int mListPos = info.position;

                                    HashMap<String, Object> map = listData
                                            .get(mListPos);

                                    int id = Integer.valueOf((map.get("id")
                                            .toString()));

                                    if (dao.delete(mDb, "student", id)) {

                                        listData.remove(mListPos);
                                        listItemAdapter.notifyDataSetChanged();
                                    }
                                }
                            })
                    .setNegativeButton("no",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {

                                }
                            }).show();
        }
    };
    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        mDb.close();
    }
}