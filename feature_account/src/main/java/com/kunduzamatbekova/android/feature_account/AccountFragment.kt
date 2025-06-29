package com.kunduzamatbekova.android.feature_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kunduzamatbekova.android.feature_account.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentAccountBinding most not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 