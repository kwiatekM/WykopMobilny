package io.github.feelfreelinux.wykopmobilny.ui.modules.profile.actions

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.github.feelfreelinux.wykopmobilny.base.BaseEntryLinkFragment
import io.github.feelfreelinux.wykopmobilny.ui.modules.profile.ProfileActivity
import io.github.feelfreelinux.wykopmobilny.utils.usermanager.UserManagerApi
import javax.inject.Inject

class ActionsFragment : BaseEntryLinkFragment(), ActionsView {
    val username by lazy { (activity as ProfileActivity).username }

    @Inject
    lateinit var presenter: ActionsFragmentPresenter
    override var loadDataListener: (Boolean) -> Unit = {
        presenter.getActions()
    }

    @Inject
    lateinit var userManager: UserManagerApi

    companion object {
        fun newInstance(): androidx.fragment.app.Fragment {
            val fragment = ActionsFragment()
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.subscribe(this)
        presenter.username = username
        entriesAdapter.entryActionListener = presenter
        entriesAdapter.linkActionListener = presenter
        entriesAdapter.loadNewDataListener = { loadDataListener(false) }
        loadDataListener(true)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }
}