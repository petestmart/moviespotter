package com.petestmart.moviespotter

//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//
//class SearchFragment : Fragment() {
//    private var binding: SearchFragmentBinding? = null
//    private val binding get() = binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = SearchFragmentBinding.inflate(inflater, container, false)
//
//        binding.textInputLayout.setEndIconOnClickListener {
//            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
//        }
//
//        binding.textInputEditText.doOnTextChanged { text, start, before, count ->
//            if (text!!.length > 10) {
//                binding.textInputLayout.error = "Over 10 characters"
//            } else if (text.length < 10) {
//                binding.textInputLayout.error = null
//            }
//        }
//
//        return binding.root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
//}