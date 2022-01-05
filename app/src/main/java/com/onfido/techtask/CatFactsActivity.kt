package com.onfido.techtask

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.onfido.techtask.app.AppViewModelFactory

class CatFactsActivity : AppCompatActivity() {

    private lateinit var viewModel: CatFactsViewModel

    private lateinit var recycler: RecyclerView

    private lateinit var stateView: TextView

    private var adapter: CatFactsAdapter = CatFactsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initViews()

        val viewModelFactory = AppViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory)[CatFactsViewModel::class.java]
        viewModel.catFacts.observe(this, adapter::update)
        viewModel.loadingState.observe(this, this::setLoading)
        viewModel.errorState.observe(this, this::showError)
    }

    private fun initViews() {
        stateView = findViewById(R.id.state_text_view)
        recycler = findViewById(R.id.cat_facts_view)
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        getDrawable(R.drawable.cat_fact_list_divider)?.let { dividerItemDecoration.setDrawable(it) }
        recycler.addItemDecoration(dividerItemDecoration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val menuItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.onUserSearch(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.onUserSearch(query)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setLoading(loading: Boolean) {
        stateView.text = getString(R.string.state_loading)
        stateView.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun showError(resId: Int) {
        stateView.text = if (resId == -1) "" else getString(resId)
        stateView.visibility = if (resId == -1) View.GONE else View.VISIBLE
    }
}