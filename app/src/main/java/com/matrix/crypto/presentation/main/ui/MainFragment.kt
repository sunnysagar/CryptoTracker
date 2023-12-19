package com.matrix.crypto.presentation.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.matrix.crypto.R
import com.matrix.crypto.databinding.FragmentMainBinding
import com.matrix.crypto.domain.Constants.ACCESS_KEY
import com.matrix.crypto.presentation.adapters.ItemsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val itemsListAdapter = ItemsListAdapter()

    private val handler = Handler(Looper.getMainLooper())
    private val refreshRunnable = object : Runnable {
        override fun run() {
            viewModel.refreshData(ACCESS_KEY)
            handler.postDelayed(this, 3 * 60 * 1000) // 3 minutes in milliseconds
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        _binding = FragmentMainBinding.bind(view)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData(ACCESS_KEY)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemsListAdapter
        }

        // Start the auto-refresh mechanism
        handler.post(refreshRunnable)

        viewModel.coinInfoList.observe(viewLifecycleOwner) { coinInfoList ->
            itemsListAdapter.setItemsList(coinInfoList)
        }

        viewModel.lastRefreshTime.observe(viewLifecycleOwner) { lastRefreshTime ->
            Log.d("API RESPONSES:: ", "Last Refresh Time: ${formatTimestamp(lastRefreshTime)}")
            // Format the timestamp (lastRefreshTime) as needed
            val formattedTime = formatTimestamp(lastRefreshTime)

            // Remove existing TextViews from the toolbar
            binding.toolbar.removeViews(1, binding.toolbar.childCount - 1)

            // Show the progress bar when refreshing starts
            binding.progressBar.visibility = View.VISIBLE

            // Create a text view for the last refresh time on the right side
            val textView = TextView(requireContext())
            textView.text = "Last Refresh: $formattedTime"
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white)) // Set your color
            textView.textSize = 14f

            // Add the text view to the toolbar and set layout parameters
            val params = Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.BOTTOM
            textView.layoutParams = params

            // Hide the progress bar when the text view is added
            textView.postDelayed({
                binding.progressBar.visibility = View.GONE
            }, 1000) // You can adjust the delay time as needed

            binding.toolbar.addView(textView)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Stop the auto-refresh when the fragment is destroyed
        handler.removeCallbacks(refreshRunnable)
        _binding = null
    }

    private fun formatTimestamp(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }

}