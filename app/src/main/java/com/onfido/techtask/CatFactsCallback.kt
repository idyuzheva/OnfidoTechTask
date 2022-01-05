package com.onfido.techtask

import androidx.recyclerview.widget.DiffUtil
import com.onfido.techtask.cat.data.model.CatFact

class CatFactsCallback(
    private val oldCatFacts: List<CatFact>,
    private val newCatFacts: List<CatFact>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldCatFacts.size

    override fun getNewListSize(): Int = newCatFacts.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCatFacts[oldItemPosition].text == newCatFacts[newItemPosition].text
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCatFacts[oldItemPosition] == newCatFacts[newItemPosition]
    }
}