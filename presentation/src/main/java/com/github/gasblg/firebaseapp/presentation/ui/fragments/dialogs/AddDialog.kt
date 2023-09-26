package com.github.gasblg.firebaseapp.presentation.ui.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.presentation.R
import com.github.gasblg.firebaseapp.presentation.databinding.DialogAddBinding
import com.github.gasblg.firebaseapp.presentation.ui.fragments.main.MainFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddDialog : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "AddDialog"
    }

    private var binding: DialogAddBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialogResizeTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dialogAddBinding = DialogAddBinding.inflate(inflater, container, false)
        this.binding = dialogAddBinding
        return dialogAddBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks() {
        binding?.add?.setOnClickListener {
            val name = binding?.name?.text.toString()
            val description = binding?.description?.text.toString()
            if (name.isNotBlank() && description.isNotBlank()) {
                val item = Item(id = "", name = name, description = description)
                val data = bundleOf(MainFragment.ITEM to item)
                requireActivity().supportFragmentManager.setFragmentResult(
                    MainFragment.ADD_KEY,
                    data
                )
                dismiss()
            } else {
                Toast.makeText(requireContext(), getString(R.string.not_filled), Toast.LENGTH_LONG).show()
            }
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