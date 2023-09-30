package com.ianindratama.searchgithubuserapp

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ianindratama.searchgithubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Deklarasi ViewModel
    private lateinit var viewModel: MainViewModel

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(this@MainActivity.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {

                // Memanggil metode findSearchUser milik ViewModel
                viewModel.findSearchUser(query)
                searchView.clearFocus()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        binding.rvSearchList.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvSearchList.addItemDecoration(itemDecoration)

        // Observe LiveData "isLoading"
        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        // Observe LiveData "listOfUser"
        viewModel.listOfUser.observe(this){
            if (it != null){
                setSearchResultData(it)
            }
        }

        // Observe LiveData "errorMessage"
        viewModel.errorMessage.observe(this){
            if (it != null){
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setSearchResultData(items: List<GithubUserData>) {

        if (binding.ivHomeFragmentSearchImage.visibility == View.VISIBLE){

            binding.ivHomeFragmentSearchImage.visibility = View.GONE
            binding.tvHomeFragmentTitle.visibility = View.GONE
            binding.tvHomeFragmentSubtitle.visibility = View.GONE

        }

        if (items.isEmpty()){

            binding.rvSearchList.visibility = View.GONE

            binding.SearchFragmentNoDataImage.visibility = View.VISIBLE
            binding.SearchFragmentNoDataTitle.visibility = View.VISIBLE
            binding.SearchFragmentNoDataSubtitle.visibility = View.VISIBLE

        }else{

            binding.rvSearchList.visibility = View.VISIBLE

            binding.SearchFragmentNoDataImage.visibility = View.GONE
            binding.SearchFragmentNoDataTitle.visibility = View.GONE
            binding.SearchFragmentNoDataSubtitle.visibility = View.GONE

            val adapter = SearchDataAdapter(items)
            binding.rvSearchList.adapter = adapter

        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

}