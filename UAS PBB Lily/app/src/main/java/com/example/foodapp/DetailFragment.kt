package com.example.foodapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.FragmentDetailBinding
import com.example.foodapp.model.MealDetail

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObservers()

        // Ambil mealId dari Bundle (bukan navArgs)
        val mealId = arguments?.getString("mealId")
        if (mealId != null) {
            viewModel.getMealDetail(mealId)
        } else {
            Toast.makeText(requireContext(), "Meal ID not found", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MainViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.mealDetail.observe(viewLifecycleOwner) { mealDetail ->
            bindMealDetail(mealDetail)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindMealDetail(mealDetail: MealDetail) {
        binding.apply {
            tvMealName.text = mealDetail.strMeal
            tvCategory.text = "Category: ${mealDetail.strCategory}"
            tvArea.text = "Area: ${mealDetail.strArea}"
            tvInstructions.text = mealDetail.strInstructions

            val ingredients = mealDetail.getIngredientsList()
            tvIngredients.text = ingredients.joinToString("\n") { "• $it" }

            Glide.with(requireContext())
                .load(mealDetail.strMealThumb)
                .centerCrop()
                .into(ivMealImage)
        }
    }

}
