package com.example.firebase

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        sign_up.setOnClickListener() {
            sign()


        }
    }

    private fun successToast(){

        var myToast: Toast=Toast.makeText(this, "", Toast.LENGTH_SHORT)
        val txtToast : TextView =myToast.view.findViewById(android.R.id.message)

        txtToast.text="Account created successfully"
        txtToast.setTextColor(Color.WHITE)
        txtToast.setBackgroundColor(Color.GRAY)
        txtToast.textSize = 20F
        myToast.setGravity(Gravity.CENTER,0,100 )
        myToast.show()

    }

    private fun failed(){




        editTextTextEmailAddress.error = "This email is already in use"
        editTextTextEmailAddress.requestFocus()
        return
    }


    private fun sign() {
        if (editTextTextEmailAddress.text.toString().isEmpty()) {
            editTextTextEmailAddress.error = "Required"
            editTextTextEmailAddress.requestFocus()

            return

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editTextTextEmailAddress.text.toString())
                .matches()
        ) {

            editTextTextEmailAddress.error = "Enter valid email"
            editTextTextEmailAddress.requestFocus()
            return
        }

        if (editTextTextPassword.text.toString().isEmpty()) {
            editTextTextPassword.error = "Required"
            editTextTextPassword.requestFocus()
            return
        }



        auth.createUserWithEmailAndPassword(
            editTextTextEmailAddress.text.toString(),
            editTextTextPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    successToast()

                }else {
                    try {
                        throw task.exception!!


                    } catch (existEmail: FirebaseAuthUserCollisionException) {
                        failed()


                    }




                }
            }

    }
}





