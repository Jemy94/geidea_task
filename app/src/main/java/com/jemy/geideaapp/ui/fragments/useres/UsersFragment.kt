package com.jemy.geideaapp.ui.fragments.useres

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.jemy.geideaapp.R
import com.jemy.geideaapp.data.model.entities.UserEntity
import com.jemy.geideaapp.databinding.FragmentUsersBinding
import com.jemy.geideaapp.ui.fragments.useres.adapter.UsersAdapter
import com.jemy.geideaapp.utils.Constants
import com.jemy.geideaapp.utils.ResourceState
import com.jemy.geideaapp.utils.extensions.gone
import com.jemy.geideaapp.utils.extensions.toastLong
import com.jemy.geideaapp.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private var binding: FragmentUsersBinding? = null
    private val adapter by lazy { UsersAdapter() }
    private val viewModel: UsersViewModel by viewModels()
    private fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return setupViewBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPhonesList()
        setupSwipeLayoutAction()
    }

    private fun getPhonesList() {
        viewModel.getUsers()
            .observe(viewLifecycleOwner) { resources ->
                when (resources.state) {
                    ResourceState.LOADING -> {
                        binding?.swipeLayout?.isRefreshing = true
                    }
                    ResourceState.SUCCESS -> {
                        binding?.swipeLayout?.isRefreshing = false
                        resources.data?.let { usersList ->
                            if (usersList.isEmpty()) {
                                binding?.usersRecycler?.gone()
                                binding?.noDataTextView?.visible()
                            } else {
                                binding?.noDataTextView?.gone()
                                binding?.usersRecycler?.visible()
                                setupPhonesAdapter((usersList))
                            }
                        }
                    }
                    ResourceState.ERROR -> {
                        binding?.swipeLayout?.isRefreshing = false
                        resources.message?.let {
                            toastLong(it)
                            Log.d("Error", it)
                        }
                    }
                }
            }
    }

    private fun setupPhonesAdapter(users: List<UserEntity>) {
        binding?.usersRecycler?.adapter = adapter
        adapter.addItems(users)

        adapter.setItemCallBack { user ->
            val bundle = bundleOf(Constants.Key.USER_ID to user?.id)
            view?.findNavController()
                ?.navigate(R.id.action_usersFragment_to_userDetailsFragment, bundle)
        }
    }

    private fun setupSwipeLayoutAction() {
        binding?.swipeLayout?.setOnRefreshListener {
            adapter.clear()
            getPhonesList()
        }
    }
}