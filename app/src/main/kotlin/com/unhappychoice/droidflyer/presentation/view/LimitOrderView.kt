package com.unhappychoice.droidflyer.presentation.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.Log
import com.github.salomonbrys.kodein.instance
import com.unhappychoice.droidflyer.domain.service.CurrentStatusService
import com.unhappychoice.droidflyer.presentation.adapter.BoardAdapter
import com.unhappychoice.droidflyer.presentation.adapter.BoardType
import com.unhappychoice.droidflyer.presentation.presenter.LimitOrderPresenter
import com.unhappychoice.droidflyer.presentation.style.DefaultStyle
import com.unhappychoice.droidflyer.presentation.view.core.BaseView
import com.unhappychoice.norimaki.extension.subscribeNext
import com.unhappychoice.norimaki.extension.subscribeOnIoObserveOnUI
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.limit_order_view.view.*
import java.util.concurrent.TimeUnit

class LimitOrderView(context: Context, attr: AttributeSet?) : BaseView(context, attr) {
    val presenter: LimitOrderPresenter by instance()

    private val currentStatusService: CurrentStatusService by instance()
    private val askAdapter by lazy { BoardAdapter(context, BoardType.Ask) }
    private val bidAdapter by lazy { BoardAdapter(context, BoardType.Bid) }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.takeView(this)

        askList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        askList.adapter = askAdapter
        bidList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bidList.adapter = bidAdapter

        currentStatusService.board.asObservable()
            .throttleLast(1, TimeUnit.SECONDS)
            .subscribeOnIoObserveOnUI()
            .subscribeNext {
                askAdapter.items.value = it.asks.sortedBy { it.price }.take(50)
                bidAdapter.items.value = it.bids.sortedByDescending { it.price }.take(50)
                askAdapter.notifyDataSetChanged()
                bidAdapter.notifyDataSetChanged()
            }.addTo(bag)
    }

    override fun onDetachedFromWindow() {
        presenter.dropView(this)
        super.onDetachedFromWindow()
    }
}