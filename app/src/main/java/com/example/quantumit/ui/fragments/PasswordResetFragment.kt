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
import kotlinx.android.synthetic.main.fragment_password_reset.*

class PasswordResetFragment : Fragment() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_password_reset, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnResetPassword.setOnClickListener { v ->
            val email = etMail.text.toString()
            if (email.isNotEmpty()) {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Snackbar.make(v,
                            "Password link has been shared with your email address",
                            Snackbar.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                }
            } else {
                Snackbar.make(v, "Please enter valid email address", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}