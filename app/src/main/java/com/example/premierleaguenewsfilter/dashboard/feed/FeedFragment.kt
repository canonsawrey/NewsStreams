package com.example.premierleaguenewsfilter.dashboard.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.premierleaguenewsfilter.R
import com.example.premierleaguenewsfilter.common.BaseAdapter
import com.example.premierleaguenewsfilter.dashboard.watched.WatchedFragment.Companion.fadeOutTransition
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.empty_state
import kotlinx.android.synthetic.main.fragment_feed.loading_state

class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private val adapter = BaseAdapter<NewsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[FeedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.news.observe(viewLifecycleOwner, Observer { receiveNewsList(it) })
        viewModel.retrievePlayerNews()
        setupRecycler()
    }

    private fun setupRecycler() {
        feed_recycler.layoutManager = LinearLayoutManager(requireContext())
        feed_recycler.adapter = adapter
        feed_recycler.itemAnimator = DefaultItemAnimator()

    }

    private fun receiveNewsList(list: List<NewsItem>?) {
        when (list) {
            null -> showLoadingState()
            else -> {
                if (list.isEmpty()) {
                    showEmptyState()
                } else {
                    adapter.submitList(list)
                    showNews()
                }
            }
        }
    }

    private fun showNews() {
        TransitionManager.beginDelayedTransition(container_feed, fadeOutTransition)
        loading_state.visibility = View.INVISIBLE
        feed_recycler.visibility = View.VISIBLE
        empty_state.visibility = View.INVISIBLE
    }

    private fun showEmptyState() {
        feed_recycler.visibility = View.INVISIBLE
        loading_state.visibility = View.INVISIBLE
        empty_state.visibility = View.VISIBLE
    }

    private fun showLoadingState() {
        feed_recycler.visibility = View.INVISIBLE
        loading_state.visibility = View.VISIBLE
        empty_state.visibility = View.INVISIBLE
    }
}