package com.suvodeep.supergrocer.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.suvodeep.supergrocer.SuperGrocerViewModel

@Composable
fun LoginScreen(superGrocerViewModel: SuperGrocerViewModel, navController: NavHostController){
    val phoneNo=superGrocerViewModel.phoneNo.collectAsState()
    val context= LocalContext.current
    val opt=superGrocerViewModel.otp.collectAsState()
    val verificationId=superGrocerViewModel.verificationId.collectAsState()
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.e("PhoneAuth", "Verification failed", e)
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            superGrocerViewModel.setVerificationId(verificationId)
            Toast.makeText(context, "OTP Sent", Toast.LENGTH_SHORT).show()
            superGrocerViewModel.resetTimer()
            superGrocerViewModel.startTimer()
        }
    }

    if(!verificationId.value.isEmpty()){
        OTPScreen(otp=opt.value,superGrocerViewModel,callbacks)
    }else{
        PhoneNumberScreen(phoneNo = phoneNo.value, superGrocerViewModel = superGrocerViewModel,callbacks=callbacks)
    }
}