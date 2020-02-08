package develop.tomo1139.diffutiltest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import develop.tomo1139.diffutiltest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    private val initialList = mutableListOf<CellData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (i in 1..100) {
            initialList.add(CellData("data $i-1", "data $i-2"))
        }
        binding.recyclerView.adapter = MainViewAdapter(initialList)

        binding.button.setOnClickListener {

            val list = mutableListOf<CellData>()
            for (i in 1..100) {
                if (i == 10) {
                    list.add(CellData("dataXXX $i-1", "dataXXX $i-2"))
                } else {
                    list.add(CellData("data $i-1", "data $i-2"))
                }
            }

            val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(initialList, list))
            binding.recyclerView.adapter?.let {
                (binding.recyclerView.adapter as MainViewAdapter).list = list
                diffResult.dispatchUpdatesTo(it)
            }
        }
    }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView1 = itemView.findViewById<TextView>(R.id.textView1)
    val textView2 = itemView.findViewById<TextView>(R.id.textView2)
}

class MainViewAdapter(
        var list: List<CellData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.cell_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.textView1).text = list[position].data1
        holder.itemView.findViewById<TextView>(R.id.textView2).text = list[position].data2
    }

    override fun getItemCount() = list.size
}

internal class DiffUtilCallback(
        private val oldItems: List<CellData>,
        private val newItems: List<CellData>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].data1 == newItems[newItemPosition].data1
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)
            = oldItems[oldItemPosition] == newItems[newItemPosition]
}