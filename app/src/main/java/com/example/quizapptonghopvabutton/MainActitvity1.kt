package com.example.quizapptonghopvabutton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager

import com.example.quizapptonghopvabutton.Adapter.CategoryAdapter1
import com.example.quizapptonghopvabutton.DBhelper.DBHelperOther
import com.example.quizapptonghopvabutton.common.Common
import com.example.quizapptonghopvabutton.common.SplacesItemDecoration1
import kotlinx.android.synthetic.main.activity_detail_1.*

class MainActitvity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_1)

        recycler_detail_1.setHasFixedSize(true)
        recycler_detail_1.layoutManager = GridLayoutManager(this, 2)

        val adapter = CategoryAdapter1(this, DBHelperOther
            .getInstance(this)
            .allCategoryDetail1(Common.selectedCategory!!.id))

        recycler_detail_1.addItemDecoration(SplacesItemDecoration1(4))
        recycler_detail_1.adapter = adapter
    }

}