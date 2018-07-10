package io.github.wotaslive.room


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.wotaslive.databinding.FragPhotoBinding
import io.github.wotaslive.utils.obtainViewModel


class PhotoFragment : Fragment() {
    private lateinit var viewDataBinding: FragPhotoBinding
    private lateinit var viewModel: RoomViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = FragPhotoBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as RoomDetailActivity).obtainViewModel(RoomViewModel::class.java).also {
            viewDataBinding.viewModel = it
            viewDataBinding.setLifecycleOwner(this)
        }
        viewDataBinding.photo.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        fun newInstance() = PhotoFragment()
    }
}
