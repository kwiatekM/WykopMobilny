package io.github.feelfreelinux.wykopmobilny.ui.modules.profile.links.related

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.feelfreelinux.wykopmobilny.R
import io.github.feelfreelinux.wykopmobilny.base.BaseFeedFragment
import io.github.feelfreelinux.wykopmobilny.base.BaseFragment
import io.github.feelfreelinux.wykopmobilny.models.dataclass.Related
import io.github.feelfreelinux.wykopmobilny.models.fragments.DataFragment
import io.github.feelfreelinux.wykopmobilny.models.fragments.PagedDataModel
import io.github.feelfreelinux.wykopmobilny.models.fragments.getDataFragmentInstance
import io.github.feelfreelinux.wykopmobilny.models.fragments.removeDataFragment
import io.github.feelfreelinux.wykopmobilny.ui.adapters.ProfileRelatedAdapter
import io.github.feelfreelinux.wykopmobilny.ui.adapters.RelatedListAdapter
import io.github.feelfreelinux.wykopmobilny.ui.modules.profile.ProfileActivity
import io.github.feelfreelinux.wykopmobilny.ui.modules.profile.ProfileFragmentNotifier
import io.github.feelfreelinux.wykopmobilny.utils.prepare
import kotlinx.android.synthetic.main.entries_fragment.*
import javax.inject.Inject

class ProfileRelatedFragment : BaseFragment(), ProfileRelatedView {
    override fun addDataToAdapter(entryList: List<Related>, shouldClearAdapter: Boolean) {
        feedAdapter.addData(entryList, shouldClearAdapter)
    }

    override fun disableLoading() {
        feedAdapter.disableLoading()
    }

    val username by lazy { (activity as ProfileActivity).username }
    @Inject lateinit var feedAdapter : ProfileRelatedAdapter
    @Inject lateinit var presenter : ProfileRelatedPresenter
    companion object {

        fun newInstance() : ProfileRelatedFragment {
            val fragment = ProfileRelatedFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.subscribe(this)
        presenter.username = username
        recyclerView.prepare()
        feedAdapter.loadNewDataListener = {
            presenter.loadData(false)
        }
        recyclerView.adapter = feedAdapter
        presenter.loadData(true)
    }
}