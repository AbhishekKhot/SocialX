package com.example.quantumit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quantumit.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener { v ->
            val email = etMail.text.toString()
            val password = editText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Snackbar.make(v, "Registered successfully", Snackbar.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_mainFragment_to_newsActivity)
                    } else {
                        Snackbar.make(v, it.exception?.message.toString(), Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Snackbar.make(v, "Empty fields not allowed", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}