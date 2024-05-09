package fer.huezo.myapplication

import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassProductos

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val Nombre = findViewById<EditText>(R.id.txtNombre)
        val Precio = findViewById<EditText>(R.id.txtPrecio)
        val Cantidad = findViewById<EditText>(R.id.txtCantidad)
        val btn = findViewById<Button>(R.id.btnIngresar)

        fun limpiar()
        {
        Nombre.setText("")
        Precio.setText("")
        Cantidad.setText("")
        }

               //TODO/////////////////////////////////////////////////Ver productos/////
        btn.setOnClickListener {

            val rcvProductos = findViewById<RecyclerView>(R.id.rcvProductos)
            rcvProductos.layoutManager = LinearLayoutManager(this)

            fun obtenerDatos(): List<dataClassProductos>
            {
                val objConexion = ClaseConexion().CadenaConexion()
                val statement = objConexion?.createStatement()
                val resultset = statement?.executeQuery("select * from tbProductos")!!
                val productos = mutableListOf<dataClassProductos>()
                while (resultset.next())
                {
                    val nombre = resultset.getString("precio")
                    val producto = dataClassProductos(nombre)
                    productos.add(producto)
                }
                return productos
            }
            CoroutineScope(Dispatchers.IO).launch {
                val productosDB = obtenerDatos()
                withContext(Dispatchers.Main){
                    val miAdapter = Adaptador(productosDB)
                    rcvProductos.adapter = miAdapter
                }
            }

            //TODO/////////////////////////////////////////////////Guardar productos/////
            GlobalScope.launch(Dispatchers.IO){

                val claseConexion = ClaseConexion().CadenaConexion()

                val addProd = claseConexion?.prepareStatement("insert into tbProductos values(?,?,?)")!!
                addProd.setString(1, Nombre.text.toString())
                addProd.setInt(2, Precio.text.toString().toInt())
                addProd.setInt(3, Cantidad.text.toString().toInt())
                addProd.executeUpdate()

                val newProd = obtenerDatos()
                withContext(Dispatchers.Main){
                    (rcvProductos.adapter as? Adaptador)?.actualizarLista(newProd)
                }
            }
            //limpiar()



        }
    }
}