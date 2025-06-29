package com.kunduzamatbekova.android.feature_login

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kunduzamatbekova.android.core.R
import com.kunduzamatbekova.android.feature_home.HomeFragment
import com.kunduzamatbekova.android.feature_login.databinding.FragmentLoginBinding
import com.kunduzamatbekova.android.feature_login.service.UrlService
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject
import kotlinx.coroutines.launch
import kotlin.text.filter
import kotlin.text.matches
import kotlin.toString

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentLoginBinding most not be null")

    private val viewModel: LoginFragmentViewModel by viewModel()
    private val urlService: UrlService by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.navigation).visibility =
            View.GONE

        with(binding) {
            edEmail.filters = arrayOf(emailInputFilter())
            edEmail.addTextChangedListener {
                viewModel.performs(
                    LoginEvent.OnEmailChanged(
                        it.toString()
                    )
                )
            }
            edPassword.addTextChangedListener {
                viewModel.performs(
                    LoginEvent.OnPasswordChanged(
                        it.toString()
                    )
                )
            }
        }

        binding.btnLogin.setOnClickListener {
            if (viewModel.isButtonEnabled.value) {
                requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
                    .edit().putBoolean("is_log_in", true).apply()

                requireActivity().findViewById<BottomNavigationView>(R.id.navigation).visibility =
                    View.VISIBLE

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            } else {
                val message = when {
                    !viewModel.emailValidate.value -> "Введите корректный email"
                    !viewModel.passwordValidate.value -> "Пароль должен быть не менее 6 символов"
                    else -> ""
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnVk.setOnClickListener {
            lifecycleScope.launch {
                urlService.openVk()
            }
        }

        binding.btnOk.setOnClickListener {
            lifecycleScope.launch {
                urlService.openOk()
            }
        }
    }

    private fun emailInputFilter(): InputFilter {
        val regex = Regex("[a-zA-Z0-9@._\\-]+")
        return InputFilter { source, _, _, _, _, _ ->
            val filtered = source.filter { it.toString().matches(regex) }
            if (filtered.length == source.length) null else filtered
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}