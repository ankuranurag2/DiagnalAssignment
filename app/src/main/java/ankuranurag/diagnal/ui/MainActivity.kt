package ankuranurag.diagnal.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import ankuranurag.diagnal.R
import ankuranurag.diagnal.adapter.MovieAdapter
import ankuranurag.diagnal.databinding.ActivityMainBinding
import ankuranurag.diagnal.utils.RecyclerEventListener
import ankuranurag.diagnal.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),RecyclerEventListener {

    private lateinit var activityBinding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    private val layoutManager by lazy {
        GridLayoutManager(this@MainActivity, 3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.toolbar_search)
        val searchView = (search?.actionView as? SearchView)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if ((newText?.length ?: 0) > 3)
                    handleSearch(newText!!)
                return true
            }
        })

        return true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> layoutManager.spanCount = 3
            Configuration.ORIENTATION_LANDSCAPE -> layoutManager.spanCount = 7
        }
    }

    override fun onBottomReached() {
        viewModel.fetchMovieList()
    }

    private fun observeData() {
        val adapter = MovieAdapter(this)
        activityBinding.moviesRv.let {
            it.adapter = adapter
            it.layoutManager = layoutManager
        }

        viewModel.movieList.observe(this, Observer {
            it?.let {
                with(activityBinding) {
                    if (it.size > 0) {
                        moviesRv.visibility = View.VISIBLE
                        errorTv.visibility = View.GONE
                        adapter.submitList(it.toMutableList())
                    } else {
                        moviesRv.visibility = View.GONE
                        errorTv.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.title.observe(this, Observer {
            supportActionBar?.title=it
        })
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
    }

    private fun handleSearch(query: String) {

    }
}