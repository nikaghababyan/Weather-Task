package com.example.weather.appbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.weather.appbase.utils.*
import com.example.weather.appbase.viewmodel.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

abstract class FragmentBase<ViewModel : BaseViewModel, ViewBind : ViewBinding> : Fragment() {

    abstract val viewModel: ViewModel

    abstract val binding: ViewBind

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onEach(viewModel.errorNullData){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        onEach()
        onView()
        onViewClick()
    }

    protected open fun onView() {}

    protected open fun onViewClick() {}

    protected open fun onEach() {}

    protected inline fun <reified T> onEach(flow: Flow<T>, crossinline action: (T) -> Unit) = view?.run {
        if (!this@FragmentBase.isAdded) return@run
        flow.onEach { action(it ?: return@onEach) }.observeInLifecycle(viewLifecycleOwner)
    }

}