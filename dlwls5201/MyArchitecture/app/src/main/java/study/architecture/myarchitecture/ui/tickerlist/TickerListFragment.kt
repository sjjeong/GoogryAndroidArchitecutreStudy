package study.architecture.myarchitecture.ui.tickerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_ticker_list.*
import org.jetbrains.anko.support.v4.toast
import study.architecture.myarchitecture.BaseFragment
import study.architecture.myarchitecture.R
import study.architecture.myarchitecture.data.Injection
import study.architecture.myarchitecture.ui.model.TickerItem

class TickerListFragment : BaseFragment(), TickerListContract.View {

    private val tickerAdapter by lazy {
        TickerAdapter { toast(it.toString()) }
    }

    private lateinit var presenter: TickerListContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_ticker_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = TickerListPresenter(
            Injection.provideFolderRepository(context!!),
            this@TickerListFragment
        )

        initRecyclerView()
        presenter.createdView()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    private fun initRecyclerView() {

        rvTickerList.adapter = tickerAdapter
    }

    override fun showProgress() {
        pbTickerList.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pbTickerList.visibility = View.GONE
    }

    override fun getKeyMarkets() = arguments?.getString(KEY_MARKETS) ?: ""

    override fun setTickers(tickers: MutableList<TickerItem>) {
        tickerAdapter.setItem(tickers)
    }

    override fun orderByField(bundle: Bundle) {
        tickerAdapter.orderByField(bundle)
    }

    companion object {

        const val KEY_MARKETS = "markets"

        fun newInstance(tickers: String) = TickerListFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_MARKETS, tickers)
            }
        }
    }
}