package com.github.gasblg.firebaseapp.presentation.ui.fragments.dialogs

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.github.gasblg.firebaseapp.analytics.data.Event
import com.github.gasblg.firebaseapp.analytics.data.Param
import com.github.gasblg.firebaseapp.analytics.manager.AnalyticsManager
import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.presentation.R
import com.github.gasblg.firebaseapp.presentation.databinding.DialogAddBinding
import com.github.gasblg.firebaseapp.presentation.di.DaggerBottomSheetDialogFragment
import com.github.gasblg.firebaseapp.presentation.ui.fragments.main.MainFragment
import javax.inject.Inject


class AddDialog : DaggerBottomSheetDialogFragment() {

    @Inject
    lateinit var analytics: AnalyticsManager

    companion object {
        const val TAG = "AddDialog"
        const val ITEM_TYPE = "item_type"
        const val ITEM = "item"
        const val ADD = "add"
        const val EDIT = "edit"
        fun createInstance(
            itemType: String,
            item: Item? = null
        ): AddDialog {
            val bundle = Bundle()
            bundle.putSerializable(ITEM_TYPE, itemType)
            bundle.putSerializable(ITEM, item)
            val fragment = AddDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var binding: DialogAddBinding? = null
    private var item: Item? = null
    private var itemType: String? = null
    private var actionTapType: String = ""
    private var paramType: String = ""

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
        initArguments()
        bindClicks()
    }

    private fun initArguments() {
        arguments?.let {
            itemType = it.getString(ITEM_TYPE) as String
            item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable(ITEM, Item::class.java)
            } else {
                it.getSerializable(ITEM) as Item?
            }
            actionTapType = if (itemType == ADD) Event.ADD_TAP else Event.EDIT_TAP
            paramType = if (itemType == ADD) Param.ADD else Param.EDIT

            fillViewInfo()
            sendEvent()
        }
    }

    private fun sendEvent() {
        if (itemType == ADD) analytics.openAddDialog() else analytics.openEditDialog()
    }

    private fun fillViewInfo() {
        binding?.title?.text = if (itemType == ADD) getString(R.string.add_item) else getString(R.string.edit_item)
        binding?.add?.text = if (itemType == ADD) getString(R.string.add) else getString(R.string.edit)
        if (itemType == EDIT) {
            item?.let {
                binding?.name?.setText(item?.name)
                binding?.description?.setText(item?.description)
            }
        }
    }

    private fun bindClicks() {
        binding?.add?.setOnClickListener {
            val name = binding?.name?.text.toString()
            val description = binding?.description?.text.toString()

            if (name.isNotBlank() && description.isNotBlank()) {
                analytics.actionTap(actionTapType, paramType)
                sendResult(name, description)
                dismiss()
            } else {
                showToast()
            }
        }

        binding?.cancel?.setOnClickListener {
            analytics.actionTap(actionTapType, Param.CANCEL)
            dismiss()
        }
        binding?.close?.setOnClickListener {
            analytics.actionTap(actionTapType, Param.CLOSE)
            dismiss()
        }
    }

    private fun showToast(){
        Toast.makeText(requireContext(), getString(R.string.not_filled), Toast.LENGTH_LONG).show()
    }

    private fun sendResult(name: String, description: String) {
        val itemId = if (itemType == EDIT) item?.id ?: "" else ""
        item = Item(id = itemId, name = name, description = description)
        val data = bundleOf(ITEM to item, ITEM_TYPE to itemType)
        requireActivity().supportFragmentManager.setFragmentResult(
            MainFragment.ADD_KEY,
            data
        )
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}