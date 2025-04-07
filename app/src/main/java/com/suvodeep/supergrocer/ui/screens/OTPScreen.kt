package com.suvodeep.supergrocer.ui.screens

import android.app.Activity
import android.content.Context
import android.text.format.DateUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.suvodeep.supergrocer.SuperGrocerViewModel
import com.suvodeep.supergrocer.auth
import java.util.concurrent.TimeUnit


@Composable
fun OTPScreen(
    otp: String,
    superGrocerViewModel: SuperGrocerViewModel,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
) {
    val context= LocalContext.current
    val verificationId = superGrocerViewModel.verificationId.collectAsState()
    val phoneNo=superGrocerViewModel.phoneNo.collectAsState()
    val timer by superGrocerViewModel.timer.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 100.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = "Verified Icon", tint = Color.Blue,
            modifier = Modifier.size(100.dp))
        Text("Enter 6 digit OTP", fontSize = 20.sp)
        OTPTextBox(otp = otp, superGrocerViewModel = superGrocerViewModel)
        Button(onClick = {
            if(otp.isEmpty()){
                Toast.makeText(context, "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }else{
                val credential = PhoneAuthProvider.getCredential(verificationId.value!!, otp)
                signInWithPhoneAuthCredential(credential,context,superGrocerViewModel)
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)) {
            Text("Verify", fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            val fontSize=if(timer>0) 10 else 20
            Text(if(timer>0) "Resend OTP (${DateUtils.formatElapsedTime(timer)})" else "Resend OTP",
                fontSize = fontSize.sp,
                fontWeight = if (timer>0) FontWeight.Normal else FontWeight.Bold,
                modifier = Modifier.clickable(onClick = {
                    val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber("+91"+phoneNo.value) // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(context as Activity) // Activity (for callback binding)
                    .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                    .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)}))
        }
    }
}


@Composable
fun OTPTextBox(otp: String,
               superGrocerViewModel: SuperGrocerViewModel){
    BasicTextField(value = otp,
        onValueChange = {
            superGrocerViewModel.setOTP(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            repeat (6){index->
                val number=when{
                    index>=otp.length->""
                    else->otp[index]
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(4.dp)) {
                    Text("$number",fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Box(modifier = Modifier
                        .width(40.dp)
                        .height(2.dp)
                        .background(Color.Gray))
                    {
                        }
                }
            }

        }
    }

}

private fun signInWithPhoneAuthCredential(
    credential: PhoneAuthCredential,
    context: Context,
    superGrocerViewModel: SuperGrocerViewModel
) {
    val activity = context as? android.app.Activity
    activity?.let {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(it) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    Toast.makeText(context, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show()
//                    LottieAnimation(composition=composition, modifier = Modifier.size(200.dp), iterations = 1)
                    superGrocerViewModel.setUser(user)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(context, "Invalid OTP!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
//    auth.signInWithCredential(credential)
//        .addOnCompleteListener(context as activity) { task ->
//            if (task.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
////                Log.d(TAG, "signInWithCredential:success")
//
//                val user = task.result?.user
//            } else {
//                // Sign in failed, display a message and update the UI
////                Log.w(TAG, "signInWithCredential:failure", task.exception)
//                if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                    // The verification code entered was invalid
//                }
//                // Update UI
//            }
//        }
