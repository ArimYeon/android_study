package com.example.roomkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "todo-db")
                .build()

        // LiveData
        db.todoDao().getAll().observe(this, Observer {
            result_text.text = it.toString()
        })

        add_button.setOnClickListener {
            // 코루틴 비동기 처리
            lifecycleScope.launch(Dispatchers.IO) {
                db.todoDao().insert(Todo(todo_edit.text.toString()))
            }
        }
    }
}