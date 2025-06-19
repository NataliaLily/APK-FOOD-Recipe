package com.example.foodapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAdapters()
        setupRecyclerViews()
        setupObservers()
        setupSearchButton()

        viewModel.getCategories()
        viewModel.getMealsByCategory("Beef")
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MainViewModel::class.java]
    }

    private fun setupAdapters() {
        categoryAdapter = CategoryAdapter()
        mealAdapter = MealAdapter()


        categoryAdapter.setOnItemClickListener { category ->
            viewModel.getMealsByCategory(category.strCategory)
        }

        mealAdapter.setOnItemClickListener { meal ->
            val detailFragment = DetailFragment()
            val bundle = Bundle()
            bundle.putString("mealId", meal.idMeal)
            detailFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupRecyclerViews() {
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = categoryAdapter

        binding.rvMeals.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMeals.adapter = mealAdapter
    }

    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.categories = categories
        }

        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            mealAdapter.meals = meals
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSearchButton() {
        binding.btnSearch.setOnClickListener {
            val keyword = binding.etSearch.text.toString().trim()
            if (keyword.isNotEmpty()) {
                viewModel.searchMeals(keyword)
            } else {
                Toast.makeText(requireContext(), "masukkan kata kunci", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
