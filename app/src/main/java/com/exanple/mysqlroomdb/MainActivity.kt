package com.exanple.mysqlroomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exanple.mysqlroomdb.data.Product
import com.exanple.mysqlroomdb.data.ProductAdapter
import com.exanple.mysqlroomdb.data.ProductDB
import com.exanple.mysqlroomdb.data.ProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var dao : ProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dao = ProductDB.getInstance(application).productDao

        val btnInsert :Button = findViewById(R.id.btnInsert)
        btnInsert.setOnClickListener(){

            val name :String  = findViewById<TextView>(R.id.tfName).text.toString()
            val price:Double =  findViewById<TextView>(R.id.tfPrice).text.toString().toDouble()

            val p = Product(0, name, price)

            CoroutineScope(IO).launch {
                dao.insertProduct(p)
            }

        }


        val btnGet :Button=findViewById(R.id.btnGet)
        btnGet.setOnClickListener{

            CoroutineScope(IO).launch {
                var productName = ""
                val productList: List<Product> = dao.getAll()
                //val productList=dao.getPriceLessThan(3000.00)


//                for (p: Product in productList) {
//                    productName += p.name + "\n"
//                }

                CoroutineScope(Main).launch {
                //val tvResult: TextView = findViewById(R.id.tvResult)
                //tvResult.text = productName
                    val myAdapter=ProductAdapter(productList)
                    val myRecyclerView: RecyclerView =findViewById(R.id.rvProduct)
                    myRecyclerView.adapter=myAdapter
                    myRecyclerView.setHasFixedSize(true)

            }

            }


        }





    }
}