package com.petestmart.moviespotter

//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.fragment.app.Fragment
//
//class JetpackFragment : Fragment() {
//    private var _binding: JetpackFragmentBinding? = null
//    // This property is only valid between onCreateView and onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = JetpackFragmentBinding.inflate(inflater, container, false)
//        val view = binding.root
//        view.composeView.apply {
//            // Dispose the Composition when the view's LifecycleOwner
//            // is destroyed
//            setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
//            setContent {
//                // In Compose world
//                MaterialTheme {
//                    Text("Hello Compose!")
//                }
//            }
//        }
//        return view
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}