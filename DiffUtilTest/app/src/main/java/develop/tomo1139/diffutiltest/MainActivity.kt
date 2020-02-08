package develop.tomo1139.diffutiltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import develop.tomo1139.diffutiltest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = listOf(CellData("first1", "first2"), CellData("second1", "second2"))
        binding.recyclerView.adapter = MainViewAdapter(list)
    }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView1 = itemView.findViewById<TextView>(R.id.textView1)
    val textView2 = itemView.findViewById<TextView>(R.id.textView2)
}

class MainViewAdapter(
        private val list: List<CellData>
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