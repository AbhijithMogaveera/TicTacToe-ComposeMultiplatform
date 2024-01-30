package com.abhijith.foundation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


abstract class BindingFragment<T : ViewBinding> : Fragment() {

    class BindingScope : CoroutineScope by CoroutineScope(Dispatchers.Main + SupervisorJob())

    private var binding: T? = null
    private var scope: BindingScope? = null

    fun <R> withBindingOnViewScope(action: suspend BindingScope.(binding: T) -> R) {
        scope?.launch {
            scope?.apply {
                action(requireBinding())
            }
        }
    }

    fun getBinding() = binding
    fun requireBinding() = requireNotNull(getBinding())

    abstract fun onCreatedViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): T

    abstract fun onBindingCreated(view: View, savedInstanceState: Bundle?)

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scope = BindingScope()
        return onCreatedViewBinding(inflater, container, savedInstanceState).apply {
            binding = this
        }.root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindingCreated(view, savedInstanceState)
    }

    open fun onDestroyBinding() {}

    override fun onDestroyView() {
        onDestroyBinding()
        scope?.cancel()
        scope = null
        binding = null
        super.onDestroyView()
    }
}