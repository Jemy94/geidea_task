package com.jemy.geideaapp.ui.fragments.userdetails

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jemy.geideaapp.R
import com.jemy.geideaapp.TimerService
import com.jemy.geideaapp.data.model.reposnose.UserResponse
import com.jemy.geideaapp.databinding.FragmentUserDetailsBinding
import com.jemy.geideaapp.utils.Constants
import com.jemy.geideaapp.utils.ResourceState
import com.jemy.geideaapp.utils.extensions.load
import com.jemy.geideaapp.utils.extensions.toastLong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private var binding: FragmentUserDetailsBinding? = null
    private val userId: Int? by lazy { arguments?.getInt(Constants.Key.USER_ID, 0) }
    private val viewModel: UserDetailsViewModel by viewModels()


    private fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    private val broadcastReceiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                updateCounterText(intent)
            }
        }
    }

    private fun updateCounterText(intent: Intent?) {
        if (intent!!.extras != null) {
            val time = intent.getLongExtra(Constants.Key.COUNT_DOWN, 0)
            binding?.counterTV?.text = time.toString()
            if (time.toString() == "0") {
                requireActivity().onBackPressed()
            }
        }
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
        startTimerService()
        userId?.let { getDetails(it) }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(
            broadcastReceiver,
            IntentFilter(Constants.Key.COUNT_DOWN_BR)
        )
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(
            broadcastReceiver
        )
    }

    override fun onDestroy() {
        requireActivity().stopService(Intent(requireActivity(), TimerService::class.java))
        super.onDestroy()

    }

    private fun startTimerService() {
        requireActivity().startService(Intent(requireActivity(), TimerService::class.java))
    }

    private fun getDetails(id: Int) {
        viewModel.getUserDetails(id).observe(viewLifecycleOwner) { resources ->
            when (resources.state) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {
                    resources.data?.let { response ->
                        setupDetailsView(response)
                    }
                }
                ResourceState.ERROR -> {
                    resources.message.let {
                    }
                }
            }
        }

    }

    private fun setupDetailsView(user: UserResponse) {
        binding?.name?.text = "${user.data?.firstName} ${user.data?.lastName}"
        binding?.email?.text = user.data?.email
        binding?.id?.text = user.data?.id.toString()
        user.data?.avatar?.let { binding?.userImage?.load(it) }
    }
}