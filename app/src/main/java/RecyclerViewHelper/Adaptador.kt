package RecyclerViewHelper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fer.huezo.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassProductos

class Adaptador(private var Datos: List<dataClassProductos>) : RecyclerView.Adapter<ViewHolder>()
{
    fun actualizarLista(newList: List<dataClassProductos>)
    {
        Datos = newList
        notifyDataSetChanged()
    }
    fun eliminarRegistro(nombreProducto: String, posicion: Int){

        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().CadenaConexion()
            val delProducto = objConexion?.prepareStatement("delete tbProductos where nombreProducto = ?")!!
            delProducto.setString(1, nombreProducto)
            delProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        notifyItemChanged(1)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }
    override fun getItemCount() = Datos.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val prod = Datos[position]
        holder.textView.text = prod.nombreProducto

        val item = Datos[position]
        holder.imgBorrar.setOnClickListener {
            eliminarRegistro(item.nombreProducto, position)
        }
        holder.imgEditar
    }
}