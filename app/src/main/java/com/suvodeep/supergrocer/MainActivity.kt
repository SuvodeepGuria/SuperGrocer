package com.suvodeep.supergrocer

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.suvodeep.supergrocer.ui.theme.SuperGrocerTheme
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity(),PaymentResultWithDataListener {
    private val superGrocerViewModel: SuperGrocerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Checkout.preload(this)
        enableEdgeToEdge()
        setContent {
            SuperGrocerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SuperGrocerApp(
                        modifier = Modifier.padding(innerPadding),
                        superGrocerViewModel = superGrocerViewModel,
                        item = emptyList()
                    )
                }
            }
        }
    }

    fun initPayment() {
            val activity:Activity = this
            val co= Checkout()
            co.setKeyID("rzp_test_uUNApDaMZ0mQHg")
            try {
                val options = JSONObject()
                options.put("name","SuperGrocer")
                options.put("description","Grocery app")
                //You can omit the image option to fetch the image from the Dashboard
                options.put("image", R.drawable.supergrocerapplogo)
                options.put("theme.color", "#00FB0A5C");
                options.put("currency","INR");
                options.put("amount",superGrocerViewModel.grandTotal.value*100)

                val retryObj =  JSONObject();
                retryObj.put("enabled", true);
                retryObj.put("max_count", 4);
                options.put("retry", retryObj);

//                val prefill = JSONObject()
//                prefill.put("email","suvodeepguria@gmail.com")
//                prefill.put("contact","8345842808")

//                options.put("prefill",prefill)
                co.open(activity,options)
            }catch (e: Exception){
                Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        superGrocerViewModel.cleanCart()
        Toast.makeText(this, "Payment Success!\nYour Order has been placed successfully", Toast.LENGTH_SHORT).show()
        val currentDateTime = SimpleDateFormat("dd/MM/yyyy\nhh:mm a", Locale.getDefault()).format(Date())

        val groupedCart = superGrocerViewModel.cartItem.value
            .groupBy { it.itemName }
            .map { (name, itemList) ->
                val quantityCount = itemList.size
                val imageUrl = itemList.first().imageResourceId
                hashMapOf(
                    "name" to name,
                    "quantity" to "$quantityCount", // or just "$quantityCount"
                    "imageUrl" to imageUrl
                )
            }

        val order = hashMapOf(
            "items" to groupedCart,
            "paymentMethod" to "Online",
            "totalPaid" to superGrocerViewModel.grandTotal.value,
            "address" to superGrocerViewModel.selectedAddress.value,
            "time&Date" to currentDateTime
        )

        superGrocerViewModel.yourOrder(order)

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
//        Toast.makeText(this, "Payment Failed!", Toast.LENGTH_SHORT).show()
    }
}
