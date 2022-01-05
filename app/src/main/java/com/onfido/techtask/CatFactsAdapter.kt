package com.onfido.techtask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onfido.techtask.cat.data.model.CAT_FACT_DATE_FORMAT
import com.onfido.techtask.cat.data.model.CatFact
import com.onfido.techtask.cat.data.model.isNew
import com.onfido.techtask.cat.data.model.toMillis

class CatFactsAdapter : RecyclerView.Adapter<CatFactsAdapter.ViewHolder>() {

    private var facts = listOf<CatFact>()

    fun update(newFacts: List<CatFact>) {
        val callback = CatFactsCallback(facts, newFacts)
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(this)
        facts = newFacts
    }

    override fun getItemCount(): Int {
        return facts.size
    }

    private fun getItem(position: Int): CatFact = facts[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.cat_fact_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val descriptionView: TextView =
            itemView.findViewById(R.id.cat_fact_item_description_view)
        private val isVerifiedView: ImageView =
            itemView.findViewById(R.id.cat_fact_item_is_verified_view)
        private val isNewView: ImageView = itemView.findViewById(R.id.cat_fact_item_is_new_view)

        fun bind(catFact: CatFact) {
            descriptionView.text = catFact.text
            isVerifiedView.visibility =
                if (catFact.status.verified) View.VISIBLE else View.GONE
            isNewView.visibility =
                if (catFact.isNew(
                        nowMillis = System.currentTimeMillis(),
                        createdAtMillis = toMillis(catFact.createdAt, CAT_FACT_DATE_FORMAT)
                    )
                ) View.VISIBLE else View.GONE
        }
    }
}