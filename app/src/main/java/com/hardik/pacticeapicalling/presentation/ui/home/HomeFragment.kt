package com.hardik.pacticeapicalling.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardik.pacticeapicalling.MainActivity
import com.hardik.pacticeapicalling.R
import com.hardik.pacticeapicalling.adapter.UserAdapter
import com.hardik.pacticeapicalling.databinding.FragmentHomeBinding
import com.hardik.pacticeapicalling.presentation.MainViewModel
import com.hardik.pacticeapicalling.presentation.UserListState
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: MainViewModel
    lateinit var userAdapter: UserAdapter


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).mainViewModel
        arguments?.let {}
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
                             ) : View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        setUpRecyclerView()
        userAdapter.setOnItemClickListener {}

//        viewModel.state.observe(viewLifecycleOwner) { userListState ->
//            // Update UI based on the state
//            if (userListState.isLoading) {
//                // Show loading indicator
//                showProgressBar()
//            } else if (userListState.error.isNotEmpty()) {
//                // Show error message
//                Toast.makeText(requireContext(), userListState.error, Toast.LENGTH_SHORT).show()
//                hideProgressBar()
//            } else {
//                // Update UI with the user list
//                val users = userListState.users
//                hideProgressBar()
//                userAdapter.differ.submitList(users.toList())
//                binding.recyclerview.setPadding(0, 0, 0, 0)
//            }
//        }

        // Collecting the StateFlow
        lifecycleScope.launch {
            viewModel.state1.collect { userListState ->
                // Update UI based on the state
                if (userListState.isLoading) {
                    // Show loading indicator
                    showProgressBar()
                } else if (userListState.error.isNotEmpty()) {
                    // Show error message
                    Toast.makeText(requireContext(), userListState.error, Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                } else {
                    // Update UI with the user list
                    val users = userListState.users
                    hideProgressBar()
                    userAdapter.differ.submitList(users.toList())
                    binding.recyclerview.setPadding(0, 0, 0, 0)
                }
            }
        }
        viewModel.fetchUsers()

    }
    private fun setUpRecyclerView() {
        userAdapter = UserAdapter()
        binding.recyclerview.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
//            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
    }
    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}