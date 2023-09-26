package com.github.gasblg.firebaseapp.presentation.ui.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.github.gasblg.firebaseapp.presentation.R
import com.github.gasblg.firebaseapp.presentation.databinding.DialogRemoveBinding
import com.github.gasblg.firebaseapp.presentation.ui.fragments.main.MainFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RemoveDialog : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "RemoveDialog"
        private const val ID = "id"

        fun createInstance(itemId: String?): RemoveDialog {
            val bundle = Bundle()
            bundle.putString(ID, itemId)
            val fragment = RemoveDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var binding: DialogRemoveBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialogResizeTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dialogRemoveItemBinding =
            DialogRemoveBinding.inflate(inflater, container, false)
        this.binding = dialogRemoveItemBinding
        return dialogRemoveItemBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val id = it.getString(ID) as String
            bindClicks(id)
        }
    }

    private fun bindClicks(itemId: String) {
        binding?.remove?.setOnClickListener {
            val data = bundleOf(MainFragment.ITEM_ID to itemId)
            requireActivity().supportFragmentManager.setFragmentResult(
                MainFragment.REMOVE_KEY,
                data
            )
            dismiss()
        }
        binding?.cancel?.setOnClickListener {
            dismiss()
        }
        binding?.close?.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}