package com.unhappychoice.droidflyer.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.github.salomonbrys.kodein.Kodein
import com.unhappychoice.droidflyer.R
import com.unhappychoice.droidflyer.presentation.view.LimitOrderView
import com.unhappychoice.droidflyer.presentation.view.MarketOrderView
import com.unhappychoice.droidflyer.presentation.view.OrdersView

class OrderViewPagerAdapter(val context: Context, val kodein: Kodein) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
    override fun getCount(): Int = 3
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return when (position) {
            0 -> createMarketOrderView(container)
            1 -> createLimitOrderView(container)
            else -> createOrdersView(container)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun createMarketOrderView(container: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.market_order_view, container, false) as MarketOrderView
        view.injector.inject(kodein)
        container?.addView(view)
        return view
    }

    private fun createLimitOrderView(container: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.limit_order_view, container, false) as LimitOrderView
        view.injector.inject(kodein)
        container?.addView(view)
        return view
    }

    private fun createOrdersView(container: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.orders_view, container, false) as OrdersView
        view.injector.inject(kodein)
        container?.addView(view)
        return view
    }
}
